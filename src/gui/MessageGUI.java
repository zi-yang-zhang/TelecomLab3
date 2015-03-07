package gui;

import telecomlab.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by ZiYang on 2015-03-05.
 */
public class MessageGUI extends JFrame implements UsernameLabelCallback{
    private JButton sendButton;
    private JTextArea displayTextArea;
    private JButton exitButton;
    private JTextField messageTextField;
    private JPanel rootPanel;
    private JButton loginButton;
    private JButton createStoreButton;
    private JButton createUserButton;
    private JButton deleteUserButton;
    private JButton echoButton;
    private JButton logOffButton;
    private JButton queryButton;
    private JTextField destUsernameTextField;
    private JLabel usernameLabel;
    private JButton connectToServerButton;
    private TextAreaOutputSteam outputSteam;
    private Client client;


    public MessageGUI(Client client){
        super("Chat App");
        this.client = client;
        setContentPane(rootPanel);
        init();
        pack();
        setSize(1000, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.outputSteam = new TextAreaOutputSteam(displayTextArea);
        this.usernameLabel.setText("Welcome to Chat App beta. Please Login.");
        setVisible(true);
    }

    public void init(){
        disConnected();
        connectToServerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
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
                dialog.setName("Log in");
                dialog.pack();
                dialog.setVisible(true);
            }
        });

        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginDialog dialog = new LoginDialog(client,1);
                dialog.setName("Create User");
                dialog.pack();
                dialog.setVisible(true);
            }
        });
        echoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    client.echo();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        createStoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    client.createStore();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
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
        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    client.queryMessage();
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
                    client.close();
                    disConnected();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

    }

    public void connected(){
        loginButton.setVisible(true);
        logOffButton.setVisible(true);
        createStoreButton.setVisible(true);
        createUserButton.setVisible(true);
        deleteUserButton.setVisible(true);
        exitButton.setVisible(true);
        queryButton.setVisible(true);
        echoButton.setVisible(true);
        sendButton.setVisible(true);
        destUsernameTextField.setEditable(true);
        messageTextField.setEditable(true);
        startReceiving();
    }
    public void disConnected(){
        loginButton.setVisible(false);
        logOffButton.setVisible(false);
        createStoreButton.setVisible(false);
        createUserButton.setVisible(false);
        deleteUserButton.setVisible(false);
        exitButton.setVisible(false);
        queryButton.setVisible(false);
        echoButton.setVisible(false);
        sendButton.setVisible(false);
        destUsernameTextField.setEditable(false);
        messageTextField.setEditable(false);
    }

    public void startReceiving(){
        final UsernameLabelCallback callback = this;
        Thread receiverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                long timeStamp = System.currentTimeMillis();
                while(true){
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
        });
        receiverThread.start();
    }

    public TextAreaOutputSteam getOutputSteam(){
        return outputSteam;
    }


    @Override
    public void loginUsernameLabel() {
        this.usernameLabel.setText("Hi, "+client.getLoggedInUsername());
    }

    @Override
    public void logoutUsernameLabel() {
        this.usernameLabel.setText("Welcome to Chat App beta. Please Login.");
    }

}
