package weblogic.management.utils;

import java.security.AccessController;
import java.util.Iterator;
import java.util.Set;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.VirtualTargetMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public final class TargetingUtils {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public static ClusterMBean getTargetCluster(String target) {
      ClusterMBean cluster = null;
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      ServerMBean server = domain.lookupServer(target);
      if (server != null) {
         cluster = server.getCluster();
      }

      if (cluster == null) {
         cluster = domain.lookupCluster(target);
      }

      return cluster;
   }

   public static void addDynamicServerNames(TargetMBean target, Set targetServerNames) {
      if (target instanceof ClusterMBean) {
         addClusterServerNames((ClusterMBean)target, targetServerNames);
      } else if (target instanceof VirtualTargetMBean) {
         TargetMBean[] var2 = ((VirtualTargetMBean)target).getTargets();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            TargetMBean targetMBean = var2[var4];
            if (targetMBean instanceof ClusterMBean) {
               addClusterServerNames((ClusterMBean)targetMBean, targetServerNames);
            }
         }
      }

   }

   private static void addClusterServerNames(ClusterMBean cluster, Set targetServerNames) {
      DomainMBean rtDomain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      ClusterMBean rtCluster = rtDomain.lookupCluster(cluster.getName());
      if (rtCluster != null) {
         Set names = rtCluster.getServerNames();
         if (names != null) {
            Iterator var5 = names.iterator();

            while(var5.hasNext()) {
               Object name = var5.next();
               if (!targetServerNames.contains(name)) {
                  targetServerNames.add(name);
               }
            }

         }
      }
   }
}
