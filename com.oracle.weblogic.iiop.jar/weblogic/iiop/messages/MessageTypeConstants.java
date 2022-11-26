package weblogic.iiop.messages;

public interface MessageTypeConstants {
   int UNKNOWN = -1;
   int REQUEST = 0;
   int REPLY = 1;
   int CANCEL_REQUEST = 2;
   int LOCATE_REQUEST = 3;
   int LOCATE_REPLY = 4;
   int CLOSE_CONNECTION = 5;
   int MESSAGE_ERROR = 6;
   int FRAGMENT = 7;
}
