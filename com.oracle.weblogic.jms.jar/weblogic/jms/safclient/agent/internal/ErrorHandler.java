package weblogic.jms.safclient.agent.internal;

import weblogic.jms.safclient.agent.DestinationImpl;

public final class ErrorHandler {
   private String name;
   public static final int DISCARD_POLICY = 0;
   public static final int LOGGING_POLICY = 1;
   public static final int ERRORDESTINATION_POLICY = 2;
   public static final int ALWAYSFORWARD_POLICY = 3;
   private int policy = 0;
   private String logFormat;
   private String errorDestinationName;
   private DestinationImpl errorDestination;

   public ErrorHandler(String paramName) {
      this.name = paramName;
   }

   private static int policyStringToInt(String policy) {
      if (policy != null && !"Discard".equals(policy)) {
         if ("Log".equals(policy)) {
            return 1;
         } else if ("Redirect".equals(policy)) {
            return 2;
         } else {
            return "Always-Forward".equals(policy) ? 3 : 0;
         }
      } else {
         return 0;
      }
   }

   public void setPolicy(String paramPolicy) {
      this.policy = policyStringToInt(paramPolicy);
   }

   public void setLogFormat(String paramFormat) {
      this.logFormat = paramFormat;
   }

   public void setErrorDestinationName(String paramErrorDestinationName) {
      this.errorDestinationName = paramErrorDestinationName;
   }

   public String getErrorDestinationName() {
      return this.errorDestinationName;
   }

   public void setErrorDestination(DestinationImpl paramErrorDestination) {
      this.errorDestination = paramErrorDestination;
   }

   public String getLogFormat() {
      return this.logFormat;
   }

   public int getPolicy() {
      return this.policy;
   }

   public DestinationImpl getErrorDestination() {
      return this.errorDestination;
   }

   public String toString() {
      return "ErrorHandler(" + this.name + ")";
   }
}
