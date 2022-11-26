package weblogic.transaction.internal;

import java.lang.annotation.Annotation;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.transaction.SystemException;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.image.ImageManager;
import weblogic.management.ManagementException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JTAMBean;
import weblogic.management.provider.ManagementService;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;
import weblogic.transaction.ClientTransactionManager;
import weblogic.transaction.TransactionHelper;
import weblogic.work.WorkManagerFactory;

@Service
@Named
@RunLevel(10)
public class TransactionService extends AbstractServerService implements Constants {
   @Inject
   @Named("RemoteNamingService")
   private ServerService dependencyOnRemoteNamingService;
   @Inject
   @Named("DefaultStoreService")
   private ServerService defendencyOnDefaultStoreService;
   @Inject
   @Named("PrimordialClusterLeaderService")
   private ServerService defendencyOnPrimordialClusterLeaderService;
   @Inject
   @Named("MigrationManagerService")
   private ServerService dependencyOnMigrationManager;
   private static ClientTransactionManagerImpl ctm = null;
   public static final int FORCE_SUSPENDING = 1004;
   private static int state = 0;
   private static Object stateLock = new String("LifecycleState");
   private static Object suspendLock = new String("LifecycleSuspend");
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final int jtaAbandonGraceSeconds = getJtaAbandonGraceSecondsSysProp();
   private static final int jtaCoordinatorWMMinConstraint = getWMMinSysProp();
   private static final int jtaCoordinatorWMMaxConstraint = getWMMaxSysProp();
   private static final boolean isPartitionLifecycleOldmodel = false;
   private static TransactionService singleton = null;

   private static int getJtaAbandonGraceSecondsSysProp() {
      String val = System.getProperty("weblogic.transaction.abandonGraceSeconds");
      int aAbandonGraceConstant = 600;
      if (val != null) {
         try {
            int i = Integer.parseInt(val);
            if (i > 0) {
               aAbandonGraceConstant = i;
            }
         } catch (NumberFormatException var3) {
         }
      }

      return aAbandonGraceConstant;
   }

   private static int getWMMinSysProp() {
      String val = System.getProperty("weblogic.transaction.jta.coordinator.wm.min.constraint");
      int minConstraint = 3;
      if (val != null) {
         try {
            int i = Integer.parseInt(val);
            if (i > 0) {
               minConstraint = i;
            }
         } catch (NumberFormatException var3) {
         }
      }

      return minConstraint;
   }

   private static int getWMMaxSysProp() {
      String val = System.getProperty("weblogic.transaction.jta.coordinator.wm.max.constraint");
      int maxConstraint = -1;
      if (val != null) {
         try {
            int i = Integer.parseInt(val);
            if (i > 0) {
               maxConstraint = i;
            }
         } catch (NumberFormatException var3) {
         }
      }

      return maxConstraint;
   }

   private void initialize() throws ServiceFailureException {
      if (!this.initStateTimerAndTxHelper()) {
         if (ctm == null) {
            this.initTM();
         }

         this.initCheckTimerRegisterWithHealthServiceAndSetState();
      }
   }

   boolean initStateTimerAndTxHelper() {
      synchronized(stateLock) {
         if (state != 0) {
            return true;
         }

         state = 1;
      }

      if (TxDebug.JTALifecycle.isDebugEnabled()) {
         TxDebug.JTALifecycle.debug("INITIALIZING ...");
      }

      WLSTimer.initialize();
      NotificationBroadcasterManager.getInstance().startTimer();
      initializeEarly();
      return false;
   }

   private void initTM() throws ServiceFailureException {
      JTAMBean mbean = this.initJNDIConfigMBeanAndTM();
      this.initJTACoordinatorWM();
      this.initCoordinatorAndSetTxTimeoutThread(mbean);
   }

   JTAMBean initJNDIConfigMBeanAndTM() throws ServiceFailureException {
      try {
         JNDIAdvertiser.initialize(getServerName());
      } catch (NamingException var3) {
         throw new ServiceFailureException(var3);
      }

      DomainMBean domainMBean = ManagementService.getRuntimeAccess(kernelId).getDomain();
      JTAMBean mbean = getConfiguration();
      ctm = createTransactionManager(domainMBean, mbean);
      return mbean;
   }

   void initJTACoordinatorWM() {
      WorkManagerFactory.getInstance().findOrCreate("JTACoordinatorWM", 100, this.getJtaCoordinatorWMMinConstraint(), this.getJtaCoordinatorWMMaxConstraint());
      WorkManagerFactory.getInstance().findOrCreate("OneWayJTACoordinatorWM", 100, 3, -1);
   }

