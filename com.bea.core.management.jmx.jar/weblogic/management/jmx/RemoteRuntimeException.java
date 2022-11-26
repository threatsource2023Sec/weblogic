package weblogic.management.jmx;

public class RemoteRuntimeException extends RuntimeException {
   RemoteRuntimeException(Exception cause) {
      super(cause);
   }

   RemoteRuntimeException(String msg, Exception cause) {
      super(msg, cause);
   }

   RemoteRuntimeException(String msg) {
      super(msg);
   }
}
