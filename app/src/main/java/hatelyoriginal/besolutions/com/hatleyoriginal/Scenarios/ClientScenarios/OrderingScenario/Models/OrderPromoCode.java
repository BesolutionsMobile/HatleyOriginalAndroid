
package hatelyoriginal.besolutions.com.hatleyoriginal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderPromoCode {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("discount_percentage")
    @Expose
    private double discountPercentage;
    @SerializedName("discount_amount")
    @Expose
    private int discountAmount;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("bandwidth")
    @Expose
    private int bandwidth;
    @SerializedName("starts_in")
    @Expose
    private String startsIn;
    @SerializedName("due_date")
    @Expose
    private String dueDate;
    @SerializedName("minimum_order_price")
    @Expose
    private int minimumOrderPrice;
    @SerializedName("max_discount_amount")
    @Expose
    private int maxDiscountAmount;
    @SerializedName("status_id")
    @Expose
    private int statusId;
    @SerializedName("created_by")
    @Expose
    private int createdBy;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_At")
    @Expose
    private String updatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(int discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(int bandwidth) {
        this.bandwidth = bandwidth;
    }

    public String getStartsIn() {
        return startsIn;
    }

    public void setStartsIn(String startsIn) {
        this.startsIn = startsIn;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public int getMinimumOrderPrice() {
        return minimumOrderPrice;
    }

    public void setMinimumOrderPrice(int minimumOrderPrice) {
        this.minimumOrderPrice = minimumOrderPrice;
    }

    public int getMaxDiscountAmount() {
        return maxDiscountAmount;
    }

    public void setMaxDiscountAmount(int maxDiscountAmount) {
        this.maxDiscountAmount = maxDiscountAmount;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
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

}
