package hatelyoriginal.besolutions.com.hatleyoriginal.jupiterchat.Activites;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
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
import hatelyoriginal.besolutions.com.hatleyoriginal.jupiterchat.Activites.evaluate_star.evaluate_star;

public class Bill_Amount extends DialogFragment implements NetworkInterface, OnMapReadyCallback {


    Context context;
    TinyDB tinyDB;

    @BindView(R.id.editPrice)
    EditText editPrice;
    @BindView(R.id.btnCall)
    Button btnCall;
    @BindView(R.id.btnFinish)
    Button btnFinish;
    Unbinder unbinder;

    ProgressDialog pd;

    String token;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.bill_amount, container);

        unbinder = ButterKnife.bind(this, rootView);

        //DEFINE VAR
        tinyDB = new TinyDB(getActivity());

        context = getActivity();

        //GET TOKEN
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                token = instanceIdResult.getToken();
                // send it to server
            }
        });

        //SUPPORT MAP
        SupportMapFragment supportMapFragment  = (SupportMapFragment) Objects.requireNonNull(getActivity()).getSupportFragmentManager().findFragmentById(R.id.map);
        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(this);

        //SET ON CLICK FINISH
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //STOP PROGRESS DIALOG
                pd = new ProgressDialog(context);
                pd.setMessage("Loading...");
                pd.setCancelable(false);
                pd.show();

                if (editPrice.getText().toString().equals("") || editPrice.getText().toString().isEmpty()) {

                    Toasty.error(context, getString(R.string.enter_price), Toast.LENGTH_SHORT).show();
                    pd.cancel();

                } else {

                    Log.i("orderID" , tinyDB.getString("orderID"));
                    Log.i("BillAmount" , editPrice.getText().toString());
                    Log.i("FirebaseToken" , token);
                    Log.i("UserToken" , tinyDB.getString("userToken"));

                    //CALL API BILL AMOUNT
                    new Apicalls(context, Bill_Amount.this).bill_amount(tinyDB.getString("orderID"), editPrice.getText().toString(),token);
                }

            }
        });

        //SET ON CLICK CALL
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //VALIDATION ON CALL BUTTON
                if (tinyDB.getString("chatPhone").equals("0000") || tinyDB.getString("chatPhone").isEmpty()) {
                    Toasty.error(context, getString(R.string.havent_number), Toast.LENGTH_LONG).show();
                } else {
                    //GO TO CALL
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + tinyDB.getString("chatPhone")));
                    context.startActivity(intent);
                }
            }
        });


        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        return rootView;

    }



    @Override
    public void OnStart() {

    }

    @Override
    public void OnResponse(ResponseModel model) {

        pd.cancel();

        Toasty.success(context, getString(R.string.order_finished), Toast.LENGTH_LONG).show();

        //Toasty.success(context, model.getJsonObject().toString(), Toast.LENGTH_LONG).show();

        EventBus.getDefault().post(new AddButtonClick("Finished"));
        dismiss();


    }

    @Override
    public void OnError(VolleyError error) {
        pd.cancel();

        if (error.networkResponse.statusCode == 400) {

            Toasty.error(context, getString(R.string.order_finished_already), Toast.LENGTH_SHORT).show();

        } else if ((error.networkResponse.statusCode == 500)) {

           // Toasty.success(context, "Order Failed With 500", Toast.LENGTH_LONG).show();

            pd.cancel();

            //Toasty.success(context, "Order has Finished Successfully with 500", Toast.LENGTH_LONG).show();
            Toasty.success(context, getString(R.string.order_finished), Toast.LENGTH_LONG).show();

            EventBus.getDefault().post(new AddButtonClick("Finished"));
            dismiss();


        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        LatLng sydney = new LatLng(30.077899, 31.342715);
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title(getString(R.string.order_finished)));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12.0f));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
