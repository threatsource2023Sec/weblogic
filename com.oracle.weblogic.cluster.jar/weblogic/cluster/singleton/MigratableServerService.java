package weblogic.cluster.singleton;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.cluster.ClusterLogger;
import weblogic.cluster.ClusterService;
import weblogic.cluster.singleton.ConsensusLeasing.Locator;
import weblogic.health.HealthMonitorService;
import weblogic.jndi.Environment;
import weblogic.management.ManagementException;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.MachineMBean;
import weblogic.management.configuration.NetworkAccessPointMBean;
import weblogic.management.configuration.NodeManagerMBean;
import weblogic.management.configuration.SSLMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.DomainAccessSettable;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.protocol.URLManagerService;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityManager;
import weblogic.security.service.SecurityServiceManager;
import weblogic.server.AbstractServerService;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;
import weblogic.store.io.jdbc.JDBCHelper;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.Timer;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.LocatorUtilities;
import weblogic.utils.net.AddressUtils;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

@Service
@Named
@RunLevel(10)
public class MigratableServerService extends AbstractServerService implements MigratableServiceConstants, LeaseLostListener, PropertyChangeListener, LeaseObtainedListener, NakedTimerListener, MigratableServer {
   @Inject
   @Named("DomainAccessService")
   ServerService dependencyOnDomainAccessService;
   @Inject
   @Named("NamingService")
   ServerService dependencyOnNamingService;
   @Inject
   @Named("RemoteNamingService")
   ServerService dependencyOnRemoteNamingService;
   @Inject
   @Named("PrimordialClusterLeaderService")
   private ServerService defendencyOnPrimordialClusterLeaderService;
   @Inject
   private RuntimeAccess runtimeAccess;
   private String serverName;
   private MachineMBean machine;
   private ServerMBean server;
   private boolean failedAndShouldMigrate = false;
   private boolean isMigratableServer;
   private boolean isMigratableCluster;
   private ClusterMaster clusterMaster;
   private SingletonMasterService singletonMaster;
   private ServerMigrationCoordinator coordinator;
   private int triggerIntervalMillis;
   private int idlePeriodsUntilTimeout;
   private static final boolean DEBUG = MigrationDebugLogger.isDebugEnabled();
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static MigratableServerService theMigratableServerService;
   private LeaseManager serverLeaseManager;
   private LeaseManager servicesLeaseManager;
   private String leasingType;
   private boolean clusterMasterUpdated = false;
   private Timer timer;
   private static final String UNRESOLVEABLE_JNDI_NAME = "__UNKNOWN";
   boolean BEADriver = false;

   private static void setInstance(MigratableServerService instance) {
      theMigratableServerService = instance;
   }

