package org.wordcamp.android.networking;

import android.content.Context;

import com.android.volley.Response;

import org.wordcamp.android.BuildConfig;
import org.wordcamp.android.WordCampApplication;
import org.wordcamp.android.objects.wordcampv2.SessionV2;
import org.wordcamp.android.objects.wordcampv2.Speaker;
import org.wordcamp.android.objects.wordcampv2.WordCamp;

/**
 * Created by aagam on 14/1/15.
 */
public class WPAPIClient {

    private static final String QUERY_PARAM_SPEAKERS_V2 = "wp-json/wp/v2/speakers?per_page=100&_embed=true&status=publish";

    private static final String QUERY_PARAM_WORDCAMP_LIST_V2 = "wp-json/wp/v2/wordcamps?per_page=100&status=wcpt-scheduled";

    private static final String QUERY_PARAM_SCHEDULE_V2 = "wp-json/wp/v2/sessions?per_page=100&_embed=true&status=publish";

    private static final String QUERY_PARAM_SINGLEWC_V2 = "wp-json/wp/v2/wordcamps/";

    private static String normalizeWordCampUrl(String wordcampURL) {
        if (!wordcampURL.endsWith("/")) {
            wordcampURL = wordcampURL + "/";
        }
        return wordcampURL;
    }

    public static void getAllWCs(Context context, Response.ErrorListener errorListener,
                                      ResponseListener responseListener){
        WCRequest request = new WCRequest(BuildConfig.CENTRAL_WORDCAMP_URL + QUERY_PARAM_WORDCAMP_LIST_V2,
                WordCamp[].class, errorListener, responseListener);
        request.setTag(context);
        WordCampApplication.requestQueue.cancelAll(context);
        WordCampApplication.requestQueue.add(request);
    }


    public static void getWordCampSpeakersVolley(String wordcampURL, Context context, Response.ErrorListener errorListener,
                                                 ResponseListener responseListener){
        WCRequest request = new WCRequest(normalizeWordCampUrl(wordcampURL) + QUERY_PARAM_SPEAKERS_V2,
                Speaker[].class, errorListener, responseListener);
        request.setTag(context);
        WordCampApplication.requestQueue.add(request);
    }

    public static void getWordCampScheduleVolley(String wordcampURL, Context context, Response.ErrorListener errorListener,
                                         ResponseListener responseListener){
        WCRequest request = new WCRequest(normalizeWordCampUrl(wordcampURL) + QUERY_PARAM_SCHEDULE_V2,
                SessionV2[].class, errorListener, responseListener);
        request.setTag(context);
        WordCampApplication.requestQueue.add(request);
    }

    public static void getSingleWCVolley(int wcid, Context context, Response.ErrorListener errorListener,
                                                 ResponseListener responseListener){
        WCRequest request = new WCRequest(BuildConfig.CENTRAL_WORDCAMP_URL + QUERY_PARAM_SINGLEWC_V2 + wcid,
                WordCamp.class, errorListener, responseListener);
        request.setTag(context);
        WordCampApplication.requestQueue.add(request);
    }

    public static void cancelAllRequests(Context ctx) {
        WordCampApplication.requestQueue.cancelAll(ctx);
    }


}
