package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Tools;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import es.dmoral.toasty.Toasty;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.Apicalls;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.NetworkInterface;
import hatelyoriginal.besolutions.com.hatleyoriginal.NetworkLayer.ResponseModel;
import hatelyoriginal.besolutions.com.hatleyoriginal.R;
import hatelyoriginal.besolutions.com.hatleyoriginal.Utils.TinyDB;

public class Change_photo implements NetworkInterface {

    final int PICK_IMAGE_REQUEST_CAMERA = 71;

    final int PICK_IMAGE_REQUEST_GALLERY = 72;

    public static ImageView image_uplaod;

    Context context;
    public static Dialog dialog;
    static public ImageView imageView;
    Uri uri;

    Button btnsave;
    static  String image_url="0";

    TinyDB tinyDB;

    public void get_uri(Uri uri){
        this.uri=uri;
    }
    public void get_user_image (ImageView imageView){
        this.imageView=imageView;
    }

    public void dialog(final Context context, int resource, double widthh ,int type) {
        this.context = context;
        tinyDB = new TinyDB(this.context);
        dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(resource);
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * widthh);
        int height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setLayout(width, height);
        //dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        //DEFINE ALL VARS
        imageView = dialog.findViewById(R.id.imgUser);
        this.image_uplaod=imageView;


        //BUTTON SAVE
        btnsave = dialog.findViewById(R.id.btnPhotoSave);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tinyDB.getString("imageURL").equals("0"))
                {
                    Toasty.success(context,context.getString(R.string.pls_upload_img),Toast.LENGTH_LONG).show();

                }
                else if (type == 0) {

                    new Apicalls(context,Change_photo.this).change_photo(tinyDB.getString("imageURL"));
                }
                else if(type == 1)
                {
                    new Apicalls(context,Change_photo.this).uploadId(tinyDB.getString("imageURL"));
                }

            }
        });



        dialog.show();
    }

    @Override
    public void OnStart() {

    }

    @Override
    public void OnResponse(ResponseModel model) {
        Toasty.success(context,context.getString(R.string.added_success),Toast.LENGTH_LONG).show();

        tinyDB.putString("userImage",tinyDB.getString("imageURL"));

        dialog.dismiss();



    }

    @Override
    public void OnError(VolleyError error) {

    }


 /*   @RequiresApi(api = Build.VERSION_CODES.M)
    public  void  showPicturDialog()
    {
        personal_info personal_info = new personal_info();
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(context);
        pictureDialog.setTitle("قم بألختيار");
        String[] pictureDlialogItem={"اختر من المعرض" ,
                "قم بألتقاط صورة"};
        pictureDialog.setItems(pictureDlialogItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0 :
                        personal_info.choosePhotoFromGallary(context);
                        break;
                    case 1:
                        personal_info.takePhotoFromCamera(context);
                        break;
                }
            }
        });
        pictureDialog.show();
    }
*/

    public void set_image(Bitmap image, String image_url)
    {
        imageView.setImageBitmap(image);
        this.image_url=image_url;

    }

}
