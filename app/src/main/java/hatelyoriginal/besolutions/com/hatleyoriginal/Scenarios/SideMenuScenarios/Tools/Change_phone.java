package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Tools;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;

import es.dmoral.toasty.Toasty;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.Apicalls;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.NetworkInterface;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.ResponseModel;
import hatelyoriginal.besolutions.com.hatleyoriginal.R;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Controllers.Fragments.change_phone;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.TinyDB;

public class Change_phone implements NetworkInterface {

    Button btnsave;
    Context context;
    Dialog dialog;

    EditText editphone;

    TinyDB tinyDB;


    public void dialog(final Context context, int resource, double widthh) {
        this.context = context;
        dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(resource);
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * widthh);
        int height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setLayout(width, height);
        //dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        editphone = dialog.findViewById(R.id.editPhone);

        btnsave = dialog.findViewById(R.id.phoneSave);

        tinyDB = new TinyDB(context);

        editphone = dialog.findViewById(R.id.editPhone);

        if (!tinyDB.getString("userPhone").equals("0000") || !tinyDB.getString("userPhone").equals("null") || tinyDB.getString("userPhone") != null)
        {
            editphone.setText(tinyDB.getString("userPhone"));
        }

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editphone.getText().toString().length()<=9)
                {
                    editphone.setError("Phone is too short!");

                }
                else if(editphone.getText().toString().length()>=16){
                    editphone.setError("Phone must be less than 16 digits");
                }
                else {
                    new Apicalls(context,Change_phone.this).add_phone(editphone.getText().toString());
                }

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
