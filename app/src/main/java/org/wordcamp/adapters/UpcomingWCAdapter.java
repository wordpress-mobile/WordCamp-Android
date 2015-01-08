package org.wordcamp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.wordcamp.R;

/**
 * Created by aagam on 8/1/15.
 */
public class UpcomingWCAdapter extends RecyclerView.Adapter<UpcomingWCAdapter.ViewHolder> {
    String[] items;
    public UpcomingWCAdapter(String[] arr) {
        items=arr;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.upcoming_wc_card, parent, false);
       return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText("Card "+position);
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView)v.findViewById(R.id.up_wc_dates);
        }
    }
}
