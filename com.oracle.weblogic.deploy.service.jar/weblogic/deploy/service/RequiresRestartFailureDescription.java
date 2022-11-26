package weblogic.deploy.service;

public final class RequiresRestartFailureDescription extends FailureDescription {
   public RequiresRestartFailureDescription(String server) {
      super(server, (Exception)null, (String)null);
   }

   public Exception getReason() {
      return null;
   }

   public String getAttemptedOperation() {
      return null;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("RequiresRestart failure description on server '");
      sb.append(this.getServer());
      sb.append("' due to non-dynamic changes");
      return sb.toString();
   }
}
