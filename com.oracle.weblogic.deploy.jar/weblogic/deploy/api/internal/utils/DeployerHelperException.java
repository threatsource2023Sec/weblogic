package weblogic.deploy.api.internal.utils;

public class DeployerHelperException extends Exception {
   private Throwable original;

   DeployerHelperException(String message) {
      super(message);
   }

   DeployerHelperException(String message, Throwable exception) {
      super(message);
      this.original = exception;
   }

   public Throwable getOrignal() {
      return this.original;
   }

   public String getMessage() {
      String result = super.getMessage();
      if (this.original != null) {
         result = result + "\n" + this.original.getMessage();
      }

      return result;
   }
}
