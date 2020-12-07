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
        textView.setText(getString(R.string.faq));

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


        mylsits.add(new expandlist(1,getString(R.string.ques_1),about));
        about.add(new itemList(1,getString(R.string.ques_1)));

        mylsits.add(new expandlist(1,getString(R.string.ques_2),about1));
        about1.add(new itemList(1,getString(R.string.ans_2)));

        mylsits.add(new expandlist(1,getString(R.string.ques_3),about2));
        about2.add(new itemList(1,getString(R.string.ans_3)));

        mylsits.add(new expandlist(1,getString(R.string.ques_4),about3));
        about3.add(new itemList(1,getString(R.string.ans_4)));

        mylsits.add(new expandlist(1,getString(R.string.ques_5),about4));
        about4.add(new itemList(1,getString(R.string.ans_5)));

        mylsits.add(new expandlist(1,getString(R.string.ques_6),about5));
        about5.add(new itemList(1,getString(R.string.ans_6)));

        mylsits.add(new expandlist(1,getString(R.string.ques_7),about6));
        about6.add(new itemList(1,getString(R.string.ans_7)));

        mylsits.add(new expandlist(1,getString(R.string.ques_8),about7));
        about7.add(new itemList(1,getString(R.string.ans_8)));

        mylsits.add(new expandlist(1,getString(R.string.ques_9),about8));
        about8.add(new itemList(1,getString(R.string.ans_9)));



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