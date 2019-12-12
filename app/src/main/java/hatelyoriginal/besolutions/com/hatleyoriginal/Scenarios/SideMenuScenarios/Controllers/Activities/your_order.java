package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Controllers.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.Apicalls;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.NetworkInterface;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.ResponseModel;
import hatelyoriginal.besolutions.com.hatleyoriginal.R;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Models.your_order_list;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Patterns.your_order_adapter;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.utils_adapter;


public class your_order extends AppCompatActivity implements NetworkInterface {

    @BindView(R.id.order_list)
    RecyclerView orderList;
    @BindView(R.id.nodata)
    TextView nodata;
    @BindView(R.id.loading)
    ProgressBar loading;

    Toolbar mToolbar;

    TextView textView;

    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.your_order);
        ButterKnife.bind(this);

        mToolbar = findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);

        getActionBarTextView().setVisibility(View.GONE);

        textView = mToolbar.findViewById(R.id.toolbartext);

        textView.setText("Your Orders");

        back = mToolbar.findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

        new Apicalls(your_order.this, your_order.this).Get_myOrder();


    }

    @Override
    public void OnStart() {

    }

    @Override
    public void OnResponse(ResponseModel model) {

        ArrayList<your_order_list> your_order_list = new ArrayList<>();
        try {
            loading.setVisibility(View.GONE);
            JSONArray jsonArray = model.getJsonObject().getJSONArray("orders");
            for (int index = 0; index < jsonArray.length(); index++) {
                JSONObject object = jsonArray.getJSONObject(index);
                JSONObject order_client = object.getJSONObject("order_client");
                JSONObject order_star = object.getJSONObject("order_star");
                JSONObject order=object.getJSONObject("order");
                JSONObject status=order.getJSONObject("status");

                your_order_list.add(new your_order_list(object.getInt("id"), object.getString("order_details"),
                        5, object.getString("created_at"),object.getString("order_from"),order_client.getString("name"),order_star.getString("name"),status.getString("status")));
            }
            utils_adapter utils_adapter = new utils_adapter();
            utils_adapter.Adapter(orderList, new your_order_adapter(your_order.this, your_order_list), your_order.this);

            if(your_order_list.isEmpty())
            {
                String no_data = this.getString(R.string.no_data);
                nodata.setText(no_data);
                loading.setVisibility(View.GONE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            String no_data = this.getString(R.string.no_data);
            nodata.setText(no_data);
            loading.setVisibility(View.GONE);
        }
    }

    @Override
    public void OnError(VolleyError error) {
        String no_data = this.getString(R.string.no_data);
        nodata.setText(no_data);
        loading.setVisibility(View.GONE);
    }


    private TextView getActionBarTextView() {
        TextView titleTextView = null;

        try {

            Field f = mToolbar.getClass().getDeclaredField("mTitleTextView");
            f.setAccessible(true);
            titleTextView = (TextView) f.get(mToolbar);


        } catch (NoSuchFieldException | IllegalAccessException ignored) {
        }
        return titleTextView;
    }

}
