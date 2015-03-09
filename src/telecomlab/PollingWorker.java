package telecomlab;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by ZiYang on 2015-03-09.
 */
public class PollingWorker extends SwingWorker<Void, Void> {

    private Client client;
    private volatile boolean running = true;
    public PollingWorker(Client client){
        this.client= client;
    }
    @Override
    protected Void doInBackground() throws Exception {
        long timeStamp = System.currentTimeMillis();
        while(running){
            try {
                if(System.currentTimeMillis()-timeStamp>1000){
                    client.queryMessage();
                    timeStamp=System.currentTimeMillis();
                }

            } catch (IOException e) {
            }


        }
        return null;
    }
    public void terminate() {
        running = false;
    }
}
