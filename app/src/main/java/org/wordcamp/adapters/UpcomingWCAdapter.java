package org.wordcamp.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ee","ee");
            }
        });
        return new ViewHolder(v,this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText("Card "+position);

    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView mTextView;
        private UpcomingWCAdapter upcomingWCAdapter;
        public ViewHolder(View v, UpcomingWCAdapter adapter) {
            super(v);
            v.setOnClickListener(this);
          /*  Button b = (Button)v.findViewById(R.id.openItem);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("Clicked", "View");
                }
            });*/
            mTextView = (TextView)v.findViewById(R.id.up_wc_dates);
            upcomingWCAdapter=adapter;
        }

        @Override
        public void onClick(View v) {
            Log.e("Clicked", "View");
        }
    }

    }
