package org.wordcamp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;
import org.wordcamp.adapters.CacheFragmentStatePagerAdapter;
import org.wordcamp.db.DBCommunicator;
import org.wordcamp.networking.WPAPIClient;
import org.wordcamp.objects.WordCampDB;
import org.wordcamp.objects.wordcamp.WordCampNew;
import org.wordcamp.utils.ImageUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BaseActivity extends AppCompatActivity implements UpcomingWCFragment.upcomingFragListener,
        SearchView.OnQueryTextListener, PastWCFragment.upcomingFragListener {

    private WCPagerAdapter mPagerAdapter;
    private String lastscanned;

    public DBCommunicator communicator;

    public List<WordCampDB> wordCampsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        setupUI();
    }

    private void setupUI() {
        communicator = new DBCommunicator(this);
        communicator.start();
        wordCampsList = communicator.getAllWc();

        ViewCompat.setElevation(findViewById(R.id.header), getResources().getDimension(R.dimen.toolbar_elevation));
        mPagerAdapter = new WCPagerAdapter(getSupportFragmentManager(), this);
        ViewPager mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mPagerAdapter);
        mPager.setOffscreenPageLimit(2);
        final int tabHeight = getResources().getDimensionPixelSize(R.dimen.tab_height);
        findViewById(R.id.pager_wrapper).setPadding(0, ImageUtils.getActionBarSize(this) + tabHeight, 0, 0);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setTabTextColors(getResources().getColor(R.color.tab_normal_text),
                getResources().getColor(R.color.tab_selected_text));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(mPager);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                getUpcomingFragment().startRefresh();
                break;
            case R.id.action_feedback:
                Intent feedbackIntent = new Intent(this, FeedbackActivity.class);
                startActivity(feedbackIntent);
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_base_act, menu);
        MenuItem searchItem = menu.findItem(R.id.search_wc);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint(Html.fromHtml("<font color = #ffffff>"
                + getString(R.string.search_wc_hint) + "</font>"));
        return true;
    }


    @Override
    protected void onPause() {
        super.onPause();
        WPAPIClient.cancelAllRequests(this);
        if (communicator != null)
            communicator.close();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (communicator == null) {
            communicator = new DBCommunicator(this);
            communicator.start();
            refreshAllFragmentsData();
        } else {
            communicator.start();
            refreshAllFragmentsData();
        }
    }

    private void fetchWCList() {

        final SharedPreferences pref = getSharedPreferences("wc", Context.MODE_PRIVATE);
        final String lastdate = pref.getString("date", "0");
        WPAPIClient.getWordCampsList(this, lastdate, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                SharedPreferences.Editor editor = pref.edit();

                wordCampsList = new ArrayList<>();
                Gson gson = new Gson();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        WordCampNew wcs = gson.fromJson(response.getJSONObject(i).toString(), WordCampNew.class);
                        if (i == 0) {
                            //Set last scan date of WC List by saving the later WC's modified GMT date
                            editor.putString("date", wcs.getModifiedGmt());
                            lastscanned = wcs.getModifiedGmt();
                            editor.apply();
                        }
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                        Date d = sdf.parse(wcs.getModifiedGmt());
                        /*if (!lastdate.equals("0") && WordCampUtils.hasStartEndDate(wcs)) {
                            Date oldDate = sdf.parse(lastdate);
                            boolean chc = d.after(oldDate);
                            if (d.after(oldDate)) {
                                WordCampDB wordCampDB = new WordCampDB(wcs, lastscanned);
                                wordCampsList.add(wordCampDB);
                            } else
                                break; //ignore older WCs as it is already scanned
                        } else if (lastdate.equals("0") && WordCampUtils.hasStartEndDate(wcs)) {
                            WordCampDB wordCampDB = new WordCampDB(wcs, lastscanned);
                            wordCampsList.add(wordCampDB);
                        }*/

                        WordCampDB wordCampDB = new WordCampDB(wcs, lastscanned);
                        if (!wordCampDB.getWc_start_date().isEmpty())
                            wordCampsList.add(wordCampDB);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                communicator.addAllNewWC(wordCampsList);

                refreshAllFragmentsData();
                stopRefresh();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                stopRefresh();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(getApplicationContext(), "Error: " + errorResponse.toString()
                        , Toast.LENGTH_SHORT).show();
                stopRefresh();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(getApplicationContext(), "Error: " + responseString, Toast.LENGTH_SHORT).show();
                stopRefresh();
            }
        });
    }

    private void stopRefresh() {
        getUpcomingFragment().stopRefresh();
        getMyWCFragment().stopRefresh();
        getPastFragment().stopRefresh();
    }

    private void refreshAllFragmentsData() {
        UpcomingWCFragment upcomingFragment = getUpcomingFragment();
        MyWCFragment myWCFragment = getMyWCFragment();
        PastWCFragment pastWCFragment = getPastFragment();
        wordCampsList = communicator.getAllWc();
        if (upcomingFragment != null)
            upcomingFragment.updateList(wordCampsList);

        if (myWCFragment != null)
            myWCFragment.updateList(wordCampsList);

        if (pastWCFragment != null) {
            pastWCFragment.updateList(wordCampsList);
        }
    }

    public void refreshUpcomingFrag() {
        UpcomingWCFragment upcomingFragment = getUpcomingFragment();
        wordCampsList = communicator.getAllWc();
        if (upcomingFragment != null)
            upcomingFragment.updateList(wordCampsList);
    }

    private UpcomingWCFragment getUpcomingFragment() {
        return (UpcomingWCFragment) mPagerAdapter.getItemAt(0);
    }

    public void refreshPastFrag() {
        PastWCFragment pastFragment = getPastFragment();
        wordCampsList = communicator.getAllWc();
        if (pastFragment != null)
            pastFragment.updateList(wordCampsList);
    }

    private PastWCFragment getPastFragment() {
        return (PastWCFragment) mPagerAdapter.getItemAt(2);
    }

    private MyWCFragment getMyWCFragment() {
        return (MyWCFragment) mPagerAdapter.getItemAt(1);
    }

    @Override
    public void onNewMyWCAdded(WordCampDB wordCampDB) {
        getMyWCFragment().addWC(wordCampDB);
    }

    @Override
    public void onRefreshStart() {
        fetchWCList();
    }

    @Override
    public void onMyWCRemoved(WordCampDB wordCampDB) {
        getMyWCFragment().removeSingleMYWC(wordCampDB);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        getUpcomingFragment().adapter.getFilter().filter(newText);
        getMyWCFragment().adapter.getFilter().filter(newText);
        getPastFragment().adapter.getFilter().filter(newText);
        return true;
    }

    private static class WCPagerAdapter extends CacheFragmentStatePagerAdapter {

        private Context mContext;

        public WCPagerAdapter(FragmentManager fm, Context ctx) {
            super(fm);
            mContext = ctx;
        }

        @Override
        protected Fragment createItem(int position) {
            switch (position) {
                case 0:
                    return UpcomingWCFragment.newInstance();
                case 1:
                    return MyWCFragment.newInstance();
                case 2:
                    return PastWCFragment.newInstance();
                default:
                    return UpcomingWCFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return mContext.getString(R.string.upcoming_wc_title);
                case 1:
                    return mContext.getString(R.string.my_wc_title);
                case 2:
                    return mContext.getString(R.string.past_wc_title);
                default:
                    return mContext.getString(R.string.past_wc_title);
            }
        }
    }
}
