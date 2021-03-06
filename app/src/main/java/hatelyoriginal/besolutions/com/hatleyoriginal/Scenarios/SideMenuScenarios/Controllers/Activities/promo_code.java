package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Controllers.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Models.RootClass;

public class promo_code extends AppCompatActivity implements NetworkInterface {

    EditText editpromo;
    TextView txtsend, txtdiscount;
    RootClass rootClass;

    LinearLayout linearpromo;

    Toolbar mToolbar;

    TextView textView;

    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promo_code);

        //DEFINE TOOLBAR
        mToolbar = findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);

        getActionBarTextView().setVisibility(View.GONE);

        //SET TOOLBAR TITLE
        textView = mToolbar.findViewById(R.id.toolbartext);
        textView.setText(getString(R.string.promo_code));

        //SET BACK BUTTON
        back = mToolbar.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

        //DEFINE ALL VARS
        editpromo = findViewById(R.id.editPromo);
        txtsend = findViewById(R.id.txtSend);
        txtdiscount = findViewById(R.id.txtDiscount);
        linearpromo = findViewById(R.id.linearAddSuccessfully);


        //SEND PROMO BUTTON
        txtsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //CALL PROMO CODE API
                new Apicalls(promo_code.this, promo_code.this).promoCode(editpromo.getText().toString());
            }
        });


    }


    @Override
    public void OnStart() {

    }

    @Override
    public void OnResponse(ResponseModel model) {

        try {
            JSONObject jsonObject = model.getJsonObject().getJSONObject("codeInfo");

            txtdiscount.setText(jsonObject.getString("discount_amount"));
            linearpromo.setVisibility(View.VISIBLE);


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    // Get ActionBar TextView
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

    @Override
    public void OnError(VolleyError error) {

        //VALIDATION ON ERROR RESPONSE
        if (error.networkResponse.statusCode == 422) {
            Toast.makeText(this, getString(R.string.code_already_used), Toast.LENGTH_SHORT).show();
            linearpromo.setVisibility(View.INVISIBLE);

        } else if (error.networkResponse.statusCode == 423) {

            Toasty.error(this, getString(R.string.promo_not_started), Toast.LENGTH_LONG).show();

            linearpromo.setVisibility(View.INVISIBLE);


        } else if (error.networkResponse.statusCode == 424) {
            Toasty.error(this, getString(R.string.promo_expired), Toast.LENGTH_LONG).show();

            linearpromo.setVisibility(View.INVISIBLE);

        } else if (error.networkResponse.statusCode == 425) {

            Toasty.error(this, getString(R.string.promo_not_valid), Toast.LENGTH_LONG).show();
            linearpromo.setVisibility(View.INVISIBLE);

        } else if (error.networkResponse.statusCode == 404) {

            Toasty.error(this, getString(R.string.promo_not_found), Toast.LENGTH_LONG).show();
            linearpromo.setVisibility(View.INVISIBLE);

        } else {
            Toasty.error(this, "", Toast.LENGTH_LONG).show();
        }

    }
}