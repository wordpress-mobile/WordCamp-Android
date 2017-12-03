package org.wordcamp.android;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.wordcamp.android.adapters.WCDetailAdapter;
import org.wordcamp.android.db.DBCommunicator;
import org.wordcamp.android.networking.ResponseListener;
import org.wordcamp.android.networking.WPAPIClient;
import org.wordcamp.android.objects.WordCampDB;
import org.wordcamp.android.objects.wordcamp.Session;
import org.wordcamp.android.objects.wordcamp.Speaker;
import org.wordcamp.android.objects.wordcamp.WordCamp;
import org.wordcamp.android.wcdetails.MySessionsActivity;
import org.wordcamp.android.wcdetails.SessionsFragment;
import org.wordcamp.android.wcdetails.SpeakerFragment;
import org.wordcamp.android.wcdetails.WordCampOverview;

/**
 * Created by aagam on 26/1/15.
 */
public class WordCampDetailActivity extends AppCompatActivity implements SessionsFragment.SessionFragmentListener,
        SpeakerFragment.SpeakerFragmentListener, WordCampOverview.WordCampOverviewListener, Response.ErrorListener, ResponseListener {

    private WCDetailAdapter adapter;
    private Toolbar toolbar;
    public WordCampDB wcdb;
    public int wcid;
    public DBCommunicator communicator;
    private Response.ErrorListener scheduleErrorListener, wcErrorListener;
    private ResponseListener scheduleResponseListener, wcResponseListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initListener();
        wcdb = (WordCampDB) getIntent().getSerializableExtra("wc");
        wcid = wcdb.getWc_id();
        setContentView(R.layout.activity_wordcamp_detail);
        communicator = new DBCommunicator(this);
        communicator.start();
        initGUI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        communicator.close();
    }

    private void initGUI() {
        ViewCompat.setElevation(findViewById(R.id.header), getResources().getDimension(R.dimen.toolbar_elevation));
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        adapter = new WCDetailAdapter(getSupportFragmentManager(), this);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(2);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setTabTextColors(getResources().getColor(R.color.tab_normal_text),
                getResources().getColor(R.color.tab_selected_text));
        tabLayout.setupWithViewPager(pager);

        setToolbar();
    }

    private void setToolbar() {
        toolbar.setTitle(wcdb.getFormattedWCTitle());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_refresh:
                updateWordCampData();
                break;
            case R.id.item_menu_feedback:
                String url = communicator.getFeedbackUrl(wcid);
                if (url == null) {
                    Toast.makeText(this, getString(R.string.feedback_url_not_available_toast),
                            Toast.LENGTH_LONG).show();
                } else {
                    startWebIntent(url);
                }
                break;
            case android.R.id.home:
                finish();
                break;
            case R.id.item_menu_website:
                break;
        }

        return true;
    }

    private void startMySessionActivity() {
        Intent mySessionIntent = new Intent(this, MySessionsActivity.class);
        mySessionIntent.putExtra("wcid", wcid);
        startActivity(mySessionIntent);
    }

    private void startWebIntent(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(browserIntent);
    }

    private void updateWordCampData() {
        getSessionsFragment().startRefreshSession();
        getSpeakerFragment().startRefreshSpeakers();
        getOverViewFragment().startRefreshOverview();
    }

    private void fetchOverviewAPI() {
        WPAPIClient.getSingleWCVolley(wcid, this, wcErrorListener, wcResponseListener);
    }

    private void fetchSessionsAPI(String webURL) {
        WPAPIClient.getWordCampScheduleVolley(webURL, this, scheduleErrorListener, scheduleResponseListener);
    }

    private void stopRefreshSession() {
        SessionsFragment fragment = getSessionsFragment();
        if (fragment != null) {
            fragment.stopRefreshSession();
        }
    }

    private void stopRefreshSpeaker() {
        SpeakerFragment fragment = getSpeakerFragment();
        if (fragment != null) {
            fragment.stopRefreshSpeaker();
        }

        updateSessionContent();
    }

    private void updateSessionContent() {
        SessionsFragment fragment = getSessionsFragment();
        if (fragment != null) {
            fragment.updateData();
        }
    }

    private void fetchSpeakersAPI(String webURL) {
        WPAPIClient.getWordCampSpeakersVolley(webURL, this, this, this);
    }

    private SpeakerFragment getSpeakerFragment() {
        return (SpeakerFragment) adapter.getItemAt(2);
    }

    private SessionsFragment getSessionsFragment() {
        return (SessionsFragment) adapter.getItemAt(1);
    }

    private WordCampOverview getOverViewFragment() {
        return (WordCampOverview) adapter.getItemAt(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_wc_detail, menu);
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (communicator == null) {
            communicator = new DBCommunicator(this);
        } else {
            communicator.restart();
            updateSessionContent();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        WPAPIClient.cancelAllRequests(this);
        if (communicator != null)
            communicator.close();
    }

    @Override
    protected void onStop() {
        super.onStop();
        WPAPIClient.cancelAllRequests(this);
    }

    @Override
    public void startRefreshSessions() {
        String webURL = wcdb.getUrl();
        fetchSessionsAPI(webURL);
    }

    @Override
    public void startRefreshSpeakers() {
        String webURL = wcdb.getUrl();
        fetchSpeakersAPI(webURL);
    }

    @Override
    public void refreshOverview() {
        fetchOverviewAPI();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (error instanceof NoConnectionError) {
            Toast.makeText(this, getString(R.string.no_network_toast), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error : " + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
        stopRefreshSpeaker();
    }

    @Override
    public void onResponseReceived(Object o) {
        Speaker[] speakerNews = (Speaker[]) o;

        for (int i = 0; i < speakerNews.length; i++) {
            try {
                communicator.addSpeaker(speakerNews[i], wcid);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (speakerNews.length > 0) {
            SpeakerFragment fragment = getSpeakerFragment();
            if (fragment != null) {
                fragment.updateSpeakers(communicator.getAllSpeakers(wcid));
            }
            updateSessionContent();
        }
        Toast.makeText(getApplicationContext(), getString(R.string.update_speakers_toast), Toast.LENGTH_SHORT).show();
        stopRefreshSpeaker();
    }

    private void initListener() {
        scheduleErrorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(), getString(R.string.no_network_toast), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Error : " + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
                stopRefreshSession();
            }
        };

        wcErrorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(), getString(R.string.no_network_toast), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Error : " + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
                WordCampOverview overview = getOverViewFragment();
                if (overview != null) {
                    overview.stopRefreshOverview();
                }
            }
        };

        scheduleResponseListener = new ResponseListener() {
            @Override
            public void onResponseReceived(Object o) {
                Session[] sessions = (Session[]) o;
                for (Session session : sessions) {
                    communicator.addSession(session, wcid);
                }
                Toast.makeText(getApplicationContext(), getString(R.string.update_sessions_toast), Toast.LENGTH_SHORT).show();
                stopRefreshSession();
                if (sessions.length > 0) {
                    updateSessionContent();
                }
            }
        };

        wcResponseListener = new ResponseListener() {
            @Override
            public void onResponseReceived(Object o) {
                WordCamp wordCamp = (WordCamp) o;
                WordCampDB wordCampDB = new WordCampDB(wordCamp, "");
                communicator.updateWC(wordCampDB);
                WordCampOverview overview = getOverViewFragment();
                if (overview != null) {
                    overview.updateData(wordCampDB);
                    Toast.makeText(getApplicationContext(), getString(R.string.update_overview_toast), Toast.LENGTH_SHORT).show();
                }
            }
        };
    }
}
