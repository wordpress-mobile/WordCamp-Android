package org.wordcamp.android;

import android.content.Context;
import android.content.Intent;
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

import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.wordcamp.android.adapters.CacheFragmentStatePagerAdapter;
import org.wordcamp.android.db.DBCommunicator;
import org.wordcamp.android.networking.ResponseListener;
import org.wordcamp.android.networking.WPAPIClient;
import org.wordcamp.android.objects.WordCampDB;
import org.wordcamp.android.objects.wordcamp.WordCamp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BaseActivity extends AppCompatActivity implements UpcomingWCFragment.upcomingFragListener,
        SearchView.OnQueryTextListener, Response.ErrorListener, ResponseListener {

    private WCPagerAdapter mPagerAdapter;

    public DBCommunicator communicator;

    public List<WordCampDB> wordCampsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        setupUI();
        getSupportActionBar().setTitle(R.string.main_action_bar_title);
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

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setTabTextColors(getResources().getColor(R.color.tab_normal_text),
                getResources().getColor(R.color.tab_selected_text));
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
        searchView.setQueryHint(Html.fromHtml("<font color = #d3ffffff>"
                + getString(R.string.search_wc_hint) + "</font>"));
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        WPAPIClient.cancelAllRequests(this);
        if (communicator != null) {
            communicator.close();
        }
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
        }
    }

    private void fetchWCList() {
        WPAPIClient.getAllWCs(this, this, this);
    }

    private void stopRefresh() {
        getUpcomingFragment().stopRefresh();
        getMyWCFragment().stopRefresh();
    }

    private void refreshAllFragmentsData() {
        UpcomingWCFragment upcomingFragment = getUpcomingFragment();
        MyWCFragment myWCFragment = getMyWCFragment();
        wordCampsList = communicator.getAllWc();
        if (upcomingFragment != null) {
            upcomingFragment.updateList(wordCampsList);
        }
        if (myWCFragment != null) {
            myWCFragment.updateList(wordCampsList);
        }
    }

    public void refreshUpcomingFrag() {
        UpcomingWCFragment upcomingFragment = getUpcomingFragment();
        wordCampsList = communicator.getAllWc();
        if (upcomingFragment != null) {
            upcomingFragment.updateList(wordCampsList);
        }
    }

    private UpcomingWCFragment getUpcomingFragment() {
        return (UpcomingWCFragment) mPagerAdapter.getItemAt(0);
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
        return true;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (error instanceof NoConnectionError) {
            Toast.makeText(this, getString(R.string.no_network_toast), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error : " + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
        stopRefresh();
    }

    @Override
    public void onResponseReceived(Object o) {
        try {
            wordCampsList = new ArrayList<>();
            WordCamp[] wordCamps = (WordCamp[]) o;
            for (WordCamp wordCamp : wordCamps) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                Date d = sdf.parse(wordCamp.getModifiedGmt());
                WordCampDB wordCampDB = new WordCampDB(wordCamp, d.toString());
                if (!wordCampDB.getWc_start_date().isEmpty()) {
                    wordCampsList.add(wordCampDB);
                }
            }

            communicator.addAllNewWC(wordCampsList);
            refreshAllFragmentsData();
            stopRefresh();
        } catch (Exception e) {
            e.printStackTrace();
            stopRefresh();
        }

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
                case 1:
                    return MyWCFragment.newInstance();
                case 0:
                default:
                    return UpcomingWCFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 1:
                    return mContext.getString(R.string.my_wc_title);
                case 0:
                default:
                    return mContext.getString(R.string.upcoming_wc_title);
            }
        }
    }
}
