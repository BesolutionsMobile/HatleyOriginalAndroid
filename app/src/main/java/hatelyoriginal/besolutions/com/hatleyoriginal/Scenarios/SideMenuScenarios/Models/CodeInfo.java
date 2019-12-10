package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Models;
//  CodeInfo.java
//  Model Generated using http://www.jsoncafe.com/ 
//  Created on November 6, 2019

import org.json.*;
import java.util.*;
import com.google.gson.annotations.SerializedName;


public class CodeInfo{

	@SerializedName("discount_amount")
	private int discountAmount;

	public void setDiscountAmount(int discountAmount){
		this.discountAmount = discountAmount;
	}
	public int getDiscountAmount(){
		return this.discountAmount;
	}

	/**
	 * Instantiate the instance using the passed jsonObject to set the properties values
	 */
	public CodeInfo(JSONObject jsonObject){
		if(jsonObject == null){
			return;
		}
		discountAmount = jsonObject.optInt("discount_amount");
	}

	/**
	 * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
	 */
	public JSONObject toJsonObject()
	{
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("discount_amount", discountAmount);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

}