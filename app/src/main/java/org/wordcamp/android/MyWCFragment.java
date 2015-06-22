package org.wordcamp.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.wordcamp.android.adapters.WCListAdapter;
import org.wordcamp.android.db.DBCommunicator;
import org.wordcamp.android.objects.WordCampDB;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MyWCFragment extends Fragment implements WCListAdapter.WCListener {
    private ListView myWCLists;
    private List<WordCampDB> wordCampDBs;
    private List<WordCampDB> myWordCampDBs;
    private DBCommunicator communicator;
    public WCListAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private TextView emptyView;

    public static MyWCFragment newInstance() {
        return new MyWCFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wc, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        communicator = ((BaseActivity) getActivity()).communicator;
        wordCampDBs = ((BaseActivity) getActivity()).wordCampsList;
        if (wordCampDBs != null) {
            sortAndModifyMyWC();
        }

        View v = getView();
        myWCLists = (ListView) v.findViewById(R.id.scroll);
        emptyView = (TextView)v.findViewById(R.id.empty_view);
        emptyView.setText(getActivity().getString(R.string.empty_mywordcamps));
        myWCLists.setEmptyView(emptyView);
        adapter = new WCListAdapter(myWordCampDBs, getActivity(), this);
        myWCLists.setAdapter(adapter);

        refreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_layout);
        refreshLayout.setColorSchemeResources(R.color.swipe_refresh_color1,
                R.color.swipe_refresh_color2, R.color.swipe_refresh_color3);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ((BaseActivity) getActivity()).onRefreshStart();
            }
        });

        myWCLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), WordCampDetailActivity.class);
                i.putExtra("wc", adapter.getItem(position));
                startActivity(i);
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    public void stopRefresh() {
        refreshLayout.setRefreshing(false);
    }

    public void updateList(List<WordCampDB> wordCampsList) {
        wordCampDBs = wordCampsList;
        sortAndModifyMyWC();
        adapter = new WCListAdapter(myWordCampDBs, getActivity(), this);
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
        adapter = new WCListAdapter(myWordCampDBs, getActivity(), this);
        myWCLists.setAdapter(adapter);
    }

    public void removeSingleMYWC(WordCampDB wordCampDB) {
        myWordCampDBs.remove(wordCampDB);
        adapter = new WCListAdapter(myWordCampDBs, getActivity(), this);
        myWCLists.setAdapter(adapter);
    }

    @Override
    public int addToMyWC(int wcid, int position) {
        return 0;
    }

    @Override
    public void removeMyWC(int wcid, int position) {
        communicator.removeFromMyWCSingle(wcid);
        myWordCampDBs = communicator.getAllMyWc();
        sort();
        adapter = new WCListAdapter(myWordCampDBs, getActivity(), this);
        myWCLists.setAdapter(adapter);
        ((BaseActivity) getActivity()).refreshUpcomingFrag();
    }
}
