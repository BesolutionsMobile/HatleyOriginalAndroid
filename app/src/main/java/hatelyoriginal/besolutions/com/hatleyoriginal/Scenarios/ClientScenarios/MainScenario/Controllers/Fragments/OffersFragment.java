package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.MainScenario.Controllers.Fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.Apicalls;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.NetworkInterface;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.ResponseModel;
import hatelyoriginal.besolutions.com.hatleyoriginal.R;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.GetOffersScenario.Models.Offer;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.GetOffersScenario.Models.Offers;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.GetOffersScenario.Patterns.available_offer_adapter;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.TinyDB;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.utils_adapter;

public class OffersFragment extends Fragment implements NetworkInterface {


    private static ProgressDialog pd;
    @BindView(R.id.nodata)
    TextView nodata;
    @BindView(R.id.offers)
    RecyclerView offers;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.delivery_time)
    TextView deliveryTime;
    @BindView(R.id.cancel)
    Button cancel;
    @BindView(R.id.cancel_layout)
    LinearLayout cancelLayout;
    @BindView(R.id.loading)
    ProgressBar loading;

    private int cancel_order_num = 0;

    private TinyDB tinyDB;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.offers, null, false);

        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        tinyDB = new TinyDB(getActivity());

        pd = new ProgressDialog(getActivity());

        //GET OFFERS DATA

        new Apicalls(getActivity(), OffersFragment.this).Get_data(tinyDB.getString("orderID"));

        //CANCEL ORDER
        cancel.setOnClickListener(view1 -> {

            pd.setMessage(getString(R.string.loading));
            pd.setCancelable(false);
            pd.show();
            cancel_order_num = 2;
            new Apicalls(getActivity(), OffersFragment.this).cancel_order(tinyDB.getString("orderID"));

        });

        String delivery_time = tinyDB.getString("deliveryTime");

        try {

            String DATE_INPUT_FORMAT = "yyyy-MM-dd HH:mm:ss";

            String DATE_OUTPUT_FORMAT = "hh:mm a";

            deliveryTime.setText(formatDateFromDateString(DATE_INPUT_FORMAT, DATE_OUTPUT_FORMAT, delivery_time));

        } catch (ParseException e) {
            e.printStackTrace();
        }


        cancelLayout.setVisibility(View.INVISIBLE);

        //SET VISIBALITY GONE
        /*if (tinyDB.getString("orderID").equals("0") || tinyDB.getString("orderID").isEmpty()) {
            cancelLayout.setVisibility(View.INVISIBLE);
        }else
            {
                cancelLayout.setVisibility(View.VISIBLE);
            }*/

        if(tinyDB.getString("orderActive").equals("True"))
        {
            cancelLayout.setVisibility(View.VISIBLE);

        }else
            {
                cancelLayout.setVisibility(View.INVISIBLE);

            }

        //SWIPE REFRESH
        swipe_refresh();

    }

    //SWIPE REFRERSH
    private void swipe_refresh() {
        /// swipe Refresh
        SwipeRefreshLayout swipNotif = Objects.requireNonNull(getActivity()).findViewById(R.id.swipe);
        swipNotif.setOnRefreshListener(() -> {

            new Apicalls(getActivity(), OffersFragment.this).Get_data(tinyDB.getString("orderID"));

            swipNotif.setRefreshing(false);

            if(tinyDB.getString("orderActive").equals("True"))
            {
                cancelLayout.setVisibility(View.VISIBLE);

            }else
            {
                cancelLayout.setVisibility(View.INVISIBLE);

            }

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
    public void OnStart() {

        cancelLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void OnResponse(ResponseModel model) {

        if (cancel_order_num == 2) {

            pd.cancel();

            Toasty.success(Objects.requireNonNull(getActivity()), "Order Canceled", Toast.LENGTH_LONG).show();

            tinyDB.putString("orderID", "0");

            tinyDB.putString("orderActive","False");

            // Should Refresh

            final OffersFragment card = new OffersFragment();

            FragmentManager fragmentManager = getFragmentManager();
            assert fragmentManager != null;
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, card);
            fragmentTransaction.commit();

        } else {

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            ArrayList<Offer> offer_lists = new ArrayList<>();

            Offers offerss = gson.fromJson(model.getJsonObject().toString(),Offers.class);

            nodata.setText("");
            loading.setVisibility(View.GONE);

            if (offerss.getOffers().size() == 0) {

                try
                {
                    String no_data = this.getString(R.string.no_data);
                    nodata.setText(no_data);
                    loading.setVisibility(View.GONE);
                }catch (Exception e){}


            } else {

                cancelLayout.setVisibility(View.VISIBLE);

                for (int index = 0; index < offerss.getOffers().size(); index++) {

                    offer_lists.clear();

                    Offer offer = new Offer();

                    offer.setOfferID(offerss.getOffers().get(index).getId());
                    offer.setOfferStarName(offerss.getOffers().get(index).getOfferStar().getName());
                    offer.setOfferStarRate(offerss.getOffers().get(index).getOfferStar().getTotalStarOrders().getOverAllRate());
                    offer.setOfferStarOrdersCount(offerss.getOffers().get(index).getOfferStar().getTotalStarOrders().getOrdersCount());
                    offer.setOfferPrice(offerss.getOffers().get(index).getOfferValue());
                    offer.setOfferTime(offerss.getOffers().get(index).getExpectedDeliveryTime());

                    if(offerss.getOffers().get(index).getOfferStar().getImageId() == null)
                    {
                        offer.setOfferStarImage("Image");
                    }else
                        {
                            offer.setOfferStarImage(offerss.getOffers().get(index).getOfferStar().getImageId());
                        }


                    offer_lists.add(offer);

                }

                utils_adapter utils_adapter = new utils_adapter();
                utils_adapter.Adapter(offers, new available_offer_adapter(getActivity(), offer_lists), getActivity());
            }
        }
        //STOP PROGRESSBAR
        loading.setVisibility(View.GONE);

    }

    @Override
    public void OnError(VolleyError error) {

        //Toasty.success(Objects.requireNonNull(getActivity()), error.toString(), Toast.LENGTH_LONG).show();

        try
        {
            pd.cancel();
            String no_data = this.getString(R.string.no_data);
            nodata.setText(no_data);
            cancelLayout.setVisibility(View.GONE);
            loading.setVisibility(View.GONE);
        }catch (Exception ignored)
        {

        }

    }

}
