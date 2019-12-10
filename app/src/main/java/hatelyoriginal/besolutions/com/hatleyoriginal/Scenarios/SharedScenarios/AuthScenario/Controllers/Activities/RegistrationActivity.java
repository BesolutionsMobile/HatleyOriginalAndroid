package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SharedScenarios.AuthScenario.Controllers.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import androidx.core.widget.NestedScrollView;
import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.Apicalls;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.NetworkInterface;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.ResponseModel;
import hatelyoriginal.besolutions.com.hatleyoriginal.R;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SharedScenarios.AuthScenario.Models.LoginResponse;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SharedScenarios.AuthScenario.Models.User;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SharedScenarios.AuthScenario.Tools.LoginLoading;
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
    @BindView(R.id.policy)
    TextView policy;
    @BindView(R.id.termsser)
    TextView termsser;
    @BindView(R.id.nested)
    NestedScrollView nested;

    ProgressDialog pd;
    String token;

    TinyDB tinyDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        ButterKnife.bind(this);

        tinyDB = new TinyDB(getApplicationContext());

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                token = instanceIdResult.getToken();
                // send it to server
            }
        });

        //REGIST
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!coPassword.getText().toString().equals(password.getText().toString()))
                {
                    coPassword.setError("Password Not Match");
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .repeat(1)
                            .playOn(findViewById(R.id.co_password));
                }
                else if(username.getText().length()<=6)
                {

                    username.setError("Username id too short!");
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .repeat(1)
                            .playOn(findViewById(R.id.username));
                }
                else if(email.getText().toString().equals(""))
                {
                    email.setError("Please enter email");
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .repeat(1)
                            .playOn(findViewById(R.id.email));

                }
                else if(password.getText().toString().equals(""))
                {
                    password.setError("Please enter password");
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .repeat(1)
                            .playOn(findViewById(R.id.password));

                }
                else if(coPassword.getText().toString().equals(""))
                {
                    coPassword.setError("Please confirm password");
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .repeat(1)
                            .playOn(findViewById(R.id.co_password));
                }
                else {
                    //DIALOG PROGRESS
                    pd = new ProgressDialog(RegistrationActivity.this);
                    pd.setMessage("Loading...");
                    pd.show();
                    //CALL API
                    new Apicalls(RegistrationActivity.this,RegistrationActivity.this).insertUser(username.getText().toString()
                            ,email.getText().toString(),password.getText().toString(),coPassword.getText().toString(),token);
                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

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
                tinyDB.putString("userimage",user.getImageId().toString());
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
        RegistrationLoading LoginLoading = new RegistrationLoading();
        LoginLoading.dialog(RegistrationActivity.this,R.layout.loading,.80);

        pd.cancel();
    }

    @Override
    public void OnError(VolleyError error) {
        Toasty.error(this,"Email already exist",Toast.LENGTH_LONG).show();
        pd.cancel();
    }
}
