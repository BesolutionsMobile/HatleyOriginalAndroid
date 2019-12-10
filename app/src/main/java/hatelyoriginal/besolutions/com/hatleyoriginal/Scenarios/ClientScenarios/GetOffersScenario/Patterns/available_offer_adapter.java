package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.GetOffersScenario.Patterns;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import hatelyoriginal.besolutions.com.hatleyoriginal.R;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.GetOffersScenario.Models.Offer;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.MainScenario.Controllers.Fragments.ReactOfferPopUpFragment;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.TinyDB;

public class available_offer_adapter extends RecyclerView.Adapter<available_offer_adapter.available_offer> {

    private Context context;
    private ArrayList<Offer> mylist;

    private TinyDB tinyDB;

    public available_offer_adapter(Context context, ArrayList<Offer> mylist) {
        this.context = context;
        this.mylist = mylist;
    }

    @NonNull
    @Override
    public available_offer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.available_offer_item,parent,false);
        tinyDB = new TinyDB(context);
        return new available_offer(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull available_offer holder, final int position) {

        int price_value_int = Math.round( (float) mylist.get(position).getOfferPrice());

        String date = mylist.get(position).getOfferTime();

        try {

            String DATE_INPUT_FORMAT = "yyyy-MM-dd HH:mm:ss";

            String DATE_OUTPUT_FORMAT = "hh:mm a";

            String mDateTimeString = date;

            date = formatDateFromDateString(DATE_INPUT_FORMAT, DATE_OUTPUT_FORMAT, mDateTimeString);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        holder.username.setText(mylist.get(position).getOfferStarName());
        holder.price.setText(String.valueOf(price_value_int));
        holder.time.setText(date);


        Picasso.with(context).load(mylist.get(position).getOfferStarImage()).placeholder(R.drawable.starplaceholder).into(holder.user_img);
        Log.e("image77",mylist.get(position).getOfferStarImage());
        holder.rating.setRating(mylist.get(position).getOfferStarRate());


        String finalDate = date;
        holder.offer_item.setOnClickListener(view -> {

            final FragmentManager fm = ((AppCompatActivity)context).getSupportFragmentManager();
            ReactOfferPopUpFragment addCommentFragment = new ReactOfferPopUpFragment();

            tinyDB.putInt("offerID",mylist.get(position).getOfferID());
            tinyDB.putString("starImage",mylist.get(position).getOfferStarImage());
            tinyDB.putString("starName",mylist.get(position).getOfferStarName());
            tinyDB.putInt("starRate",mylist.get(position).getOfferStarRate());
            tinyDB.putString("starOrdersCount",String.valueOf(mylist.get(position).getOfferStarOrdersCount()));
            tinyDB.putString("offerPrice",String.valueOf(price_value_int));
            tinyDB.putString("offerTime", finalDate);


            addCommentFragment.show(fm,"tag");

        });
    }

    private static String formatDateFromDateString(String inputDateFormat, String outputDateFormat, String inputDate) throws ParseException {

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

    class available_offer extends RecyclerView.ViewHolder {
        ImageView user_img;
        TextView username,price,time;
        RatingBar rating;
        LinearLayout offer_item;

        available_offer(@NonNull View itemView) {
            super(itemView);
            user_img = itemView.findViewById(R.id.userimg);
            username = itemView.findViewById(R.id.username);
            price = itemView.findViewById(R.id.price);
            time = itemView.findViewById(R.id.time);
            offer_item = itemView.findViewById(R.id.offer_item);
            rating = itemView.findViewById(R.id.rating);
        }
    }
}
