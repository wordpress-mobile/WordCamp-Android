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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;
import org.wordcamp.android.adapters.WCDetailAdapter;
import org.wordcamp.android.db.DBCommunicator;
import org.wordcamp.android.networking.WPAPIClient;
import org.wordcamp.android.objects.WordCampDB;
import org.wordcamp.android.objects.speaker.Session;
import org.wordcamp.android.objects.speaker.SpeakerNew;
import org.wordcamp.android.objects.speaker.Terms;
import org.wordcamp.android.objects.wordcamp.WordCampNew;
import org.wordcamp.android.utils.CustomGsonDeSerializer;
import org.wordcamp.android.utils.ImageUtils;
import org.wordcamp.android.utils.WordCampUtils;
import org.wordcamp.android.wcdetails.MySessionsActivity;
import org.wordcamp.android.wcdetails.SessionsFragment;
import org.wordcamp.android.wcdetails.SpeakerFragment;
import org.wordcamp.android.wcdetails.WordCampOverview;

/**
 * Created by aagam on 26/1/15.
 */
public class WordCampDetailActivity extends AppCompatActivity implements SessionsFragment.SessionFragmentListener,
        SpeakerFragment.SpeakerFragmentListener, WordCampOverview.WordCampOverviewListener {

    private WCDetailAdapter adapter;
    private Toolbar toolbar;
    private ViewPager mPager;
    public WordCampDB wcdb;
    public int wcid;
    public DBCommunicator communicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        adapter = new WCDetailAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(adapter);
        mPager.setOffscreenPageLimit(2);
        final int tabHeight = getResources().getDimensionPixelSize(R.dimen.tab_height);
        findViewById(R.id.pager_wrapper).setPadding(0, ImageUtils.getActionBarSize(this) + tabHeight, 0, 0);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setTabTextColors(getResources().getColor(R.color.tab_normal_text),
                getResources().getColor(R.color.tab_selected_text));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(mPager);

        setToolbar();
    }

    private void setToolbar() {
        TextView title = (TextView) toolbar.findViewById(R.id.title);
        TextView subTitle = (TextView) toolbar.findViewById(R.id.sub_title);
        title.setText(wcdb.getWc_title());
        title.setSelected(true);
        subTitle.setText(WordCampUtils.getProperDate(wcdb));
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_attending:
                if (!wcdb.isMyWC) {
                    int recv = communicator.addToMyWC(wcid);
                    item.setIcon(R.drawable.ic_bookmark_white_36dp);
                    wcdb.isMyWC = true;
                } else {
                    communicator.removeFromMyWCSingle(wcid);
                    item.setIcon(R.drawable.ic_bookmark_outline_white_36dp);
                    wcdb.isMyWC = false;
                }
                break;
            case R.id.action_refresh:
                updateWordCampData();
                break;
            case R.id.item_menu_feedback:
                String url = communicator.getFeedbackUrl(wcid);
                if (url == null) {
                    Toast.makeText(this, getString(R.string.feedback_url_not_available_toast), Toast.LENGTH_SHORT).show();
                } else {
                    startWebIntent(url);
                }
                break;
            case android.R.id.home:
                finish();
                break;
            case R.id.item_menu_website:
                startWebIntent(wcdb.getUrl());
                break;
            case R.id.item_menu_my_sessions:
                startMySessionActivity();
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
        String webURL = wcdb.getUrl();

        fetchSpeakersAPI(webURL);
        getSessionsFragment().startRefreshSession();
//        fetchSessionsAPI(webURL);
//        fetchOverviewAPI();
    }

    private void fetchOverviewAPI() {
        WPAPIClient.getSingleWC(this, wcid, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Gson g = new Gson();
                WordCampNew wc = g.fromJson(response.toString(), WordCampNew.class);
                WordCampDB wordCampDB = new WordCampDB(wc, "");
                communicator.updateWC(wordCampDB);

                WordCampOverview overview = getOverViewFragment();
                if (overview != null) {
                    overview.updateData(wordCampDB);
                    Toast.makeText(getApplicationContext(), getString(R.string.update_overview_toast), Toast.LENGTH_SHORT).show();

                }
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                WordCampOverview overview = getOverViewFragment();
                if (overview != null) {
                    overview.stopRefreshOverview();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                WordCampOverview overview = getOverViewFragment();
                if (overview != null) {
                    overview.stopRefreshOverview();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                WordCampOverview overview = getOverViewFragment();
                if (overview != null) {
                    overview.stopRefreshOverview();
                }
            }
        });
    }

    private void fetchSessionsAPI(String webURL) {
        WPAPIClient.getWordCampSchedule(this, webURL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(Terms.class,
                        new CustomGsonDeSerializer()).create();

                for (int i = 0; i < response.length(); i++) {
                    try {
                        Session session = gson.fromJson(response.getJSONObject(i).toString(), Session.class);
                        if (communicator != null) {
                            communicator.addSession(session, wcid);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                Toast.makeText(getApplicationContext(), getString(R.string.update_sessions_toast), Toast.LENGTH_SHORT).show();
                stopRefreshSession();
                if (response.length() > 0) {
                    updateSessionContent();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                stopRefreshSession();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                stopRefreshSession();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                stopRefreshSession();
            }
        });
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
        WPAPIClient.getWordCampSpeakers(this, webURL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                addUpdateSpeakers(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                stopRefreshSpeaker();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                stopRefreshSpeaker();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                stopRefreshSpeaker();
            }
        });
    }

    private void addUpdateSpeakers(JSONArray array) {
        Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(Terms.class,
                new CustomGsonDeSerializer()).create();
        for (int i = 0; i < array.length(); i++) {
            try {
                SpeakerNew skn = gson.fromJson(array.getJSONObject(i).toString(), SpeakerNew.class);
                communicator.addSpeaker(skn, wcid);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (array.length() > 0) {
            SpeakerFragment fragment = getSpeakerFragment();
            if (fragment != null) {
                fragment.updateSpeakers(communicator.getAllSpeakers(wcid));
            }
        }
        Toast.makeText(getApplicationContext(), getString(R.string.update_speakers_toast), Toast.LENGTH_SHORT).show();
        stopRefreshSpeaker();
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
        if (wcdb.isMyWC) {
            MenuItem attending = menu.findItem(R.id.action_attending);
            attending.setIcon(R.drawable.ic_bookmark_white_36dp);
        }
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
        //Even we are refreshing sessions,
        // we will fetch Speakers as we get Sessions from there

        String webURL = wcdb.getUrl();
        fetchSessionsAPI(webURL);
        getSessionsFragment().startRefreshingBar();
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
}
