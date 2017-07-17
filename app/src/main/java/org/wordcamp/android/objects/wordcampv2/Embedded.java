
package org.wordcamp.android.objects.wordcampv2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Embedded {
    @SerializedName("wp:term")
    @Expose
    private List<List<WpTerm>> wpTerm = null;

    @SerializedName("sessions")
    @Expose
    private List<SessionV2> sessions = null;

    public List<List<WpTerm>> getWpTerm() {
        return wpTerm;
    }

    public void setWpTerm(List<List<WpTerm>> wpTerm) {
        this.wpTerm = wpTerm;
    }

    public List<SessionV2> getSessions() {
        return sessions;
    }

    public void setSessions(List<SessionV2> sessions) {
        this.sessions = sessions;
    }
}
