package weblogic.cluster.singleton;

import java.io.IOException;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.security.AccessController;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.cluster.ClusterExtensionLogger;
import weblogic.cluster.ClusterLogger;
import weblogic.cluster.ClusterMemberInfo;
import weblogic.cluster.ClusterService;
import weblogic.cluster.migration.DynamicLoadbalancer;
import weblogic.cluster.migration.ExactlyOnceServiceLocationSelector;
import weblogic.cluster.migration.FailureRecoveryServiceLocationSelector;
import weblogic.cluster.migration.MigrationException;
import weblogic.cluster.migration.MigrationManagerService;
import weblogic.cluster.migration.PartitionAwareObject;
import weblogic.cluster.migration.RemoteMigratableServiceCoordinator;
import weblogic.cluster.migration.ShutdownRecoveryServiceLocationSelector;
import weblogic.cluster.singleton.ConsensusLeasing.Locator;
import weblogic.cluster.singleton.MemberDeathDetector.ServerMigrationStateValidator;
import weblogic.deploy.event.DeploymentEventManager;
import weblogic.descriptor.DescriptorBean;
import weblogic.jndi.Environment;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JTAMigratableTargetMBean;
import weblogic.management.configuration.MachineMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.SingletonServiceMBean;
import weblogic.management.provider.ManagementService;
import weblogic.nodemanager.mbean.NodeManagerLifecycleService;
import weblogic.nodemanager.mbean.NodeManagerLifecycleServiceGenerator;
import weblogic.protocol.LocalServerIdentity;
import weblogic.protocol.URLManagerService;
import weblogic.rjvm.PeerGoneException;
import weblogic.rmi.extensions.PortableRemoteObject;
import weblogic.rmi.extensions.RequestTimeoutException;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.Timer;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.transaction.internal.TxDebug;
import weblogic.work.WorkManagerFactory;

public class SingletonMonitor implements NakedTimerListener, MigratableServiceConstants, SingletonMonitorRemote, ConsensusServiceGroupViewListener {
   private static final boolean DEBUG = SingletonServicesDebugLogger.isDebugEnabled();
   private final int leaseRenewInterval;
   private Timer timer;
   private boolean active = false;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private SingletonMonitorServiceTracker serviceTracker;
   private TimerManager timerManager = null;
   private LeaseManager manager;
   private ClusterMBean cluster;
   private DomainMBean domain;
   private SingletonServicesStateManager stateManager;
   ServiceMigrationRuntimeMBeanImpl runtimeMBean;
   private MemberDeathDetector memberDeathDetector;
   private DynamicMigratableGroupMonitor dynamicMGMonitor;
   protected static final Set emptySet = new HashSet();
   HashSet currentlyRunningMigrations = new HashSet();

   SingletonMonitor(LeaseManager manager, int leaseRenewInterval) {
      this.manager = manager;
      this.leaseRenewInterval = leaseRenewInterval;
      this.serviceTracker = new SingletonMonitorServiceTracker(manager);
      this.stateManager = new ReplicatedSingletonServicesStateManager("default-singleton-statemanager", manager);
   }

   private ServiceMigrationRuntimeMBeanImpl getRuntimeMBean() {
      if (this.runtimeMBean != null) {
         return this.runtimeMBean;
      } else {
         this.runtimeMBean = ServiceMigrationRuntimeMBeanImpl.getInstance();
         return this.runtimeMBean;
      }
   }

   void start() {
      if (DEBUG) {
         this.p("Starting singleton monitor");
      }

      this.domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      this.cluster = this.domain.lookupServer(LocalServerIdentity.getIdentity().getServerName()).getCluster();
      this.dynamicMGMonitor = new DynamicMigratableGroupMonitorImpl(ManagementService.getRuntimeAccess(kernelId).getServer());
      this.getRuntimeMBean();
      this.serviceTracker.initialize(this.cluster, this.domain);
      DescriptorBean db = this.domain;
      db.addBeanUpdateListener(this.serviceTracker);
      DeploymentEventManager.addDeploymentEventListener(this.serviceTracker, false);
      ClusterService.getServices().addClusterMembersListener(this.serviceTracker);
      synchronized(this) {
         this.active = true;
      }

      this.stateManager.leaseAcquired();
      if (DEBUG) {
         this.p("Scheduling monitoring service to check lease status every " + this.leaseRenewInterval + " millis.");
      }

      this.timerManager = TimerManagerFactory.getTimerManagerFactory().getTimerManager("SingletonServiceTimerManager", "weblogic.kernel.System");
      this.timer = this.timerManager.schedule(this, 0L, (long)this.leaseRenewInterval);
      if (ClusterService.getClusterServiceInternal().isMemberDeathDetectorEnabled() && !"consensus".equals(MigratableServerService.theOne().getLeasingType())) {
         ClusterExtensionLogger.logStartingMemberDeathDetector();
         this.initializeMemberDeathDetector();
      }

   }

   void stop() {
      synchronized(this) {
         this.active = false;
      }

      if (this.timer != null) {
         this.timer.cancel();
      }

      this.stateManager.lostLease();
      if (this.memberDeathDetector != null) {
         this.memberDeathDetector.stop();
      }

   }

   public void register(String name) {
      this.serviceTracker.register(name);
   }

   public void registerJTA(String name) {
      this.serviceTracker.registerJTA(name);
   }

   public void unregister(String name) {
      this.serviceTracker.unregister(name);
   }

   public String findServiceLocation(String serviceName) {
      try {
         String owner = this.manager.findOwner(serviceName);
         ServerMBean loc = null;
         if (owner != null && this.isServiceActive(serviceName)) {
            loc = this.domain.lookupServer(LeaseManager.getServerNameFromOwnerIdentity(owner));
         }

         return loc == null ? null : loc.getName();
      } catch (LeasingException var4) {
         return null;
      }
   }

   private boolean isServiceActive(String serviceName) {
      MigratableTargetMBean mtBean = this.getMigratableTarget(serviceName);
      if (mtBean != null && mtBean instanceof JTAMigratableTargetMBean) {
         return true;
      } else {
         return this.stateManager.checkServiceState(serviceName, 1) || this.stateManager.checkServiceState(serviceName, 2);
      }
   }

