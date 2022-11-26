package javax.enterprise.deploy.spi.status;

import javax.enterprise.deploy.spi.TargetModuleID;
import javax.enterprise.deploy.spi.exceptions.OperationUnsupportedException;

public interface ProgressObject {
   DeploymentStatus getDeploymentStatus();

   TargetModuleID[] getResultTargetModuleIDs();

   ClientConfiguration getClientConfiguration(TargetModuleID var1);

   boolean isCancelSupported();

   void cancel() throws OperationUnsupportedException;

   boolean isStopSupported();

   void stop() throws OperationUnsupportedException;

   void addProgressListener(ProgressListener var1);

   void removeProgressListener(ProgressListener var1);
}
