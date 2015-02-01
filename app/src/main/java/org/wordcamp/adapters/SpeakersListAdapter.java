package org.wordcamp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.wordcamp.R;
import org.wordcamp.objects.SpeakerDB;
import org.wordcamp.widgets.CircularImageView;

import java.util.List;

/**
 * Created by aagam on 30/1/15.
 */
public class SpeakersListAdapter extends BaseAdapter {

    public Context context;
    public LayoutInflater inflater;
    public List<SpeakerDB> speakerDBList;

    public SpeakersListAdapter(Context ctx, List<SpeakerDB> speakerDBList) {
        this.context = ctx;
        inflater = LayoutInflater.from(this.context);
        this.speakerDBList = speakerDBList;
    }

    @Override
    public int getCount() {
        return speakerDBList.size();
    }

    @Override
    public Object getItem(int position) {
        return speakerDBList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if(convertView==null){
            convertView = inflater.inflate(R.layout.item_speaker,null);
            holder = new ViewHolder();
            holder.title = (TextView)convertView.findViewById(R.id.speaker_title);
            holder.dp = (CircularImageView)convertView.findViewById(R.id.speaker_dp);

            convertView.setTag(holder);
        } else{
             holder = (ViewHolder)convertView.getTag();
        }

        SpeakerDB speakerDB = speakerDBList.get(position);
        holder.title.setText(speakerDB.getName());
        Picasso.with(context).load(speakerDB.getGravatar()).placeholder(R.drawable.ic_launcher).into(holder.dp);

        return convertView;
    }

    static class ViewHolder {
        TextView title;
        CircularImageView dp;
    }
}
