package core.protocolDefinition;

/**
 * Created by ZiYang on 2015-03-04.
 */
public enum CommandType {
    Exit(20){
        @Override
        public String toString() {
            return "Exit";
        }
    },
    BadlyFormattedMessage(21){
        @Override
        public String toString() {
            return "Badly Formatted Message";
        }
    },
    Echo(22){
        @Override
        public String toString() {
            return "Echo";
        }
    },
    Login(23){
        @Override
        public String toString() {
            return "Login";
        }
    },
    Logoff(24){
        @Override
        public String toString() {
            return "Log off";
        }
    },
    CreateUser(25){
        @Override
        public String toString() {
            return "Create User";
        }
    },
    DeleteUser(26){

        @Override
        public String toString() {
            return "Delete User";
        }
    },
    CreateStore(27){
        @Override
        public String toString() {
            return "Create Store";
        }
    },
    SendMessageToUser(28){
        @Override
        public String toString() {
            return "Send Message To User";
        }
    },
    QueryMessage(29){
        @Override
        public String toString() {
            return "Query Message";
        }
    };

    private int messageType;
    CommandType(int messageType){
        this.messageType = messageType;
    }

    public int getMessageType(){
        return messageType;
    }
}
