package hatelyoriginal.besolutions.com.hatleyoriginal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import co.infinum.goldfinger.Error;
import co.infinum.goldfinger.Goldfinger;
import es.dmoral.toasty.Toasty;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.TinyDB;

public class fingerprint extends AppCompatActivity {

    Goldfinger goldfinger;

    TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fingerprint);

        tinyDB = new TinyDB(this);

        goldfinger= new Goldfinger.Builder(fingerprint.this).build();
    }
    @Override
    protected void onStart() {
        super.onStart();

        if (goldfinger.hasFingerprintHardware() && goldfinger.hasEnrolledFingerprint()) {

            authenticateUserFingerprint();

        }
    }

    private void authenticateUserFingerprint() {
        final ImageView finger = (ImageView)findViewById(R.id.finger);
        goldfinger.authenticate(new Goldfinger.Callback() {
            @Override
            public void onError(Error error) {
                Toasty.error(fingerprint.this, "Not Match", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onSuccess(String value) {
                //login.setEnabled(true);
                Toasty.success(fingerprint.this, "Successfully", Toast.LENGTH_LONG).show();

                if(tinyDB.getString("userType").equals("1"))
                {
                    startActivity(new Intent(fingerprint.this, MainActivity.class));
                }else
                    {
                        startActivity(new Intent(fingerprint.this, StarActivity.class));
                    }


                finish();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        goldfinger.cancel();
    }
}

