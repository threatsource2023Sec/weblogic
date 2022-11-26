package javax.websocket;

public final class SendResult {
   private final Throwable exception;
   private final boolean isOK;

   public SendResult(Throwable exception) {
      this.exception = exception;
      this.isOK = false;
   }

   public SendResult() {
      this.exception = null;
      this.isOK = true;
   }

   public Throwable getException() {
      return this.exception;
   }

   public boolean isOK() {
      return this.isOK;
   }
}
