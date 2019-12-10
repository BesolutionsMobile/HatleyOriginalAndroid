package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Patterns;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import hatelyoriginal.besolutions.com.hatleyoriginal.R;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Models.saved_places_list;


import java.util.ArrayList;



public class saved_places_adapter_side extends RecyclerView.Adapter<saved_places_adapter_side.places_holder> {
    Context context;
    ArrayList<saved_places_list>mylist;

    public saved_places_adapter_side(Context context, ArrayList<saved_places_list> mylist) {
        this.context = context;
        this.mylist = mylist;
    }

    @NonNull
    @Override
    public places_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.your_place_item,parent,false);
        places_holder places_holder=new places_holder(view);
        return places_holder;
    }

    @Override
    public void onBindViewHolder(@NonNull places_holder holder, int position) {
         holder.saved_place.setText(mylist.get(position).getPlace_name().toString());
    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }

    class places_holder extends RecyclerView.ViewHolder {
        TextView saved_place;
        public places_holder(@NonNull View itemView) {
            super(itemView);
            saved_place=(TextView)itemView.findViewById(R.id.place_name);
        }
    }
}