   void initCoordinatorAndSetTxTimeoutThread(JTAMBean mbean) throws ServiceFailureException {
      createCoordinator(ctm);
      setTransactionTimeoutMainThread(mbean);
   }

   void initCheckTimerRegisterWithHealthServiceAndSetState() throws ServiceFailureException {
      if (!ctm.isTimerStarted()) {
         ClientTransactionManagerImpl var10003 = ctm;
         throw new ServiceFailureException("JTA Timer did not start: ", ClientTransactionManagerImpl.getTimerFailureReason());
      } else {
         ((JTARuntimeImpl)getTM().getRuntime()).registerWithHealthService();
         synchronized(stateLock) {
            if (state == 1) {
               state = 3;
            }
         }

         if (TxDebug.JTALifecycle.isDebugEnabled()) {
            TxDebug.JTALifecycle.debug("INITIALIZING DONE");
         }

      }
   }

   public void start() throws ServiceFailureException {
      this.initialize();
      this.resume();
   }

   private void resume() {
      if (TxDebug.JTALifecycle.isDebugEnabled()) {
         TxDebug.JTALifecycle.debug("RESUMING ...");
      }

      synchronized(stateLock) {
         if (state != 3) {
            if (TxDebug.JTALifecycle.isDebugEnabled()) {
               TxDebug.JTALifecycle.debug("Skip resuming, state:" + state);
            }

            return;
         }

         state = 6;
      }

      TransactionImpl.setAbandonGraceTimeEndSec((int)(System.currentTimeMillis() / 1000L) + this.getAbandonGraceSeconds());
      if (TxDebug.JTALifecycle.isDebugEnabled()) {
         TxDebug.JTALifecycle.debug("abandonGraceSeconds is set: " + this.getAbandonGraceSeconds());
      }

      synchronized(stateLock) {
         if (state == 6) {
            state = 2;
         }
      }

      if (TxDebug.JTALifecycle.isDebugEnabled()) {
         TxDebug.JTALifecycle.debug("RESUMING DONE");
      }

   }

   public static void initializeEarly() {
      TransactionHelper.setTransactionHelper(new TransactionHelperImpl());
   }

   public void stop() throws ServiceFailureException {
      if (TxDebug.JTALifecycle.isDebugEnabled()) {
         TxDebug.JTALifecycle.debug("TransactionService SUSPENDING ...");
      }

      if (!getTM().getJdbcTLogEnabled()) {
         runForceSuspend();
      }

      ServerTransactionManagerImpl stm = getTM();
      synchronized(suspendLock) {
         label245: {
            boolean var16 = false;

            label235: {
               try {
                  var16 = true;
                  ConcurrentHashMap txMap = stm.getTxMap();
                  if (txMap != null && txMap.size() > 0) {
                     if (TxDebug.JTALifecycle.isDebugEnabled()) {
                        TxDebug.JTALifecycle.debug("TransactionService SUSPENDING waiting on " + this.getPendingTxMap(stm));
                     }

                     suspendLock.wait();
                     var16 = false;
                  } else {
                     if (TxDebug.JTALifecycle.isDebugEnabled()) {
                        TxDebug.JTALifecycle.debug("TransactionService SUSPENDING with no pending transactions");
                     }

                     state = 3;
                     var16 = false;
                  }
                  break label235;
               } catch (InterruptedException var20) {
                  var16 = false;
               } finally {
                  if (var16) {
                     synchronized(stateLock) {
                        if (state == 3) {
                           if (TxDebug.JTALifecycle.isDebugEnabled()) {
                              TxDebug.JTALifecycle.debug("TransactionService suspendSuccessfullyCompleted");
                           }

                        }

                        if (TxDebug.JTALifecycle.isDebugEnabled()) {
                           TxDebug.JTALifecycle.debug("TransactionService suspendFailed with " + this.getPendingTxMap(stm));
                        }

                        throw new ServiceFailureException("TransactionService suspend failed");
                     }
                  }
               }

               synchronized(stateLock) {
                  if (state != 3) {
                     if (TxDebug.JTALifecycle.isDebugEnabled()) {
                        TxDebug.JTALifecycle.debug("TransactionService suspendFailed with " + this.getPendingTxMap(stm));
                     }

                     throw new ServiceFailureException("TransactionService suspend failed");
                  }

                  if (TxDebug.JTALifecycle.isDebugEnabled()) {
                     TxDebug.JTALifecycle.debug("TransactionService suspendSuccessfullyCompleted");
                  }
                  break label245;
               }
            }

            synchronized(stateLock) {
               if (state != 3) {
                  if (TxDebug.JTALifecycle.isDebugEnabled()) {
                     TxDebug.JTALifecycle.debug("TransactionService suspendFailed with " + this.getPendingTxMap(stm));
                  }

                  throw new ServiceFailureException("TransactionService suspend failed");
               }

               if (TxDebug.JTALifecycle.isDebugEnabled()) {
                  TxDebug.JTALifecycle.debug("TransactionService suspendSuccessfullyCompleted");
               }
            }
         }
      }

      if (TxDebug.JTALifecycle.isDebugEnabled()) {
         TxDebug.JTALifecycle.debug("TransactionService SUSPEND DONE");
      }

      this.shutdown();
   }

