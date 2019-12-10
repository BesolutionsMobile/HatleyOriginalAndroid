package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.function.UnaryOperator;


public class itemList{
    int id;
    String name;

    public itemList(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