   public void initialize() throws ServiceFailureException {
      setInstance(this);
      if (this.runtimeAccess.isAdminServer()) {
         this.coordinator = new ServerMigrationCoordinatorImpl();
         this.bindServerMigrationCoordinator(this.coordinator);
      }

      this.server = this.runtimeAccess.getServer();
      this.isMigratableServer = this.server.isAutoMigrationEnabled();
      ClusterMBean cluster = this.server.getCluster();
      if (cluster != null) {
         this.leasingType = cluster.getMigrationBasis();
      }

      this.serverName = this.server.getName();
      this.triggerIntervalMillis = cluster == null ? 0 : cluster.getHealthCheckIntervalMillis();
      this.idlePeriodsUntilTimeout = cluster == null ? 0 : cluster.getHealthCheckPeriodsUntilFencing();
      if (this.isMigratableServer && cluster == null) {
         throw new ServiceFailureException("Migratable server is not part of a cluster");
      } else {
         if (this.isMigratableServer) {
            verifyMachineConfiguration(this.server);
            this.runtimeAccess.getServerRuntime().addPropertyChangeListener(this);
            this.machine = this.getCurrentMachine();
            if (this.machine == null) {
               throw new AssertionError("Failed to find the current machine");
            }

            this.runtimeAccess.getServerRuntime().setCurrentMachine(this.machine.getName());
         } else {
            this.machine = this.server.getMachine();
            if (MigrationDebugLogger.isDebugEnabled()) {
               MigrationDebugLogger.debug("MigratableServerService.initialize() : Final current machine = " + this.machine);
            }

            if (this.machine == null) {
               this.runtimeAccess.getServerRuntime().setCurrentMachine("");
            } else {
               this.runtimeAccess.getServerRuntime().setCurrentMachine(this.machine.getName());
            }
         }

         this.isMigratableCluster = this.isMigratableServer || isMigratableCluster(cluster);
         if (DEBUG) {
            p("Initializing isMigratableCluster?=" + this.isMigratableCluster);
         }

         if (cluster != null && ClusterService.getServices().getDefaultLeasingBasis() != null) {
            this.servicesLeaseManager = this.findOrCreateLeasingService("service");
            this.singletonMaster = ((SingletonMasterServiceGenerator)LocatorUtilities.getService(SingletonMasterServiceGenerator.class)).createSingletonMasterService(this.servicesLeaseManager, this.triggerIntervalMillis);
            this.servicesLeaseManager.start();
            SingletonServicesManagerService.getInstance().start();
         }

         if (this.isMigratableCluster) {
            try {
               this.serverLeaseManager = this.findOrCreateLeasingService("wlsserver");
               this.serverLeaseManager.start();
               this.clusterMaster = new ClusterMaster(this.triggerIntervalMillis);
            } catch (Throwable var3) {
               throw new ServiceFailureException("Failed to start singleton service because of: " + var3, var3);
            }
         }

      }
   }

   public String getLeasingType() {
      return this.leasingType;
   }

   public static MigratableServerService theOne() {
      return theMigratableServerService;
   }

   public boolean isClusterMaster() {
      return this.clusterMaster == null ? false : this.clusterMaster.isClusterMaster();
   }

   String findClusterMaster() throws LeasingException {
      if (this.isClusterMaster()) {
         return this.serverName;
      } else if (this.isConsensusLeasing()) {
         return this.findSingletonMaster();
      } else {
         return this.servicesLeaseManager == null ? null : this.unwrapServerID(this.servicesLeaseManager.findOwner("CLUSTER_MASTER"));
      }
   }

   ClusterMasterRemote getClusterMasterRemote() throws LeasingException {
      if (this.isClusterMaster()) {
         return this.clusterMaster;
      } else {
         String serverName = this.findClusterMaster();
         return this.getClusterMaster(serverName);
      }
   }

   public boolean isSingletonMaster() {
      return this.singletonMaster == null ? false : this.singletonMaster.isSingletonMaster();
   }

   public String findSingletonMaster() throws LeasingException {
      if (this.isSingletonMaster()) {
         return this.serverName;
      } else {
         return this.servicesLeaseManager == null ? null : this.unwrapServerID(this.servicesLeaseManager.findOwner("SINGLETON_MASTER"));
      }
   }

   public SingletonMonitorRemote getSingletonMasterRemote() throws LeasingException {
      return this.getSingletonMasterRemote(1);
   }

   public SingletonMonitorRemote getSingletonMasterRemote(int retryCount) throws LeasingException {
      if (this.isSingletonMaster()) {
         return this.singletonMaster.getSingletonMonitorRemote();
      } else {
         String serverName = null;
         LeasingException exception = new LeasingException("No singleton master found");

         for(int i = 0; i < retryCount; ++i) {
            if (i > 0) {
               try {
                  Thread.sleep(5000L);
               } catch (InterruptedException var6) {
               }
            }

            try {
               serverName = this.findSingletonMaster();
               SingletonMonitorRemote smr = this.getSingletonMaster(serverName);
               if (smr != null) {
                  return smr;
               }

               exception = new LeasingException("Could not contact singleton master: " + serverName);
            } catch (LeasingException var7) {
               exception = var7;
               p("getSingletonMasterRemote Failed - " + var7.getCause() + " with current subject: " + SecurityServiceManager.getCurrentSubject(kernelId));
            }
         }

         throw exception;
      }
   }

