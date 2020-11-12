package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SharedScenarios.AuthScenario.Tools;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.Window;

import java.util.Objects;

import hatelyoriginal.besolutions.com.hatleyoriginal.MainActivity;
import hatelyoriginal.besolutions.com.hatleyoriginal.StarActivity;

public class LoginLoading {

    public void dialog(final Context context, int resource, double widthh,String type) {

        final Dialog dialog = new Dialog(context);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(resource);

        //PROGRESS DIALOG WIDTH HEIGHT
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * widthh);
        int height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setLayout(width, height);
        dialog.show();

        //CHECK TO KNOW IF ITS CLIENT OR DELIVERY MAN
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            if(type.equals("1"))
            {
                context.startActivity(new Intent(context, MainActivity.class));
            }
            else {
                context.startActivity(new Intent(context, StarActivity.class));

            }
            dialog.dismiss();
        }, 2000);
    }
}
