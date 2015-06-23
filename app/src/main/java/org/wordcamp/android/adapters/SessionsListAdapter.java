package org.wordcamp.android.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        return convertView;
    }

    @Override
    public View getHeaderView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.header_time_session, viewGroup, false);
        }

        TextView date = (TextView) view.findViewById(R.id.headerListDate);
        date.setText(WordCampUtils.getFormattedDate(list.get(i).getTime()));
        TextView time = (TextView) view.findViewById(R.id.headerListTime);
        time.setText(WordCampUtils.getFormattedTime(list.get(i).getTime()));
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
        public TextView title;
        public TextView location;
        public LinearLayout container;

        public ViewHolder(View v) {
            title = (TextView) v.findViewById(R.id.titleSession);
            location = (TextView) v.findViewById(R.id.locationSession);
            container = (LinearLayout) v.findViewById(R.id.item_session_container);
        }
    }

    public interface OnAddToMySessionListener {
        void addMySession(SessionDB db);

        void removeMySession(SessionDB db);
    }

}
