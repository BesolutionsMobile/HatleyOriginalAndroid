
package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.ClientNotesScenario.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.ClientNotesScenario.Models.Notification;

public class Notifications {

    @SerializedName("notifications")
    @Expose
    private List<Notification> notifications = null;

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

}
