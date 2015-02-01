
package org.wordcamp.objects.wordcamp;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class FeaturedImage {

    @Expose
    private int ID;
    @Expose
    private String title;
    @Expose
    private String status;
    @Expose
    private String type;
    @Expose
    private Author_ author;
    @Expose
    private String content;
    @Expose
    private int parent;
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
    private Object excerpt;
    @SerializedName("menu_order")
    @Expose
    private int menuOrder;
    @SerializedName("comment_status")
    @Expose
    private String commentStatus;
    @SerializedName("ping_status")
    @Expose
    private String pingStatus;
    @Expose
    private boolean sticky;
    @SerializedName("date_gmt")
    @Expose
    private String dateGmt;
    @SerializedName("modified_gmt")
    @Expose
    private String modifiedGmt;
    @Expose
    private Meta___ meta;
    @Expose
    private Foo_ foo;
    @Expose
    private List<Object> terms = new ArrayList<Object>();
    @Expose
    private String source;
    @SerializedName("is_image")
    @Expose
    private boolean isImage;
    @SerializedName("attachment_meta")
    @Expose
    private AttachmentMeta attachmentMeta;

    /**
     * 
     * @return
     *     The ID
     */
    public int getID() {
        return ID;
    }

    /**
     * 
     * @param ID
     *     The ID
     */
    public void setID(int ID) {
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
    public Author_ getAuthor() {
        return author;
    }

    /**
     * 
     * @param author
     *     The author
     */
    public void setAuthor(Author_ author) {
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
    public int getParent() {
        return parent;
    }

    /**
     * 
     * @param parent
     *     The parent
     */
    public void setParent(int parent) {
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
     *     The menuOrder
     */
    public int getMenuOrder() {
        return menuOrder;
    }

    /**
     * 
     * @param menuOrder
     *     The menu_order
     */
    public void setMenuOrder(int menuOrder) {
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
    public boolean isSticky() {
        return sticky;
    }

    /**
     * 
     * @param sticky
     *     The sticky
     */
    public void setSticky(boolean sticky) {
        this.sticky = sticky;
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
    public Meta___ getMeta() {
        return meta;
    }

    /**
     * 
     * @param meta
     *     The meta
     */
    public void setMeta(Meta___ meta) {
        this.meta = meta;
    }

    /**
     * 
     * @return
     *     The foo
     */
    public Foo_ getFoo() {
        return foo;
    }

    /**
     * 
     * @param foo
     *     The foo
     */
    public void setFoo(Foo_ foo) {
        this.foo = foo;
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
     *     The source
     */
    public String getSource() {
        return source;
    }

    /**
     * 
     * @param source
     *     The source
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * 
     * @return
     *     The isImage
     */
    public boolean isIsImage() {
        return isImage;
    }

    /**
     * 
     * @param isImage
     *     The is_image
     */
    public void setIsImage(boolean isImage) {
        this.isImage = isImage;
    }

    /**
     * 
     * @return
     *     The attachmentMeta
     */
    public AttachmentMeta getAttachmentMeta() {
        return attachmentMeta;
    }

    /**
     * 
     * @param attachmentMeta
     *     The attachment_meta
     */
    public void setAttachmentMeta(AttachmentMeta attachmentMeta) {
        this.attachmentMeta = attachmentMeta;
    }

}
