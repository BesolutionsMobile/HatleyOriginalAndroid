package hatelyoriginal.besolutions.com.hatleyoriginal.jupiterchat.Activites;

import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.ButterKnife;
import hatelyoriginal.besolutions.com.hatleyoriginal.R;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.TinyDB;
import hatelyoriginal.besolutions.com.hatleyoriginal.jupiterchat.Adapters.ConversationsAdapter;
import hatelyoriginal.besolutions.com.hatleyoriginal.jupiterchat.Models.ConversationItem;


public class ConversationsActivity extends AppCompatActivity {


    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    RecyclerView recyclerView;

    List<ConversationItem> list = new ArrayList<>();

    ConversationsAdapter adapter;

    String userName, senderImage;
    RelativeLayout swipe;

    TinyDB tinyDB;

    // Act as a Home Activity for Chats

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversations);
        ButterKnife.bind(this);

        tinyDB = new TinyDB(this);

        Bundle extras = getIntent().getExtras();
        assert extras != null;

        userName = extras.getString("userName");
        senderImage = extras.getString("senderImage");

        tinyDB.putString("userName", userName);
        swipe = findViewById(R.id.swiper);

        setup();
        //  Toast.makeText(this, "chat act", Toast.LENGTH_SHORT).show();
/*
        //SWIPE
        swipe.setOnTouchListener(new OnSwipeTouchListener(ConversationsActivity.this) {
            public void onSwipeTop() {

            }
            public void onSwipeRight() {

            }
            public void onSwipeLeft() {
                evaluate_star evaluate_star=new evaluate_star();
                evaluate_star.dialog(ConversationsActivity.this,R.layout.evaluate_star,.90);            }
            public void onSwipeBottom() {

            }

        });

 */


    }

    public void setup() {
        db.collection("Conversations").orderBy("timeStamp", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        if (document.getId().contains(userName)) {
                            ConversationItem item = new ConversationItem();

                            Date currentTime = Calendar.getInstance().getTime();

                            item.setName(document.getId());
                            item.setReceiver(Objects.requireNonNull(document.get("receiver")).toString());
                            item.setSender(Objects.requireNonNull(document.get("sender")).toString());
                            item.setLastMessage(Objects.requireNonNull(document.get("lastMessage")).toString());
                            try {
                                item.setTimestamp(Objects.requireNonNull(document.getDate("timeStamp")));
                            } catch (Exception e) {
                                item.setTimestamp(currentTime);
                            }


                            list.add(item);
                        }

                    }

                    adapter = new ConversationsAdapter(list, userName, senderImage);

                    recyclerView = findViewById(R.id.rv);
                    recyclerView.setHasFixedSize(true);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ConversationsActivity.this);

                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(adapter);


                } else {
                    Log.d("t", "Error getting documents: ", task.getException());
                }
            }
        });

    }


}
