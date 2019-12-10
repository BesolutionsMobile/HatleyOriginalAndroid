package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.StarScenarios.GetOrdersScenario.Controllers.Fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
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
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.OrderingScenario.Models.OrderItem;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.OrderingScenario.Models.Orders;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.OrderingScenario.Patterns.available_order_adapter;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.utils_adapter;

public class OrdersFragment extends Fragment implements NetworkInterface {

    private RecyclerView availableOrderlist;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;

    private ProgressBar loading;

    private TextView nodata;

    private ProgressDialog pd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.available_order, null, false);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        availableOrderlist = Objects.requireNonNull(getActivity()).findViewById(R.id.available_orderlist);
        nodata = getActivity().findViewById(R.id.nodata);
        loading = getActivity().findViewById(R.id.loading);

        swipe_refresh();

        new Apicalls(getActivity(), OrdersFragment.this).Get_order_data();

    }

    private void swipe_refresh()
    {
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading...");

        /// swipe Refresh
        SwipeRefreshLayout swipNotif = Objects.requireNonNull(getActivity()).findViewById(R.id.swipe);
        swipNotif.setOnRefreshListener(() -> {

            new Apicalls(getActivity(), OrdersFragment.this).Get_order_data();
            swipNotif.setRefreshing(false);
        });
    }

    @Override
    public void OnStart() {

        pd.cancel();
    }

    @Override
    public void OnResponse(ResponseModel model) {

        TextView no_orders = Objects.requireNonNull(getActivity()).findViewById(R.id.nodata);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        ArrayList<OrderItem> order_lists = new ArrayList<>();

        Orders orders = gson.fromJson(model.getJsonObject().toString(),Orders.class);

        try {
            JSONArray orderss = model.getJsonObject().getJSONArray("orders");
            if(orderss.length()==0)
            {

                no_orders.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);
            }
            else {

                nodata.setText("");


                for (int index = 0; index < orders.getOrders().size(); index++) {

                    OrderItem item = new OrderItem();

                    item.setOrderID(orders.getOrders().get(index).getId());
                    item.setOrderClientName(orders.getOrders().get(index).getOrderDetails().getOrderClient().getName());
                    item.setOrderTo(orders.getOrders().get(index).getOrderDetails().getOrderTo());
                    item.setOrderFrom(orders.getOrders().get(index).getOrderDetails().getOrderFrom());
                    item.setClientLocationlat(orders.getOrders().get(index).getOrderDetails().getClientLocationLat());
                    item.setClientLocationLong(orders.getOrders().get(index).getOrderDetails().getClientLocationLong());
                    item.setOrderDeliveryTime(orders.getOrders().get(index).getDeliveryTime());
                    item.setOrderMinVal(orders.getOrders().get(index).getOrderFinance().getMinimumValue());
                    item.setOrderDetails(orders.getOrders().get(index).getOrderDetails().getOrderDetails());
                    item.setOrderLocationlat(orders.getOrders().get(index).getOrderDetails().getOrderLocationLat());
                    item.setOrderLocationLong(orders.getOrders().get(index).getOrderDetails().getOrderLocationLong());
                    item.setOrderClientOrdersCount(orders.getOrders().get(index).getOrderDetails().getOrderClient().getTotalUserOrders().getOrdersCount());
                    item.setOrderClientImage(orders.getOrders().get(index).getOrderDetails().getOrderClient().getImageId());
                    item.setOrderClientID(orders.getOrders().get(index).getOrderDetails().getOrderClient().getId());
                    item.setOrderClientRating(orders.getOrders().get(index).getOrderDetails().getOrderClient().getTotalUserOrders().getOverAllRate());

                    order_lists.add(item);

                }


                utils_adapter utils_adapter = new utils_adapter();
                utils_adapter.Adapter(availableOrderlist, new available_order_adapter(getActivity(), order_lists), getActivity());

                //DISMISS LOADING
                no_orders.setVisibility(View.INVISIBLE);
                loading.setVisibility(View.GONE);



            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void OnError(VolleyError error) {

        loading.setVisibility(View.GONE);
        TextView no_orders = Objects.requireNonNull(getActivity()).findViewById(R.id.nodata);
        no_orders.setVisibility(View.VISIBLE);
        pd.cancel();

    }
}
