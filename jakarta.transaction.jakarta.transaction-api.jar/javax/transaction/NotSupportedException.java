package javax.transaction;

public class NotSupportedException extends Exception {
   private static final long serialVersionUID = 56870312332816390L;

   public NotSupportedException() {
   }

   public NotSupportedException(String msg) {
      super(msg);
   }
}
