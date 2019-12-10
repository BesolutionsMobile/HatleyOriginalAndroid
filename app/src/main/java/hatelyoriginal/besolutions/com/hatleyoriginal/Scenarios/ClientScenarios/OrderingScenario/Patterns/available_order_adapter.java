package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.OrderingScenario.Patterns;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import hatelyoriginal.besolutions.com.hatleyoriginal.R;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.OrderingScenario.Models.OrderItem;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.StarScenarios.GetOrdersScenario.Controllers.Fragments.SubmitOfferPopupFragment;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.TinyDB;

public class available_order_adapter extends RecyclerView.Adapter<available_order_adapter.available_order> {

    private Context context;
    private ArrayList<OrderItem> mylist;

    private TinyDB tinyDB;

    public available_order_adapter(Context context, ArrayList<OrderItem> mylist) {
        this.context = context;
        this.mylist = mylist;
    }

    @NonNull
    @Override
    public available_order onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.available_order_item,parent,false);
        tinyDB = new TinyDB(context);
        return new available_order(view);
    }

    @Override
    public void onBindViewHolder(@NonNull available_order holder, final int position) {

        double price_d = mylist.get(position).getOrderMinVal();
        int price_i = (int) Math.round(price_d);

        String time = mylist.get(position).getOrderDeliveryTime();

        try {

            String DATE_INPUT_FORMAT = "yyyy-MM-dd HH:mm:ss";

            String DATE_OUTPUT_FORMAT = "hh:mm a";

            String mDateTimeString = time;

            time = formatDateFromDateString(DATE_INPUT_FORMAT, DATE_OUTPUT_FORMAT, mDateTimeString);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        holder.price.setText(String.valueOf(price_i));
        holder.order_name.setText(mylist.get(position).getOrderDetails());
        holder.time.setText(time);
        holder.rating.setRating((float) mylist.get(position).getOrderClientRating());
        holder.from.setText(mylist.get(position).getOrderFrom());
        holder.to.setText(mylist.get(position).getOrderTo());
        holder.username.setText(mylist.get(position).getOrderClientName());


        //OPEN ORDER INFO
        holder.order_item.setOnClickListener(view -> {

            final FragmentManager fm = ((AppCompatActivity)context).getSupportFragmentManager();
            SubmitOfferPopupFragment addCommentFragment = new SubmitOfferPopupFragment();

            tinyDB.putString("clientName",mylist.get(position).getOrderClientName());
            tinyDB.putDouble("clientRating",mylist.get(position).getOrderClientRating());
            tinyDB.putInt("clientOrdersCount",mylist.get(position).getOrderClientOrdersCount());
            tinyDB.putString("orderTitle",mylist.get(position).getOrderDetails());
            tinyDB.putString("orderFrom",mylist.get(position).getOrderFrom());
            tinyDB.putString("orderTo",mylist.get(position).getOrderTo());
            tinyDB.putDouble("orderClientLat",mylist.get(position).getClientLocationlat());
            tinyDB.putDouble("orderClientLong",mylist.get(position).getClientLocationLong());
            tinyDB.putDouble("orderLat",mylist.get(position).getOrderLocationlat());
            tinyDB.putDouble("orderLong",mylist.get(position).getOrderLocationLong());
            tinyDB.putInt("orderSubmitID",mylist.get(position).getOrderID());

            addCommentFragment.show(fm,"tag");

        });

    }

    public static String formatDateFromDateString(String inputDateFormat, String outputDateFormat, String inputDate) throws ParseException {

        Date mParsedDate;
        String mOutputDateString;
        SimpleDateFormat mInputDateFormat = new SimpleDateFormat(inputDateFormat, java.util.Locale.getDefault());
        SimpleDateFormat mOutputDateFormat = new SimpleDateFormat(outputDateFormat, java.util.Locale.getDefault());
        mParsedDate = mInputDateFormat.parse(inputDate);
        assert mParsedDate != null;
        mOutputDateString = mOutputDateFormat.format(mParsedDate);
        return mOutputDateString;

    }


    @Override
    public int getItemCount() {
        return mylist.size();
    }

    public class available_order extends RecyclerView.ViewHolder {

        TextView price,order_name,from,to,username,time;
        RatingBar rating;
        LinearLayout order_item;

        public available_order(@NonNull View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.price);
            order_name = itemView.findViewById(R.id.order_name);
            from = itemView.findViewById(R.id.from);
            to = itemView.findViewById(R.id.to);
            username = itemView.findViewById(R.id.username);
            rating = itemView.findViewById(R.id.ratings);
            time = itemView.findViewById(R.id.time);
            order_item = itemView.findViewById(R.id.order_item);
        }
    }
}
