package weblogic.deploy.event;

import java.util.EventListener;

public interface DeploymentEventListener extends EventListener {
   void applicationDeployed(DeploymentEvent var1);

   void applicationRedeployed(DeploymentEvent var1);

   void applicationDeleted(DeploymentEvent var1);
}
