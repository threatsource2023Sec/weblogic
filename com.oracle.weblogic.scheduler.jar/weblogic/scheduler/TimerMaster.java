package weblogic.scheduler;

import java.lang.annotation.Annotation;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.cluster.ClusterExtensionLogger;
import weblogic.cluster.ClusterLogger;
import weblogic.cluster.ClusterServices;
import weblogic.cluster.singleton.LeaseLostListener;
import weblogic.cluster.singleton.Leasing;
import weblogic.cluster.singleton.LeasingException;
import weblogic.cluster.singleton.LeasingFactory;
import weblogic.cluster.singleton.SingletonService;
import weblogic.cluster.singleton.SingletonServicesManager;
import weblogic.cluster.singleton.LeasingFactory.Locator;
import weblogic.jndi.Environment;
import weblogic.server.GlobalServiceLocator;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.Timer;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.Debug;

public class TimerMaster implements TimerMasterRemote, NakedTimerListener, SingletonService, LeaseLostListener {
   private static final boolean DEBUG = Debug.getCategory("weblogic.JobScheduler").isEnabled();
   private static final String TIMER_MANAGER = "weblogic.scheduler.TimerMaster";
   static final String JNDI_NAME = "weblogic.scheduler.TimerMaster";
   static final String LEASE_NAME = "TimerMaster";
   static final int eventHorizonSeconds = 60;
   static final int pollsPerPeriod = 2;
   private static Leasing leasingService;
   private List readyTimers;
   private int numServersCalledIn;
   private int status;
   private static final int WAITING_TO_REGISTER = 0;
   private static final long registrationRetryPeriodSeconds = 20L;
   private static final int WAITING_TO_LEASE = 1;
   private static final int IS_MASTER = 2;
   private Context context;
   private Timer timer;
   private Timer executorTimer;
   private final String leaseName;
   private final String jndiName;
   private TimerBasis timerBasis;
   private Set timersWithLease;
   private final boolean forGlobalRG;
   private static HashMap timerMasters = new HashMap();
   static final String GLOBAL_RG_DOMAIN = "weblogic.timers.defaultDomain-RG";
   List globalRGTimerManagers;

   static synchronized void initialize() throws TimerException {
      Debug.assertion(timerMasters.get("weblogic.timers.defaultDomain") == null);
      timerMasters.put("weblogic.timers.defaultDomain", new TimerMaster("weblogic.timers.defaultDomain", false));
   }

   static synchronized TimerMaster initialize(String domainID) throws TimerException {
      TimerMaster timerMaster = (TimerMaster)timerMasters.get(domainID);
      if (timerMaster == null) {
         timerMaster = new TimerMaster(domainID, true);
         timerMasters.put(domainID, timerMaster);
      } else {
         timerMaster.start(domainID);
      }

      return timerMaster;
   }

   static synchronized TimerMaster getInstance() {
      return (TimerMaster)timerMasters.get("weblogic.timers.defaultDomain");
   }

   private TimerMaster(String domainID, boolean forPartitionOrGlobalRG) throws TimerException {
      this.forGlobalRG = "weblogic.timers.defaultDomain-RG".equals(domainID);
      this.leaseName = this.forGlobalRG ? "TimerMaster$0" : (forPartitionOrGlobalRG ? "TimerMaster$" + domainID : "TimerMaster");
      if (forPartitionOrGlobalRG) {
         this.timersWithLease = new HashSet();
      }

      if (this.forGlobalRG) {
         this.globalRGTimerManagers = new ArrayList();
      }

      this.jndiName = this.forGlobalRG ? "weblogic.scheduler.TimerMaster-RG" : "weblogic.scheduler.TimerMaster";
      this.start(domainID);
      this.setupRegistration();
   }

   static void addTimerWithLeaseForDomain(String domainID, TimerState timerState) {
      TimerMaster timerMaster = (TimerMaster)timerMasters.get(domainID);
      if (timerMaster != null) {
         timerMaster.addTimerWithLease(timerState);
      }

   }

   static void removeTimerWithLeaseForDomain(String domainID, TimerState timerState) {
      TimerMaster timerMaster = (TimerMaster)timerMasters.get(domainID);
      if (timerMaster != null) {
         timerMaster.removeTimerWithLease(timerState);
      }

   }

   void addTimerWithLease(TimerState timerState) {
      if (this.timersWithLease != null) {
         synchronized(this.timersWithLease) {
            this.timersWithLease.add(timerState);
         }

         if (DEBUG) {
            debug("addTimerWithLease(" + timerState.getId() + ")");
         }
      }

   }

   void removeTimerWithLease(TimerState timerState) {
      if (this.timersWithLease != null) {
         synchronized(this.timersWithLease) {
            this.timersWithLease.remove(timerState);
         }

         if (DEBUG) {
            debug("removeTimerWithLease(" + timerState.getId() + ")");
         }
      }

   }

