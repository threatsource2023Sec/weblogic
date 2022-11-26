package weblogic.messaging.saf;

public class SAFException extends Exception {
   static final long serialVersionUID = -7167007358028207744L;
   private boolean retry;
   private SAFResult.Result resultCode;

   public SAFException(String reason) {
      super(reason);
      this.resultCode = null;
   }

   public SAFException(String reason, SAFResult.Result resultCode) {
      this(reason);
      this.resultCode = resultCode;
   }

   public SAFException(String reason, Throwable thrown) {
      super(reason, thrown);
      this.resultCode = null;
   }

   public SAFException(String reason, Throwable thrown, SAFResult.Result resultCode) {
      this(reason, thrown);
      this.resultCode = resultCode;
   }

   public SAFException(String reason, boolean retry, Throwable thrown) {
      super(reason, thrown);
      this.resultCode = null;
      this.retry = retry;
   }

   public SAFException(Throwable thrown) {
      super(thrown);
      this.resultCode = null;
   }

   public final SAFResult.Result getResultCode() {
      return this.resultCode;
   }

   public final boolean shouldRetry() {
      return this.retry;
   }
}
