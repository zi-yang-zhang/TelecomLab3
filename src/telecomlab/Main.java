package telecomlab;

import gui.MessageGUI;

import java.io.PrintStream;

public class Main {

    public static void main(String[] args) {
        Client client = new Client("ecse-489.ece.mcgill.ca",5001 );
        MessageGUI gui = new MessageGUI(client);
        System.setOut(new PrintStream(gui.getOutputSteam(), true));
    }
}
