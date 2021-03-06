package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Controllers.Fragments;

/**
 * CHANGE PASSWORD DIALOG
 */

import android.app.Dialog;
import android.content.Context;
import android.icu.text.MessagePattern;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;

import es.dmoral.toasty.Toasty;
import hatelyoriginal.besolutions.com.hatleyoriginal.LocalData.SavedData;
import hatelyoriginal.besolutions.com.hatleyoriginal.LocalData.SendData;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.Apicalls;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.NetworkInterface;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.ResponseModel;
import hatelyoriginal.besolutions.com.hatleyoriginal.R;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Controllers.Activities.personal_info;


public class change_pass implements NetworkInterface {

    Context context;
    Button btnsave;
    Dialog dialog;

    EditText editpass, editconpass, currentPass;

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

        //DEFINE ALL VARS
        btnsave = dialog.findViewById(R.id.passsave);
        editpass = dialog.findViewById(R.id.editPassword);
        editconpass = dialog.findViewById(R.id.editConPassword);
        currentPass = dialog.findViewById(R.id.currentPass);


        //ON CLICK SAVE BUTTON
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!currentPass.getText().toString().equals(new SavedData().getCurrentPass(context))) {
                    currentPass.setError(context.getString(R.string.wrong_current_pass));
                    Log.e("current_pass", new SavedData().get_lan(context));

                } else if (!editpass.getText().toString().equals(editconpass.getText().toString())) {
                    editconpass.setError(context.getString(R.string.pass_not_match));

                } else if (editpass.getText().toString().length() <= 5) {
                    editpass.setError(context.getString(R.string.short_pass));

                } else {
                    //CALL API CHANE PASSWORD
                    new Apicalls(context, change_pass.this).change_password(editpass.getText().toString(), editconpass.getText().toString());
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
            SendData.sendCurrentPass(context, editpass.getText().toString());

            Toasty.success(context, "" + model.getJsonObject().getString("message"), Toast.LENGTH_LONG).show();

            dialog.dismiss();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dialog.dismiss();

    }

    @Override
    public void OnError(VolleyError error) {
        Toasty.error(context, "" + error.toString(), Toast.LENGTH_LONG).show();


    }
}
