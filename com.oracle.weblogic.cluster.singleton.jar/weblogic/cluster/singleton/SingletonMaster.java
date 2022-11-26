package weblogic.cluster.singleton;

import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.cluster.ClusterExtensionLogger;
import weblogic.cluster.singleton.ConsensusLeasing.Locator;
import weblogic.cluster.singleton.MemberDeathDetector.ServerMigrationStateValidator;
import weblogic.health.HealthMonitorService;
import weblogic.jndi.Environment;
import weblogic.management.provider.ManagementService;
import weblogic.protocol.LocalServerIdentity;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;

class SingletonMaster implements MigratableServiceConstants, LeaseObtainedListener, LeaseLostListener, ClusterLeaderListener, SingletonMasterService {
   private boolean isSingletonMaster = false;
   private final LeaseManager manager;
   private final SingletonMonitor monitor;
   public static final String SINGLETON_MASTER = "SINGLETON_MASTER";
   private static final boolean DEBUG = SingletonServicesDebugLogger.isDebugEnabled();
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   SingletonMaster(LeaseManager manager, int leaseRenewInterval) {
      this.manager = manager;
      this.monitor = new SingletonMonitor(manager, leaseRenewInterval);
      this.bind("weblogic.cluster.singleton.SingletonServicesStateManager", this.monitor.getSingletonServicesStateManager(), false);
   }

   public void start() {
      if (DEBUG) {
         p("Starting Singleton Master Service. Beginning attempts to claim the SingletonMaster lock.");
      }

      if ("consensus".equals(MigratableServerService.theOne().getLeasingType())) {
         Locator.locate().addClusterLeaderListener(this);
      } else {
         try {
            this.manager.acquire("SINGLETON_MASTER", this);
         } catch (LeasingException var2) {
            if (DEBUG) {
               p("Encountered LeasingException while attempting to start the Singleton Master Service: ", var2);
            }

            if (!(var2.getCause() instanceof SQLException)) {
               if (DEBUG) {
                  p("Stopping the Leasing Manager due to non-SQLException acquring SINGLETON_MASTER");
               }

               this.manager.stop();
            }
         }
      }

   }

   public void stop() {
      if (DEBUG) {
         p("Stopping Singleton Master service.");
      }

      this.cleanup();
   }

   public synchronized boolean isSingletonMaster() {
      return this.isSingletonMaster;
   }

   public void localServerIsClusterLeader() {
      String srvr;
      try {
         if (DEBUG) {
            p("Local Server is the Cluster Leader. Going to acquire the SingletonMaster lease.");
         }

         String owner = this.manager.findOwner("SINGLETON_MASTER");
         if (owner != null) {
            srvr = LeaseManager.getServerNameFromOwnerIdentity(owner);
            if (!LocalServerIdentity.getIdentity().getServerName().equals(srvr)) {
               String srvrState = Locator.locate().getServerState(srvr);
               if (ServerMigrationStateValidator.canMigrateLease(srvrState)) {
                  if (DEBUG) {
                     p(srvr + " is marked as " + srvrState + ". Voiding all its leases");
                  }

                  this.manager.voidLeases(owner);
               }
            }
         }

         this.manager.acquire("SINGLETON_MASTER", this);
      } catch (LeasingException var4) {
         srvr = "Got LeasingException " + var4 + " while trying to acquire SingletonMaster lease";
         HealthMonitorService.subsystemFailed("DatabaseLessLeasing", srvr);
      }

   }

   public void localServerLostClusterLeadership() {
      this.onRelease();
   }

   public synchronized void onAcquire(String leaseName) {
      if (!this.isSingletonMaster) {
         this.isSingletonMaster = true;
         ClusterExtensionLogger.logSingletonMasterLeaseAcquired(LocalServerIdentity.getIdentity().getServerName());
         this.manager.addLeaseLostListener(this);
         this.monitor.start();
         this.bind("weblogic/cluster/singleton/SingletonMonitorRemote", this.monitor, false);
         if ("consensus".equals(MigratableServerService.theOne().getLeasingType())) {
            Locator.locate().addConsensusServiceGroupViewListener(this.monitor);
         }

      }
   }

   public void onException(Exception e, String leaseName) {
      if (DEBUG) {
         p("Encountered an exeption while trying to get the SingletonMaster lease. We are ignoring the exception and continuing to try to get the lease.", e);
      }

   }

   public synchronized void onRelease() {
      if (this.isSingletonMaster) {
         ClusterExtensionLogger.logSingletonMasterLeaseReleased(LocalServerIdentity.getIdentity().getServerName());
         this.cleanup();
      }
   }

   private synchronized void cleanup() {
      if (this.isSingletonMaster) {
         this.isSingletonMaster = false;
         this.monitor.stop();
         this.manager.removeLeaseLostListener(this);

         try {
            this.manager.release("SINGLETON_MASTER");
         } catch (LeasingException var2) {
         }

         this.unbindMigrator();
         if (!"consensus".equals(MigratableServerService.theOne().getLeasingType())) {
            if (!ManagementService.getRuntimeAccess(KERNEL_ID).getServerRuntime().isShuttingDown()) {
               this.start();
            }
         } else {
            Locator.locate().removeConsensusServiceGroupViewListener(this.monitor);
         }

      }
   }

   private void bind(final String jndiName, final Object obj, final boolean replicateBindings) {
      try {
         SecurityServiceManager.runAs(KERNEL_ID, KERNEL_ID, new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               Context ctx = null;

               try {
                  Environment env = new Environment();
                  env.setCreateIntermediateContexts(true);
                  env.setReplicateBindings(replicateBindings);
                  ctx = env.getInitialContext();
                  ctx.rebind(jndiName, obj);
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
      } catch (Exception var5) {
         throw new AssertionError("Unexpected exception" + var5);
      }
   }

   private void unbindMigrator() {
      try {
         AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               Context ctx = null;

               try {
                  Environment env = new Environment();
                  ctx = env.getContext();
                  ctx.unbind("weblogic/cluster/singleton/SingletonMonitorRemote");
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
      } catch (Exception var2) {
         throw new AssertionError("Unexpected exception" + var2);
      }
   }

   SingletonMonitor getSingletonMonitor() {
      return this.monitor;
   }

   public SingletonMonitorRemote getSingletonMonitorRemote() {
      return this.monitor;
   }

   private static final void p(String msg) {
      SingletonServicesDebugLogger.debug("SingletonMaster: " + msg);
   }

   private static final void p(String msg, Exception e) {
      SingletonServicesDebugLogger.debug("SingletonMaster: " + msg, e);
   }
}
