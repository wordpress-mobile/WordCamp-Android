package org.wordcamp.wcdetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
public class SessionsFragment extends Fragment implements SessionsListAdapter.OnAddToMySessionListener{

    private GridLayoutManager mLayoutManager;
    private StickyListHeadersListView sessionList;
    private SessionsListAdapter sessionsListAdapter;
    private List<SessionDB> sessionDBList;
    public DBCommunicator communicator;
    public FavoriteSession favoriteSession;
    private int wcid;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wcid = ((WordCampDetailActivity)getActivity()).wcid;
        communicator = ((WordCampDetailActivity)getActivity()).communicator;
        sessionDBList = communicator.getAllSession(wcid);
        favoriteSession = new FavoriteSession(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fargment_sessions_list_new,container,false);
        sessionList = (StickyListHeadersListView)v.findViewById(R.id.sessionList);
        if(sessionDBList!=null)
            sessionsListAdapter = new SessionsListAdapter(getActivity(),sessionDBList,this);

        sessionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detail = new Intent(getActivity(),SessionDetailsActivity.class);
                detail.putExtra("session",sessionDBList.get(position));
                getActivity().startActivity(detail);
            }
        });

        sessionList.getWrappedList().setHeaderDividersEnabled(true);
        sessionList.setAdapter(sessionsListAdapter);
        return v;
    }

    public void updateData(){
        sessionDBList = ((WordCampDetailActivity)getActivity()).communicator.getAllSession(wcid);
        sessionsListAdapter = new SessionsListAdapter(getActivity(),sessionDBList,this);
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
}
