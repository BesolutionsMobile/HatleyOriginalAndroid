package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Controllers.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;
import java.util.List;

import es.dmoral.toasty.Toasty;
import hatelyoriginal.besolutions.com.hatleyoriginal.R;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.MainScenario.Tools.local_data;

public class add_new_place extends AppCompatActivity {

    List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
    AutocompleteSupportFragment autoCompleteSupportFragment;
    static Double lat, lng;
    static Double place_lat, place_lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_place);
        initPlaces();
        setupPlaceAutoComplete();
    }


    //GET PLACES BY AUTO COMPLETE
    public void setupPlaceAutoComplete() {
        autoCompleteSupportFragment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autoCompleteSupportFragment.setPlaceFields(fields);
        autoCompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                LatLng latLng = place.getLatLng();
                Log.e("currentLocation", "" + latLng);
                String mStringLatitude = String.valueOf(latLng.latitude);
                String mStringLongitude = String.valueOf(latLng.longitude);
                lat = Double.parseDouble(mStringLatitude);
                lng = Double.parseDouble(mStringLongitude);

                //GO TO YOUR PLACES
                Intent intent = new Intent(add_new_place.this, your_places.class);
                startActivity(intent);

                //ADD DATA TO LOCAL DATA
                local_data local_data=new local_data(add_new_place.this);
                if(local_data.add_new_place(place.getName(),lat,lng))
                {
                    String add_success=add_new_place.this.getString(R.string.added_success);
                    Toasty.success(add_new_place.this,add_success,Toast.LENGTH_LONG).show();
                }
                else {
                    String not_add=add_new_place.this.getString(R.string.not_added);
                    Toasty.error(add_new_place.this, not_add,Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onError(@NonNull Status status) {
                Toasty.error(add_new_place.this,""+status.getStatusMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    //INITIALIZE PLACES
    public void initPlaces() {
        Places.initialize(this, "AIzaSyAFZWqZ7it3SPq_vuGpWM7qPx1ZrLAYB74");
    }


}
