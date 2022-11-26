package weblogic.management.patching.commands;

import java.rmi.RemoteException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.patching.PatchingDebugLogger;
import weblogic.management.provider.ManagementService;
import weblogic.management.workflow.command.AbstractCommand;
import weblogic.management.workflow.command.SharedState;
import weblogic.management.workflow.command.WorkflowContext;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public abstract class ClusterSessionHandlingBase extends AbstractCommand {
   private static final long serialVersionUID = 7742623665423972490L;
   protected static final boolean ENABLE = true;
   protected static final boolean DISABLE = false;
   @SharedState
   public String clusterName;
   @SharedState
   protected ArrayList failoverGroups;
   @SharedState
   protected ArrayList origFailoverGroups;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   protected List castFailoverGroups(ArrayList failoverGroupsToCast) {
      if (failoverGroupsToCast == null) {
         return null;
      } else {
         List castFailoverGroups = new ArrayList();
         castFailoverGroups.addAll(failoverGroupsToCast);
         return castFailoverGroups;
      }
   }

   public void initialize(WorkflowContext workFlowContext) {
      super.initialize(workFlowContext);
   }

   protected void applySessionHandlingSetting(boolean enable, List updateFailoverGroups) throws RemoteException, NamingException {
      this.applySessionHandlingSetting(enable, updateFailoverGroups, false, 0);
   }

   protected void applySessionHandlingSetting(boolean enable, List updateFailoverGroups, boolean disableTimeout, int sessionHandlingTimeout) throws RemoteException, NamingException {
      ServerUtils serverUtils = new ServerUtils();
      if (PatchingDebugLogger.isDebugEnabled()) {
         StringBuilder sb = new StringBuilder();
         sb.append("ClusterHandlingBase.applySessionHandling ( ");
         sb.append(this.clusterName);
         sb.append(", ");
         sb.append(serverUtils.failoverGroupsToString(updateFailoverGroups));
         PatchingDebugLogger.debug(sb.toString());
      }

      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      ServerMBean[] servers = null;
      ClusterMBean[] clusters = domain.getClusters();
      ClusterMBean[] var9 = clusters;
      int var10 = clusters.length;

      for(int var11 = 0; var11 < var10; ++var11) {
         ClusterMBean cluster = var9[var11];
         if (cluster.getName().equals(this.clusterName)) {
            servers = cluster.getServers();
            break;
         }
      }

      if (servers != null) {
         for(int i = 0; i < servers.length; ++i) {
            if (serverUtils.isRunning(servers[i].getName())) {
               this.applySessionHandlingSettingToServerUtils(serverUtils, enable, servers[i].getName(), updateFailoverGroups, disableTimeout, sessionHandlingTimeout);
            }
         }
      }

   }

   protected void applySessionHandlingSettingToServerUtils(ServerUtils serverUtils, boolean enable, String serverName, List failoverGroups, boolean disableTimeout, int sessionHandlingTimeout) throws RemoteException, NamingException {
      serverUtils.applySessionHandlingSetting(enable, serverName, failoverGroups, disableTimeout, sessionHandlingTimeout);
   }
}
