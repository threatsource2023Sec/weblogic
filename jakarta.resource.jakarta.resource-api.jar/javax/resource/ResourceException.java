package javax.resource;

public class ResourceException extends Exception {
   private String errorCode;
   private Exception linkedException;

   public ResourceException() {
   }

   public ResourceException(String message) {
      super(message);
   }

   public ResourceException(Throwable cause) {
      super(cause);
   }

   public ResourceException(String message, Throwable cause) {
      super(message, cause);
   }

   public ResourceException(String message, String errorCode) {
      super(message);
      this.errorCode = errorCode;
   }

   public void setErrorCode(String errorCode) {
      this.errorCode = errorCode;
   }

   public String getErrorCode() {
      return this.errorCode;
   }

   /** @deprecated */
   public Exception getLinkedException() {
      return this.linkedException;
   }

   /** @deprecated */
   public void setLinkedException(Exception ex) {
      this.linkedException = ex;
   }

   public String getMessage() {
      String msg = super.getMessage();
      String ec = this.getErrorCode();
      if (msg == null && ec == null) {
         return null;
      } else if (msg != null && ec != null) {
         return msg + ", error code: " + ec;
      } else {
         return msg != null ? msg : "error code: " + ec;
      }
   }
}
