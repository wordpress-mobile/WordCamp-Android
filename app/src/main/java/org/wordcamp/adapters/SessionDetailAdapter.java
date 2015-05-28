package org.wordcamp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.wordcamp.R;
import org.wordcamp.objects.MiniSpeaker;

import java.util.List;

/**
 * Created by aagam on 12/2/15.
 */
public class SessionDetailAdapter extends BaseAdapter {

    private Context mContext;
    private List<MiniSpeaker> speakers;
    private LayoutInflater inflater;

    public SessionDetailAdapter(Context ctx, List<MiniSpeaker> speakerList){
        mContext = ctx;
        speakers = speakerList;
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
        ImageView dp;
        if(convertView==null){
            convertView = inflater.inflate(R.layout.item_session_detail_speaker,parent,false);
        }

        title = (TextView)convertView.findViewById(R.id.item_session_speaker);
        dp = (ImageView)convertView.findViewById(R.id.speaker_dp);
        title.setText(speakers.get(position).name);
        Picasso.with(mContext).load(speakers.get(position).dp).
                placeholder(R.drawable.ic_account_circle_grey600).into(dp);

        return convertView;
    }
}
