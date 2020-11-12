package hatelyoriginal.besolutions.com.hatleyoriginal.jupiterchat.Activites.complaint;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
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
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.TinyDB;
import hatelyoriginal.besolutions.com.hatleyoriginal.jupiterchat.Models.ComplainType;

public class complaint extends DialogFragment implements NetworkInterface {

    TinyDB tinyDB;

    List<String> postList = new ArrayList<>();

    String idcomplaint;
    @BindView(R.id.complaintType)
    Spinner complaintType;
    @BindView(R.id.complaintdesc)
    EditText complaintdesc;
    @BindView(R.id.save)
    Button save;
    Unbinder unbinder;

    int x = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.complaint, container);

        //define all vars
        tinyDB = new TinyDB(getActivity());

        unbinder = ButterKnife.bind(this, rootView);

        x = 1;

        //call api complaint types
        new Apicalls(getActivity(),complaint.this).Get_complaint_types();

        //set on click save button
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (complaintdesc.getText().toString().length() <= 5) {

                    Toasty.error(Objects.requireNonNull(getActivity()), "Description is Too Short!", Toast.LENGTH_SHORT).show();

                } else if (complaintdesc.getText().toString().equals("")) {

                    Toasty.error(Objects.requireNonNull(getActivity()), "Please Write Your problem!", Toast.LENGTH_SHORT).show();

                } else {

                    x = 2;

                    //call api set complaint
                    new Apicalls(getActivity(),complaint.this).set_complaint(tinyDB.getString("orderID"),idcomplaint,complaintdesc.getText().toString());
                }

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

            ComplainType complaints = gson.fromJson(model.getJsonObject().toString(), ComplainType.class);

            for (int i = 0; i < complaints.getCompliantTypes().size(); i++)
            {
                postList.add(String.valueOf(complaints.getCompliantTypes().get(i).getType()));
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_item, postList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            complaintType.setAdapter(adapter);

            complaintType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    idcomplaint = String.valueOf(complaints.getCompliantTypes().get(position).getId());

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                    idcomplaint = "1";
                }
            });

        }else
            {
                Toasty.success(Objects.requireNonNull(getActivity()), "Complaint Added Successfully", Toast.LENGTH_SHORT).show();

                if(tinyDB.getString("userType").equals("1"))
                {
                    startActivity(new Intent(getActivity(), MainActivity.class));
                }
                else {
                    startActivity(new Intent(getActivity(), StarActivity.class));
                }
            }


    }

    @Override
    public void OnError(VolleyError error) {

        //validation on all error response
        if(x == 1)
        {
            Toasty.info(Objects.requireNonNull(getActivity()), "Types Fetching Failed", Toast.LENGTH_SHORT).show();
        }else
            {
                if (error.networkResponse.statusCode == 401) {
                    Toasty.error(Objects.requireNonNull(getActivity()), "Please Enter your Complaint.... ", Toast.LENGTH_SHORT).show();
                } else if (error.networkResponse.statusCode == 422)

                {

                    Toasty.error(Objects.requireNonNull(getActivity()), "Sorry....You Cant Complaint In this Order.", Toast.LENGTH_SHORT).show();
                } else {

                    Toasty.error(Objects.requireNonNull(getActivity()), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
