package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.StarScenarios.GetOrdersScenario.Controllers.Fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.greenrobot.eventbus.EventBus;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.Apicalls;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.NetworkInterface;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.ResponseModel;
import hatelyoriginal.besolutions.com.hatleyoriginal.R;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.AddButtonClick;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.TinyDB;

public class SubmitOfferPopupFragment extends DialogFragment implements NetworkInterface, OnMapReadyCallback {

    TinyDB tinyDB;

    ProgressDialog pd;

    @BindView(R.id.cancel)
    ImageView cancel;
    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.ratings)
    RatingBar ratings;
    @BindView(R.id.orderscount)
    TextView orderscount;
    @BindView(R.id.order_name)
    TextView orderName;
    @BindView(R.id.from)
    TextView from;
    @BindView(R.id.to)
    TextView to;
    @BindView(R.id.expected_arrival_time)
    EditText expectedArrivalTime;
    @BindView(R.id.expected_price)
    EditText expectedPrice;
    @BindView(R.id.create_offer)
    Button createOffer;

    Unbinder unbinder;

    SupportMapFragment supportMapFragment;

    View rootView;


    String end;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.order_info, container);

        tinyDB = new TinyDB(getActivity());

        unbinder = ButterKnife.bind(this, rootView);


        supportMapFragment = (SupportMapFragment) Objects.requireNonNull(getActivity()).getSupportFragmentManager().findFragmentById(R.id.map2);
        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(this);


        username.setText(tinyDB.getString("clientName"));
        //SET RATING

        float rate = (float) tinyDB.getDouble("clientRating", 0.0);
        ratings.setRating(rate);
        //ORDERS COUNT

        orderscount.setText(String.valueOf(tinyDB.getInt("clientOrdersCount")));
        //SET ORDER NAME

        orderName.setText(tinyDB.getString("orderTitle"));
        //SET FROM

        from.setText(tinyDB.getString("orderFrom"));
        //SET TO

        to.setText(tinyDB.getString("orderTo"));


        //SEND OFFER

        createOffer.setOnClickListener(view -> {

            if (expectedArrivalTime.getText().toString().equals("")) {

                Toasty.error(getActivity(), "Please enter delivery time", Toast.LENGTH_LONG).show();

            } else if (expectedPrice.getText().toString().equals("")) {

                Toasty.error(getActivity(), "Please enter expected price", Toast.LENGTH_LONG).show();

            } else {

                new Apicalls(getActivity(), SubmitOfferPopupFragment.this).Submit_Offer("0", String.valueOf(tinyDB.getInt("orderSubmitID")),
                        end, expectedPrice.getText().toString());

                pd = new ProgressDialog(getActivity());
                pd.setMessage("Loading...");
                pd.show();
            }

        });

        //CLOSE OFFER

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        //ARRIVAL TIME
        expectedArrivalTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                get_date_picker(expectedArrivalTime);
            }
        });


        return rootView;


    }


   /* @Override
    public int getTheme() {
        return R.style.MyCustomTheme;
    }
*/


    @Override
    public void OnStart() {

    }

    @Override
    public void OnResponse(ResponseModel model) {

        pd.cancel();

        Toasty.success(getActivity(), "Offer Submitted Successfully", Toast.LENGTH_LONG).show();

        //Rejected
        EventBus.getDefault().post(new AddButtonClick("Submitted"));

        dismiss();
    }

    @Override
    public void OnError(VolleyError error) {
        pd.cancel();


        StringWriter errors = new StringWriter();
        error.printStackTrace(new PrintWriter(errors));

        Toasty.error(getActivity(), "Offer Not Sent.", Toast.LENGTH_LONG).show();

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
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        LatLng sydney = new LatLng(tinyDB.getDouble("orderLat", 30.077899), tinyDB.getDouble("orderLong", 31.342715));
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("order location"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12.0f));

    }


    //GET DATE PICKER
    void get_date_picker(EditText arrival_time) {
        // TODO Auto-generated method stub
        //To show current date in the datepicker
        Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker;
        mDatePicker = new DatePickerDialog(getActivity(), R.style.DialogTheme, (datepicker, selectedyear, selectedmonth, selectedday) -> {
            // TODO Auto-generated method stub
            /*      Your code   to get date and time    */
            selectedmonth = selectedmonth + 1;
            //GET TIME PICKER
            get_time_picker(selectedyear, selectedmonth, selectedday, arrival_time);
        }, mYear, mMonth, mDay);
        mDatePicker.setTitle("Select Date");
        mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        mDatePicker.show();
    }

    //GET TIME PICKER
    void get_time_picker(int selectedyear, int selectedmonth, int selectedday, EditText arrival_time) {

        // TODO Auto-generated method stub
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getActivity(), R.style.DialogTheme, (timePicker, selectedHour, selectedMinute) -> {
            try {
                end = selectedyear + "-" + selectedmonth + "-" + selectedday + " " + selectedHour + ":" + selectedMinute + ":" + "00";
                arrival_time.setText(formatDateFromDateString("yyyy-MM-dd HH:mm:ss","hh:mm a",end));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }, hour, minute, false);//Yes 24 hour time        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();


        assert getFragmentManager() != null;
        Fragment fragment = (getFragmentManager().findFragmentById(R.id.map2));
        FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        assert fragment != null;
        ft.remove(fragment);
        ft.commit();


        unbinder.unbind();
    }
}
