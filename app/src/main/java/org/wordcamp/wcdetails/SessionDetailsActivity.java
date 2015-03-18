package org.wordcamp.wcdetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.wordcamp.R;
import org.wordcamp.adapters.SessionDetailAdapter;
import org.wordcamp.db.DBCommunicator;
import org.wordcamp.notifs.FavoriteSession;
import org.wordcamp.objects.MiniSpeaker;
import org.wordcamp.objects.SessionDB;
import org.wordcamp.objects.SpeakerDB;
import org.wordcamp.objects.session.Session;
import org.wordcamp.utils.WordCampUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by aagam on 12/2/15.
 */
public class SessionDetailsActivity extends ActionBarActivity {

    public SessionDB sessionDB;
    public TextView title,info,speakers,time;
    public Toolbar toolbar;
    public Session session;
    public Gson gson;
    public ArrayList<MiniSpeaker> speakerList;
    public ListView speakersListView;
    public DBCommunicator communicator;
    public FavoriteSession fav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionDB = (SessionDB) getIntent().getSerializableExtra("session");
        setContentView(R.layout.activity_session_detail);
        fav = new FavoriteSession(this);
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

        speakersListView = (ListView)findViewById(R.id.session_list_speakers);
        speakersListView.addHeaderView(headerView,null,false);

        final HashMap<String,MiniSpeaker> names = communicator.getSpeakersForSession(sessionDB.getWc_id(),sessionDB.getPost_id());

        if(names!=null){
            speakerList = new ArrayList<>(names.values());
            speakersListView.setAdapter(new SessionDetailAdapter(getApplicationContext(),speakerList));
            speakersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    position--;

                    SpeakerDB speakerDB = communicator.getSpeaker(sessionDB.getWc_id(), speakerList.get(position).id);
                    Intent intent = new Intent(getApplicationContext(),SpeakerDetailsActivity.class);
                    intent.putExtra("speaker",speakerDB);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_session_detail,menu);
        if(sessionDB.isMySession){
            MenuItem attending = menu.findItem(R.id.action_favorite);
            attending.setIcon(R.drawable.ic_favorite_white_36dp);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_favorite:
                if(sessionDB.isMySession){
                    item.setIcon(R.drawable.ic_favorite_outline_white_36dp);
                    sessionDB.isMySession=false;
                    fav.unFavoriteSession(sessionDB);
                    communicator.removeFromMySession(sessionDB);
                }
                else{
                    item.setIcon(R.drawable.ic_favorite_white_36dp);
                    sessionDB.isMySession=true;
                    fav.favoriteSession(sessionDB);
                    communicator.addToMySession(sessionDB);
                }
                return true;
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
