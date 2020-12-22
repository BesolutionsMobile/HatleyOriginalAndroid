package hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer;

import android.content.Context;

import com.android.volley.Request;

import org.json.JSONException;

import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @desc Java Api Calls Contains all the Project Calls
 */

public class Apicalls {

    private APIRouter apiRouter;

    public Apicalls(Context context, NetworkInterface networkInterface) {

        apiRouter = new APIRouter(context, networkInterface);
    }

    //----------------------------------------------------------------------------------------------

    /**
     *
     * @func User Login
     */

    public void loginUser(final String email, final String pass,final String mob_token) {

        apiRouter.performRequest(Apiclient.LOGIN_USER.getURL(),Apiclient.LOGIN_USER.getParams(), Arrays.asList(email,pass,mob_token), Request.Method.POST,0);

    }



    //----------------------------------------------------------------------------------------------

    /**
     *
     * @func User Registration
     */

    public void insertUser(final String name, final String email, final String password, final String confirm_password,final  String token,final String image_id) {

        apiRouter.performRequest(Apiclient.INSERT_USER.getURL(),Apiclient.INSERT_USER.getParams(), Arrays.asList(name,email,password,confirm_password,token,image_id), Request.Method.POST,2);

    }



    //----------------------------------------------------------------------------------------------

    /**
     *
     * @func Accept Offer
     */