   private void checkFailedLeases(Collection stuffToMigrate, Set stuffToIgnore) {
      if (DEBUG) {
         this.p("Checking Failed Leases");
      }

      Map map = this.stateManager.getAllServicesState();
      Iterator itr = map.entrySet().iterator();

      while(true) {
         while(true) {
            Serializable service;
            do {
               SingletonServicesState state;
               do {
                  Map.Entry e;
                  do {
                     do {
                        if (!itr.hasNext()) {
                           return;
                        }

                        e = (Map.Entry)itr.next();
                        service = (Serializable)e.getKey();
                     } while(stuffToIgnore.contains(service.toString()));
                  } while(this.isDynamicService(service.toString()));

                  state = (SingletonServicesState)e.getValue();
               } while(state.getState() != 0);

               if (DEBUG) {
                  this.p(service + " - Detected that it is in Failed State.");
               }
            } while(stuffToMigrate.contains(service));

            if (service instanceof String && !this.startMigration((String)service, -1L)) {
               if (DEBUG) {
                  this.p(service + " - In the middle of migration. Ignore it in failed lease.");
               }
            } else {
               ClusterExtensionLogger.logFailedLeaseIsFound(service instanceof String ? (String)service : service.toString());
               stuffToMigrate.add(service);
            }
         }
      }
   }

   private void checkExpiredLeases(Collection stuffToMigrate, Set stuffToIgnore) {
      if (DEBUG) {
         this.p("Checking existant, but expired leases");
      }

      String[] badLeases = this.manager.findExpiredLeases();

      for(int i = 0; i < badLeases.length; ++i) {
         if (DEBUG) {
            this.p(badLeases[i] + " - Its an expired lease.");
         }

         SingletonDataObject service = (SingletonDataObject)this.serviceTracker.get(badLeases[i]);
         String serviceName = null;
         if (service == null) {
            SingletonService ss = SingletonServicesManagerService.getInstance().getService(badLeases[i]);
            if (ss != null) {
               serviceName = badLeases[i];
            }
         } else {
            serviceName = service.getName();
         }

         if (serviceName == null) {
            if (DEBUG) {
               this.p(badLeases[i] + " - But the expired lease corresponds to no registered singleton service, ignoring it");
            }
         } else if (!stuffToMigrate.contains(serviceName) && !stuffToIgnore.contains(serviceName) && !this.isDynamicService(serviceName)) {
            if (!this.startMigration(serviceName, -1L)) {
               if (DEBUG) {
                  this.p(serviceName + " - In the middle of migration. Not starting new one.");
               }
            } else {
               ClusterExtensionLogger.logExpiredLeaseIsFound(serviceName);
               stuffToMigrate.add(serviceName);
            }
         }
      }

   }

   private void checkRegisteredSingletons(Collection stuffToMigrate, Set stuffToIgnore) {
      if (DEBUG) {
         this.p("Checking for registered Migratable Targets and Singleton Services without a lease");
      }

      Iterator iter = this.serviceTracker.values().iterator();

      while(true) {
         SingletonDataObject dataObj;
         String singleton;
         do {
            do {
               if (!iter.hasNext()) {
                  return;
               }

               dataObj = (SingletonDataObject)iter.next();
               singleton = dataObj.getName();
            } while(stuffToIgnore.contains(singleton));
         } while(this.isDynamicService(singleton));

         try {
            if (!this.startMigration(singleton, -1L)) {
               if (DEBUG) {
                  this.p(singleton + " - In the middle of migration. Not starting new one.");
               }
            } else {
               String owner = this.manager.findOwner(singleton);
               if (owner != null) {
                  if (DEBUG) {
                     this.p(singleton + " - Found an owner - " + owner);
                  }

                  if (this.stateManager.getServiceState(singleton) == null && (dataObj.isJTA() || this.isServiceActiveOnOwner(singleton, owner))) {
                     ServerMBean srvrMBean = this.domain.lookupServer(LeaseManager.getServerNameFromOwnerIdentity(owner));
                     ServiceLocationSelector locSelector = this.getServiceLocationSelector(dataObj.getName());
                     locSelector.migrationSuccessful(srvrMBean, true);
                  }

                  this.endMigration(singleton);
               } else {
                  ClusterExtensionLogger.logNoLeaseIsFound(singleton);
                  stuffToMigrate.add(singleton);
               }
            }
         } catch (LeasingException var9) {
            this.endMigration(singleton);
            ClusterLogger.logExceptionWhileMigratingService(singleton != null ? singleton : "an unknown service", var9);
         }
      }
   }

   private boolean restartInPlace(String service, ServerMBean currentLocation, int currentState, ServiceLocationSelector locSelector) {
      if (DEBUG) {
         this.p("restartInPlace for service:" + service + ",currentLocation:" + currentLocation + ",currentState:" + currentState + ",locSelector=" + locSelector);
      }

      MigratableTargetMBean target = this.getMigratableTarget(service);
      if (target == null) {
         return false;
      } else if (!target.getRestartOnFailure()) {
         return false;
      } else if (currentState == -1 || currentState != 0 && currentState != 1 && currentState != 2) {
         return false;
      } else if (currentLocation == null) {
         return false;
      } else {
         try {
            String owner = this.manager.findOwner(service);
            if (owner != null) {
               if (DEBUG) {
                  this.p("restartInPlace is not required because service:" + service + " has owner:" + owner);
               }

               return false;
            }
         } catch (LeasingException var20) {
            if (DEBUG) {
               this.p("Ignore an exception while restarting in place.", var20);
            }
         }

         int restartAttempts = this.getNumberOfRestartAttempts(target);

         for(int i = 0; i < restartAttempts; ++i) {
            if (i >= 1) {
               try {
                  Thread.sleep((long)(target.getSecondsBetweenRestarts() * 1000));
               } catch (InterruptedException var16) {
               }
            }

            RemoteSingletonServicesControl rssc = this.getRemoteSingletonServicesControl(currentLocation.getName());
            if (rssc == null) {
               return false;
            }

            if (this.getRuntimeMBean() != null) {
               this.getRuntimeMBean().migrationStarted(service, currentLocation.getName(), currentLocation.getName());
            }

            try {
               ClusterExtensionLogger.logRestartInPlaceStarted(service, currentLocation.getName());
               if (DEBUG) {
                  this.p("Trying to re-start service " + service + " " + (i + 1) + "/" + restartAttempts + " on " + currentLocation);
               }

               rssc.restartService(service);
               locSelector.migrationSuccessful(currentLocation, true);
               boolean var9 = true;
               return var9;
            } catch (MigrationException var17) {
               if (DEBUG) {
                  this.p("Trying to re-start failed because of MigrationException", var17);
                  var17.printStackTrace();
               }
            } catch (RemoteException var18) {
               if (DEBUG) {
                  this.p("Trying to re-start failed because of RemoteException", var18);
                  var18.printStackTrace();
               }
            } finally {
               if (this.getRuntimeMBean() != null) {
                  this.getRuntimeMBean().migrationCompleted(service, currentLocation.getName(), currentLocation.getName());
               }

            }
         }

         return false;
      }
   }

