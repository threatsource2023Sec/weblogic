package weblogic.messaging.dispatcher;

public class DispatcherException extends Exception {
   static final long serialVersionUID = -2703860484874964564L;

   public DispatcherException(String string) {
      super(string);
   }

   public DispatcherException(Throwable t) {
      super(t);
   }

   public DispatcherException(String string, Throwable t) {
      super(string, t);
   }
}