   public static String findURLOfUnconnectedServer(final String server) {
      PrivilegedExceptionAction action = new PrivilegedExceptionAction() {
         public Object run() {
            try {
               String url = MigratableServerService.getURLSuggestion(server);
               Environment env = new Environment();
               env.setProviderUrl(url);
               Context ctx = env.getInitialContext();
               ctx.lookup("__UNKNOWN");
               return null;
            } catch (NameNotFoundException var4) {
               return null;
            } catch (NamingException var5) {
               return var5;
            }
         }
      };

      try {
         Object result = SecurityManager.runAs(kernelId, SubjectUtils.getAnonymousSubject(), action);
         if (result != null) {
            return null;
         }
      } catch (PrivilegedActionException var4) {
         throw new AssertionError(var4);
      }

      try {
         return getURLManagerService().findAdministrationURL(server);
      } catch (UnknownHostException var3) {
         return null;
      }
   }

   private static URLManagerService getURLManagerService() {
      return (URLManagerService)GlobalServiceLocator.getServiceLocator().getService(URLManagerService.class, new Annotation[0]);
   }

   protected static String getURLSuggestion(String server) {
      ServerMBean s = ManagementService.getRuntimeAccess(kernelId).getDomain().lookupServer(server);
      if (s == null) {
         if (DEBUG) {
            p(server + " not found in Domain");
         }

         return null;
      } else {
         String protocol = s.getDefaultProtocol();
         String host = s.getListenAddress();
         if (host == null || host.equals("")) {
            host = "localhost";
         }

         if (s.isListenPortEnabled()) {
            int port = s.getListenPort();
            if (DEBUG) {
               p("Chosen url: " + protocol + "://" + host + ":" + port);
            }

            return protocol + "://" + host + ":" + port;
         } else {
            int i;
            if (s.getSSL() != null && s.getSSL().isEnabled()) {
               SSLMBean sslbean = s.getSSL();
               if (!protocol.endsWith("s")) {
                  protocol = protocol + "s";
               }

               i = sslbean.getListenPort();
               if (DEBUG) {
                  p("chosen SSL url: " + protocol + "://" + host + ":" + i);
               }

               return protocol + "://" + host + ":" + i;
            } else {
               NetworkAccessPointMBean[] naps = s.getNetworkAccessPoints();

               for(i = 0; i < naps.length; ++i) {
                  if (naps[i].isEnabled()) {
                     String subhost = naps[i].getListenAddress();
                     if (subhost != null && !subhost.equals("")) {
                        host = subhost;
                     }

                     if (DEBUG) {
                        p("chosen NAP url: " + naps[i].getProtocol() + "://" + host + ":" + naps[i].getListenPort());
                     }

                     return naps[i].getProtocol() + "://" + host + ":" + naps[i].getListenPort();
                  }
               }

               return null;
            }
         }
      }
   }

   public SingletonMonitorRemote getSingletonMaster(String server) throws LeasingException {
      if (server == null) {
         return null;
      } else {
         Environment env = new Environment();
         Context ctx = null;

         SingletonMonitorRemote var5;
         try {
            String url = null;

            try {
               url = getURLManagerService().findAdministrationURL(server);
            } catch (UnknownHostException var16) {
               url = findURLOfUnconnectedServer(server);
            }

            if (url == null) {
               var5 = null;
               return var5;
            }

            if (DEBUG) {
               p("Looking up SingletonMonitorRemote on " + server + " with url " + url);
            }

            env.setProviderUrl(url);
            ctx = env.getInitialContext();
            var5 = (SingletonMonitorRemote)ctx.lookup("weblogic/cluster/singleton/SingletonMonitorRemote");
         } catch (NamingException var17) {
            throw new LeasingException("Error connecting to Singleton Monitor at " + server + " : " + var17, var17);
         } finally {
            if (ctx != null) {
               try {
                  ctx.close();
               } catch (NamingException var15) {
               }
            }

         }

         return var5;
      }
   }

