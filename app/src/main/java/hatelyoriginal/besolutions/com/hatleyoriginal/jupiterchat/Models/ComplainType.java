
package hatelyoriginal.besolutions.com.hatleyoriginal.jupiterchat.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ComplainType {

    @SerializedName("compliantTypes")
    @Expose
    private List<CompliantType> compliantTypes = null;

    public List<CompliantType> getCompliantTypes() {
        return compliantTypes;
    }

    public void setCompliantTypes(List<CompliantType> compliantTypes) {
        this.compliantTypes = compliantTypes;
    }

}
