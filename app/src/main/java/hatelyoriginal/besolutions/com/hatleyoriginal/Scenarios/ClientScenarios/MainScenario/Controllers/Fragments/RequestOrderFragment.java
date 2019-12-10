package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.MainScenario.Controllers.Fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.Apicalls;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.NetworkInterface;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.ResponseModel;
import hatelyoriginal.besolutions.com.hatleyoriginal.R;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.MainScenario.Tools.RequestLoading;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.AddButtonClick;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.TinyDB;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.utils;

import static android.app.Activity.RESULT_OK;

public class RequestOrderFragment extends Fragment implements NetworkInterface {

    @BindView(R.id.descripition)
    EditText descripition;
    @BindView(R.id.upload_img)
    ImageView uploadImg;
    @BindView(R.id.delivery_time)
    EditText deliveryTime;
    @BindView(R.id.code)
    EditText code;
    @BindView(R.id.order)
    Button order;
    @BindView(R.id.backbutton)
    ImageView backbutton;

    private ProgressDialog pd;

    private TinyDB tinyDB;

    String end;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.request_page, null, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        tinyDB = new TinyDB(getActivity());

        final utils utils = new utils(getActivity());

        //upload image
        uploadImg.setOnClickListener(view1 -> utils.upload_image(getActivity(), 1));


        //SET ON DIALOG Delivery time
        deliveryTime.setOnClickListener(view12 -> get_date_picker());

        //ORDER
        order.setOnClickListener(view13 -> {

            //VALIDATE IN ALL ITEMS
            if (descripition.getText().toString().equals("") || deliveryTime.getText().toString().equals("")) {
                String enter_info = Objects.requireNonNull(getActivity()).getString(R.string.enter_info);
                Toasty.error(getActivity(), enter_info, Toast.LENGTH_LONG).show();

            } else if (descripition.getText().toString().length() <= 10) {
                Toasty.error(Objects.requireNonNull(getActivity()), "Description is too short", Toast.LENGTH_LONG).show();

            } else {

                pd = new ProgressDialog(getActivity());
                pd.setMessage("Loading...");
                pd.show();

                //GET DATA

                double lat = tinyDB.getDouble("orderLat", 0.0);
                double lng = tinyDB.getDouble("orderLong", 0.0);
                double lat2 = tinyDB.getDouble("deliveryLat", 0.0);
                double lng2 = tinyDB.getDouble("deliveryLong", 0.0);

                float lat_f = (float) lat;
                float lng_f = (float) lng;
                float lat_f2 = (float) lat2;
                float lng_f2 = (float) lng2;

                int dist = cal_distance(lat_f, lng_f, lat_f2, lng_f2);
                String dur = String.valueOf((dist * 0.65));


                String place_name = tinyDB.getString("orderPlace");
                String user_place = tinyDB.getString("deliveryName");


                //CALL API
                new Apicalls(getActivity(), RequestOrderFragment.this).Insert_Order(descripition.getText().toString(), "data:image/jpeg;base64", String.valueOf(cal_distance(lat_f, lng_f, lat_f2, lng_f2))
                        , dur, code.getText().toString(), end, place_name, user_place,
                        String.valueOf(lat2), String.valueOf(lng2), String.valueOf(lat), String.valueOf(lng));
            }
        });


        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final DeliveryPlaceFragment card = new DeliveryPlaceFragment();

                FragmentManager fragmentManager = getFragmentManager();
                assert fragmentManager != null;
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, card);
                fragmentTransaction.commit();

            }
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
    public void startActivityForResult(Intent data, int requestCode) {
        super.startActivityForResult(data, requestCode);

        if (requestCode == RESULT_OK) {

            Uri selectedImage = data.getData();
            InputStream imageStream = null;
            try {
                assert selectedImage != null;
                imageStream = Objects.requireNonNull(getActivity()).getContentResolver().openInputStream(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            Bitmap selectedPhoto = BitmapFactory.decodeStream(imageStream);
            Bitmap bitmaps = Bitmap.createScaledBitmap(selectedPhoto, 300, 300, true);
            uploadImg.setImageBitmap(bitmaps);
            //CONVERT IMAGE TO STRING BASE 64
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            bitmaps.compress(Bitmap.CompressFormat.PNG, 100, os);
            byte[] b = os.toByteArray();
            //String image = Base64.encodeToString(b, Base64.DEFAULT);

        }
    }

    //GET DATE PICKER
    private void get_date_picker() {
        // TODO Auto-generated method stub
        //To show current date in the datepicker
        Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker;


        mDatePicker = new DatePickerDialog(Objects.requireNonNull(getActivity()), R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                // TODO Auto-generated method stub
                /*      Your code   to get date and time    */
                selectedmonth = selectedmonth + 1;
                //GET TIME PICKER
                get_time_picker(selectedyear, selectedmonth, selectedday);
            }
        }, mYear, mMonth, mDay);
        mDatePicker.setTitle("Select Date");
        mDatePicker.show();
    }

    //GET TIME PICKER
    @SuppressLint("SetTextI18n")
    private void get_time_picker(int selectedyear, int selectedmonth, int selectedday) {

        // TODO Auto-generated method stub
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getActivity(), R.style.DialogTheme, (timePicker, selectedHour, selectedMinute) -> {
            try {
                end = selectedyear + "-" + selectedmonth + "-" + selectedday + " " + selectedHour + ":" + selectedMinute + ":" + "00";
                deliveryTime.setText(formatDateFromDateString("yyyy-MM-dd HH:mm:ss","hh:mm a",end));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    private int cal_distance(float lat_a, float lng_a, float lat_b, float lng_b) {
        double earthRadius = 3958.75;
        double latDiff = Math.toRadians(lat_b - lat_a);
        double lngDiff = Math.toRadians(lng_b - lng_a);
        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2) +
                Math.cos(Math.toRadians(lat_a)) * Math.cos(Math.toRadians(lat_b)) *
                        Math.sin(lngDiff / 2) * Math.sin(lngDiff / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthRadius * c;

        int meterConversion = 1609;

        return (int) ((distance * meterConversion) / 1000);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onButtonClick(AddButtonClick addButtonClick) {
        if (addButtonClick.getEvent().equals("Done")) {
            final MapFragment card = new MapFragment();

            FragmentManager fragmentManager = getFragmentManager();
            assert fragmentManager != null;
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, card);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void OnStart() {

    }

    @Override
    public void OnResponse(ResponseModel model) {

        pd.cancel();

        JSONObject jsonObject = model.getJsonObject();
        try {

            JSONObject jsonObject_offer = jsonObject.getJSONObject("order");
            String order_id = jsonObject_offer.getString("id");

            //SAVE DATA IN SHARED_PREFRENCES
            tinyDB.putString("orderID", order_id);

            //SAVE DELIVERY TIME
            tinyDB.putString("deliveryTime", end);


            RequestLoading loading = new RequestLoading();
            loading.dialog(getActivity(), R.layout.loading, .80);


        } catch (JSONException e) {
            pd.cancel();
            e.printStackTrace();
        }

    }

    @Override
    public void OnError(VolleyError error) {

        pd.cancel();
        if (error.networkResponse.statusCode == 404) {
            Toasty.error(Objects.requireNonNull(getActivity()), "Code Not Found", Toast.LENGTH_LONG).show();
        } else if (error.networkResponse.statusCode == 422) {
            Toasty.error(Objects.requireNonNull(getActivity()), "Expired Code", Toast.LENGTH_LONG).show();


        }
    }
}
