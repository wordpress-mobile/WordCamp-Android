
package org.wordcamp.objects;


import com.google.gson.annotations.Expose;

import java.io.Serializable;


public class Links_ implements Serializable {

    @Expose
    private String archives;
    @Expose
    private String self;

    /**
     * 
     * @return
     *     The archives
     */
    public String getArchives() {
        return archives;
    }

    /**
     * 
     * @param archives
     *     The archives
     */
    public void setArchives(String archives) {
        this.archives = archives;
    }

    /**
     * 
     * @return
     *     The self
     */
    public String getSelf() {
        return self;
    }

    /**
     * 
     * @param self
     *     The self
     */
    public void setSelf(String self) {
        this.self = self;
    }

}
