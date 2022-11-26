package weblogic.transaction.internal;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import javax.inject.Named;
import javax.transaction.SystemException;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.cluster.migration.JTAMigrationHandler;
import weblogic.cluster.migration.Migratable;
import weblogic.cluster.migration.MigrationException;
import weblogic.cluster.migration.MigrationManager;
import weblogic.cluster.singleton.ClusterReformationInProgressException;
import weblogic.management.ManagementException;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.JTARecoveryRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.server.AbstractServerService;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.ServiceFailureException;
import weblogic.transaction.PeerExchangeTransaction;
import weblogic.work.WorkAdapter;
import weblogic.work.WorkManagerFactory;

@Service
@Named
@RunLevel(15)
public final class TransactionRecoveryService extends AbstractServerService implements Migratable {
   private String serverName;
   private String siteName;
   private volatile boolean active;
   private volatile boolean failBackInProgress;
   private volatile boolean passive;
   private JTARecoveryRuntimeMBeanImpl runtimeMBean;
   private MigratableTargetMBean mtConfig;
   private Set subordinatesPendingCheckStatus;
   private volatile boolean delayedAutoFailback;
   private Set checkpointedServers;
   private static final HashMap mServices = new HashMap(3);
   private static volatile boolean ownRecoveryService = false;
   private static final Object startLock = new Object();
   private static final Object suspendLock = new Object();
   private static volatile boolean started;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   static final int TRS_MIGRATABLE_ORDER = -900;
   private static final int DEFAULT_TRS_ACTIVATION_RETRY_WAIT_MILLIS = 5000;
   private static int trsActivationRetryWaitMillis = Integer.getInteger("weblogic.transaction.TRSActivationRetryWaitMillis", 5000);
   private static int syspropTRSActivationRetryPeriods = Integer.getInteger("weblogic.transaction.TRSActivationRetryPeriods", -1);
   private static final boolean m_isOwnershipDisable2pc = Boolean.getBoolean("weblogic.transaction.ownership.disable2pc");
   int testSleepTimeMillis;

   public TransactionRecoveryService() {
      this.active = false;
      this.failBackInProgress = false;
      this.passive = false;
      this.subordinatesPendingCheckStatus = Collections.synchronizedSet(new HashSet());
      this.checkpointedServers = new HashSet();
      this.testSleepTimeMillis = -1;
   }

   TransactionRecoveryService(String serverName) {
      this.active = false;
      this.failBackInProgress = false;
      this.passive = false;
      this.subordinatesPendingCheckStatus = Collections.synchronizedSet(new HashSet());
      this.checkpointedServers = new HashSet();
      this.testSleepTimeMillis = -1;
      this.serverName = serverName;

      try {
         this.runtimeMBean = new JTARecoveryRuntimeMBeanImpl(this);
      } catch (ManagementException var3) {
      }

   }

   public TransactionRecoveryService(String siteName, String serverName) {
      this(serverName);
      this.siteName = siteName;
   }

   public void migratableInitialize() {
      if (TxDebug.JTAMigration.isDebugEnabled()) {
         TxDebug.JTAMigration.debug("migratableInitialize called for '" + this.serverName + "'");
      }

   }

