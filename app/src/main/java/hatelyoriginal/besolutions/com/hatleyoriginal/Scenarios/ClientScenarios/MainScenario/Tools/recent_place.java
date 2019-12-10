package hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.MainScenario.Tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import hatelyoriginal.besolutions.com.hatleyoriginal.Scenarios.ClientScenarios.MainScenario.Models.saved_places_list;


public class recent_place extends SQLiteOpenHelper {
    public static final String DB_NAME = "recent_places.db";
    public static final String TABLE_NAME ="recent_places";

    public recent_place(Context context) {
        super(context, DB_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE recent_places(id INTEGER PRIMARY KEY AUTOINCREMENT,place TEXT,lat REAL,lng REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS recent_places");
        onCreate(sqLiteDatabase);
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

    public ArrayList RetreiveAllData()
    {
        ArrayList<saved_places_list> allPerson=new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM recent_places", null);
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
}

