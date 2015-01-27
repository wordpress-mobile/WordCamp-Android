
package org.wordcamp.objects.session;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Foo {

    @SerializedName("_edit_last")
    @Expose
    private List<String> EditLast = new ArrayList<String>();
    @SerializedName("_wcb_session_speakers")
    @Expose
    private List<String> WcbSessionSpeakers = new ArrayList<String>();
    @SerializedName("_wcpt_session_time")
    @Expose
    private List<String> WcptSessionTime = new ArrayList<String>();
    @SerializedName("_wcpt_session_type")
    @Expose
    private List<String> WcptSessionType = new ArrayList<String>();
    @SerializedName("_wcpt_speaker_id")
    @Expose
    private List<String> WcptSpeakerId = new ArrayList<String>();
    @SerializedName("_edit_lock")
    @Expose
    private List<String> EditLock = new ArrayList<String>();

    /**
     * 
     * @return
     *     The EditLast
     */
    public List<String> getEditLast() {
        return EditLast;
    }

    /**
     * 
     * @param EditLast
     *     The _edit_last
     */
    public void setEditLast(List<String> EditLast) {
        this.EditLast = EditLast;
    }

    /**
     * 
     * @return
     *     The WcbSessionSpeakers
     */
    public List<String> getWcbSessionSpeakers() {
        return WcbSessionSpeakers;
    }

    /**
     * 
     * @param WcbSessionSpeakers
     *     The _wcb_session_speakers
     */
    public void setWcbSessionSpeakers(List<String> WcbSessionSpeakers) {
        this.WcbSessionSpeakers = WcbSessionSpeakers;
    }

    /**
     * 
     * @return
     *     The WcptSessionTime
     */
    public List<String> getWcptSessionTime() {
        return WcptSessionTime;
    }

    /**
     * 
     * @param WcptSessionTime
     *     The _wcpt_session_time
     */
    public void setWcptSessionTime(List<String> WcptSessionTime) {
        this.WcptSessionTime = WcptSessionTime;
    }

    /**
     * 
     * @return
     *     The WcptSessionType
     */
    public List<String> getWcptSessionType() {
        return WcptSessionType;
    }

    /**
     * 
     * @param WcptSessionType
     *     The _wcpt_session_type
     */
    public void setWcptSessionType(List<String> WcptSessionType) {
        this.WcptSessionType = WcptSessionType;
    }

    /**
     * 
     * @return
     *     The WcptSpeakerId
     */
    public List<String> getWcptSpeakerId() {
        return WcptSpeakerId;
    }

    /**
     * 
     * @param WcptSpeakerId
     *     The _wcpt_speaker_id
     */
    public void setWcptSpeakerId(List<String> WcptSpeakerId) {
        this.WcptSpeakerId = WcptSpeakerId;
    }

    /**
     * 
     * @return
     *     The EditLock
     */
    public List<String> getEditLock() {
        return EditLock;
    }

    /**
     * 
     * @param EditLock
     *     The _edit_lock
     */
    public void setEditLock(List<String> EditLock) {
        this.EditLock = EditLock;
    }

}
