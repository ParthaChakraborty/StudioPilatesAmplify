package com.studio.amplify.firebase;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.studio.amplify.R;
import com.studio.amplify.activity.MainActivity;
import com.studio.amplify.activity.MyMessagingActivity;
import com.studio.amplify.activity.SplashScreenActivity;
import com.studio.amplify.activity.WebViewActivity;
import com.studio.amplify.util.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Random;


/**
 * Created by Krishna Singh on 12/4/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FirebaseIDService";
    Intent intent;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        System.out.println("remoteMessage data: " + remoteMessage.getData());
        try {
            Map<String, String> data = remoteMessage.getData();
            System.out.println("remote_msg_params: " + data.toString());
            JSONObject json = new JSONObject(data);
            Log.d("***remoteMessage",data.toString());
            sendNotification2(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendNotification2(JSONObject json) {
        try {
            Log.d("TAG", "JSON" + json);
            String menuList = json.getString("menu");
            String url = json.getString("url");

            if (menuList.equalsIgnoreCase("home_workouts")) {
                Bundle bundleDataToSend = new Bundle();
                bundleDataToSend.putString("webViewContentUrl", url);
                bundleDataToSend.putString("titleForHeader", "Home Workout");
                bundleDataToSend.putString("fromPushNotification", "1");
                intent = new Intent(this, WebViewActivity.class);
                intent.putExtras(bundleDataToSend);
                System.out.println("isSplashScreen: " + false);
            } else if (menuList.equalsIgnoreCase("cardio_plans")) {
                Bundle bundleDataToSend = new Bundle();
                bundleDataToSend.putString("webViewContentUrl", url);
                bundleDataToSend.putString("titleForHeader", "Cardio Plans");
                bundleDataToSend.putString("fromPushNotification", "1");
                intent = new Intent(this, WebViewActivity.class);
                intent.putExtras(bundleDataToSend);
            } else if (menuList.equalsIgnoreCase("health_tips")) {
                Bundle bundleDataToSend = new Bundle();
                bundleDataToSend.putString("urlKey", url);
                bundleDataToSend.putString("titleForHeader", "Nutrition Tips");
                bundleDataToSend.putString("fromPushNotification", "1");
                intent = new Intent(this, WebViewActivity.class);
                intent.putExtras(bundleDataToSend);
            } else if (menuList.equalsIgnoreCase("meal_plan")) {
                Bundle bundleDataToSend = new Bundle();
                bundleDataToSend.putString("urlKey", url);
                bundleDataToSend.putString("titleForHeader", "Recipe");
                bundleDataToSend.putString("fromPushNotification", "1");
                intent = new Intent(this, WebViewActivity.class);
                intent.putExtras(bundleDataToSend);
            } else if (menuList.equalsIgnoreCase("user_notification")) {
                Log.d("***Entered", "user_notification");
                Bundle bundleDataToSend = new Bundle();
                bundleDataToSend.putString("urlKey", url);
                bundleDataToSend.putString("titleForHeader", "Message");
                bundleDataToSend.putString("fromPushNotification", "1");
                intent = new Intent(this, WebViewActivity.class);
                intent.putExtras(bundleDataToSend);

            } else {
                System.out.println("isSplashScreen: " + true);
                intent = new Intent(this, SplashScreenActivity.class);
            }

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            Random random = new Random();
            int Unique_Integer_Number = random.nextInt(9999 - 1000) + 1000;
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ico_launcher);
            NotificationCompat.Builder noBuilder = null;

            if(json.has("body")) {
                noBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(getNotificationIcon())
                        .setLargeIcon(largeIcon)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(json.getString("body"))
                        .setSound(sound)
                        .setColor(Color.BLACK)
                        .setDefaults(Notification.DEFAULT_VIBRATE)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent);

            } else if(json.has("title")) {
                noBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(getNotificationIcon())
                        .setLargeIcon(largeIcon)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(json.getString("title"))
                        .setSound(sound)
                        .setColor(Color.BLACK)
                        .setDefaults(Notification.DEFAULT_VIBRATE)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent);
            }
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(Unique_Integer_Number, noBuilder.build());
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //This method is generating a notification and displaying the notification
    private void sendNotification(JSONObject json) {
        try {
            intent = new Intent(this, SplashScreenActivity.class);
            intent.putExtra("message", json.getString("message"));
            intent.putExtra("type", json.getString("type"));
            Random random = new Random();
            int Unique_Integer_Number = random.nextInt(9999 - 1000) + 1000;
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            int requestCode = 0;
            PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_ONE_SHOT);
            Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ico_launcher);

            NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(getNotificationIcon())
                    .setLargeIcon(largeIcon)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(json.getString("message"))
                    .setSound(sound)
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(Unique_Integer_Number, noBuilder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.notification_icon : R.drawable.ico_launcher;
    }

    @Override
    public void onNewToken(String s) {
        Log.d(TAG, "Refreshed token: " + s);
        Constant.FirebaseDeviceId = s;
        // TODO: Implement this method to send any registration to your app's servers.
    }

}