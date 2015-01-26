package org.wordcamp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.wordcamp.adapters.WCDetailAdapter;
import org.wordcamp.utils.ImageUtils;
import org.wordcamp.widgets.SlidingTabLayout;

/**
 * Created by aagam on 26/1/15.
 */
public class NewWordCampDetail extends ActionBarActivity {

    public WCDetailAdapter adapter;
    public Toolbar toolbar;
    public ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wc_detail_new);
        initGUI();
    }

    private void initGUI() {
        ViewCompat.setElevation(findViewById(R.id.header), getResources().getDimension(R.dimen.toolbar_elevation));
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        adapter = new WCDetailAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(adapter);
        final int tabHeight = getResources().getDimensionPixelSize(R.dimen.tab_height);
        findViewById(R.id.pager_wrapper).setPadding(0, ImageUtils.getActionBarSize(this) + tabHeight, 0, 0);

        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        slidingTabLayout.setCustomTabView(R.layout.tab_view, android.R.id.text1);
        slidingTabLayout.setSelectedIndicatorColors(Color.parseColor("#0F85D1"));
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(mPager);

        toolbar.setTitle("WordCamp London");
        toolbar.setSubtitle("March 20-22, 2015");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_attending){

            item.setIcon(R.drawable.ic_star_rate_white_36dp);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_wc_detail,menu);
        return true;
    }
}
