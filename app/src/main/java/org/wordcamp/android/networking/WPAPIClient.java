package org.wordcamp.android.networking;

import android.content.Context;

import com.android.volley.Response;

import org.wordcamp.android.BuildConfig;
import org.wordcamp.android.WordCampApplication;
import org.wordcamp.android.objects.speaker.Session;
import org.wordcamp.android.objects.speaker.SpeakerNew;
import org.wordcamp.android.objects.wordcamp.WordCampNew;

/**
 * Created by aagam on 14/1/15.
 */
public class WPAPIClient {

    private static final String QUERY_PARAM_SPEAKERS = "wp-json/posts?type=wcb_speaker&filter[posts_per_page]=100";

    private static final String QUERY_PARAM_WORDCAMP_LIST = "wp-json/posts?type=wordcamp&filter[order]=DESC&filter[posts_per_page]=50";

    private static final String QUERY_PARAM_SCHEDULE = "wp-json/posts?type=wcb_session&filter[order]=DESC&filter[orderby]=modified&filter[posts_per_page]=100";

    private static final String QUERY_PARAM_SINGLEWC = "wp-json/posts/";

    private static String normalizeWordCampUrl(String wordcampURL) {
        if (!wordcampURL.endsWith("/")) {
            wordcampURL = wordcampURL + "/";
        }
        return wordcampURL;
    }

    public static void getAllWCs(Context context, Response.ErrorListener errorListener,
                                      ResponseListener responseListener){
        WCRequest request = new WCRequest(BuildConfig.CENTRAL_WORDCAMP_URL + QUERY_PARAM_WORDCAMP_LIST,
                WordCampNew[].class, errorListener, responseListener);
        request.setTag(context);
        WordCampApplication.requestQueue.cancelAll(context);
        WordCampApplication.requestQueue.add(request);
    }


    public static void getWordCampSpeakersVolley(String wordcampURL, Context context, Response.ErrorListener errorListener,
                                                 ResponseListener responseListener){
        WCRequest request = new WCRequest(normalizeWordCampUrl(wordcampURL) + QUERY_PARAM_SPEAKERS,
                SpeakerNew[].class, errorListener, responseListener);
        request.setTag(context);
        WordCampApplication.requestQueue.add(request);
    }

    public static void getWordCampScheduleVolley(String wordcampURL, Context context, Response.ErrorListener errorListener,
                                         ResponseListener responseListener){
        WCRequest request = new WCRequest(normalizeWordCampUrl(wordcampURL) + QUERY_PARAM_SCHEDULE,
                Session[].class, errorListener, responseListener);
        request.setTag(context);
        WordCampApplication.requestQueue.add(request);
    }

    public static void getSingleWCVolley(int wcid, Context context, Response.ErrorListener errorListener,
                                                 ResponseListener responseListener){
        WCRequest request = new WCRequest(BuildConfig.CENTRAL_WORDCAMP_URL + QUERY_PARAM_SINGLEWC + wcid,
                WordCampNew.class, errorListener, responseListener);
        request.setTag(context);
        WordCampApplication.requestQueue.add(request);
    }

    public static void cancelAllRequests(Context ctx) {
        WordCampApplication.requestQueue.cancelAll(ctx);
    }


}
