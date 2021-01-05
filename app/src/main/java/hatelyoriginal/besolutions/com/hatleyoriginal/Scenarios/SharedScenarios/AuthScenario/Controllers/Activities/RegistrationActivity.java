package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SharedScenarios.AuthScenario.Controllers.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import com.android.volley.VolleyError;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import hatelyoriginal.besolutions.com.hatleyoriginal.LocalData.SendData;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.Apicalls;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.NetworkInterface;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.ResponseModel;
import hatelyoriginal.besolutions.com.hatleyoriginal.R;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SharedScenarios.AuthScenario.Models.LoginResponse;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SharedScenarios.AuthScenario.Models.User;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SharedScenarios.AuthScenario.Tools.RegistrationLoading;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.TinyDB;

public class RegistrationActivity extends AppCompatActivity implements NetworkInterface {

    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.co_password)
    EditText coPassword;
    @BindView(R.id.regist)
    Button regist;
    @BindView(R.id.facebookkkk)
    Button facebook;
    @BindView(R.id.policy)
    TextView policy;
    @BindView(R.id.termsser)
    TextView termsser;
    @BindView(R.id.nested)
    NestedScrollView nested;

    ProgressDialog pd;
    String token;

    TinyDB tinyDB;

    String imageURL;

    String emailValidate;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @BindView(R.id.phone)
    EditText phone;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        ButterKnife.bind(this);

        emailValidate = email.getText().toString().trim();


        tinyDB = new TinyDB(getApplicationContext());

        //GET FIREBASE TOKEN
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                token = instanceIdResult.getToken();
                // send it to server
            }
        });


        //REGIST BUTTON
        regist.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                //VALIDATION
                if (username.getText().toString().equals("")) {
                    username.setError(getString(R.string.please_enter_username));
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .repeat(1)
                            .playOn(findViewById(R.id.username));
                } else if (!coPassword.getText().toString().equals(password.getText().toString())) {
                    //set text error and yoyo library for animation
                    coPassword.setError(getString(R.string.pass_not_match));
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .repeat(1)
                            .playOn(findViewById(R.id.co_password));
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches() ||
                        email.getText().toString().equals("")) {
                    //set text error and yoyo library for animation
                    email.setError(getString(R.string.enter_mail));
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .repeat(1)
                            .playOn(findViewById(R.id.email));

                } else if (phone.getText().toString().length() <= 10) {
                    //set text error and yoyo library for animation
                    phone.setError(getString(R.string.please_enter_phone));
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .repeat(1)
                            .playOn(findViewById(R.id.phone));

                } else if (password.getText().toString().equals("")) {
                    //set text error and yoyo library for animation
                    password.setError(getString(R.string.please_enter_password));
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .repeat(1)
                            .playOn(findViewById(R.id.password));

                } else if (coPassword.getText().toString().equals("")) {
                    //set text error and yoyo library for animation
                    coPassword.setError(getString(R.string.pls_co_pass));
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .repeat(1)
                            .playOn(findViewById(R.id.co_password));
                } else {

                    //DIALOG PROGRESS
                    pd = new ProgressDialog(RegistrationActivity.this);
                    pd.setMessage(getString(R.string.loading));
                    pd.show();
                    //CALL API
                    imageURL = "image";

                    //CALL SIGN UP API
                    new Apicalls(RegistrationActivity.this, RegistrationActivity.this).insertUser(username.getText().toString()
                            , email.getText().toString(), password.getText().toString(), coPassword.getText().toString(),
                            token, imageURL, phone.getText().toString());
                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, LoginActivity.class));
    }


    @Override
    public void OnStart() {

    }

    @Override
    public void OnResponse(ResponseModel model) {


        Gson gson = new Gson();
        LoginResponse loginResponse = gson.fromJson(model.getResponse(), LoginResponse.class);

        //SAVE token IN LOCAL

        tinyDB.putBoolean("isLoggedIn", true);

        tinyDB.putString("userToken", loginResponse.getToken());

        User user = loginResponse.getUser();

        //Save User Info
        tinyDB.putString("userName", user.getName());
        tinyDB.putString("userEmail", user.getEmail());
        tinyDB.putString("userPhone", String.valueOf(user.getPhone()));
        tinyDB.putString("userID", String.valueOf(user.getId()));
        tinyDB.putString("userType", String.valueOf(user.getUserTypeId()));
        tinyDB.putString("userCode", user.getCode());
        SendData.sendCurrentPass(RegistrationActivity.this, password.getText().toString()); //SAVE PASS

        //send to save image
        try {
            if (user.getImageId().toString() != null) {
                tinyDB.putString("userimage", user.getImageId().toString());
            } else {
                tinyDB.putString("userImage", imageURL);
            }
        } catch (Exception ignored) {
            tinyDB.putString("userImage", imageURL);
        }

        //GO TO MAIN
        RegistrationLoading loading = new RegistrationLoading();
        loading.dialog(RegistrationActivity.this, R.layout.loading, .80);

        pd.cancel();
    }

    @Override
    public void OnError(VolleyError error) {

        Toasty.error(this, getString(R.string.email_exist), Toast.LENGTH_LONG).show();
        pd.cancel();
    }

}
