package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Controllers.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;


import java.lang.reflect.Field;

import androidx.appcompat.widget.Toolbar;
import es.dmoral.toasty.Toasty;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.Apicalls;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.NetworkInterface;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.ResponseModel;
import hatelyoriginal.besolutions.com.hatleyoriginal.R;


public class payments extends AppCompatActivity implements NetworkInterface {

    LinearLayout linearpay;

    TextView txtcredit, txtpromoted, txtearned;

    Toolbar mToolbar;

    TextView textView;

    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payments);

        mToolbar = findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);

        getActionBarTextView().setVisibility(View.GONE);

        textView = mToolbar.findViewById(R.id.toolbartext);

        textView.setText("Payment");

        back = mToolbar.findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });


        linearpay = findViewById(R.id.linearPayment);
        txtcredit = findViewById(R.id.txtCredit);
        txtearned = findViewById(R.id.txtEarned);
        txtpromoted = findViewById(R.id.txtPromoted);



        new Apicalls(payments.this,payments.this).get_balance();


    }

    @Override
    public void OnStart() {

    }

    @Override
    public void OnResponse(ResponseModel model) {

        linearpay.setVisibility(View.VISIBLE);

        try {

            JSONObject jsonObject = model.getJsonObject().getJSONObject("data");

            txtpromoted.setText(jsonObject.getString("promoted_value"));
            txtearned.setText(jsonObject.getString("from_earned"));
            txtcredit.setText(jsonObject.getString("credit"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void OnError(VolleyError error) {

        if (error.networkResponse.statusCode == 500)
        {
            linearpay.setVisibility(View.INVISIBLE);
            Toasty.error(this,"Sorry There Is Nothing To Show ",Toast.LENGTH_LONG).show();


        }else {
            Toasty.error(this,""+error.networkResponse.statusCode,Toast.LENGTH_LONG).show();


        }
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
