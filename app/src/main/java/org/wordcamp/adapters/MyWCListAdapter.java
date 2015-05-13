package org.wordcamp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.squareup.picasso.Picasso;

import org.wordcamp.R;
import org.wordcamp.objects.WordCampDB;
import org.wordcamp.utils.WordCampUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aagam on 5/2/15.
 */
public class MyWCListAdapter extends BaseAdapter implements Filterable {

    public List<WordCampDB> wordCamps, filteredWCs;

    public Context ctx;

    public OnDeleteListener listener;

    private WordCampsFilter wordCampsFilter;

    public LayoutInflater inflater;

    private int color, color1;

    public MyWCListAdapter(List<WordCampDB> arr, Context context, OnDeleteListener listener) {
        wordCamps = arr;
        ctx = context;
        inflater = LayoutInflater.from(ctx);
        this.listener = listener;
        filteredWCs = wordCamps;
        wordCampsFilter = new WordCampsFilter();
        color = ctx.getResources().getColor(R.color.flat_light_pink);
        color1 = ctx.getResources().getColor(R.color.flat_light_blue);

    }

    @Override
    public int getCount() {
        return filteredWCs.size();
    }

    @Override
    public WordCampDB getItem(int position) {
        return filteredWCs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder holder;

        if (view == null) {
            view = inflater.inflate(R.layout.my_wc_card, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final WordCampDB wc = filteredWCs.get(position);
        holder.title.setText(wc.getWc_title());
        holder.date.setText(WordCampUtils.getProperDate(wc));
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.removeWC();
            }
        });
        if (wc.featureImageUrl != null && !wc.featureImageUrl.equals(""))
            Picasso.with(ctx).load(wc.featureImageUrl).error(R.drawable.ic_refresh_white_36dp).into(holder.icon);

        TextDrawable drawable = TextDrawable.builder()
                .buildRound("" + wc.getWc_title().split(" ")[1].charAt(0), position % 2 == 0 ? color : color1);

        holder.icon.setImageDrawable(drawable);
        return view;
    }

    @Override
    public Filter getFilter() {
        return wordCampsFilter;
    }

    public static class ViewHolder {
        public TextView title, date;
        public ImageView icon, delete;

        public ViewHolder(View v) {
            title = (TextView) v.findViewById(R.id.up_wc_title);
            date = (TextView) v.findViewById(R.id.up_wc_dates);
            icon = (ImageView) v.findViewById(R.id.wcIcon);
            delete = (ImageView) v.findViewById(R.id.wcDelete);
        }
    }

    public interface OnDeleteListener {
        public void removeWC();
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
            filteredWCs = (ArrayList<WordCampDB>) results.values;
            notifyDataSetChanged();
        }
    }
}
