package org.wordcamp.android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.wordcamp.android.R;

import java.util.List;

/**
 * Created by aagam on 30/1/15.
 */
public class SessionsBySpeakerListAdapter extends RecyclerView.Adapter<SessionsBySpeakerListAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<String> sessionList;
    private OnSessionSelected onSessionSelectedListener;

    public SessionsBySpeakerListAdapter(Context ctx, List<String> sessionList) {
        this.context = ctx;
        inflater = LayoutInflater.from(this.context);
        this.sessionList = sessionList;
    }

    public void setOnSessionSelectedListener(OnSessionSelected listener) {
        onSessionSelectedListener = listener;
    }

    public interface OnSessionSelected {
        void onSessionSelected(int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_session_by_speaker, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.title.setText(Html.fromHtml(sessionList.get(position)));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSessionSelectedListener.onSessionSelected(position);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return sessionList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.item_session);
        }
    }
}
