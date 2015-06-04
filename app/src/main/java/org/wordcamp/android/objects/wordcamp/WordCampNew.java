
package org.wordcamp.android.objects.wordcamp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class WordCampNew {

    @Expose
    private Integer ID;
    @Expose
    private String title;
    @Expose
    private String status;
    @Expose
    private String type;
    @Expose
    private Author author;
    @Expose
    private String content;
    @Expose
    private Object parent;
    @Expose
    private String link;
    @Expose
    private String date;
    @Expose
    private String modified;
    @Expose
    private String format;
    @Expose
    private String slug;
    @Expose
    private String guid;
    @Expose
    private String excerpt;
    @SerializedName("menu_order")
    @Expose
    private Integer menuOrder;
    @SerializedName("comment_status")
    @Expose
    private String commentStatus;
    @SerializedName("ping_status")
    @Expose
    private String pingStatus;
    @Expose
    private Boolean sticky;
    @SerializedName("date_tz")
    @Expose
    private String dateTz;
    @SerializedName("date_gmt")
    @Expose
    private String dateGmt;
    @SerializedName("modified_tz")
    @Expose
    private String modifiedTz;
    @SerializedName("modified_gmt")
    @Expose
    private String modifiedGmt;
    @Expose
    private Meta_ meta;
    @Expose
    private List<Object> terms = new ArrayList<Object>();
    @SerializedName("post_meta")
    @Expose
    private List<PostMetum> postMeta = new ArrayList<PostMetum>();

    /**
     *
     * @return
     *     The ID
     */
    public Integer getID() {
        return ID;
    }

    /**
     *
     * @param ID
     *     The ID
     */
    public void setID(Integer ID) {
        this.ID = ID;
    }

    /**
     *
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     *     The author
     */
    public Author getAuthor() {
        return author;
    }

    /**
     *
     * @param author
     *     The author
     */
    public void setAuthor(Author author) {
        this.author = author;
    }

    /**
     *
     * @return
     *     The content
     */
    public String getContent() {
        return content;
    }

    /**
     *
     * @param content
     *     The content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     *
     * @return
     *     The parent
     */
    public Object getParent() {
        return parent;
    }

    /**
     *
     * @param parent
     *     The parent
     */
    public void setParent(Object parent) {
        this.parent = parent;
    }

    /**
     *
     * @return
     *     The link
     */
    public String getLink() {
        return link;
    }

    /**
     *
     * @param link
     *     The link
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     *
     * @return
     *     The date
     */
    public String getDate() {
        return date;
    }

    /**
     *
     * @param date
     *     The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     *
     * @return
     *     The modified
     */
    public String getModified() {
        return modified;
    }

    /**
     *
     * @param modified
     *     The modified
     */
    public void setModified(String modified) {
        this.modified = modified;
    }

    /**
     *
     * @return
     *     The format
     */
    public String getFormat() {
        return format;
    }

    /**
     *
     * @param format
     *     The format
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     *
     * @return
     *     The slug
     */
    public String getSlug() {
        return slug;
    }

    /**
     *
     * @param slug
     *     The slug
     */
    public void setSlug(String slug) {
        this.slug = slug;
    }

    /**
     *
     * @return
     *     The guid
     */
    public String getGuid() {
        return guid;
    }

    /**
     *
     * @param guid
     *     The guid
     */
    public void setGuid(String guid) {
        this.guid = guid;
    }

    /**
     *
     * @return
     *     The excerpt
     */
    public String getExcerpt() {
        return excerpt;
    }

    /**
     *
     * @param excerpt
     *     The excerpt
     */
    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    /**
     *
     * @return
     *     The menuOrder
     */
    public Integer getMenuOrder() {
        return menuOrder;
    }

    /**
     *
     * @param menuOrder
     *     The menu_order
     */
    public void setMenuOrder(Integer menuOrder) {
        this.menuOrder = menuOrder;
    }

    /**
     *
     * @return
     *     The commentStatus
     */
    public String getCommentStatus() {
        return commentStatus;
    }

    /**
     *
     * @param commentStatus
     *     The comment_status
     */
    public void setCommentStatus(String commentStatus) {
        this.commentStatus = commentStatus;
    }

    /**
     *
     * @return
     *     The pingStatus
     */
    public String getPingStatus() {
        return pingStatus;
    }

    /**
     *
     * @param pingStatus
     *     The ping_status
     */
    public void setPingStatus(String pingStatus) {
        this.pingStatus = pingStatus;
    }

    /**
     *
     * @return
     *     The sticky
     */
    public Boolean getSticky() {
        return sticky;
    }

    /**
     *
     * @param sticky
     *     The sticky
     */
    public void setSticky(Boolean sticky) {
        this.sticky = sticky;
    }

    /**
     *
     * @return
     *     The dateTz
     */
    public String getDateTz() {
        return dateTz;
    }

    /**
     *
     * @param dateTz
     *     The date_tz
     */
    public void setDateTz(String dateTz) {
        this.dateTz = dateTz;
    }

    /**
     *
     * @return
     *     The dateGmt
     */
    public String getDateGmt() {
        return dateGmt;
    }

    /**
     *
     * @param dateGmt
     *     The date_gmt
     */
    public void setDateGmt(String dateGmt) {
        this.dateGmt = dateGmt;
    }

    /**
     *
     * @return
     *     The modifiedTz
     */
    public String getModifiedTz() {
        return modifiedTz;
    }

    /**
     *
     * @param modifiedTz
     *     The modified_tz
     */
    public void setModifiedTz(String modifiedTz) {
        this.modifiedTz = modifiedTz;
    }

    /**
     *
     * @return
     *     The modifiedGmt
     */
    public String getModifiedGmt() {
        return modifiedGmt;
    }

    /**
     *
     * @param modifiedGmt
     *     The modified_gmt
     */
    public void setModifiedGmt(String modifiedGmt) {
        this.modifiedGmt = modifiedGmt;
    }

    /**
     *
     * @return
     *     The meta
     */
    public Meta_ getMeta() {
        return meta;
    }

    /**
     *
     * @param meta
     *     The meta
     */
    public void setMeta(Meta_ meta) {
        this.meta = meta;
    }

    /**
     *
     * @return
     *     The terms
     */
    public List<Object> getTerms() {
        return terms;
    }

    /**
     *
     * @param terms
     *     The terms
     */
    public void setTerms(List<Object> terms) {
        this.terms = terms;
    }

    /**
     *
     * @return
     *     The postMeta
     */
    public List<PostMetum> getPostMeta() {
        return postMeta;
    }

    /**
     *
     * @param postMeta
     *     The post_meta
     */
    public void setPostMeta(List<PostMetum> postMeta) {
        this.postMeta = postMeta;
    }

}
