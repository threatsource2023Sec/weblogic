package weblogic.common;

public final class ConnectDeadException extends ResourceException {
   private static final long serialVersionUID = -7172663358986720879L;

   public ConnectDeadException(String s) {
      super(s);
   }

   public ConnectDeadException() {
      this((String)null);
   }

   public ConnectDeadException(String s, Throwable t) {
      super(s, t);
   }
}
