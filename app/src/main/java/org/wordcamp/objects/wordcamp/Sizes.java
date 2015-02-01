
package org.wordcamp.objects.wordcamp;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Sizes {

    @Expose
    private Thumbnail thumbnail;
    @Expose
    private Medium medium;
    @SerializedName("wccsp_image_medium_rectangle")
    @Expose
    private WccspImageMediumRectangle wccspImageMediumRectangle;
    @SerializedName("post-thumbnail")
    @Expose
    private PostThumbnail postThumbnail;

    /**
     * 
     * @return
     *     The thumbnail
     */
    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    /**
     * 
     * @param thumbnail
     *     The thumbnail
     */
    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    /**
     * 
     * @return
     *     The medium
     */
    public Medium getMedium() {
        return medium;
    }

    /**
     * 
     * @param medium
     *     The medium
     */
    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    /**
     * 
     * @return
     *     The wccspImageMediumRectangle
     */
    public WccspImageMediumRectangle getWccspImageMediumRectangle() {
        return wccspImageMediumRectangle;
    }

    /**
     * 
     * @param wccspImageMediumRectangle
     *     The wccsp_image_medium_rectangle
     */
    public void setWccspImageMediumRectangle(WccspImageMediumRectangle wccspImageMediumRectangle) {
        this.wccspImageMediumRectangle = wccspImageMediumRectangle;
    }

    /**
     * 
     * @return
     *     The postThumbnail
     */
    public PostThumbnail getPostThumbnail() {
        return postThumbnail;
    }

    /**
     * 
     * @param postThumbnail
     *     The post-thumbnail
     */
    public void setPostThumbnail(PostThumbnail postThumbnail) {
        this.postThumbnail = postThumbnail;
    }

}