   public static synchronized void ensureStartedForGlobalRG(TimerManager timerManager) {
      String domainID = "weblogic.timers.defaultDomain-RG";
      TimerMaster timerMaster = (TimerMaster)timerMasters.get(domainID);
      if (timerMaster == null || timerMaster.isEmptyGlobalRGTimerManagersList()) {
         try {
            timerMaster = initialize(domainID);
            TimerExecutor.initialize(timerMaster, (String)null);
            if (DEBUG) {
               debug("started TimerMaster for global domain RG in ensureStartedForGlobalRG()");
            }
         } catch (TimerException var4) {
            ClusterExtensionLogger.logTimerMasterNotStarted("Global Domain Resource Group", var4);
         }
      }

      if (timerMaster != null) {
         timerMaster.addGlobalRGTimerManager(timerManager);
      }

   }

   public static synchronized void stopGlobalRGIfNoMoreUsers(TimerManager timerManager) {
      String domainID = "weblogic.timers.defaultDomain-RG";
      TimerMaster timerMaster = (TimerMaster)timerMasters.get(domainID);
      if (timerMaster != null && timerMaster.removeGlobalRGTimerManager(timerManager)) {
         if (DEBUG) {
            debug("stopped TimerMaster for global domain RG in stopGlobalRGIfNoMoreUsers()");
         }

         shutdownPartition("weblogic.timers.defaultDomain-RG");
      }

   }

   private synchronized void addGlobalRGTimerManager(TimerManager timerManager) {
      this.globalRGTimerManagers.add(timerManager);
   }

   private synchronized boolean removeGlobalRGTimerManager(TimerManager timerManager) {
      if (this.globalRGTimerManagers != null && !this.globalRGTimerManagers.isEmpty()) {
         this.globalRGTimerManagers.remove(timerManager);
         return this.globalRGTimerManagers.isEmpty();
      } else {
         return false;
      }
   }

   private synchronized boolean isEmptyGlobalRGTimerManagersList() {
      return this.globalRGTimerManagers != null ? this.globalRGTimerManagers.isEmpty() : false;
   }

   private synchronized void start(String domainID) throws TimerException {
      this.timerBasis = TimerBasisAccess.getTimerBasis(domainID);

      try {
         Environment env = new Environment();
         env.setReplicateBindings(false);
         env.setCreateIntermediateContexts(true);
         this.context = env.getInitialContext();

         try {
            this.context.bind(this.jndiName, this);
         } catch (NamingException var4) {
            var4.printStackTrace();
            throw new TimerException("Unable to bind TimerMaster");
         }

         getLeaseManager();
      } catch (NamingException var5) {
         throw new TimerException("Failed to create initial context");
      }
   }

   static synchronized void shutdownPartition(String domainID) {
      TimerMaster timerMaster = (TimerMaster)timerMasters.get(domainID);
      if (timerMaster != null) {
         timerMaster.shutdown();
      }

      TimerBasisAccess.removeTimerBasis(domainID);
   }

   private synchronized void shutdown() {
      this.cancelExecutorTimer();
      this.timerBasis = null;

      try {
         Environment env = new Environment();
         env.setReplicateBindings(false);
         this.context = env.getInitialContext();

         try {
            this.context.unbind(this.jndiName);
         } catch (NamingException var6) {
            if (DEBUG) {
               debug("Unable to unbind TimerMaster " + this.leaseName + " during shutdown: " + var6);
            }
         }
      } catch (NamingException var7) {
         if (DEBUG) {
            debug("Failed to create initial context during shutdown of TimerMaster " + this.leaseName + ":" + var7);
         }
      }

      if (this.timersWithLease != null && leasingService != null) {
         synchronized(this.timersWithLease) {
            TimerState timerState;
            for(Iterator var2 = this.timersWithLease.iterator(); var2.hasNext(); timerState.releaseLease()) {
               timerState = (TimerState)var2.next();
               if (DEBUG) {
                  debug("shutdown releasing lease for " + timerState.getId());
               }
            }

            this.timersWithLease.clear();
         }
      }

   }

   static synchronized Leasing getLeaseManager() throws NamingException {
      if (leasingService != null) {
         return leasingService;
      } else {
         LeasingFactory leasingFactory = Locator.locateService();

         try {
            leasingService = leasingFactory.findOrCreateTimerLeasingService();
         } catch (LeasingException var4) {
            Throwable leasingExceptionCause = var4.getCause();
            NamingException ne;
            if (leasingExceptionCause instanceof NamingException) {
               ne = (NamingException)leasingExceptionCause;
            } else {
               ne = new NamingException();
               ne.setRootCause(leasingExceptionCause);
            }

            throw ne;
         }

         return leasingService;
      }
   }

   private void setupRegistration() {
      this.status = 1;
      SingletonServicesManager ssm = (SingletonServicesManager)GlobalServiceLocator.getServiceLocator().getService(SingletonServicesManager.class, new Annotation[0]);
      ssm.add(this.getName(), this);
   }

