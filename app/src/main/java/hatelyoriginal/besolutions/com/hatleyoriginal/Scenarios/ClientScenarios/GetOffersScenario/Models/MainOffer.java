package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.GetOffersScenario.Models;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;


public class MainOffer{

	@SerializedName("created_at")
	private String createdAt;
	@SerializedName("duration")
	private int duration;
	@SerializedName("expected_delivery_time")
	private String expectedDeliveryTime;
	@SerializedName("id")
	private int id;
	@SerializedName("offer_star")
	private MainOfferStar offerStar;
	@SerializedName("offer_value")
	private double offerValue;
	@SerializedName("order_id")
	private int orderId;
	@SerializedName("star_id")
	private int starId;
	@SerializedName("status_id")
	private int statusId;
	@SerializedName("updated_at")
	private String updatedAt;

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}
	public String getCreatedAt(){
		return this.createdAt;
	}
	public void setDuration(int duration){
		this.duration = duration;
	}
	public Object getDuration(){
		return this.duration;
	}
	public void setExpectedDeliveryTime(String expectedDeliveryTime){
		this.expectedDeliveryTime = expectedDeliveryTime;
	}
	public String getExpectedDeliveryTime(){
		return this.expectedDeliveryTime;
	}
	public void setId(int id){
		this.id = id;
	}
	public int getId(){
		return this.id;
	}
	public void setOfferStar(MainOfferStar offerStar){
		this.offerStar = offerStar;
	}
	public MainOfferStar getOfferStar(){
		return this.offerStar;
	}
	public void setOfferValue(double offerValue){
		this.offerValue = offerValue;
	}
	public double getOfferValue(){
		return this.offerValue;
	}
	public void setOrderId(int orderId){
		this.orderId = orderId;
	}
	public int getOrderId(){
		return this.orderId;
	}
	public void setStarId(int starId){
		this.starId = starId;
	}
	public int getStarId(){
		return this.starId;
	}
	public void setStatusId(int statusId){
		this.statusId = statusId;
	}
	public int getStatusId(){
		return this.statusId;
	}
	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}
	public String getUpdatedAt(){
		return this.updatedAt;
	}

	public MainOffer() {
	}

	/**
	 * Instantiate the instance using the passed jsonObject to set the properties values
	 */



	public MainOffer(JSONObject jsonObject){
		if(jsonObject == null){
			return;
		}
		createdAt = (String) jsonObject.opt("created_at");
		expectedDeliveryTime = (String) jsonObject.opt("expected_delivery_time");
		offerValue = (double) jsonObject.opt("offer_value");
		updatedAt = (String) jsonObject.opt("updated_at");
		duration = jsonObject.optInt("duration");
		id = jsonObject.optInt("id");
		orderId = jsonObject.optInt("order_id");
		starId = jsonObject.optInt("star_id");
		statusId = jsonObject.optInt("status_id");
		offerStar = new MainOfferStar(jsonObject.optJSONObject("offer_star"));
	}

	/**
	 * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
	 */
	public JSONObject toJsonObject()
	{
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("created_at", createdAt);
			jsonObject.put("duration", duration);
			jsonObject.put("expected_delivery_time", expectedDeliveryTime);
			jsonObject.put("id", id);
			jsonObject.put("offer_value", offerValue);
			jsonObject.put("order_id", orderId);
			jsonObject.put("star_id", starId);
			jsonObject.put("status_id", statusId);
			jsonObject.put("updated_at", updatedAt);
			jsonObject.put("offerStar", offerStar.toJsonObject());
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

}