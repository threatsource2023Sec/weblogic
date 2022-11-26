package weblogic.servlet.internal.session;

import weblogic.logging.Loggable;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.VirtualHostMBean;
import weblogic.servlet.internal.WebAppModule;

final class TargetValidator {
   static void validateTargetting(WebAppModule module, String type) throws DeploymentException {
      TargetMBean[] targets = module.getTargets();

      for(int i = 0; i < targets.length; ++i) {
         TargetMBean target = targets[i];
         if (target instanceof ServerMBean) {
            ClusterMBean cluster = ((ServerMBean)target).getCluster();
            if (cluster == null) {
               continue;
            }

            if (!listContainsAllClusterMembers(targets, cluster)) {
               Loggable l = HTTPSessionLogger.logInhomogeneousDeploymentForAppLoggable(module.getId(), module.getAppDisplayName(), type, cluster.getName());
               l.log();
               throw new DeploymentException(l.getMessage());
            }
         }

         if (target instanceof VirtualHostMBean) {
            validateTargetting((VirtualHostMBean)target, module, type);
         }
      }

   }

   private static void validateTargetting(VirtualHostMBean vhost, WebAppModule module, String type) throws DeploymentException {
      TargetMBean[] targets = vhost.getTargets();

      for(int i = 0; i < targets.length; ++i) {
         TargetMBean target = targets[i];
         if (target instanceof ServerMBean) {
            ClusterMBean cluster = ((ServerMBean)target).getCluster();
            if (cluster != null && !listContainsAllClusterMembers(targets, cluster)) {
               Loggable l = HTTPSessionLogger.logInhomogeneousDeploymentForVHostLoggable(module.getId(), module.getAppDisplayName(), type, vhost.getName(), cluster.getName());
               l.log();
               throw new DeploymentException(l.getMessage());
            }
         }
      }

   }

   private static boolean listContainsAllClusterMembers(TargetMBean[] targets, ClusterMBean cluster) {
      ServerMBean[] members = cluster.getServers();

      for(int i = 0; i < members.length; ++i) {
         if (!contains(targets, members[i])) {
            return false;
         }
      }

      return true;
   }

   private static boolean contains(TargetMBean[] targets, ServerMBean member) {
      for(int i = 0; i < targets.length; ++i) {
         if (targets[i] == member) {
            return true;
         }
      }

      return false;
   }
}
