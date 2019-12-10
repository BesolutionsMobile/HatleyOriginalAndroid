
package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.OrderingScenario.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TotalUserOrders {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("user_id")
    @Expose
    private int userId;
    @SerializedName("orders_count")
    @Expose
    private int ordersCount;
    @SerializedName("total_orders_value")
    @Expose
    private String totalOrdersValue;
    @SerializedName("over_all_rate")
    @Expose
    private int overAllRate;
    @SerializedName("total_discount_value")
    @Expose
    private String totalDiscountValue;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getOrdersCount() {
        return ordersCount;
    }

    public void setOrdersCount(int ordersCount) {
        this.ordersCount = ordersCount;
    }

    public String getTotalOrdersValue() {
        return totalOrdersValue;
    }

    public void setTotalOrdersValue(String totalOrdersValue) {
        this.totalOrdersValue = totalOrdersValue;
    }

    public int getOverAllRate() {
        return overAllRate;
    }

    public void setOverAllRate(int overAllRate) {
        this.overAllRate = overAllRate;
    }

    public String getTotalDiscountValue() {
        return totalDiscountValue;
    }

    public void setTotalDiscountValue(String totalDiscountValue) {
        this.totalDiscountValue = totalDiscountValue;
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
