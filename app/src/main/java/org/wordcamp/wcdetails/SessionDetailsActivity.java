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
import org.wordcamp.adapters.SessionDetailSpeakerAdapter;
import org.wordcamp.db.DBCommunicator;
import org.wordcamp.objects.SessionDB;
import org.wordcamp.objects.SpeakerDB;
import org.wordcamp.objects.session.Session;
import org.wordcamp.utils.WordCampUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by aagam on 12/2/15.
 */
public class SessionDetailsActivity extends ActionBarActivity {

    public SessionDB sessionDB;
    public TextView title,info,speakers,time;
    public Toolbar toolbar;
    public Session session;
    public Gson gson;
    public List<String> speakerList;
    public ListView speakersListView;
    public DBCommunicator communicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionDB = (SessionDB) getIntent().getSerializableExtra("session");
        setContentView(R.layout.activity_session_detail);
        communicator = new DBCommunicator(this);
        communicator.start();
        initGUI();
    }

    private void initGUI() {
        gson = new Gson();
        session = gson.fromJson(sessionDB.getGson_object(),Session.class);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        View headerView = LayoutInflater.from(this).inflate(R.layout.item_header_session,null);

        title = (TextView)findViewById(R.id.wc_detail_title);
        time = (TextView)findViewById(R.id.wc_detail_date);
        info = (TextView)headerView.findViewById(R.id.wc_detail_abstract);
        title.setText(Html.fromHtml(session.getTitle()));
        info.setText(Html.fromHtml(session.getContent()));

        if(session.getTerms().getWcbTrack().size()==1){
            time.setText(WordCampUtils.formatProperTime(sessionDB.getTime())+" in "
                    +session.getTerms().getWcbTrack().get(0).getName());
        } else{
            time.setText(WordCampUtils.formatProperTime(sessionDB.getTime()));
        }

        final HashMap<String,Integer> names = communicator.getSpeakersForSession(sessionDB.getWc_id(),sessionDB.getPost_id());
        speakerList = new ArrayList<>(names.keySet());

        speakersListView = (ListView)findViewById(R.id.session_list_speakers);
        speakersListView.addHeaderView(headerView,null,false);


        speakersListView.setAdapter(new SessionDetailSpeakerAdapter(getApplicationContext(),speakerList));
        speakersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position--;

                SpeakerDB speakerDB = communicator.getSpeaker(sessionDB.getWc_id(), names.get(speakerList.get(position)));
                Intent intent = new Intent(getApplicationContext(),SpeakerDetailsActivity.class);
                intent.putExtra("speaker",speakerDB);
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