   public void migratableActivate() throws MigrationException {
      if (TxDebug.JTAMigration.isDebugEnabled()) {
         TxDebug.JTAMigration.debug("migratableActivate called for '" + this.serverName + "', active:" + this.active);
      }

      ServerTransactionManagerImpl stm = getTM();

      try {
         synchronized(this) {
            if (this.active) {
               if (TxDebug.JTAMigration.isDebugEnabled()) {
                  TxDebug.JTAMigration.debug("migratableActivate called for '" + this.serverName + "', returning, already active");
               }

               return;
            }

            this.active = true;
         }

         if (!this.passive && stm.getDBPassiveModeState() == 0) {
            if (this.serverName.equals(getLocalServerName())) {
               if (TxDebug.JTAMigration.isDebugEnabled()) {
                  TxDebug.JTAMigration.debug("migratableActivate called for '" + this.serverName + "', active:" + this.active + " serverName.equals(getLocalServerName()):" + this.serverName.equals(getLocalServerName()));
               }

               ownRecoveryService = true;
               stm.recover();
            } else {
               if (TxDebug.JTAMigration.isDebugEnabled()) {
                  TxDebug.JTAMigration.debug("migratableActivate called for '" + this.serverName + "', active:" + this.active + " serverName.equals(getLocalServerName()):" + this.serverName.equals(getLocalServerName()));
               }

               stm.recover(this.serverName);
               Iterator it = stm.getTransactions();

               while(true) {
                  ServerTransactionImpl tx;
                  do {
                     if (!it.hasNext()) {
                        Set subordinates = new HashSet();
                        Iterator var11 = this.checkpointedServers.iterator();

                        while(var11.hasNext()) {
                           CoordinatorDescriptor server = (CoordinatorDescriptor)var11.next();
                           if (!server.equals(getTM().getLocalCoordinatorDescriptor())) {
                              this.subordinatesPendingCheckStatus.add(server.getServerID());
                              subordinates.add(server);
                           }
                        }

                        WorkManagerFactory.getInstance().getSystem().schedule(new RecoveryServiceMigratedRequest(subordinates));
                        return;
                     }

                     tx = (ServerTransactionImpl)it.next();
                  } while(!tx.isActive() && (!tx.isPreparing() && !tx.isPrepared() || tx.getDeterminer() != null));

                  CoordinatorDescriptor txcoord = tx.getCoordinatorDescriptor();
                  if (txcoord.getServerName().equals(this.serverName) && txcoord.getDomainName().equals(PlatformHelper.getPlatformHelper().getDomainName())) {
                     tx.setRollingBack();
                     tx.localRollback();
                  }
               }
            }
         } else {
            if (TxDebug.JTAMigration.isDebugEnabled()) {
               TxDebug.JTAMigration.debug("migratableActivate called for '" + this.serverName + "', returning, PASSIVE mode");
            }

            this.passive = true;
         }
      } catch (Exception var9) {
         synchronized(this) {
            this.active = false;
         }

         TXLogger.logRecoveryServiceActivationFailed(this.serverName, var9);
         throw new MigrationException("Activation of TransactionRecoveryService for server '" + this.serverName + "' failed", var9);
      }
   }

   public void migratableDeactivate() throws MigrationException {
      if (TxDebug.JTAMigration.isDebugEnabled()) {
         TxDebug.JTAMigration.debug("migratableDeactivate called for '" + this.serverName + "', active:" + this.active);
      }

      if (!this.serverName.equals(getLocalServerName())) {
         synchronized(this) {
            if (!this.active) {
               return;
            }

            this.active = false;
         }

         this.cleanupSubordinateState();
         getTM().suspendRecovery(this.serverName);
      } else {
         if (TransactionService.isRunning()) {
            TXLogger.logMigrateRecoveryServiceWhileServerActive();
            throw new MigrationException("Current server is still active.  Cannot migrate Transaction Recovery Service from current server.  For controlled migration, please shut down the server and then perform the manual migration.");
         }

         if (TransactionService.isSuspending() || TransactionService.isForceSuspending() || TransactionService.isShuttingDown()) {
            synchronized(this) {
               if (!this.active) {
                  return;
               }

               this.active = false;
            }

            getTM().checkpoint();
         }
      }

   }

   public int getOrder() {
      return -900;
   }

   String getServerName() {
      return this.serverName;
   }

   String getSiteName() {
      return this.siteName;
   }

   static final boolean isInCluster() {
      return ManagementService.getRuntimeAccess(kernelId).getServer().getCluster() != null;
   }

   boolean isActive() {
      return this.active && getTM().getDBPassiveModeState() == 0;
   }

   private JTARecoveryRuntime getRuntimeMBean() {
      return this.runtimeMBean;
   }

   static TransactionRecoveryService getLocalTransactionRecoveryService() {
      String localServerName = getLocalServerName();
      synchronized(mServices) {
         return (TransactionRecoveryService)mServices.get(localServerName);
      }
   }

   static void resume() throws ServiceFailureException {
      startOwnRecoveryIfNeeded();
      if (isInCluster()) {
         try {
            deployAllTransactionRecoveryServices();
         } catch (MigrationException var1) {
            throw new ServiceFailureException("Error occurred while registering or activating the Transaction Recovery Service for the current server.", var1);
         }

         checkTransactionLogOwnership();
         started = true;
      }
   }

   public void start() throws ServiceFailureException {
   }

   public void stop() {
      this.halt();
      this.waitShutdownGracePeriod();
   }

   private void waitShutdownGracePeriod() {
      long endTime = System.currentTimeMillis() + (long)(getTM().getShutdownGracePeriod() * 1000);
      long currentTime = System.currentTimeMillis();

      while(getTM().getNumTransactions() > 0 && currentTime < endTime) {
         try {
            TxDebug.JTARecovery.debug("Waiting " + (endTime - currentTime) + "ms more for " + getTM().getNumTransactions() + " open transactions to complete.");
            if (this.testSleepTimeMillis == -1) {
               Thread.sleep(3000L);
            }

            currentTime = System.currentTimeMillis();
         } catch (InterruptedException var6) {
         }
      }

   }

