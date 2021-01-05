package hatelyoriginal.besolutions.com.hatleyoriginal.LocalData;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SavedData {
    public String get_lan(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("language", Context.MODE_PRIVATE);
        return sharedPreferences.getString("lan", "en");

    }

    public String getCurrentPass(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("info", Context.MODE_PRIVATE);
        return sharedPreferences.getString("pass", "en");
    }
}
