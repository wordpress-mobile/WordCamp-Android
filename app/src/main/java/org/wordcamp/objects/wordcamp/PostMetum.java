
package org.wordcamp.objects.wordcamp;

import com.google.gson.annotations.Expose;


public class PostMetum {

    @Expose
    private Integer ID;
    @Expose
    private String key;
    @Expose
    private String value;

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
     *     The key
     */
    public String getKey() {
        return key;
    }

    /**
     * 
     * @param key
     *     The key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 
     * @return
     *     The value
     */
    public String getValue() {
        return value;
    }

    /**
     * 
     * @param value
     *     The value
     */
    public void setValue(String value) {
        this.value = value;
    }

}
