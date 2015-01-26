
package org.wordcamp.objects.speakers;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Foo {

    @SerializedName("_edit_last")
    @Expose
    private List<String> EditLast = new ArrayList<String>();

    /**
     * 
     * @return
     *     The EditLast
     */
    public List<String> getEditLast() {
        return EditLast;
    }

    /**
     * 
     * @param EditLast
     *     The _edit_last
     */
    public void setEditLast(List<String> EditLast) {
        this.EditLast = EditLast;
    }

}
