package org.wordcamp.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.wordcamp.android.adapters.WCListAdapter;
import org.wordcamp.android.db.DBCommunicator;
import org.wordcamp.android.objects.WordCampDB;
import org.wordcamp.android.utils.WordCampUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class PastWCFragment extends Fragment implements WCListAdapter.WCListener {
    private ListView pastWCLists;
    private List<WordCampDB> wordCampDBs;
    private DBCommunicator communicator;
    private upcomingFragListener listener;
    private SwipeRefreshLayout refreshLayout;
    public WCListAdapter adapter;
    private TextView emptyView;

    public static PastWCFragment newInstance() {
        return new PastWCFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wc, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        communicator = ((BaseActivity) getActivity()).communicator;
        wordCampDBs = ((BaseActivity) getActivity()).wordCampsList;
        if (wordCampDBs != null) {
            sortWC();
        }

        View v = getView();
        pastWCLists = (ListView) v.findViewById(R.id.scroll);
        emptyView = (TextView) v.findViewById(R.id.empty_view);
        emptyView.setText(getActivity().getString(R.string.empty_wordcamps));

        refreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_layout);
        refreshLayout.setColorSchemeResources(R.color.swipe_refresh_color1,
                R.color.swipe_refresh_color2, R.color.swipe_refresh_color3);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //listener.onRefreshStart();
            }
        });
        setProperListPosition();

        adapter = new WCListAdapter(wordCampDBs, getActivity(), this);
        updateEmptyView();
        pastWCLists.setAdapter(adapter);
        pastWCLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), WordCampDetailActivity.class);
                i.putExtra("wc", adapter.getItem(position));
                startActivity(i);
            }
        });

        if (wordCampDBs.size() == 0) {
            startRefresh();
        }
    }

    private void updateEmptyView() {
        if (adapter.getCount() < 1) {
            emptyView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (BaseActivity) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement PastFragListener");
        }
    }

    private void startRefresh() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                //listener.onRefreshStart();
            }
        });
    }

    private void sortDescWC() {
        Collections.sort(wordCampDBs, new Comparator<WordCampDB>() {
            @Override
            public int compare(WordCampDB lhs, WordCampDB rhs) {
                int lhstime = Integer.parseInt(lhs.getWc_start_date());
                int rhstime = Integer.parseInt(rhs.getWc_start_date());
                return rhstime - lhstime;
            }
        });
    }

    private void sortWC() {
        Collections.sort(wordCampDBs, new Comparator<WordCampDB>() {
            @Override
            public int compare(WordCampDB lhs, WordCampDB rhs) {
                int lhstime = Integer.parseInt(lhs.getWc_start_date());
                int rhstime = Integer.parseInt(rhs.getWc_start_date());
                return lhstime - rhstime;
            }
        });
    }

    public void updateList(List<WordCampDB> wordCampsList) {
        wordCampDBs = wordCampsList;
        sortWC();
        setProperListPosition();
        adapter = new WCListAdapter(wordCampDBs, getActivity(), this);
        updateEmptyView();
        pastWCLists.setAdapter(adapter);

    }

    private void setProperListPosition() {
        int pos = WordCampUtils.findFirstUpcomingFrag(wordCampDBs);
        if (pos > -1) {
            wordCampDBs = wordCampDBs.subList(0, pos);
            sortDescWC();
        }
    }

    @Override
    public int addToMyWC(int wcid, int position) {
        int retId = communicator.addToMyWC(wcid);
        WordCampDB wordCampDB = adapter.getItem(position);
        listener.onNewMyWCAdded(wordCampDB);
        return retId;
    }

    @Override
    public void removeMyWC(int wcid, int position) {
        communicator.removeFromMyWCSingle(wcid);
        WordCampDB wordCampDB = adapter.getItem(position);
        listener.onMyWCRemoved(wordCampDB);
    }

    public void stopRefresh() {
        refreshLayout.setRefreshing(false);
    }

    public interface upcomingFragListener {
        void onNewMyWCAdded(WordCampDB wordCampDB);

        void onRefreshStart();

        void onMyWCRemoved(WordCampDB wordCampDB);
    }
}
