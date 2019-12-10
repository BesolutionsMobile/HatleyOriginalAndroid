package hatelyoriginal.besolutions.com.hatleyoriginal.Utils;


import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import androidx.annotation.NonNull;
import es.dmoral.toasty.Toasty;


public class firebase_storage {

    private String imageURL = "NoLink";

    private TinyDB tinyDB;

    public String uploadImage(Uri customfilepath, final Context context,Boolean isEnglish) {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();

        tinyDB = new TinyDB(context);

        final String uploading,done,failed;

        if(isEnglish)
        {
            uploading = "Uploading...";
            done = "Uploaded Successfully";
            failed = "Uploading Failed";
        }else
        {
            uploading = "???? ????????";
            done = "?? ????? ?????";
            failed = "???";
        }

        if(customfilepath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setTitle(uploading);
            progressDialog.show();

            final StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(customfilepath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                                @Override
                                public void onSuccess(Uri uri) {

                                    progressDialog.dismiss();

                                    Toasty.success(context, done, Toast.LENGTH_LONG).show();

                                    imageURL = uri.toString();

                                    tinyDB.putString("imageURL",imageURL);
                                    tinyDB.putString("imageURLNAV",imageURL);

                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toasty.error(context, failed, Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }

        return imageURL;
    }
}



