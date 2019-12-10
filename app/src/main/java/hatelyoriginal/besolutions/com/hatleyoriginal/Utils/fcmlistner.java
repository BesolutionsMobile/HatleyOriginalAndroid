package hatelyoriginal.besolutions.com.hatleyoriginal.Utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import hatelyoriginal.besolutions.com.hatleyoriginal.MainActivity;
import hatelyoriginal.besolutions.com.hatleyoriginal.R;
import hatelyoriginal.besolutions.com.hatleyoriginal.StarActivity;

public class fcmlistner extends FirebaseMessagingService {

    private final String ADMIN_CHANNEL_ID ="admin_channel";

    TinyDB tinyDB;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
      //  final Intent intent = new Intent(this, offers.class);
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        int notificationID = new Random().nextInt(3000);

        tinyDB = new TinyDB(this);

        Intent broadcastIntent = new Intent(this, NotificationReceiver.class);
        broadcastIntent.putExtra("toastMessage", "message");
     //   PendingIntent actionIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        System.out.println("Printing the Type of the Notification"+remoteMessage.getData().get("type"));
        System.out.println("Printing the Name of Client of the Notification"+remoteMessage.getData().get("client_name"));
        System.out.println("Printing the ID of the Client Notification"+remoteMessage.getData().get("order_id"));

        //remoteMessage.getData().get("click_action");




        Intent intent = new Intent(remoteMessage.getData().get("type"));
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);



        Intent intent2;

        switch (remoteMessage.getData().get("type")) {
            case "reject_offer":
                intent2 = new Intent(this, StarActivity.class);
                break;
            case "accept_offer":
                intent2 = new Intent(remoteMessage.getData().get("type"));
                tinyDB.putString("reciverName", remoteMessage.getData().get("client_name"));
                tinyDB.putString("orderID",remoteMessage.getData().get("order_id"));
                break;
            default:
                intent2 = new Intent(this, MainActivity.class);
                break;
        }

        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent2.putExtra("data", remoteMessage.getData().get("type"));
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);










      /*
        Apps targeting SDK 26 or above (Android O) must implement notification channels and add its notifications
        to at least one of them. Therefore, confirm if version is Oreo or higher, then setup notification channel
      */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupChannels(notificationManager);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

       // PendingIntent pendingIntent = PendingIntent.getActivity(this , 0, intent,
         //       PendingIntent.FLAG_ONE_SHOT);

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),
                R.drawable.green);



        Uri notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
                .setSmallIcon(R.drawable.green)
                .setLargeIcon(largeIcon)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setAutoCancel(true)
                .setSound(notificationSoundUri)
                .setContentIntent(contentIntent)
                .setPriority(Notification.PRIORITY_MAX)
                .setWhen(0)
                .setColor(Color.parseColor("#000000"))
                .setStyle(new NotificationCompat.BigTextStyle());

        //Set notification color to match your app color template
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            notificationBuilder.setColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        notificationManager.notify(notificationID, notificationBuilder.build());
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupChannels(NotificationManager notificationManager){
        CharSequence adminChannelName = "New notification";
        String adminChannelDescription = "Device to devie notification";

        NotificationChannel adminChannel;
        adminChannel = new NotificationChannel(ADMIN_CHANNEL_ID, adminChannelName, NotificationManager.IMPORTANCE_HIGH);
        adminChannel.setDescription(adminChannelDescription);
        adminChannel.enableLights(true);
        adminChannel.setLightColor(Color.RED);
        adminChannel.enableVibration(true);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(adminChannel);
        }
    }
}
