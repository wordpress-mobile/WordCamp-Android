package org.wordcamp.wcdetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.wordcamp.R;
import org.wordcamp.WordCampDetailActivity;
import org.wordcamp.objects.WordCampDB;
import org.wordcamp.objects.wordcamp.WordCamps;
import org.wordcamp.utils.ImageUtils;

/**
 * Created by aagam on 26/1/15.
 */
public class WordCampOverview extends Fragment {
    public WordCampDB wc;
    //    public WordCamps wholeWC;
    public TextView location, about;
    public ImageView wcFeaturedImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wc = ((WordCampDetailActivity) getActivity()).wcdb;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_wcdetails_overview, container, false);
        View v1 = v.findViewById(R.id.wc_image_container);
        wcFeaturedImage = (ImageView) v.findViewById(R.id.featuredImage);
        if (wc.featureImageUrl != null && !wc.featureImageUrl.equals("")) {
            Picasso.with(getActivity()).load("http://central.wordcamp.org/files/2014/12/Norway.png").placeholder(R.drawable.wcparis).into(wcFeaturedImage);
        }
        location = (TextView) v.findViewById(R.id.wc_location);

        setLocationText();

        about = (TextView) v.findViewById(R.id.wc_about);
        about.setText(Html.fromHtml(wc.getAbout()));
        v1.getLayoutParams().height = ImageUtils.getAspectRatio(getActivity());
        ViewCompat.setElevation(v.findViewById(R.id.centerLayoutDetail), getResources().getDimension(R.dimen.list_elevation));
        return v;
    }

    private void setLocationText() {
        location.setText(wc.getLocation() + "\n" + wc.getVenue() + "\n" + wc.getAddress());
    }

    public void updateData(WordCamps wcs) {
/*//        wholeWC = wcs;
//If WC featured image is null, then when passed to WordCampDb, it cant access it.
        if (wcs.getFeaturedImage() != null)
            wc = new WordCampDB(wcs, "");
        else {
            Gson gson = new Gson();
            String url = "";
            if (wcs.getFoo().getURL().size() > 0 && !wcs.getFoo().getURL().get(0).equals("")) {
                url = wcs.getFoo().getURL().get(0);
            }
            wc = new WordCampDB(wcs.getID(), wcs.getTitle(), wcs.getFoo().getStartDateYYYYMmDd().get(0),
                    wcs.getFoo().getEndDateYYYYMmDd().get(0), "", gson.toJson(wcs), url, "", wc.isMyWC, "");
        }
        setLocationText();
        about.setText(Html.fromHtml(wholeWC.getContent()));
        if (!wc.featureImageUrl.equals("")) {
            Picasso.with(getActivity()).load(wc.getFeatureImageUrl()).placeholder(R.drawable.wclondon).into(wcFeaturedImage);
        }*/
    }
}