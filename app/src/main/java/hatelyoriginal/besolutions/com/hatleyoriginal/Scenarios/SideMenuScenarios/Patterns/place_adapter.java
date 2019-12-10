package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Patterns;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import hatelyoriginal.besolutions.com.hatleyoriginal.R;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Models.place_list;


import java.util.ArrayList;

public class place_adapter extends RecyclerView.Adapter<place_adapter.place_holder> {
    Context context;
    ArrayList<place_list>mylist;

    public place_adapter(Context context, ArrayList<place_list> mylist) {
        this.context = context;
        this.mylist = mylist;
    }

    @NonNull
    @Override
    public place_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.your_place_item,parent,false);
        place_holder available_order=new place_holder(view);
        return available_order;
    }

    @Override
    public void onBindViewHolder(@NonNull place_holder holder, int position) {
           holder.place_name.setText(mylist.get(position).getName().toString());
    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }

    public class place_holder extends RecyclerView.ViewHolder {
        TextView place_name;
        public place_holder(@NonNull View itemView) {
            super(itemView);
            place_name=(TextView)itemView.findViewById(R.id.place_name);
        }
    }
}
