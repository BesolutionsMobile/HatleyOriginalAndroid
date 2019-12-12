package hatelyoriginal.besolutions.com.hatleyoriginal.jupiterchat.Activites;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hatelyoriginal.besolutions.com.hatleyoriginal.R;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.AddButtonClick;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.OnSwipeTouchListener;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.TinyDB;
import hatelyoriginal.besolutions.com.hatleyoriginal.jupiterchat.Activites.complaint.complaint;
import hatelyoriginal.besolutions.com.hatleyoriginal.jupiterchat.Activites.evaluate_star.evaluate_star;
import hatelyoriginal.besolutions.com.hatleyoriginal.jupiterchat.Adapters.ChatAdapter;
import hatelyoriginal.besolutions.com.hatleyoriginal.jupiterchat.Models.ChatMessage;

//client
public class NewChatActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private ChatAdapter adapter;

    EditText editText;

    ImageView sendbutton;

    RecyclerView recyclerView;

    RelativeLayout swip;

    String userName,senderImage,receiverName,receiverImage;

    LinearLayout chatlayout,aboutalayout;

    TextView abouttochat;

    Button startnow;

    TinyDB tinyDB;

    Toolbar mToolbar;

    TextView textView;

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newchat);

        tinyDB = new TinyDB(this);

        mToolbar = findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);

        getActionBarTextView().setVisibility(View.GONE);

        textView = mToolbar.findViewById(R.id.toolbartext);
        textView.setText("Chat");


        chatlayout = findViewById(R.id.chatlayout);
        aboutalayout = findViewById(R.id.aboulayout);

        abouttochat = findViewById(R.id.abouttostarttext);

        startnow = findViewById(R.id.startnowbutton);

        editText = findViewById(R.id.edittext);
        sendbutton = findViewById(R.id.sendbutton);
        chatlayout = findViewById(R.id.chatlayout);

        Bundle extras = getIntent().getExtras();
        assert extras != null;

        swip = findViewById(R.id.swip);


        userName = tinyDB.getString("userName");
        receiverName = tinyDB.getString("reciverName");

        senderImage = "mahmoud";
        receiverImage = "samer";

        setUpRecyclerView();

       // ConstructChat(userName+"-"+receiverName);

        sendMessage("Hello " + receiverName + " are you here ?");

        RealtimeRate();


        aboutalayout.setVisibility(View.VISIBLE);
        chatlayout.setVisibility(View.GONE);

        startnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                aboutalayout.setVisibility(View.GONE);
                chatlayout.setVisibility(View.VISIBLE);

                // Just if i want to chat with someone for the first time
                addChat();

            }
        });


        sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!editText.getText().toString().trim().isEmpty())
                {
                    sendMessage(editText.getText().toString().trim());
                }
            }
        });

        aboutalayout.setVisibility(View.GONE);
        chatlayout.setVisibility(View.VISIBLE);


    }

    public void addChat()
    {

        Map<String, Object> conversation = new HashMap<>();
        conversation.put("name", userName+"-"+receiverName);
        conversation.put("sender", userName);
        conversation.put("receiver", receiverName);
        conversation.put("receiverImage", receiverImage);
        conversation.put("lastMessage", "");
        conversation.put("starFinish", "No");
        conversation.put("timeStamp", FieldValue.serverTimestamp());

        db.collection("Conversations").document(userName+"-"+receiverName).set(conversation).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                sendMessage("Hello " + receiverName + " are you here ?");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });

    }

    public void sendMessage(final String message)
    {

        if(!message.isEmpty())
        {

            List<String> seenArray = new ArrayList<>();
            seenArray.add(userName);

            Map<String, Object> messages = new HashMap<>();
            messages.put("sender", userName);
            messages.put("senderImage", senderImage);
            messages.put("seenArray",seenArray);
            messages.put("message", message);
            messages.put("timeStamp", FieldValue.serverTimestamp());

            db.collection("Conversations").document(userName +"-"+receiverName).collection("Messages").document().set(messages).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    recyclerView.scrollToPosition(adapter.getItemCount() - 1);

                    editText.setText("");
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

                    lastMessage(message);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }

    }

    public void lastMessage(String last)
    {
        Map<String, Object> conversation = new HashMap<>();
        conversation.put("name", userName +"-"+receiverName);
        conversation.put("sender", userName);
        conversation.put("receiver", receiverName);
        conversation.put("lastMessage", last);
        conversation.put("timeStamp", FieldValue.serverTimestamp());

        db.collection("Conversations").document(userName +"-"+receiverName).update(conversation).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }


    private void ConstructChat(String docId)
    {

        DocumentReference docIdRef = db.collection("Conversations").document(docId);
        docIdRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                assert document != null;
                if (document.exists()) {

                    Log.d("", "Document exist and deleted");

                    db.collection("Conversations").document(docId).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            // Just if i want to chat with someone for the first time
                            //addChat();

                            sendMessage("Hello " + receiverName + " are you here ?");

                            RealtimeRate();
                        }
                    });

                } else {

                    // Just if i want to chat with someone for the first time
                    //addChat();

                    sendMessage("Hello " + receiverName + " are you here ?");

                    RealtimeRate();

                }
            } else {
                Log.d("", "Failed with: ", task.getException());
            }
        });


    }

    private void setUpRecyclerView() {

        Query query3 = db.collection("Conversations").document(userName +"-"+receiverName).collection("Messages").orderBy("timeStamp", Query.Direction.ASCENDING);


        FirestoreRecyclerOptions<ChatMessage> options = new FirestoreRecyclerOptions.Builder<ChatMessage>()
                .setQuery(query3, ChatMessage.class)
                .build();

        adapter = new ChatAdapter(options);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onButtonClick(AddButtonClick addButtonClick)
    {

        if (addButtonClick.getEvent().equals("Complaint"))
        {

            FragmentManager fm = getSupportFragmentManager();
            complaint addCommentFragment = new complaint();

            addCommentFragment.show(fm,"TV_tag");

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
        EventBus.getDefault().unregister(this);
    }

    public void RealtimeRate()
    {
        db.collection("Conversations").document(userName+"-"+receiverName).addSnapshotListener((documentSnapshot, e) -> {
            if (e != null) {
                Toast.makeText(this, "Listen failed.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (documentSnapshot != null) {
                String group = (String) documentSnapshot.get("starFinish");
                assert group != null;
                try {
                    if(group.equals("Yes"))
                    {
                        FragmentManager fm = getSupportFragmentManager();
                        evaluate_star addCommentFragment = new evaluate_star();

                        addCommentFragment.show(fm,"TV_tag");
                    }
                }catch (Exception m)
                {
                    //Toast.makeText(this, m.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                }

            } else {
                Toast.makeText(this, "Current data: null", Toast.LENGTH_SHORT).show();
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
