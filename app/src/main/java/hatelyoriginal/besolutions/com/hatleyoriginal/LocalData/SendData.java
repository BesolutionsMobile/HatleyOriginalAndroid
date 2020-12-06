package hatelyoriginal.besolutions.com.hatleyoriginal.LocalData;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SendData {
    public static void send_lan(Context context, String lan)
    {
        //SAVE LANGUAGE STATUS
        SharedPreferences sharedPreferences=context.getSharedPreferences("language",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("lan", lan);
        editor.commit();
    }
}
