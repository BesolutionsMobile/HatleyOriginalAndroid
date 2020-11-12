package hatelyoriginal.besolutions.com.hatleyoriginal;

import android.content.Intent;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SharedScenarios.AuthScenario.Controllers.Activities.LoginActivity;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.TinyDB;

public class slider_pages extends AppIntro {

    TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //ADD SLIDES
        tinyDB =  new TinyDB(this);

        tinyDB.putString("slider","1");

        addSlide(AppIntroFragment.newInstance("Get Your Order", "Get your order from any place you want when ever you want you dont have to do anything we are here to make it easy for you just get out our phone and make your order  ",
                R.drawable.order, ContextCompat.getColor(getApplicationContext(), R.color.fcolor)));
        addSlide(AppIntroFragment.newInstance("Start Make Money", " Register as a star and start to make money you can make money for each time you complete an order your money depend on how much order you take and how much stars you get just get started and enjoy your cash",
                R.drawable.money, ContextCompat.getColor(getApplicationContext(), R.color.fcolor)));
        addSlide(AppIntroFragment.newInstance("More Than 4000 Star", "Your order will be covered with more than 4000 star you will get what ever you want when ever you what just make your order and leave the rest on us ",
                R.drawable.scooter, ContextCompat.getColor(getApplicationContext(), R.color.fcolor)));

    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        //WHEN DONE GO TO LOGIN
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        //WHEN SKIP GO TO LOGIN
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);

    }
}




