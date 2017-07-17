
package org.wordcamp.android.objects.wordcampv2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AvatarUrls {

    @SerializedName("24")
    @Expose
    private String _24;
    @SerializedName("48")
    @Expose
    private String _48;
    @SerializedName("96")
    @Expose
    private String _96;

    public String get24() {
        return _24;
    }

    public void set24(String _24) {
        this._24 = _24;
    }

    public String get48() {
        return _48;
    }

    public void set48(String _48) {
        this._48 = _48;
    }

    public String get96() {
        return _96;
    }

    public void set96(String _96) {
        this._96 = _96;
    }

}
