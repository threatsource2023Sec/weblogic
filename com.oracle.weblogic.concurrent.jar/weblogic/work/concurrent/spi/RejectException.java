package weblogic.work.concurrent.spi;

public class RejectException extends Exception {
   private static final long serialVersionUID = 1L;
   private String message;

   public String getMessage() {
      return this.message;
   }

   public void setMessage(String message) {
      this.message = message;
   }
}
