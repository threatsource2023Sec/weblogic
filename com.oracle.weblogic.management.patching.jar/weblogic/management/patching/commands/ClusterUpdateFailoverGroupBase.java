package weblogic.management.patching.commands;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.List;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.patching.PatchingDebugLogger;
import weblogic.management.provider.ManagementService;
import weblogic.management.workflow.command.AbstractCommand;
import weblogic.management.workflow.command.SharedState;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public abstract class ClusterUpdateFailoverGroupBase extends AbstractCommand {
   private static final long serialVersionUID = -2119983081008790679L;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   @SharedState
   public transient String clusterName;
   @SharedState
   public transient ArrayList failoverGroups;
   @SharedState
   public transient ArrayList origFailoverGroups;

   protected void updateFailoverGroups(String clusterName, List updateFailoverGroups, List revertFailoverGroups) throws CommandException {
      ServerUtils serverUtils = new ServerUtils();
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("ClusterUpdateFailoverGroupCommand.updateFailoverGroups ( " + clusterName + ", " + serverUtils.failoverGroupsToString(updateFailoverGroups) + ")");
      }

      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      ServerMBean[] servers = null;
      ClusterMBean[] clusters = domain.getClusters();
      ClusterMBean[] var8 = clusters;
      int var9 = clusters.length;

      int rollback;
      for(rollback = 0; rollback < var9; ++rollback) {
         ClusterMBean cluster = var8[rollback];
         if (cluster.getName().equals(clusterName)) {
            servers = cluster.getServers();
            break;
         }
      }

      if (servers != null) {
         for(int i = 0; i < servers.length; ++i) {
            if (serverUtils.isRunning(servers[i].getName())) {
               try {
                  this.applyFailoverGroupsWithServerUtils(serverUtils, servers[i].getName(), updateFailoverGroups);
               } catch (Exception var12) {
                  if (PatchingDebugLogger.isDebugEnabled()) {
                     PatchingDebugLogger.debug("ClusterUpdateFailoverGroupCommand unable to updateFailoverGroups on " + servers[i].getName());
                  }

                  for(rollback = i - 1; rollback >= 0; --rollback) {
                     this.applyFailoverGroupsWithServerUtils(serverUtils, servers[i].getName(), revertFailoverGroups);
                  }

                  throw var12;
               }
            }
         }
      }

   }

   protected void applyFailoverGroupsWithServerUtils(ServerUtils serverUtils, String serverName, List failoverGroups) throws CommandException {
      serverUtils.applyFailoverGroups(serverName, failoverGroups);
   }

   protected void updateFailoverGroups() throws CommandException {
      ServerUtils serverUtils = new ServerUtils();
      List revertFailoverGroups = new ArrayList();
      if (this.origFailoverGroups != null) {
         revertFailoverGroups.addAll(this.origFailoverGroups);
      }

      List updateFailoverGroups = new ArrayList();
      updateFailoverGroups.addAll(this.failoverGroups);
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("Updating " + this.clusterName + " with failoverGroups: " + serverUtils.failoverGroupsToString((List)updateFailoverGroups));
      }

      this.updateFailoverGroups(this.clusterName, updateFailoverGroups, revertFailoverGroups);
   }

   protected void revertFailoverGroups() throws CommandException {
      ServerUtils serverUtils = new ServerUtils();
      List revertFailoverGroups = new ArrayList();
      if (this.origFailoverGroups != null) {
         revertFailoverGroups.addAll(this.origFailoverGroups);
      }

      List updateFailoverGroups = new ArrayList();
      updateFailoverGroups.addAll(this.failoverGroups);
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("Reverting " + this.clusterName + " with failoverGroups: " + serverUtils.failoverGroupsToString((List)revertFailoverGroups));
      }

      this.updateFailoverGroups(this.clusterName, revertFailoverGroups, updateFailoverGroups);
   }
}
