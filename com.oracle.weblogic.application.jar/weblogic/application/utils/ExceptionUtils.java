package weblogic.application.utils;

import weblogic.application.ModuleException;
import weblogic.application.WrappedDeploymentException;
import weblogic.management.DeploymentException;
import weblogic.utils.ErrorCollectionException;

public class ExceptionUtils {
   public static void throwDeploymentException(Throwable th) throws DeploymentException {
      if (th instanceof DeploymentException) {
         throw (DeploymentException)th;
      } else if (th instanceof ErrorCollectionException && ((ErrorCollectionException)th).size() == 1) {
         ErrorCollectionException ece = (ErrorCollectionException)th;
         throwDeploymentException((Throwable)ece.getErrors().next());
      } else {
         throw new WrappedDeploymentException(th);
      }
   }

   public static void throwModuleException(Throwable th) throws ModuleException {
      if (th instanceof ModuleException) {
         throw (ModuleException)th;
      } else if (th instanceof ErrorCollectionException && ((ErrorCollectionException)th).size() == 1) {
         ErrorCollectionException ece = (ErrorCollectionException)th;
         throwModuleException((Throwable)ece.getErrors().next());
      } else {
         throw new WrappedDeploymentException(th);
      }
   }
}
