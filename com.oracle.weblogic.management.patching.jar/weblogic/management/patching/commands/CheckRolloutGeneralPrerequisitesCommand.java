package weblogic.management.patching.commands;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import weblogic.management.patching.PatchingDebugLogger;
import weblogic.management.patching.model.Cluster;
import weblogic.management.patching.model.Node;
import weblogic.management.patching.model.Server;
import weblogic.management.patching.model.ServerGroup;
import weblogic.management.workflow.CommandFailedException;
import weblogic.management.workflow.command.SharedState;

public class CheckRolloutGeneralPrerequisitesCommand extends CheckPrerequisitesBaseCommand {
   private static final long serialVersionUID = 8553263039262879302L;
   public static final String ENFORCE_HA_KEY = "enforceHA";
   public static final int MIN_CLUSTER_SIZE = 2;
   public static final int MIN_NODEMANAGER_COUNT = 2;
   @SharedState
   private transient boolean enforceHA = true;
   @SharedState
   private transient boolean useNM = true;

   public boolean execute() throws Exception {
      boolean result = super.execute();
      if (!result) {
         return result;
      } else {
         String className = this.getClass().getName();
         String workflowId = this.getContext().getWorkflowId();
         PatchingLogger.logExecutingStep(workflowId, className, this.logTarget);

         try {
            this.validateTopology();
            this.validateNMConnectivity();
            result = true;
            PatchingLogger.logCompletedStep(workflowId, className, this.logTarget);
            return result;
         } catch (Exception var5) {
            PatchingLogger.logFailedStepNoError(workflowId, className, this.logTarget);
            throw var5;
         }
      }
   }

   void validateAdminNodeManager() throws CommandFailedException {
      if (!Boolean.getBoolean("weblogic.nodemanager.ServiceEnabled")) {
         throw new CommandFailedException(PatchingMessageTextFormatter.getInstance().getNodeManagerRequiredAdmin());
      }
   }

   public void validateTopology() throws CommandException {
      Map clusterMap;
      Iterator var4;
      Cluster c;
      if (this.enforceHA) {
         new DomainModelIterator(this.domainModel);
         clusterMap = this.domainModel.getClusters();
         if (clusterMap != null && clusterMap.size() > 0) {
            var4 = clusterMap.values().iterator();

            while(var4.hasNext()) {
               c = (Cluster)var4.next();
               if (c.isTargeted()) {
                  int count = c.countNodes();
                  if (count < 2) {
                     throw new CommandException(PatchingMessageTextFormatter.getInstance().getMinimumClusterSizeNotMet(c.getClusterName(), count, 2));
                  }
               }
            }
         }
      }

      if (this.useNM && this.domainModel.getAdminNode().isTargeted()) {
         this.validateAdminNodeManager();
      }

      if (this.enforceHA) {
         this.validateClusters();
      }

      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("CHECKPREREQ: CheckRolloutGeneralPrerequisitesCommand Found following server(s) and node manager(s):");
      }

      DomainModelIterator iterator = new DomainModelIterator(this.domainModel);

      while(iterator.hasNextNode()) {
         Node n = iterator.nextNode();
         String machineName = n.getNodeName();
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("CHECKPREREQ: CheckRolloutGeneralPrerequisitesCommand Node Manager: " + machineName);
         }

         while(iterator.hasNextServerGroup()) {
            ServerGroup serverGroup = iterator.nextServerGroup();
            if (!serverGroup.isAdmin() && serverGroup.getCluster() == null && iterator.hasNextServer()) {
               throw new CommandException(PatchingMessageTextFormatter.getInstance().getStandAloneServerError(iterator.nextServer().getServerName()));
            }
         }
      }

      clusterMap = this.domainModel.getClusters();
      if (this.enforceHA && clusterMap != null && !clusterMap.isEmpty()) {
         var4 = clusterMap.values().iterator();

         while(var4.hasNext()) {
            c = (Cluster)var4.next();
            if (c.getNumTargetedMembers() != 0 && c.getNumUntargetedMembers() != 0) {
               throw new CommandException(PatchingMessageTextFormatter.getInstance().clusterContainsUntargetedServers(c.getClusterName(), c.getNumUntargetedMembers()));
            }
         }
      }

      iterator = new DomainModelIterator(this.domainModel);

      while(true) {
         Node n;
         do {
            if (!iterator.hasNextNode()) {
               return;
            }

            n = iterator.nextNode();
            String machineName = n.getNodeName();
         } while(!n.isTargeted());

         if (n.getNumUntargetedMembers() != 0) {
            throw new CommandException(PatchingMessageTextFormatter.getInstance().nodeContainsUntargetedServers(n.getNodeName()));
         }

         while(iterator.hasNextServerGroup()) {
            ServerGroup serverGroup = iterator.nextServerGroup();
            if (serverGroup.getNumTargetedMembers() != 0 && serverGroup.getNumUntargetedMembers() != 0) {
               throw new CommandException(PatchingMessageTextFormatter.getInstance().nodeContainsUntargetedServers(n.getNodeName()));
            }
         }
      }
   }

   public void validateClusters() throws CommandException {
      Map clusterMap = this.domainModel.getClusters();
      if (clusterMap != null && clusterMap.values() != null) {
         Iterator var2 = clusterMap.values().iterator();

         Cluster cluster;
         HashSet nodeNames;
         do {
            do {
               if (!var2.hasNext()) {
                  return;
               }

               cluster = (Cluster)var2.next();
            } while(!cluster.isTargeted());

            nodeNames = new HashSet();
            Map serverMap = cluster.getServers();
            if (serverMap != null && serverMap.values() != null) {
               Iterator var6 = serverMap.values().iterator();

               while(var6.hasNext()) {
                  Server server = (Server)var6.next();
                  String nodeName = server.getServerGroup().getNode().getNodeName();
                  if (!nodeNames.contains(nodeName)) {
                     nodeNames.add(nodeName);
                  }
               }
            }

            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("CHECKPREREQ: CheckRolloutGeneralPrerequisitesCommand validateClusters: Cluster name: " + cluster.getClusterName() + " Machines = " + nodeNames.size());
            }
         } while(nodeNames.size() >= 2);

         throw new CommandException(PatchingMessageTextFormatter.getInstance().getNotHAClusterError(cluster.getClusterName()));
      }
   }

   public void validateNMConnectivity() throws CommandException {
      DomainModelIterator iterator = new DomainModelIterator(this.domainModel);

      while(iterator.hasNextNode()) {
         Node n = iterator.nextNode();
         String machineName = n.getNodeName();
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("CHECKPREREQ: CheckRolloutGeneralPrerequisitesCommand validateNMConnectivity to machine: " + machineName);
         }

         if (!machineName.equals("?ORPHANED?")) {
            this.verifyConnection(machineName);
         }
      }

   }
}
