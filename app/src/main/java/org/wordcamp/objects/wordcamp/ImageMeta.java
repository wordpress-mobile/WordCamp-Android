
package org.wordcamp.objects.wordcamp;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ImageMeta {

    @Expose
    private int aperture;
    @Expose
    private String credit;
    @Expose
    private String camera;
    @Expose
    private String caption;
    @SerializedName("created_timestamp")
    @Expose
    private int createdTimestamp;
    @Expose
    private String copyright;
    @SerializedName("focal_length")
    @Expose
    private int focalLength;
    @Expose
    private int iso;
    @SerializedName("shutter_speed")
    @Expose
    private int shutterSpeed;
    @Expose
    private String title;
    @Expose
    private int orientation;

    /**
     * 
     * @return
     *     The aperture
     */
    public int getAperture() {
        return aperture;
    }

    /**
     * 
     * @param aperture
     *     The aperture
     */
    public void setAperture(int aperture) {
        this.aperture = aperture;
    }

    /**
     * 
     * @return
     *     The credit
     */
    public String getCredit() {
        return credit;
    }

    /**
     * 
     * @param credit
     *     The credit
     */
    public void setCredit(String credit) {
        this.credit = credit;
    }

    /**
     * 
     * @return
     *     The camera
     */
    public String getCamera() {
        return camera;
    }

    /**
     * 
     * @param camera
     *     The camera
     */
    public void setCamera(String camera) {
        this.camera = camera;
    }

    /**
     * 
     * @return
     *     The caption
     */
    public String getCaption() {
        return caption;
    }

    /**
     * 
     * @param caption
     *     The caption
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }

    /**
     * 
     * @return
     *     The createdTimestamp
     */
    public int getCreatedTimestamp() {
        return createdTimestamp;
    }

    /**
     * 
     * @param createdTimestamp
     *     The created_timestamp
     */
    public void setCreatedTimestamp(int createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    /**
     * 
     * @return
     *     The copyright
     */
    public String getCopyright() {
        return copyright;
    }

    /**
     * 
     * @param copyright
     *     The copyright
     */
    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    /**
     * 
     * @return
     *     The focalLength
     */
    public int getFocalLength() {
        return focalLength;
    }

    /**
     * 
     * @param focalLength
     *     The focal_length
     */
    public void setFocalLength(int focalLength) {
        this.focalLength = focalLength;
    }

    /**
     * 
     * @return
     *     The iso
     */
    public int getIso() {
        return iso;
    }

    /**
     * 
     * @param iso
     *     The iso
     */
    public void setIso(int iso) {
        this.iso = iso;
    }

    /**
     * 
     * @return
     *     The shutterSpeed
     */
    public int getShutterSpeed() {
        return shutterSpeed;
    }

    /**
     * 
     * @param shutterSpeed
     *     The shutter_speed
     */
    public void setShutterSpeed(int shutterSpeed) {
        this.shutterSpeed = shutterSpeed;
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
     *     The orientation
     */
    public int getOrientation() {
        return orientation;
    }

    /**
     * 
     * @param orientation
     *     The orientation
     */
    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

}
