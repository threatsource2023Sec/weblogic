package javax.batch.operations;

public class NoSuchJobInstanceException extends BatchRuntimeException {
   private static final long serialVersionUID = 1L;

   public NoSuchJobInstanceException() {
   }

   public NoSuchJobInstanceException(String message) {
      super(message);
   }

   public NoSuchJobInstanceException(Throwable cause) {
      super(cause);
   }

   public NoSuchJobInstanceException(String message, Throwable cause) {
      super(message, cause);
   }
}
