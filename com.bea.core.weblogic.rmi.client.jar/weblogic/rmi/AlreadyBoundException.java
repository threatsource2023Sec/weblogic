package weblogic.rmi;

/** @deprecated */
@Deprecated
public final class AlreadyBoundException extends Exception {
   private static final long serialVersionUID = 41657763509323723L;

   public AlreadyBoundException() {
   }

   public AlreadyBoundException(String s) {
      super(s);
   }
}