    public void AcceptOffer(final String offer_id,final String mobile_token) {

        try {
            apiRouter.makeAdvancedRequest(Apiclient.ACCEPT_OFFER.getURL(),Request.Method.POST,Apiclient.ACCEPT_OFFER.getParams(), Arrays.asList(offer_id,mobile_token),null);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

//----------------------------------------------------------------------------------------------

    /**
     *
     * @func Reject Offer
     */

    public void RejectOffer(final String offer_id) {

        try {
            apiRouter.makeAdvancedRequest(Apiclient.REJECT_OFFER.getURL(),Request.Method.POST,Apiclient.REJECT_OFFER.getParams(), Arrays.asList(offer_id),null);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    //----------------------------------------------------------------------------------------------

    /**
     *
     * @func Update Investor Data
     */

    public void Insert_Order(final String order_descripition, final String image, final String distance, final String duration, final String promo_code, final String delivery_time, final String order_from_location,
                             final String order_to_location,String client_location_lat,String client_location_long,String order_location_lat,String order_location_long,String mobile_token) {

        try {
            apiRouter.makeAdvancedRequest(Apiclient.INSERT_ORDER.getURL(),Request.Method.POST,Apiclient.INSERT_ORDER.getParams(),Arrays.asList(order_descripition,image,distance,duration,promo_code,delivery_time,
                    order_from_location,order_to_location,client_location_lat,client_location_long,order_location_lat,order_location_long,mobile_token),null);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }



    //----------------------------------------------------------------------------------------------

    /**
     *
     * @func Get Offer Data
     */

    public void Get_data(String id) {

        try {
            apiRouter.makeAdvancedRequest(Apiclient.OFFERS.getURL()+id,Request.Method.GET,Apiclient.OFFERS.getParams(),null,null);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //----------------------------------------------------------------------------------------------

    /**
     *
     * @func Get Complaints Types
     */

    public void Get_complaint_types() {

        try {
            apiRouter.makeAdvancedRequest(Apiclient.GET_COMPLAINT_TYPE.getURL(),Request.Method.GET,Apiclient.GET_COMPLAINT_TYPE.getParams(),null,null);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //----------------------------------------------------------------------------------------------

    /**
     *
     * @func Get Orders Data
     */

    public void Get_order_data(final String starLat, final String starLong, final String mobile_token) {

        try {
            apiRouter.makeAdvancedRequest(Apiclient.ORDERS.getURL(),Request.Method.POST,Apiclient.ORDERS.getParams(),Arrays.asList(starLat,starLong,mobile_token),null);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //----------------------------------------------------------------------------------------------

    /**
     *
     * @func Get Orders Data
     */

    public void Logout() {

        try {
            apiRouter.makeAdvancedRequest(Apiclient.LOGOUT.getURL(),Request.Method.GET,Apiclient.LOGOUT.getParams(),null,null);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //----------------------------------------------------------------------------------------------

    /**
     *
     * @func Submit Offer
     */

    public void Submit_Offer(final String star_id,final String order_id,final String expected_delivery_time,final String offer_value,final String mobile_token) {

        try {
            apiRouter.makeAdvancedRequest(Apiclient.SUBMIT_OFFER.getURL(),Request.Method.POST,Apiclient.SUBMIT_OFFER.getParams(),Arrays.asList(star_id,order_id,expected_delivery_time,offer_value,mobile_token),null);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //----------------------------------------------------------------------------------------------

    /**
     *
     * @func Get Offer Data
     */

    public void Get_myOrder() {

        try {
            apiRouter.makeAdvancedRequest(Apiclient.MY_ORDERS.getURL(),Request.Method.GET,Apiclient.MY_ORDERS.getParams(),null,null);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //----------------------------------------------------------------------------------------------

    /**
     *
     * @func Set Rate
     */

    public void set_rate(String order_id,String rate,String note_id) {

        try {
            apiRouter.makeAdvancedRequest(Apiclient.SET_RATE.getURL(),Request.Method.POST,Apiclient.SET_RATE.getParams(),Arrays.asList(order_id,rate,note_id),null);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //----------------------------------------------------------------------------------------------

    /**
     *
     * @func Set Complaint
     */

    public void set_complaint(String order_id,String complaint_type_id,String complaint) {

        try {
            apiRouter.makeAdvancedRequest(Apiclient.SET_COMPLAINT.getURL(),Request.Method.POST,Apiclient.SET_COMPLAINT.getParams(),Arrays.asList(order_id,complaint_type_id,complaint),null);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //----------------------------------------------------------------------------------------------

    /**
     *
     * @func Cancel Order
     */

    public void cancel_order(String order_id) {

        try {
            apiRouter.makeAdvancedRequest(Apiclient.CANCEL_ORDER.getURL(),Request.Method.PATCH,Apiclient.CANCEL_ORDER.getParams(),Arrays.asList(order_id
            ),null);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    //----------------------------------------------------------------------------------------------

    /**
     *
     * @func Get notifcation Data
     */

    public void Get_notifcation_data() {

        try {
            apiRouter.makeAdvancedRequest(Apiclient.NOTIFCATION.getURL(),Request.Method.GET,Apiclient.NOTIFCATION.getParams(),null,null);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //----------------------------------------------------------------------------------------------


    /**
     *
     * @func Reject Offer
     */

    public void change_password(final String password,final String password_confirmation) {

        try {
            apiRouter.makeAdvancedRequest(Apiclient.CHANGE_PASS.getURL(),Request.Method.PATCH,Apiclient.CHANGE_PASS.getParams(), Arrays.asList(password,password_confirmation),null);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    //----------------------------------------------------------------------------------------------

    /**
     *
     * @func Reject Offer
     */

    public void add_phone (final String phone) {

        try {
            apiRouter.makeAdvancedRequest(Apiclient.ADD_PHONE.getURL(),Request.Method.PATCH,Apiclient.ADD_PHONE.getParams(),Collections.singletonList(phone),null);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    //----------------------------------------------------------------------------------------------

    /**
     *
     * @func Reject Offer
     */

    public void change_photo (final String image) {

        try {
            apiRouter.makeAdvancedRequest(Apiclient.CHANGE_PHOTO.getURL(),Request.Method.PATCH,Apiclient.CHANGE_PHOTO.getParams(),Collections.singletonList(image),null);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * @func Reject Offer
     */

    public void uploadId (final String image) {

        try {
            apiRouter.makeAdvancedRequest(Apiclient.UPLOAD_ID.getURL(),Request.Method.PATCH,Apiclient.UPLOAD_ID.getParams(),Collections.singletonList(image),null);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    //----------------------------------------------------------------------------------------------

    /**
     *
     * @func Get Offer Data
     */


    public void get_balance() {

        try {
            apiRouter.makeAdvancedRequest(Apiclient.GET_BALANCE.getURL(),Request.Method.GET,Apiclient.GET_BALANCE.getParams(),null,null);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    //----------------------------------------------------------------------------------------------

    public void bill_amount(final String order_id,final String bill_amount,final String mobile_token) {

        try {
            apiRouter.makeAdvancedRequest(Apiclient.BILL_AMOUNT.getURL(),Request.Method.PATCH,Apiclient.BILL_AMOUNT.getParams(),Arrays.asList(order_id,bill_amount,mobile_token),null);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    //----------------------------------------------------------------------------------------------


    /**
     *
     * @func GET PROMO CODE
     */

    public void promoCode(final String promo_code) {

        try {
            apiRouter.makeAdvancedRequest(Apiclient.ADD_PROMO_CODE.getURL(),Request.Method.POST,Apiclient.ADD_PROMO_CODE.getParams(),Arrays.asList(promo_code),null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //----------------------------------------------------------------------------------------------


    /**
     *
     * @func USING SWITCH
     */

    public void switch_user() {

        try {
            apiRouter.makeAdvancedRequest(Apiclient.SWITCH.getURL(),Request.Method.PATCH,Apiclient.SWITCH.getParams(),Arrays.asList(),null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
