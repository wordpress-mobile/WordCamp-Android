
package org.wordcamp.objects.wordcamp;


import com.google.gson.annotations.Expose;

import java.io.Serializable;


public class Meta_ implements Serializable {

    @Expose
    private Links_ links;

    /**
     * 
     * @return
     *     The links
     */
    public Links_ getLinks() {
        return links;
    }

    /**
     * 
     * @param links
     *     The links
     */
    public void setLinks(Links_ links) {
        this.links = links;
    }

}
