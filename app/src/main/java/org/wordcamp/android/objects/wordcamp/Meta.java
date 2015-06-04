
package org.wordcamp.android.objects.wordcamp;

import com.google.gson.annotations.Expose;


public class Meta {

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