   private ClusterMasterRemote getClusterMaster(String server) {
      if (server == null) {
         return null;
      } else {
         Environment env = new Environment();
         Context ctx = null;

         ClusterMasterRemote var5;
         try {
            String url = findURLOfUnconnectedServer(server);
            if (DEBUG) {
               p("Looking up " + server + " at " + url);
            }

            if (url != null) {
               env.setProviderUrl(url);
               ctx = env.getInitialContext();
               var5 = (ClusterMasterRemote)ctx.lookup("weblogic/cluster/singleton/ClusterMasterRemote");
               return var5;
            }

            var5 = null;
         } catch (NamingException var16) {
            if (DEBUG) {
               var16.printStackTrace();
            }

            var5 = null;
            return var5;
         } finally {
            if (ctx != null) {
               try {
                  ctx.close();
               } catch (NamingException var15) {
               }
            }

         }

         return var5;
      }
   }

   private String unwrapServerID(String serverID) {
      if (serverID == null) {
         return null;
      } else {
         return serverID.indexOf("/") == -1 ? serverID : serverID.substring(serverID.indexOf("/") + 1, serverID.length());
      }
   }

   private static void verifyMachineConfiguration(ServerMBean server) throws ServiceFailureException {
      ClusterMBean cluster = server.getCluster();
      MachineMBean[] clusterCandidates = cluster.getCandidateMachinesForMigratableServers();
      MachineMBean[] serverCandidates = server.getCandidateMachines();
      HashSet set = new HashSet();
      if (serverCandidates == null && clusterCandidates == null) {
         ClusterLogger.logMisconfiguredMigratableCluster();
         throw new ServiceFailureException("Invalid migratable cluster configuration");
      } else {
         int i;
         if (serverCandidates != null) {
            for(i = 0; i < serverCandidates.length; ++i) {
               set.add(serverCandidates[i]);
            }
         }

         if (clusterCandidates != null) {
            for(i = 0; i < clusterCandidates.length; ++i) {
               set.add(clusterCandidates[i]);
            }
         }

         if (set.size() < 2) {
            ClusterLogger.logMigratableServerNotTargetToAMachine(server.getName());
         }

      }
   }

   public void start() throws ServiceFailureException {
      if (DEBUG) {
         p("Starting");
      }

      this.initialize();
      this.initializeMigrationMonitoring();
      if (this.singletonMaster != null) {
         this.singletonMaster.start();
      }

      if (this.isMigratableCluster) {
         this.clusterMaster.start();
         this.serverLeaseManager.addLeaseLostListener(this);
      }

      if (this.isMigratableServer) {
         if (this.isConsensusLeasing()) {
            WorkManager wm = WorkManagerFactory.getInstance().findOrCreate("WorkManagerForConsensusLeasing", 2, -1);
            this.timer = TimerManagerFactory.getTimerManagerFactory().getTimerManager("ConsensusLeasingTimerManager", wm).schedule(this, 0L, (long)this.triggerIntervalMillis);
            if (this.timer.isCancelled()) {
               this.timer = null;
            }
         } else {
            boolean assumedMembership = false;

            try {
               assumedMembership = this.serverLeaseManager.tryAcquire(this.serverName);
               this.updateClusterMaster();
            } catch (LeasingException var3) {
               ClusterLogger.logDatabaseUnreachable("startup", "database");
               if (MigrationDebugLogger.isDebugEnabled()) {
                  MigrationDebugLogger.debug("Failed to obtain lease: " + var3, var3);
               }

               throw new ServiceFailureException("There was a problem contacting the database: " + var3, var3);
            }

            if (!assumedMembership) {
               throw new ServiceFailureException("There is either a problem contacting the database or there is another instance of " + this.serverName + " running");
            }
         }

      }
   }

