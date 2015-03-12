package core.protocolDefinition;

import gui.ResponseListener;

/**
 * Created by ZiYang on 2015-03-05.
 */
public class ResponseDecoder {

    public static void decode(Message message, ResponseListener listener){
        CommandType type = CommandType.values()[message.getMessageType()-20];
        int subMessageType = message.getSubMessageType();

        switch (type){
            case Exit:
                System.out.println(type.toString());
                break;
            case BadlyFormattedMessage:
                System.out.println(type.toString());
                break;
            case Echo:
                System.out.println(type.toString());
                break;
            case Login:
                switch (subMessageType){
                    case 0:
                        System.out.println("Login Ok");
                        listener.onLoggedIn();
                        break;
                    case 1:
                        System.out.println("User already logged in");
                        break;
                    case 2:
                        System.out.println("Bad credentials");
                        break;
                    case 3:
                        System.out.println("Badly formatted message");
                        break;
                    default:
                        System.out.println("Unknown SubMessage Type");
                        break;

                }
                break;
            case Logoff:
                switch (subMessageType){
                    case 0:
                        System.out.println("Logoff OK");
                        listener.onLoggedOut();
                        break;
                    case 1:
                        System.out.println("Not Logged In");
                        break;
                    case 2:
                        System.out.println("Session Expired");
                        break;
                    default:
                        System.out.println("Unknown SubMessage Type");
                        break;
                }
                break;
            case CreateUser:
                switch (subMessageType){
                    case 0:
                        System.out.println("User Creation Success");
                        listener.onUserCreated();
                        break;
                    case 1:
                        System.out.println("User Already Exists");
                        break;
                    case 2:
                        System.out.println("User Already Logged In");
                        break;
                    case 3:
                        System.out.println("Badly Formatted Create User Request");
                        break;
                    default:
                        System.out.println("Unknown SubMessage Type");
                        break;
                }
                break;
            case DeleteUser:
                switch (subMessageType){
                    case 0:
                        System.out.println("User Deletion success");
                        listener.onLoggedOut();
                        break;
                    case 1:
                        System.out.println("Not Logged In");
                        break;
                    default:
                        System.out.println("General Error ");
                        break;
                }
                break;
            case CreateStore:
                switch (subMessageType){
                    case 0:
                        listener.onReceiveMessage(message);
                        System.out.println("Store Created Successfully");
                        break;
                    case 1:
                        System.out.println("Store already exists");
                        break;
                    case 2:
                        System.out.println("Not Logged In");
                        break;
                    default:
                        System.out.println("Unknown SubMessage Type");
                        break;
                }
                break;
            case SendMessageToUser:
                switch (subMessageType){
                    case 0:
                        System.out.println("Message sent");
                        break;
                    case 1:
                        System.out.println("Failed to write to the user’s data store");
                        break;
                    case 2:
                        System.out.println("User doesn’t exist");
                        break;
                    case 3:
                        System.out.println("Not Logged In");
                        break;
                    case 4:
                        System.out.println("Badly formatted");
                        break;
                    default:
                        System.out.println("Unknown SubMessage Type");
                        break;
                }
                break;
            case QueryMessage:
                switch (subMessageType){
                    case 0:
                        break;
                    case 1:
                        listener.onReceiveMessage(message);
                        System.out.println("Messages:");
                        System.out.println(formatReceivedMessage(new String(message.getMessageData())));
                        break;
                    default:
                        break;
                }
                break;
        }

    }

    public static String formatReceivedMessage(String message){
        String [] response = message.split(",");
        StringBuilder builder = new StringBuilder();
        builder.append("@"+response[0]+":").append("\n").append(response[2]).append("\n").append(response[1]).append("\n");
        return builder.toString();

    }



}
