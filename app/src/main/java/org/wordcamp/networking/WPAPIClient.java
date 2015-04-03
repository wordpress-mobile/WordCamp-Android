package org.wordcamp.networking;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.wordcamp.BuildConfig;

/**
 * Created by aagam on 14/1/15.
 */
public class WPAPIClient {

    private static final String LOCAL = BuildConfig.URL_LOCAL;

    private static final String QUERY_PARAM_SPEAKERS = "/wp-json/posts?type=wcb_speaker&filter[posts_per_page]=100";

    private static final String QUERY_PARAM_WC = BuildConfig.URL_BETA;

    private static final String QUERY_PARAM_SCHEDULE = "/wp-json/posts?type=wcb_session&filter[order]=DESC&filter[orderby]=modified&filter[posts_per_page]=100";

    private static final String QUERY_PARAM_SINGLEWC = BuildConfig.URL_SINGLEWC;

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void getWordCampsList(String date,JsonHttpResponseHandler responseHandler) {

        //There is no query yet to get WCs after modified date in WP-API, so not using date param currently
        RequestParams params1 = new RequestParams();
        params1.add("url",QUERY_PARAM_WC);
        client.get(LOCAL, params1, responseHandler);
    }

    public static void getWordCampSpeakers(String wordcampURL,JsonHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.add("url",wordcampURL+QUERY_PARAM_SPEAKERS);
        client.get(LOCAL, params, responseHandler);
    }

    public static void getWordCampSchedule(String wordcampURL,JsonHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.add("url",wordcampURL+QUERY_PARAM_SCHEDULE);
        client.get(LOCAL, params, responseHandler);
    }

    public static void getSession(String url, AsyncHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.add("url",url+QUERY_PARAM_SCHEDULE);
        client.get(LOCAL, params, responseHandler);
    }

    public static void getSingleWC(int wcid ,AsyncHttpResponseHandler responseHandler){
        RequestParams params = new RequestParams();
        params.add("url",QUERY_PARAM_SINGLEWC+""+wcid);
        client.get(LOCAL,params,responseHandler);
    }


}