package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.MainScenario.Controllers.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
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
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Controllers.Activities.personal_info;
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

    String token;

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

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                token = instanceIdResult.getToken();
                // send it to server
            }
        });

        //upload image
        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            2000);
                }
                else {
                    choosePhotoFromGallary(getActivity());
                }
            }
        });


        //SET ON DIALOG Delivery time
       // deliveryTime.setOnClickListener(view12 -> get_date_picker());

        deliveryTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                int selectedmonth = mMonth + 1;
                get_time_picker(mYear,selectedmonth,mDay);
            }
        });

        //ORDER
        order.setOnClickListener(view13 -> {

            //VALIDATE IN ALL ITEMS
            if (descripition.getText().toString().equals("") || deliveryTime.getText().toString().equals("")) {
                String enter_info = Objects.requireNonNull(getActivity()).getString(R.string.enter_info);
                Toasty.error(getActivity(), enter_info, Toast.LENGTH_LONG).show();

            } else if (descripition.getText().toString().length() <= 10) {
                Toasty.error(Objects.requireNonNull(getActivity()), getString(R.string.short_desc), Toast.LENGTH_LONG).show();

            } else {

                pd = new ProgressDialog(getActivity());
                pd.setMessage(getString(R.string.loading));
                pd.setCancelable(false);
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
                        String.valueOf(lat2), String.valueOf(lng2), String.valueOf(lat), String.valueOf(lng),token);
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

    private static String formatDateFromDateString(String inputDate) throws ParseException {

        Date mParsedDate;
        String mOutputDateString;
        SimpleDateFormat mInputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault());
        SimpleDateFormat mOutputDateFormat = new SimpleDateFormat("hh:mm a", java.util.Locale.getDefault());
        mParsedDate = mInputDateFormat.parse(inputDate);
        assert mParsedDate != null;
        mOutputDateString = mOutputDateFormat.format(mParsedDate);
        return mOutputDateString;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super method removed
        if (resultCode == RESULT_OK) {
            if (requestCode == 1000) {
                Uri returnUri = data.getData();
                Bitmap bitmapImage = null;
                try {
                    bitmapImage = MediaStore.Images.Media.getBitmap(Objects.requireNonNull(getActivity()).getContentResolver(), returnUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                uploadImg.setImageBitmap(bitmapImage);
            }
        }
    }
        //Uri returnUri;
        //returnUri = data.getData();

    private void choosePhotoFromGallary(Context context) {

        Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (cameraIntent.resolveActivity(context.getPackageManager()) != null) {
            startActivityForResult(cameraIntent, 1000);
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
        mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
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
                deliveryTime.setText(formatDateFromDateString(end));
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

            tinyDB.putString("orderActive","True");


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
            Toasty.error(Objects.requireNonNull(getActivity()), getString(R.string.code_not_found), Toast.LENGTH_LONG).show();
        } else if (error.networkResponse.statusCode == 422) {
            Toasty.error(Objects.requireNonNull(getActivity()), getString(R.string.expired_code), Toast.LENGTH_LONG).show();


        }
    }
}
