package weblogic.io.common.internal;

public interface T3RemoteConstants {
   boolean DEBUG = false;
   int READ_START = 0;
   int READ_RESULT = 1;
   int SKIP_START = 2;
   int SKIP_RESULT = 3;
   int WRITE_START = 4;
   int WRITE_RESULT = 5;
   int FLUSH_START = 6;
   int FLUSH_RESULT = 7;
   int CLOSE_RESULT = 8;
   int CLOSE = 9;
   int ERROR = 10;
   String[] CMD_NAMES = new String[]{"READ_START", "READ_RESULT", "SKIP_START", "SKIP_RESULT", "WRITE_START", "WRITE_RESULT", "FLUSH_START", "FLUSH_RESULT", "CLOSE_RESULT", "CLOSE", "ERROR"};
}
