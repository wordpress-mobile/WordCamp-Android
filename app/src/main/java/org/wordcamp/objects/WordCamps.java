
package org.wordcamp.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class WordCamps implements Serializable {

    @Expose
    private String status;
    @Expose
    private Object excerpt;
    @Expose
    private List<Object> terms = new ArrayList<Object>();
    @SerializedName("featured_image")
    @Expose
    private Object featuredImage;
    @SerializedName("comment_status")
    @Expose
    private String commentStatus;
    @Expose
    private String link;
    @Expose
    private Object parent;
    @Expose
    private Foo foo;
    @Expose
    private String format;
    @SerializedName("modified_gmt")
    @Expose
    private String modifiedGmt;
    @Expose
    private String date;
    @Expose
    private String type;
    @Expose
    private Meta meta;
    @Expose
    private String modified;
    @Expose
    private String guid;
    @Expose
    private String content;
    @Expose
    private Author author;
    @Expose
    private Boolean sticky;
    @Expose
    private String title;
    @SerializedName("menu_order")
    @Expose
    private Integer menuOrder;
    @Expose
    private Integer ID;
    @SerializedName("date_gmt")
    @Expose
    private String dateGmt;
    @SerializedName("ping_status")
    @Expose
    private String pingStatus;
    @Expose
    private String slug;

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
     *     The excerpt
     */
    public Object getExcerpt() {
        return excerpt;
    }

    /**
     * 
     * @param excerpt
     *     The excerpt
     */
    public void setExcerpt(Object excerpt) {
        this.excerpt = excerpt;
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
     *     The featuredImage
     */
    public Object getFeaturedImage() {
        return featuredImage;
    }

    /**
     * 
     * @param featuredImage
     *     The featured_image
     */
    public void setFeaturedImage(Object featuredImage) {
        this.featuredImage = featuredImage;
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
     *     The foo
     */
    public Foo getFoo() {
        return foo;
    }

    /**
     * 
     * @param foo
     *     The foo
     */
    public void setFoo(Foo foo) {
        this.foo = foo;
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
     *     The meta
     */
    public Meta getMeta() {
        return meta;
    }

    /**
     * 
     * @param meta
     *     The meta
     */
    public void setMeta(Meta meta) {
        this.meta = meta;
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

}
