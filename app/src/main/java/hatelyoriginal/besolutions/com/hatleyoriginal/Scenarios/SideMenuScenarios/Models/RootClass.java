package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Models;
//  RootClass.java
//  Model Generated using http://www.jsoncafe.com/ 
//  Created on November 6, 2019

import org.json.*;
import java.util.*;
import com.google.gson.annotations.SerializedName;


public class RootClass{

	@SerializedName("codeInfo")
	private CodeInfo codeInfo;
	@SerializedName("message")
	private String message;

	public void setCodeInfo(CodeInfo codeInfo){
		this.codeInfo = codeInfo;
	}
	public CodeInfo getCodeInfo(){
		return this.codeInfo;
	}
	public void setMessage(String message){
		this.message = message;
	}
	public String getMessage(){
		return this.message;
	}

	/**
	 * Instantiate the instance using the passed jsonObject to set the properties values
	 */
	public RootClass(JSONObject jsonObject){
		if(jsonObject == null){
			return;
		}
		message = (String) jsonObject.opt("message");
		codeInfo = new CodeInfo(jsonObject.optJSONObject("codeInfo"));
	}

	/**
	 * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
	 */
	public JSONObject toJsonObject()
	{
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("message", message);
			jsonObject.put("codeInfo", codeInfo.toJsonObject());
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

}