package gui;

import core.connection.Client;
import core.connection.PollingWorker;
import core.connection.ReceiverWorker;
import core.protocolDefinition.Message;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ZiYang on 2015-03-05.
 */
public class MessageGUI extends JFrame implements ResponseListener{
    private JButton sendButton;
    private JTextArea displayTextArea;
    private JButton exitButton;
    private JTextField messageTextField;
    private JPanel rootPanel;
    private JButton loginButton;
    private JButton createUserButton;
    private JButton deleteUserButton;
    private JButton logOffButton;
    private JTextField destUsernameTextField;
    private JLabel usernameLabel;
    private JButton connectToServerButton;
    private JComboBox servers;
    private JLabel toUsernameLabel;
    private JLabel messageLabel;
    private TextAreaOutputStream outputSteam;
    private Client client;
    private ReceiverWorker receiver;
    private PollingWorker poller;

    public MessageGUI(){
        super("Chat App");
        client = new Client();
        client.registerListener(this);
        setContentPane(rootPanel);
        init();
        pack();
        setSize(1000, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.outputSteam = new TextAreaOutputStream(displayTextArea);
        this.usernameLabel.setText("Welcome to Chat App beta. Please Login.");
        System.setOut(new PrintStream(getOutputSteam(), true));
    }

    public void init(){
        disConnected();
        connectToServerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    client.setServer(getServerFromComboBox().get(0), Integer.valueOf(getServerFromComboBox().get(1)));
                    client.connect();
                    connected();
                } catch (IOException e1) {
                    disConnected();
                    e1.printStackTrace();
                }
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginDialog dialog = new LoginDialog(client,0);
                dialog.setTitle("Log in");
                dialog.pack();
                dialog.setVisible(true);
            }
        });

        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginDialog dialog = new LoginDialog(client,1);
                dialog.setTitle("Create User");
                dialog.pack();
                dialog.setVisible(true);
            }
        });
        logOffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    client.logoff();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        deleteUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    client.deleteUser();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    client.sendMessage(destUsernameTextField.getText(),messageTextField.getText());
                    destUsernameTextField.setText("");
                    messageTextField.setText("");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    client.logoff();
                    client.close();
                    disConnected();
                    onLoggedOut();
                    receiver.terminate();
                    receiver.cancel(true);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

    }

    public void connected(){
        loginButton.setVisible(true);
        logOffButton.setVisible(true);
        logOffButton.setEnabled(false);
        createUserButton.setVisible(true);
        deleteUserButton.setVisible(true);
        deleteUserButton.setEnabled(false);
        exitButton.setVisible(true);
        sendButton.setVisible(true);
        sendButton.setEnabled(false);
        destUsernameTextField.setEditable(false);
        messageTextField.setEditable(false);
        destUsernameTextField.setVisible(true);
        messageTextField.setVisible(true);
        connectToServerButton.setVisible(false);
        toUsernameLabel.setVisible(true);
        messageLabel.setVisible(true);
        receiver = new ReceiverWorker(client);

        receiver.execute();


    }
    public void disConnected(){
        loginButton.setVisible(false);
        logOffButton.setVisible(false);
        createUserButton.setVisible(false);
        deleteUserButton.setVisible(false);
        exitButton.setVisible(false);
        sendButton.setVisible(false);
        destUsernameTextField.setVisible(false);
        messageTextField.setVisible(false);
        connectToServerButton.setVisible(true);
        toUsernameLabel.setVisible(false);
        messageLabel.setVisible(false);


    }


    public TextAreaOutputStream getOutputSteam(){
        return outputSteam;
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MessageGUI app = new MessageGUI();
                app.setVisible(true);
            }
        });
    }


    @Override
    public void onReceiveMessage(Message message) {

    }

    @Override
    public void onUserCreated() {
        try {
            client.login(client.getCredentials().get(0),client.getCredentials().get(1));
            client.createStore();
            client.logoff();
            System.setOut(new PrintStream(getOutputSteam(), true));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onLoggedIn() {
        this.usernameLabel.setText("Hi, " + client.getLoggedInUsername());
        loginButton.setEnabled(false);
        logOffButton.setEnabled(true);
        createUserButton.setEnabled(false);
        deleteUserButton.setEnabled(true);
        sendButton.setEnabled(true);
        destUsernameTextField.setEditable(true);
        messageTextField.setEditable(true);
        poller = new PollingWorker(client);
        poller.execute();

    }

    @Override
    public void onLoggedOut() {
        this.usernameLabel.setText("Welcome to Chat App beta. Please Login.");
        loginButton.setEnabled(true);
        logOffButton.setEnabled(false);
        createUserButton.setEnabled(true);
        deleteUserButton.setEnabled(false);
        sendButton.setEnabled(false);
        destUsernameTextField.setEditable(false);
        messageTextField.setEditable(false);
        poller.terminate();
        poller.cancel(true);

    }

    private List<String> getServerFromComboBox(){
        LinkedList<String> server = new LinkedList<>();
        for(String name:((String)this.servers.getSelectedItem()).split(":")){
            server.add(name);
        }
        return server;
    }
}