   public synchronized void timerExpired(Timer timer) {
      if (this.status != 2) {
         throw new AssertionError("TimerMaster executing in " + this.status);
      } else if (this.timerBasis != null) {
         try {
            if (DEBUG) {
               debug("we are the master of lease. get ready timers");
            }

            List nextTimers = this.timerBasis.getReadyTimers(60);
            Iterator i = nextTimers.listIterator();

            String timerName;
            while(i.hasNext()) {
               timerName = (String)i.next();
               if (!this.readyTimers.contains(timerName)) {
                  this.readyTimers.add(timerName);
               }
            }

            if (DEBUG) {
               debug("total number of ready timers = " + this.readyTimers.size());
            }

            i = this.readyTimers.listIterator();

            while(i.hasNext()) {
               timerName = (String)i.next();

               try {
                  if (leasingService.findOwner(timerName) != null) {
                     if (DEBUG) {
                        debug("timer already has a owner:" + timerName);
                     }

                     i.remove();
                  } else if (DEBUG) {
                     debug("timer is ready:" + timerName);
                  }
               } catch (LeasingException var6) {
               }
            }

            this.numServersCalledIn = 0;
         } catch (TimerException var7) {
         }

      }
   }

   private static void debug(String s) {
      ClusterLogger.logDebug("[TimerMaster] " + s);
   }

   public synchronized void activate() {
      if (this.status != 1) {
         if (DEBUG) {
            debug("Trying to activate TimerMaster lease '" + this.getName() + "' with status " + this.status);
         }

      } else {
         this.status = 2;
         if (DEBUG) {
            debug("Obtained lease NOTIFICATION on " + this.getName());
         }

         this.readyTimers = new ArrayList();
         this.numServersCalledIn = 0;
         this.timer = TimerManagerFactory.getTimerManagerFactory().getTimerManager("weblogic.scheduler.TimerMaster", "weblogic.kernel.System").schedule(this, 0L, 30000L);
         this.registerWithSingletonMonitorLeaseManager();
      }
   }

   public synchronized void deactivate() {
      if (this.status == 2) {
         this.timer.cancel();
         if (DEBUG) {
            debug("Lease LOST [" + this.leaseName + "] ");
         }

         this.status = 1;
      }
   }

   public synchronized List getReadyTimers(Set unavailableTimers) throws RemoteException, TimerException {
      if (this.status != 2) {
         throw new TimerException("Attempt to get ready timers from server that is not master");
      } else {
         ClusterServices clusterServices = (ClusterServices)GlobalServiceLocator.getServiceLocator().getService(ClusterServices.class, new Annotation[0]);
         int numServersPending = clusterServices.getRemoteMembers().size() + 1 - this.numServersCalledIn;
         int maxTimersToSend = 1;
         if (numServersPending < 1) {
            numServersPending = 1;
         }

         if (numServersPending < this.readyTimers.size()) {
            maxTimersToSend = this.readyTimers.size() / numServersPending;
         }

         int i = 0;
         ArrayList result = new ArrayList();
         if (DEBUG) {
            debug("getReadyTimers size=" + this.readyTimers.size() + ", numServersCalledIn=" + this.numServersCalledIn + ", numServerToCall=" + numServersPending + ", timers to send=" + maxTimersToSend);
         }

         for(Iterator itr = this.readyTimers.iterator(); i >= 0 && i < maxTimersToSend && itr.hasNext(); ++i) {
            Object timer = itr.next();
            if (!unavailableTimers.contains(timer)) {
               result.add(timer);
               itr.remove();
               if (DEBUG) {
                  debug("Sending ready timer " + timer + " to TimerExecutor");
               }
            } else if (DEBUG) {
               debug("Skipping to send ready timer " + timer + " to TimerExecutor");
            }
         }

         ++this.numServersCalledIn;
         return result;
      }
   }

   String getName() {
      return this.leaseName;
   }

   String getJndiName() {
      return this.jndiName;
   }

   TimerBasis getTimerBasis() {
      return this.timerBasis;
   }

   void setExecutorTimer(Timer executorTimer) {
      this.executorTimer = executorTimer;
   }

   void cancelExecutorTimer() {
      if (this.executorTimer != null) {
         this.executorTimer.cancel();
      }

   }

   private void registerWithSingletonMonitorLeaseManager() {
      Leasing leaseService = this.findOrCreateLeasingService("service");
      leaseService.addLeaseLostListener(this);
   }

   private void unregisterWithSingletonMonitorLeaseManager() {
      Leasing leaseService = this.findOrCreateLeasingService("service");
      leaseService.removeLeaseLostListener(this);
   }

   public void onRelease() {
      if (this.status == 2) {
         if (DEBUG) {
            debug(this.leaseName + " Got a callback for LeaseLost.");
         }

         this.deactivate();
         this.unregisterWithSingletonMonitorLeaseManager();
      }
   }

   Leasing findOrCreateLeasingService(String leaseTypeName) {
      LeasingFactory factory = Locator.locateService();
      return factory.findOrCreateLeasingService(leaseTypeName);
   }
}
