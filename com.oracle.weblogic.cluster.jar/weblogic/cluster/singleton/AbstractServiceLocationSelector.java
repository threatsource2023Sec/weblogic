package weblogic.cluster.singleton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import weblogic.cluster.ClusterMemberInfo;
import weblogic.cluster.ClusterService;
import weblogic.cluster.PartitionAwareSenderManager;
import weblogic.cluster.UpgradeUtils;
import weblogic.cluster.migration.ScriptExecutor;
import weblogic.cluster.singleton.ConsensusLeasing.Locator;
import weblogic.common.internal.PeerInfo;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.ServerMBean;

public abstract class AbstractServiceLocationSelector implements ServiceLocationSelector {
   protected static final boolean DEBUG = SingletonServicesDebugLogger.isDebugEnabled();

   protected List getAcceptableServers(List serverList) {
      ArrayList newList = new ArrayList();
      UpgradeUtils uu = UpgradeUtils.getInstance();
      Iterator itr = serverList.iterator();

      while(itr.hasNext()) {
         ServerMBean candidate = (ServerMBean)itr.next();
         if (uu.getServerVersion(candidate.getName()) == null) {
            newList.add(candidate);
         } else if (uu.getServerVersion(candidate.getName()).compareTo(PeerInfo.VERSION_920) > -1) {
            newList.add(candidate);
         }
      }

      return newList;
   }

   protected boolean isServerRunning(ServerMBean srvr) {
      boolean result = false;
      String serverName = srvr.getName();
      ClusterMemberInfo localInfo = ClusterService.getClusterServiceInternal().getLocalMember();
      if (localInfo != null && localInfo.serverName().equals(serverName) && !PartitionAwareSenderManager.theOne().isBlocked()) {
         return true;
      } else {
         ConsensusLeasing consensusLeasing = null;
         ClusterMBean clusterMBean = srvr.getCluster();
         if (clusterMBean != null && "consensus".equalsIgnoreCase(clusterMBean.getMigrationBasis())) {
            consensusLeasing = Locator.locate();
         }

         Collection remMembers = ClusterService.getClusterServiceInternal().getRemoteMembers();
         Iterator var8 = remMembers.iterator();

         while(var8.hasNext()) {
            ClusterMemberInfo info = (ClusterMemberInfo)var8.next();
            if (info.serverName().equals(serverName)) {
               if (consensusLeasing != null) {
                  String srvrState = consensusLeasing.getServerState(serverName);
                  if (srvrState == null || srvrState.equals("RESUMING") || srvrState.equals("RUNNING")) {
                     result = true;
                  }
               } else {
                  result = true;
               }
               break;
            }
         }

         return result;
      }
   }

   protected boolean executePostScript(MigratableTargetMBean mtBean, ServerMBean target, ServerMBean lastHost) {
      boolean result = true;
      if (mtBean.getPostScript() != null && mtBean.isPostScriptFailureFatal() && target != null) {
         boolean postScriptExecuted = false;
         boolean executionTried = false;
         if (lastHost != null) {
            executionTried = true;
            postScriptExecuted = this.executePostScript(mtBean, lastHost);
            if (!postScriptExecuted && DEBUG) {
               p("Failed to run the postscript on " + lastHost + " for " + mtBean.getName());
            }
         }

         if (!postScriptExecuted && mtBean.isNonLocalPostAllowed()) {
            executionTried = true;
            postScriptExecuted = this.executePostScript(mtBean, target);
            if (!postScriptExecuted && DEBUG) {
               p("Failed to run the postscript on " + target + " for " + mtBean.getName());
            }
         }

         if (executionTried && !postScriptExecuted) {
            result = false;
         }
      }

      return result;
   }

   protected boolean executePostScript(MigratableTargetMBean bean, ServerMBean target) {
      if (DEBUG) {
         p("Going to execute the post script " + bean.getPostScript() + " for " + bean.getName() + " on " + target);
      }

      return ScriptExecutor.runNMScript(bean.getPostScript(), bean, target);
   }

   protected static void p(Object o) {
      SingletonServicesDebugLogger.debug("ServiceLocationSelector: " + o);
   }

   public void migrationSuccessful(ServerMBean server, boolean automaticMigration) {
   }

   public abstract ServerMBean chooseServer();

   public abstract void setLastHost(ServerMBean var1);

   public abstract void setServerList(List var1);
}
