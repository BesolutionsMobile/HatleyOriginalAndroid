package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Patterns.FAQ;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import hatelyoriginal.besolutions.com.hatleyoriginal.R;

public class childHolder extends ChildViewHolder {

    TextView title;
    LinearLayout linearLayout;

    public childHolder(View itemView) {

        super(itemView);
        title = itemView.findViewById(R.id.child);
        linearLayout= itemView.findViewById(R.id.clickon);

    }


    public void setArtistName(String name) {
        title.setText(name);
    }
}

