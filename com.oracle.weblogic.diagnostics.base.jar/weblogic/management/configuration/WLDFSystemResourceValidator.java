package weblogic.management.configuration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.utils.ArrayUtils;

public class WLDFSystemResourceValidator {
   public static void validateWLDFSystemResources(DomainMBean domain) {
      WLDFSystemResourceMBean[] wldfSysResources = domain.getWLDFSystemResources();
      if (wldfSysResources != null) {
         Set targettedServers = new HashSet();

         for(int i = 0; i < wldfSysResources.length; ++i) {
            TargetMBean[] targets = wldfSysResources[i].getTargets();
            if (targets != null) {
               TargetMBean[] targets = getServersInTargets(targets);

               for(int j = 0; j < targets.length; ++j) {
                  String key = targets[j].getName();
                  if (targettedServers.contains(key)) {
                     throw new IllegalArgumentException(DiagnosticsLogger.logTargettingMultipleWLDFSystemResourcesToServerLoggable(key).getMessage());
                  }

                  targettedServers.add(key);
               }
            }
         }

      }
   }

   private static ServerMBean[] getServersInTargets(TargetMBean[] targets) {
      ArrayList servers = new ArrayList();

      for(int i = 0; i < targets.length; ++i) {
         TargetMBean target = targets[i];
         if (target instanceof ServerMBean) {
            servers.add(target);
         } else {
            if (!(target instanceof ClusterMBean)) {
               throw new AssertionError("The list of targets contained a non-server or a non-cluster member");
            }

            ClusterMBean cluster = (ClusterMBean)target;
            ArrayUtils.addAll(servers, cluster.getServers());
         }
      }

      ServerMBean[] result = new ServerMBean[servers.size()];
      servers.toArray(result);
      return result;
   }
}
