package org.wordcamp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;

import org.wordcamp.adapters.UpcomingWCAdapter;
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
    public ObservableRecyclerView rView;
    private RecyclerView.LayoutManager mLayoutManager;
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
        // Inflate the layout for this fragment
        Activity parentActivity = getActivity();
        View v =  inflater.inflate(R.layout.fragment_upcoming_wc, container, false);
        rView = (ObservableRecyclerView)v.findViewById(R.id.scroll);
        rView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        rView.setLayoutManager(mLayoutManager);


        UpcomingWCAdapter adapter = new UpcomingWCAdapter(wordCampDBs,getActivity());
        rView.setAdapter(adapter);
        rView.scrollVerticallyToPosition(3);

        rView.addOnItemTouchListener(
                new RecyclerItemListener(getActivity(), new RecyclerItemListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // do whatever
                        Intent i = new Intent(getActivity(), WordCampDetailActivity.class);
                        i.putExtra("wc",wordCampDBs.get(position));
                        startActivity(i);

                    }
                })
        );

        if (parentActivity instanceof ObservableScrollViewCallbacks) {
            rView.setScrollViewCallbacks((ObservableScrollViewCallbacks) parentActivity);
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
        UpcomingWCAdapter adapter = new UpcomingWCAdapter(wordCampDBs,getActivity());
        rView.setAdapter(adapter);
    }
}
