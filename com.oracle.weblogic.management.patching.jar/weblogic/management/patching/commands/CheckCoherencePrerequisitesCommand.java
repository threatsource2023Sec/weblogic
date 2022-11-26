package weblogic.management.patching.commands;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.CoherenceClusterSystemResourceMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.patching.model.Node;
import weblogic.management.patching.model.ServerGroup;
import weblogic.management.provider.ManagementService;
import weblogic.management.workflow.command.SharedState;

public class CheckCoherencePrerequisitesCommand extends CheckPrerequisitesBaseCommand {
   private static final long serialVersionUID = -5113465200139054982L;
   @SharedState
   private String haStatusTarget;

   public boolean execute() throws Exception {
      boolean result = super.execute();
      if (!result) {
         return result;
      } else {
         Set affectedCoherenceClusters = new HashSet();
         DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
         DomainModelIterator iterator = new DomainModelIterator(this.domainModel);

         while(iterator.hasNextNode()) {
            Node node = iterator.nextNode();

            while(iterator.hasNextServerGroup()) {
               ServerGroup serverGroup = iterator.nextServerGroup();

               while(iterator.hasNextServer()) {
                  String server = iterator.nextServer().getServerName();
                  ServerMBean serverMBean = domain.lookupServer(server);
                  CoherenceClusterSystemResourceMBean coherenceCluster = this.getCoherenceCluster(serverMBean);
                  if (coherenceCluster != null) {
                     affectedCoherenceClusters.add(coherenceCluster);
                  }
               }
            }
         }

         if (affectedCoherenceClusters.size() == 0) {
            return true;
         } else {
            Iterator var10 = affectedCoherenceClusters.iterator();

            while(var10.hasNext()) {
               CoherenceClusterSystemResourceMBean coherenceCluster = (CoherenceClusterSystemResourceMBean)var10.next();
               List serverMBeans = this.getCoherenceManagedServers(domain, coherenceCluster);
               Set machines = this.getMachines(serverMBeans);
               this.checkForPossibleHAStatusEnforcement(serverMBeans, machines, coherenceCluster.getName());
            }

            return true;
         }
      }
   }

   private void checkForPossibleHAStatusEnforcement(List serverMBeans, Set machines, String coherenceCluster) throws CommandException {
      switch (this.haStatusTarget) {
         case "node-safe":
            boolean canBeNodeSafe = this.checkForPossibleNodeSafety(serverMBeans, machines, coherenceCluster);
            if (!canBeNodeSafe) {
               throw new CommandException(PatchingMessageTextFormatter.getInstance().cannotEnforceNodeSafeHAStatus(coherenceCluster));
            }
            break;
         case "machine-safe":
            boolean canBeMachineSafe = this.checkForPossibleMachineSafety(machines);
            if (!canBeMachineSafe) {
               throw new CommandException(PatchingMessageTextFormatter.getInstance().cannotEnforceMachineSafeHAStatus(coherenceCluster));
            }
      }

   }

   private CoherenceClusterSystemResourceMBean getCoherenceCluster(ServerMBean serverMBean) {
      ClusterMBean wlsClusterMBean = serverMBean.getCluster();
      if (wlsClusterMBean != null) {
         CoherenceClusterSystemResourceMBean coherenceClusterSystemResource = wlsClusterMBean.getCoherenceClusterSystemResource();
         if (coherenceClusterSystemResource != null) {
            return coherenceClusterSystemResource;
         }
      }

      return serverMBean.getCoherenceClusterSystemResource();
   }

   private List getCoherenceManagedServers(DomainMBean domain, CoherenceClusterSystemResourceMBean ccsrMBean) {
      ServerMBean[] servers = domain.getServers();
      List listServers = new ArrayList();
      ServerMBean[] var5 = servers;
      int var6 = servers.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         ServerMBean server = var5[var7];
         ClusterMBean cluster = server.getCluster();
         CoherenceClusterSystemResourceMBean foundCCSR = cluster != null && cluster.getCoherenceClusterSystemResource() != null ? cluster.getCoherenceClusterSystemResource() : server.getCoherenceClusterSystemResource();
         if (foundCCSR != null && foundCCSR.getName().equals(ccsrMBean.getName())) {
            listServers.add(server);
         }
      }

      return listServers;
   }

   private boolean checkForPossibleMachineSafety(Set machines) {
      return machines.size() >= 3;
   }

   private Set getMachines(List serverMBeans) {
      Set machineMBeans = new HashSet();
      Iterator var3 = serverMBeans.iterator();

      while(var3.hasNext()) {
         ServerMBean serverMBean = (ServerMBean)var3.next();
         machineMBeans.add(serverMBean.getMachine().getName());
      }

      return machineMBeans;
   }

   private boolean checkForPossibleNodeSafety(List serverMBeans, Set machines, String coherenceCluster) {
      boolean possibleNodeSafe = serverMBeans.size() >= 2 && machines.size() >= 2;
      if (serverMBeans.size() == 2 || machines.size() == 2) {
         PatchingLogger.logCoherenceHaNodeSafeTwoMachineWarning(coherenceCluster);
      }

      return possibleNodeSafe;
   }
}
