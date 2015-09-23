package org.wordcamp.android.networking;



/**
 * Created by shah.aagam on 30/07/15.
 */
public interface ResponseListener<T> {

    public void onResponseReceived(T t);
}
