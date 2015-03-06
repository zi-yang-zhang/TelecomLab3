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
                    System.exit(0);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

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
