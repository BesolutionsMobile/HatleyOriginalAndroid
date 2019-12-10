
package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.GetOffersScenario.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Offers {

    @SerializedName("offers")
    @Expose
    private List<MainOffer> offers = null;

    public List<MainOffer> getOffers() {
        return offers;
    }

    public void setOffers(List<MainOffer> offers) {
        this.offers = offers;
    }

}
