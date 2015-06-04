package org.wordcamp.android;

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

import org.wordcamp.android.adapters.WCListAdapter;
import org.wordcamp.android.db.DBCommunicator;
import org.wordcamp.android.objects.WordCampDB;
import org.wordcamp.android.utils.WordCampUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class UpcomingWCFragment extends android.support.v4.app.Fragment implements WCListAdapter.WCListener {
    private ListView upWCLists;
    private List<WordCampDB> wordCampDBs;
    private DBCommunicator communicator;
    private upcomingFragListener listener;
    private SwipeRefreshLayout refreshLayout;
    public WCListAdapter adapter;

    public static UpcomingWCFragment newInstance() {
        return new UpcomingWCFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_upcoming_wc, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        communicator = ((BaseActivity) getActivity()).communicator;
        wordCampDBs = ((BaseActivity) getActivity()).wordCampsList;
        if (wordCampDBs != null) {
            sortWC();
        }

        View v = getView();
        upWCLists = (ListView) v.findViewById(R.id.scroll);
        refreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_layout);
        refreshLayout.setColorSchemeColors(Color.parseColor("#3F51B5"),
                Color.parseColor("#FF4081"), Color.parseColor("#9C27B0"));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listener.onRefreshStart();
            }
        });
        setProperListPosition();
        adapter = new WCListAdapter(wordCampDBs, getActivity(), this);
        upWCLists.setAdapter(adapter);
        upWCLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), WordCampDetailActivity.class);
                i.putExtra("wc", adapter.getItem(position));
                startActivity(i);
            }
        });

        if (wordCampDBs.size() == 0) {
            startRefresh();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (BaseActivity) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement UpcomingFragListener");
        }
    }

    public void startRefresh() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                listener.onRefreshStart();
            }
        });
    }

    private void sortWC() {
        Collections.sort(wordCampDBs, new Comparator<WordCampDB>() {
            @Override
            public int compare(WordCampDB lhs, WordCampDB rhs) {
                int lhstime = Integer.parseInt(lhs.getWc_start_date());
                int rhstime = Integer.parseInt(rhs.getWc_start_date());
                return lhstime - rhstime;
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void updateList(List<WordCampDB> wordCampsList) {
        wordCampDBs = wordCampsList;
        sortWC();
        setProperListPosition();
        adapter = new WCListAdapter(wordCampDBs, getActivity(), this);
        upWCLists.setAdapter(adapter);
    }

    private void setProperListPosition() {
        int pos = WordCampUtils.findFirstUpcomingFrag(wordCampDBs);
        if (pos > -1) {
            wordCampDBs = wordCampDBs.subList(pos, wordCampDBs.size());
        }
    }

    @Override
    public int addToMyWC(int wcid, int position) {
        int retId = communicator.addToMyWC(wcid);
        WordCampDB wordCampDB = adapter.getItem(position);
        listener.onNewMyWCAdded(wordCampDB);
        return retId;
    }

    @Override
    public void removeMyWC(int wcid, int position) {
        communicator.removeFromMyWCSingle(wcid);
        WordCampDB wordCampDB = adapter.getItem(position);
        listener.onMyWCRemoved(wordCampDB);
    }

    public void stopRefresh() {
        refreshLayout.setRefreshing(false);
    }

    public interface upcomingFragListener {
        void onNewMyWCAdded(WordCampDB wordCampDB);

        void onRefreshStart();

        void onMyWCRemoved(WordCampDB wordCampDB);
    }
}
