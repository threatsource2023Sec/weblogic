package weblogic.protocol;

public class UnknownProtocolException extends Exception {
   public UnknownProtocolException(String s) {
      super(s);
   }

   public UnknownProtocolException(String s, Throwable t) {
      super(s, t);
   }

   public UnknownProtocolException(Throwable t) {
      super(t);
   }
}
