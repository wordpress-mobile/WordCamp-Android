package org.wordcamp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.wordcamp.R;
import org.wordcamp.objects.WordCampDB;
import org.wordcamp.utils.WordCampUtils;

import java.util.List;

/**
 * Created by aagam on 8/1/15.
 */
public class UpcomingWCAdapter extends RecyclerView.Adapter<UpcomingWCAdapter.ViewHolder> {

    public List<WordCampDB> wordCamps;

    public Context ctx;
    public UpcomingWCAdapter(List<WordCampDB> arr, Context context) {
        wordCamps=arr;
        ctx=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.upcoming_wc_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WordCampDB wc = wordCamps.get(position);
        holder.title.setText(wc.getWc_title());
        holder.date.setText(WordCampUtils.getProperDate(wc));
        holder.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ctx, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        if(wc.featureImageUrl!=null && !wc.featureImageUrl.equals(""))
            Picasso.with(ctx).load(wc.featureImageUrl).into(holder.icon);
    }


    @Override
    public int getItemCount() {
        return wordCamps.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title,date;
        public ImageView icon,bookmark;
        public ViewHolder(View v) {
            super(v);
            title = (TextView)v.findViewById(R.id.up_wc_title);
            date = (TextView)v.findViewById(R.id.up_wc_dates);
            icon = (ImageView)v.findViewById(R.id.wcIcon);
            bookmark = (ImageView)v.findViewById(R.id.bookmark);
        }

    }

}
