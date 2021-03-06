package hatelyoriginal.besolutions.com.hatleyoriginal;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;
import java.util.Locale;

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
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.MainScenario.Controllers.Fragments.OffersFragment;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SharedScenarios.AuthScenario.Controllers.Activities.LoginActivity;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Controllers.Activities.payments;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Controllers.Activities.personal_info;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Controllers.Activities.your_order;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.StarScenarios.GetOrdersScenario.Controllers.Fragments.OrdersFragment;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.AddButtonClick;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.TinyDB;

public class StarActivity extends AppCompatActivity implements NavigationDrawerCallbacks,NetworkInterface {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    @SuppressLint("StaticFieldLeak")
    public static NavigationDrawerFragmentStar mNavigationDrawerFragment;
    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.homeicon)
    ImageView homeicon;
    @BindView(R.id.hometext)
    TextView hometext;
    @BindView(R.id.homebutton)
    LinearLayout homebutton;
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

    private ProgressDialog pd;

    public static String sDefSystemLanguage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star);
        ButterKnife.bind(this);

        //DEFINE VARS
        tinyDB = new TinyDB(this);
        sDefSystemLanguage = Locale.getDefault().getLanguage();

        //TOOLBAR
        mToolbar = findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);




        getActionBarTextView().setVisibility(View.GONE);

        //SET TOOL TITLES
        textView = mToolbar.findViewById(R.id.toolbartext);
        textView.setText(getString(R.string.available_orders));

        mNavigationDrawerFragment = (NavigationDrawerFragmentStar) getFragmentManager().findFragmentById(R.id.fragment_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, findViewById(R.id.drawer), mToolbar);


        //ADD FRAGMENT MAP
        final OrdersFragment map = new OrdersFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, map);
        fragmentTransaction.commit();

        Buttons();


        mNavigationDrawerFragment.closeDrawer();

    }

    //SET ON CLICK BUTTONS
    public void Buttons() {

        //SET ON CLICK HOME
        homebutton.setOnClickListener(view -> {

            //SET TITLE
            textView.setText(R.string.available_orders);

            //SET HOME ICON AND TEXT COLOR
            homeicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
            hometext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));

            //SET NOTIFICATIONS ICON AND TEXT COLOR
            notificationsicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray), PorterDuff.Mode.SRC_IN);
            notofocationstext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray));


            //ADD MAP FRAGMENT
            final OrdersFragment map = new OrdersFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, map);
            fragmentTransaction.commit();


        });

        //SET ON NOTIFICATIONS BUTTON
        notificationsbutton.setOnClickListener(view -> {

            //SET TITLE
            textView.setText(getString(R.string.notifications));

            //SET HOME ICON AND TEXT COLOR
            homeicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray), PorterDuff.Mode.SRC_IN);
            hometext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray));

            //SET NOTIFICATIONS ICON AND TEXT COLOR
            notificationsicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
            notofocationstext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));

            //ADD MAP FRAGMENT
            final ClientNotificationsFragment map = new ClientNotificationsFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, map);
            fragmentTransaction.commit();

        });
    }




    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent != null) {

            String data = intent.getStringExtra("data");

            if (data != null) {

                //SET TITLE
                textView.setText(getString(R.string.available_orders));

                //SET HOME ICON AND TEXT COLOR
                homeicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
                hometext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));

                //SET NOTIFICATIONS ICON AND TEXT COLOR
                notificationsicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray), PorterDuff.Mode.SRC_IN);
                notofocationstext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray));

                //ADD MAP FRAGMENT
                final OrdersFragment map = new OrdersFragment();

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, map);
                fragmentTransaction.commit();


            }


        }


    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onButtonClick(AddButtonClick addButtonClick)
    {

        if(addButtonClick.getEvent().equals("Submitted"))
        {

            //SET TITLE
            textView.setText(getString(R.string.available_orders));

            //SET HOME ICON AND TEXT COLOR
            homeicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
            hometext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));

            //SET NOTIFICATIONS ICON AND TEXT COLOR
            notificationsicon.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray), PorterDuff.Mode.SRC_IN);
            notofocationstext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDarkerGray));


            //ADD MAP FRAGMENT
            final OrdersFragment map = new OrdersFragment();

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
        switch (position)
        {
            case 0:
                startActivity(new Intent(this, personal_info.class));
                break;
            case 1:
                startActivity(new Intent(this, payments.class));
                break;
            case 2:
                startActivity(new Intent(this, your_order.class));
                break;
            case 3:
                //SHOW DIALOG
                pd = new ProgressDialog(this);
                pd.setMessage(getString(R.string.loading));
                pd.setCancelable(false);
                pd.show();
                x = 1;
                //CALL API SWITCH USER
                new Apicalls(this, this).switch_user();
                break;
            case 4:
                //SHOW DIALOG
                pd = new ProgressDialog(this);
                pd.setMessage(getString(R.string.loading));
                pd.setCancelable(false);
                pd.show();
                x = 2;

                //CALL API LOGOUT
                new Apicalls(this,this).Logout();
                break;
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

        if(x == 1)
        {
            tinyDB.putString("userType","1");
            startActivity(new Intent(this, MainActivity.class));
        }else
            {
                tinyDB.putBoolean("isLoggedIn",false);
                tinyDB.putBoolean("fingerprint",false);
                startActivity(new Intent(this, LoginActivity.class));
            }



    }

    @Override
    public void OnError(VolleyError error) {

    }
}
