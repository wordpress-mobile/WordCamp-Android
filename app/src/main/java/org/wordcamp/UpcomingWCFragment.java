package org.wordcamp;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.wordcamp.adapters.UpcomingWCAdapter;


public class UpcomingWCFragment extends android.support.v4.app.Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private RecyclerView rView;
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
        View v =  inflater.inflate(R.layout.fragment_upcoming_wc, container, false);
        rView = (RecyclerView)v.findViewById(R.id.upcomingRecyclerView);
        rView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        rView.setLayoutManager(mLayoutManager);

        rView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                super.onScrolled(recyclerView, dx, dy);


            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        String[] arr = {"aasdasd"," adasdadasd","rewdadsad","adsad ada","aasdasd"," adasdadasd","rewdadsad","adsad ada",
                "aasdasd"," adasdadasd","rewdadsad","adsad ada","aasdasd"," adasdadasd","rewdadsad","adsad ada",
                "aasdasd"," adasdadasd","rewdadsad","adsad ada","aasdasd"," adasdadasd","rewdadsad","adsad ada"};

        UpcomingWCAdapter adapter = new UpcomingWCAdapter(arr);
        rView.setAdapter(adapter);

        TextView tv = (TextView)v.findViewById(R.id.up_title);
        tv.setText(mParam1);
        return v;
    }




    @Override
    public void onDetach() {
        super.onDetach();
    }


}
