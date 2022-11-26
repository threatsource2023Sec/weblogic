package weblogic.cluster.singleton;

import java.rmi.RemoteException;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.cluster.ClusterLogger;
import weblogic.cluster.ClusterService;
import weblogic.cluster.singleton.ConsensusLeasing.Locator;
import weblogic.jndi.Environment;
import weblogic.protocol.LocalServerIdentity;

class ClusterMaster implements MigratableServiceConstants, SingletonService, ClusterMasterRemote, ClusterLeaderListener {
   private boolean isClusterMaster = false;
   private final MigratableServersMonitorImpl monitor;
   static final String CLUSTER_MASTER = "CLUSTER_MASTER";

   ClusterMaster(int leaseRenewInterval) {
      this.monitor = new MigratableServersMonitorImpl(ClusterService.getServices().getServerLeasingService(), leaseRenewInterval);
   }

   public boolean isClusterMaster() {
      return this.isClusterMaster;
   }

   public void start() {
      if ("consensus".equals(MigratableServerService.theOne().getLeasingType())) {
         Locator.locate().addClusterLeaderListener(this);
      } else {
         SingletonServicesManagerService.getInstance().add(this.getName(), this);
      }

   }

   public void activate() {
      this.isClusterMaster = true;
      this.bindClusterMaster(this);
      this.monitor.start();
      ClusterLogger.logClusterMasterElected(LocalServerIdentity.getIdentity().getServerName());
   }

   public void deactivate() {
      if (this.isClusterMaster) {
         this.isClusterMaster = false;
         ClusterLogger.logRevokeClusterMasterRole(LocalServerIdentity.getIdentity().getServerName());
         this.monitor.stop();
      }
   }

   public void localServerIsClusterLeader() {
      this.activate();
   }

   public void localServerLostClusterLeadership() {
      this.deactivate();
   }

   public String getName() {
      return "CLUSTER_MASTER";
   }

   public void setServerLocation(String server, String machine) throws RemoteException {
      this.monitor.setServerLocation(server, machine);
   }

   public String getServerLocation(String server) throws RemoteException {
      return this.monitor.getCurrentMachine(server);
   }

   private void bindClusterMaster(final ClusterMaster cm) {
      try {
         AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               Context ctx = null;

               try {
                  Environment env = new Environment();
                  env.setCreateIntermediateContexts(true);
                  env.setReplicateBindings(false);
                  ctx = env.getInitialContext();
                  ctx.rebind("weblogic/cluster/singleton/ClusterMasterRemote", cm);
               } catch (NamingException var10) {
                  throw new AssertionError("Unexpected exception" + var10);
               } finally {
                  if (ctx != null) {
                     try {
                        ctx.close();
                     } catch (NamingException var9) {
                     }
                  }

               }

               return null;
            }
         });
      } catch (Exception var3) {
         throw new AssertionError("Unexpected exception" + var3);
      }
   }

   public void removeServerLocation(String serverName) {
      if (this.isClusterMaster() && this.monitor != null) {
         this.monitor.removeServerLocation(serverName);
      }

   }
}
