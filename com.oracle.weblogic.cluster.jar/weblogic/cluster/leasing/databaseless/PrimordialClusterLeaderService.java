package weblogic.cluster.leasing.databaseless;

import java.rmi.RemoteException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.cluster.ClusterMemberInfo;
import weblogic.cluster.ClusterMembersChangeEvent;
import weblogic.cluster.ClusterService;
import weblogic.cluster.InboundService;
import weblogic.cluster.messaging.internal.ClusterMessageFactory;
import weblogic.cluster.messaging.internal.ClusterMessageSender;
import weblogic.cluster.messaging.internal.ConsensusLeasingDebugLogger;
import weblogic.cluster.messaging.internal.MachineState;
import weblogic.cluster.messaging.internal.ServerInformation;
import weblogic.cluster.messaging.internal.ServerInformationImpl;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.MachineMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(10)
public class PrimordialClusterLeaderService extends AbstractServerService {
   @Inject
   @Named("ClusterServiceActivator")
   private ServerService dependencyOnClusterServiceActivator;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static PrimordialClusterLeaderService THE_ONE;
   private boolean stopped;
   private boolean canQuery = false;
   private List machineList;
   private ServerInformation leader = null;

   public PrimordialClusterLeaderService() {
      Class var1 = PrimordialClusterLeaderService.class;
      synchronized(PrimordialClusterLeaderService.class) {
         if (THE_ONE != null) {
            throw new AssertionError("PrimordialClusterLeaderService cannot be initialized more than once !");
         } else {
            THE_ONE = this;
         }
      }
   }

   public static PrimordialClusterLeaderService getInstance() {
      return THE_ONE;
   }

   public synchronized void start() throws ServiceFailureException {
      ClusterMBean clusterMBean = ManagementService.getRuntimeAccess(kernelId).getServer().getCluster();
      if (clusterMBean == null || !"consensus".equals(clusterMBean.getMigrationBasis()) || !isAutoServiceMigrationEnabled(clusterMBean) && !isAutoMigratableCluster(clusterMBean)) {
         if (ConsensusLeasingDebugLogger.isDebugEnabled()) {
            this.debug("Not starting the PrimordialClusterLeaderService");
         }

      } else {
         ClusterLeaderService.ensureServersHaveMachines(clusterMBean);
         this.canQuery = true;
         this.machineList = this.createMachineList(clusterMBean);
         InboundService.startListening();
         long discoveryTimeout = (long)ManagementService.getRuntimeAccess(kernelId).getServer().getCluster().getDatabaseLessLeasingBasis().getMemberDiscoveryTimeout();
         long endTime = System.currentTimeMillis() + discoveryTimeout;

         while(true) {
            try {
               if (ConsensusLeasingDebugLogger.isDebugEnabled()) {
                  this.debug("waiting for " + discoveryTimeout + " seconds to sync ... ");
               }

               this.wait(discoveryTimeout * 1000L);
            } catch (InterruptedException var9) {
            }

            long curTime = System.currentTimeMillis();
            ServerInformation leaderInfo = this.getLeaderInformation();
            if (leaderInfo != null || curTime >= endTime) {
               if (ConsensusLeasingDebugLogger.isDebugEnabled()) {
                  this.debug("done waiting to sync with cluster leader");
               }

               return;
            }

            discoveryTimeout = endTime - curTime;
         }
      }
   }

   public synchronized void clusterMembersChanged(ClusterMembersChangeEvent cece) {
      if (cece.getAction() == 0) {
         this.notify();
      }

   }

   ServerInformation getLeaderInformation() {
      return this.getLeaderInformationInternal(false);
   }

   public void stop() {
      this.stopped = true;
   }

