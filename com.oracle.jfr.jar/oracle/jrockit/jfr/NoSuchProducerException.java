package oracle.jrockit.jfr;

public class NoSuchProducerException extends Exception {
   private static final long serialVersionUID = 2362960483505059802L;

   public NoSuchProducerException(int id) {
      this(String.valueOf(id));
   }

   public NoSuchProducerException() {
   }

   public NoSuchProducerException(String message) {
      super(message);
   }

   public NoSuchProducerException(Throwable cause) {
      super(cause);
   }

   public NoSuchProducerException(String message, Throwable cause) {
      super(message, cause);
   }
}
