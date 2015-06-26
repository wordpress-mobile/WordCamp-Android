package org.wordcamp.android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.wordcamp.android.R;
import org.wordcamp.android.objects.WordCampDB;
import org.wordcamp.android.utils.WordCampUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aagam on 5/2/15.
 */
public class WCListAdapter extends RecyclerView.Adapter<WCListAdapter.ViewHolder> implements Filterable {

    private List<WordCampDB> wordCamps;
    private List<WordCampDB> filteredWordCamps;
    private Context ctx;
    private LayoutInflater inflater;
    private WCListener listener;
    private WordCampsFilter wordCampsFilter;
    private OnWCSelectedListener wcSelectedListener;

    public WCListAdapter(List<WordCampDB> arr, Context context, WCListener listener) {
        wordCamps = arr;
        ctx = context;
        this.listener = listener;
        inflater = LayoutInflater.from(ctx);
        filteredWordCamps = wordCamps;
        wordCampsFilter = new WordCampsFilter();
    }

    public void setOnWCSelectedListener(OnWCSelectedListener listener) {
        wcSelectedListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_wordcamp, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final WordCampDB wc = filteredWordCamps.get(position);
        holder.title.setText(wc.getWc_title());
        holder.date.setText(WordCampUtils.getProperDate(wc));
        if (wc.isMyWC) {
            Picasso.with(ctx).load(R.drawable.ic_favorite_solid_24dp).into(holder.bookmark);
        } else {
            Picasso.with(ctx).load(R.drawable.ic_favorite_outline_24dp).into(holder.bookmark);
        }

        holder.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!wc.isMyWC) {
                    listener.addToMyWC(wc.getWc_id(), position);
                    Picasso.with(ctx).load(R.drawable.ic_favorite_solid_24dp).into(holder.bookmark);
                    wc.isMyWC = true;
                    filteredWordCamps.set(position, wc);
                } else {
                    Picasso.with(ctx).load(R.drawable.ic_favorite_outline_24dp).into(holder.bookmark);
                    wc.isMyWC = false;
                    filteredWordCamps.set(position, wc);
                    listener.removeMyWC(wc.getWc_id(), position);
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WordCampDB wc = getItem(position);
                wcSelectedListener.onWCSelected(wc);
            }
        });

    }

    public void removeWC(int position) {
        filteredWordCamps.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, filteredWordCamps.size());
    }

    public WordCampDB getItem(int position) {
        return filteredWordCamps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return filteredWordCamps.size();
    }

    @Override
    public Filter getFilter() {
        return wordCampsFilter;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, date;
        public ImageView icon, bookmark;

        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.up_wc_title);
            date = (TextView) v.findViewById(R.id.up_wc_dates);
            bookmark = (ImageView) v.findViewById(R.id.bookmark);
        }
    }

    public interface WCListener {
        int addToMyWC(int wcid, int position);

        void removeMyWC(int wcid, int position);
    }

    public interface OnWCSelectedListener {
        void onWCSelected(WordCampDB wc);
    }

    private class WordCampsFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            final String filterString = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();

            List<WordCampDB> filteredList = new ArrayList<>();
            int size = wordCamps.size();
            for (int i = 0; i < size; ++i) {
                WordCampDB wcdb = wordCamps.get(i);
                if (wcdb.getWc_title().toLowerCase().contains(filterString)) {
                    filteredList.add(wcdb);
                }
            }
            results.values = filteredList;
            results.count = filteredList.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredWordCamps = (ArrayList<WordCampDB>) results.values;
            notifyDataSetChanged();
        }
    }
}
