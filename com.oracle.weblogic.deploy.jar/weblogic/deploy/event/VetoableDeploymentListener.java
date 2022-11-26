package weblogic.deploy.event;

import java.util.EventListener;

public interface VetoableDeploymentListener extends EventListener {
   void vetoableApplicationDeploy(VetoableDeploymentEvent var1) throws DeploymentVetoException;

   void vetoableApplicationUndeploy(VetoableDeploymentEvent var1) throws DeploymentVetoException;
}
