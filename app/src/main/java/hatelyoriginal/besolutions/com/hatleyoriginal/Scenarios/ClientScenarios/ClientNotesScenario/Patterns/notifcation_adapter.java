package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.ClientNotesScenario.Patterns;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import hatelyoriginal.besolutions.com.hatleyoriginal.R;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.ClientNotesScenario.Models.Notification;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.ClientNotesScenario.Models.NotificationItem;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.TinyDB;
import hatelyoriginal.besolutions.com.hatleyoriginal.jupiterchat.Activites.ContinueChatActivity;

public class notifcation_adapter extends RecyclerView.Adapter<notifcation_adapter.view_holder> {

    private Context context;
    private ArrayList<NotificationItem> mylist;

    private TinyDB tinyDB;

    public notifcation_adapter(Context context, ArrayList<NotificationItem> mylist) {
        this.context = context;
        this.mylist = mylist;
    }

    @NonNull
    @Override
    public view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.notifcation_item,parent,false);
        tinyDB =  new TinyDB(context);
        return new view_holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull view_holder holder, int position) {

            holder.title.setText(mylist.get(position).getNotificationTitle());
            holder.descripition.setText(mylist.get(position).getNotificationData());

        //CHECK IF USER OR CLIENT

         if(tinyDB.getString("userType").equals("1"))
         {
             //GO TO OFFERS
             holder.item.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                    // context.startActivity(new Intent(context,offers.class));
                    // ((AppCompatActivity)context).finish();
                 }
             });
         }

         else {
             //GO TO CHAT
             holder.item.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {

                     Intent intent=new Intent(context, ContinueChatActivity.class);

                     tinyDB.putString("orderID",String.valueOf(mylist.get(position).getOrderID()));

                     if(mylist.get(position).getSenderPhone() == null)
                     {
                         tinyDB.putString("chatPhone","0000");
                     }else
                         {
                             tinyDB.putString("chatPhone",mylist.get(position).getSenderPhone());
                         }

                     tinyDB.putString("reciverName",mylist.get(position).getSenderName());

                     context.startActivity(intent);
                 }
             });
         }

    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }

    class view_holder extends RecyclerView.ViewHolder {
        TextView title,descripition;
        LinearLayout item;

        view_holder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            descripition= itemView.findViewById(R.id.descripition);
            item= itemView.findViewById(R.id.item);
        }
    }
}
