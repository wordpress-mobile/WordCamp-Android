
package org.wordcamp.objects.wordcamp;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Foo_ {

    @SerializedName("_wp_attached_file")
    @Expose
    private List<String> WpAttachedFile = new ArrayList<String>();
    @SerializedName("_wp_attachment_metadata")
    @Expose
    private List<String> WpAttachmentMetadata = new ArrayList<String>();

    /**
     * 
     * @return
     *     The WpAttachedFile
     */
    public List<String> getWpAttachedFile() {
        return WpAttachedFile;
    }

    /**
     * 
     * @param WpAttachedFile
     *     The _wp_attached_file
     */
    public void setWpAttachedFile(List<String> WpAttachedFile) {
        this.WpAttachedFile = WpAttachedFile;
    }

    /**
     * 
     * @return
     *     The WpAttachmentMetadata
     */
    public List<String> getWpAttachmentMetadata() {
        return WpAttachmentMetadata;
    }

    /**
     * 
     * @param WpAttachmentMetadata
     *     The _wp_attachment_metadata
     */
    public void setWpAttachmentMetadata(List<String> WpAttachmentMetadata) {
        this.WpAttachmentMetadata = WpAttachmentMetadata;
    }

}
