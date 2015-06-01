package org.wordcamp.networking;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.wordcamp.BuildConfig;

/**
 * Created by aagam on 14/1/15.
 */
public class WPAPIClient {

    private static final String QUERY_PARAM_SPEAKERS = "wp-json/posts?type=wcb_speaker&filter[posts_per_page]=100";

    private static final String QUERY_PARAM_WORDCAMP_LIST = "wp-json/posts?type=wordcamp&filter[order]=DESC&filter[posts_per_page]=50";

    private static final String QUERY_PARAM_SCHEDULE = "wp-json/posts?type=wcb_session&filter[order]=DESC&filter[orderby]=modified&filter[posts_per_page]=100";

    private static final String QUERY_PARAM_SINGLEWC = "wp-json/posts/";

    // This config of client accepts all SSL connections
    private static AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);

    private static String normalizeWordCampUrl(String wordcampURL) {
        if (!wordcampURL.endsWith("/")) {
            wordcampURL = wordcampURL + "/";
        }
        return wordcampURL;
    }

    public static void getWordCampsList(Context ctx, String date, JsonHttpResponseHandler responseHandler) {
        client.get(ctx, BuildConfig.CENTRAL_WORDCAMP_URL + QUERY_PARAM_WORDCAMP_LIST, responseHandler);
    }

    public static void getWordCampSpeakers(Context ctx, String wordcampURL, JsonHttpResponseHandler responseHandler) {
        client.setEnableRedirects(true, true, true);
        client.get(ctx, normalizeWordCampUrl(wordcampURL) + QUERY_PARAM_SPEAKERS, responseHandler);
    }

    public static void getWordCampSchedule(Context ctx, String wordcampURL, JsonHttpResponseHandler responseHandler) {
        client.setEnableRedirects(true, true, true);
        client.get(ctx, normalizeWordCampUrl(wordcampURL) + QUERY_PARAM_SCHEDULE, responseHandler);
    }

    public static void getSingleWC(Context ctx, int wcid, AsyncHttpResponseHandler responseHandler) {
        client.setEnableRedirects(true, true, true);
        client.get(ctx, BuildConfig.CENTRAL_WORDCAMP_URL + QUERY_PARAM_SINGLEWC + wcid, responseHandler);
    }

    public static void cancelAllRequests(Context ctx) {
        client.cancelRequests(ctx, true);
    }
}
