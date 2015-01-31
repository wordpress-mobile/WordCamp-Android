package org.wordcamp.wcdetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.wordcamp.R;
import org.wordcamp.WordCampDetailActivity;
import org.wordcamp.adapters.SessionsListAdapter;
import org.wordcamp.objects.SessionDB;

import java.util.List;

/**
 * Created by aagam on 31/1/15.
 */
public class SessionsFragment extends Fragment {

    private GridLayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private SessionsListAdapter sessionsListAdapter;
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
        View v = inflater.inflate(R.layout.fargment_sessions_list,container,false);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView = (RecyclerView)v.findViewById(R.id.recyclerView);

      /*  StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);*/
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
               if(sessionDBList.get(position).getTitle().equals("Keynote")
                       ||sessionDBList.get(position).getTitle().equals("Lunch")){
                   return 2;
               }
                return 1;
            }
        });




        mRecyclerView.setLayoutManager(mLayoutManager);
        if(sessionDBList!=null)
            sessionsListAdapter = new SessionsListAdapter(sessionDBList);
        mRecyclerView.setAdapter(sessionsListAdapter);
        return v;
    }

    public void updateData(){
        int position = ((GridLayoutManager) mRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        sessionDBList = ((WordCampDetailActivity)getActivity()).communicator.getAllSession(wcid);
        sessionsListAdapter = new SessionsListAdapter(sessionDBList);
        mRecyclerView.setAdapter(sessionsListAdapter);
        mRecyclerView.scrollToPosition(position);
    }
}
