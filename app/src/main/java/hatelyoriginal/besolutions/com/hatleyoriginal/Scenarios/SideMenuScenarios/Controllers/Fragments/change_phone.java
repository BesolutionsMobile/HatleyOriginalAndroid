package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Controllers.Fragments;

/**
 *CHANGE PHONE DIALOG
 */
import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;

import java.util.Objects;

import es.dmoral.toasty.Toasty;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.Apicalls;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.NetworkInterface;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.ResponseModel;
import hatelyoriginal.besolutions.com.hatleyoriginal.R;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.TinyDB;


public class change_phone implements NetworkInterface {

    private Context context;
    private Dialog dialog;

    private EditText editphone;

    TinyDB tinyDB;

    public void dialog(final Context context, int resource, double widthh) {

        this.context = context;
        dialog = new Dialog(context);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(resource);
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * widthh);
        int height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setLayout(width, height);
        //dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        //define tiny db
        tinyDB = new TinyDB(context);

        //DEFINE VARS
        editphone = dialog.findViewById(R.id.editPhone);

        //VALIDATION ON PHONE EDITTEXT
        if (!tinyDB.getString("userPhone").equals("0000") || !tinyDB.getString("userPhone").isEmpty())
        {
            editphone.setText(tinyDB.getString("userPhone"));
        }

        Toasty.success(context, tinyDB.getString("userPhone"), Toast.LENGTH_SHORT).show();

        //SAVE BUTTON
        Button btnsave = dialog.findViewById(R.id.phoneSave);
        btnsave.setOnClickListener(v -> {
             if(editphone.getText().toString().length()<=9)
             {
                 editphone.setError(context.getString(R.string.short_phone));

             }
             else if(editphone.getText().toString().length()>=16){

                 editphone.setError(context.getString(R.string.phone_must_less_16));
             }
             else {
                 //CALL API CHANGE PHONE
                 new Apicalls(context,change_phone.this).add_phone(editphone.getText().toString());
             }

        });



        dialog.show();

    }

    @Override
    public void OnStart() {

    }

    @Override
    public void OnResponse(ResponseModel model) {


        try {
            Toasty.success(context, ""+model.getJsonObject().getString("message"), Toast.LENGTH_SHORT).show();
            tinyDB.putString("userPhone",editphone.getText().toString());
            dialog.dismiss();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dialog.dismiss();

    }

    @Override
    public void OnError(VolleyError error) {

        Toasty.error(context, ""+error.toString(), Toast.LENGTH_SHORT).show();

    }
}