   public void timerExpired(Timer timer) {
      try {
         String owner = null;
         boolean fail = false;

         try {
            owner = this.serverLeaseManager.findOwner(this.serverName);
            if (owner == null) {
               if (MigrationDebugLogger.isDebugEnabled()) {
                  MigrationDebugLogger.debug("Found no owner for " + this.serverName + ", will acquire server lease");
               }

               if (this.acquireServerLease()) {
                  this.updateClusterMaster();
                  this.stopTimer(timer);
                  return;
               }

               if (MigrationDebugLogger.isDebugEnabled()) {
                  MigrationDebugLogger.debug("Could not acquire available server lease for " + this.serverName + " at this time, will retry");
               }

               return;
            }

            if (MigrationDebugLogger.isDebugEnabled()) {
               MigrationDebugLogger.debug("Another owner was found for lease of: " + this.serverName + ", other owner: " + owner);
            }

            fail = true;
         } catch (LeasingException var6) {
            Throwable thr = var6.getCause();
            if (thr == null || !(thr instanceof ClusterReformationInProgressException)) {
               throw var6;
            }

            if (MigrationDebugLogger.isDebugEnabled()) {
               MigrationDebugLogger.debug("Using PrimordialLeasingBasis to find owner for " + this.serverName);
            }

            owner = this.serverLeaseManager.findOwner(this.serverName);
            if (owner != null) {
               fail = true;
            }
         }

         if (fail) {
            String reason = "Another server " + owner + " has the consensus lease for: " + this.serverName;
            this.failShutDown(reason);
         }
      } catch (LeasingException var7) {
         if (MigrationDebugLogger.isDebugEnabled()) {
            MigrationDebugLogger.debug("Got exception trying to obtain consensus lease: " + this.serverName, var7);
         }
      }

   }

   private void failShutDown(String reason) {
      if (MigrationDebugLogger.isDebugEnabled()) {
         MigrationDebugLogger.debug(reason);
      }

      try {
         this.stop();
      } catch (ServiceFailureException var3) {
      }

      HealthMonitorService.subsystemFailedForceShutdown("MigratableServerService", reason);
   }

   private synchronized void stopTimer(Timer theTimer) {
      if (theTimer != null) {
         theTimer.cancel();
      }

      if (this.timer != null) {
         this.timer = null;
      }

   }

   private boolean acquireServerLease() throws LeasingException {
      boolean result = false;
      if (this.serverLeaseManager.tryAcquire(this.serverName)) {
         if (MigrationDebugLogger.isDebugEnabled()) {
            MigrationDebugLogger.debug("MigratableServerService has obtained the consensus lease for: " + this.serverName + "\nWill cancel the timer and add the LeaseListener");
         }

         result = true;
      }

      return result;
   }

   private void initializeMigrationMonitoring() throws ServiceFailureException {
      try {
         ServerMigrationRuntimeMBeanImpl.initialize();
         ServiceMigrationRuntimeMBeanImpl.initialize();
         if (this.runtimeAccess.isAdminServer()) {
            DomainMigrationHistoryImpl domainMigrationHistoryImpl = DomainMigrationHistoryImpl.createInstance();
            DomainAccessSettable domainAccessSettable = (DomainAccessSettable)ManagementService.getDomainAccess(kernelId);
            domainAccessSettable.setDomainMigrationHistory(domainMigrationHistoryImpl);
            ServerHelper.exportObject(domainMigrationHistoryImpl);
         }

      } catch (ManagementException var3) {
         throw new ServiceFailureException(var3);
      } catch (RemoteException var4) {
         throw new ServiceFailureException(var4);
      }
   }

   public void stop() throws ServiceFailureException {
      this.halt();
   }

   public void halt() throws ServiceFailureException {
      if (DEBUG) {
         p("Stopping");
      }

      this.stopTimer(this.timer);
      if (this.singletonMaster != null) {
         this.singletonMaster.stop();
      }

      SingletonServicesManagerService.getInstance().stop();
      if (this.isMigratableCluster) {
         this.servicesLeaseManager.voidLeases();
         this.serverLeaseManager.stop();
      }

      if (this.isMigratableServer) {
         if (!this.failedAndShouldMigrate) {
            try {
               this.serverLeaseManager.release(this.serverName);
            } catch (LeasingException var2) {
               ClusterLogger.logDatabaseUnreachable("shutdown", this.getLeaseBasisLocation());
               if (MigrationDebugLogger.isDebugEnabled()) {
                  MigrationDebugLogger.debug("Failed to obtain lease: " + var2, var2);
               }
            }

         }
      }
   }

