package org.wordcamp.wcdetails;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import org.wordcamp.R;
import org.wordcamp.WordCampDetailActivity;
import org.wordcamp.adapters.SessionsListAdapter;
import org.wordcamp.db.DBCommunicator;
import org.wordcamp.notifs.FavoriteSession;
import org.wordcamp.objects.SessionDB;

import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by aagam on 31/1/15.
 */
public class SessionsFragment extends Fragment implements SessionsListAdapter.OnAddToMySessionListener {

    private GridLayoutManager mLayoutManager;
    private StickyListHeadersListView sessionList;
    private SessionsListAdapter sessionsListAdapter;
    private List<SessionDB> sessionDBList;
    public DBCommunicator communicator;
    public FavoriteSession favoriteSession;
    private SessionFragmentListener listener;
    private int wcid;
    private SwipeRefreshLayout refreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wcid = ((WordCampDetailActivity) getActivity()).wcid;
        communicator = ((WordCampDetailActivity) getActivity()).communicator;
        sessionDBList = communicator.getAllSession(wcid);
        favoriteSession = new FavoriteSession(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sessions_list, container, false);
        refreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_layout);
        refreshLayout.setColorSchemeColors(Color.parseColor("#3F51B5"),
                Color.parseColor("#FF4081"), Color.parseColor("#9C27B0"));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startRefreshSession();
            }
        });
        sessionList = (StickyListHeadersListView) v.findViewById(R.id.sessionList);
        sessionList.setEmptyView(v.findViewById(R.id.empty_view));
        if (sessionDBList != null) {
            if (sessionDBList.size() == 0) {
                startRefreshSession();
            }
            sessionsListAdapter = new SessionsListAdapter(getActivity(), sessionDBList, this);
        }

        sessionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detail = new Intent(getActivity(), SessionDetailsActivity.class);
                detail.putExtra("session", sessionDBList.get(position));
                getActivity().startActivity(detail);
            }
        });

        sessionList.getWrappedList().setHeaderDividersEnabled(true);
        sessionList.setAdapter(sessionsListAdapter);
        return v;
    }

    public void startRefreshSession() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                listener.startRefreshSessions();
            }
        });
    }

    public void stopRefreshSession() {
        refreshLayout.setRefreshing(false);
    }

    public void updateData() {
        sessionDBList = ((WordCampDetailActivity) getActivity()).communicator.getAllSession(wcid);
        sessionsListAdapter = new SessionsListAdapter(getActivity(), sessionDBList, this);
        sessionList.setAdapter(sessionsListAdapter);
    }

    @Override
    public void addMySession(SessionDB db) {
        favoriteSession.favoriteSession(db);
        communicator.addToMySession(db);
    }

    @Override
    public void removeMySession(SessionDB db) {
        favoriteSession.unFavoriteSession(db);
        communicator.removeFromMySession(db);
    }

    public interface SessionFragmentListener {
        void startRefreshSessions();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (WordCampDetailActivity) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement SessionFragmentListener");
        }
    }
}
