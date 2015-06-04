package org.wordcamp.android.wcdetails;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.wordcamp.android.R;
import org.wordcamp.android.WordCampDetailActivity;
import org.wordcamp.android.adapters.SpeakersListAdapter;
import org.wordcamp.android.objects.SpeakerDB;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by aagam on 29/1/15.
 */
public class SpeakerFragment extends Fragment {

    private ListView lv;
    private SpeakersListAdapter adapter;
    private List<SpeakerDB> speakerDBList;
    private int wcid;
    private SwipeRefreshLayout refreshLayout;
    private SpeakerFragmentListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wcdetails_speaker, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        wcid = ((WordCampDetailActivity) getActivity()).wcid;
        speakerDBList = ((WordCampDetailActivity) getActivity()).communicator.getAllSpeakers(wcid);
        sortList();

        View v = getView();
        refreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_layout);
        refreshLayout.setColorSchemeColors(Color.parseColor("#3F51B5"),
                Color.parseColor("#FF4081"), Color.parseColor("#9C27B0"));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startRefreshSession();
            }
        });

        lv = (ListView) v.findViewById(R.id.speaker_list);
        lv.setEmptyView(v.findViewById(R.id.empty_view));
        adapter = new SpeakersListAdapter(getActivity(), speakerDBList);

        if (speakerDBList.size() == 0) {
            startRefreshSession();
        }

        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent t = new Intent(getActivity(), SpeakerDetailsActivity.class);
                t.putExtra("speaker", speakerDBList.get(position));
                getActivity().startActivity(t);
            }
        });
    }

    private void sortList() {
        Collections.sort(speakerDBList, new Comparator<SpeakerDB>() {
            @Override
            public int compare(SpeakerDB lhs, SpeakerDB rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }
        });
    }

    private void startRefreshSession() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                listener.startRefreshSpeakers();
            }
        });
    }

    public void stopRefreshSpeaker() {
        refreshLayout.setRefreshing(false);
    }

    public void updateSpeakers(List<SpeakerDB> newSpeakerDBList) {
        speakerDBList = newSpeakerDBList;
        sortList();
        adapter = new SpeakersListAdapter(getActivity(), speakerDBList);
        lv.setAdapter(adapter);
    }

    public interface SpeakerFragmentListener {
        void startRefreshSpeakers();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (WordCampDetailActivity) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement SpeakerFragmentListener");
        }
    }
}
