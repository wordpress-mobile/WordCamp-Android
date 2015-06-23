package org.wordcamp.android.wcdetails;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.wordcamp.android.R;
import org.wordcamp.android.WordCampDetailActivity;
import org.wordcamp.android.objects.WordCampDB;
import org.wordcamp.android.utils.WordCampUtils;

/**
 * Created by aagam on 26/1/15.
 */
public class WordCampOverview extends Fragment {
    private WordCampDB wc;
    private TextView location;
    private TextView mAboutTextView;
    private WordCampOverviewListener listener;
    private SwipeRefreshLayout refreshLayout;
    private View mAboutCardView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wcdetails_overview, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        wc = ((WordCampDetailActivity) getActivity()).wcdb;
        View v = getView();

        refreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_layout);
        refreshLayout.setColorSchemeResources(R.color.swipe_refresh_color1,
                R.color.swipe_refresh_color2, R.color.swipe_refresh_color3);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startRefreshOverview();
            }
        });

        location = (TextView) v.findViewById(R.id.wc_location);
        setLocationText();

        TextView dateTextView = (TextView) v.findViewById(R.id.wc_date);
        dateTextView.setText(WordCampUtils.getProperDate(wc));

        mAboutCardView = v.findViewById(R.id.about_card);
        mAboutTextView = (TextView) v.findViewById(R.id.wc_about);
        setAboutText();

        View maps = v.findViewById(R.id.navigate_button);
        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMaps();
            }
        });
    }

    private void openMaps() {
        String map = "http://maps.google.com/maps?q="
                + location.getText().toString().replaceAll("\n|\r", ",").replaceAll(" ", "+");
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
        startActivity(intent);
    }

    private void setLocationText() {
        location.setText(wc.getVenue() + "\n" + wc.getAddress());
    }

    private void setAboutText() {
        if (wc.getAbout().isEmpty())
            mAboutCardView.setVisibility(View.GONE);
        else {
            mAboutCardView.setVisibility(View.VISIBLE);
            mAboutTextView.setText(Html.fromHtml(wc.getAbout()));
        }
    }

    private void startRefreshOverview() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                listener.refreshOverview();
            }
        });
    }

    public void stopRefreshOverview() {
        refreshLayout.setRefreshing(false);
    }

    public void updateData(WordCampDB wcd) {
        wc = wcd;
        setLocationText();
        setAboutText();
        stopRefreshOverview();
    }

    public interface WordCampOverviewListener {
        void refreshOverview();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (WordCampDetailActivity) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement WordCampOverviewListener");
        }
    }
}
