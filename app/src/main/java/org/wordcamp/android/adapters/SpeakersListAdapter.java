package org.wordcamp.android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.wordcamp.android.R;
import org.wordcamp.android.objects.SpeakerDB;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by aagam on 30/1/15.
 */
public class SpeakersListAdapter extends RecyclerView.Adapter<SpeakersListAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<SpeakerDB> speakerDBList;
    private OnSpeakerSelectedListener speakerSelectedListener;

    public SpeakersListAdapter(Context ctx, List<SpeakerDB> speakerDBList) {
        this.context = ctx;
        inflater = LayoutInflater.from(this.context);
        this.speakerDBList = speakerDBList;
    }

    public void setOnSpeakerSelectedListener(OnSpeakerSelectedListener listener) {
        speakerSelectedListener = listener;
    }

    public interface OnSpeakerSelectedListener {
        void onSpeakerSelected(SpeakerDB speakerDB);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_speaker, null);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final SpeakerDB speakerDB = speakerDBList.get(position);
        holder.title.setText(speakerDB.getName());
        Picasso.with(context).load(speakerDB.getGravatar())
                .placeholder(R.drawable.ic_account_circle_grey600).into(holder.dp);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speakerSelectedListener.onSpeakerSelected(speakerDB);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return speakerDBList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        CircleImageView dp;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.speaker_name);
            dp = (CircleImageView) itemView.findViewById(R.id.speaker_avatar);
        }
    }
}
