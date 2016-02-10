package org.wordcamp.android.wcdetails;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import org.wordcamp.android.R;
import org.wordcamp.android.WordCampDetailActivity;
import org.wordcamp.android.adapters.SessionsListAdapter;
import org.wordcamp.android.db.DBCommunicator;
import org.wordcamp.android.notifs.FavoriteSession;
import org.wordcamp.android.objects.SessionDB;

import java.util.HashMap;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by aagam on 31/1/15.
 */
public class SessionsFragment extends Fragment implements SessionsListAdapter.OnAddToMySessionListener {

    private StickyListHeadersListView sessionList;
    private SessionsListAdapter sessionsListAdapter;
    private List<SessionDB> sessionDBList;
    private HashMap<Integer, String> speakersForSession;
    private DBCommunicator communicator;
    private FavoriteSession favoriteSession;
    private SessionFragmentListener listener;
    private int wcid;
    private SwipeRefreshLayout refreshLayout;
    private Parcelable sessionListSavedState;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sessions_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        wcid = ((WordCampDetailActivity) getActivity()).wcid;
        communicator = ((WordCampDetailActivity) getActivity()).communicator;
        sessionDBList = communicator.getAllSession(wcid);
        speakersForSession = communicator.getSpeakersforAllSessions(wcid);
        favoriteSession = new FavoriteSession(getActivity());

        View v = getView();
        refreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_layout);
        refreshLayout.setColorSchemeResources(R.color.swipe_refresh_color1,
                R.color.swipe_refresh_color2, R.color.swipe_refresh_color3);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startRefreshSession();
            }
        });

        sessionList = (StickyListHeadersListView) v.findViewById(R.id.sessionList);
        sessionList.setEmptyView(v.findViewById(R.id.empty_view));
        if (sessionDBList.size() == 0) {
            startRefreshSession();
        }
        sessionsListAdapter = new SessionsListAdapter(getActivity(), sessionDBList,
                speakersForSession, this);

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

        if (savedInstanceState != null) {
            sessionList.onRestoreInstanceState(savedInstanceState.getParcelable("sessionListState"));
        }

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

    public void startRefreshingBar() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
            }
        });
    }

    public void stopRefreshSession() {
        refreshLayout.setRefreshing(false);
    }

    public void updateData() {
        sessionDBList = ((WordCampDetailActivity) getActivity()).communicator.getAllSession(wcid);
        speakersForSession = ((WordCampDetailActivity) getActivity()).communicator.getSpeakersforAllSessions(wcid);
        sessionsListAdapter = new SessionsListAdapter(getActivity(), sessionDBList, speakersForSession, this);
        sessionList.setAdapter(sessionsListAdapter);
        stopRefreshSession();
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


    @Override
    public void onPause() {
        sessionListSavedState = sessionList.onSaveInstanceState();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sessionList != null && sessionListSavedState != null) {
            sessionList.onRestoreInstanceState(sessionListSavedState);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("sessionListState", sessionList.onSaveInstanceState());
    }
}
