package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Controllers.Activities;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import hatelyoriginal.besolutions.com.hatleyoriginal.R;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Models.expandlist;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Models.itemList;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Patterns.FAQ.expandAdapter;


import java.lang.reflect.Field;
import java.util.ArrayList;


public class faq extends AppCompatActivity {

    RecyclerView list;
    RecyclerView.LayoutManager layoutManager;

    Toolbar mToolbar;

    TextView textView;

    ImageView back;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq);

        mToolbar = findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);

        getActionBarTextView().setVisibility(View.GONE);


        //  SET TOOLBAR TITLE
        textView = mToolbar.findViewById(R.id.toolbartext);
        textView.setText("FAQ");

        //ON CLICK BACK TOOL BAR
        back = mToolbar.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

        //FAQ  QUESTIONS AND ANSWERS

        RecyclerView recyclerViewexpand= findViewById(R.id.recycle);

        ArrayList mylsits = new ArrayList();

        ArrayList about = new ArrayList();
        ArrayList about1 = new ArrayList();
        ArrayList about2 = new ArrayList();
        ArrayList about3 = new ArrayList();
        ArrayList about4 = new ArrayList();
        ArrayList about5 = new ArrayList();
        ArrayList about6 = new ArrayList();
        ArrayList about7 = new ArrayList();
        ArrayList about8 = new ArrayList();


        mylsits.add(new expandlist(1,"HOW CAN I CHANGE MY SHIPPING ADDRESS?",about));
        about.add(new itemList(1,"By default, the last used shipping address will be saved into to your Sample Store account. When you are checking out your order, the default shipping address will be displayed and you have the option to amend it if you need to."));

        mylsits.add(new expandlist(1,"HOW CAN I USE MY REMAINING ACCOUNT CREDITS?",about1));
        about1.add(new itemList(1,"We are in the process of removing the option to pay for your orders by ‘Account Credits’. If you have remaining credits in your account, it will be used to pay for your next checkout. If there are insufficient credits, the system will direct you automatically to pay the balance via Paypal."));

        mylsits.add(new expandlist(1,"HOW DO I ACTIVATE MY ACCOUNT?",about2));
        about2.add(new itemList(1,"The instructions to activate your account will be sent to your email once you have submitted the registration form. If you did not receive this email, your email service provider’s mailing software may be blocking it. You can try checking your junk / spam folder or contact us at help@samplestore.com"));

        mylsits.add(new expandlist(1,"WHY MUST I MAKE PAYMENT IMMEDIATELY AT CHECKOUT?\n" +
                "\n",about3));
        about3.add(new itemList(1,"Sample ordering is on ‘first-come-first-served’ basis. To ensure that you get your desired samples, it is recommended that you make your payment within 60 minutes of checking out."));

        mylsits.add(new expandlist(1,"WHAT DO YOU MEAN BY POINTS? HOW DO I EARN IT?",about4));
        about4.add(new itemList(1,"Because you are important to us, we want to know what you think about the products. As an added value, every time you rate the products you earn points which go straight to your account. 1 point are added to your account for every review that you give. You will need those points in order to redeem the sample products. So keep rating the products to keep earning points!"));

        mylsits.add(new expandlist(1,"HOW MANY FREE SAMPLES CAN I REDEEM?",about5));
        about5.add(new itemList(1,"Due to the limited quantity, each member's account is only entitled to 1 unique free sample. You can check out up to 4 free samples in each checkout."));

        mylsits.add(new expandlist(1,"WHAT HAPPENS IF THERE'S BEEN A DELIVERY MISHAP TO MY ORDER? (DAMAGED OR LOST DELIVERY)",about6));
        about6.add(new itemList(1,"We take such matters very seriously and will look into individual cases thoroughly. Any sample that falls under the below categories should not be thrown away before taking photo proof and emailing the photo of the affected sample and your D.O (Delivery Order) to us at help@samplestore.com (if applicable)"));

        mylsits.add(new expandlist(1,"HOW CAN I TRACK MY ORDERS & PAYMENT?",about7));
        about7.add(new itemList(1,"After logging into your account, the status of your checkout history can be found under Order History. For orders via registered postage, a tracking number (article tracking number) will be given to you after the receipt given from Singapore Post Limited (SingPost)."));

        mylsits.add(new expandlist(1,"HOW DO I CANCEL MY ORDERS BEFORE I MAKE A PAYMENT?" +
                "\n",about8));
        about8.add(new itemList(1,"After logging into your account, go to your Shopping Cart. Here, you will be able to make payment or cancel your order. Note: We cannot give refunds once payment is verified"));



        RecyclerView.LayoutManager layoutManagerr = new LinearLayoutManager(this);
        recyclerViewexpand.setLayoutManager(layoutManagerr);
        RecyclerView.Adapter adapterr = new expandAdapter(mylsits,faq.this);
        recyclerViewexpand.setAdapter(adapterr);
    }

    //get ActionBar TextView
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

}