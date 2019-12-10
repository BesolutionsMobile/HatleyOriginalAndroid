package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Models;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by admin on 02/05/2018.
 */

public class expandlist extends ExpandableGroup {
    int id;
    String title;


    public expandlist(int id, String title, List items) {
        super(title, items);

        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }




}
