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

import org.wordcamp.adapters.UpcomingWCListAdapter;
import org.wordcamp.db.DBCommunicator;
import org.wordcamp.objects.WordCampDB;
import org.wordcamp.objects.wordcamp.WordCamps;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class UpcomingWCFragment extends android.support.v4.app.Fragment implements UpcomingWCListAdapter.WCListener{
    public ListView upWCLists;
    public List<WordCampDB> wordCampDBs;
    public DBCommunicator communicator;
    public upcomingFragListener listener;
    public SwipeRefreshLayout refreshLayout;

    public static UpcomingWCFragment newInstance(String param1, String param2) {
        UpcomingWCFragment fragment = new UpcomingWCFragment();
        return fragment;
    }

    public UpcomingWCFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (BaseActivity) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement upcomingFragListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        communicator = ((BaseActivity)getActivity()).communicator;
        wordCampDBs = ((BaseActivity)getActivity()).wordCampsList;
        if(wordCampDBs!=null) {
           sortWC();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Activity parentActivity = getActivity();
        View v =  inflater.inflate(R.layout.fragment_upcoming_wc, container, false);
        upWCLists = (ListView)v.findViewById(R.id.scroll);
        refreshLayout = (SwipeRefreshLayout)v.findViewById(R.id.swipe_refresh_layout);
        refreshLayout.setColorSchemeColors(Color.parseColor("#3F51B5"),
                Color.parseColor("#FF4081"),Color.parseColor("#9C27B0"));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listener.onRefreshStart();
            }
        });
        UpcomingWCListAdapter adapter = new UpcomingWCListAdapter(wordCampDBs,parentActivity,this);
        upWCLists.setAdapter(adapter);

        upWCLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), WordCampDetailActivity.class);
                i.putExtra("wc", wordCampDBs.get(position));
                startActivity(i);
            }
        });

        if(wordCampDBs.size()==0){
            refreshLayout.setRefreshing(true);
            listener.onRefreshStart();
        }
        return v;
    }

    public void sortWC(){
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
        UpcomingWCListAdapter adapter = new UpcomingWCListAdapter(wordCampDBs,getActivity(),this);
        upWCLists.setAdapter(adapter);
    }

    @Override
    public int addToMyWC(int wcid,int position) {
        int retId =  communicator.addToMyWC(wcid);

        WordCampDB wordCampDB =wordCampDBs.get(position);
        listener.onNewMyWCAdded(wordCampDB);
        Gson g = new Gson();
        WordCamps wcs = g.fromJson(wordCampDB.getGson_object(),WordCamps.class);

        if(wcs.getFoo().getTwitter()!=null && wcs.getFoo().getTwitter().size()>0){
            ParsePush.subscribeInBackground(wcs.getFoo().getTwitter().get(0));
        }
        return retId;
    }

    public void stopRefresh(){
        refreshLayout.setRefreshing(false);
    }

    public interface upcomingFragListener{
        public void onNewMyWCAdded(WordCampDB wordCampDB);
        public void onRefreshStart();
    }
}
