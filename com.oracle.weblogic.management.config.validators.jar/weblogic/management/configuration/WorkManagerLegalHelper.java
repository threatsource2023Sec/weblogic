package weblogic.management.configuration;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import weblogic.diagnostics.debug.DebugLogger;

public final class WorkManagerLegalHelper {
   private static DebugLogger debug = DebugLogger.getDebugLogger("DebugValidateWorkManager");

   public static void validateMaxThreadsConstraint(MaxThreadsConstraintMBean mtc) {
      String poolName = mtc.getConnectionPoolName();
      if (mtc.getCount() > 0 && poolName != null && poolName.trim().length() > 0) {
         throw new IllegalArgumentException("Count and ConnectionPoolName cannot be set together. Please set either count or connection pool name but not both");
      }
   }

   public static void validateWorkManager(WorkManagerMBean wm) {
      int count = 0;
      if (wm.getFairShareRequestClass() != null) {
         ++count;
      }

      if (wm.getResponseTimeRequestClass() != null) {
         ++count;
      }

      if (wm.getContextRequestClass() != null) {
         ++count;
      }

      if (count > 1) {
         throw new IllegalArgumentException("WorkManagerMBean cannot have more than one RequestClass. Please choose either a FairShareRequestClass, ResponseTimeRequestClass, or ContextRequestClass but not more than one");
      } else {
         if (wm.getFairShareRequestClass() != null) {
            validateTargets(wm, wm.getFairShareRequestClass());
         }

         if (wm.getResponseTimeRequestClass() != null) {
            validateTargets(wm, wm.getResponseTimeRequestClass());
         }

         if (wm.getContextRequestClass() != null) {
            validateTargets(wm, wm.getContextRequestClass());
         }

         if (wm.getMinThreadsConstraint() != null) {
            validateTargets(wm, wm.getMinThreadsConstraint());
         }

         if (wm.getMaxThreadsConstraint() != null) {
            validateTargets(wm, wm.getMaxThreadsConstraint());
         }

         if (wm.getCapacity() != null) {
            validateTargets(wm, wm.getCapacity());
         }

      }
   }

   public static void validateTargets(WorkManagerMBean wm, DeploymentMBean deployment) {
      if (wm == null) {
         printDebug("VALID:  null WorkManagerMBean");
      } else if (deployment == null) {
         printDebug("VALID:  null DeploymentMBean");
      } else {
         TargetMBean[] wmTargets = wm.getTargets();
         if (wmTargets == null) {
            printDebug("VALID: WorkManagerMBean.getTargets() returned null");
         } else if (wmTargets.length == 0) {
            printDebug("VALID: WorkManagerMBean.getTargets() returned an empty array");
         } else {
            TargetMBean[] depTargets = deployment.getTargets();
            if (depTargets == null) {
               printDebug("VALID: DeploymentMBean.getTargets() returned null");
            } else if (depTargets.length == 0) {
               printDebug("VALID: DeploymentMBean.getTargets() returned an empty array");
            } else {
               Set wmServers = getServers(wmTargets);
               Set wmClusters = getClusters(wmTargets);
               Set depServers = getServers(depTargets);
               Set depClusters = getClusters(depTargets);
               if (wmServers.isEmpty() && wmClusters.isEmpty()) {
                  printDebug("VALID:  THe Work Manager has no servers or clusters targeted.");
               } else if (depServers.isEmpty() && depClusters.isEmpty()) {
                  printDebug("VALID:  THe Deployment MBean has no servers or clusters targeted.");
               } else {
                  Iterator var8 = wmServers.iterator();

                  String clusterName;
                  do {
                     if (!var8.hasNext()) {
                        var8 = wmClusters.iterator();

                        do {
                           if (!var8.hasNext()) {
                              throw new IllegalArgumentException("WorkManagerMBean '" + wm.getName() + "' refers to a constraint or request class '" + deployment.getName() + "' but they are deployed on targets that have no servers in common. Please deploy the mbeans so that they have at least one server in common.  Here are the servers and clusters:\n" + formatString("Work Manager", wmServers, wmClusters) + formatString("Deployment", depServers, depClusters));
                           }

                           clusterName = (String)var8.next();
                        } while(!depClusters.contains(clusterName));

                        printDebug("VALID:  They have at least one cluster in common: " + clusterName);
                        return;
                     }

                     clusterName = (String)var8.next();
                  } while(!depServers.contains(clusterName));

                  printDebug("VALID:  They have at least one server in common: " + clusterName);
               }
            }
         }
      }
   }

   private static Set getClusters(TargetMBean[] targets) {
      Set clusters = new HashSet();
      TargetMBean[] var2 = targets;
      int var3 = targets.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         TargetMBean target = var2[var4];
         if (target instanceof ClusterMBean) {
            clusters.add(target.getName());
         }
      }

      return clusters;
   }

   private static Set getServers(TargetMBean[] targets) {
      Set servers = new HashSet();
      TargetMBean[] var2 = targets;
      int var3 = targets.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         TargetMBean target = var2[var4];
         if (!(target instanceof ClusterMBean)) {
            servers.addAll(target.getServerNames());
         }
      }

      return servers;
   }

   private static String formatString(String s, Set servers, Set clusters) {
      StringBuilder sb = (new StringBuilder(s)).append(" ==> ");
      Iterator var4 = servers.iterator();

      String cluster;
      while(var4.hasNext()) {
         cluster = (String)var4.next();
         sb.append(cluster).append(',');
      }

      var4 = clusters.iterator();

      while(var4.hasNext()) {
         cluster = (String)var4.next();
         sb.append(cluster).append(',');
      }

      return sb.toString();
   }

   private static void printDebug(String s) {
      if (debug.isDebugEnabled()) {
         debug.debug("ValidateWorkManager:  " + s);
      }

   }
}
