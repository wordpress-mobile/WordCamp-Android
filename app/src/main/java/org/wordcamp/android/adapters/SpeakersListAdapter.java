package org.wordcamp.android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.wordcamp.android.R;
import org.wordcamp.android.objects.SpeakerDB;
import org.wordcamp.android.widgets.CircularImageView;

import java.util.List;

/**
 * Created by aagam on 30/1/15.
 */
public class SpeakersListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<SpeakerDB> speakerDBList;

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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_speaker, null);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.speaker_name);
            holder.dp = (CircularImageView) convertView.findViewById(R.id.speaker_avatar);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        SpeakerDB speakerDB = speakerDBList.get(position);
        holder.title.setText(speakerDB.getName());
        Picasso.with(context).load(speakerDB.getGravatar()).placeholder(R.drawable.ic_account_circle_grey600).into(holder.dp);

        return convertView;
    }

    static class ViewHolder {
        TextView title;
        CircularImageView dp;
    }
}