   public void halt() {
   }

   static void forceSuspend() {
      synchronized(startLock) {
         if (!started) {
            return;
         }
      }

      stopOwnRecoveryIfNeeded();
      if (isInCluster()) {
         synchronized(suspendLock) {
            Iterator iter = mServices.values().iterator();

            while(iter.hasNext()) {
               TransactionRecoveryService trs = (TransactionRecoveryService)iter.next();
               trs.cleanup();
               iter.remove();
            }

         }
      }
   }

   public static void passiveModePassivate() {
      ServerTransactionManagerImpl stm = getTM();
      TransactionRecoveryService trs = getOrCreate(getLocalServerName());
      trs.passive = true;
   }

   public static void passiveModeActivate() throws SystemException {
      ServerTransactionManagerImpl stm = getTM();
      TransactionRecoveryService trs = getOrCreate(getLocalServerName());

      try {
         stm.recover();
         trs.passive = false;
      } catch (Exception var4) {
         SystemException se = new SystemException("Local Transaction Recovery Service failure during passive mode activation");
         se.initCause(var4);
         throw se;
      }
   }

   private static void startOwnRecoveryIfNeeded() throws ServiceFailureException {
      if (!isInCluster()) {
         ServerTransactionManagerImpl stm = getTM();
         TransactionRecoveryService trs = getOrCreate(getLocalServerName());
         if (!trs.passive && stm.getDBPassiveModeState() != 1) {
            try {
               stm.recover();
               trs.active = true;
               ownRecoveryService = true;
               stm.setJdbcTLogInitialized(true);
            } catch (Exception var3) {
               throw new ServiceFailureException("Fatal error creating or processing transaction log during crash recovery", var3);
            }
         } else {
            if (TxDebug.JTARecovery.isDebugEnabled()) {
               TxDebug.JTARecovery.debug("startOwnRecoveryIfNeeded() not recovering, PASSIVE mode");
            }

            trs.active = true;
            trs.passive = true;
            ownRecoveryService = true;
         }
      }
   }

   private static void stopOwnRecoveryIfNeeded() {
      if (!isInCluster()) {
         getTM().checkpoint();
      }

   }

   static void scheduleFailBack(String serverName) {
      if (requestFailBack(serverName)) {
         if (TxDebug.JTAMigration.isDebugEnabled()) {
            TxDebug.JTAMigration.debug("MigratedTLog.scheduleFailBack for '" + serverName + "'");
         }

         WorkManagerFactory.getInstance().getSystem().schedule(new FailBackRequest(serverName));
      }

   }

   static JTARecoveryRuntime getRuntimeMBean(String serverName) {
      TransactionRecoveryService trs = get(serverName);
      return trs != null ? trs.getRuntimeMBean() : null;
   }

   static JTARecoveryRuntimeMBean[] getAllRuntimeMBeans() {
      Vector mbeans = new Vector(mServices.size());
      synchronized(mServices) {
         Iterator iter = mServices.values().iterator();

         while(true) {
            if (!iter.hasNext()) {
               break;
            }

            TransactionRecoveryService trs = (TransactionRecoveryService)iter.next();
            mbeans.add(trs.getRuntimeMBean());
         }
      }

      JTARecoveryRuntimeMBean[] ary = new JTARecoveryRuntimeMBean[mbeans.size()];
      mbeans.toArray(ary);
      return ary;
   }

   private void cleanup() {
      if (this.mtConfig != null) {
         try {
            if (TxDebug.JTAMigration.isDebugEnabled()) {
               TxDebug.JTAMigration.debug("Unregistering migratable for '" + this.serverName + "', isManualActiveOn: " + this.mtConfig.isManualActiveOn(ManagementService.getRuntimeAccess(kernelId).getServer()) + "', userPreferredServer: " + this.mtConfig.getUserPreferredServer().getName() + ", migrationPolicy: " + this.mtConfig.getMigrationPolicy());
            }

            getMigrationManager().unregister(this, this.mtConfig);
            if (TxDebug.JTAMigration.isDebugEnabled()) {
               TxDebug.JTAMigration.debug("Migratable for '" + this.serverName + "' unregistered");
            }
         } catch (MigrationException var3) {
            if (TxDebug.JTAMigration.isDebugEnabled()) {
               TxDebug.JTAMigration.debug("Migratable for '" + this.serverName + "' unregistration failed", var3);
            }

            TXLogger.logRecoveryServiceUnregistrationFailed(this.serverName);
         }
      }

      if (this.runtimeMBean != null) {
         try {
            this.runtimeMBean.unregister();
         } catch (ManagementException var2) {
         }

         this.runtimeMBean = null;
      }

   }

