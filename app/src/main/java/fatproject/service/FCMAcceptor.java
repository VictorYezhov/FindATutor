package fatproject.service;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import fatproject.Helpers.MessageUpdateQueue;
import fatproject.activities.FragmentDispatcher;
import fatproject.internet.NotificationType;

/**
 * Created by Victor on 04.05.2018.
 */

public class FCMAcceptor extends FirebaseMessagingService {


    private static final String TAG = "FCM Service";
    private MessageUpdateQueue updateQueue = MessageUpdateQueue.getInstance();
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: Handle FCM messages here.


        Log.d(TAG, "Type: " + remoteMessage.getData().get("type"));
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if(remoteMessage.getData().get("type").equals(NotificationType.PERSONALMESSAGE.getType())){
            updateQueue.add(remoteMessage.getNotification().getBody());
        }
        





    }

}
