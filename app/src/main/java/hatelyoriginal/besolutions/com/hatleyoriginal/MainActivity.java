package hatelyoriginal.besolutions.com.hatleyoriginal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.Apicalls;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.NetworkInterface;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.ResponseModel;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.ClientNotesScenario.Controllers.Fragments.ClientNotificationsFragment;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.MainScenario.Controllers.Fragments.MapFragment;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.MainScenario.Controllers.Fragments.OffersFragment;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.MainScenario.Controllers.Fragments.SettingsFragment;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SharedScenarios.AuthScenario.Controllers.Activities.LoginActivity;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Controllers.Activities.faq;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Controllers.Activities.payments;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Controllers.Activities.personal_info;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Controllers.Activities.promo_code;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Controllers.Activities.share_earn;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Controllers.Activities.your_order;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Controllers.Activities.your_places;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.AddButtonClick;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.TinyDB;


public class MainActivity extends AppCompatActivity implements NavigationDrawerCallbacks, NetworkInterface {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    @SuppressLint("StaticFieldLeak")
    public static NavigationDrawerFragment mNavigationDrawerFragment;
    @BindView(R.id.homeicon)
    ImageView homeicon;
    @BindView(R.id.hometext)
    TextView hometext;
    @BindView(R.id.homebutton)
    LinearLayout homebutton;
    @BindView(R.id.offersicon)
    ImageView offersicon;
    @BindView(R.id.offerstext)
    TextView offerstext;
    @BindView(R.id.offersbutton)
    LinearLayout offersbutton;
    @BindView(R.id.notificationsicon)
    ImageView notificationsicon;
    @BindView(R.id.notofocationstext)
    TextView notofocationstext;
    @BindView(R.id.notificationsbutton)
    LinearLayout notificationsbutton;

    Toolbar mToolbar;

    TextView textView;

    TinyDB tinyDB;

    int x = 0;
    @BindView(R.id.settingicon)
    ImageView settingicon;
    @BindView(R.id.settingtext)
    TextView settingtext;
    @BindView(R.id.settingbutton)
    LinearLayout settingbutton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        tinyDB = new TinyDB(this);

        mToolbar = findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);

        getActionBarTextView().setVisibility(View.GONE);

        textView = mToolbar.findViewById(R.id.toolbartext);

        mToolbar.setVisibility(View.GONE);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.fragment_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, findViewById(R.id.drawer), mToolbar);

        final MapFragment map = new MapFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, map);
        fragmentTransaction.commit();

        Buttons();

        mNavigationDrawerFragment.closeDrawer();

    }


    public void Buttons() {

        homebutton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                mToolbar.setVisibility(View.GONE);

                homeicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
                hometext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));

                offersicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray), PorterDuff.Mode.SRC_IN);
                offerstext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray));

                notificationsicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray), PorterDuff.Mode.SRC_IN);
                notofocationstext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray));

                settingicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray), PorterDuff.Mode.SRC_IN);
                settingtext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray));


                final MapFragment map = new MapFragment();

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, map);
                fragmentTransaction.commit();

            }
        });

        offersbutton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                mToolbar.setVisibility(View.VISIBLE);
                textView.setText("Available Offers");

                homeicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray), PorterDuff.Mode.SRC_IN);
                offerstext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray));

                offersicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
                offerstext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));

                notificationsicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray), PorterDuff.Mode.SRC_IN);
                notofocationstext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray));

                settingicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray), PorterDuff.Mode.SRC_IN);
                settingtext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray));

                final OffersFragment map = new OffersFragment();

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, map);
                fragmentTransaction.commit();

            }
        });

        notificationsbutton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                mToolbar.setVisibility(View.VISIBLE);
                textView.setText("Notifications");

                homeicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray), PorterDuff.Mode.SRC_IN);
                hometext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray));

                offersicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray), PorterDuff.Mode.SRC_IN);
                offerstext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray));

                notificationsicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
                notofocationstext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));

                settingicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray), PorterDuff.Mode.SRC_IN);
                settingtext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray));


                final ClientNotificationsFragment map = new ClientNotificationsFragment();

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, map);
                fragmentTransaction.commit();
            }
        });


        settingbutton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                mToolbar.setVisibility(View.VISIBLE);
                textView.setText("Settings");

                homeicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray), PorterDuff.Mode.SRC_IN);
                hometext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray));

                offersicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray), PorterDuff.Mode.SRC_IN);
                offerstext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray));

                notificationsicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray), PorterDuff.Mode.SRC_IN);
                notofocationstext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray));

                settingicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
                settingtext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));

                final SettingsFragment map = new SettingsFragment();

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, map);
                fragmentTransaction.commit();
            }
        });

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onButtonClick(AddButtonClick addButtonClick) {

        if (addButtonClick.getEvent().equals("Rejected")) {


            mToolbar.setVisibility(View.VISIBLE);
            textView.setText("Available Offers");

            homeicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray), PorterDuff.Mode.SRC_IN);
            offerstext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray));

            offersicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
            offerstext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));

            notificationsicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray), PorterDuff.Mode.SRC_IN);
            notofocationstext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray));

            settingicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray), PorterDuff.Mode.SRC_IN);
            settingtext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray));

            final OffersFragment map = new OffersFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, map);
            fragmentTransaction.commit();


        }

    }


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments

        switch (position) {
            case 0:
                startActivity(new Intent(this, personal_info.class));
                break;
            case 1:
                startActivity(new Intent(this, share_earn.class));
                break;
            case 2:
                startActivity(new Intent(this, promo_code.class));
                break;
            case 3:
                startActivity(new Intent(this, payments.class));
                break;
            case 4:
                startActivity(new Intent(this, faq.class));
                break;
            case 5:
                startActivity(new Intent(this, your_order.class));
                break;
            case 6:
                startActivity(new Intent(this, your_places.class));
                break;
            case 7:
                x = 1;
                new Apicalls(this, this).switch_user();
                break;
            case 8:
                x = 2;
                new Apicalls(this, this).Logout();
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent != null) {

            String data = intent.getStringExtra("data");

            if (data != null) {

                if (data.equals("submit_offer")) {
                    mToolbar.setVisibility(View.VISIBLE);
                    textView.setText("Available Offers");

                    homeicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray), PorterDuff.Mode.SRC_IN);
                    offerstext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray));

                    offersicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
                    offerstext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));

                    notificationsicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray), PorterDuff.Mode.SRC_IN);
                    notofocationstext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray));

                    final OffersFragment map = new OffersFragment();

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container, map);
                    fragmentTransaction.commit();

                } else if (data.equals("finish_order")) {

                    mToolbar.setVisibility(View.GONE);

                    homeicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
                    hometext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));

                    offersicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray), PorterDuff.Mode.SRC_IN);
                    offerstext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray));

                    notificationsicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray), PorterDuff.Mode.SRC_IN);
                    notofocationstext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray));


                    final MapFragment map = new MapFragment();

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container, map);
                    fragmentTransaction.commit();

                }


            }


        }


    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            moveTaskToBack(true);
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


    @Override
    public void OnStart() {

    }

    @Override
    public void OnResponse(ResponseModel model) {

        if (x == 1) {
            startActivity(new Intent(this, StarActivity.class));
        } else {
            tinyDB.putBoolean("isLoggedIn", false);
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    @Override
    public void OnError(VolleyError error) {

    }
}
