package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Patterns;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import hatelyoriginal.besolutions.com.hatleyoriginal.R;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Models.your_order_list;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.TinyDB;
import hatelyoriginal.besolutions.com.hatleyoriginal.jupiterchat.Activites.NewChatActivity;

public class your_order_adapter extends RecyclerView.Adapter<your_order_adapter.your_order> {

    private Context context;
    private ArrayList<your_order_list>mylist;

    private TinyDB tinyDB;

    public your_order_adapter(Context context, ArrayList<your_order_list> mylist) {
        this.context = context;
        this.mylist = mylist;
    }

    @NonNull
    @Override
    public your_order onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.your_order_item,parent,false);
        tinyDB = new TinyDB(context);
        return new your_order(view);
    }

    @Override
    public void onBindViewHolder(@NonNull your_order holder, int position) {

          holder.name.setText(mylist.get(position).getOrder_name());
          holder.date.setText(mylist.get(position).getDate());
          holder.ratings.setRating(mylist.get(position).getstars());
          holder.location.setText(mylist.get(position).getLocation());

          //SET ON ITEM CLICK
          holder.order_item.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {


                  if(mylist.get(position).getStatus().equals("Running"))
                  {
                      tinyDB.putString("reciverName",mylist.get(position).getClient_name());

                      context.startActivity(new Intent(context,NewChatActivity.class));
                  }

              }
          });
    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }

    class your_order extends RecyclerView.ViewHolder {

         TextView name,date,location;
         RatingBar ratings;
         LinearLayout order_item;

        your_order(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.order_name);
            date = itemView.findViewById(R.id.date);
            ratings = itemView.findViewById(R.id.ratings);
            location = itemView.findViewById(R.id.location);
            order_item = itemView.findViewById(R.id.order_item);
        }
    }
}
