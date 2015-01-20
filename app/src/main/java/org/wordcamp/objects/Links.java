
package org.wordcamp.objects;

import java.util.HashMap;
import java.util.Map;



public class Links {

    private String self;
    private String archives;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
