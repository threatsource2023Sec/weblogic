package weblogic.cluster.singleton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.cluster.ClusterLogger;
import weblogic.cluster.singleton.Leasing.LeaseOwnerIdentity;
import weblogic.protocol.LocalServerIdentity;
import weblogic.protocol.ServerIdentity;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.Timer;
import weblogic.timers.TimerManagerFactory;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public class LeaseManager implements Leasing, NakedTimerListener, MigratableServiceConstants {
   private LeasingBasis basis;
   private int heartbeatPeriod;
   private int healthCheckPeriod;
   private int gracePeriod;
   private Map leaseObtainedListeners;
   private Timer timer;
   private String leaseType;
   private ArrayList leaseLostListeners;
   private int missedHeartbeats = 0;
   private static volatile Set outstandingLeasesSet = new HashSet();
   private final Set myLeasesSet = new HashSet();
   private final boolean DEBUG = SingletonServicesDebugLogger.isDebugEnabled();
   public static final String FAILED = "WLS_FAILED_SERVICE";
   public static final String DELIMITER = ".";
   public static final String DIVIDER = "/";
   private boolean warnedAboutLazyTimer = false;
   private long lastTimerTime = 0L;
   private static WorkManager onReleaseWM;

   private static synchronized void createOnReleaseWM() {
      if (onReleaseWM == null) {
         onReleaseWM = WorkManagerFactory.getInstance().findOrCreate("weblogic.cluster.singleton.LeaseManager.onReleaseWM", 1, -1);
      }
   }

   public LeaseManager(LeasingBasis basis, int heartbeatPeriod, int healthCheckPeriod, int gracePeriod, String leaseType) {
      this.basis = basis;
      this.heartbeatPeriod = heartbeatPeriod;
      this.healthCheckPeriod = healthCheckPeriod;
      this.gracePeriod = gracePeriod;
      this.leaseType = leaseType;
      this.leaseObtainedListeners = new HashMap();
      this.leaseLostListeners = new ArrayList();
      this.lastTimerTime = System.currentTimeMillis();
      createOnReleaseWM();
   }

   public boolean tryAcquire(String leaseName) throws LeasingException {
      synchronized(outstandingLeasesSet) {
         if (this.leaseObtainedListeners.containsKey(this.qualifyLeaseName(leaseName))) {
            throw new LeasingException("Previously registered to obtain lease");
         } else {
            boolean var10000;
            try {
               if (this.isCurrentOwner(leaseName)) {
                  var10000 = true;
                  return var10000;
               }

               if (this.basis.acquire(this.qualifyLeaseName(leaseName), getOwnerIdentity(LocalServerIdentity.getIdentity()), this.healthCheckPeriod)) {
                  this.addToOutStandingLeasesSet(leaseName);
                  var10000 = true;
                  return var10000;
               }

               var10000 = false;
            } catch (IOException var5) {
               throw new LeasingException("tryAcquire()", var5);
            }

            return var10000;
         }
      }
   }

   public void acquire(String leaseName, LeaseObtainedListener listener) throws LeasingException {
      synchronized(outstandingLeasesSet) {
         if (this.leaseObtainedListeners.containsKey(this.qualifyLeaseName(leaseName))) {
            throw new LeasingException("Previously registered to obtain lease");
         } else if (this.isCurrentOwner(leaseName)) {
            listener.onAcquire(leaseName);
            if (!outstandingLeasesSet.contains(this.qualifyLeaseName(leaseName))) {
               this.addToOutStandingLeasesSet(leaseName);
            }

         } else {
            try {
               if (this.basis.acquire(this.qualifyLeaseName(leaseName), getOwnerIdentity(LocalServerIdentity.getIdentity()), this.healthCheckPeriod)) {
                  this.addToOutStandingLeasesSet(leaseName);
                  listener.onAcquire(leaseName);
                  return;
               }
            } catch (IOException var6) {
            }

            this.leaseObtainedListeners.put(this.qualifyLeaseName(leaseName), listener);
         }
      }
   }

   public void release(String leaseName) throws LeasingException {
      synchronized(outstandingLeasesSet) {
         if (this.leaseObtainedListeners.containsKey(this.qualifyLeaseName(leaseName))) {
            this.leaseObtainedListeners.remove(this.qualifyLeaseName(leaseName));
         } else {
            try {
               this.basis.release(this.qualifyLeaseName(leaseName), getOwnerIdentity(LocalServerIdentity.getIdentity()));
               this.removeFromOutStandingLeasesSet(leaseName);
               if (this.DEBUG) {
                  p("release " + leaseName + " successful");
               }
            } catch (IOException var5) {
               if (this.DEBUG) {
                  p("release " + leaseName + " failed");
               }

               throw new LeasingException("'" + var5.getMessage() + "'", var5);
            }

         }
      }
   }

   public String findOwner(String leaseName) throws LeasingException {
      try {
         return this.basis.findOwner(this.qualifyLeaseName(leaseName));
      } catch (IOException var3) {
         throw new LeasingException("findOwner()", var3);
      }
   }

   public String findPreviousOwner(String leaseName) throws LeasingException {
      try {
         return this.basis.findPreviousOwner(this.qualifyLeaseName(leaseName));
      } catch (IOException var3) {
         throw new LeasingException("findPreviousOwner()", var3);
      }
   }

   /** @deprecated */
   @Deprecated
   public static String getOwnerIdentity(ServerIdentity id) {
      return LeaseOwnerIdentity.getIdentity(id);
   }

   /** @deprecated */
   @Deprecated
   public static String getServerNameFromOwnerIdentity(String ownerIdentity) {
      return LeaseOwnerIdentity.getServerNameFromIdentity(ownerIdentity);
   }

   private boolean isCurrentOwner(String leaseName) {
      try {
         String currentOwner = this.basis.findOwner(this.qualifyLeaseName(leaseName));
         return currentOwner != null && currentOwner.equals(getOwnerIdentity(LocalServerIdentity.getIdentity()));
      } catch (IOException var3) {
         return false;
      }
   }

   private String qualifyLeaseName(String leaseName) {
      return this.leaseType + "." + leaseName;
   }

   private String dequalifyLeaseName(String qualifiedLeaseName) {
      return qualifiedLeaseName == null ? null : qualifiedLeaseName.substring(qualifiedLeaseName.lastIndexOf(".") + 1, qualifiedLeaseName.length());
   }

   public void addLeaseLostListener(LeaseLostListener listener) {
      synchronized(this.leaseLostListeners) {
         this.leaseLostListeners.add(listener);
      }
   }

   public void removeLeaseLostListener(LeaseLostListener listener) {
      synchronized(this.leaseLostListeners) {
         int i = this.leaseLostListeners.indexOf(listener);
         if (i > -1) {
            this.leaseLostListeners.remove(i);
         }

      }
   }

   public int getGracePeriod() {
      return this.gracePeriod;
   }

   LeasingBasis getLeasingBasis() {
      return this.basis;
   }

   public String[] findExpiredLeases() {
      try {
         String[] leases = this.basis.findExpiredLeases(this.gracePeriod);
         ArrayList validLeases = new ArrayList();

         int counter;
         for(counter = 0; counter < leases.length; ++counter) {
            if (leases[counter].startsWith(this.leaseType)) {
               validLeases.add(leases[counter]);
            }
         }

         leases = new String[validLeases.size()];
         counter = 0;

         for(Iterator iter = validLeases.iterator(); iter.hasNext(); ++counter) {
            leases[counter] = this.dequalifyLeaseName((String)iter.next());
         }

         return leases;
      } catch (IOException var5) {
         return new String[0];
      }
   }

   public void stop() {
      if (this.timer != null) {
         try {
            this.timer.cancel();
            this.basis.release(this.qualifyLeaseName(""), getOwnerIdentity(LocalServerIdentity.getIdentity()));
         } catch (IOException var2) {
         }

      }
   }

   public void start() {
      WorkManager wm = WorkManagerFactory.getInstance().findOrCreate("WorkManagerForAaronLeaseManagerPatch", 2, -1);
      this.timer = TimerManagerFactory.getTimerManagerFactory().getTimerManager("MigratableServerTimerManager", wm).schedule(this, 0L, (long)this.heartbeatPeriod);
      this.lastTimerTime = System.currentTimeMillis();
   }

   public void voidLeases() {
      this.voidLeases(getOwnerIdentity(LocalServerIdentity.getIdentity()));
   }

   public void voidLeases(String owner) {
      if (this.DEBUG) {
         p("Voiding leases for " + owner);
      }

      synchronized(outstandingLeasesSet) {
         try {
            this.basis.renewAllLeases(-this.healthCheckPeriod * 3, owner);
            if (this.DEBUG) {
               p("Successfully voided leases for " + owner);
            }
         } catch (Exception var5) {
            if (this.DEBUG) {
               p("Failed voiding leases for " + owner + ":" + var5);
            }
         }

      }
   }

   public void timerExpired(Timer timer) {
      long currentTime = System.currentTimeMillis();
      if (currentTime - this.lastTimerTime > (long)(this.healthCheckPeriod * 3) && !this.warnedAboutLazyTimer) {
         this.warnedAboutLazyTimer = true;
         ClusterLogger.logDelayedLeaseRenewal((currentTime - this.lastTimerTime) / 1000L);
      }

      this.lastTimerTime = currentTime;
      synchronized(outstandingLeasesSet) {
         Iterator listenerIter = this.leaseObtainedListeners.keySet().iterator();

         while(listenerIter.hasNext()) {
            String leaseName = (String)listenerIter.next();
            LeaseObtainedListener listener = (LeaseObtainedListener)this.leaseObtainedListeners.get(leaseName);

            try {
               if (this.basis.acquire(leaseName, getOwnerIdentity(LocalServerIdentity.getIdentity()), this.healthCheckPeriod)) {
                  this.addToOutStandingLeasesSet(this.dequalifyLeaseName(leaseName));
                  listener.onAcquire(this.dequalifyLeaseName(leaseName));
                  listenerIter.remove();
               }
            } catch (IOException var14) {
               if (this.DEBUG) {
                  p("IOException during lease checking: " + var14);
               }

               listener.onException(var14, leaseName);
            } catch (LeasingException var15) {
               if (this.DEBUG) {
                  p("LeasingException during lease checking: " + var15);
               }

               listener.onException(var15, leaseName);
            }
         }
      }

      try {
         synchronized(outstandingLeasesSet) {
            if (outstandingLeasesSet.size() == 0) {
               return;
            }

            int leases = this.basis.renewLeases(getOwnerIdentity(LocalServerIdentity.getIdentity()), outstandingLeasesSet, this.healthCheckPeriod);
            if (leases != -1 && leases != outstandingLeasesSet.size()) {
               this.warnedAboutLazyTimer = false;
               p("Signal Lease Lost because total leases renewed = " + leases + " .Outstanding leases=" + outstandingLeasesSet);
               this.signalLeaseLost();
               this.removeOutstandingLeases();
            } else if (this.warnedAboutLazyTimer) {
               this.warnedAboutLazyTimer = false;
               ClusterLogger.logLeaseRenewedAfterDelay();
            }
         }

         this.missedHeartbeats = 0;
      } catch (ClusterReformationInProgressException var12) {
         if (this.DEBUG) {
            p("Consensus leasing basis is in reformation. will NOT signal lease lost. exception:" + var12.getMessage());
         }
      } catch (Exception var13) {
         ++this.missedHeartbeats;
         if (this.DEBUG) {
            p("missed heartbeat " + this.missedHeartbeats + ", " + this.basis.getClass().getName() + ": " + var13 + ")");
         }

         if (this.missedHeartbeats * this.heartbeatPeriod >= this.healthCheckPeriod) {
            if (this.DEBUG) {
               p("Signal Lease Lost because of missed heartbeats beyond healthCheckPeriod (" + this.missedHeartbeats + ", " + (this.missedHeartbeats * this.heartbeatPeriod >= this.healthCheckPeriod) + ", " + this.basis.getClass().getName() + ": " + var13 + ")");
            }

            this.signalLeaseLost();
            this.removeOutstandingLeases();
         }
      }

   }

   private void removeOutstandingLeases() {
      Iterator myLeases = this.myLeasesSet.iterator();

      while(myLeases.hasNext()) {
         outstandingLeasesSet.remove(myLeases.next());
      }

      this.myLeasesSet.clear();
   }

   private void signalLeaseLost() {
      if (this.DEBUG) {
         p("signalLeaseLost() called on " + this + " with leaseLostListeners: " + this.leaseLostListeners.size());
      }

      final ArrayList cloneLeaseLostListeners = null;
      synchronized(this.leaseLostListeners) {
         cloneLeaseLostListeners = (ArrayList)this.leaseLostListeners.clone();
      }

      onReleaseWM.schedule(new Runnable() {
         public void run() {
            LeaseLostListener hml;
            for(Iterator listenerIter = cloneLeaseLostListeners.iterator(); listenerIter.hasNext(); hml.onRelease()) {
               hml = (LeaseLostListener)listenerIter.next();
               if (LeaseManager.this.DEBUG) {
                  LeaseManager.p("Notifying " + hml + " of " + LeaseManager.this.missedHeartbeats + " missed heartbeats.");
               }
            }

         }
      });
   }

   private void addToOutStandingLeasesSet(String leaseName) {
      String qualifiedName = this.qualifyLeaseName(leaseName);
      outstandingLeasesSet.add(qualifiedName);
      this.myLeasesSet.add(qualifiedName);
      if (this.DEBUG) {
         p("Added service " + leaseName + " successfully. Total outstanding leases = " + outstandingLeasesSet);
      }

   }

   public void removeFromOutStandingLeasesSet(String leaseName) {
      String qualifiedName = this.qualifyLeaseName(leaseName);
      outstandingLeasesSet.remove(qualifiedName);
      this.myLeasesSet.remove(qualifiedName);
      if (this.DEBUG) {
         p("Removed service " + leaseName + " successfully. Total outstanding leases = " + outstandingLeasesSet);
      }

   }

   private static final void p(String msg) {
      SingletonServicesDebugLogger.debug("LeaseManager: " + msg);
   }
}
