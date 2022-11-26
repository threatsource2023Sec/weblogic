package weblogic.rmi.extensions;

public class DisconnectEventImpl implements DisconnectEvent {
   private long creationTime = System.currentTimeMillis();
   private String message;
   private Throwable cause;

   public DisconnectEventImpl() {
   }

   public DisconnectEventImpl(Throwable reason) {
      this.cause = reason;
   }

   public void setThrowable(Throwable reason) {
      this.cause = reason;
   }

   public void setMessage(String msg) {
      this.message = msg;
   }

   public Throwable getThrowable() {
      return this.cause;
   }

   public String getMessage() {
      return this.message == null ? this.cause.getMessage() : this.message;
   }

   public long getTime() {
      return this.creationTime;
   }

   public String toString() {
      return this.getClass().getName() + ":" + this.creationTime + ":" + this.message + ":cause:" + this.cause;
   }
}
