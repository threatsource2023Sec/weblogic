package weblogic.deploy.internal.targetserver.state;

import weblogic.application.ModuleListener;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.SystemResourceMBean;

public class ListenerFactory {
   public static ModuleListener createListener(BasicDeploymentMBean b, String taskID, DeploymentState state) {
      if (b instanceof AppDeploymentMBean) {
         return new ModuleTransitionTracker((AppDeploymentMBean)b, taskID, state);
      } else if (b instanceof SystemResourceMBean) {
         return new ModuleStateTracker(state, (SystemResourceMBean)b);
      } else {
         throw new AssertionError("Unknown type " + b.getType());
      }
   }
}
