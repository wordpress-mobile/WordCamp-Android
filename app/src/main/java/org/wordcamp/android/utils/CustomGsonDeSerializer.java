package org.wordcamp.android.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.wordcamp.android.objects.speaker.Terms;

import java.lang.reflect.Type;


/**
 * Created by aagam on 15/5/15.
 */
public class CustomGsonDeSerializer implements JsonDeserializer<Terms> {

    @Override
    public Terms deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        //Terms object when null returns an empty array rather then empty list
        if (json instanceof JsonArray) {
            return null;
        } else {
            Gson gson = new Gson();
            return gson.fromJson(json,Terms.class);
        }
    }
}
