package org.wordcamp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import org.wordcamp.adapters.SpeakersListAdapter;
import org.wordcamp.objects.SpeakerDB;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aagam on 30/1/15.
 */
public class Test extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_wcdetails_speaker);

        ListView lv = (ListView)findViewById(R.id.speaker_list);
        List<SpeakerDB> speakerDBList = new ArrayList<>();

        SpeakersListAdapter adapter = new SpeakersListAdapter(this,speakerDBList);
        lv.setAdapter(adapter);
    }
}
