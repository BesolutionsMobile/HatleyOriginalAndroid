package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.MainScenario.Controllers.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.Apicalls;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.NetworkInterface;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.ResponseModel;
import hatelyoriginal.besolutions.com.hatleyoriginal.R;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.AddButtonClick;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.TinyDB;
import hatelyoriginal.besolutions.com.hatleyoriginal.jupiterchat.Activites.NewChatActivity;

public class ReactOfferPopUpFragment extends DialogFragment implements NetworkInterface {

    int x = 0;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private ProgressDialog pd;

    TinyDB tinyDB;

    Context context;

    @BindView(R.id.userimg)
    ImageView userimg;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.ratings)
    RatingBar ratings;
    @BindView(R.id.order_nums)
    TextView orderNums;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.hours)
    TextView hours;
    @BindView(R.id.approve)
    Button approve;
    @BindView(R.id.reject)
    Button reject;
    Unbinder unbinder;
    @BindView(R.id.dismiss)
    Button dismiss;

    String token;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.star_info, container);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                token = instanceIdResult.getToken();
                // send it to server
            }
        });

        context = getActivity();

        tinyDB = new TinyDB(getActivity());

        unbinder = ButterKnife.bind(this, rootView);


        name.setText(tinyDB.getString("starName"));

        orderNums.setText(tinyDB.getString("starOrdersCount"));


        ratings.setRating((float) tinyDB.getInt("starRate"));


        price.setText(tinyDB.getString("offerPrice"));


        Picasso.with(getActivity()).load(tinyDB.getString("starImage")).placeholder(R.drawable.starplaceholder).into(userimg);


        hours.setText(tinyDB.getString("offerTime"));

        updateChat();

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                x = 1;
                pd = new ProgressDialog(getActivity());
                pd.setMessage(getString(R.string.loading));
                pd.setCancelable(false);
                pd.show();
                new Apicalls(getActivity(), ReactOfferPopUpFragment.this).AcceptOffer(String.valueOf(tinyDB.getInt("offerID")),token);
            }
        });
        //REJECT BUTTON

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                x = 2;
                pd = new ProgressDialog(getActivity());
                pd.setMessage("Loading...");
                pd.setCancelable(false);
                pd.show();
                new Apicalls(getActivity(), ReactOfferPopUpFragment.this).RejectOffer(String.valueOf(tinyDB.getInt("offerID")));

                dismiss();
            }
        });

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dismiss();

            }
        });

        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        return rootView;
    }

    private void updateChat() {
        Map<String, Object> conversation = new HashMap<>();
        conversation.put("name",  tinyDB.getString("userName")+"-"+tinyDB.getString("starName"));
        conversation.put("sender", tinyDB.getString("userName"));
        conversation.put("receiver",  tinyDB.getString("starName"));
        conversation.put("receiverImage", "");
        conversation.put("lastMessage", "");
        conversation.put("starFinish", "No");
        conversation.put("timeStamp", FieldValue.serverTimestamp());
        db.collection("Conversations").document( tinyDB.getString("userName")+"-"+ tinyDB.getString("starName")).set(conversation);
    }

    /*@Override
    public int getTheme() {
        return R.style.MyCustomTheme;
    }
*/
    @Override
    public void OnStart() {

    }

    @Override
    public void OnResponse(ResponseModel model) {

        pd.cancel();

        if (x == 1) {

            Toasty.success(Objects.requireNonNull(getActivity()), getString(R.string.offer_accepted), Toast.LENGTH_LONG).show();

            Intent intent = new Intent(getActivity(), NewChatActivity.class);

            tinyDB.putString("reciverName", tinyDB.getString("starName"));

            startActivity(intent);


        } else if (x == 2) {

            Toasty.success(context, getString(R.string.offer_rejected), Toast.LENGTH_LONG).show();

            //Rejected
            EventBus.getDefault().post(new AddButtonClick("Rejected"));

            Handler handler = new Handler();
            handler.postDelayed(() -> {

                dismiss();

            }, 1000);


        }


    }


    @Override
    public void OnError(VolleyError error) {

        pd.cancel();

        Toasty.success(context, getString(R.string.offer_already_acc), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