   public void halt() throws ServiceFailureException {
      if (TxDebug.JTALifecycle.isDebugEnabled()) {
         TxDebug.JTALifecycle.debug("TransactionService FORCE SUSPENDING ...");
      }

      if (!getTM().getJdbcTLogEnabled()) {
         PlatformHelper.getPlatformHelper().setTransactionServiceHalt(System.getProperty("weblogic.transactionservice.halt", "true"));
         performForceSuspend();
      }

      synchronized(stateLock) {
         if (state == 1004) {
            state = 3;
         }
      }

      if (TxDebug.JTALifecycle.isDebugEnabled()) {
         TxDebug.JTALifecycle.debug("TransactionService FORCE SUSPEND DONE");
      }

      this.shutdown();
   }

   public void shutdown() throws ServiceFailureException {
      if (TxDebug.JTALifecycle.isDebugEnabled()) {
         TxDebug.JTALifecycle.debug("SHUTTING DOWN ...");
      }

      synchronized(stateLock) {
         if (state == 7 || state == 0) {
            if (TxDebug.JTALifecycle.isDebugEnabled()) {
               TxDebug.JTALifecycle.debug("Skip shutdown, state:" + state);
            }

            return;
         }

         state = 7;
      }

      NotificationBroadcasterManager.getInstance().stopTimer();
      ((JTARuntimeImpl)getTM().getRuntime()).unregisterFromHealthService();
      synchronized(stateLock) {
         state = 0;
      }

      if (TxDebug.JTALifecycle.isDebugEnabled()) {
         TxDebug.JTALifecycle.debug("SHUTDOWN DONE");
      }

   }

   static boolean isRunning() {
      return state == 2;
   }

   static boolean isSuspending() {
      return state == 4;
   }

   static boolean isForceSuspending() {
      return state == 1004;
   }

   static boolean isShuttingDown() {
      return state == 7;
   }

   static void suspendDone() {
      synchronized(suspendLock) {
         if (state == 4) {
            state = 3;
         }

         suspendLock.notify();
      }
   }

   static void performForceSuspend() {
      synchronized(stateLock) {
         if (state != 2 && state != 4) {
            if (TxDebug.JTALifecycle.isDebugEnabled()) {
               TxDebug.JTALifecycle.debug("Skip performForceSuspend, state:" + state);
            }

            return;
         }

         setState(1004);
      }

      if (TxDebug.JTALifecycle.isDebugEnabled()) {
         TxDebug.JTALifecycle.debug("Performing forceSuspend ...");
      }

      synchronized(suspendLock) {
         suspendLock.notify();
      }

      TransactionRecoveryService.forceSuspend();
      getTM().dropAllTransactions();
   }

   private static ClientTransactionManagerImpl createTransactionManager(DomainMBean domainMBean, JTAMBean mbean) throws ServiceFailureException {
      ServerTransactionManagerImpl stm = new ServerTransactionManagerImpl(new JTAConfigImpl(domainMBean, mbean), getServerName());
      stm.setLocalCoordinatorDescriptor(getLocalCoordinatorDescriptor());

      try {
         stm.setJTARuntime(new JTARuntimeImpl("JTARuntime", stm));
      } catch (ManagementException var4) {
         throw new ServiceFailureException(var4);
      }

      ImageManager imageManager = (ImageManager)GlobalServiceLocator.getServiceLocator().getService(ImageManager.class, new Annotation[0]);
      imageManager.registerImageSource("JTA", new JTADiagnosticImageSource(stm));
      ctm = new ClientTransactionManagerImpl();
      ctm.setCoordinatorURL(getLocalCoordinatorURL());
      ctm.setAbandonTimeoutSeconds(mbean.getAbandonTimeoutSeconds());
      ctm.setDefaultTimeoutSeconds(mbean.getTimeoutSeconds());
      ClientTransactionManagerImpl var10000 = ctm;
      ClientTransactionManagerImpl.initialized = true;
      JNDIAdvertiser.advertiseTransactionManager(ctm);
      JNDIAdvertiser.advertiseUserTransaction(ctm);
      JNDIAdvertiser.advertiseTransactionSynchronizationRegistry(ctm);
      return ctm;
   }

