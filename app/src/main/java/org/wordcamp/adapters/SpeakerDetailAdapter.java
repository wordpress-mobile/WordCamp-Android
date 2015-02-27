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
public class SpeakerDetailAdapter extends BaseAdapter {

    public Context mContext;
    public List<String> session;
    public LayoutInflater inflater;

    public SpeakerDetailAdapter(Context ctx, List<String> names){
        mContext = ctx;
        session = names;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return session.size();
    }

    @Override
    public Object getItem(int position) {
        return session.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView title;
        if(convertView==null){
            convertView = inflater.inflate(R.layout.item_session_by_speaker,parent,false);
        }

        title = (TextView)convertView.findViewById(R.id.item_session);
        title.setText(session.get(position));

        return convertView;
    }
}
