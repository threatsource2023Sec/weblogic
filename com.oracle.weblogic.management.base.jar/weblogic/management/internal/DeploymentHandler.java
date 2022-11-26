package weblogic.management.internal;

import org.jvnet.hk2.annotations.Contract;
import weblogic.management.DeploymentException;
import weblogic.management.UndeploymentException;
import weblogic.management.configuration.DeploymentMBean;

@Contract
public interface DeploymentHandler {
   void prepareDeployment(DeploymentMBean var1, DeploymentHandlerContext var2) throws DeploymentException;

   void activateDeployment(DeploymentMBean var1, DeploymentHandlerContext var2) throws DeploymentException;

   void deactivateDeployment(DeploymentMBean var1, DeploymentHandlerContext var2) throws UndeploymentException;

   void unprepareDeployment(DeploymentMBean var1, DeploymentHandlerContext var2) throws UndeploymentException;
}
