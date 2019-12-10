
package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.OrderingScenario.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetails {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("order_id")
    @Expose
    private int orderId;
    @SerializedName("user_id")
    @Expose
    private int userId;
    @SerializedName("star_id")
    @Expose
    private int starId;
    @SerializedName("user_location")
    @Expose
    private String userLocation;
    @SerializedName("star_location")
    @Expose
    private String starLocation;
    @SerializedName("order_details")
    @Expose
    private String orderDetails;
    @SerializedName("order_from")
    @Expose
    private String orderFrom;
    @SerializedName("order_to")
    @Expose
    private String orderTo;
    @SerializedName("order_location_lat")
    @Expose
    private double orderLocationLat;
    @SerializedName("order_location_long")
    @Expose
    private double orderLocationLong;
    @SerializedName("client_location_lat")
    @Expose
    private double clientLocationLat;
    @SerializedName("client_location_long")
    @Expose
    private double clientLocationLong;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("order_client")
    @Expose
    private OrderClient orderClient;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStarId() {
        return starId;
    }

    public void setStarId(int starId) {
        this.starId = starId;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public String getStarLocation() {
        return starLocation;
    }

    public void setStarLocation(String starLocation) {
        this.starLocation = starLocation;
    }

    public String getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(String orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(String orderFrom) {
        this.orderFrom = orderFrom;
    }

    public String getOrderTo() {
        return orderTo;
    }

    public void setOrderTo(String orderTo) {
        this.orderTo = orderTo;
    }

    public double getOrderLocationLat() {
        return orderLocationLat;
    }

    public void setOrderLocationLat(double orderLocationLat) {
        this.orderLocationLat = orderLocationLat;
    }

    public double getOrderLocationLong() {
        return orderLocationLong;
    }

    public void setOrderLocationLong(double orderLocationLong) {
        this.orderLocationLong = orderLocationLong;
    }

    public double getClientLocationLat() {
        return clientLocationLat;
    }

    public void setClientLocationLat(double clientLocationLat) {
        this.clientLocationLat = clientLocationLat;
    }

    public double getClientLocationLong() {
        return clientLocationLong;
    }

    public void setClientLocationLong(double clientLocationLong) {
        this.clientLocationLong = clientLocationLong;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public OrderClient getOrderClient() {
        return orderClient;
    }

    public void setOrderClient(OrderClient orderClient) {
        this.orderClient = orderClient;
    }

}
