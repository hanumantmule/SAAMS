package com.bitshift.saams.helper;


import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import com.bitshift.saams.activity.MainActivity;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getData().size() > 0) {
            try {
                JSONObject json = new JSONObject(String.valueOf(remoteMessage.getData()));
                sendPushNotification(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    void sendPushNotification(JSONObject json) {
        try {

            JSONObject data = json.getJSONObject(Constant.DATA);

            String type = data.getString("type");
            String title = data.getString("title");
            String message = data.getString("message");
            String imageUrl = data.getString("image");
            String id = data.getString("id");

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);

          if (type.equals("notification")) {
                intent.putExtra(Constant.FROM, type);
            }
           else {
                intent.putExtra(Constant.FROM, "");
            }

            MyNotificationManager mNotificationManager = new MyNotificationManager(getApplicationContext());

            if (imageUrl.equals("null") || imageUrl.equals("")) {
                mNotificationManager.showSmallNotification(title, message, intent);
            } else {
                mNotificationManager.showBigNotification(title, message, imageUrl, intent);
            }


        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getLocalizedMessage());

        }
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
    }

}
