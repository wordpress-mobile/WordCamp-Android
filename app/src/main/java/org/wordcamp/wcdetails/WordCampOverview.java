package org.wordcamp.wcdetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import org.wordcamp.WordCampDetailActivity;
import org.wordcamp.R;
import org.wordcamp.objects.WordCampDB;
import org.wordcamp.objects.wordcamp.WordCamps;
import org.wordcamp.utils.ImageUtils;

/**
 * Created by aagam on 26/1/15.
 */
public class WordCampOverview extends Fragment {

    public WordCampDB wc;
    public WordCamps wholeWC;
    public TextView location,about;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wc = ((WordCampDetailActivity)getActivity()).wcdb;
        Gson gson = new Gson();
        wholeWC = gson.fromJson(wc.getGson_object(),WordCamps.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_wcdetails_overview,container,false);
        View v1 = v.findViewById(R.id.wc_image_container);
        location = (TextView)v.findViewById(R.id.wc_location);
        location.setText(wholeWC.getFoo().getVenueName().toString()
                +"\n"+wholeWC.getFoo().getPhysicalAddress().toString());
        about = (TextView)v.findViewById(R.id.wc_about);
        about.setText(Html.fromHtml(wholeWC.getContent()));
        int height = ImageUtils.getAspectRatio(getActivity());
        v1.getLayoutParams().height = height;
        View details = v.findViewById(R.id.centerLayoutDetail);
        ViewCompat.setElevation(v.findViewById(R.id.centerLayoutDetail), getResources().getDimension(R.dimen.list_elevation));
        return v;
    }

    public void updateData(WordCamps wcs){
        wholeWC = wcs;
        wc = new WordCampDB(wcs,"");


        location.setText(wholeWC.getFoo().getVenueName().toString()
                +"\n"+wholeWC.getFoo().getPhysicalAddress().toString());
        about.setText(Html.fromHtml(wholeWC.getContent()));
    }
}
