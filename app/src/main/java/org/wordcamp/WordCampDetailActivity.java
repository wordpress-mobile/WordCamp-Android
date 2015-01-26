package org.wordcamp;

import android.content.res.TypedArray;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.wordcamp.utils.ImageUtils;

/**
 * Created by aagam on 12/1/15.
 */
public class WordCampDetailActivity extends ActionBarActivity {


    private Toolbar mToolbar;
    private View mHeader;
    private ImageView wcpic;
//    private WordCamps wc;
    public TextView loc,about;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private int mNavDrawerSize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        wc = (WordCamps)getIntent().getSerializableExtra("wc");
        setContentView(R.layout.activity_wordcamp_detail_my);
        initGUI();
        setCoverImageAspect();
        setNavDrawerSize();
    }

    private void setNavDrawerSize() {
        final TypedArray styledAttributes = getTheme().obtainStyledAttributes(
                new int[]{android.R.attr.actionBarSize});
        int navDrawerSize = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        if (android.os.Build.VERSION.SDK_INT >= 13) {
            display.getSize(size);
            int width = size.x;
            mNavDrawerSize = width - navDrawerSize;
        } else {
            mNavDrawerSize = display.getWidth() - navDrawerSize;
        }

        int mNavDrawerSizeDPMAx = ImageUtils.dpToPx(this,400);
        if(mNavDrawerSize > mNavDrawerSizeDPMAx)
            mNavDrawerSize = mNavDrawerSizeDPMAx;
        findViewById(R.id.fragment_drawer).getLayoutParams().width = mNavDrawerSize;
    }

    private void setCoverImageAspect() {
        //setting 16:9 aspect ration
        wcpic.getLayoutParams().height = ImageUtils.getAspectRatio(this);
    }

    private void initGUI() {


        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
//        mToolbar.setTitle(wc.getTitle());
        mToolbar.setSubtitle("March 20-22, 2015");
        wcpic = (ImageView)findViewById(R.id.wc_photo);

        /*Bitmap bp = BitmapFactory.decodeResource(getResources(), R.drawable.wclondon);
        Palette p = Palette.generate(bp);
        int c = p.getVibrantColor(Color.WHITE);

        mToolbar.setTitleTextColor(c);
        mToolbar.setSubtitleTextColor(c);*/

        loc = (TextView)findViewById(R.id.wc_location);
        about = (TextView)findViewById(R.id.wc_about);
//        loc.setText(wc.getFoo().getLocation().get(0));
//        about.setText(Html.fromHtml(wc.getContent()));


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mActionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        return super.onOptionsItemSelected(menuItem);
    }

}
