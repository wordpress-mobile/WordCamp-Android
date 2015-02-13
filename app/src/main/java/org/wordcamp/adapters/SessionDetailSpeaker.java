package org.wordcamp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.wordcamp.R;

import java.util.List;

/**
 * Created by aagam on 12/2/15.
 */
public class SessionDetailSpeaker extends BaseAdapter {

    public Context mContext;
    public List<String> speakers;
    public LayoutInflater inflater;

    public SessionDetailSpeaker(Context ctx, List<String> names){
        mContext = ctx;
        speakers = names;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return speakers.size();
    }

    @Override
    public Object getItem(int position) {
        return speakers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView title;
        if(convertView==null){
            convertView = inflater.inflate(R.layout.item_session_detail_speaker,parent,false);
        }

        title = (TextView)convertView.findViewById(R.id.item_session_speaker);
        title.setText(speakers.get(position));

        return convertView;
    }
}
