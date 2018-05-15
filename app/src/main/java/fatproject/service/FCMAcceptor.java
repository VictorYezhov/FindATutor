package fatproject.service;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import fatproject.Helpers.MessageUpdateQueue;
import fatproject.activities.FragmentDispatcher;

/**
 * Created by Victor on 04.05.2018.
 */

public class FCMAcceptor extends FirebaseMessagingService {


    private static final String TAG = "FCM Service";
    private MessageUpdateQueue updateQueue = MessageUpdateQueue.getInstance();
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: Handle FCM messages here.


        Log.d(TAG, "Title: " + remoteMessage.getMessageType());
        Log.d(TAG, "From: " + remoteMessage.getFrom());
      //  Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        updateQueue.add(remoteMessage.getNotification().getBody());




    }

}
