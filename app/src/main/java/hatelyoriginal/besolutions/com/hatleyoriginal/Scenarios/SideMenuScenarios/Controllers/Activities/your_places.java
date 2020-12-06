package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Controllers.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;


import butterknife.BindView;
import butterknife.ButterKnife;
import hatelyoriginal.besolutions.com.hatleyoriginal.MainActivity;
import hatelyoriginal.besolutions.com.hatleyoriginal.R;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Patterns.saved_places_adapter_side;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.TinyDB;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.utils;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.utils_adapter;


public class your_places extends AppCompatActivity {

    @BindView(R.id.places_list)
    RecyclerView placesList;
    @BindView(R.id.add_new_place)
    EditText addNewPlace;
    @BindView(R.id.added_sql)
    ImageView addedSql;
    @BindView(R.id.nodata)
    TextView nodata;

    Toolbar mToolbar;

    TextView textView;

    ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.your_places);
        ButterKnife.bind(this);

        //DEFINE TOOLBAR
        mToolbar = findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);

        getActionBarTextView().setVisibility(View.GONE);

        //SET TOOLBAR TITLE
        textView = mToolbar.findViewById(R.id.toolbartext);
        textView.setText(getString(R.string.saved_places));

        //SET BACK BUTTON
        back = mToolbar.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        //GET DATA ARRAY
        utils local_data = new utils(this);

        utils_adapter utils_adapter = new utils_adapter();
        utils_adapter.Adapter(placesList, new saved_places_adapter_side(this, local_data.RetreiveAllData()), this);

        //SET PLACE HOLDER IF NO DATA
        if (local_data.RetreiveAllData().size() == 0) {
            nodata.setText(getString(R.string.no_data));
        }


        //Go To SAVED PLACE PAGE
        addedSql.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(your_places.this, add_new_place.class));
            }
        });

        //GO TO ADD NEW PLACE ACTIVITY
        addNewPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(your_places.this, add_new_place.class));
            }
        });


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

}
