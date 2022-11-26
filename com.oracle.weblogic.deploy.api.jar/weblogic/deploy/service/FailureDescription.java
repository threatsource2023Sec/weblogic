package weblogic.deploy.service;

public class FailureDescription {
   private final String server;
   private final Exception reason;
   private final String operation;
   private final boolean connectFailure;

   public FailureDescription(String server, Exception reason, String operation) {
      this(server, reason, operation, false);
   }

   public FailureDescription(String server, Exception reason, String operation, boolean connectFailure) {
      this.server = server;
      this.reason = reason;
      this.operation = operation;
      this.connectFailure = connectFailure;
   }

   public String getServer() {
      return this.server;
   }

   public Exception getReason() {
      return this.reason;
   }

   public String getAttemptedOperation() {
      return this.operation;
   }

   public boolean isConnectFailure() {
      return this.connectFailure;
   }

   public String toString() {
      return "'" + this.operation + (this.connectFailure ? " connect" : "") + "' failure on '" + this.server + "' due to '" + this.reason.toString() + "'";
   }
}
