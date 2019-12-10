
package hatelyoriginal.besolutions.com.hatleyoriginal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderFinance {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("order_id")
    @Expose
    private int orderId;
    @SerializedName("minimum_value")
    @Expose
    private double minimumValue;
    @SerializedName("service_value")
    @Expose
    private double serviceValue;
    @SerializedName("product_expected_price")
    @Expose
    private double productExpectedPrice;
    @SerializedName("product_real_price")
    @Expose
    private double productRealPrice;
    @SerializedName("promo_code_id")
    @Expose
    private int promoCodeId;
    @SerializedName("total")
    @Expose
    private double total;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("order_promo_code")
    @Expose
    private OrderPromoCode orderPromoCode;

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

    public double getMinimumValue() {
        return minimumValue;
    }

    public void setMinimumValue(double minimumValue) {
        this.minimumValue = minimumValue;
    }

    public double getServiceValue() {
        return serviceValue;
    }

    public void setServiceValue(double serviceValue) {
        this.serviceValue = serviceValue;
    }

    public double getProductExpectedPrice() {
        return productExpectedPrice;
    }

    public void setProductExpectedPrice(double productExpectedPrice) {
        this.productExpectedPrice = productExpectedPrice;
    }

    public double getProductRealPrice() {
        return productRealPrice;
    }

    public void setProductRealPrice(double productRealPrice) {
        this.productRealPrice = productRealPrice;
    }

    public int getPromoCodeId() {
        return promoCodeId;
    }

    public void setPromoCodeId(int promoCodeId) {
        this.promoCodeId = promoCodeId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
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

    public OrderPromoCode getOrderPromoCode() {
        return orderPromoCode;
    }

    public void setOrderPromoCode(OrderPromoCode orderPromoCode) {
        this.orderPromoCode = orderPromoCode;
    }

}
