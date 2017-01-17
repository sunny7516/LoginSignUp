package com.example.tacademy.login_signup_module.fcm;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 *  메시지 수신
 */

public class MyfirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    // 메시지 수신 : 수신 이벤트 발생시 자동 호출
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getData().size() > 0) {
        }
        if (remoteMessage.getNotification() != null) {
        }
    }
}