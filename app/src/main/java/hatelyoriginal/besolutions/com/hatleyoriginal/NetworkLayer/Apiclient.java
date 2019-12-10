package hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Apiclient {

    /**
     * @API
     *
     * ---> 1) URL OF API METHOD
     *
     * ---> 2) ARRAY OF PARAMETERS KEYS
     *
     */

    LOGIN_USER("auth/login", Arrays.asList("email", "password","mobile_token")),
    INSERT_USER("auth/register", Arrays.asList("name","email","password","password_confirmation","mobile_token")),
    INSERT_ORDER("store-order", Arrays.asList("order_description","image","distance","duration","promo_code","delivery_time","order_from_location"
            ,"order_to_location","client_location_lat","client_location_long","order_location_lat", "order_location_long")),
    ACCEPT_OFFER("accept-offer", Collections.singletonList("offer_id")),
    REJECT_OFFER("reject-offer", Collections.singletonList("offer_id")),
    SUBMIT_OFFER("submit-offer", Arrays.asList("star_id","order_id","expected_delivery_time","offer_value")),
    SET_RATE("rate", Arrays.asList("order_id","rate","note_id")),
    SET_COMPLAINT("make-complaint", Arrays.asList("order_id","complaint_type_id","complaint")),
    CANCEL_ORDER("cancel-order", Collections.singletonList("order_id")),
    NOTIFCATION("my-notifications", null),
    MY_ORDERS("my-orders", null),
    GET_COMPLAINT_TYPE("get-complaint-type", null),
    ORDERS("show-orders", null),
    OFFERS("order-offers/", null),
    SWITCH("switch-user", null),
    GET_BALANCE("get-balance", null),
    ADD_PHONE("add-phone", Collections.singletonList("phone")),
    CHANGE_PASS("change-password", Arrays.asList("password","password_confirmation")),
    CHANGE_PHOTO("update-personal-image", Collections.singletonList("image_url")),
    ADD_PROMO_CODE("add-promocode", Collections.singletonList("promo_code")),
    BILL_AMOUNT("finish-order", Arrays.asList("order_id","bill_amount")),
    LOGOUT("logout",null);


    //--------------------------------------

    /**
     * @BASE_URL
     */

    String BASE_URL = "https://www.hatly.be4maps.com/api/";

    private final String URL;
    private final List<String> params;


    Apiclient(String URL, List<String> params) {

        this.URL = URL;
        this.params = params;
    }

    public String getURL() {
        return BASE_URL + URL;
    }

    public List<String> getParams() {
        return params;
    }
}
