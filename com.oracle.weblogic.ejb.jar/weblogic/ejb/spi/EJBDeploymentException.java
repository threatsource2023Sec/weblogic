package weblogic.ejb.spi;

import java.io.PrintWriter;
import weblogic.ejb.container.EJBTextTextFormatter;
import weblogic.management.DeploymentException;
import weblogic.utils.PlatformConstants;

public final class EJBDeploymentException extends DeploymentException {
   private static final long serialVersionUID = 5093229614062171037L;
   private final String ejbName;
   private final String ejbFileName;
   private WLDeploymentException[] deploymentExceptions;
   private Throwable unexpectedError;

   public EJBDeploymentException(String ejbName, String ejbFileName, WLDeploymentException ex) {
      super(ejbName, ex);
      this.ejbName = ejbName;
      this.ejbFileName = ejbFileName;
      this.deploymentExceptions = new WLDeploymentException[1];
      this.deploymentExceptions[0] = ex;
   }

   public EJBDeploymentException(String ejbName, String ejbFileName, Throwable unexpectedError) {
      super(ejbName, unexpectedError);
      this.ejbName = ejbName;
      this.ejbFileName = ejbFileName;
      this.unexpectedError = unexpectedError;
   }

   public String getMessage() {
      StringBuilder sb = new StringBuilder(200);
      sb.append(EJBTextTextFormatter.getInstance().ejbDeploymentError(this.ejbName, this.ejbFileName));
      if (this.deploymentExceptions != null) {
         WLDeploymentException[] var2 = this.deploymentExceptions;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            WLDeploymentException deploymentException = var2[var4];
            sb.append(deploymentException.getErrorMessage());
            sb.append(PlatformConstants.EOL);
         }
      } else {
         if (this.unexpectedError == null) {
            throw new AssertionError("Expected either deploymentExceptions or unexpectedError to be non-null.");
         }

         sb.append(this.unexpectedError.getMessage());
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
