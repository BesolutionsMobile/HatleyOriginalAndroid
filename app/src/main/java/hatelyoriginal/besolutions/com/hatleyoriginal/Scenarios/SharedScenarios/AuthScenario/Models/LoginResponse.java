package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SharedScenarios.AuthScenario.Models;
//  LoginResponse.java
//  Model Generated using http://www.jsoncafe.com/ 
//  Created on October 22, 2019

import org.json.*;

import com.google.gson.annotations.SerializedName;


public class LoginResponse {

	@SerializedName("token")
	private String token;
	@SerializedName("user")
	private User user;

	public void setToken(String token){
		this.token = token;
	}
	public String getToken(){
		return this.token;
	}
	public void setUser(User user){
		this.user = user;
	}
	public User getUser(){
		return this.user;
	}

	/**
	 * Instantiate the instance using the passed jsonObject to set the properties values
	 */
	public LoginResponse(JSONObject jsonObject){
		if(jsonObject == null){
			return;
		}
		token = (String) jsonObject.opt("token");
		user = new User(jsonObject.optJSONObject("user"));
	}

	/**
	 * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
	 */
	public JSONObject toJsonObject()
	{
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("token", token);
			jsonObject.put("user", user.toJsonObject());
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

}