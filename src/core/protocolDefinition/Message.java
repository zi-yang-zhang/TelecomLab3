package core.protocolDefinition;

/**
 * Created by ZiYang on 2015-03-03.
 */
public class Message {

    private byte[] rawMessage;
    private int messageType;
    private int subMessageType;
    private int size;
    private byte[] messageData;
    public Message(){

    }

    public byte[] getRawMessage(){
        return rawMessage;
    }

    public void setRawMessage(byte[] rawMessage) {
        this.rawMessage = rawMessage;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public int getSubMessageType() {
        return subMessageType;
    }

    public void setSubMessageType(int subMessageType) {
        this.subMessageType = subMessageType;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public byte[] getMessageData() {
        return messageData;
    }

    public void setMessageData(byte[] messageData) {
        this.messageData = messageData;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Message{");
        sb.append("messageType=").append(messageType);
        sb.append(", subMessageType=").append(subMessageType);
        sb.append(", size=").append(size);
        sb.append(", messageData=").append(new String(messageData));
        sb.append('}');
        return sb.toString();
    }
}
