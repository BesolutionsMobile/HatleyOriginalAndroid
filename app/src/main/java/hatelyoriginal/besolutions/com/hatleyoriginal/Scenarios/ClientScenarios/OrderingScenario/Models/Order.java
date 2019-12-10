
package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.OrderingScenario.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import hatelyoriginal.besolutions.com.hatleyoriginal.OrderFinance;

public class Order {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("status_id")
    @Expose
    private int statusId;
    @SerializedName("upload_id")
    @Expose
    private int uploadId;
    @SerializedName("offer_id")
    @Expose
    private int offerId;
    @SerializedName("delivery_time")
    @Expose
    private String deliveryTime;
    @SerializedName("delivered_at")
    @Expose
    private String deliveredAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("order_details")
    @Expose
    private OrderDetails orderDetails;
    @SerializedName("order_finance")
    @Expose
    private OrderFinance orderFinance;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getUploadId() {
        return uploadId;
    }

    public void setUploadId(int uploadId) {
        this.uploadId = uploadId;
    }

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(String deliveredAt) {
        this.deliveredAt = deliveredAt;
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

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }

    public OrderFinance getOrderFinance() {
        return orderFinance;
    }

    public void setOrderFinance(OrderFinance orderFinance) {
        this.orderFinance = orderFinance;
    }

}
