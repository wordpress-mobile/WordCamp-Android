
package org.wordcamp.android.objects.wordcampv2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("_wcpt_session_time")
    @Expose
    private long wcptSessionTime;
    @SerializedName("_wcpt_session_type")
    @Expose
    private String wcptSessionType;
    @SerializedName("_wcpt_session_slides")
    @Expose
    private String wcptSessionSlides;
    @SerializedName("_wcpt_session_video")
    @Expose
    private String wcptSessionVideo;

    public long getWcptSessionTime() {
        return wcptSessionTime;
    }

    public void setWcptSessionTime(long wcptSessionTime) {
        this.wcptSessionTime = wcptSessionTime;
    }

    public String getWcptSessionType() {
        return wcptSessionType;
    }

    public void setWcptSessionType(String wcptSessionType) {
        this.wcptSessionType = wcptSessionType;
    }

    public String getWcptSessionSlides() {
        return wcptSessionSlides;
    }

    public void setWcptSessionSlides(String wcptSessionSlides) {
        this.wcptSessionSlides = wcptSessionSlides;
    }

    public String getWcptSessionVideo() {
        return wcptSessionVideo;
    }

    public void setWcptSessionVideo(String wcptSessionVideo) {
        this.wcptSessionVideo = wcptSessionVideo;
    }

}
