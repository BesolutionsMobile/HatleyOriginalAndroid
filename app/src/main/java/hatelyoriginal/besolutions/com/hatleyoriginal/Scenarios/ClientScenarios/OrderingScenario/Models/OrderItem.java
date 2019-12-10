package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.OrderingScenario.Models;

public class OrderItem {

    int orderID;
    String orderTo;
    String orderFrom;
    String orderClientName;
    double orderMinVal;
    String orderDetails;
    String orderDeliveryTime;
    int orderClientID;
    int orderClientOrdersCount;
    String orderClientImage;
    double orderClientRating;
    double orderLocationlat;
    double orderLocationLong;
    double clientLocationlat;
    double clientLocationLong;

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getOrderTo() {
        return orderTo;
    }

    public void setOrderTo(String orderTo) {
        this.orderTo = orderTo;
    }

    public String getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(String orderFrom) {
        this.orderFrom = orderFrom;
    }

    public String getOrderClientName() {
        return orderClientName;
    }

    public void setOrderClientName(String orderClientName) {
        this.orderClientName = orderClientName;
    }

    public double getOrderMinVal() {
        return orderMinVal;
    }

    public void setOrderMinVal(double orderMinVal) {
        this.orderMinVal = orderMinVal;
    }

    public String getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(String orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getOrderDeliveryTime() {
        return orderDeliveryTime;
    }

    public void setOrderDeliveryTime(String orderDeliveryTime) {
        this.orderDeliveryTime = orderDeliveryTime;
    }

    public int getOrderClientID() {
        return orderClientID;
    }

    public void setOrderClientID(int orderClientID) {
        this.orderClientID = orderClientID;
    }

    public int getOrderClientOrdersCount() {
        return orderClientOrdersCount;
    }

    public void setOrderClientOrdersCount(int orderClientOrdersCount) {
        this.orderClientOrdersCount = orderClientOrdersCount;
    }

    public String getOrderClientImage() {
        return orderClientImage;
    }

    public void setOrderClientImage(String orderClientImage) {
        this.orderClientImage = orderClientImage;
    }

    public double getOrderClientRating() {
        return orderClientRating;
    }

    public void setOrderClientRating(double orderClientRating) {
        this.orderClientRating = orderClientRating;
    }

    public double getOrderLocationlat() {
        return orderLocationlat;
    }

    public void setOrderLocationlat(double orderLocationlat) {
        this.orderLocationlat = orderLocationlat;
    }

    public double getOrderLocationLong() {
        return orderLocationLong;
    }

    public void setOrderLocationLong(double orderLocationLong) {
        this.orderLocationLong = orderLocationLong;
    }

    public double getClientLocationlat() {
        return clientLocationlat;
    }

    public void setClientLocationlat(double clientLocationlat) {
        this.clientLocationlat = clientLocationlat;
    }

    public double getClientLocationLong() {
        return clientLocationLong;
    }

    public void setClientLocationLong(double clientLocationLong) {
        this.clientLocationLong = clientLocationLong;
    }
}
