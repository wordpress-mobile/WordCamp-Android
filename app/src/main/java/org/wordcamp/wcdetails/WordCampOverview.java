package org.wordcamp.wcdetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.wordcamp.R;
import org.wordcamp.utils.ImageUtils;

/**
 * Created by aagam on 26/1/15.
 */
public class WordCampOverview extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_wcdetails_overview,container,false);
        View v1 = v.findViewById(R.id.wc_image_container);
        v1.getLayoutParams().height = ImageUtils.getAspectRatio(getActivity());

        ViewCompat.setElevation(v.findViewById(R.id.centerLayoutDetail), getResources().getDimension(R.dimen.list_elevation));

        return v;
    }
}
