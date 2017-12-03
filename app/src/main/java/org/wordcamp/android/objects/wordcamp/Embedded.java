
package org.wordcamp.android.objects.wordcamp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Embedded {
    @SerializedName("wp:term")
    @Expose
    private List<List<WpTerm>> wpTerm = null;

    @SerializedName("sessions")
    @Expose
    private List<Session> sessions = null;

    public List<List<WpTerm>> getWpTerm() {
        return wpTerm;
    }

    public void setWpTerm(List<List<WpTerm>> wpTerm) {
        this.wpTerm = wpTerm;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }
}
