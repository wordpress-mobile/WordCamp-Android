
package org.wordcamp.objects.wordcamp;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PostThumbnail {

    @Expose
    private String file;
    @Expose
    private int width;
    @Expose
    private int height;
    @SerializedName("mime-type")
    @Expose
    private String mimeType;
    @Expose
    private String url;

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
     *     The mimeType
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * 
     * @param mimeType
     *     The mime-type
     */
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    /**
     * 
     * @return
     *     The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 
     * @param url
     *     The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

}