   private boolean isConsensusLeasing() {
      return "consensus".equalsIgnoreCase(this.server.getCluster().getMigrationBasis());
   }

   private String getLeaseBasisLocation() {
      if (this.isConsensusLeasing()) {
         String location = Locator.locate().getLeasingBasisLocation();
         return location != null ? "leasing basis hosted by " + location : "consensus leasing basis";
      } else {
         return "database";
      }
   }

   public void propertyChange(PropertyChangeEvent event) {
      if ("State".equals(event.getPropertyName())) {
         String state = (String)event.getNewValue();
         if ("FAILED".equals(state)) {
            this.failedAndShouldMigrate = true;
         }
      }

   }

   private void bindServerMigrationCoordinator(final ServerMigrationCoordinator coor) {
      try {
         AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               Context ctx = null;

               try {
                  Environment env = new Environment();
                  env.setCreateIntermediateContexts(true);
                  env.setProperty("weblogic.jndi.createUnderSharable", "true");
                  ctx = env.getInitialContext();
                  ctx.bind("weblogic/cluster/singleton/ServerMigrationCoordinator", coor);
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

   private MachineMBean getConfiguredMachine() {
      return this.runtimeAccess.getDomain().lookupServer(this.serverName).getMachine();
   }

   private boolean isLocalMachine(MachineMBean machine) throws java.net.UnknownHostException {
      NodeManagerMBean nm = machine.getNodeManager();
      if (nm == null) {
         return true;
      } else {
         InetAddress addr = InetAddress.getByName(nm.getListenAddress());
         if (MigrationDebugLogger.isDebugEnabled()) {
            MigrationDebugLogger.debug("MigratableServerService.isLocalMachine() : Inet address = " + addr);
         }

         for(int j = 0; j < AddressUtils.getIPAny().length; ++j) {
            if (MigrationDebugLogger.isDebugEnabled()) {
               MigrationDebugLogger.debug("MigratableServerService.isLocalMachine() : AddressUtils.getIPAny[" + j + "]=" + AddressUtils.getIPAny()[j]);
            }

            if (addr.equals(AddressUtils.getIPAny()[j])) {
               MigrationDebugLogger.debug("MigratableServerService.isLocalMachine() : selected " + machine);
               return true;
            }
         }

         if (MigrationDebugLogger.isDebugEnabled()) {
            MigrationDebugLogger.debug("MigratableServerService.isLocalMachine() : returned false");
         }

         return false;
      }
   }

   public MachineMBean getCurrentMachine() {
      try {
         MachineMBean configuredMachine = this.getConfiguredMachine();
         if (MigrationDebugLogger.isDebugEnabled()) {
            MigrationDebugLogger.debug("MigratableServerService.getCurrentMachine() : configured machine = " + configuredMachine);
         }

         if (configuredMachine == null) {
            return null;
         } else if (this.isLocalMachine(configuredMachine)) {
            if (MigrationDebugLogger.isDebugEnabled()) {
               MigrationDebugLogger.debug("MigratableServerService.getCurrentMachine() : configured machine = " + configuredMachine + " matches machine from local list ");
            }

            return configuredMachine;
         } else {
            MachineMBean[] machines = this.runtimeAccess.getDomain().getMachines();
            if (MigrationDebugLogger.isDebugEnabled()) {
               MigrationDebugLogger.debug("MigratableServerService.getCurrentMachine() : now going to domain mbean to get list of machines = " + Arrays.toString(machines));
            }

            for(int i = 0; i < machines.length; ++i) {
               if (this.isLocalMachine(machines[i])) {
                  return machines[i];
               }
            }

            return null;
         }
      } catch (java.net.UnknownHostException var4) {
         throw new AssertionError("Unexpected exception" + var4);
      }
   }

   private static boolean isMigratableCluster(ClusterMBean cluster) {
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

   public void onRelease() {
      ClusterLogger.logServerFailedtoRenewLease(this.serverName, this.getLeaseBasisLocation());
      ClusterLogger.logDatabaseUnreachableForLeaseRenewal(this.idlePeriodsUntilTimeout * this.triggerIntervalMillis / 1000, this.getLeaseBasisLocation());
      HealthMonitorService.subsystemFailedForceShutdown("ServerMigration", "Server" + this.serverName + " failed to renew lease in the " + this.getLeaseBasisLocation());
   }

   boolean isBEADriver() {
      return this.BEADriver;
   }

   QueryHelper identifyVendorSpecificQuery(String tableName, Connection con) {
      String queryHelperClassName = this.server.getCluster().getSingletonSQLQueryHelper();
      String domainName = this.server.getParent().getName();
      String clusterName = this.server.getCluster().getName();

      try {
         String dn = con.getMetaData().getDriverName().toLowerCase(Locale.ENGLISH);
         if (!dn.startsWith("oracle jdbc")) {
            this.BEADriver = true;
         }
      } catch (SQLException var11) {
         throw new AssertionError("Could not contact database to get vendor and version: " + var11);
      }

      if (queryHelperClassName != null && queryHelperClassName.length() > 0) {
         try {
            Class clazz = Class.forName(queryHelperClassName);
            Constructor queryHelperClassConstructor = clazz.getConstructor((Class[])null);
            QueryHelper qh = (QueryHelper)queryHelperClassConstructor.newInstance((Object[])null);
            qh.init(tableName, domainName, clusterName, JDBCHelper.getDBMSType(con.getMetaData(), (String[])null));
            return qh;
         } catch (Throwable var9) {
            ClusterLogger.logUnableToLoadCustomQueryHelper(queryHelperClassName, var9);
            AssertionError ae = new AssertionError("Failed to load " + queryHelperClassName + " because of " + var9);
            ae.initCause(var9);
            throw ae;
         }
      } else {
         try {
            return new QueryHelperImpl(tableName, domainName, clusterName, JDBCHelper.getDBMSType(con.getMetaData(), (String[])null));
         } catch (SQLException var10) {
            throw new AssertionError("Could not contact database to get vendor and version: " + var10);
         }
      }
   }

   private static final void p(String msg) {
      if (MigrationDebugLogger.isDebugEnabled()) {
         MigrationDebugLogger.debug("<MigratableServerService>: " + msg);
      }

   }

   public void onAcquire(String leaseName) {
      this.updateClusterMaster();
   }

   public void onException(Exception e, String leaseName) {
   }

   private void updateClusterMaster() {
      if (!this.clusterMasterUpdated) {
         try {
            ClusterMasterRemote cmr = this.getClusterMasterRemote();
            if (cmr != null) {
               cmr.setServerLocation(this.serverName, this.machine.getName());
               this.clusterMasterUpdated = true;
            }
         } catch (LeasingException var2) {
            if (DEBUG) {
               var2.printStackTrace();
            }
         } catch (RemoteException var3) {
            if (DEBUG) {
               var3.printStackTrace();
            }
         }

      }
   }

   public void notifySingletonMasterShutdown() {
      try {
         SingletonMonitorRemote singletonMaster = this.getSingletonMasterRemote();
         if (DEBUG) {
            p("notifySingletonMasterShutdown server: " + this.serverName + ", ClusterMaster: " + this.clusterMaster);
         }

         if (singletonMaster != null) {
            singletonMaster.notifyShutdown(this.serverName);
         }
      } catch (LeasingException var2) {
         if (DEBUG) {
            p(var2.toString());
         }
      }

   }

   private LeaseManager findOrCreateLeasingService(String leasingServiceName) {
      LeaseManagerFactory leaseManagerFactory = (LeaseManagerFactory)GlobalServiceLocator.getServiceLocator().getService(LeaseManagerFactory.class, new Annotation[0]);
      return leaseManagerFactory.getLeaseManager(leasingServiceName);
   }

   public void notifyServerShutdown(String serverName) {
      if (this.isClusterMaster()) {
         this.clusterMaster.removeServerLocation(serverName);
      }
   }
}
