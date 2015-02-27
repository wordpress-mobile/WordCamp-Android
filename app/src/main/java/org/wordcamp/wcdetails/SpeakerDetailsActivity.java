package org.wordcamp.wcdetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.wordcamp.R;
import org.wordcamp.adapters.SessionDetailSpeaker;
import org.wordcamp.db.DBCommunicator;
import org.wordcamp.objects.SessionDB;
import org.wordcamp.objects.SpeakerDB;
import org.wordcamp.objects.speakers.Speakers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by aagam on 26/2/15.
 */
public class SpeakerDetailsActivity extends ActionBarActivity {

    public Toolbar toolbar;

    public SpeakerDB speakerDB;
    public Speakers speakers;
    public Gson gson;
    public DBCommunicator communicator;
    public HashMap<String, Integer> titleSession;
    public TextView info;
    public ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        speakerDB = (SpeakerDB) getIntent().getSerializableExtra("speaker");
        setContentView(R.layout.activity_speaker_detail);

        gson = new Gson();
        communicator = new DBCommunicator(this);
        communicator.start();
//        speakers = gson.fromJson(speakerDB.getGson_object(),Speakers.class);

        titleSession = communicator.getSpeakerSession(speakerDB.getWc_id(),speakerDB.getSpeaker_id());

        initGUI();
    }

    private void initGUI() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(speakerDB.getName());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        View headerView = LayoutInflater.from(this).inflate(R.layout.item_header_speaker,null);
        info = (TextView)headerView.findViewById(R.id.speaker_detail);
        info.setText(Html.fromHtml(speakerDB.getInfo()));
        lv = (ListView)findViewById(R.id.session_list_speakers);
        lv.addHeaderView(headerView,null,false);
        final List<String> names = new ArrayList<>(titleSession.keySet());
        lv.setAdapter(new SessionDetailSpeaker(getApplicationContext(),names));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                //As we have 1 header view so the counter starts from 1
                i--;
                SessionDB sessionDB = communicator.getSession(speakerDB.getWc_id(),titleSession.get(names.get(i)));
                Intent intent = new Intent(getApplicationContext(),SessionDetailsActivity.class);
                intent.putExtra("session",sessionDB);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(communicator ==null){
            communicator = new DBCommunicator(this);
        } else{
            communicator.restart();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if(communicator!=null)
            communicator.close();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(communicator!=null)
            communicator.close();
    }
}
