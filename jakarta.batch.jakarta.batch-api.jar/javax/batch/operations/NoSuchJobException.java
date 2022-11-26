package javax.batch.operations;

public class NoSuchJobException extends BatchRuntimeException {
   private static final long serialVersionUID = 1L;

   public NoSuchJobException() {
   }

   public NoSuchJobException(String message) {
      super(message);
   }

   public NoSuchJobException(Throwable cause) {
      super(cause);
   }

   public NoSuchJobException(String message, Throwable cause) {
      super(message, cause);
   }
}
