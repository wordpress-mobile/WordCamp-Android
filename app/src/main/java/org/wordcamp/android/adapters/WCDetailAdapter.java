package org.wordcamp.android.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import org.wordcamp.android.R;
import org.wordcamp.android.wcdetails.SessionsFragment;
import org.wordcamp.android.wcdetails.SpeakerFragment;
import org.wordcamp.android.wcdetails.WordCampOverview;

/**
 * Created by aagam on 26/1/15.
 */
public class WCDetailAdapter extends CacheFragmentStatePagerAdapter {

    private Context mContext;

    public WCDetailAdapter(FragmentManager fm,Context ctx) {
        super(fm);
        mContext = ctx;
    }

    @Override
    protected Fragment createItem(int position) {
        switch (position){
            case 0:
                return new WordCampOverview();
            case 1:
                return new SessionsFragment();
            case 2:
                return new SpeakerFragment();
            default:
                return new WordCampOverview();
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
                return mContext.getString(R.string.title_overview);
            case 1:
                return mContext.getString(R.string.title_schedule);
            default:
                return mContext.getString(R.string.title_speakers);
        }
    }
}