   static TransactionRecoveryService get(String serverName) {
      synchronized(mServices) {
         return (TransactionRecoveryService)mServices.get(serverName);
      }
   }

   private static TransactionRecoveryService getOrCreate(String serverName) {
      return getOrCreate((String)null, serverName);
   }

   private static TransactionRecoveryService getOrCreate(String siteName, String serverName) {
      if (siteName != null) {
         serverName = siteName + "." + serverName;
      }

      synchronized(mServices) {
         TransactionRecoveryService mg = (TransactionRecoveryService)mServices.get(serverName);
         if (mg != null) {
            mg.siteName = siteName;
            return mg;
         } else {
            mg = new TransactionRecoveryService(siteName, serverName);
            mServices.put(serverName, mg);
            return mg;
         }
      }
   }

   private static TransactionRecoveryService getOrCreate(ServerMBean serverConfig) throws MigrationException {
      TransactionRecoveryService mg = getOrCreate(serverConfig.getName());
      mg.mtConfig = serverConfig.getJTAMigratableTarget();
      if (mg.mtConfig != null) {
         String serverName = serverConfig.getName();

         try {
            if (TxDebug.JTAMigration.isDebugEnabled()) {
               TxDebug.JTAMigration.debug("Registering migratable for '" + serverName + "', isManualActiveOn: " + mg.mtConfig.isManualActiveOn(ManagementService.getRuntimeAccess(kernelId).getServer()) + "', userPreferredServer: " + mg.mtConfig.getUserPreferredServer().getName() + ", migrationPolicy: " + mg.mtConfig.getMigrationPolicy());
            }

            getMigrationManager().register(mg, mg.mtConfig);
            if (TxDebug.JTAMigration.isDebugEnabled()) {
               TxDebug.JTAMigration.debug("Migratable for '" + serverName + "' registered");
            }
         } catch (MigrationException var4) {
            if (TxDebug.JTAMigration.isDebugEnabled()) {
               TxDebug.JTAMigration.debug("Migratable for '" + serverName + "' registration failed", var4);
            }

            TXLogger.logRecoveryServiceRegistrationFailed(serverName);
            if (serverName.equals(getLocalServerName())) {
               throw var4;
            }
         }
      }

      return mg;
   }

   private static void remove(String serverName) {
      TransactionRecoveryService mg = get(serverName);
      if (mg != null) {
         mg.cleanup();
         synchronized(mServices) {
            mServices.remove(serverName);
         }
      }

   }

   private static void deployAllTransactionRecoveryServices() throws MigrationException {
      ClusterMBean clusterConfig = ManagementService.getRuntimeAccess(kernelId).getServer().getCluster();
      ServerMBean[] serverConfigs = clusterConfig.getServers();

      for(int i = 0; i < serverConfigs.length; ++i) {
         ServerMBean serverConfig = serverConfigs[i];
         String serverName = serverConfigs[i].getName();
         MigratableTargetMBean mtConfig = serverConfig.getJTAMigratableTarget();
         if (isLocalServerIncluded(mtConfig.getAllCandidateServers())) {
            getOrCreate(serverConfig);
         }

         if (!serverName.equals(getLocalServerName())) {
            mtConfig.addPropertyChangeListener(new DeploymentChangeListener(serverName));
            if (TxDebug.JTAMigration.isDebugEnabled()) {
               TxDebug.JTAMigration.debug("Added DeploymentChangeListener for '" + serverName + "'");
            }
         }
      }

   }

   private static void checkTransactionLogOwnership() throws ServiceFailureException {
      if (ownRecoveryService) {
         if (TxDebug.JTAMigration.isDebugEnabled()) {
            TxDebug.JTAMigration.debug("Successfully gained TLOG ownership.");
         }

      } else {
         TXLogger.logGetTransactionLogOwnershipError();
         if (m_isOwnershipDisable2pc) {
            getTM().setTwoPhaseCommitEnabled(false);
            throw new ServiceFailureException("Cannot get ownership of Transaction Log.  Make sure that the Transaction Recovery Migratable Service is migrated back to the current server before restarting it. Two-phase commit transactions will be temporarily disabled and aborted until transaction log ownership is successful.  One-phase commit transaction processing will continue to function.");
         } else {
            throw new ServiceFailureException("Cannot get ownership of Transaction Log.  Make sure that the Transaction Recovery Migratable Service is migrated back to the current server before restarting it.");
         }
      }
   }

