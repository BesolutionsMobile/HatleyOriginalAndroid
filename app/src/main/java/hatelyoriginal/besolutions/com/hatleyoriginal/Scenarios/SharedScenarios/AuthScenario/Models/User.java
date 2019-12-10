package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SharedScenarios.AuthScenario.Models;
//  User.java
//  Model Generated using http://www.jsoncafe.com/ 
//  Created on October 22, 2019

import org.json.*;
import java.util.*;
import com.google.gson.annotations.SerializedName;


public class User {

	@SerializedName("address")
	private Object address;
	@SerializedName("code")
	private String code;
	@SerializedName("created_at")
	private String createdAt;
	@SerializedName("email")
	private String email;
	@SerializedName("email_verified_at")
	private Object emailVerifiedAt;
	@SerializedName("gender")
	private Object gender;
	@SerializedName("id")
	private int id;
	@SerializedName("image_id")
	private Object imageId;
	@SerializedName("name")
	private String name;
	@SerializedName("phone")
	private Object phone;
	@SerializedName("updated_at")
	private String updatedAt;
	@SerializedName("user_type_id")
	private int userTypeId;

	public void setAddress(Object address){
		this.address = address;
	}
	public Object getAddress(){
		return this.address;
	}
	public void setCode(String code){
		this.code = code;
	}
	public String getCode(){
		return this.code;
	}
	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}
	public String getCreatedAt(){
		return this.createdAt;
	}
	public void setEmail(String email){
		this.email = email;
	}
	public String getEmail(){
		return this.email;
	}
	public void setEmailVerifiedAt(Object emailVerifiedAt){
		this.emailVerifiedAt = emailVerifiedAt;
	}
	public Object getEmailVerifiedAt(){
		return this.emailVerifiedAt;
	}
	public void setGender(Object gender){
		this.gender = gender;
	}
	public Object getGender(){
		return this.gender;
	}
	public void setId(int id){
		this.id = id;
	}
	public int getId(){
		return this.id;
	}
	public void setImageId(Object imageId){
		this.imageId = imageId;
	}
	public Object getImageId(){
		return this.imageId;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return this.name;
	}
	public void setPhone(Object phone){
		this.phone = phone;
	}
	public Object getPhone(){
		return this.phone;
	}
	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}
	public String getUpdatedAt(){
		return this.updatedAt;
	}
	public void setUserTypeId(int userTypeId){
		this.userTypeId = userTypeId;
	}
	public int getUserTypeId(){
		return this.userTypeId;
	}

	/**
	 * Instantiate the instance using the passed jsonObject to set the properties values
	 */
	public User(JSONObject jsonObject){
		if(jsonObject == null){
			return;
		}
		code = (String) jsonObject.opt("code");
		createdAt = (String)jsonObject.opt("created_at");
		email = (String)jsonObject.opt("email");
		name = (String)jsonObject.opt("name");
		updatedAt =(String) jsonObject.opt("updated_at");
		/*address = jsonObject.optObject("address");
		emailVerifiedAt = jsonObject.optObject("email_verified_at");
		gender = jsonObject.optObject("gender");
		id = jsonObject.optInt("id");
		imageId = jsonObject.optObject("image_id");
		phone = jsonObject.optObject("phone");*/
		userTypeId = jsonObject.optInt("user_type_id");
	}

	/**
	 * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
	 */
	public JSONObject toJsonObject()
	{
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("address", address);
			jsonObject.put("code", code);
			jsonObject.put("created_at", createdAt);
			jsonObject.put("email", email);
			jsonObject.put("email_verified_at", emailVerifiedAt);
			jsonObject.put("gender", gender);
			jsonObject.put("id", id);
			jsonObject.put("image_id", imageId);
			jsonObject.put("name", name);
			jsonObject.put("phone", phone);
			jsonObject.put("updated_at", updatedAt);
			jsonObject.put("user_type_id", userTypeId);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

}