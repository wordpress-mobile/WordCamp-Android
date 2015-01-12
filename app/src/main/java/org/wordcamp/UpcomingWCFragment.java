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


public class UpcomingWCFragment extends android.support.v4.app.Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private ObservableRecyclerView rView;
    private RecyclerView.LayoutManager mLayoutManager;


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



        String[] arr = {"aasdasd"," adasdadasd","rewdadsad","adsad ada","aasdasd"," adasdadasd","rewdadsad","adsad ada",
                "aasdasd"," adasdadasd","rewdadsad","adsad ada","aasdasd"," adasdadasd","rewdadsad","adsad ada",
                "aasdasd"," adasdadasd","rewdadsad","adsad ada","aasdasd"," adasdadasd","rewdadsad","adsad ada"};

        UpcomingWCAdapter adapter = new UpcomingWCAdapter(arr);
        rView.setAdapter(adapter);

        rView.setTouchInterceptionViewGroup((ViewGroup) parentActivity.findViewById(R.id.container));
        rView.addOnItemTouchListener(
                new RecyclerItemListener(getActivity(), new RecyclerItemListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever

                        Intent i = new Intent(getActivity(),WordCampDetailActivity.class);
                        getActivity().startActivity(i);
                    }
                })
        );
        if (parentActivity instanceof ObservableScrollViewCallbacks) {
            rView.setScrollViewCallbacks((ObservableScrollViewCallbacks) parentActivity);
        }



        return v;
    }




    @Override
    public void onDetach() {
        super.onDetach();
    }

}
