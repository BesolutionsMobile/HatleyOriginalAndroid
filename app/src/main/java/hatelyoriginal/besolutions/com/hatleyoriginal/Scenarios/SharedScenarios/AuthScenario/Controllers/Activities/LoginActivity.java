package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SharedScenarios.AuthScenario.Controllers.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import hatelyoriginal.besolutions.com.hatleyoriginal.MainActivity;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.Apicalls;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.NetworkInterface;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.ResponseModel;
import hatelyoriginal.besolutions.com.hatleyoriginal.R;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.OrderingScenario.Patterns.available_order_adapter;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SharedScenarios.AuthScenario.Models.LoginResponse;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SharedScenarios.AuthScenario.Models.User;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SharedScenarios.AuthScenario.Tools.LoginLoading;
import hatelyoriginal.besolutions.com.hatleyoriginal.StarActivity;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.TinyDB;
import hatelyoriginal.besolutions.com.hatleyoriginal.fingerprint;

public class LoginActivity extends AppCompatActivity implements NetworkInterface {

    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.remember_me)
    CheckBox rememberMe;
    @BindView(R.id.finger_print)
    CheckBox fingerPrint;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.createacc)
    TextView createacc;

    ProgressDialog pd;

    String token;

    TinyDB tinyDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);

        tinyDB = new TinyDB(getApplicationContext());

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                token = instanceIdResult.getToken();
                // send it to server
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(username.getText().toString().equals(""))
                {
                    username.setError("Please enter username");

                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .repeat(1)
                            .playOn(findViewById(R.id.username));

                    // Toast.makeText(login.this, "Please enter username", Toast.LENGTH_SHORT).show();
                }
                else if(password.getText().toString().equals(""))
                {

                    password.setError("Please enter your password");
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .repeat(1)
                            .playOn(findViewById(R.id.password));

                    //Toast.makeText(login.this, "Please enter password", Toast.LENGTH_SHORT).show();
                }
                else {

                    pd = new ProgressDialog(LoginActivity.this);
                    pd.setMessage("Loading...");
                    pd.show();

                    new Apicalls(LoginActivity.this, LoginActivity.this).loginUser(username.getText().toString(), password.getText().toString(), token);
                }
            }
        });

        ifLogin();


        //SET ON FINGER
        fingerPrint.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    tinyDB.putBoolean("fingerprint",true);
                }
                else {
                    tinyDB.putBoolean("fingerprint",false);
                }
            }
        });

        //GO TO SIGNUP
        createacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });



    }

    //Check If Login Or Not
    private void ifLogin() {


        if (tinyDB.getBoolean("isLoggedIn")) {
            if (tinyDB.getString("userType").equals("1")) {
                if(tinyDB.getBoolean("fingerprint"))
                {
                    startActivity(new Intent(this, fingerprint.class));
                    finish();
                }else {
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }
            } else if (tinyDB.getString("userType").equals("2")) {
                startActivity(new Intent(this, StarActivity.class));
                finish();
            }

        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public void OnStart() {

    }

    @Override
    public void OnResponse(ResponseModel model) {

        Gson gson = new Gson();
        LoginResponse loginResponse = gson.fromJson(model.getResponse(), LoginResponse.class);

        //SAVE token IN LOCAL

        tinyDB.putBoolean("isLoggedIn",true);

        tinyDB.putString("userToken",loginResponse.getToken());

        User user = loginResponse.getUser();

        //Toast.makeText(this, user.getEmail(), Toast.LENGTH_SHORT).show();

        //Save User Info

        tinyDB.putString("userName",user.getName());
        tinyDB.putString("userEmail",user.getEmail());
        tinyDB.putString("userPhone",String.valueOf(user.getPhone()));
        tinyDB.putString("userID",String.valueOf(user.getId()));
        tinyDB.putString("userType",String.valueOf(user.getUserTypeId()));
        tinyDB.putString("userCode",user.getCode());

        //send to save image
        try {
            if(user.getImageId().toString()!=null)
            {
                tinyDB.putString("userImage",user.getImageId().toString());
            }else
                {
                    tinyDB.putString("userImage","image");
                }
        }
        catch (Exception ignored)

        {
            tinyDB.putString("userImage","image");
        }

        //GO TO MAIN
        LoginLoading LoginLoading = new LoginLoading();
        LoginLoading.dialog(LoginActivity.this,R.layout.loading,.80,String.valueOf(user.getUserTypeId()));

        pd.cancel();

    }

    @Override
    public void OnError(VolleyError error) {
        Toasty.error(this,"Wrong User Name Or Password", Toast.LENGTH_LONG,true).show();
        //Toast.makeText(this, "Wrong User Name Or Password", Toast.LENGTH_SHORT).show();
        pd.cancel();
    }
}