   private static MigrationManager getMigrationManager() {
      return (MigrationManager)GlobalServiceLocator.getServiceLocator().getService(MigrationManager.class, new Annotation[0]);
   }

   private static JTAMigrationHandler getJTAMigrationHandler() {
      return (JTAMigrationHandler)GlobalServiceLocator.getServiceLocator().getService(JTAMigrationHandler.class, new Annotation[0]);
   }

   public void prepareDeployments(Collection added, boolean initialUpdate) {
   }

   public void activateDeployments() {
      initialize();
   }

   public void rollbackDeployments() {
   }

   public void destroyDeployments(Collection removed) {
   }

   static int getDefaultTRSActivationRetryPeriods() {
      ClusterMBean clusterMBean = ManagementService.getRuntimeAccess(kernelId).getServer().getCluster();
      if (clusterMBean != null) {
         int leaseExpirationMillis = clusterMBean.getHealthCheckIntervalMillis() * clusterMBean.getHealthCheckPeriodsUntilFencing() + clusterMBean.getFencingGracePeriodMillis();
         return leaseExpirationMillis / 5000 + 1;
      } else {
         return 1;
      }
   }

   public static void initialize() {
      try {
         synchronized(startLock) {
            if (!started) {
               TxDebug.JTARecovery.debug("TransactionRecoveryService.initialize setting registerCrossSiteJTARecoveryRuntimeListener...");
               PlatformHelperImpl.registerCrossSiteJTARecoveryRuntimeListener = new PlatformHelperImpl.CrossSiteJTARecoveryRuntimeListener() {
                  public JTARecoveryRuntime manageCrossDomainJTARecoveryRuntime(String siteName, String serverName, boolean isRegister) {
                     TxDebug.JTARecovery.debug("CrossSiteJTARecoveryRuntimeListener.manageCrossDomainJTARecoveryRuntime  siteName:" + siteName + " serverName:" + serverName + " isRegister:" + isRegister);
                     TransactionRecoveryService trs = TransactionRecoveryService.getOrCreate(siteName, serverName);
                     trs.active = isRegister;
                     return trs.getRuntimeMBean();
                  }
               };
               PlatformHelper.getPlatformHelper().openPrimaryStore(false);
               if (PlatformHelper.getPlatformHelper().getPrimaryStore() == null) {
                  String msg = "Transaction Log PrimaryStore can not be set";
                  getTM().registerFailedPrimaryStore(new ServiceFailureException(msg));
                  TXLogger.logFailedTLOGBoot(msg);
               } else if (isInCluster() && isAutomaticMigrationMode() && isStrictOwnershipCheck() && syspropTRSActivationRetryPeriods != 0) {
                  int retryPeriods = syspropTRSActivationRetryPeriods == -1 ? getDefaultTRSActivationRetryPeriods() : syspropTRSActivationRetryPeriods;
                  int retryAttempt = 0;

                  while(retryAttempt < retryPeriods) {
                     if (retryAttempt > 0) {
                        if (TxDebug.JTAMigration.isDebugEnabled()) {
                           TxDebug.JTAMigration.debug("Waiting " + trsActivationRetryWaitMillis + " ms for migratable activation (attempt " + retryAttempt + " of " + retryPeriods + ")...");
                        }

                        try {
                           Thread.sleep((long)trsActivationRetryWaitMillis);
                        } catch (InterruptedException var7) {
                        }
                     }

                     if (!TransactionService.isShuttingDown() && !TransactionService.isSuspending() && !TransactionService.isForceSuspending()) {
                        try {
                           resume();
                        } catch (ServiceFailureException var8) {
                           if (TxDebug.JTAMigration.isDebugEnabled()) {
                              TxDebug.JTAMigration.debug("Error resuming transaction recovery service: " + var8);
                           }

                           if (retryAttempt >= retryPeriods - 1) {
                              if (m_isOwnershipDisable2pc) {
                                 if (TxDebug.JTAMigration.isDebugEnabled()) {
                                    TxDebug.JTAMigration.debug("Unable to obtain TRS ownership after " + retryPeriods + " attempts, not failing server boot because weblogic.transaction.ownership.disable2pc=true");
                                 }
                              } else {
                                 if (TxDebug.JTAMigration.isDebugEnabled()) {
                                    TxDebug.JTAMigration.debug("Unable to obtain TRS ownership after " + retryPeriods + " attempts, setting JTA health state to FAILED");
                                 }

                                 JTARuntime runtime = getTM().getRuntime();
                                 if (runtime != null) {
                                    HealthEvent event = new HealthEvent(1, getTM().getServerName(), "Transaction Recovery Service is not active after waiting " + (long)retryPeriods * (long)trsActivationRetryWaitMillis + " ms");
                                    runtime.healthEvent(event);
                                 }
                              }

                              throw var8;
                           }

                           ++retryAttempt;
                           continue;
                        }

                        return;
                     }

                     if (TxDebug.JTAMigration.isDebugEnabled()) {
                        TxDebug.JTAMigration.debug("Aborting TransactionRecoveryService intialization; Transaction Service is suspending or shutting down");
                     }
                     break;
                  }
               } else {
                  resume();
               }
            }
         }
      } catch (ServiceFailureException var10) {
         TXLogger.logFailedActivateDeployments(var10);
      }

   }

