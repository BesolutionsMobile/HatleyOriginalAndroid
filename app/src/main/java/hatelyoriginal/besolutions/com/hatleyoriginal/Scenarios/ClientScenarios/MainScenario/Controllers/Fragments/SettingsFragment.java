package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.MainScenario.Controllers.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import hatelyoriginal.besolutions.com.hatleyoriginal.LocalData.SavedData;
import hatelyoriginal.besolutions.com.hatleyoriginal.LocalData.SendData;
import hatelyoriginal.besolutions.com.hatleyoriginal.R;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SharedScenarios.AuthScenario.Controllers.Activities.LoginActivity;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Controllers.Activities.personal_info;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Controllers.Fragments.change_pass;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Tools.Change_phone;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Tools.Change_photo;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.TinyDB;

public class SettingsFragment extends Fragment {

    LinearLayout linearname,linearemail,linearpass,linearid,linearphoto,linearphone,language;

    TextView name,email;

    TinyDB tinyDB;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.personal_info_new, null, false);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tinyDB = new TinyDB(getActivity());

        linearname = view.findViewById(R.id.linChangeName);
        linearemail =  view.findViewById(R.id.linChangeEmail);
        linearid =  view.findViewById(R.id.linChangeId);
        linearphone =  view.findViewById(R.id.linChangePhone);
        linearphoto =  view.findViewById(R.id.linChangePhoto);
        linearpass =  view.findViewById(R.id.linChangePass);
        language =  view.findViewById(R.id.language);
        name =  view.findViewById(R.id.name);
        email =  view.findViewById(R.id.email);


        linearpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                change_pass change_pass = new change_pass();
                change_pass.dialog(getActivity(),R.layout.change_pass,.90);


            }
        });


        linearphoto.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                Change_photo change_photo = new Change_photo();
                change_photo.dialog(getActivity(),R.layout.change_photo,.90,0);



            }
        });


        linearphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Change_phone change_phone = new Change_phone();
                change_phone.dialog(getActivity(),R.layout.change_phone,.90);

            }
        });

        //CHANGE LANGUAGE
        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(new SavedData().get_lan(getContext()).equals("en"))
                {
                    SendData.send_lan(getContext(),"ar");
                }
                else {
                    SendData.send_lan(getContext(),"en");
                }
                Intent i =new Intent(getContext(), LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);


            }
        });


        //SET USER_NAME
        name.setText(tinyDB.getString("userName"));
        //SET USER_EMAIL
        email.setText(tinyDB.getString("userEmail"));

    }
}
