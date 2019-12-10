package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Patterns.FAQ;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import hatelyoriginal.besolutions.com.hatleyoriginal.R;

public class parentHolder extends GroupViewHolder {

    TextView title;
    RelativeLayout button;

    public parentHolder(View itemView) {
        super(itemView);
        title= itemView.findViewById(R.id.parent);
      //  button = itemView.findViewById(R.id.button);

    }

    public void setGenreName(String name){
        title.setText(name);
    }
    public void setArtistName(int img){

    }}
