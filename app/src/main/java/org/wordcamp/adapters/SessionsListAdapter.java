package org.wordcamp.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.wordcamp.R;
import org.wordcamp.objects.SessionDB;

import java.util.Date;
import java.util.List;

/**
 * Created by aagam on 31/1/15.
 */
public class SessionsListAdapter extends RecyclerView.Adapter<SessionsListAdapter.ViewHolder> {

    public List<SessionDB> list;
    public SessionsListAdapter(List<SessionDB> sessionList){
        list = sessionList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_session, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(Html.fromHtml(list.get(position).getTitle()));
        Date d = new Date(list.get(position).getTime());
        holder.abstractView.setText(d.toString());
    }

    @Override
    public int getItemCount() {
        return 30;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView title, abstractView;
        public final ImageView bgPic;

        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.session_title);
            abstractView = (TextView) v.findViewById(R.id.session_abstract);
            bgPic = (ImageView)v.findViewById(R.id.session_image);
        }
    }
}