   public void timerExpired(Timer timer) {
      this.dumpAllServices();
      if (this.isActive()) {
         if (DEBUG) {
            this.p("Now checking lease statuses.");
         }

         Set allDynamicServices = this.processAllDynamicServices();
         if (!this.isActive()) {
            if (DEBUG) {
               this.p("skip run since SingletonMonitor is not active");
            }

         } else {
            HashSet stuffToMigrate = new HashSet();
            this.checkRegisteredSingletons(stuffToMigrate, allDynamicServices);
            this.checkExpiredLeases(stuffToMigrate, allDynamicServices);
            this.checkFailedLeases(stuffToMigrate, allDynamicServices);
            Iterator i = stuffToMigrate.iterator();

            while(true) {
               while(i.hasNext()) {
                  final String service = (String)i.next();
                  if (!this.stateManager.checkServiceState(service, 4) && !this.isManualMigrationPolicyConfigured(service)) {
                     ClusterExtensionLogger.logStartAutoMigration(service);
                     WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
                        public void run() {
                           try {
                              if (SingletonMonitor.this.isActive()) {
                                 Object obj = SingletonMonitor.this.serviceTracker.get(service);
                                 Iterator itr;
                                 ServerMBean source;
                                 if (obj != null) {
                                    SingletonDataObject dataObj = (SingletonDataObject)obj;
                                    if (dataObj.isAppScopedSingleton()) {
                                       itr = dataObj.getApplicationScopedTargets().iterator();
                                       boolean appInactive = true;

                                       while(true) {
                                          if (itr.hasNext()) {
                                             source = (ServerMBean)itr.next();

                                             try {
                                                RemoteSingletonServicesControl ctrl = SingletonMonitor.this.getRemoteSingletonServicesControl(source.getName());
                                                if (ctrl == null) {
                                                   continue;
                                                }

                                                if (ctrl.isServiceRegistered(service)) {
                                                   appInactive = false;
                                                }
                                             } catch (Exception var26) {
                                                continue;
                                             }
                                          }

                                          if (appInactive) {
                                             if (SingletonMonitor.DEBUG) {
                                                SingletonMonitor.this.p(service + " - belongs to app " + dataObj.getAppName() + " which is not active. Skipping its migration");
                                             }

                                             return;
                                          }
                                          break;
                                       }
                                    }
                                 }

                                 int numberOfAttempts = 0;
                                 itr = null;
                                 ServiceLocationSelector locSelector = SingletonMonitor.this.getServiceLocationSelector(service);
                                 source = null;
                                 String serverLoc = SingletonMonitor.this.findServiceLocation(service);
                                 if (serverLoc != null) {
                                    source = SingletonMonitor.this.domain.lookupServer(serverLoc);
                                 } else {
                                    try {
                                       source = SingletonMonitor.this.findPreviousServer(service);
                                    } catch (LeasingException var23) {
                                    }
                                 }

                                 ServerMBean lastSource = null;
                                 int lastState = -1;
                                 SingletonServicesState state = SingletonMonitor.this.stateManager.getServiceState(service);
                                 if (state != null) {
                                    lastState = state.getState();
                                    if (lastState == 0) {
                                       lastSource = SingletonMonitor.this.domain.lookupServer((String)state.getStateData());
                                    }
                                 }

                                 if (lastSource == null) {
                                    try {
                                       lastSource = SingletonMonitor.this.findPreviousServer(service);
                                    } catch (LeasingException var22) {
                                    }
                                 }

                                 List serverList = null;
                                 serverList = SingletonMonitor.this.getAllCandidateServers(service);
                                 if (serverList == null) {
                                    serverList = SingletonMonitor.this.serviceTracker.getServerList(service);
                                 }

                                 locSelector.setServerList(serverList);
                                 if (lastSource != null) {
                                    locSelector.setLastHost(lastSource);
                                 }

                                 if (SingletonMonitor.DEBUG) {
                                    String stringifiedState = lastState >= 0 ? SingletonServicesStateManager.STRINGIFIED_STATE[lastState] : null;
                                    SingletonMonitor.this.p(service + " - LastState: " + stringifiedState + " LastLocation: " + lastSource);
                                 }

                                 if (SingletonMonitor.this.restartInPlace(service, lastSource, lastState, locSelector)) {
                                    ClusterExtensionLogger.logRestartInPlaceCompleted(service);
                                    return;
                                 }

                                 do {
                                    if (!SingletonMonitor.this.isActive()) {
                                       return;
                                    }

                                    ServerMBean target = locSelector.chooseServer();
                                    if (target == null) {
                                       return;
                                    }

                                    ClusterExtensionLogger.logAutoMigrationStarted(service, source != null ? source.getName() : "null", target.getName());
                                    boolean result = false;

                                    try {
                                       result = SingletonMonitor.this.migrate(service, source, target, true);
                                    } catch (Exception var25) {
                                       if (SingletonMonitor.DEBUG) {
                                          SingletonMonitor.this.p(service + " - Exception while migrating service it  from " + source + " to " + target + " : " + var25);
                                       }
                                    }

                                    if (result) {
                                       locSelector.migrationSuccessful(target, true);
                                       ClusterExtensionLogger.logAutoMigrationCompleted(service, target.getName());
                                       return;
                                    }

                                    try {
                                       String[] badLeases = SingletonMonitor.this.manager.findExpiredLeases();
                                       List list = Arrays.asList(badLeases);
                                       if (!list.contains(service)) {
                                          String owner = SingletonMonitor.this.manager.findOwner(service);
                                          if (owner != null) {
                                             if (SingletonMonitor.DEBUG) {
                                                SingletonMonitor.this.p(service + " - Its no more a bad lease. It also has a owner " + owner + ".No need to migrate it");
                                             }

                                             return;
                                          }
                                       }
                                    } catch (LeasingException var24) {
                                    }

                                    ++numberOfAttempts;
                                 } while(numberOfAttempts != serverList.size());

                                 ClusterLogger.logNoSuitableServerFoundForSingletonService(service);
                                 return;
                              }
                           } finally {
                              SingletonMonitor.this.endMigration(service);
                           }

                        }
                     });
                  } else {
                     if (DEBUG) {
                        this.p(service + " - needs to be manually migrated. Cannot auto-migrate");
                     }

                     this.endMigration(service);
                  }
               }

               this.stateManager.syncState();
               return;
            }
         }
      }
   }

   private boolean isManualMigrationPolicyConfigured(String service) {
      boolean isManualMigrationPolicyConfigured = false;
      MigratableTargetMBean bean = this.getMigratableTarget(service);
      if (bean != null && bean.getMigrationPolicy().equals("manual")) {
         isManualMigrationPolicyConfigured = true;
      }

      return isManualMigrationPolicyConfigured;
   }

   private Set processAllDynamicServices() {
      Set allDynamicServices = emptySet;
      if (MigrationManagerService.getState() == 2) {
         allDynamicServices = this.dynamicMGMonitor.processAllDynamicServices(MigrationManagerService.singleton().getAllAutomaticDynamicServices());
      }

      return allDynamicServices;
   }

   private boolean isDynamicService(String service) {
      if (MigrationManagerService.getState() == 2) {
         return MigrationManagerService.singleton().isDynamicService(service);
      } else {
         MigratableTargetMBean mtMBean = this.domain.lookupMigratableTarget(service);
         return MigrationManagerService.isDynamicService(mtMBean);
      }
   }

   private void dumpAllServices() {
      if (DEBUG) {
         Map allServices = this.serviceTracker.getAll();
         this.p("SingletonMonitor.timerExpired: all known services: " + allServices);
         Map allStates = this.stateManager.getAllServicesState();
         this.p("SingletonMonitor.timerExpired: all known states: " + allStates);
         this.p("SingletonMonitor.timerExpired: all currently Running Migrations: " + this.getAllCurrentlyRunningMigrations());
         Set servicesWithoutState = new HashSet();
         Iterator var4 = allServices.keySet().iterator();

         while(var4.hasNext()) {
            Object key = var4.next();
            if (!allStates.containsKey(key)) {
               servicesWithoutState.add(key);
            }
         }

         this.p("SingletonMonitor.timerExpired: all services without states: " + servicesWithoutState);
         Set unknownServicesButHaveState = new HashSet();
         Iterator var8 = allStates.keySet().iterator();

         while(var8.hasNext()) {
            Object key = var8.next();
            if (!allServices.containsKey(key)) {
               unknownServicesButHaveState.add(key);
            }
         }

         this.p("SingletonMonitor.timerExpired: all services that has state but not in serviceTracker: " + unknownServicesButHaveState);
      }
   }

   private synchronized boolean isActive() {
      return !ManagementService.getRuntimeAccess(kernelId).getServerRuntime().isShuttingDown() && this.active;
   }

   public boolean migrate(String service, String destination, boolean sourceUp, boolean destUp) throws RemoteException {
      return this.migrate(service, destination);
   }

   public boolean migrateJTA(String service, String destination, boolean sourceUp, boolean destUp) throws RemoteException {
      return this.migrate(service, destination);
   }

   public boolean migrate(String ss, String target) throws RemoteException {
      ServerMBean sourceServer = null;

      try {
         String source = this.findServiceLocation(ss);
         if (source != null) {
            sourceServer = this.domain.lookupServer(source);
         }
      } catch (Exception var11) {
         throw new RemoteException("Unavailable to migrate, current location could not be determined because of an error: " + var11);
      }

      ServerMBean destServer = this.domain.lookupServer(target);
      if (destServer == null) {
         throw new RemoteException("No server named " + target + " found.");
      } else {
         boolean migrationStarted = false;

         boolean var14;
         try {
            if (!this.startMigration(ss, (long)(3 * this.leaseRenewInterval))) {
               if (DEBUG) {
                  this.p("Timed out while waiting for auto-migration of " + ss + " to complete");
               }

               throw new RemoteException("Timed out while waiting for auto-migration of " + ss + " to complete");
            }

            migrationStarted = true;
            if (DEBUG) {
               this.p(ss + " - Manually migrating from " + sourceServer + " to " + destServer);
            }

            boolean result = this.migrate(ss, sourceServer, destServer, false);
            if (result) {
               ServiceLocationSelector locSelector = this.getServiceLocationSelector(ss);
               locSelector.migrationSuccessful(destServer, false);
            }

            var14 = result;
         } finally {
            if (migrationStarted) {
               this.endMigration(ss);
            }

         }

         return var14;
      }
   }

   private SingletonServiceMBean getSingletonServiceMBean(String name) {
      return this.domain.lookupSingletonService(name);
   }

   private MigratableTargetMBean getMigratableTarget(String name) {
      MigratableTargetMBean bean = null;
      bean = this.domain.lookupMigratableTarget(name);
      if (bean != null) {
         return bean;
      } else {
         ServerMBean[] servers = this.domain.getServers();

         for(int i = 0; i < servers.length; ++i) {
            MigratableTargetMBean bean = servers[i].getJTAMigratableTarget();
            if (bean != null && bean.getName().equals(name)) {
               return bean;
            }
         }

         return null;
      }
   }

   public void deactivateJTA(String name, String hostServer) throws RemoteException {
      try {
         if (!this.startMigration(name, (long)(3 * this.leaseRenewInterval))) {
            if (DEBUG) {
               this.p(name + " - Timed out while waiting for JTA MT auto-migration to complete");
            }

            throw new RemoteException("Timed out while waiting for JTA MT auto-migration of " + name + " to complete");
         }

         if (DEBUG) {
            this.p(name + " - Going to deactivate JTA MT with host " + hostServer);
         }

         String currentLocation = this.findServiceLocation(name);
         if (currentLocation == null || currentLocation.equals(hostServer)) {
            if (DEBUG) {
               this.p(name + " - Current location of JTA MT = " + currentLocation + ".No need to deactivate");
            }

            this.stateManager.removeServiceState(name);
            return;
         }

         RemoteSingletonServicesControl rssc = this.getRemoteSingletonServicesControl(currentLocation);
         if (rssc != null) {
            if (DEBUG) {
               this.p(name + " - Going to deactivate JTA MT on " + currentLocation);
            }

            rssc.deactivateService(name);
            this.stateManager.removeServiceState(name);
         } else {
            try {
               String owner = this.manager.findOwner(name);
               if (owner != null) {
                  throw new RemoteException("Could not deactivate JTA service " + name + ", " + currentLocation + " could not be reached.");
               }
            } catch (LeasingException var9) {
            }
         }
      } finally {
         this.endMigration(name);
      }

   }

   private boolean migrate(String ss, ServerMBean source, ServerMBean target, boolean autoMigrate) {
      return this.migrate(ss, source != null ? source.getName() : null, target != null ? target.getName() : null, autoMigrate, true);
   }

   private boolean migrate(String ss, String source, String target, boolean autoMigrate, boolean ignoreUnknownService) {
      if (ss == null && ignoreUnknownService) {
         return true;
      } else if (this.serviceTracker.get(ss) == null && SingletonServicesManagerService.getInstance().getService(ss) == null && ignoreUnknownService) {
         if (DEBUG) {
            this.p(ss + " - No such service is registered, despite there being a lease for it. Ignoring for now.");
         }

         return true;
      } else {
         RemoteSingletonServicesControl rssc = null;
         RemoteSingletonServicesControl drssc = null;
         String migrationStartedSource = source != null ? source : "inactive";
         String migrationStartedTarget = target != null ? target : "no target";

         boolean var10;
         try {
            if (this.getRuntimeMBean() != null) {
               this.getRuntimeMBean().migrationStarted(ss, migrationStartedSource, migrationStartedTarget);
            }

            if (source != null) {
               rssc = this.getRemoteSingletonServicesControl(source);
            }

            drssc = this.getRemoteSingletonServicesControl(target);
            if (drssc == null) {
               String reason = ss + " - Error talking to destination server - " + target + " . Can't migrate.";
               if (DEBUG) {
                  this.p(reason);
               }

               throw new MigrationException(reason);
            }

            if (rssc == null) {
               if (DEBUG) {
                  this.p(ss + " - Error talking to source server - " + source + ", it may have crashed. Continuing with its migration to " + target);
               }
            } else {
               if (this.getMigratableTarget(ss) instanceof JTAMigratableTargetMBean && source != null && source.equals(ss)) {
                  ClusterLogger.logAttemptedJTAMigrationFromLivingServer(source);
                  var10 = true;
                  return var10;
               }

               try {
                  if (DEBUG) {
                     this.p(ss + " - Attempting to deactivate it on the source server - " + source);
                  }

                  rssc.deactivateService(ss);
               } catch (PeerGoneException var16) {
                  if (DEBUG) {
                     this.p(ss + " - Error talking to source server - " + source + " . It may have crashed. Continuing with the migration.");
                  }
               }
            }

            if (DEBUG) {
               this.p(ss + " - Attempting to activate the service on the target server - " + target);
            }

            try {
               drssc.activateService(ss);
            } catch (RequestTimeoutException var17) {
               if (DEBUG) {
                  this.p("Timeout may be occur while migrating. Then do activateService again.", var17);
               }

               drssc.activateService(ss);
            }

            if (DEBUG) {
               this.p(ss + " - Service succesfully activated on remote server - " + target);
            }

            var10 = true;
         } catch (RemoteException var18) {
            if (DEBUG) {
               this.p("Error communicating to remote server, or no named service registered", var18);
            }

            throw new MigrationException("Exception - " + var18);
         } finally {
            if (this.getRuntimeMBean() != null) {
               this.getRuntimeMBean().migrationCompleted(ss, migrationStartedSource, migrationStartedTarget);
            }

         }

         return var10;
      }
   }

   private static URLManagerService getURLManagerService() {
      return (URLManagerService)GlobalServiceLocator.getServiceLocator().getService(URLManagerService.class, new Annotation[0]);
   }

   private RemoteMigratableServiceCoordinator getRemoteMigratableServiceCoordinator() {
      String url;
      try {
         url = getURLManagerService().findAdministrationURL(this.domain.getAdminServerName());
         if (DEBUG) {
            this.p("Admin URL for looking up RemoteMigratableServiceCoordinator:" + url);
         }
      } catch (UnknownHostException var6) {
         return null;
      }

      Environment env = new Environment();
      env.setProviderUrl(url);

      try {
         Context ctx = env.getInitialContext();
         return (RemoteMigratableServiceCoordinator)ctx.lookup("weblogic.cluster.migration.migratableServiceCoordinator");
      } catch (NamingException var5) {
         if (TxDebug.JTAMigration.isDebugEnabled()) {
            TxDebug.JTAMigration.debug("Unexpected exception while getting RemoteMigratableServiceCoordinator", var5);
         }

         return null;
      }
   }

   private ServerMBean findPreviousServer(String serviceName) throws LeasingException {
      if (DEBUG) {
         this.p(serviceName + " - Finding its previous location");
      }

      String owner = this.manager.findPreviousOwner(serviceName);
      if (owner == null) {
         if (DEBUG) {
            this.p(serviceName + " - Couldn't find its current or previous location");
         }

         return null;
      } else {
         if (DEBUG) {
            this.p(serviceName + " - Its previous location is: " + LeaseManager.getServerNameFromOwnerIdentity(owner));
         }

         return this.domain.lookupServer(LeaseManager.getServerNameFromOwnerIdentity(owner));
      }
   }

   private boolean isServerRunning(String serverName) {
      try {
         if (DEBUG) {
            this.p("Checking if " + serverName + " is running.");
         }

         ClusterMasterRemote cmr = MigratableServerService.theOne().getClusterMasterRemote();
         if (cmr == null) {
            return true;
         }

         String machineName = cmr.getServerLocation(serverName);
         if (machineName == null) {
            return false;
         }

         DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
         MachineMBean machine = domain.lookupMachine(machineName);
         ServerMBean serverMBean = domain.lookupServer(serverName);
         NodeManagerLifecycleServiceGenerator generator = (NodeManagerLifecycleServiceGenerator)GlobalServiceLocator.getServiceLocator().getService(NodeManagerLifecycleServiceGenerator.class, new Annotation[0]);
         NodeManagerLifecycleService nmr = generator.getInstance(machine);
         String state = nmr.getState(serverMBean);
         if (DEBUG) {
            this.p("Current state of " + serverName + " is: " + state);
         }

         if (state != null && !state.equals("STARTING") && !state.equals("RUNNING") && !state.equals("STANDBY") && !state.equals("ADMIN") && !state.equals("RESUMING") && !state.equals("UNKNOWN")) {
            if (DEBUG) {
               this.p("We consider that state NON-RUNNING.");
            }

            return false;
         }
      } catch (IOException var10) {
         if (DEBUG) {
            this.p("Error while talking to NM, considering server down.", var10);
         }

         return false;
      } catch (LeasingException var11) {
         if (DEBUG) {
            this.p("Error while talking to remote ClusterMaster, considering server down.", var11);
         }

         return false;
      }

      if (DEBUG) {
         this.p("We consider that state RUNNING.");
      }

      return true;
   }

   private RemoteSingletonServicesControl getRemoteSingletonServicesControl(String server) {
      Environment env = new Environment();
      Context ctx = null;
      String url = null;
      if (!this.isServerRunning(server)) {
         return null;
      } else {
         try {
            url = getURLManagerService().findAdministrationURL(server);
         } catch (UnknownHostException var18) {
            url = MigratableServerService.findURLOfUnconnectedServer(server);
         }

         Object var5;
         try {
            RemoteSingletonServicesControl var6;
            try {
               if (DEBUG) {
                  this.p("Contacting " + server + " at " + url + " to perform migration tasks.");
               }

               if (url != null) {
                  env.setProviderUrl(url);
                  env.setConnectionTimeout(10000L);
                  int requestResponseTimeout = this.cluster.getServiceActivationRequestResponseTimeout();
                  if (requestResponseTimeout > 0) {
                     env.setResponseReadTimeout((long)requestResponseTimeout);
                  }

                  var6 = (RemoteSingletonServicesControl)PortableRemoteObject.narrow(env.getInitialReference(SingletonServicesManagerService.class), RemoteSingletonServicesControl.class);
                  return var6;
               }

               var5 = null;
            } catch (NamingException var19) {
               if (DEBUG) {
                  this.p("Could not find RemoteSingletonServicesControl on " + server, var19);
               }

               var6 = null;
               return var6;
            }
         } finally {
            if (ctx != null) {
               try {
                  ((Context)ctx).close();
               } catch (NamingException var17) {
               }
            }

         }

         return (RemoteSingletonServicesControl)var5;
      }
   }

   private List getAllCandidateServers(String service) {
      List serverList = null;
      ServerMBean[] srvrMBeans = null;
      MigratableTargetMBean mtBean = this.getMigratableTarget(service);
      if (mtBean != null) {
         srvrMBeans = mtBean.getAllCandidateServers();
      } else {
         SingletonServiceMBean singletonMBean = this.getSingletonServiceMBean(service);
         if (singletonMBean != null) {
            srvrMBeans = singletonMBean.getAllCandidateServers();
         }
      }

      if (srvrMBeans != null && srvrMBeans.length > 0) {
         serverList = Arrays.asList(srvrMBeans);
      }

      return serverList;
   }

   private ServerMBean getUserPreferedServer(String service) {
      ServerMBean ups = null;
      MigratableTargetMBean mtBean = this.getMigratableTarget(service);
      if (mtBean != null) {
         ups = mtBean.getUserPreferredServer();
      }

      return ups;
   }

   private void p(Object o) {
      SingletonServicesDebugLogger.debug("SingletonMonitor: " + o);
   }

   private void p(Object o, Exception e) {
      SingletonServicesDebugLogger.debug("SingletonMonitor: " + o, e);
   }

   public ServiceLocationSelector getServiceLocationSelector(String service) {
      MigratableTargetMBean bean = this.getMigratableTarget(service);
      if (bean != null) {
         String policy = bean.getMigrationPolicy();
         if (policy.equals("exactly-once")) {
            return new ExactlyOnceServiceLocationSelector(bean, this.stateManager);
         }

         if (policy.equals("failure-recovery")) {
            return new FailureRecoveryServiceLocationSelector(bean, this.stateManager);
         }

         if (policy.equals("shutdown-recovery")) {
            return new ShutdownRecoveryServiceLocationSelector(bean, this.stateManager);
         }
      }

      BasicServiceLocationSelector locSelector = new BasicServiceLocationSelector(service, this.stateManager);
      SingletonServiceMBean singletonMBean = this.getSingletonServiceMBean(service);
      if (singletonMBean != null) {
         locSelector.setUPS(singletonMBean.getUserPreferredServer());
      }

      return locSelector;
   }

   public SingletonServicesStateManager getSingletonServicesStateManager() {
      return this.stateManager;
   }

   public void memberAdded(String server) {
   }

   public void memberRemoved(String server) {
      Iterator iter = this.serviceTracker.values().iterator();

      while(iter.hasNext()) {
         SingletonDataObject dataObj = (SingletonDataObject)iter.next();
         String singleton = dataObj.getName();

         try {
            String owner = this.manager.findOwner(singleton);
            if (owner != null) {
               String ownerName = LeaseManager.getServerNameFromOwnerIdentity(owner);
               if (ownerName.equals(server)) {
                  String srvrState = Locator.locate().getServerState(server);
                  if (ServerMigrationStateValidator.canMigrateLease(srvrState)) {
                     if (DEBUG) {
                        this.p(server + " is marked as " + srvrState + ". Voiding all its leases");
                     }

                     this.manager.voidLeases(owner);
                     break;
                  }
               }
            }
         } catch (LeasingException var8) {
         }
      }

   }

   private boolean startMigration(String mtName, long waitTime) {
      synchronized(this.currentlyRunningMigrations) {
         long currTime = System.currentTimeMillis();

         while(this.currentlyRunningMigrations.contains(mtName)) {
            if (waitTime < 0L || currTime + waitTime < System.currentTimeMillis()) {
               return false;
            }

            try {
               if (DEBUG) {
                  this.p("Going to wait for " + waitTime + " ms. to get lock for " + mtName);
               }

               this.currentlyRunningMigrations.wait(waitTime);
            } catch (Exception var9) {
            }
         }

         this.currentlyRunningMigrations.add(mtName);
         return true;
      }
   }

   private boolean endMigration(String mtName) {
      synchronized(this.currentlyRunningMigrations) {
         this.currentlyRunningMigrations.notify();
         return this.currentlyRunningMigrations.remove(mtName);
      }
   }

   private HashSet getAllCurrentlyRunningMigrations() {
      synchronized(this.currentlyRunningMigrations) {
         HashSet set = new HashSet(this.currentlyRunningMigrations.size());
         set.addAll(this.currentlyRunningMigrations);
         return set;
      }
   }

   private void initializeMemberDeathDetector() {
      this.memberDeathDetector = (MemberDeathDetector)GlobalServiceLocator.getServiceLocator().getService(MemberDeathDetector.class, new Annotation[0]);
      this.memberDeathDetector.start();
   }

   public void notifyShutdown(String server) {
      if (this.memberDeathDetector != null) {
         this.memberDeathDetector.removeMember(server);
      }

   }

   private boolean isServiceActiveOnOwner(String singleton, String owner) {
      try {
         RemoteSingletonServicesControl remotessc = this.getRemoteSingletonServicesControl(LeaseManager.getServerNameFromOwnerIdentity(owner));
         return remotessc == null ? false : remotessc.isServiceActive(singleton);
      } catch (Exception var4) {
         return false;
      }
   }

   private int getNumberOfRestartAttempts(MigratableTargetMBean target) {
      int numberOfRestartAttempts = target.getNumberOfRestartAttempts();
      return numberOfRestartAttempts != -1 ? numberOfRestartAttempts : Integer.MAX_VALUE;
   }

   public void manualMigrateDynamicService(String partitionName, String migratableGroupConfigName, String destination) throws RemoteException, IllegalArgumentException {
      this.dynamicMGMonitor.manualMigrate(partitionName, migratableGroupConfigName, destination);
   }

   public DynamicLoadbalancer.ServiceStatus getServiceStatus(String partitionName, String migratableGroupConfigName) throws RemoteException, IllegalArgumentException {
      return this.dynamicMGMonitor.getServiceStatus(partitionName, migratableGroupConfigName);
   }

   private final class DynamicMigratableGroupMonitorImpl extends DynamicMigratableGroupMonitor {
      protected DynamicMigratableGroupMonitorImpl(ServerMBean server) {
         super(server);
      }

      protected String findServiceLocation(String serviceName) {
         return SingletonMonitor.this.findServiceLocation(serviceName);
      }

      protected SingletonServicesState findServiceState(String service) {
         return SingletonMonitor.this.stateManager.getServiceState(service);
      }

      protected void storeServiceState(String service, SingletonServicesState state) {
         SingletonMonitor.this.stateManager.storeServiceState(service, state);
      }

      protected Map getRuningServersInCluster() {
         Map runningServers = new HashMap();
         ClusterMemberInfo member = ClusterService.getServices().getLocalMember();
         runningServers.put(member.serverName(), member.identity().toString());
         Collection servers = ClusterService.getServices().getRemoteMembers();
         Iterator var4 = servers.iterator();

         while(var4.hasNext()) {
            Object s = var4.next();
            member = (ClusterMemberInfo)s;
            runningServers.put(member.serverName(), member.identity().toString());
         }

         return runningServers;
      }

      protected boolean deactivate(String partitionName, String configName, String source) {
         String targetName = PartitionAwareObject.qualifyPartitionInfo(partitionName, configName);
         RemoteSingletonServicesControl rssc = null;

         boolean var6;
         try {
            if (SingletonMonitor.this.getRuntimeMBean() != null) {
               SingletonMonitor.this.getRuntimeMBean().migrationStarted(targetName, source, "no target");
            }

            rssc = SingletonMonitor.this.getRemoteSingletonServicesControl(source);
            if (rssc != null) {
               try {
                  if (isDebug()) {
                     p(targetName + " - Attempting to deactivate it on the source server - " + source);
                  }

                  rssc.deactivateService(targetName);
               } catch (PeerGoneException var11) {
                  if (isDebug()) {
                     p(targetName + " - Error talking to source server - " + source + " . It may have crashed. Continuing with the migration.");
                  }
               }

               ClusterExtensionLogger.logAutoMigrationCompleted(targetName, "null");
               var6 = true;
               return var6;
            }

            var6 = false;
         } catch (RemoteException var12) {
            if (isDebug()) {
               p("Error communicating to remote server, or no named service registered", var12);
            }

            throw new MigrationException("Exception - " + var12);
         } finally {
            if (SingletonMonitor.this.getRuntimeMBean() != null) {
               SingletonMonitor.this.getRuntimeMBean().migrationCompleted(targetName, source, "no target");
            }

         }

         return var6;
      }

      protected boolean migrate(String partitionName, String configName, String source, String target) {
         String targetName = PartitionAwareObject.qualifyPartitionInfo(partitionName, configName);
         boolean migrationStarted = false;

         boolean result;
         try {
            if (SingletonMonitor.this.startMigration(targetName, -1L)) {
               migrationStarted = true;
               result = SingletonMonitor.this.migrate(targetName, source, target, true, false);
               if (result) {
                  ClusterExtensionLogger.logAutoMigrationCompleted(targetName, target != null ? target : "null");
               }

               boolean var8 = result;
               return var8;
            }

            if (isDebug()) {
               p("Dynamic Service " + configName + PartitionAwareObject.getMessageInPartition(partitionName) + " - In the middle of migration. Ignore it.");
            }

            result = false;
         } finally {
            if (migrationStarted) {
               SingletonMonitor.this.endMigration(targetName);
            }

         }

         return result;
      }

      protected void schedule(Runnable r) {
         if (this.isActive()) {
            WorkManagerFactory.getInstance().getSystem().schedule(r);
         } else if (isDebug()) {
            p("DynamicMigratableGroupMonitorImpl: skip schedule Runnable due to system is not active now");
         }

      }

      protected boolean isActive() {
         return SingletonMonitor.this.isActive();
      }

      public void manualMigrate(String partitionName, String migratableGroupConfigName, String destination) throws RemoteException, IllegalArgumentException {
         if (SingletonMonitor.DEBUG) {
            p("manualMigrate for automatic dynamic service begin: migrate " + migratableGroupConfigName + PartitionAwareObject.getMessageInPartition(partitionName) + " to " + destination);
         }

         this.validateAutomaticDynamicService(partitionName, migratableGroupConfigName, "manualMigrate for automatic dynamic service");
         MigrationManagerService.singleton().validatePartition(partitionName, migratableGroupConfigName, "manually migrate");
         String targetName = PartitionAwareObject.qualifyPartitionInfo(partitionName, migratableGroupConfigName);
         String source = null;

         try {
            source = this.findServiceLocation(targetName);
         } catch (Exception var10) {
            throw new RemoteException("Unavailable to migrate, current location of " + migratableGroupConfigName + PartitionAwareObject.getMessageInPartition(partitionName) + " could not be determined because of an error: " + var10);
         }

         if (SingletonMonitor.this.domain.lookupServer(destination) == null) {
            throw new IllegalArgumentException("Destination server " + destination + " is not defined in domain.");
         } else if (!this.cluster.getServerNames().contains(destination)) {
            throw new IllegalArgumentException("Destination server " + destination + " does NOT belong to local cluster " + this.cluster + ".");
         } else if (!SingletonMonitor.this.startMigration(targetName, (long)(3 * SingletonMonitor.this.leaseRenewInterval))) {
            String msg = "Timed out while waiting for previous migration of " + migratableGroupConfigName + PartitionAwareObject.getMessageInPartition(partitionName) + " to complete";
            if (SingletonMonitor.DEBUG) {
               p(msg);
            }

            throw new RemoteException(msg);
         } else {
            try {
               if (SingletonMonitor.DEBUG) {
                  p(migratableGroupConfigName + PartitionAwareObject.getMessageInPartition(partitionName) + " - Manually migrating from " + source + " to " + destination);
               }

               SingletonMonitor.this.migrate(targetName, source, destination, false, false);
               if (SingletonMonitor.DEBUG) {
                  p("manualMigrate for automatic dynamic service end: migrate " + migratableGroupConfigName + PartitionAwareObject.getMessageInPartition(partitionName) + " to " + destination);
               }
            } finally {
               SingletonMonitor.this.endMigration(targetName);
            }

         }
      }

      private void validateAutomaticDynamicService(String partitionName, String migratableGroupConfigName, String hint) throws IllegalArgumentException {
         String targetName = PartitionAwareObject.qualifyPartitionInfo(partitionName, migratableGroupConfigName);
         MigratableTargetMBean mtMBean = SingletonMonitor.this.getMigratableTarget(targetName);
         String msg;
         if (mtMBean == null) {
            msg = "MigratableGroupConfig " + migratableGroupConfigName + PartitionAwareObject.getMessageInPartition(partitionName) + " does not registered on SingletonMonitor server " + this.server.getName() + ".";
            if (dynLogger.isDebugEnabled()) {
               p(hint + ": error: " + msg);
            }

            throw new IllegalArgumentException(msg);
         } else if (!mtMBean.isDynamicallyCreated()) {
            msg = "Service " + migratableGroupConfigName + PartitionAwareObject.getMessageInPartition(partitionName) + " must be registered as dynamic service and must not be statistically configured MigratableTargetMBean on SingletonMonitor server " + this.server.getName() + ".";
            if (dynLogger.isDebugEnabled()) {
               p(hint + ": error: " + msg);
            }

            throw new IllegalArgumentException(msg);
         } else if ("manual".equals(mtMBean.getMigrationPolicy())) {
            msg = "MigratableGroupConfig " + migratableGroupConfigName + PartitionAwareObject.getMessageInPartition(partitionName) + " must be automatic service on SingletonMonitor server " + this.server.getName() + ".";
            if (dynLogger.isDebugEnabled()) {
               p(hint + ": error: " + msg);
            }

            throw new IllegalArgumentException(msg);
         }
      }

      protected DynamicLoadbalancer.ServiceStatus getServiceStatus(String partitionName, String migratableGroupConfigName) throws RemoteException, IllegalArgumentException {
         this.validateAutomaticDynamicService(partitionName, migratableGroupConfigName, "getServiceStatus for automatic dynamic service");
         String targetName = PartitionAwareObject.qualifyPartitionInfo(partitionName, migratableGroupConfigName);
         return this.getCurrentServiceStatus(targetName);
      }
   }
}
