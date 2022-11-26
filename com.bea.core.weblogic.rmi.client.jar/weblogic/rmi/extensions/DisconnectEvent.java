package weblogic.rmi.extensions;

public interface DisconnectEvent {
   Throwable getThrowable();

   String getMessage();

   long getTime();
}
