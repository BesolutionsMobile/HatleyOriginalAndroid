package hatelyoriginal.besolutions.com.hatleyoriginal.jupiterchat.Activites;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import hatelyoriginal.besolutions.com.hatleyoriginal.R;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.MainScenario.Controllers.Fragments.ReactOfferPopUpFragment;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.AddButtonClick;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.OnSwipeTouchListener;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.TinyDB;
import hatelyoriginal.besolutions.com.hatleyoriginal.jupiterchat.Activites.complaint.complaint;
import hatelyoriginal.besolutions.com.hatleyoriginal.jupiterchat.Activites.evaluate_star.evaluate_star;
import hatelyoriginal.besolutions.com.hatleyoriginal.jupiterchat.Adapters.ChatAdapter;
import hatelyoriginal.besolutions.com.hatleyoriginal.jupiterchat.Models.ChatMessage;

//star

public class ContinueChatActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private ChatAdapter adapter;

    EditText editText;
    LinearLayout swipe ;

    ImageView sendbutton;

    RecyclerView recyclerView;

    String username,recivername,userImage,reciverImage,conversationname;

    TextView seen;

    TinyDB tinyDB;

    Toolbar mToolbar;

    TextView textView;

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue);

        tinyDB = new TinyDB(this);

        mToolbar = findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);

        getActionBarTextView().setVisibility(View.GONE);

        textView = mToolbar.findViewById(R.id.toolbartext);
        textView.setText("Chat");

        editText = findViewById(R.id.edittext);
        sendbutton = findViewById(R.id.sendbutton);

        swipe = findViewById(R.id.chatlayout2);
        seen = findViewById(R.id.seen);

        Bundle extras = getIntent().getExtras();
        assert extras != null;

        username = tinyDB.getString("userName");
        recivername = tinyDB.getString("reciverName");

        conversationname = recivername+"-"+username;
        userImage = "dashsdhas";
        reciverImage = "ssssssss";

        setUpRecyclerView();

        // RealtimeSeen();

        sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!editText.getText().toString().trim().isEmpty())
                {
                    sendMessage(editText.getText().toString().trim());
                }
            }
        });


        recyclerView.setOnTouchListener(new OnSwipeTouchListener(ContinueChatActivity.this) {
            public void onSwipeTop() {

            }

            public void onSwipeRight() {

            }

            public void onSwipeLeft() {

                FragmentManager fm = getSupportFragmentManager();
                Bill_Amount addCommentFragment = new Bill_Amount();

                addCommentFragment.show(fm,"TV_tag");
            }
            public void onSwipeBottom() {

            }

        });

    }

    public void sendMessage(final String message)
    {
        if(!message.isEmpty())
        {

            List<String> seenArray = new ArrayList<>();
            seenArray.add(username);

            Map<String, Object> messages = new HashMap<>();
            messages.put("sender", username);
            messages.put("senderImage", userImage);
            messages.put("seenArray",seenArray);
            messages.put("message", message);
            messages.put("timeStamp", FieldValue.serverTimestamp());

            db.collection("Conversations").document(conversationname).collection("Messages").document().set(messages).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    recyclerView.scrollToPosition(adapter.getItemCount() - 1);

                    editText.setText("");
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

                    updateChat(message);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }

    }



    public void updateChat(String last)
    {

        Map<String, Object> conversation = new HashMap<>();
        conversation.put("name", conversationname);
        conversation.put("sender", username);
        conversation.put("receiver", recivername);
        conversation.put("receiverImage", reciverImage);
        conversation.put("lastMessage", last);
        conversation.put("starFinish", "No");
        conversation.put("timeStamp", FieldValue.serverTimestamp());

        db.collection("Conversations").document(conversationname).set(conversation);

    }


    public void RealtimeSeen()
    {

        Query query = db.collection("Conversations").document(conversationname).collection("Messages").orderBy("timeStamp", Query.Direction.DESCENDING).limit(1);

        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    showMessage("Listen failed.");
                    return;
                }

                if (snapshot != null) {

                    @SuppressWarnings (value="unchecked")
                    List<String> group = (List<String>) snapshot.getDocuments().get(0).get("seenArray");
                    assert group != null;

                    if(!group.get(0).equals(username))
                    {

                        List<String> seenArray = new ArrayList<>();
                        seenArray.add(group.get(0));
                        seenArray.add(username);

                        Map<String, Object> users = new HashMap<>();
                        users.put("seenArray",seenArray);


                        db.collection("Conversations").document(conversationname).collection("Messages").document(snapshot.getDocuments().get(0).getId()).update(users);

                    }


                    if(group.size()==2)
                    {
                        seen.setVisibility(View.VISIBLE);
                    }else
                    {
                        seen.setVisibility(View.GONE);
                    }


                } else {
                    showMessage("Current data: null");
                }
            }
        });
    }


    private void setUpRecyclerView() {

        Query query3 = db.collection("Conversations").document(conversationname).collection("Messages").orderBy("timeStamp", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<ChatMessage> options = new FirestoreRecyclerOptions.Builder<ChatMessage>()
                .setQuery(query3, ChatMessage.class)
                .build();

        adapter = new ChatAdapter(options);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);


        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);

                int friendlyMessageCount = adapter.getItemCount();
                int lastVisiblePosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();

                // If the recycler view is initially being loaded or the
                // user is at the bottom of the list, scroll to the bottom
                // of the list to show the newly added message.
                if (lastVisiblePosition == -1 || (positionStart >= (friendlyMessageCount - 1) && lastVisiblePosition == (positionStart - 1)))
                {
                    recyclerView.scrollToPosition(positionStart);
                }

            }
        });


    }


    private void showMessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(this,ContinueChatActivity.class));
        Toast.makeText(this, "Cant back from chat", Toast.LENGTH_SHORT).show();

    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onButtonClick(AddButtonClick addButtonClick)
    {

        if(addButtonClick.getEvent().equals("Finished"))
        {

            FragmentManager fm = getSupportFragmentManager();
            evaluate_star addCommentFragment = new evaluate_star();

            addCommentFragment.show(fm,"TV_tag");

        }else if (addButtonClick.getEvent().equals("Complaint"))
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

    @Override
    protected void onRestart() {
        super.onRestart();
        startActivity(new Intent(this,ContinueChatActivity.class));
        finish();
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
