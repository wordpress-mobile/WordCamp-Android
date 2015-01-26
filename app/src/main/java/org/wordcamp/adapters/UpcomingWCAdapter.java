package org.wordcamp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.wordcamp.R;
import org.wordcamp.objects.wordcamp.WordCamps;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by aagam on 8/1/15.
 */
public class UpcomingWCAdapter extends RecyclerView.Adapter<UpcomingWCAdapter.ViewHolder> {



    public List<WordCamps> wordCamps;

    public UpcomingWCAdapter(List<WordCamps> arr) {
        wordCamps=arr;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.upcoming_wc_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WordCamps wc = wordCamps.get(position);
        holder.title.setText(wc.getTitle());
        if(wc.getFoo().getStartDateYYYYMmDd().size()>0 && !wc.getFoo().getStartDateYYYYMmDd().get(0).equals("")
                && !wc.getFoo().getEndDateYYYYMmDd().get(0).equals("")){


            Date d = new Date(Long.parseLong(wc.getFoo().getStartDateYYYYMmDd().get(0)) * 1000);
            Date d1 = new Date(Long.parseLong(wc.getFoo().getEndDateYYYYMmDd().get(0)) * 1000);

            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd");
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd, yyyy");

            holder.date.setText(sdf.format(d)+" - "+sdf1.format(d1));}
        else
            holder.date.setText("Coming soon");

    }

    @Override
    public int getItemCount() {
        return wordCamps.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title,date;
        private UpcomingWCAdapter upcomingWCAdapter;
        public ViewHolder(View v) {
            super(v);
            title = (TextView)v.findViewById(R.id.up_wc_title);
            date = (TextView)v.findViewById(R.id.up_wc_dates);

        }

    }

}
