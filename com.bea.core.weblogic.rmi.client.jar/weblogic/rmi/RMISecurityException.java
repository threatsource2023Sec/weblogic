package weblogic.rmi;

/** @deprecated */
@Deprecated
public final class RMISecurityException extends SecurityException {
   private static final long serialVersionUID = -1683272254824320042L;

   public RMISecurityException() {
   }

   public RMISecurityException(String s) {
      super(s);
   }

   public RMISecurityException(String s1, String s2) {
      super(s1 + ": " + s2);
   }
}