   private static void setTransactionTimeoutMainThread(JTAMBean mbean) throws ServiceFailureException {
      try {
         TransactionHelper th = TransactionHelper.getTransactionHelper();
         ClientTransactionManager tm = th.getTransactionManager();
         tm.setTransactionTimeout(mbean.getTimeoutSeconds());
      } catch (SystemException var3) {
         throw new ServiceFailureException("Setting transaction timeout " + var3);
      }
   }

   private static String getServerName() {
      return ManagementService.getRuntimeAccess(kernelId).getServer().getName();
   }

   private static CoordinatorImpl createCoordinator(ClientTransactionManagerImpl ctm) throws ServiceFailureException {
      CoordinatorImpl co = new CoordinatorImpl();
      ctm.setCoordinator(co);
      getTM().setLocalCoordinator(co);

      try {
         Context ctx = JNDIAdvertiser.getServerContext();
         ctx.bind(JNDIAdvertiser.getServerName(), ctm);
         ServerHelper.exportObject((Remote)ctm.getCoordinator());
         return co;
      } catch (NamingException var3) {
         TXLogger.logAdvertiseCoordinatorError(var3);
         throw new ServiceFailureException(var3);
      } catch (RemoteException var4) {
         TXLogger.logExportCoordinatorObjIDError(var4);
         throw new ServiceFailureException(var4);
      }
   }

   private static JTAMBean getConfiguration() throws ServiceFailureException {
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      JTAMBean mb = domain.getJTA();
      if (mb == null) {
         throw new ServiceFailureException("Unable to obtain configuration for transaction service");
      } else {
         return mb;
      }
   }

   private static CoordinatorDescriptor getLocalCoordinatorDescriptor() throws ServiceFailureException {
      try {
         return ((ServerCoordinatorDescriptorManager)PlatformHelper.getPlatformHelper().getCoordinatorDescriptorManager()).getLocalCoordinatorDescriptor();
      } catch (Exception var1) {
         throw new ServiceFailureException("Transaction service startup failure", var1);
      }
   }

   private static String getLocalCoordinatorURL() throws ServiceFailureException {
      try {
         return ((ServerCoordinatorDescriptorManager)PlatformHelper.getPlatformHelper().getCoordinatorDescriptorManager()).getLocalCoordinatorURL();
      } catch (Exception var1) {
         throw new ServiceFailureException("Transaction service startup failure", var1);
      }
   }

   private static ServerTransactionManagerImpl getTM() {
      return (ServerTransactionManagerImpl)TransactionManagerImpl.getTransactionManager();
   }

   public TransactionService() {
      setTransactionService(this);
   }

   private static void setTransactionService(TransactionService ts) {
      singleton = ts;
   }

   public static TransactionService getTransactionService() {
      return singleton;
   }

   public String getName() {
      return "Transaction Service";
   }

   public String getVersion() {
      return "JTA 1.1";
   }

   static void runForceSuspend() {
      synchronized(stateLock) {
         if (state != 2) {
            if (TxDebug.JTALifecycle.isDebugEnabled()) {
               TxDebug.JTALifecycle.debug("Skip runForceSuspend, state:" + state);
            }

            return;
         }

         setState(4);
      }

      TransactionRecoveryService.forceSuspend();
   }

   static void setState(int setState) {
      state = setState;
   }

   static int getState() {
      return state;
   }

   int getAbandonGraceSeconds() {
      return jtaAbandonGraceSeconds;
   }

   int getJtaCoordinatorWMMinConstraint() {
      return jtaCoordinatorWMMinConstraint;
   }

   int getJtaCoordinatorWMMaxConstraint() {
      return jtaCoordinatorWMMaxConstraint;
   }

   private String getPendingTxMap(ServerTransactionManagerImpl stm) {
      String pendingStr = "None";
      if (stm == null) {
         return pendingStr;
      } else {
         ConcurrentHashMap txMap = stm.getTxMap();
         int ct = 0;
         if (txMap != null && txMap.size() > 0) {
            pendingStr = "\n   ";

            StringBuilder var10000;
            TransactionImpl tx;
            for(Iterator it = txMap.values().iterator(); it.hasNext(); pendingStr = var10000.append(ct).append(": ").append(tx.toString()).append("\n   ").toString()) {
               tx = (TransactionImpl)it.next();
               var10000 = (new StringBuilder()).append(pendingStr).append(" ");
               ++ct;
            }

            pendingStr = ct + " pending transactions: " + pendingStr;
         }

         return pendingStr;
      }
   }
}
