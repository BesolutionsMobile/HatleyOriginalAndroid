package hatelyoriginal.besolutions.com.hatleyoriginal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SharedScenarios.AuthScenario.Controllers.Activities.LoginActivity;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.TinyDB;

public class splash_screen extends AppCompatActivity {

    @BindView(R.id.logo)
    ImageView logo;
    private int progressStatus = 0;
    ProgressBar progressBar;
    private Handler handler = new Handler();

    TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        ButterKnife.bind(this);

        tinyDB = new TinyDB(this);

        progressBar = findViewById(R.id.progressBar);

        YoYo.with(Techniques.BounceInDown).repeat(0).duration(1500).playOn(logo);


        if (progressStatus == 100) {
            progressStatus = 0;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progressStatus < 100) {
                    // Update the progress status
                    progressStatus += 1;

                    try {
                        Thread.sleep(20);  //3 seconds
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Update the progress bar
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(progressStatus);
                        }
                    });
                }
            }
        }).start();

        if (progressStatus == 100) {

        } else if (tinyDB.getString("slider").equals("1")) {

            splash_screen(splash_screen.this, LoginActivity.class);
        } else {

            splash_screen(splash_screen.this, slider_pages.class);

        }


    }


    public void splash_screen(final Context context, final Class second_class)
    {
        new Thread(new Runnable() {
            public void run() {
                try {
                    // sleep during 800ms
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // start HomeActivity
                Intent intent = new Intent(context, second_class);
                context.startActivity(intent);
                ((AppCompatActivity)context).finish();
            }
        }).start();
    }


}
