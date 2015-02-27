package org.wordcamp.wcdetails;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.wordcamp.R;
import org.wordcamp.adapters.SessionDetailSpeaker;
import org.wordcamp.objects.SessionDB;
import org.wordcamp.objects.session.Session;
import org.wordcamp.utils.WordCampUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by aagam on 12/2/15.
 */
public class SessionDetailsActivity extends ActionBarActivity {

    public SessionDB sessionDB;
    public TextView title,info,speakers,time;
    public Toolbar toolbar;
    public Session session;
    public Gson gson;
    public List<String> speakerList,location;
    public ListView speakersListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionDB = (SessionDB) getIntent().getSerializableExtra("session");
        setContentView(R.layout.activity_session_detail);
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

        speakerList = session.getFoo().getWcbSessionSpeakers();

        if(session.getTerms().getWcbTrack().size()==1){
            time.setText(WordCampUtils.formatProperTime(sessionDB.getTime())+" in "
                    +session.getTerms().getWcbTrack().get(0).getName());
        } else{
            time.setText(WordCampUtils.formatProperTime(sessionDB.getTime()));
        }

        speakerList =  Arrays.asList(speakerList.get(0).split("\\s*,\\s*"));
        Set<String> s = new LinkedHashSet<>(speakerList);
        speakerList = new ArrayList<>(s);

        speakersListView = (ListView)findViewById(R.id.session_list_speakers);
        speakersListView.addHeaderView(headerView,null,false);


        speakersListView.setAdapter(new SessionDetailSpeaker(getApplicationContext(),speakerList));
        /*if(!speakerList.get(speakerList.size()-1).equals(""))
            sb.append(speakerList.get(speakerList.size()-1));
        else
            sb.deleteCharAt();*/
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
}
