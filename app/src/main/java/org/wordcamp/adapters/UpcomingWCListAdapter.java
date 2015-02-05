package org.wordcamp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.wordcamp.R;
import org.wordcamp.objects.WordCampDB;
import org.wordcamp.utils.WordCampUtils;

import java.util.List;

/**
 * Created by aagam on 5/2/15.
 */
public class UpcomingWCListAdapter extends BaseAdapter {

    public List<WordCampDB> wordCamps;

    public Context ctx;

    public LayoutInflater inflater;

    public UpcomingWCListAdapter(List<WordCampDB> arr, Context context) {
        wordCamps=arr;
        ctx=context;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return wordCamps.size();
    }

    @Override
    public Object getItem(int position) {
        return wordCamps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder holder;

        if (view == null) {
            view = inflater.inflate(R.layout.upcoming_wc_card, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final WordCampDB wc = wordCamps.get(position);
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

        return view;
    }

    public static class ViewHolder{
        public TextView title,date;
        public ImageView icon,bookmark;
        public ViewHolder(View v) {
            title = (TextView)v.findViewById(R.id.up_wc_title);
            date = (TextView)v.findViewById(R.id.up_wc_dates);
            icon = (ImageView)v.findViewById(R.id.wcIcon);
            bookmark = (ImageView)v.findViewById(R.id.bookmark);
        }
    }
}
