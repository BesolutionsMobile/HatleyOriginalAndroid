package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.GetOffersScenario.Models;//
//  MainOffersResponse.java
//  Model Generated using http://www.jsoncafe.com/ 
//  Created on December 1, 2019

import org.json.*;
import java.util.*;
import com.google.gson.annotations.SerializedName;


public class MainOffersResponse{

	@SerializedName("offers")
	private MainOffer[] offers;

	public void setOffers(MainOffer[] offers){
		this.offers = offers;
	}
	public MainOffer[] getOffers(){
		return this.offers;
	}

	/**
	 * Instantiate the instance using the passed jsonObject to set the properties values
	 */
	public MainOffersResponse(JSONObject jsonObject){
		if(jsonObject == null){
			return;
		}
		JSONArray offersJsonArray = jsonObject.optJSONArray("offers");
		if(offersJsonArray != null){
			ArrayList<MainOffer> offersArrayList = new ArrayList<>();
			for (int i = 0; i < offersJsonArray.length(); i++) {
				JSONObject offersObject = offersJsonArray.optJSONObject(i);
				offersArrayList.add(new MainOffer(offersObject));
			}
			offers = (MainOffer[]) offersArrayList.toArray();
		}
	}

	/**
	 * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
	 */
	public JSONObject toJsonObject()
	{
		JSONObject jsonObject = new JSONObject();
		try {
			if(offers != null && offers.length > 0){
				JSONArray offersJsonArray = new JSONArray();
				for(MainOffer offersElement : offers){
					offersJsonArray.put(offersElement.toJsonObject());
				}
				jsonObject.put("offers", offersJsonArray);
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

}