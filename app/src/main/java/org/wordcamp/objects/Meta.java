
package org.wordcamp.objects;


import com.google.gson.annotations.Expose;

import java.io.Serializable;


public class Meta implements Serializable {

    @Expose
    private Links links;

    /**
     * 
     * @return
     *     The links
     */
    public Links getLinks() {
        return links;
    }

    /**
     * 
     * @param links
     *     The links
     */
    public void setLinks(Links links) {
        this.links = links;
    }

}
