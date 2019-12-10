package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Models;

public class your_order_list {

    int id,stars;
    String order_name,date,location,star_name,client_name,status;

    public your_order_list(int id, String order_name, int stars, String date,String location,String star_name,String client_name,String status) {
        this.id = id;
        this.order_name = order_name;
        this.stars = stars;
        this.date = date;
        this.location=location;
        this.star_name=star_name;
        this.client_name=client_name;
        this.status=status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrder_name() {
        return order_name;
    }

    public void setOrder_name(String order_name) {
        this.order_name = order_name;
    }

    public int getstars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStar_name() {
        return star_name;
    }

    public void setStar_name(String star_name) {
        this.star_name = star_name;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
