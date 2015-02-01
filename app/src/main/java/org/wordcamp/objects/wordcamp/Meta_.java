
package org.wordcamp.objects.wordcamp;


import com.google.gson.annotations.Expose;


public class Meta_ {

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
