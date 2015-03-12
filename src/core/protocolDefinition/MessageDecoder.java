package core.protocolDefinition;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by ZiYang on 2015-03-04.
 */
public class MessageDecoder implements Protocol{

    public static Message decodeMessage(DataInputStream dataInputStream) throws IOException {
        byte[] messageTypeBuffer = new byte[INT_SIZE];
        byte[] subMessageTypeBuffer = new byte[INT_SIZE];
        byte[] sizeBuffer = new byte[INT_SIZE];

        dataInputStream.read(messageTypeBuffer);
        dataInputStream.read(subMessageTypeBuffer);
        dataInputStream.read(sizeBuffer);

        int size = ByteBuffer.wrap(sizeBuffer).getInt();
        byte[] messageData = new byte[size];
        dataInputStream.read(messageData);

        Message messageFromServer = new Message();
        messageFromServer.setMessageType(ByteBuffer.wrap(messageTypeBuffer).getInt());
        messageFromServer.setMessageData(messageData);
        messageFromServer.setSize(size);
        messageFromServer.setSubMessageType(ByteBuffer.wrap(subMessageTypeBuffer).getInt());
        return messageFromServer;
    }

    public static Message decodeMessage(byte[] rawMessage){
        Message message = new Message();
        int messageType = ByteBuffer.wrap(rawMessage).getInt(Protocol.MESSAGE_TYPE_INDEX);
        int subMessageType = ByteBuffer.wrap(rawMessage).getInt(Protocol.SUB_MESSAGE_TYPE_INDEX);
        int size = ByteBuffer.wrap(rawMessage).getInt(Protocol.SIZE_INDEX);
        byte[] messageData = new byte[size];
        System.arraycopy(rawMessage,Protocol.MESSAGE_DATA_INDEX,messageData,0,size);
        message.setMessageType(messageType);
        message.setSubMessageType(subMessageType);
        message.setSize(size);
        message.setMessageData(messageData);
        message.setRawMessage(rawMessage);
        return message;
    }
}
