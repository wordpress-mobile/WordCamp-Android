package org.wordcamp.android.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import org.wordcamp.android.wcdetails.SessionsFragment;
import org.wordcamp.android.wcdetails.SpeakerFragment;
import org.wordcamp.android.wcdetails.WordCampOverview;

/**
 * Created by aagam on 26/1/15.
 */
public class WCDetailAdapter extends CacheFragmentStatePagerAdapter {

    public WCDetailAdapter(FragmentManager fm) {
        super(fm);
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
                return "Overview";
            case 1:
                return "Schedule";
            default:
                return "Speakers";
        }
    }
}
