package org.wordcamp.networking;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by aagam on 14/1/15.
 */
public class WPAPIClient {
    private static final String BASE_URL = "http://central.wordcamp.dev/wp-json/";

    private static final String LOCAL = "http://192.168.0.102/myVagrant/index.php";

    private static final String QUERY_PARAM_SPEAKERS = "/wp-json/posts?type=wcb_speaker";

    private static final String QUERY_PARAM_WC = "central.wordcamp.dev/wp-json/posts?type=wordcamp&filter[order]=DESC&filter[orderby]=modified";

    private static final String QUERY_PARAM_SCHEDULE = "/wp-json/posts?type=wcb_session";

    private static final String QUERY_PARAM_SESSION = "2014.seattle.wordcamp.dev/wp-json/posts?filter[posts_per_page]=58&type=wcb_session";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void getWordCampsList(String date,JsonHttpResponseHandler responseHandler) {

        //There is no query yet to get WCs after modified date in WP-API, so not using date param currently
        RequestParams params1 = new RequestParams();
        params1.add("url",QUERY_PARAM_WC);
        client.get(LOCAL, params1, responseHandler);
    }

    public static void getWordCampSpeakers(String wordcampURL,JsonHttpResponseHandler responseHandler) {
        client.get(wordcampURL+QUERY_PARAM_SPEAKERS, null, responseHandler);
    }

    public static void getWordCampSchedule(String wordcampURL,JsonHttpResponseHandler responseHandler) {
        client.get(wordcampURL+QUERY_PARAM_SCHEDULE, null, responseHandler);
    }

    public static void getSession(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        RequestParams params1 = new RequestParams();
        params1.add("url",QUERY_PARAM_SESSION);

        client.get(LOCAL, params1, responseHandler);

    }
}