   private static boolean requestFailBack(String serverName) {
      TransactionRecoveryService mg = get(serverName);
      if (mg != null) {
         synchronized(mg) {
            if (!mg.failBackInProgress && mg.active) {
               mg.failBackInProgress = true;
               return true;
            } else {
               return false;
            }
         }
      } else {
         return false;
      }
   }

   private static void failBack(String serverName, boolean destinationUp) {
      boolean var17 = false;

      label174: {
         try {
            var17 = true;
            JTAMigrationHandler migrationHandler = getJTAMigrationHandler();
            if (migrationHandler.isAvailable(serverName)) {
               try {
                  migrateJTA(serverName, serverName, true, destinationUp);
                  if (TxDebug.JTAMigration.isDebugEnabled()) {
                     TxDebug.JTAMigration.debug("Successfully failed back to '" + serverName + "'");
                     var17 = false;
                  } else {
                     var17 = false;
                  }
               } catch (MigrationException var22) {
                  if (TxDebug.JTAMigration.isDebugEnabled()) {
                     TxDebug.JTAMigration.debug("Recovery is done, but fail-back to '" + serverName + "' failed.", var22);
                  }

                  if (destinationUp) {
                     TXLogger.logRecoveryServiceFailbackFailed(serverName);
                     var17 = false;
                  } else {
                     TxDebug.JTAMigration.debug("Try again with destinationUp=true");

                     try {
                        migrateJTA(serverName, serverName, true, true);
                        if (TxDebug.JTAMigration.isDebugEnabled()) {
                           TxDebug.JTAMigration.debug("Successfully failed back to '" + serverName + "'");
                           var17 = false;
                        } else {
                           var17 = false;
                        }
                     } catch (MigrationException var21) {
                        if (TxDebug.JTAMigration.isDebugEnabled()) {
                           TxDebug.JTAMigration.debug("Recovery is done, but fail-back to '" + serverName + "' failed.", var21);
                        }

                        TXLogger.logRecoveryServiceFailbackRetryFailed(serverName);
                        var17 = false;
                     }
                  }
               }
               break label174;
            }

            if (TxDebug.JTAMigration.isDebugEnabled()) {
               TxDebug.JTAMigration.debug("Migrator is not available. Will skip failback.");
               var17 = false;
            } else {
               var17 = false;
            }
         } finally {
            if (var17) {
               TransactionRecoveryService mg = get(serverName);
               if (mg != null) {
                  synchronized(mg) {
                     mg.failBackInProgress = false;
                  }
               }

            }
         }

         TransactionRecoveryService mg = get(serverName);
         if (mg != null) {
            synchronized(mg) {
               mg.failBackInProgress = false;
            }
         }

         return;
      }

      TransactionRecoveryService mg = get(serverName);
      if (mg != null) {
         synchronized(mg) {
            mg.failBackInProgress = false;
         }
      }

   }