   ServerInformation getLeaderInformationInternal(boolean queryOtherServers) {
      if (!this.canQuery || this.stopped && !queryOtherServers) {
         return null;
      } else {
         if (this.leader != null) {
            try {
               ServerInformation leaderInfo = this.queryForLeader(this.leader);
               if (leaderInfo != null) {
                  this.leader = leaderInfo;
                  return leaderInfo;
               }
            } catch (RemoteException var12) {
            }
         }

         Iterator iter = ClusterService.getClusterServiceInternal().getAllRemoteMembers().iterator();

         TreeSet discoveredMembers;
         ServerInformationImpl server;
         for(discoveredMembers = new TreeSet(); iter.hasNext(); discoveredMembers.add(server)) {
            ClusterMemberInfo info = (ClusterMemberInfo)iter.next();
            server = new ServerInformationImpl(info);
            if (ConsensusLeasingDebugLogger.isDebugEnabled()) {
               this.debug("discovered " + server);
            }
         }

         if (discoveredMembers.size() > 0) {
            try {
               return this.queryForLeader((ServerInformation)discoveredMembers.first());
            } catch (RemoteException var14) {
            }
         }

         if (queryOtherServers) {
            Iterator itr = this.machineList.iterator();

            while(itr.hasNext()) {
               MachineMBean machine = (MachineMBean)itr.next();
               MachineState machineState = MachineState.getMachineState(machine, true);
               List runningServers = machineState.getServersInState("RUNNING");
               Iterator srvrItr = runningServers.iterator();

               while(srvrItr.hasNext()) {
                  String srvrName = (String)srvrItr.next();
                  ServerInformation srvrInfo = new ServerInformationImpl(srvrName);

                  try {
                     ServerInformation leaderInfo = this.queryForLeader(srvrInfo);
                     if (leaderInfo != null) {
                        this.leader = leaderInfo;
                        return leaderInfo;
                     }
                  } catch (Exception var13) {
                     if (ConsensusLeasingDebugLogger.isDebugEnabled()) {
                        this.debug("Query for Leader encountered exception:  " + var13);
                     }
                  }
               }
            }
         }

         return discoveredMembers.size() > 0 ? (ServerInformation)discoveredMembers.first() : null;
      }
   }

   private ServerInformation queryForLeader(ServerInformation server) throws RemoteException {
      ClusterMessageSender messageSender = ClusterMessageFactory.getInstance().getDefaultMessageSender();
      LeaderQueryMessage message = LeaderQueryMessage.create(ClusterMember.getInstance().getLocalServerInformation());
      if (ConsensusLeasingDebugLogger.isDebugEnabled()) {
         this.debug("requesting cluster leader " + message + " from " + server);
      }

      LeaderQueryResponse response = (LeaderQueryResponse)messageSender.send(message, server);
      if (ConsensusLeasingDebugLogger.isDebugEnabled()) {
         this.debug("received response to the cluster leader query  " + response);
      }

      return response.getLeaderInformation();
   }

   private List createMachineList(ClusterMBean clusterMBean) {
      ServerMBean[] servers = clusterMBean.getServers();
      ArrayList machineList = new ArrayList();

      for(int i = 0; i < servers.length; ++i) {
         ServerMBean srvr = servers[i];
         MachineMBean machine = srvr.getMachine();
         if (!machineList.contains(machine)) {
            machineList.add(machine);
         }
      }

      return machineList;
   }

   private static boolean isAutoServiceMigrationEnabled(ClusterMBean cluster) {
      MigratableTargetMBean[] targets = cluster.getMigratableTargets();
      if (targets == null) {
         return false;
      } else {
         for(int i = 0; i < targets.length; ++i) {
            if (!"manual".equals(targets[i].getMigrationPolicy())) {
               return true;
            }
         }

         return false;
      }
   }

   private static boolean isAutoMigratableCluster(ClusterMBean cluster) {
      if (cluster == null) {
         return false;
      } else {
         ServerMBean[] servers = cluster.getServers();

         for(int i = 0; i < servers.length; ++i) {
            if (servers[i].isAutoMigrationEnabled()) {
               return true;
            }
         }

         return false;
      }
   }

   private void debug(String s) {
      ConsensusLeasingDebugLogger.debug("[PrimordialClusterLeaderService] " + s);
   }
}
