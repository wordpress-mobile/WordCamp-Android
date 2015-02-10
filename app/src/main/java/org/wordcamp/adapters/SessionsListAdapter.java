package org.wordcamp.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.wordcamp.R;
import org.wordcamp.objects.SessionDB;
import org.wordcamp.utils.WordCampUtils;

import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by aagam on 8/2/15.
 */
public class SessionsListAdapter extends BaseAdapter implements StickyListHeadersAdapter {
    public List<SessionDB> list;
    public Context ctx;
    public LayoutInflater inflater;
    public OnAddToMySessionListener listener;

    public SessionsListAdapter(Context ctx, List<SessionDB> dbList, OnAddToMySessionListener listener){
        this.ctx = ctx;
        this.listener = listener;
        this.list = dbList;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView==null){
            convertView = inflater.inflate(R.layout.item_session_new,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else{
            holder = (ViewHolder)convertView.getTag();
        }

        final SessionDB db  = list.get(position);

        holder.title.setText(Html.fromHtml(list.get(position).getTitle()));
        holder.speakers.setText("Aagam Shah");
        holder.location.setText(db.getLocation());

        if(db.isMySession){
            Picasso.with(ctx).load(R.drawable.ic_favorite_black_24dp).into(holder.favorite);
        }

        holder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do not refresh the whole list, just update in DB & the item

                if(db.isMySession){
                    db.isMySession = false;
                    list.set(position,db);
                    listener.removeMySession(db);
                    Picasso.with(ctx).load(R.drawable.ic_favorite_outline_black_24dp).into(holder.favorite);
                } else{
                    db.isMySession = true;
                    list.set(position,db);
                    listener.addMySession(db);
                    Picasso.with(ctx).load(R.drawable.ic_favorite_black_24dp).into(holder.favorite);
                }
            }
        });
        return convertView;
    }

    @Override
    public View getHeaderView(int i, View view, ViewGroup viewGroup) {

        if(view ==null){
            view = inflater.inflate(R.layout.header_time_session, viewGroup, false);
        }

        TextView date = (TextView) view.findViewById(R.id.headerListTitle);
        date.setText(WordCampUtils.formatProperTime(list.get(i).getTime()));
        return view;
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
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder{
        public TextView title,location,speakers;
        public ImageView favorite;
        public ViewHolder(View v){
            title = (TextView) v.findViewById(R.id.titleSession);
            location = (TextView) v.findViewById(R.id.locationSession);
            speakers = (TextView) v.findViewById(R.id.speakersSession);
            favorite = (ImageView) v.findViewById(R.id.favorite);

        }
    }

    public interface OnAddToMySessionListener{
        public void addMySession(SessionDB db);
        public void removeMySession(SessionDB db);
    }

}
