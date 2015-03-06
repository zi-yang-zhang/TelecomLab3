package telecomlab;

/**
 * Created by ZiYang on 2015-03-04.
 */
public interface Protocol {
    static final int MESSAGE_TYPE_INDEX  = 0;
    static final int SUB_MESSAGE_TYPE_INDEX = 4;
    static final int SIZE_INDEX = 8;
    static final int MESSAGE_DATA_INDEX = 12;
    static final int SUB_MESSAGE_TYPE_CODE = 0;
    static final int INT_SIZE = 4;
}
