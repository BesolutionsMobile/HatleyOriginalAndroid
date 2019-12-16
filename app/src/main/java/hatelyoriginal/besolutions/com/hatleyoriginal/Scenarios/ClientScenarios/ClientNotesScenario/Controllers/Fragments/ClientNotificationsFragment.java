package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.ClientNotesScenario.Controllers.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.Apicalls;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.NetworkInterface;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.ResponseModel;
import hatelyoriginal.besolutions.com.hatleyoriginal.R;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.ClientNotesScenario.Models.NotificationItem;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.ClientNotesScenario.Models.Notifications;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.ClientNotesScenario.Patterns.notifcation_adapter;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.utils_adapter;

public class ClientNotificationsFragment extends Fragment implements NetworkInterface {


    @BindView(R.id.nodata)
    TextView nodata;
    @BindView(R.id.notifcation_list)
    RecyclerView notifcationList;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.loading)
    ProgressBar loading;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.my_notifcation, null, false);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        new Apicalls(getActivity(), ClientNotificationsFragment.this).Get_notifcation_data();

        SwipeRefreshLayout swipNotif = Objects.requireNonNull(getActivity()).findViewById(R.id.swipe);
        swipNotif.setOnRefreshListener(() -> {

            new Apicalls(getActivity(), ClientNotificationsFragment.this).Get_notifcation_data();
            swipNotif.setRefreshing(false);

        });

    }

    @Override
    public void OnStart() {

    }

    @Override
    public void OnResponse(ResponseModel model) {

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        ArrayList<NotificationItem> notifcation_list = new ArrayList<>();

        try {


            Notifications notifications = gson.fromJson(model.getJsonObject().toString(), Notifications.class);

            try {

                if (model.getJsonObject().getString("notifications").equals("There is no Notifications")) {
                    String no_data = this.getString(R.string.no_data);
                    nodata.setText(no_data);
                    //STOP PROGRESSBAR
                    loading.setVisibility(View.GONE);
                } else {

                    nodata.setText("");


                    for (int index = 0; index < notifications.getNotifications().size(); index++) {

                        NotificationItem item = new NotificationItem();

                        item.setNotificationID(notifications.getNotifications().get(index).getId());
                        item.setNotificationTitle(notifications.getNotifications().get(index).getTitle());
                        item.setNotificationData(notifications.getNotifications().get(index).getData());
                        item.setNotificationType(notifications.getNotifications().get(index).getType());
                        item.setOrderID(notifications.getNotifications().get(index).getOrderId());
                        item.setSenderName(notifications.getNotifications().get(index).getSentFrom().getName());
                        item.setSenderPhone(notifications.getNotifications().get(index).getSentFrom().getPhone());


                        notifcation_list.add(item);

                    }

                    utils_adapter adapter = new utils_adapter();
                    adapter.Adapter(notifcationList, new notifcation_adapter(getActivity(), notifcation_list), getActivity());

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }



        }catch (Exception e)
        {

            nodata.setText("No Notifications");
            //STOP PROGRESSBAR
            loading.setVisibility(View.GONE);
        }



        //STOP PROGRESSBAR
        loading.setVisibility(View.GONE);

    }

    @Override
    public void OnError(VolleyError error) {

    }

}