   static final void failbackIfNeeded() {
      JTAMigrationHandler jtaMigrationHandler = getJTAMigrationHandler();

      try {
         if (isInCluster()) {
            if (ManagementService.getRuntimeAccess(kernelId).isAdminServer() && !isAutomaticMigrationMode()) {
               if (TxDebug.JTAMigration.isDebugEnabled()) {
                  TxDebug.JTAMigration.debug("AdminServer itself is in cluster, and it is manual JTA migration policy. Will skip TRS failback.");
               }

            } else {
               if (TxDebug.JTAMigration.isDebugEnabled()) {
                  TxDebug.JTAMigration.debug("Going to deactivate JTAMT...");
               }

               jtaMigrationHandler.deactivateJTA(getLocalServerName(), getLocalServerName());
               if (TxDebug.JTAMigration.isDebugEnabled()) {
                  TxDebug.JTAMigration.debug("Deactivated JTAMT");
               }

            }
         }
      } catch (Exception var4) {
         if (TxDebug.JTAMigration.isDebugEnabled()) {
            TxDebug.JTAMigration.debug("Failed to deactivate JTAMT", var4);
         }

         if (isStrictOwnershipCheck()) {
            Throwable thrw = var4.getCause();

            for(int ix = 0; ix < 5 && thrw != null; ++ix) {
               if (thrw instanceof ClusterReformationInProgressException) {
                  if (TxDebug.JTAMigration.isDebugEnabled()) {
                     TxDebug.JTAMigration.debug("Ignore failing deactivate JTAMT even strict-ownership-check enabled, there is no other server up and running in the cluster.");
                  }

                  return;
               }

               thrw = thrw.getCause();
            }
         }

         if (!isStrictOwnershipCheck() && !jtaMigrationHandler.isAvailable(getLocalServerName())) {
            TXLogger.logMigratorNotAvailable(getLocalServerName());
         } else {
            throw new MigrationException("Could not start JTAMT on local server because it could not be deactivated on the current host.", var4);
         }
      }
   }

   private static void migrateJTA(String migratableName, String serverName, boolean sourceUp, boolean destinationUp) throws MigrationException {
      try {
         SecurityServiceManager.runAs(kernelId, kernelId, new MigrateJTAAction(migratableName, serverName, sourceUp, destinationUp));
      } catch (PrivilegedActionException var6) {
         Exception e = var6.getException();
         if (e instanceof RuntimeException) {
            throw (RuntimeException)e;
         } else if (e instanceof MigrationException) {
            throw (MigrationException)e;
         } else {
            throw new MigrationException("Unexpected exception thrown during migrate operation.", e);
         }
      }
   }

   private static ServerMBean getServerConfigMBean(String serverName) {
      return ManagementService.getRuntimeAccess(kernelId).getDomain().lookupServer(serverName);
   }

   static final String getLocalServerName() {
      return ManagementService.getRuntimeAccess(kernelId).getServer().getName();
   }

   static final ServerMBean getLocalServer() {
      return ManagementService.getRuntimeAccess(kernelId).getServer();
   }

   static final boolean isAutomaticMigrationMode() {
      return getLocalServer().getJTAMigratableTarget() != null && ("failure-recovery".equals(getLocalServer().getJTAMigratableTarget().getMigrationPolicy()) || "shutdown-recovery".equals(getLocalServer().getJTAMigratableTarget().getMigrationPolicy()));
   }

   private static final boolean isStrictOwnershipCheck() {
      return getLocalServer().getJTAMigratableTarget() != null && getLocalServer().getJTAMigratableTarget().isStrictOwnershipCheck();
   }

   private static ServerTransactionManagerImpl getTM() {
      return (ServerTransactionManagerImpl)ServerTransactionManagerImpl.getTransactionManager();
   }

   private static boolean isLocalServerIncluded(ServerMBean[] servers) {
      for(int i = 0; i < servers.length; ++i) {
         if (servers[i].getName().equals(getLocalServerName())) {
            return true;
         }
      }

      return false;
   }

   synchronized void addCheckpointedServer(CoordinatorDescriptor cd) {
      if (cd != null) {
         this.checkpointedServers.add(cd);
      }
   }

   synchronized List getCheckpointedServers() {
      return new ArrayList(this.checkpointedServers);
   }

   boolean allowAutoFailback() {
      return this.subordinatesPendingCheckStatus.size() == 0;
   }

   void setDelayedFailback() {
      this.delayedAutoFailback = true;
   }

   void checkStatusFrom(String scURL) {
      boolean removed = this.subordinatesPendingCheckStatus.remove(CoordinatorDescriptor.getServerID(scURL));
      if (removed && this.delayedAutoFailback) {
         scheduleFailBack(this.serverName);
      }

   }

   void cleanupSubordinateState() {
      this.checkpointedServers.clear();
      this.subordinatesPendingCheckStatus.clear();
      this.delayedAutoFailback = false;
   }

   private static final class DeploymentChangeListener implements PropertyChangeListener {
      final String serverName;

      DeploymentChangeListener(String serverName) {
         this.serverName = serverName;
      }

