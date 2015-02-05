package org.wordcamp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.wordcamp.adapters.UpcomingWCListAdapter;
import org.wordcamp.objects.WordCampDB;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class UpcomingWCFragment extends android.support.v4.app.Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    public ListView upWCLists;
    public List<WordCampDB> wordCampDBs;

    public static UpcomingWCFragment newInstance(String param1, String param2) {
        UpcomingWCFragment fragment = new UpcomingWCFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public UpcomingWCFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

        UpcomingWCListAdapter adapter = new UpcomingWCListAdapter(wordCampDBs,parentActivity);
        upWCLists.setAdapter(adapter);

        upWCLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), WordCampDetailActivity.class);
                i.putExtra("wc", wordCampDBs.get(position));
                startActivity(i);
            }
        });

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
        UpcomingWCListAdapter adapter = new UpcomingWCListAdapter(wordCampDBs,getActivity());
        upWCLists.setAdapter(adapter);
    }
}
