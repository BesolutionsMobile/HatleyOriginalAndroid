package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SharedScenarios.AuthScenario.Controllers.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.infinum.goldfinger.Goldfinger;
import es.dmoral.toasty.Toasty;
import hatelyoriginal.besolutions.com.hatleyoriginal.LocalData.SavedData;
import hatelyoriginal.besolutions.com.hatleyoriginal.MainActivity;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.Apicalls;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.NetworkInterface;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.ResponseModel;
import hatelyoriginal.besolutions.com.hatleyoriginal.R;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SharedScenarios.AuthScenario.Models.LoginResponse;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SharedScenarios.AuthScenario.Models.User;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SharedScenarios.AuthScenario.Tools.LoginLoading;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SharedScenarios.AuthScenario.Tools.RegistrationLoading;
import hatelyoriginal.besolutions.com.hatleyoriginal.StarActivity;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.TinyDB;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.utils;
import hatelyoriginal.besolutions.com.hatleyoriginal.fingerprint;
import hatelyoriginal.besolutions.com.hatleyoriginal.splash_screen;

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
    @BindView(R.id.facebook)
    Button facebook;
    @BindView(R.id.createacc)
    TextView createacc;
    @BindView(R.id.imagehatly)
    ImageView image;

    Goldfinger goldfinger;

    ProgressDialog pd;

    String token;

    TinyDB tinyDB;

    String imageURL;

    String phone = "";

    CallbackManager callbackManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);

        //CHANGE LANGUAGE
        new utils(this).set_language(new SavedData().get_lan(this), LoginActivity

                .this);

        //build finger print
        goldfinger= new Goldfinger.Builder(LoginActivity.this).build();

        tinyDB = new TinyDB(getApplicationContext());

        //GET FIREBASE TOKEN
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                token = instanceIdResult.getToken();
                // send it to server
            }
        });


        //FB LOGIN
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fb_login();

                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile","user_friends","user_photos","email","user_birthday","public_profile","contact_email"));

            }
        });

        //LOGIN BUTTON
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // USER INPUT VALIDATION
                if(username.getText().toString().equals(""))
                {
                    username.setError(getString(R.string.please_enter_username));

                    //YOYO LIBRARY TO SHAKE INPUTS
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .repeat(1)
                            .playOn(findViewById(R.id.username));

                    // Toast.makeText(login.this, "Please enter username", Toast.LENGTH_SHORT).show();
                }
                else if(password.getText().toString().equals(""))
                {

                    password.setError(getString(R.string.please_enter_password));
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .repeat(1)
                            .playOn(findViewById(R.id.password));

                    //Toast.makeText(login.this, "Please enter password", Toast.LENGTH_SHORT).show();
                }
                else {
                  //progress bar loading
                    pd = new ProgressDialog(LoginActivity.this);
                    pd.setMessage(getString(R.string.loading));
                    pd.setCancelable(false);
                    pd.show();

                    imageURL = "image";


                     //CALL LOGIN API
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
                finish();
            }
        });



    }

    private void registration(String username,String email,String password,String userImage)
    {
        pd = new ProgressDialog(LoginActivity.this);
        pd.setMessage(getString(R.string.loading));
        pd.setCancelable(false);
        pd.show();

        imageURL = userImage;

        new Apicalls(LoginActivity.this,LoginActivity.this).insertUser(username
                ,email,password,password,token,userImage);
    }


    private void login(String email,String password,String userImage)
    {

        imageURL = userImage;

        new Apicalls(LoginActivity.this, LoginActivity.this).loginUser(email, password, token);

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
                if(tinyDB.getBoolean("fingerprint"))
                {
                    startActivity(new Intent(this, fingerprint.class));
                    finish();
                }else
                    {
                        startActivity(new Intent(this, StarActivity.class));
                        finish();
                    }

            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        //CHECK FOR THE FINGERPRINT IS FOUND IN THE DEVICE OR NOT  IF NOT WE WILL HIDE IT
        if (!goldfinger.hasFingerprintHardware()) {

            fingerPrint.setVisibility(View.GONE);

        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        goldfinger.cancel();
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

            //Save User Info TO LOCAL

            tinyDB.putString("userName",user.getName());
            tinyDB.putString("userEmail",user.getEmail());
            try {
                if(!user.getPhone().equals("null") || user.getPhone() != null)
                {
                    tinyDB.putString("userPhone",String.valueOf(user.getPhone()));

                }
            }catch (Exception ignored)
            {
                tinyDB.putString("userPhone",phone);
            }

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
                    tinyDB.putString("userImage",imageURL);
                }
            }
            catch (Exception ignored)

            {
                tinyDB.putString("userImage",imageURL);
            }

            //GO TO MAIN SCREEN
            LoginLoading loading = new LoginLoading();
            loading.dialog(LoginActivity.this,R.layout.loading,.80,String.valueOf(user.getUserTypeId()));

            //CANCEL PROGRESS BAR
            pd.cancel();



    }

    @Override
    public void OnError(VolleyError error) {

        Log.e("www_oo",""+error.networkResponse);
        if(error.networkResponse.statusCode == 401)
        {
            Toasty.error(this,"Wrong User Name Or Password", Toast.LENGTH_LONG,true).show();
            pd.cancel();
        }else
            {
                login(tinyDB.getString("facebookEmail"),tinyDB.getString("facebookPassword"),tinyDB.getString("facebookUserImage"));

            }


    }


    //FACEBOOK LOGIN
    private void fb_login() {

        FacebookSdk.sdkInitialize(this);

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        loadUserProfile(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException exception) {
                    }
                });


        /*fb_login = findViewById(R.id.fb_login);
        fb_login.setReadPermissions("public_profile","user_friends","user_photos","email","user_birthday","public_profile","contact_email");
        fb_login.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        fb_login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {

                Toasty.error(LoginActivity.this,error.getMessage(), Toast.LENGTH_LONG,true).show();

            }
        });*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

    }

    //AccessTokenTracker
    AccessTokenTracker tokenTracker = new AccessTokenTracker() {

        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken)
        {
            AccessToken.setCurrentAccessToken(null);
            Profile.setCurrentProfile(null);
            if(currentAccessToken==null)
            {}
           // else
               // loadUserProfile(currentAccessToken);
        }
    };


    //GET DATA FROM FACEBOOK
    private void loadUserProfile(AccessToken newAccessToken)
    {
        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {

            @Override
            public void onCompleted(JSONObject object, GraphResponse response)
            {
                try {
                    String first_name = object.getString("first_name");
                    String last_name = object.getString("last_name");
                    String id = object.getString("id");
                    String email = object.getString("email");
                    String userImage = "https://graph.facebook.com/"+object.getString("id")+"/picture";

                    tinyDB.putString("facebookUsername",first_name + " " + last_name);
                    tinyDB.putString("facebookPassword",id);
                    tinyDB.putString("facebookEmail",email);
                    tinyDB.putString("facebookUserImage",userImage);

                    registration(first_name +" " + last_name,email,id,userImage);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields","first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();
    }

}
