package telecomlab;

import gui.UsernameLabelCallback;

import java.io.IOException;

/**
 * Created by ZiYang on 2015-03-07.
 */
public class ReceiverAndPollingThread implements Runnable {
    private Client client;
    private UsernameLabelCallback callback;
    private volatile boolean running = true;
    public ReceiverAndPollingThread(Client client, UsernameLabelCallback callback){
        this.client = client;
        this.callback= callback;
    }
    @Override
    public void run() {
        long timeStamp = System.currentTimeMillis();
        while(running){
            try {
                if (System.currentTimeMillis() - timeStamp > 1000) {
                    timeStamp = System.currentTimeMillis();
                    client.queryMessage();
                }
                client.receiveMessage(callback);

            } catch (IOException e) {
            }


        }

    }
    public void terminate() {
        running = false;
    }
}
