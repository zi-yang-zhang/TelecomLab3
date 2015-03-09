package telecomlab;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by ZiYang on 2015-03-07.
 */
public class ReceiverWorker extends SwingWorker<Void, Void> {
    private Client client;
    private volatile boolean running = true;
    public ReceiverWorker(Client client){
        this.client = client;
    }

    @Override
    protected Void doInBackground() throws Exception {
        while(running){
            try {
                client.receiveMessage();

            } catch (IOException e) {
            }


        }
        return null;
    }
    public void terminate() {
        running = false;
    }
}
