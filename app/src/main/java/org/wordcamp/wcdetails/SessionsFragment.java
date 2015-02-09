package org.wordcamp.wcdetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.wordcamp.R;
import org.wordcamp.WordCampDetailActivity;
import org.wordcamp.adapters.NewSessListAdapter;
import org.wordcamp.objects.SessionDB;

import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by aagam on 31/1/15.
 */
public class SessionsFragment extends Fragment {

    private GridLayoutManager mLayoutManager;
    private StickyListHeadersListView sessionList;
    private NewSessListAdapter sessionsListAdapter;
    private List<SessionDB> sessionDBList;
    private int wcid;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wcid = ((WordCampDetailActivity)getActivity()).wcid;
        sessionDBList = ((WordCampDetailActivity)getActivity()).communicator.getAllSession(wcid);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fargment_sessions_list_new,container,false);
        sessionList = (StickyListHeadersListView)v.findViewById(R.id.sessionList);
        if(sessionDBList!=null)
            sessionsListAdapter = new NewSessListAdapter(getActivity(),sessionDBList);


        sessionList.setAdapter(sessionsListAdapter);
        return v;
    }

    public void updateData(){
        sessionDBList = ((WordCampDetailActivity)getActivity()).communicator.getAllSession(wcid);
        sessionsListAdapter = new NewSessListAdapter(getActivity(),sessionDBList);
        sessionList.setAdapter(sessionsListAdapter);
    }
}
