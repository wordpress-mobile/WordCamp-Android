
package org.wordcamp.objects.speaker;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Terms {

    @SerializedName("wcb_track")
    @Expose
    private List<WcbTrack> wcbTrack = new ArrayList<WcbTrack>();

    /**
     * 
     * @return
     *     The wcbTrack
     */
    public List<WcbTrack> getWcbTrack() {
        return wcbTrack;
    }

    /**
     * 
     * @param wcbTrack
     *     The wcb_track
     */
    public void setWcbTrack(List<WcbTrack> wcbTrack) {
        this.wcbTrack = wcbTrack;
    }

}
