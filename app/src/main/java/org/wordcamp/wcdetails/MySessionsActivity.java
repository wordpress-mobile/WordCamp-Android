package org.wordcamp.wcdetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import org.wordcamp.R;
import org.wordcamp.adapters.SessionsListAdapter;
import org.wordcamp.db.DBCommunicator;
import org.wordcamp.notifs.FavoriteSession;
import org.wordcamp.objects.SessionDB;

import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by aagam on 12/2/15.
 */
public class MySessionsActivity extends AppCompatActivity implements SessionsListAdapter.OnAddToMySessionListener {

    private Toolbar toolbar;
    private DBCommunicator communicator;
    private int wcid;
    private FavoriteSession favoriteSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sessions_list);
        wcid = getIntent().getIntExtra("wcid", 0);
        favoriteSession = new FavoriteSession(this);
        communicator = new DBCommunicator(this);
        communicator.start();
        initGUI();
    }

    private void initGUI() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.my_sessions_title));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final List<SessionDB> sessionDBList = communicator.getFavoriteSessions(wcid);
        final SessionsListAdapter sessionsListAdapter = new SessionsListAdapter(this, sessionDBList, this);
        StickyListHeadersListView mySessionList = (StickyListHeadersListView) findViewById(R.id.sessionList);
        mySessionList.setEmptyView(findViewById(R.id.empty_view));
        mySessionList.setAdapter(sessionsListAdapter);
        mySessionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detail = new Intent(getApplicationContext(), SessionDetailsActivity.class);
                detail.putExtra("session", sessionsListAdapter.getItem(position));
                startActivity(detail);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (communicator == null) {
            communicator = new DBCommunicator(this);
        } else {
            communicator.restart();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if (communicator != null)
            communicator.close();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (communicator != null)
            communicator.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void addMySession(SessionDB db) {
        favoriteSession.favoriteSession(db);
        communicator.addToMySession(db);
    }

    @Override
    public void removeMySession(SessionDB db) {
        favoriteSession.unFavoriteSession(db);
        communicator.removeFromMySession(db);
    }
}
