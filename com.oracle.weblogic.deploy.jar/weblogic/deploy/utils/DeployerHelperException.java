package weblogic.deploy.utils;

public class DeployerHelperException extends Exception {
   private Exception original;

   DeployerHelperException(String message) {
      super(message);
   }

   DeployerHelperException(String message, Exception exception) {
      super(message);
      this.original = exception;
   }

   public Exception getOrignal() {
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
