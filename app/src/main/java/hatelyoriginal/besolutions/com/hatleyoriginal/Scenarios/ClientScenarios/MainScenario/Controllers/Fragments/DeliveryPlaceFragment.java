package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.MainScenario.Controllers.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import hatelyoriginal.besolutions.com.hatleyoriginal.R;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.MainScenario.Patterns.recent_places_adapter;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.MainScenario.Patterns.saved_places_adapter;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.MainScenario.Tools.local_data;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.MainScenario.Tools.recent_place;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.TinyDB;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.utils_adapter;

public class DeliveryPlaceFragment extends Fragment {


    @BindView(R.id.places_list)
    RecyclerView placesList;
    @BindView(R.id.recent_places_list)
    RecyclerView recentPlacesList;
    @BindView(R.id.backbutton)
    ImageView backbutton;

    private List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
    private static Double lat, lng;


    private TinyDB tinyDB;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.my_place, null, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        tinyDB = new TinyDB(getActivity());

        initPlaces();
        setupPlaceAutoComplete();
        get_saved_places();

        //RECENT PLACES
        get_recent_places();

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final MapFragment card = new MapFragment();

                FragmentManager fragmentManager = getFragmentManager();
                assert fragmentManager != null;
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, card);
                fragmentTransaction.commit();

            }
        });

    }


    //GET PLACES
    private void setupPlaceAutoComplete() {
        AutocompleteSupportFragment autoCompleteSupportFragment = (AutocompleteSupportFragment) getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        assert autoCompleteSupportFragment != null;
        autoCompleteSupportFragment.setPlaceFields(fields);
        autoCompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {

                LatLng latLng = place.getLatLng();
                String user_place = place.getName();

                lat = Objects.requireNonNull(place.getLatLng()).latitude;
                lng = place.getLatLng().longitude;

                Log.e("currentLocation", "" + latLng);

                tinyDB.putString("deliveryName", user_place);
                tinyDB.putDouble("deliveryLat", lat);
                tinyDB.putDouble("deliveryLong", lng);

                final RequestOrderFragment card = new RequestOrderFragment();

                FragmentManager fragmentManager = getFragmentManager();
                assert fragmentManager != null;
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, card);
                fragmentTransaction.commit();


                //INSERT PLACE TO ACTIVITY
                recent_place recent_place = new recent_place(getActivity());
                if (recent_place.add_new_place(place.getName(), lat, lng)) {
                    String add_success = Objects.requireNonNull(getActivity()).getString(R.string.added_success);
                } else {
                    String not_add = Objects.requireNonNull(getActivity()).getString(R.string.not_added);
                }
            }

            @Override
            public void onError(@NonNull Status status) {
                //Toasty.error(Objects.requireNonNull(getActivity()), "" + status.getStatusMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    //INITIALIZE PLACES
    private void initPlaces() {
        Places.initialize(Objects.requireNonNull(getActivity()), "AIzaSyAFZWqZ7it3SPq_vuGpWM7qPx1ZrLAYB74");
    }

    //GET SAVED PLACES
    private void get_saved_places() {

        //GET DATA ARRAY
        local_data local_data = new local_data(getActivity());

        utils_adapter utils_adapter = new utils_adapter();
        utils_adapter.Adapter(placesList, new saved_places_adapter(getActivity(), local_data.RetreiveAllData()), getActivity());

        if (local_data.RetreiveAllData().size() == 0) {
            TextView nodata = Objects.requireNonNull(getActivity()).findViewById(R.id.nodata);
            nodata.setText(getString(R.string.no_saved_places));
        }

    }


    //GET RECENT PLACES
    private void get_recent_places() {
        //GET DATA ARRAY
        recent_place local_data = new recent_place(getActivity());

        utils_adapter utils_adapter = new utils_adapter();
        utils_adapter.Adapter(recentPlacesList, new recent_places_adapter(getActivity(), local_data.RetreiveAllData()), getActivity());

        if (local_data.RetreiveAllData().size() == 0) {
            TextView nodata_recent = Objects.requireNonNull(getActivity()).findViewById(R.id.nodata_recent);
            nodata_recent.setText(getString(R.string.no_recent_place));
        }
    }


}
