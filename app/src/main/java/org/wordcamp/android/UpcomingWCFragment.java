package org.wordcamp.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.wordcamp.android.adapters.WCListAdapter;
import org.wordcamp.android.db.DBCommunicator;
import org.wordcamp.android.objects.WordCampDB;
import org.wordcamp.android.utils.WordCampUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class UpcomingWCFragment extends android.support.v4.app.Fragment implements WCListAdapter.WCListener,
        WCListAdapter.OnWCSelectedListener {
    private RecyclerView upWCLists;
    private List<WordCampDB> wordCampDBs;
    private DBCommunicator communicator;
    private upcomingFragListener listener;
    private SwipeRefreshLayout refreshLayout;
    public WCListAdapter adapter;
    private TextView emptyView;

    public static UpcomingWCFragment newInstance() {
        return new UpcomingWCFragment();
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
            sortWC();
        }

        View v = getView();
        upWCLists = (RecyclerView) v.findViewById(R.id.scroll);
        upWCLists.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        upWCLists.setLayoutManager(mLayoutManager);

        emptyView = (TextView) v.findViewById(R.id.empty_view);
        emptyView.setText(getActivity().getString(R.string.empty_wordcamps));

        refreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_layout);
        refreshLayout.setColorSchemeResources(R.color.swipe_refresh_color1,
                R.color.swipe_refresh_color2, R.color.swipe_refresh_color3);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listener.onRefreshStart();
            }
        });
        setProperListPosition();
        adapter = new WCListAdapter(wordCampDBs, getActivity(), this);
        adapter.setOnWCSelectedListener(this);
        updateEmptyView();
        upWCLists.setAdapter(adapter);

        if (wordCampDBs.size() == 0) {
            startRefresh();
        }
    }

    private void updateEmptyView() {
        if (adapter.getItemCount() < 1) {
            emptyView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.GONE);
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

    public void updateList(List<WordCampDB> wordCampsList) {
        wordCampDBs = wordCampsList;
        sortWC();
        setProperListPosition();
        adapter = new WCListAdapter(wordCampDBs, getActivity(), this);
        adapter.setOnWCSelectedListener(this);
        updateEmptyView();
        upWCLists.swapAdapter(adapter, false);
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

    @Override
    public void onWCSelected(WordCampDB wc) {
        Intent i = new Intent(getActivity(), WordCampDetailActivity.class);
        i.putExtra("wc", wc);
        startActivity(i);
    }

    public interface upcomingFragListener {
        void onNewMyWCAdded(WordCampDB wordCampDB);

        void onRefreshStart();

        void onMyWCRemoved(WordCampDB wordCampDB);
    }
}
