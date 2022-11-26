package weblogic.deploy.api.spi.status;

import java.util.Iterator;
import javax.enterprise.deploy.spi.status.DeploymentStatus;

public interface WebLogicDeploymentStatus extends DeploymentStatus {
   Iterator getRootException();

   boolean isCompleted();

   boolean isFailed();

   boolean isRunning();
}
