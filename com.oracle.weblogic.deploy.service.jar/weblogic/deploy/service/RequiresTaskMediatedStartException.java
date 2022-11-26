package weblogic.deploy.service;

public class RequiresTaskMediatedStartException extends Exception {
   private final String msg;

   public RequiresTaskMediatedStartException(String msg) {
      super(msg);
      this.msg = msg;
   }

   public String toString() {
      return this.msg != null ? "RequiresTaskMediatedStartException : " + this.msg : "RequiresTaskMediatedStartException";
   }
}
