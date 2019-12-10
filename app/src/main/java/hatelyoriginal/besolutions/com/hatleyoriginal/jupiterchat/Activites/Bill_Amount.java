package hatelyoriginal.besolutions.com.hatleyoriginal.jupiterchat.Activites;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.greenrobot.eventbus.EventBus;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.bill_amount, container);

        unbinder = ButterKnife.bind(this, rootView);

        tinyDB = new TinyDB(getActivity());

        context = getActivity();

        //SupportMapFragment supportMapFragment = (SupportMapFragment) ((AppCompatActivity) context).getSupportFragmentManager().findFragmentById(R.id.map);
        SupportMapFragment supportMapFragment  = (SupportMapFragment) Objects.requireNonNull(getActivity()).getSupportFragmentManager().findFragmentById(R.id.map);
        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(this);

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = new ProgressDialog(context);
                pd.setMessage("Loading...");
                pd.show();
                if (editPrice.getText().toString().equals("")) {

                    Toasty.error(context, "You Must Enter a Price", Toast.LENGTH_SHORT).show();
                } else {

                    new Apicalls(context, Bill_Amount.this).bill_amount(tinyDB.getString("orderID"), editPrice.getText().toString());
                }

            }
        });

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tinyDB.getString("chatPhone").equals("0000") || tinyDB.getString("chatPhone").isEmpty()) {
                    Toasty.error(context, "This Client Not Having a Number ", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + tinyDB.getString("chatPhone")));
                    context.startActivity(intent);
                }
            }
        });


        return rootView;

    }



    @Override
    public void OnStart() {

    }

    @Override
    public void OnResponse(ResponseModel model) {

        pd.cancel();

        Toasty.success(context, "Order has Finished Successfully", Toast.LENGTH_LONG).show();

        EventBus.getDefault().post(new AddButtonClick("Finished"));
        dismiss();


    }

    @Override
    public void OnError(VolleyError error) {
        pd.cancel();
        if (error.networkResponse.statusCode == 400) {
            Toasty.error(context, "Order Already Finished", Toast.LENGTH_SHORT).show();
        } else if ((error.networkResponse.statusCode == 500)) {

            Toasty.success(context, "Order Failed With 500", Toast.LENGTH_LONG).show();

           // EventBus.getDefault().post(new AddButtonClick("Finished"));
           // dismiss();
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        LatLng sydney = new LatLng(30.077899, 31.342715);
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("order location"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12.0f));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}