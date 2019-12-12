package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.MainScenario.Controllers.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import hatelyoriginal.besolutions.com.hatleyoriginal.MainActivity;
import hatelyoriginal.besolutions.com.hatleyoriginal.R;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.TinyDB;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    @BindView(R.id.currentlocation)
    ImageView currentlocation;
    @BindView(R.id.confirm)
    Button confirm;
    @BindView(R.id.menu)
    ImageView menu;

    private GoogleMap mMap;
    private static double lat = 0.0;
    private static double lng = 0.0;
    double mylng, mylat;
    private static double current_lat;
    private static double current_lng;
    private static String place_name;

    private Boolean mLocationPermissionsGranted = false;
    private List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
    private AutocompleteSupportFragment autoCompleteSupportFragment;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;

    private TinyDB tinyDB;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.map, null, false);

        assert getFragmentManager() != null;
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this,view);

        tinyDB = new TinyDB(getActivity());

        initPlaces();
        setupPlaceAutoComplete();
        onClick();

        //IF NO GBS
        statusCheck();

        getDeviceLocation();

        menu = Objects.requireNonNull(getActivity()).findViewById(R.id.menu);

        menu.setOnClickListener(view1 -> MainActivity.mNavigationDrawerFragment.openDrawer());

    }


    private void setupPlaceAutoComplete() {
        assert getFragmentManager() != null;
        autoCompleteSupportFragment = (AutocompleteSupportFragment) getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        assert autoCompleteSupportFragment != null;
        autoCompleteSupportFragment.setHint("Delivery place");
        // autoCompleteSupportFragment.setText(address_name);
        autoCompleteSupportFragment.setPlaceFields(fields);
        autoCompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                LatLng latLng = place.getLatLng();
                place_name = place.getName();
                Log.e("currentLocation", "" + latLng);
                assert latLng != null;
                String mStringLatitude = String.valueOf(latLng.latitude);
                String mStringLongitude = String.valueOf(latLng.longitude);
                lat = Double.parseDouble(mStringLatitude);
                lng = Double.parseDouble(mStringLongitude);

                moveCamera(new LatLng(lat, lng)
                );
                mMap.setOnCameraIdleListener(() -> {

                    mylat = mMap.getProjection().getVisibleRegion().latLngBounds.getCenter().latitude;
                    mylng = mMap.getProjection().getVisibleRegion().latLngBounds.getCenter().longitude;

                });
                mMap.clear();

            }

            @Override
            public void onError(@NonNull Status status) {
                //Toasty.error(Objects.requireNonNull(getActivity()), "" + status.getStatusMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    private void initPlaces() {
        Places.initialize(Objects.requireNonNull(getActivity()), "AIzaSyAFZWqZ7it3SPq_vuGpWM7qPx1ZrLAYB74");
    }


    //GET CURRENT LOCATION CAMERA
    private void moveCamera(LatLng latLng) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, (float) 15.0));
    }

    //IF GRANTED PREMISSION DONE GET CURRENT LOCATION
    private void getloca() {
        if (mLocationPermissionsGranted) {
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }

    //GET CURRENT LOCATION
    private void getDeviceLocation() {

        FusedLocationProviderClient mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(getActivity()));
        try {
            if (mLocationPermissionsGranted) {

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        Location currentLocation = (Location) task.getResult();
                        current_lat = currentLocation.getLatitude();
                        current_lng = currentLocation.getLongitude();
                        Log.e("currentLocation", current_lat + "....." + current_lng);
                        moveCamera(new LatLng(current_lat, current_lng)
                        );
                        mMap.setOnCameraIdleListener(() -> {

                            mylat = mMap.getProjection().getVisibleRegion().latLngBounds.getCenter().latitude;
                            mylng = mMap.getProjection().getVisibleRegion().latLngBounds.getCenter().longitude;

                        });
                    } else {

                        Toasty.error(Objects.requireNonNull(getActivity()), "Unable to get Current Location", Toast.LENGTH_LONG).show();

                    }
                });
            }
        } catch (SecurityException ignored) {
        }
    }


    //GET PREMISSION IF YES OR NO
    private void getLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionsGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionsGranted = true;
                    //initialize our map
                }
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
        getLocationPermission();
        ImageView currentlocation = Objects.requireNonNull(getActivity()).findViewById(R.id.currentlocation);
        currentlocation.setOnClickListener(v -> getloca());
        getloca();

        mMap.setOnCameraChangeListener(cameraPosition -> {
            lat = cameraPosition.target.latitude;
            lng = cameraPosition.target.longitude;
            getAddress(cameraPosition.target.latitude, cameraPosition.target.longitude);
        });

    }


    //ON CLICK BUTTON
    private void onClick() {

        confirm = Objects.requireNonNull(getActivity()).findViewById(R.id.confirm);

        confirm.setOnClickListener(view -> {

            if(!place_name.isEmpty())
            {
                tinyDB.putDouble("orderLat",lat);
                tinyDB.putDouble("orderLong",lng);
                tinyDB.putString("orderPlace",place_name);


                final DeliveryPlaceFragment card = new DeliveryPlaceFragment();

                FragmentManager fragmentManager = getFragmentManager();
                assert fragmentManager != null;
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container ,card);
                fragmentTransaction.commit();

                Toasty.success(getActivity(),"Added Successfully",Toast.LENGTH_LONG).show();
            }else
                {
                    Toasty.error(getActivity(),"Select a Place First",Toast.LENGTH_LONG).show();

                }



        });
    }


    //GET ADDRESS
    private void getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);

            if (addresses.size() != 0) {
                Address obj = addresses.get(0);
                String add = obj.getAddressLine(0);
                add = add + "\n" + obj.getCountryName();
                add = add + "\n" + obj.getCountryCode();
                add = add + "\n" + obj.getAdminArea();
                add = add + "\n" + obj.getPostalCode();
                add = add + "\n" + obj.getSubAdminArea();
                add = add + "\n" + obj.getLocality();
                add = add + "\n" + obj.getSubThoroughfare();


                Log.v("IGA", "Address" + add);
                // Toast.makeText(this, "Address=>" + add,
                // Toast.LENGTH_SHORT).show();
                String address_name = obj.getAddressLine(0);
                autoCompleteSupportFragment.setText(address_name);
                place_name = address_name;

            }
            // TennisAppActivity.showDialog(add);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    //CHECK IF GBS OPEND OR NOT
    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("You Must Open GPS To Get Delivery place")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 1))
                .setNegativeButton("No", (dialog, id) -> dialog.cancel());
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void statusCheck() {

        final LocationManager manager = (LocationManager) Objects.requireNonNull(getActivity()).getSystemService(Context.LOCATION_SERVICE);

        assert manager != null;
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }

    }
}