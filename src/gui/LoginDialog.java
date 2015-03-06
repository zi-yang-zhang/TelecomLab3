package gui;

import telecomlab.Client;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class LoginDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextPane usernameTextPane;
    private JTextField usernameField;
    private JTextPane passwordTextPane;
    private JPasswordField passwordField;
    private Client client;
    public LoginDialog(Client client, final int type) {
        setContentPane(contentPane);
        this.client = client;
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    onOK(type);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK(int type) throws IOException {
        if(type==0){
            client.login(usernameField.getText(),new String (passwordField.getPassword()));
        }else{
            client.createUser(usernameField.getText(),new String (passwordField.getPassword()));
        }
// add your code here
        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

}
