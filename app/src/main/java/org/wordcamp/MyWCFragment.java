package org.wordcamp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.parse.ParsePush;

import org.wordcamp.adapters.MyWCListAdapter;
import org.wordcamp.db.DBCommunicator;
import org.wordcamp.objects.WordCampDB;
import org.wordcamp.objects.wordcamp.WordCamps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MyWCFragment extends android.support.v4.app.Fragment implements MyWCListAdapter.OnDeleteListener {
    private ListView myWCLists;
    private List<WordCampDB> wordCampDBs;
    private List<WordCampDB> myWordCampDBs;
    public DBCommunicator communicator;
    public MyWCListAdapter adapter;
    private List<Integer> deleteItems = new ArrayList<>();
    private SwipeRefreshLayout refreshLayout;

    public static MyWCFragment newInstance() {
        return new MyWCFragment();
    }

    public MyWCFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        communicator = ((BaseActivity) getActivity()).communicator;
        wordCampDBs = ((BaseActivity) getActivity()).wordCampsList;
        if (wordCampDBs != null) {
            sortAndModifyMyWC();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        deleteItems = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Activity parentActivity = getActivity();
        View v = inflater.inflate(R.layout.fragment_my_wc, container, false);
        myWCLists = (ListView) v.findViewById(R.id.scroll);
        myWCLists.setEmptyView(v.findViewById(R.id.empty_view));
        adapter = new MyWCListAdapter(myWordCampDBs, parentActivity, this);
        myWCLists.setAdapter(adapter);

        refreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_layout);
        refreshLayout.setColorSchemeColors(Color.parseColor("#3F51B5"),
                Color.parseColor("#FF4081"), Color.parseColor("#9C27B0"));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ((BaseActivity) getActivity()).onRefreshStart();
            }
        });

        myWCLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (!deleteItems.contains(position)) {
                    Intent i = new Intent(getActivity(), WordCampDetailActivity.class);
                    i.putExtra("wc", adapter.getItem(position));
                    startActivity(i);
                } else {
                    MyWCListAdapter.ViewHolder holder = (MyWCListAdapter.ViewHolder) view.getTag();
                    deleteItems.remove(Integer.valueOf(position));
                    holder.delete.setVisibility(View.GONE);

                }
            }
        });

        myWCLists.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.notifyDataSetChanged();
                MyWCListAdapter.ViewHolder holder = (MyWCListAdapter.ViewHolder) view.getTag();
                holder.delete.setVisibility(View.VISIBLE);

                if (!deleteItems.contains(position)) {
                    deleteItems.add(position);
                }
                return true;
            }
        });

        return v;
    }

    public void stopRefresh() {
        refreshLayout.setRefreshing(false);
    }

    public void updateList(List<WordCampDB> wordCampsList) {
        wordCampDBs = wordCampsList;
        sortAndModifyMyWC();
        adapter = new MyWCListAdapter(myWordCampDBs, getActivity(), this);
        myWCLists.setAdapter(adapter);
    }

    private void sortAndModifyMyWC() {
        myWordCampDBs = new ArrayList<>();
        for (int i = 0; i < wordCampDBs.size(); i++) {
            if (wordCampDBs.get(i).isMyWC) {
                myWordCampDBs.add(wordCampDBs.get(i));
            }
        }
        sort();
    }

    private void sort() {
        Collections.sort(myWordCampDBs, new Comparator<WordCampDB>() {
            @Override
            public int compare(WordCampDB lhs, WordCampDB rhs) {
                int lhstime = Integer.parseInt(lhs.getWc_start_date());
                int rhstime = Integer.parseInt(rhs.getWc_start_date());
                return lhstime - rhstime;
            }
        });
    }

    public void addWC(WordCampDB wordCampDB) {
        myWordCampDBs.add(wordCampDB);
        sort();
        adapter = new MyWCListAdapter(myWordCampDBs, getActivity(), this);
        myWCLists.setAdapter(adapter);
    }

    @Override
    public void removeWC() {
        List<Integer> removedWCIDs = new ArrayList<>();
        Collections.sort(deleteItems);
        for (int i = deleteItems.size() - 1; i >= 0; i--) {
            WordCampDB db = adapter.getItem(deleteItems.get(i));
            removedWCIDs.add(db.getWc_id());
            myWordCampDBs.remove((int) deleteItems.get(i));
            if (!db.getTwitter().isEmpty()) {
                ParsePush.unsubscribeInBackground(db.getTwitter().replace("#", ""));
            }
        }
        deleteItems = new ArrayList<>();
        communicator.removeFromMyWC(removedWCIDs);
        adapter = new MyWCListAdapter(myWordCampDBs, getActivity(), this);
        myWCLists.setAdapter(adapter);
        ((BaseActivity) getActivity()).refreshUpcomingFrag();
        ((BaseActivity) getActivity()).refreshPastFrag();

    }

    public void removeSingleMYWC(WordCampDB wordCampDB) {
        wordCampDBs = communicator.getAllWc();
        if (wordCampDBs != null) {
            sortAndModifyMyWC();
        }
        adapter = new MyWCListAdapter(myWordCampDBs, getActivity(), this);
        myWCLists.setAdapter(adapter);
    }

}
