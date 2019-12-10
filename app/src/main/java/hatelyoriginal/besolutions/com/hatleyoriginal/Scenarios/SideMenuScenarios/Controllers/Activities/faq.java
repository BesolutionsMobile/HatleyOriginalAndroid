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

        textView = mToolbar.findViewById(R.id.toolbartext);

        textView.setText("FAQ");

        back = mToolbar.findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });
/*
        list = findViewById(R.id.recycle);
        list.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);
        setData();
 */
        String[]faqs={
                "Question Number 1",
                "Question Number 2",
                "Question Number 3",
                "Question Number 4",
                "Question Number 5",
                "Question Number 6",
                "Question Number 7",
                "Question Number 8",
                "Question Number 9"
        };

        String[][]answer={{

                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                        "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                        "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                        "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                        "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                        "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                        "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                        "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                        "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"},

                {

                        "Answer To Question Number2 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"},
                {

                        "Answer To Question Number3 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"},
                {

                        "Answer To Question Number4 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"},
                {

                        "Answer To Question Number4 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"},
                {

                        "Answer To Question Number5 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"},
                {

                        "Answer To Question Number6 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"},
                {

                        "Answer To Question Number7 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"},
                {

                        "Answer To Question Number8 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"},
                {

                        "Answer To Question Number9 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"+
                                "Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 Answer To Question Number1 \n\n"},





        };

        RecyclerView recyclerViewexpand= findViewById(R.id.recycle);

        ArrayList<expandlist>mylsits=new ArrayList();

        ArrayList<itemList>about=new ArrayList();
        ArrayList<itemList>about1=new ArrayList();
        ArrayList<itemList>about2=new ArrayList();
        ArrayList<itemList>about3=new ArrayList();
        ArrayList<itemList>about4=new ArrayList();
        ArrayList<itemList>about5=new ArrayList();
        ArrayList<itemList>about6=new ArrayList();
        ArrayList<itemList>about7=new ArrayList();
        ArrayList<itemList>about8=new ArrayList();



        mylsits.add(new expandlist(1,"Whats your name?",about));
        about.add(new itemList(1,"My name is Hatly"));

        mylsits.add(new expandlist(1,"Whats your name of your best friend?",about1));
        about1.add(new itemList(1,"his name is Hatly"));

        mylsits.add(new expandlist(1,"Whats your name of your best friend?",about2));
        about2.add(new itemList(1,"his name is Hatly"));

        mylsits.add(new expandlist(1,"Whats your name of your best friend?",about3));
        about3.add(new itemList(1,"his name is Hatly"));

        mylsits.add(new expandlist(1,"Whats your name of your best friend?",about4));
        about4.add(new itemList(1,"his name is Hatly"));

        mylsits.add(new expandlist(1,"Whats your name of your best friend?",about5));
        about5.add(new itemList(1,"his name is Hatly"));

        mylsits.add(new expandlist(1,"Whats your name of your best friend?",about6));
        about6.add(new itemList(1,"his name is Hatly"));

        mylsits.add(new expandlist(1,"Whats your name of your best friend?",about7));
        about7.add(new itemList(1,"his name is Hatly"));

        mylsits.add(new expandlist(1,"Whats your name of your best friend?",about8));
        about8.add(new itemList(1,"his name is Hatly"));



        RecyclerView.LayoutManager layoutManagerr=new LinearLayoutManager(this);
        recyclerViewexpand.setLayoutManager(layoutManagerr);
        RecyclerView.Adapter adapterr=new expandAdapter(mylsits,faq.this);
        recyclerViewexpand.setAdapter(adapterr);
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
/*
    private void setData() {
        for (int i = 0; i<20;i++)
        {
            if (i%1==0)
            {
                items itim = new items("This Is Item "+(i+1),"This Is Child "+(i+1),true);
                item.add(itim);
            }
            else
            {
                items itim = new items("This Is Item "+(i+1),"",false);
                item.add(itim);
            }
        }
        my_Adapter adapter = new my_Adapter(item);
        list.setAdapter(adapter);
    }
 */


}