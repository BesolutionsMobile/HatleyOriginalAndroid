package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.GetOffersScenario.Models;

import org.json.*;
import java.util.*;
import com.google.gson.annotations.SerializedName;


public class MainOfferStar{

	@SerializedName("address")
	private String address;
	@SerializedName("code")
	private String code;
	@SerializedName("created_at")
	private String createdAt;
	@SerializedName("deleted_at")
	private String deletedAt;
	@SerializedName("email")
	private String email;
	@SerializedName("email_verified_at")
	private String emailVerifiedAt;
	@SerializedName("gender")
	private String gender;
	@SerializedName("id")
	private int id;
	@SerializedName("image_id")
	private String imageId;
	@SerializedName("mobile_token")
	private String mobileToken;
	@SerializedName("name")
	private String name;
	@SerializedName("phone")
	private String phone;
	@SerializedName("total_star_orders")
	private MainTotalStarOrder totalStarOrders;
	@SerializedName("updated_at")
	private String updatedAt;
	@SerializedName("user_type_id")
	private int userTypeId;

	public void setAddress(String address){
		this.address = address;
	}
	public String getAddress(){
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
	public void setDeletedAt(String deletedAt){
		this.deletedAt = deletedAt;
	}
	public String getDeletedAt(){
		return this.deletedAt;
	}
	public void setEmail(String email){
		this.email = email;
	}
	public String getEmail(){
		return this.email;
	}
	public void setEmailVerifiedAt(String emailVerifiedAt){
		this.emailVerifiedAt = emailVerifiedAt;
	}
	public String getEmailVerifiedAt(){
		return this.emailVerifiedAt;
	}
	public void setGender(String gender){
		this.gender = gender;
	}
	public String getGender(){
		return this.gender;
	}
	public void setId(int id){
		this.id = id;
	}
	public int getId(){
		return this.id;
	}
	public void setImageId(String imageId){
		this.imageId = imageId;
	}
	public String getImageId(){
		return this.imageId;
	}
	public void setMobileToken(String mobileToken){
		this.mobileToken = mobileToken;
	}
	public String getMobileToken(){
		return this.mobileToken;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return this.name;
	}
	public void setPhone(String phone){
		this.phone = phone;
	}
	public String getPhone(){
		return this.phone;
	}
	public void setTotalStarOrders(MainTotalStarOrder totalStarOrders){
		this.totalStarOrders = totalStarOrders;
	}
	public MainTotalStarOrder getTotalStarOrders(){
		return this.totalStarOrders;
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
	public MainOfferStar(JSONObject jsonObject){
		if(jsonObject == null){
			return;
		}
		address = (String) jsonObject.opt("address");
		code = (String) jsonObject.opt("code");
		createdAt = (String) jsonObject.opt("created_at");
		deletedAt = (String) jsonObject.opt("deleted_at");
		email = (String) jsonObject.opt("email");
		emailVerifiedAt = (String) jsonObject.opt("email_verified_at");
		gender = (String) jsonObject.opt("gender");
		imageId = (String) jsonObject.opt("image_id");
		mobileToken = (String) jsonObject.opt("mobile_token");
		name = (String) jsonObject.opt("name");
		phone = (String) jsonObject.opt("phone");
		updatedAt = (String) jsonObject.opt("updated_at");
		id = jsonObject.optInt("id");
		userTypeId = jsonObject.optInt("user_type_id");
		totalStarOrders = new MainTotalStarOrder(jsonObject.optJSONObject("total_star_orders"));
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
			jsonObject.put("deleted_at", deletedAt);
			jsonObject.put("email", email);
			jsonObject.put("email_verified_at", emailVerifiedAt);
			jsonObject.put("gender", gender);
			jsonObject.put("id", id);
			jsonObject.put("image_id", imageId);
			jsonObject.put("mobile_token", mobileToken);
			jsonObject.put("name", name);
			jsonObject.put("phone", phone);
			jsonObject.put("updated_at", updatedAt);
			jsonObject.put("user_type_id", userTypeId);
			jsonObject.put("totalStarOrders", totalStarOrders.toJsonObject());
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

}