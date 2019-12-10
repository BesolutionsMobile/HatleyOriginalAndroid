package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Controllers.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.lang.reflect.Field;

import androidx.appcompat.widget.Toolbar;
import es.dmoral.toasty.Toasty;
import hatelyoriginal.besolutions.com.hatleyoriginal.R;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.TinyDB;

public class share_earn extends AppCompatActivity {

    TextView code,txtcopy;

    TinyDB tinyDB;

    Toolbar mToolbar;

    TextView textView;

    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_earn);

        tinyDB = new TinyDB(this);

        mToolbar = findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);

        getActionBarTextView().setVisibility(View.GONE);

        textView = mToolbar.findViewById(R.id.toolbartext);

        textView.setText("Share & Earn");

        back = mToolbar.findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

        code = findViewById(R.id.txtshare);
        code.setText(tinyDB.getString("userCode"));

        txtcopy = findViewById(R.id.txtcopy);

        txtcopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("", tinyDB.getString("userCode"));
                assert clipboard != null;
                clipboard.setPrimaryClip(clip);
                Toasty.success(share_earn.this,"Copied", Toast.LENGTH_LONG,true).show();

            }
        });
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


}
