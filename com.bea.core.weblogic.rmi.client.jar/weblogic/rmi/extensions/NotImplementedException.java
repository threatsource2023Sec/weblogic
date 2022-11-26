package weblogic.rmi.extensions;

public final class NotImplementedException extends RuntimeException {
   private static final long serialVersionUID = 5254579466997351594L;

   public NotImplementedException() {
   }

   public NotImplementedException(String s) {
      super(s);
   }
}
