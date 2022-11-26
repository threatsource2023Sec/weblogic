package weblogic.rmi.rmic;

public final class NonRemoteClassException extends Exception {
   private static final long serialVersionUID = -8015688947573936883L;

   public NonRemoteClassException() {
   }

   public NonRemoteClassException(String msg) {
      super(msg);
   }
}
