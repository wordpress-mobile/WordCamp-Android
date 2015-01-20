package org.wordcamp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import org.wordcamp.widgets.ObservableScrollView;

/**
 * Created by aagam on 12/1/15.
 */
public class WordCampDetailActivity extends ActionBarActivity implements ObservableScrollView.Callbacks {


    private Toolbar mToolbar;
    private ObservableScrollView scrollView;
    private View mHeader;
    private int mLastDampedScroll;
    private Drawable mActionBarBackgroundDrawable;
    private ImageView wcpic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordcamp_detail_my);
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        mToolbar.setTitle("WordCamp London");
        mToolbar.setSubtitle("March 20-22, 2015");
        mToolbar.getBackground().setAlpha(0);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        wcpic = (ImageView)findViewById(R.id.wc_photo);
        setSupportActionBar(mToolbar);
        mHeader = findViewById(R.id.wc_photo_container);
        scrollView=(ObservableScrollView)findViewById(R.id.scroll_view);
        scrollView.addCallbacks(this);

        Bitmap bp = BitmapFactory.decodeResource(getResources(), R.drawable.wclondon);
        Palette p = Palette.generate(bp);
        int c = p.getVibrantColor(Color.WHITE);
        mToolbar.setTitleTextColor(c);
        mToolbar.setSubtitleTextColor(c);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onScrollChanged(int scaleX, int scaleY) {
        //Apply MaterialDesign scrolling here
    }



}
