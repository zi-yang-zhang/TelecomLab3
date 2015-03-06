package telecomlab;

import gui.UsernameLabelCallback;

import java.io.*;
import java.net.Socket;

/**
 * Created by ZiYang on 2015-03-04.
 */
public class Client {
    private String hostname;
    private int port;
    private Socket socket;
    private String loggedInUsername;


    public Client(String hostname, int port){
        this.hostname = hostname;
        this.port = port;
        this.loggedInUsername="";
    }
    public void setLoggedInUsername(String username){this.loggedInUsername = username;}
    public String getLoggedInUsername(){return loggedInUsername;}

    public void connect() throws IOException {
        System.out.println("Attempting to connect to "+hostname+":"+port);
        socket = new Socket(hostname,port);
        System.out.println("Connection Established");
    }

    public void sendCommand(Message command) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        DataOutputStream  dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.write(command.getRawMessage());
        //System.out.println("Command sent to the server : "+ command.toString());
    }
    public void receiveMessage(UsernameLabelCallback callback) throws IOException {
        InputStream inputStream = socket.getInputStream();
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        Message response = MessageDecoder.decodeMessage(dataInputStream);
        ResponseDecoder.decode(response,callback);
    }


    public void echo() throws IOException {
        Message command  =  MessageEncoder.encodeMessage(CommandType.Echo,new byte[1]);
        sendCommand(command);
    }


    public void login(String username, String password) throws IOException {
        String credentials = username+","+password;
        Message command = MessageEncoder.encodeMessage(CommandType.Login,credentials.getBytes());
        sendCommand(command);
    }
    public void logoff() throws IOException {
        Message command = MessageEncoder.encodeMessage(CommandType.Logoff,new byte[1]);
        sendCommand(command);
    }
    public void createUser(String username, String password) throws IOException {
        String credentials = username+","+password;
        Message command = MessageEncoder.encodeMessage(CommandType.CreateUser,credentials.getBytes());
        sendCommand(command);
    }
    public void deleteUser() throws IOException {
        Message command = MessageEncoder.encodeMessage(CommandType.DeleteUser,new byte[1]);
        sendCommand(command);
    }
    public void createStore() throws IOException {
        Message command = MessageEncoder.encodeMessage(CommandType.CreateStore,new byte[1]);
        sendCommand(command);
    }
    public void sendMessage(String destUsername, String message) throws IOException {
        String credentials = destUsername+","+message;
        Message command = MessageEncoder.encodeMessage(CommandType.SendMessageToUser,credentials.getBytes());
        sendCommand(command);
    }
    public void queryMessage() throws IOException {
        Message command = MessageEncoder.encodeMessage(CommandType.QueryMessage,new byte[1]);
        sendCommand(command);
    }
    public void close() throws IOException {
        Message command = MessageEncoder.encodeMessage(CommandType.Exit,new byte[1]);
        sendCommand(command);
        socket.close();
    }


}
