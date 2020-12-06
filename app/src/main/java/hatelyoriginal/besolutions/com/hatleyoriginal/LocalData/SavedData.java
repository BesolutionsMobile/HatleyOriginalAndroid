package hatelyoriginal.besolutions.com.hatleyoriginal.LocalData;

import android.content.Context;
import android.content.SharedPreferences;

public class SavedData {
    public String get_lan(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("language", Context.MODE_PRIVATE);
        return sharedPreferences.getString("lan", "en");

    }
}
