package org.wordcamp.wcdetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.wordcamp.R;
import org.wordcamp.WordCampDetailActivity;
import org.wordcamp.adapters.SpeakersListAdapter;
import org.wordcamp.objects.SpeakerDB;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by aagam on 29/1/15.
 */
public class SpeakerFragment extends Fragment {

    public ListView lv;
    public SpeakersListAdapter adapter;
    public List<SpeakerDB> speakerDBList;
    public int wcid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wcid = ((WordCampDetailActivity)getActivity()).wcid;
        speakerDBList = ((WordCampDetailActivity)getActivity()).communicator.getAllSpeakers(wcid);
        sortList();
        adapter = new SpeakersListAdapter(getActivity(),speakerDBList);
    }

    private void sortList() {
        Collections.sort(speakerDBList, new Comparator<SpeakerDB>() {
            @Override
            public int compare(SpeakerDB lhs, SpeakerDB rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_wcdetails_speaker,container,false);
        lv = (ListView)v.findViewById(R.id.speaker_list);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent t = new Intent(getActivity(),SpeakerDetailsActivity.class);
                t.putExtra("speaker",speakerDBList.get(position));
                getActivity().startActivity(t);
            }
        });
        return v;
    }

    public void updateSpeakers(List<SpeakerDB> newSpeakerDBList){
        speakerDBList = newSpeakerDBList;
        sortList();
        adapter = new SpeakersListAdapter(getActivity(),speakerDBList);
        lv.setAdapter(adapter);
    }
}
