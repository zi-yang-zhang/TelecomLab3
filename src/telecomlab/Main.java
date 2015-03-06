package telecomlab;

import gui.MessageGUI;

import java.io.IOException;
import java.io.PrintStream;

public class Main {

    public static void main(String[] args) {
        final Client client = new Client("ecse-489.ece.mcgill.ca",5001 );

        try {
            MessageGUI gui = new MessageGUI(client);
            System.setOut(new PrintStream(gui.getOutputSteam(), true));
            client.connect();
            Thread receiverThread = new Thread(new Runnable() {
                @Override
                public void run() {

                    while(true){
                        try {
                            client.receiveMessage();

                        } catch (IOException e) {
                            System.exit(0);
                            e.printStackTrace();
                        }


                    }
                }
            });
            receiverThread.start();
            long timeStamp = System.currentTimeMillis();
            while(true){
                if(System.currentTimeMillis()-timeStamp>1000){
                    timeStamp = System.currentTimeMillis();
                    client.queryMessage();
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
