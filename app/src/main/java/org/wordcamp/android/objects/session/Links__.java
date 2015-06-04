
package org.wordcamp.android.objects.session;


import com.google.gson.annotations.Expose;


public class Links__ {

    @Expose
    private String collection;
    @Expose
    private String self;

    /**
     *
     * @return
     *     The collection
     */
    public String getCollection() {
        return collection;
    }

    /**
     *
     * @param collection
     *     The collection
     */
    public void setCollection(String collection) {
        this.collection = collection;
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
