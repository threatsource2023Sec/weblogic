package javax.faces.model;

class NoRowAvailableException extends IllegalArgumentException {
   private static final long serialVersionUID = 3794135774633215459L;

   public NoRowAvailableException() {
   }

   public NoRowAvailableException(String s) {
      super(s);
   }

   public NoRowAvailableException(String message, Throwable cause) {
      super(message, cause);
   }

   public NoRowAvailableException(Throwable cause) {
      super(cause);
   }
}
