package org.wordcamp.objects;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.wordcamp.R;
import org.wordcamp.networking.WPAPIClient;
import org.wordcamp.objects.session.Session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by aagam on 28/1/15.
 */
public class SessionTester extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        WPAPIClient.getSession("", new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                    List<SessionDB> sessionDBList = new ArrayList<>();

                    Gson gson = new Gson();
                    JSONArray array = response;
                    for (int i = 0; i < array.length(); i++) {

                        try {
                            Session session = gson.fromJson(array.getJSONObject(i).toString(), Session.class);
                            String name = session.getTitle();
                            SessionDB db = new SessionDB(session.getID(),session.getID(),
                                    session.getTitle(),1,"",session.getTerms().getWcbTrack().get(0).getName(),
                                    session.getFoo().getWcptSessionType().get(0),array.getJSONObject(i).toString());

                            sessionDBList.add(i, db);
                            Log.e("Session " + i, db.getTitle());
                        }
                        catch(Exception e){
                            e.printStackTrace();
                            Log.e("Exception","If some posts are not properly created, ignore");
                        }
                    }

                    Collections.sort(sessionDBList, new Comparator<SessionDB>() {
                        @Override
                        public int compare(SessionDB lhs, SessionDB rhs) {
                            int lhstime = lhs.getTime();
                            int rhstime = rhs.getTime();
                            return lhstime - rhstime;
                        }
                    });

                    for (int i = 0; i < sessionDBList.size(); i++) {
                        Log.e("Session " + i, sessionDBList.get(i).getTitle());
                    }
            }
        });
    }
}
