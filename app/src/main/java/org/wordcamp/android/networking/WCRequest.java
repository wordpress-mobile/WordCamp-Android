package org.wordcamp.android.networking;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpStatus;

import org.wordcamp.android.objects.speaker.Terms;
import org.wordcamp.android.utils.CustomGsonDeSerializer;

import java.io.UnsupportedEncodingException;

/**
 * Created by shah.aagam on 30/07/15.
 */
public class WCRequest<T> extends Request<T> {

    private final Gson gson;
    private ResponseListener<T> responseListener;
    private Class<T> requestType;

    public WCRequest(String url, Class<T> typeResponse, Response.ErrorListener listener, ResponseListener<T> responseListener) {
        super(Method.GET, url, listener);
        this.responseListener = responseListener;

        gson = new GsonBuilder().registerTypeHierarchyAdapter(Terms.class,
                new CustomGsonDeSerializer()).create();

        requestType = typeResponse;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        if (response.statusCode == HttpStatus.SC_OK) {
            try {
                String json = new String(
                        response.data, HttpHeaderParser.parseCharset(response.headers));
                return Response.success(
                        gson.fromJson(json, requestType), HttpHeaderParser.parseCacheHeaders(response));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return Response.error(new VolleyError(response));
            }
        } else {
            return Response.error(new VolleyError(response));
        }

    }



    @Override
    public Priority getPriority() {
        return Priority.IMMEDIATE;
    }

    @Override
    protected void deliverResponse(T response) {
        responseListener.onResponseReceived(response);
    }
}
