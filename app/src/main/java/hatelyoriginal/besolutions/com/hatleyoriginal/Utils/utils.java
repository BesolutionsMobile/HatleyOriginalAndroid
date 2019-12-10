package hatelyoriginal.besolutions.com.hatleyoriginal.Utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.SideMenuScenarios.Models.saved_places_list;


public class utils extends SQLiteOpenHelper {


    public static final String DB_NAME = "saved_places.db";
    public static final String TABLE_NAME ="saved_places";
    Context context;

   /* public utils(Context context) {
        this.context = context;
    }*/

    public utils(Context context) {
        super(context, DB_NAME, null, 1);
    }

    /**
     * SPLASH SCREEN
     */
      public void splash_screen(final Context context, final Class second_class)
       {
           new Thread(new Runnable() {
               public void run() {
                   try {
                       // sleep during 800ms
                       Thread.sleep(3000);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
                   // start HomeActivity
                   Intent intent=new Intent(context, second_class);
                   context.startActivity(intent);
                   ((AppCompatActivity)context).finish();
               }
           }).start();
       }

    /**
     * Upload Image
     */
     public void upload_image(Context context,int requestCode)
     {
         Intent i = new Intent( Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
         ((AppCompatActivity)context).startActivityForResult(Intent.createChooser(i, "Select Your Photo"),requestCode);
     }

    /**
     * REPLACE FRAGMENT
     */
    public void Replace_Fragment(Fragment fragment, int id, Context context)
    {
        //ADD FRAGMENT TO ACTIVITY
        Fragment home=fragment;
        ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction()
          .replace(id, home ).commit();
    }

    /**
     * convert to byte array
     */
    public Bitmap convertToBitmap(byte[] b){
        return BitmapFactory.decodeByteArray(b, 0, b.length);
    }


    public ArrayList RetreiveAllData()
    {
        ArrayList allPerson = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor=db.rawQuery("SELECT * FROM saved_places", null);
        if(cursor.moveToNext())
        {
            do{
                int id=cursor.getInt(0);
                String place_name=cursor.getString(1);
                Double lat=cursor.getDouble(2);
                Double lng=cursor.getDouble(3);
                allPerson.add(new saved_places_list(id,place_name,lat,lng));
            }while(cursor.moveToNext());
        }
        return allPerson;

    }

    public boolean add_new_place(String place,double lat,double lng)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("place", place);
        values.put("lat",lat);
        values.put("lng",lng);
        long status = db.insert(TABLE_NAME, null, values);
        if(status == -1){
            return false;
        }
        return true;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE saved_places(id INTEGER PRIMARY KEY AUTOINCREMENT,place TEXT,lat REAL,lng REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS saved_places");
        onCreate(sqLiteDatabase);
    }

}
