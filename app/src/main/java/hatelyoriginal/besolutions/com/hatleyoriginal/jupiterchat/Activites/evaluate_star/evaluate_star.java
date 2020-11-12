package hatelyoriginal.besolutions.com.hatleyoriginal.jupiterchat.Activites.evaluate_star;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;
import hatelyoriginal.besolutions.com.hatleyoriginal.MainActivity;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.Apicalls;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.NetworkInterface;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.ResponseModel;
import hatelyoriginal.besolutions.com.hatleyoriginal.R;
import hatelyoriginal.besolutions.com.hatleyoriginal.StarActivity;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.AddButtonClick;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.TinyDB;
import hatelyoriginal.besolutions.com.hatleyoriginal.jupiterchat.Activites.Bill_Amount;
import hatelyoriginal.besolutions.com.hatleyoriginal.jupiterchat.Activites.complaint.complaint;
import hatelyoriginal.besolutions.com.hatleyoriginal.jupiterchat.Models.Notes;

public class evaluate_star extends DialogFragment implements NetworkInterface {

    private Context context;

    private TinyDB tinyDB;

    private int id1, id2, id3;

    private int x = 0;

    @BindView(R.id.evaluate_txt)
    TextView evaluateTxt;
    @BindView(R.id.userimg)
    ImageView userimg;
    @BindView(R.id.ratings)
    RatingBar ratings;
    @BindView(R.id.compalint)
    TextView compalint;
    @BindView(R.id.txtNote1)
    TextView txtNote1;
    @BindView(R.id.visible_item1)
    LinearLayout visibleItem1;
    @BindView(R.id.viewswitch1)
    ViewSwitcher viewswitch1;
    @BindView(R.id.txtNote2)
    TextView txtNote2;
    @BindView(R.id.txtNote3)
    TextView txtNote3;
    @BindView(R.id.viewswitch2)
    ViewSwitcher viewswitch2;
    private Unbinder unbinder;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private double value = 10.0;

    private String username, recivername;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.evaluate_star, container);

        context = getActivity();

        unbinder = ButterKnife.bind(this, rootView);

        //define tiny db
        tinyDB = new TinyDB(context);

        username = tinyDB.getString("userName");
        recivername = tinyDB.getString("reciverName");


        //CHANGE TEXT IN EVALUATE

        //ADD IMAGE
        Glide.with(context).load(tinyDB.getString("image")).placeholder(R.drawable.starplaceholder).into(userimg);


        if (tinyDB.getString("userType").equals("2")) {

            evaluateTxt.setText("Evaluate Client"); //SET EVALUATE TEXT

            Glide.with(context).load(tinyDB.getString("image")).placeholder(R.drawable.starplaceholder).into(userimg);   //ADD IMAGE


            updateChat();

        }

        //GO TO COMPLAINT
        compalint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (value <= 1.0) {

                    EventBus.getDefault().post(new AddButtonClick("Complaint"));
                    dismiss();

                }
            }
        });

        //SET ON RATING
        ratings.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                if (v <= 1.0) {

                    value = v;
                    compalint.setBackgroundResource(R.drawable.buttons_bg);

                    ratingBar.setEnabled(false);

                    viewswitch1.showNext();
                    viewswitch2.showNext();

                } else {

                    value = v;
                }

                int stars_num = Math.round(v);

                x = 1;

                //CALL API SET RATE
                new Apicalls(getActivity(),evaluate_star.this).set_rate(tinyDB.getString("orderID"),String.valueOf(stars_num),"");


                txtNote1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        x = 2;

                        //CALL API SET RATE
                        new Apicalls(getActivity(),evaluate_star.this).set_rate(tinyDB.getString("orderID"),String.valueOf(stars_num),String.valueOf(id1));

                    }
                });


                txtNote2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        x = 2;
                        //CALL API SET RATE
                        new Apicalls(getActivity(),evaluate_star.this).set_rate(tinyDB.getString("orderID"),String.valueOf(stars_num),String.valueOf(id2));


                    }
                });


                txtNote3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        x = 2;
                        //CALL API SET RATE
                        new Apicalls(getActivity(),evaluate_star.this).set_rate(tinyDB.getString("orderID"),String.valueOf(stars_num),String.valueOf(id3));

                    }
                });
            }
        });


        return rootView;
    }


    @Override
    public void OnStart() {

    }

    @Override
    public void OnResponse(ResponseModel model) {

        if(x == 1)
        {

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            Notes notes = gson.fromJson(model.getJsonObject().toString(), Notes.class);

            if(notes.getNotes().size() == 0)
            {
                // More than 1 star pressed

                Toasty.success(context, "Ratted Successfully", Toast.LENGTH_LONG).show();

                if(tinyDB.getString("userType").equals("1"))
                {
                    context.startActivity(new Intent(context, MainActivity.class));
                }
                else {
                    context.startActivity(new Intent(context, StarActivity.class));
                }

            }else
                {
                    // 1 Star Pressed

                    id1 = notes.getNotes().get(0).getId();

                    id2 = notes.getNotes().get(1).getId();

                    id3 = notes.getNotes().get(2).getId();

                    txtNote1.setText(notes.getNotes().get(0).getNote());

                    txtNote2.setText(notes.getNotes().get(1).getNote());

                    txtNote3.setText(notes.getNotes().get(2).getNote());

                }

        }else
            {
                Toasty.success(context, "Evaluated", Toast.LENGTH_LONG).show();

                if(tinyDB.getString("userType").equals("1"))
                {
                    context.startActivity(new Intent(context, MainActivity.class));
                }
                else {
                    context.startActivity(new Intent(context, StarActivity.class));
                }
            }

    }

    @Override
    public void OnError(VolleyError error) {

        Toasty.error(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
    }

   //update chat
    private void updateChat() {
        Map<String, Object> conversation = new HashMap<>();
        conversation.put("name", recivername+"-"+username);
        conversation.put("sender", username);
        conversation.put("receiver", recivername);
        conversation.put("receiverImage", "");
        conversation.put("lastMessage", "");
        conversation.put("starFinish", "Yes");
        conversation.put("timeStamp", FieldValue.serverTimestamp());
        db.collection("Conversations").document(recivername+"-"+username).set(conversation);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