      public void propertyChange(PropertyChangeEvent evt) {
         if (evt.getPropertyName().equals("ConstrainedCandidateServers")) {
            boolean localServerIncluded = TransactionRecoveryService.isLocalServerIncluded((ServerMBean[])((ServerMBean[])evt.getNewValue()));
            if (TxDebug.JTAMigration.isDebugEnabled()) {
               TxDebug.JTAMigration.debug("Received deployment change notification for '" + this.serverName + "', localServerIncluded: " + localServerIncluded);
            }

            if (localServerIncluded) {
               try {
                  TransactionRecoveryService.getOrCreate(TransactionRecoveryService.getServerConfigMBean(this.serverName));
               } catch (MigrationException var4) {
                  if (TxDebug.JTAMigration.isDebugEnabled()) {
                     TxDebug.JTAMigration.debug("Processing of deployment change notification for '" + this.serverName + "' failed", var4);
                  }
               } catch (Exception var5) {
                  if (TxDebug.JTAMigration.isDebugEnabled()) {
                     TxDebug.JTAMigration.debug("Processing of deployment change notification for '" + this.serverName + "' failed", var5);
                  }
               }
            } else {
               TransactionRecoveryService.remove(this.serverName);
            }
         }

      }
   }

   private final class RecoveryServiceMigratedRequest extends WorkAdapter {
      final Set subordinates;

      RecoveryServiceMigratedRequest(Set subordinates) {
         this.subordinates = subordinates;
      }

      public void run() {
         Iterator var1 = this.subordinates.iterator();

         while(var1.hasNext()) {
            CoordinatorDescriptor server = (CoordinatorDescriptor)var1.next();

            try {
               if (!(Boolean)SecurityServiceManager.runAs(TransactionRecoveryService.kernelId, TransactionRecoveryService.kernelId, new RecoveryServiceMigratedAction(TransactionRecoveryService.this.serverName, server))) {
                  TransactionRecoveryService.this.subordinatesPendingCheckStatus.remove(server.getServerID());
               }
            } catch (PrivilegedActionException var4) {
               if (TxDebug.JTAMigration.isDebugEnabled()) {
                  TxDebug.JTAMigration.debug("error invoking recoveryServiceMigrated() on " + server + " for migrated TRS " + TransactionRecoveryService.this.serverName, var4);
               }

               TransactionRecoveryService.this.subordinatesPendingCheckStatus.remove(server.getServerID());
            }
         }

      }

      public String toString() {
         return "RecoveryServiceMigratedRequest for " + TransactionRecoveryService.this.serverName + ": subordinates=" + this.subordinates;
      }
   }

   private static class RecoveryServiceMigratedAction implements PrivilegedExceptionAction {
      CoordinatorDescriptor subordinate;
      String migratableName;

      public RecoveryServiceMigratedAction(String migratableName, CoordinatorDescriptor subordinate) {
         this.migratableName = migratableName;
         this.subordinate = subordinate;
      }

      public Boolean run() throws Exception {
         Object co = CoordinatorFactory.getCachedCoordinator(this.subordinate, (PeerExchangeTransaction)null);
         if (co != null && co instanceof SubCoordinatorOneway7) {
            if (TxDebug.JTAMigration.isDebugEnabled()) {
               TxDebug.JTAMigration.debug("invoking recoveryServiceMigrated() on " + this.subordinate + " for migrated TRS " + this.migratableName);
            }

            SubCoordinatorOneway7 sco7 = (SubCoordinatorOneway7)co;
            sco7.recoveryServiceMigrated(PlatformHelper.getPlatformHelper().getDomainName(), this.migratableName, TransactionRecoveryService.getTM().getLocalCoordinatorDescriptor().getCoordinatorURL());
            return Boolean.TRUE;
         } else {
            if (TxDebug.JTAMigration.isDebugEnabled()) {
               TxDebug.JTAMigration.debug("NOT invoking recoveryServiceMigrated() on " + this.subordinate + " for migrated TRS " + this.migratableName);
            }

            return Boolean.FALSE;
         }
      }
   }

   private static class MigrateJTAAction implements PrivilegedExceptionAction {
      String migratableName;
      String serverName;
      boolean sourceUp;
      boolean destinationUp;

      MigrateJTAAction(String migratableName, String serverName, boolean sourceUp, boolean destinationUp) {
         this.migratableName = migratableName;
         this.serverName = serverName;
         this.sourceUp = sourceUp;
         this.destinationUp = destinationUp;
      }

      public Object run() throws Exception {
         TransactionRecoveryService.getJTAMigrationHandler().migrateJTA(this.migratableName, this.serverName, this.sourceUp, this.destinationUp);
         return null;
      }
   }

   private static final class FailBackRequest extends WorkAdapter {
      final String serverName;

      FailBackRequest(String serverName) {
         this.serverName = serverName;
      }

      public void run() {
         TransactionRecoveryService.failBack(this.serverName, false);
      }

      public String toString() {
         return "Recovery fail-back request for '" + this.serverName + "'";
      }
   }
}
