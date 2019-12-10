package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Controllers.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import hatelyoriginal.besolutions.com.hatleyoriginal.R;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Controllers.Fragments.change_pass;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Tools.Change_phone;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Tools.Change_photo;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.TinyDB;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.firebase_storage;


public class personal_info extends AppCompatActivity {

    LinearLayout linearname,linearemail,linearpass,linearid,linearphoto,linearphone;

    TextView name,email;

    final int PICK_IMAGE_REQUEST_CAMERA = 71;

    final int PICK_IMAGE_REQUEST_GALLERY = 72;

    TinyDB tinyDB;

    FirebaseStorage storage;
    StorageReference storageReference;

    Uri selectedImage;

    public String imageURL;
    ImageView userimage;


    static Bitmap SelectedPhotos=null;
    static Bitmap bitmaps;
    static  String Image="0";


    Change_photo change_photo = new Change_photo();

    Toolbar mToolbar;

    TextView textView;

    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_info);

        tinyDB = new TinyDB(this);

        mToolbar = findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);

        getActionBarTextView().setVisibility(View.GONE);

        textView = mToolbar.findViewById(R.id.toolbartext);

        textView.setText("Personal Info");

        back = mToolbar.findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

        linearname = findViewById(R.id.linChangeName);
        linearemail = findViewById(R.id.linChangeEmail);
        linearid = findViewById(R.id.linChangeId);
        linearphone = findViewById(R.id.linChangePhone);
        linearphoto = findViewById(R.id.linChangePhoto);
        linearpass = findViewById(R.id.linChangePass);
        userimage = findViewById(R.id.imgUser);
        name= findViewById(R.id.name);
        email= findViewById(R.id.email);

        FirebaseApp.initializeApp(this);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        linearpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                change_pass change_pass = new change_pass();
                change_pass.dialog(personal_info.this,R.layout.change_pass,.90);


            }
        });


        linearphoto.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                Change_photo change_photo = new Change_photo();
                change_photo.dialog(personal_info.this,R.layout.change_photo,.90);



            }
        });


        linearphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Change_phone change_phone = new Change_phone();
                change_phone.dialog(personal_info.this,R.layout.change_phone,.90);

            }
        });


        //SET USER_NAME
        name.setText(tinyDB.getString("userName"));
        //SET USER_EMAIL
        email.setText(tinyDB.getString("userEmail"));


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public  void  showPicturDialog()
    {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("قم بألختيار");
        String[] pictureDlialogItem={"اختر من المعرض" ,
                "قم بألتقاط صورة"};
        pictureDialog.setItems(pictureDlialogItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0 :
                        choosePhotoFromGallary(personal_info.this);
                        break;
                    case 1:
                        takePhotoFromCamera(personal_info.this);
                        break;
                }
            }
        });
        pictureDialog.show();
    }
    public void choosePhotoFromGallary(Context context) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        ((AppCompatActivity)context).startActivityForResult(galleryIntent,PICK_IMAGE_REQUEST_GALLERY);
    }

    public void takePhotoFromCamera(Context context) {

        //From Camera
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(pictureIntent.resolveActivity(Objects.requireNonNull((AppCompatActivity)context).getPackageManager()) != null) {
            ((AppCompatActivity)context).startActivityForResult(pictureIntent, PICK_IMAGE_REQUEST_CAMERA);
        }

    }




    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    public void onChooseFile (View v){

        CropImage.activity()
                .setMaxCropResultSize(1000,1000)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .start(personal_info.this);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK){

                selectedImage = result.getUri();
                InputStream imageStream = null;
                try {
                    FileOutputStream fos = null;
                    imageStream = getContentResolver().openInputStream(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Bitmap SelectedPhoto = BitmapFactory.decodeStream(imageStream);
                bitmaps = Bitmap.createScaledBitmap(SelectedPhoto, 300, 300, true);

                firebase_storage firebase_storage=new firebase_storage();
                firebase_storage.uploadImage(selectedImage,personal_info.this,true);

                //SET IMAGE
                Change_photo change_photo=new Change_photo();
                change_photo.set_image(bitmaps,firebase_storage.uploadImage(selectedImage,personal_info.this,true));

            }
            else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){

                Exception e = result.getError();
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
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