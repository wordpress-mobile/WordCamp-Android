
package org.wordcamp.objects.wordcamp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Links_ {

    @Expose
    private String self;
    @Expose
    private String author;
    @Expose
    private String collection;
    @Expose
    private String replies;
    @SerializedName("version-history")
    @Expose
    private String versionHistory;

    /**
     * 
     * @return
     *     The self
     */
    public String getSelf() {
        return self;
    }

    /**
     * 
     * @param self
     *     The self
     */
    public void setSelf(String self) {
        this.self = self;
    }

    /**
     * 
     * @return
     *     The author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * 
     * @param author
     *     The author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * 
     * @return
     *     The collection
     */
    public String getCollection() {
        return collection;
    }

    /**
     * 
     * @param collection
     *     The collection
     */
    public void setCollection(String collection) {
        this.collection = collection;
    }

    /**
     * 
     * @return
     *     The replies
     */
    public String getReplies() {
        return replies;
    }

    /**
     * 
     * @param replies
     *     The replies
     */
    public void setReplies(String replies) {
        this.replies = replies;
    }

    /**
     * 
     * @return
     *     The versionHistory
     */
    public String getVersionHistory() {
        return versionHistory;
    }

    /**
     * 
     * @param versionHistory
     *     The version-history
     */
    public void setVersionHistory(String versionHistory) {
        this.versionHistory = versionHistory;
    }

}
