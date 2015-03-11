package gui;

import core.connection.Client;

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

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK(int type) throws IOException {
        if(validator(usernameField.getText())){

            if(type==0){
                client.login(usernameField.getText(),new String (passwordField.getPassword()));
                client.setLoggedInUsername(usernameField.getText());
            }else{
                client.createUser(usernameField.getText(), new String(passwordField.getPassword()));
                client.setCredentials(usernameField.getText(),new String(passwordField.getPassword()));
            }
        }

        dispose();
    }

    private void onCancel() {
        dispose();
    }
    private boolean validator(String username){
        if(username.contains(",")){
            JOptionPane.showMessageDialog(this,
                    "Invalid Username!",
                    "WARNING.",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;

    }

}
