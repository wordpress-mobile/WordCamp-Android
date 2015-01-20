package org.wordcamp.networking;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by aagam on 14/1/15.
 */
public class WPAPIClient {
    private static final String BASE_URL = "http://central.wordcamp.dev/wp-json/";

    private static final String LOCAL = "http://192.168.0.102/myVagrant";

    private static AsyncHttpClient client = new AsyncHttpClient();


    public static void get(String url, RequestParams params, JsonHttpResponseHandler responseHandler) {
        client.get(url, params, responseHandler);
    }

    public static void getWordCampsList(JsonHttpResponseHandler responseHandler) {
        client.get(LOCAL, null, responseHandler);
    }

    public static void post(String url, RequestParams params, JsonHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}