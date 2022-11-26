package weblogic.messaging.interception.module;

import java.io.PrintWriter;
import weblogic.management.DeploymentException;
import weblogic.utils.AssertionError;
import weblogic.utils.PlatformConstants;
import weblogic.utils.StackTraceUtils;

public final class InterceptionDeploymentException extends DeploymentException {
   private String interceptionName;
   private String interceptionFileName;
   private WLDeploymentException[] deploymentExceptions;
   private Throwable unexpectedError;

   public InterceptionDeploymentException(String interceptionName, String interceptionFileName, WLDeploymentException ex) {
      super(interceptionName);
      this.interceptionName = interceptionName;
      this.interceptionFileName = interceptionFileName;
      this.deploymentExceptions = new WLDeploymentException[1];
      this.deploymentExceptions[0] = ex;
   }

   public InterceptionDeploymentException(String interceptionName, String interceptionFileName, WLDeploymentException[] exs) {
      super(interceptionName);
      this.interceptionName = interceptionName;
      this.interceptionFileName = interceptionFileName;
      this.deploymentExceptions = exs;
   }

   public InterceptionDeploymentException(String interceptionName, String interceptionFileName, Throwable unexpectedError) {
      super(interceptionName);
      this.interceptionName = interceptionName;
      this.interceptionFileName = interceptionFileName;
      this.unexpectedError = unexpectedError;
   }

   public String getMessage() {
      StringBuffer sb = new StringBuffer(200);
      if (this.deploymentExceptions != null) {
         sb.append(PlatformConstants.EOL);
         sb.append("Reason: ");

         for(int i = 0; i < this.deploymentExceptions.length; ++i) {
            sb.append(this.deploymentExceptions[i].getErrorMessage());
            sb.append(PlatformConstants.EOL);
         }
      } else {
         if (this.unexpectedError == null) {
            throw new AssertionError("Expected either deploymentExceptions or unexpectedError to be non-null.");
         }

         sb.append(StackTraceUtils.throwable2StackTrace(this.unexpectedError));
         sb.append(PlatformConstants.EOL);
      }

      sb.append(PlatformConstants.EOL);
      return sb.toString();
   }

   public String toString() {
      return this.getMessage();
   }

   public void printStackTrace(PrintWriter out) {
      super.printStackTrace(out);
      out.println(this.getMessage());
   }
}
