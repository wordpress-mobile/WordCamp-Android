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
public class UpcomingWCListAdapter extends BaseAdapter implements Filterable {

    public List<WordCampDB> wordCamps, filteredWordCamps;

    public Context ctx;

    public LayoutInflater inflater;

    private WCListener listener;

    private int color, color1;

    private WordCampsFilter wordCampsFilter;

    public UpcomingWCListAdapter(List<WordCampDB> arr, Context context, WCListener listener) {
        wordCamps = arr;
        ctx = context;
        this.listener = listener;
        inflater = LayoutInflater.from(ctx);
        color = ctx.getResources().getColor(R.color.flat_light_pink);
        color1 = ctx.getResources().getColor(R.color.flat_light_blue);
        filteredWordCamps = wordCamps;
        wordCampsFilter = new WordCampsFilter();
    }

    @Override
    public int getCount() {
        return filteredWordCamps.size();
    }

    @Override
    public WordCampDB getItem(int position) {
        return filteredWordCamps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;

        if (view == null) {
            view = inflater.inflate(R.layout.upcoming_wc_card, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final WordCampDB wc = filteredWordCamps.get(position);
        holder.title.setText(wc.getWc_title());
//        holder.date.setText(WordCampUtils.getProperFormatDate(wc.getWc_start_date()));
        holder.date.setText(WordCampUtils.getProperDate(wc));
        if (wc.isMyWC) {
            Picasso.with(ctx).load(R.drawable.ic_bookmark_grey600_24dp).into(holder.bookmark);
            holder.bookmark.setEnabled(false);
        } else {
            Picasso.with(ctx).load(R.drawable.ic_bookmark_outline_grey600_24dp).into(holder.bookmark);
            holder.bookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!wc.isMyWC) {
                        listener.addToMyWC(wc.getWc_id(), position);
                        Picasso.with(ctx).load(R.drawable.ic_bookmark_grey600_24dp).into(holder.bookmark);
                        wc.isMyWC = true;
                        filteredWordCamps.set(position, wc);
                    } else {
                        listener.removeMyWC(wc.getWc_id(), position);
                        Picasso.with(ctx).load(R.drawable.ic_bookmark_outline_grey600_24dp).into(holder.bookmark);
                        wc.isMyWC = false;
                        filteredWordCamps.set(position, wc);
                    }
                }
            });
        }

        //Currently featured image will always be null due to bug in API
        /*if(wc.featureImageUrl!=null && !wc.featureImageUrl.equals(""))
            Picasso.with(ctx).load(wc.featureImageUrl).into(holder.icon);*/

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
        public ImageView icon, bookmark;

        public ViewHolder(View v) {
            title = (TextView) v.findViewById(R.id.up_wc_title);
            date = (TextView) v.findViewById(R.id.up_wc_dates);
            icon = (ImageView) v.findViewById(R.id.wcIcon);
            bookmark = (ImageView) v.findViewById(R.id.bookmark);
        }
    }

    public interface WCListener {
        public int addToMyWC(int wcid, int position);

        public void removeMyWC(int wcid, int position);
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
