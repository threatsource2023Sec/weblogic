package weblogic.common;

public final class ConnectDisabledException extends ResourceException {
   private static final long serialVersionUID = 1073506607380441357L;

   public ConnectDisabledException(String s) {
      super(s);
   }

   public ConnectDisabledException() {
      this((String)null);
   }

   public ConnectDisabledException(String s, Throwable t) {
      super(s, t);
   }
}
