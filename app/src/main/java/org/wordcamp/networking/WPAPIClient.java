package org.wordcamp.networking;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.wordcamp.BuildConfig;

/**
 * Created by aagam on 14/1/15.
 */
public class WPAPIClient {

    private static final String LOCAL = "";

    private static final String QUERY_PARAM_SPEAKERS = "wp-json/posts?type=wcb_speaker&filter[posts_per_page]=100";

    private static final String QUERY_PARAM_WORDCAMP_LIST = "wp-json/posts?type=wordcamp";

    private static final String QUERY_PARAM_SCHEDULE = "wp-json/posts?type=wcb_session&filter[order]=DESC&filter[orderby]=modified&filter[posts_per_page]=100";

    private static final String QUERY_PARAM_SINGLEWC = "";

    //This config of client accepts all SSL connections
    private static AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);

    private static String getListWordCampEndpoint() {
        return BuildConfig.LIST_WORDCAMP_URL + QUERY_PARAM_WORDCAMP_LIST;
    }

    public static void getWordCampsList(Context ctx, String date, JsonHttpResponseHandler responseHandler) {
        client.get(ctx, getListWordCampEndpoint(), responseHandler);
    }

    public static void getWordCampSpeakers(Context ctx, String wordcampURL, JsonHttpResponseHandler responseHandler) {
        if (!wordcampURL.endsWith("/")) {
            wordcampURL = wordcampURL + "/";
        }
        client.setEnableRedirects(true, true, true);
        client.get(ctx, wordcampURL + QUERY_PARAM_SPEAKERS, responseHandler);
    }

    public static void getWordCampSchedule(Context ctx, String wordcampURL, JsonHttpResponseHandler responseHandler) {
        if (!wordcampURL.endsWith("/")) {
            wordcampURL = wordcampURL + "/";
        }
        client.setEnableRedirects(true, true, true);
        client.get(ctx, wordcampURL + QUERY_PARAM_SCHEDULE, responseHandler);
    }

    public static void getSession(Context ctx, String url, AsyncHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.add("url", url + QUERY_PARAM_SCHEDULE);
        client.setEnableRedirects(true, true, true);
        client.get(ctx, LOCAL, params, responseHandler);
    }

    public static void getSingleWC(Context ctx, int wcid, AsyncHttpResponseHandler responseHandler) {
        client.setEnableRedirects(true, true, true);
        client.get(ctx, QUERY_PARAM_SINGLEWC+""+wcid, responseHandler);
    }

    public static void cancelAllRequests(Context ctx) {
        client.cancelRequests(ctx, true);
    }


}
