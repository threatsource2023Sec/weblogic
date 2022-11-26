package javax.websocket;

public class EncodeException extends Exception {
   private final Object object;
   private static final long serialVersionUID = 6L;

   public EncodeException(Object object, String message) {
      super(message);
      this.object = object;
   }

   public EncodeException(Object object, String message, Throwable cause) {
      super(message, cause);
      this.object = object;
   }

   public Object getObject() {
      return this.object;
   }
}
