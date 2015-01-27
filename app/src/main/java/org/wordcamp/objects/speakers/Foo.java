
package org.wordcamp.objects.speakers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class Foo {

    @SerializedName("_edit_last")
    @Expose
    private List<String> EditLast = new ArrayList<String>();

    public List<String> getSpeakerEmail() {
        return speakerEmail;
    }

    public void setSpeakerEmail(List<String> speakerEmail) {
        this.speakerEmail = speakerEmail;
    }

    public List<String> getPtUserId() {
        return ptUserId;
    }

    public void setPtUserId(List<String> ptUserId) {
        this.ptUserId = ptUserId;
    }

    @SerializedName("_wcb_speaker_email")
    @Expose
    private List<String> speakerEmail = new ArrayList<String>();

    @SerializedName("_wcpt_user_id")
    @Expose
    private List<String> ptUserId = new ArrayList<String>();

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

}
