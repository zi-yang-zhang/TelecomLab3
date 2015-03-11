package gui;

import core.protocolDefinition.Message;

/**
 * Created by ZiYang on 2015-03-09.
 */
public interface ResponseListener {
    void onReceiveMessage(Message message);
    void onUserCreated();
    void onLoggedIn();
    void onLoggedOut();

}
