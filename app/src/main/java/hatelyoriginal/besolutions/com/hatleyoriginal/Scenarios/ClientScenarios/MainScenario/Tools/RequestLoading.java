package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.MainScenario.Tools;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.Window;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.Objects;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import hatelyoriginal.besolutions.com.hatleyoriginal.R;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.MainScenario.Controllers.Fragments.RequestOrderFragment;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.AddButtonClick;

public class RequestLoading {

    public void dialog(final Context context, int resource, double widthh) {

        final Dialog dialog = new Dialog(context);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(resource);
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * widthh);
        int height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setLayout(width, height);
        dialog.show();

        TextView txt_msg = dialog.findViewById(R.id.popup_msg);
        txt_msg.setText(context.getString(R.string.order_submitted));


        Handler handler = new Handler();
        handler.postDelayed(() -> {

            EventBus.getDefault().post(new AddButtonClick("Done"));
            dialog.dismiss();
        }, 2000);


    }
}
