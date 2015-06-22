package org.wordcamp.android.adapters;

import android.content.Context;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.wordcamp.android.R;
import org.wordcamp.android.objects.SessionDB;
import org.wordcamp.android.utils.WordCampUtils;

import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by aagam on 8/2/15.
 */
public class SessionsListAdapter extends BaseAdapter implements StickyListHeadersAdapter {
    private List<SessionDB> list;
    private Context ctx;
    private LayoutInflater inflater;
    private OnAddToMySessionListener listener;
    private int singleLineSize, doubleLineSize;

    public SessionsListAdapter(Context ctx, List<SessionDB> dbList, OnAddToMySessionListener listener) {
        this.ctx = ctx;
        this.listener = listener;
        this.list = dbList;
        inflater = LayoutInflater.from(ctx);
        singleLineSize = (int) ctx.getResources().getDimension(R.dimen.single_line_list_item);
        doubleLineSize = (int) ctx.getResources().getDimension(R.dimen.double_line_list_item);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_session, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final SessionDB db = list.get(position);

        holder.title.setText(Html.fromHtml(db.getTitle()));

        if (db.getLocation() == null || db.getLocation().isEmpty()) {
            holder.location.setVisibility(View.GONE);
        } else {
            holder.location.setVisibility(View.VISIBLE);
            holder.location.setText(Html.fromHtml(db.getLocation()));
        }

        if (db.isMySession) {
            Picasso.with(ctx).load(R.drawable.ic_favorite_red_24dp).into(holder.favorite);
        } else {
            Picasso.with(ctx).load(R.drawable.ic_favorite_border_red_24dp).into(holder.favorite);
        }

        holder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do not refresh the whole list, just update in DB & the item

                if (db.isMySession) {
                    db.isMySession = false;
                    list.set(position, db);
                    listener.removeMySession(db);
                    Picasso.with(ctx).load(R.drawable.ic_favorite_border_red_24dp).into(holder.favorite);
                } else {
                    db.isMySession = true;
                    list.set(position, db);
                    listener.addMySession(db);
                    Picasso.with(ctx).load(R.drawable.ic_favorite_red_24dp).into(holder.favorite);
                }
            }
        });

        if (list.get(position).category.equals("custom")) {
            holder.container.getLayoutParams().height = singleLineSize;
            holder.favorite.setVisibility(View.INVISIBLE);
            if (db.getLocation() == null || db.getLocation().isEmpty())
                holder.title.setGravity(Gravity.CENTER);
            else
                holder.title.setGravity(Gravity.LEFT);
        } else {
            holder.container.getLayoutParams().height = doubleLineSize;
            holder.favorite.setVisibility(View.VISIBLE);
            if (db.getLocation() == null || db.getLocation().isEmpty())
                holder.title.setGravity(Gravity.CENTER_VERTICAL);
            else
                holder.title.setGravity(Gravity.LEFT);
        }
        return convertView;
    }

    @Override
    public View getHeaderView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = inflater.inflate(R.layout.header_time_session, viewGroup, false);
        }

        TextView date = (TextView) view.findViewById(R.id.headerListTitle);
        date.setText(WordCampUtils.formatProperTime(list.get(i).getTime()));
        return view;
    }

    @Override
    public boolean isEnabled(int position) {
        return !list.get(position).category.equals("custom");
    }

    @Override
    public long getHeaderId(int i) {
        return (long) WordCampUtils.formatProperTimeHash(list.get(i).getTime());
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public SessionDB getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView title, location, speakers;
        public ImageView favorite;
        public RelativeLayout container;

        public ViewHolder(View v) {
            title = (TextView) v.findViewById(R.id.titleSession);
            location = (TextView) v.findViewById(R.id.locationSession);
            speakers = (TextView) v.findViewById(R.id.speakersSession);
            favorite = (ImageView) v.findViewById(R.id.favorite);
            container = (RelativeLayout) v.findViewById(R.id.item_session_container);
        }
    }

    public interface OnAddToMySessionListener {
        void addMySession(SessionDB db);

        void removeMySession(SessionDB db);
    }

}
