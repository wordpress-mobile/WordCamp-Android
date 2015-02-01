
package org.wordcamp.objects.wordcamp;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AttachmentMeta {

    @Expose
    private int width;
    @Expose
    private int height;
    @Expose
    private String file;
    @Expose
    private Sizes sizes;
    @SerializedName("image_meta")
    @Expose
    private ImageMeta imageMeta;

    /**
     * 
     * @return
     *     The width
     */
    public int getWidth() {
        return width;
    }

    /**
     * 
     * @param width
     *     The width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * 
     * @return
     *     The height
     */
    public int getHeight() {
        return height;
    }

    /**
     * 
     * @param height
     *     The height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * 
     * @return
     *     The file
     */
    public String getFile() {
        return file;
    }

    /**
     * 
     * @param file
     *     The file
     */
    public void setFile(String file) {
        this.file = file;
    }

    /**
     * 
     * @return
     *     The sizes
     */
    public Sizes getSizes() {
        return sizes;
    }

    /**
     * 
     * @param sizes
     *     The sizes
     */
    public void setSizes(Sizes sizes) {
        this.sizes = sizes;
    }

    /**
     * 
     * @return
     *     The imageMeta
     */
    public ImageMeta getImageMeta() {
        return imageMeta;
    }

    /**
     * 
     * @param imageMeta
     *     The image_meta
     */
    public void setImageMeta(ImageMeta imageMeta) {
        this.imageMeta = imageMeta;
    }

}
