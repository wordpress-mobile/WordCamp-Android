package org.wordcamp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.wordcamp.widgets.SlidingTabLayout;

/**
 * Created by aagam on 6/1/15.
 */
public class SlidingTabsFragment extends Fragment {

    private SlidingTabLayout mSlidingTabLayout;

    private ViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.slider_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mViewPager.setAdapter(new WCFragmentPagerAdapter(getActivity().getSupportFragmentManager()));
//        mViewPager.setAdapter(new WCPagerAdapter());
        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setDistributeEvenly(true);

        mSlidingTabLayout.setSelectedIndicatorColors(Color.parseColor("#0F85D1"));
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    class WCPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
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

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            switch (position){
                case 0:
                    View view = getActivity().getLayoutInflater().inflate(R.layout.pager_item,
                            container, false);
                    // Add the newly created View to the ViewPager
                    container.addView(view);
                    return view;
                default:
                    // Inflate a new layout from our resources
                    View views = getActivity().getLayoutInflater().inflate(R.layout.pager_item,
                            container, false);
                    // Add the newly created View to the ViewPager
                    container.addView(views);

                    // Retrieve a TextView from the inflated View, and update it's text
                    TextView title = (TextView) views.findViewById(R.id.item_title);
                    title.setText(String.valueOf(position + 1));
                    return views;
            }
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }

    class WCFragmentPagerAdapter extends FragmentPagerAdapter{
        public WCFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
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

        @Override
        public Fragment getItem(int position) {
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
    }


}
