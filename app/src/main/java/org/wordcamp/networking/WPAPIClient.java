package org.wordcamp.networking;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

/**
 * Created by aagam on 14/1/15.
 */
public class WPAPIClient {
    private static final String BASE_URL = "http://central.wordcamp.dev/wp-json/";

    private static final String LOCAL = "http://192.168.0.102/myVagrant";

    private static final String QUERY_PARAM_SPEAKERS = "/wp-json/posts?type=wcb_speaker";

    private static final String QUERY_PARAM_SCHEDULE = "/wp-json/posts?type=wcb_session";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void getWordCampsList(String date,JsonHttpResponseHandler responseHandler) {
        client.get(LOCAL, null, responseHandler);
    }

    public static void getWordCampSpeakers(String wordcampURL,JsonHttpResponseHandler responseHandler) {
        client.get(wordcampURL+QUERY_PARAM_SPEAKERS, null, responseHandler);
    }

    public static void getWordCampSchedule(String wordcampURL,JsonHttpResponseHandler responseHandler) {
        client.get(wordcampURL+QUERY_PARAM_SCHEDULE, null, responseHandler);
    }
}