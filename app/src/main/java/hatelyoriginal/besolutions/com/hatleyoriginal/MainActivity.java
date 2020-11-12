package hatelyoriginal.besolutions.com.hatleyoriginal;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
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

    private ProgressDialog pd;

    public static String sDefSystemLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //DEFINE TINY DB
        tinyDB = new TinyDB(this);

        sDefSystemLanguage = Locale.getDefault().getLanguage();

        //DEFINE TOOL BAR
        mToolbar = findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);

        //CHECK LANGUAGE ENG OR AR
        if(sDefSystemLanguage.equals("ar"))
        {
            mToolbar.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }else
            {
                mToolbar.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            }


        getActionBarTextView().setVisibility(View.GONE);

        textView = mToolbar.findViewById(R.id.toolbartext);

        mToolbar.setVisibility(View.GONE);

        // Set up the drawer.
        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.fragment_drawer);
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, findViewById(R.id.drawer), mToolbar);

        //ADD MAP FRAGMENT
        final MapFragment map = new MapFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, map);
        fragmentTransaction.commit();

        Buttons();

        mNavigationDrawerFragment.closeDrawer();

    }


    //ON CLICK BUTTONS
    public void Buttons() {

        //HOME BUTTON
        homebutton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                mToolbar.setVisibility(View.GONE);  //SET TOOLBAR GONE

                //SET HOME ICON COLOR AND TEXT
                homeicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
                hometext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));

                //SET OFFERS ICON COLOR AND TEXT
                offersicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray), PorterDuff.Mode.SRC_IN);
                offerstext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray));

                //SET NOTIFICATIONS ICON COLOR AND TEXT
                notificationsicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray), PorterDuff.Mode.SRC_IN);
                notofocationstext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray));

                //SET SETTINGS ICON COLOR AND TEXT
                settingicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray), PorterDuff.Mode.SRC_IN);
                settingtext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray));


                //ADD MAP FRAGMENT
                final MapFragment map = new MapFragment();

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, map);
                fragmentTransaction.commit();

            }
        });

        //SET ON OFFER BUTTON
        offersbutton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                //SET TOOLBAR TITLE
                mToolbar.setVisibility(View.VISIBLE);
                textView.setText("Available Offers");

                //SET HOME ICON COLOR AND TEXT
                homeicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray), PorterDuff.Mode.SRC_IN);
                offerstext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray));

                //SET OFFERS ICON COLOR AND TEXT
                offersicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
                offerstext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));

                //SET NOTIFICATIONS ICON COLOR AND TEXT
                notificationsicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray), PorterDuff.Mode.SRC_IN);
                notofocationstext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray));

                //SET SETTINGS ICON COLOR AND TEXT
                settingicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray), PorterDuff.Mode.SRC_IN);
                settingtext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray));

                //ADD MAP FRAGMENT
                final OffersFragment map = new OffersFragment();

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, map);
                fragmentTransaction.commit();

            }
        });

        //SET ON NOTIFICATION LISTNER
        notificationsbutton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                //SET TOOLBAR TITLE
                mToolbar.setVisibility(View.VISIBLE);
                textView.setText("Notifications");

                //SET HOME ICON COLOR AND TEXT
                homeicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray), PorterDuff.Mode.SRC_IN);
                hometext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray));

                //SET OFFERS ICON COLOR AND TEXT
                offersicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray), PorterDuff.Mode.SRC_IN);
                offerstext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray));

                //SET NOTIFICATIONS ICON COLOR AND TEXT
                notificationsicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
                notofocationstext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));

                //SET SETTINGS ICON COLOR AND TEXT
                settingicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray), PorterDuff.Mode.SRC_IN);
                settingtext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray));


                //ADD MAP FRAGMENT
                final ClientNotificationsFragment map = new ClientNotificationsFragment();

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, map);
                fragmentTransaction.commit();
            }
        });


        //SET ON SETTING BUTTON LISTNER
        settingbutton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                //SET TOOLBAR TITLE
                mToolbar.setVisibility(View.VISIBLE);
                textView.setText("Settings");

                //SET HOME ICON COLOR AND TEXT
                homeicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray), PorterDuff.Mode.SRC_IN);
                hometext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray));

                //SET OFFERS ICON COLOR AND TEXT
                offersicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray), PorterDuff.Mode.SRC_IN);
                offerstext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray));

                //SET NOTIFICATIONS ICON COLOR AND TEXT
                notificationsicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray), PorterDuff.Mode.SRC_IN);
                notofocationstext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray));

                //SET SETTINGS ICON COLOR AND TEXT
                settingicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
                settingtext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));

                //ADD MAP FRAGMENT
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


            //SET TOOLBAR TITLE
            mToolbar.setVisibility(View.VISIBLE);
            textView.setText("Available Offers");

            //SET HOME ICON COLOR AND TEXT
            homeicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray), PorterDuff.Mode.SRC_IN);
            offerstext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray));

            //SET OFFERS ICON COLOR AND TEXT
            offersicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
            offerstext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));

            //SET NOTIFICATIONS ICON COLOR AND TEXT
            notificationsicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray), PorterDuff.Mode.SRC_IN);
            notofocationstext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray));

            //SET SETTINGS ICON COLOR AND TEXT
            settingicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray), PorterDuff.Mode.SRC_IN);
            settingtext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray));

            //ADD MAP FRAGMENT
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

        //GO TO ACTIVITIES
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
                //STOP DIALOG
                pd = new ProgressDialog(this);
                pd.setMessage("Loading...");
                pd.setCancelable(false);
                pd.show();
                x = 1;
                //CALL API SWITCH USER
                new Apicalls(this, this).switch_user();
                break;
            case 8:
                //STOP DIALOG
                pd = new ProgressDialog(this);
                pd.setMessage("Loading...");
                pd.setCancelable(false);
                pd.show();
                x = 2;
                //CALL API LOGOUT
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
                    //SET TOOLBAR TITLE
                    mToolbar.setVisibility(View.VISIBLE);
                    textView.setText("Available Offers");

                    //SET HOME ICON COLOR AND TEXT
                    homeicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray), PorterDuff.Mode.SRC_IN);
                    offerstext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray));

                    //SET OFFERS ICON COLOR AND TEXT
                    offersicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
                    offerstext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));

                    //SET NOTIFICATIONS ICON COLOR AND TEXT
                    notificationsicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray), PorterDuff.Mode.SRC_IN);
                    notofocationstext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray));


                    //ADD MAP FRAGMENT
                    final OffersFragment map = new OffersFragment();

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container, map);
                    fragmentTransaction.commit();

                } else if (data.equals("finish_order")) {

                    mToolbar.setVisibility(View.GONE); //TOOL BAR GONE

                    //SET HOME ICON COLOR AND TEXT
                    homeicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
                    hometext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));

                    //SET OFFERS ICON COLOR AND TEXT
                    offersicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray), PorterDuff.Mode.SRC_IN);
                    offerstext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray));

                    //SET NOTIFICATIONS ICON COLOR AND TEXT
                    notificationsicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray), PorterDuff.Mode.SRC_IN);
                    notofocationstext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray));


                    //ADD MAP FRAGMENT
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

        //CLOSE DRAWER
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            moveTaskToBack(true);
    }

    //Get ActionBar TextView
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

        pd.cancel();

        if (x == 1) {
            tinyDB.putString("userType","2");
            startActivity(new Intent(this, StarActivity.class));
        } else {
            tinyDB.putBoolean("isLoggedIn", false);
            tinyDB.putBoolean("fingerprint",false);
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    @Override
    public void OnError(VolleyError error) {

    }
}
