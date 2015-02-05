/*
 * Copyright 2014 Soichiro Kashima
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wordcamp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.wordcamp.adapters.CacheFragmentStatePagerAdapter;
import org.wordcamp.db.DBCommunicator;
import org.wordcamp.networking.WPAPIClient;
import org.wordcamp.objects.WordCampDB;
import org.wordcamp.objects.wordcamp.WordCamps;
import org.wordcamp.utils.ImageUtils;
import org.wordcamp.utils.WordCampUtils;
import org.wordcamp.widgets.SlidingTabLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BaseActivity extends ActionBarActivity {

    private View mToolbarView;
    private ViewPager mPager;
    private WCPagerAdapter mPagerAdapter;
    private int mSlop;
    private boolean mScrolled;
    public String lastscanned;

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
        mToolbarView = findViewById(R.id.toolbar);
        mPagerAdapter = new WCPagerAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mPagerAdapter);
        final int tabHeight = getResources().getDimensionPixelSize(R.dimen.tab_height);
        findViewById(R.id.pager_wrapper).setPadding(0, ImageUtils.getActionBarSize(this) + tabHeight, 0, 0);

        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        slidingTabLayout.setCustomTabView(R.layout.tab_view, android.R.id.text1);
        slidingTabLayout.setSelectedIndicatorColors(Color.parseColor("#0F85D1"));
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(mPager);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_refresh){

            fetchWCList();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_base_act,menu);
        return true;
    }


    @Override
    protected void onPause() {
        super.onPause();
        if(communicator!=null)
            communicator.close();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(communicator==null){
            communicator = new DBCommunicator(this);
        }
        else{
            communicator.start();
        }
    }

    private void fetchWCList() {

        final SharedPreferences pref = getSharedPreferences("wc", Context.MODE_PRIVATE);
        final String lastdate = pref.getString("date","0");
        WPAPIClient.getWordCampsList(lastdate, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                SharedPreferences.Editor editor = pref.edit();

                wordCampsList = new ArrayList<>();
                int count=0;
                Gson gson = new Gson();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        WordCamps wcs = gson.fromJson(response.getJSONObject(i).toString(), WordCamps.class);
                        if(i==0){
                            //Set last scan date of WC List by saving the later WC's modified GMT date
                            editor.putString("date",wcs.getModifiedGmt());
                            lastscanned = wcs.getModifiedGmt();
                            editor.commit();
                        }
                        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        Date d = sdf.parse(wcs.getModifiedGmt());
                        if(!lastdate.equals("0") && WordCampUtils.hasStartEndDate(wcs)) {
                            Date oldDate = sdf.parse(lastdate);
                            boolean chc = d.after(oldDate);
                            if (d.after(oldDate)){
                                WordCampDB wordCampDB = new WordCampDB(wcs,lastscanned);
                                wordCampsList.add(wordCampDB);
                            }
                            else
                                break; //ignore older WCs as it is already scanned
                        }
                        else if(lastdate.equals("0") && WordCampUtils.hasStartEndDate(wcs)){
                            WordCampDB wordCampDB = new WordCampDB(wcs,lastscanned);
                            wordCampsList.add(wordCampDB);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                communicator.addAllNewWC(wordCampsList);
                UpcomingWCFragment upcomingFragment = getUpcomingFragment();
                wordCampsList = communicator.getAllWc();
                upcomingFragment.updateList(wordCampsList);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(getApplicationContext(),"Some error took place",Toast.LENGTH_SHORT).show();
                Log.e("failure", responseString);
            }
        });
    }

    private UpcomingWCFragment getUpcomingFragment(){
        return (UpcomingWCFragment) mPagerAdapter.getItemAt(0);
    }


    private static class WCPagerAdapter extends CacheFragmentStatePagerAdapter {

        public WCPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        protected Fragment createItem(int position) {
            switch (position){
                case 0:
                    return UpcomingWCFragment.newInstance("Upcoming","");
                case 1:
                    return MyWCFragment.newInstance("MY","");
                case 2:
                    return UpcomingWCFragment.newInstance("Past","");
                default:
                    return UpcomingWCFragment.newInstance("","");
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "Upcoming";
                case 1:
                    return "My WordCamp";
                default:
                    return "Past";
            }
        }
    }
}
