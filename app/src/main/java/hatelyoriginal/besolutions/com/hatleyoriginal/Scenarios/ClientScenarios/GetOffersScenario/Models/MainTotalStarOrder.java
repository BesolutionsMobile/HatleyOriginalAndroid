package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.GetOffersScenario.Models;

import org.json.*;
import java.util.*;
import com.google.gson.annotations.SerializedName;


public class MainTotalStarOrder{

	@SerializedName("created_at")
	private String createdAt;
	@SerializedName("id")
	private int id;
	@SerializedName("orders_count")
	private int ordersCount;
	@SerializedName("over_all_rate")
	private int overAllRate;
	@SerializedName("star_id")
	private int starId;
	@SerializedName("total_orders_value")
	private String totalOrdersValue;
	@SerializedName("updated_at")
	private String updatedAt;

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}
	public String getCreatedAt(){
		return this.createdAt;
	}
	public void setId(int id){
		this.id = id;
	}
	public int getId(){
		return this.id;
	}
	public void setOrdersCount(int ordersCount){
		this.ordersCount = ordersCount;
	}
	public int getOrdersCount(){
		return this.ordersCount;
	}
	public void setOverAllRate(int overAllRate){
		this.overAllRate = overAllRate;
	}
	public int getOverAllRate(){
		return this.overAllRate;
	}
	public void setStarId(int starId){
		this.starId = starId;
	}
	public int getStarId(){
		return this.starId;
	}
	public void setTotalOrdersValue(String totalOrdersValue){
		this.totalOrdersValue = totalOrdersValue;
	}
	public String getTotalOrdersValue(){
		return this.totalOrdersValue;
	}
	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}
	public String getUpdatedAt(){
		return this.updatedAt;
	}

	/**
	 * Instantiate the instance using the passed jsonObject to set the properties values
	 */
	public MainTotalStarOrder(JSONObject jsonObject){
		if(jsonObject == null){
			return;
		}
		createdAt = (String) jsonObject.opt("created_at");
		totalOrdersValue = (String) jsonObject.opt("total_orders_value");
		updatedAt = (String) jsonObject.opt("updated_at");
		id = jsonObject.optInt("id");
		ordersCount = jsonObject.optInt("orders_count");
		overAllRate = jsonObject.optInt("over_all_rate");
		starId = jsonObject.optInt("star_id");
	}

	/**
	 * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
	 */
	public JSONObject toJsonObject()
	{
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("created_at", createdAt);
			jsonObject.put("id", id);
			jsonObject.put("orders_count", ordersCount);
			jsonObject.put("over_all_rate", overAllRate);
			jsonObject.put("star_id", starId);
			jsonObject.put("total_orders_value", totalOrdersValue);
			jsonObject.put("updated_at", updatedAt);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

}