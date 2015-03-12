package core.protocolDefinition;

import java.nio.ByteBuffer;

/**
 * Created by ZiYang on 2015-03-03.
 */
public class MessageEncoder implements Protocol{

    public static Message encodeMessage(CommandType type, byte[] message){

        byte[] messageType = ByteBuffer.allocate(INT_SIZE).putInt(type.getMessageType()).array();
        byte[] subMessageType = ByteBuffer.allocate(INT_SIZE).putInt(SUB_MESSAGE_TYPE_CODE).array();
        byte[] size = ByteBuffer.allocate(INT_SIZE).putInt(message.length).array();
        int totalMessageLength = messageType.length+subMessageType.length+size.length + message.length;
        byte [] commandMessage = new byte[totalMessageLength];

        System.arraycopy(messageType, 0, commandMessage, MESSAGE_TYPE_INDEX, messageType.length);
        System.arraycopy(subMessageType,0,commandMessage,SUB_MESSAGE_TYPE_INDEX,subMessageType.length);
        System.arraycopy(size,0,commandMessage,SIZE_INDEX,size.length);
        System.arraycopy(message,0,commandMessage,MESSAGE_DATA_INDEX,message.length);

        return MessageDecoder.decodeMessage(commandMessage);

    }


}
