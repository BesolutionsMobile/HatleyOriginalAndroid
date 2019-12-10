package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.MainScenario.Patterns;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import hatelyoriginal.besolutions.com.hatleyoriginal.R;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.MainScenario.Controllers.Fragments.RequestOrderFragment;

import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.MainScenario.Models.saved_places_list;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.TinyDB;

public class saved_places_adapter extends RecyclerView.Adapter<saved_places_adapter.places_holder> {

    private Context context;
    private ArrayList<saved_places_list>mylist;
    private TinyDB tinyDB;

    public saved_places_adapter(Context context, ArrayList<saved_places_list> mylist) {
        this.context = context;
        this.mylist = mylist;
    }

    @NonNull
    @Override
    public places_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.saved_place_item,parent,false);
        tinyDB = new TinyDB(context);
        return new places_holder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull places_holder holder, int position) {
        holder.saved_place.setText(mylist.get(position).getPlace_name());
         //GO TO NEXT ACTIVITY
        holder.saved_place.setOnClickListener(view -> {

            tinyDB.putString("deliveryName", mylist.get(position).getPlace_name());
            tinyDB.putDouble("deliveryLat",mylist.get(position).getLat());
            tinyDB.putDouble("deliveryLong",mylist.get(position).getLng());


            final RequestOrderFragment card = new RequestOrderFragment();

            FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container ,card);
            fragmentTransaction.commit();

        });
    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }

    class places_holder extends RecyclerView.ViewHolder {
        TextView saved_place;
        public places_holder(@NonNull View itemView) {
            super(itemView);
            saved_place= itemView.findViewById(R.id.saved_place);
        }
    }
}
