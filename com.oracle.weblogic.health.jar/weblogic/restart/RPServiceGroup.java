package weblogic.restart;

import java.security.AccessController;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import weblogic.health.HealthFeedbackCallback;
import weblogic.health.HealthLogger;
import weblogic.health.HealthMonitorService;
import weblogic.health.HealthState;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.logging.Loggable;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;

public class RPServiceGroup implements HealthFeedbackCallback, TimerListener {
   private String name;
   private final TreeSet services = new TreeSet(new RPServiceComparator());
   private int secondsBetweenRestarts;
   private int numberOfRestartAttempts;
   private Set startedServices;
   private int triedAttemps = 0;
   private Timer rpTimer = null;
   private Object lock = new Object();
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final ComponentInvocationContextManager cicManager;
   private final ComponentInvocationContext cic;

   public RPServiceGroup(String name, int secondsBetweenRestarts, int numberOfRestartAttempts) {
      this.name = name;
      this.secondsBetweenRestarts = secondsBetweenRestarts;
      if (numberOfRestartAttempts == -1) {
         this.numberOfRestartAttempts = Integer.MAX_VALUE;
      } else {
         this.numberOfRestartAttempts = numberOfRestartAttempts;
      }

      this.cic = cicManager.getCurrentComponentInvocationContext();
   }

   synchronized void addService(RPService newService) {
      this.services.add(newService);
      if (this.services.size() == 1) {
         HealthMonitorService.registerForCallback(this);
         if (RPDebug.isDebugEnabled()) {
            RPDebug.debug("RPServiceGroup <" + this.name + "> addService: Register to HealthMonitorService.");
         }
      }

      if (RPDebug.isDebugEnabled()) {
         RPDebug.debug("RPServiceGroup <" + this.name + "> addService: service " + newService.getName() + " is added, group size=" + this.services.size());
      }

   }

   synchronized boolean removeService(RPService newService) {
      boolean lastService = false;
      this.services.remove(newService);
      if (this.services.size() == 0) {
         lastService = true;
         HealthMonitorService.deregisterForCallback(this);
         this.rpCleanup();
         if (RPDebug.isDebugEnabled()) {
            RPDebug.debug("RPServiceGroup <" + this.name + "> removeService: Deregister from HealthMonitorService.");
         }
      }

      if (RPDebug.isDebugEnabled()) {
         RPDebug.debug("RPServiceGroup <" + this.name + "> removeService: service " + newService.getName() + " is removed. group size=" + this.services.size());
      }

      return lastService;
   }

   public void healthStateChange(HealthState hs) {
      if (hs != null) {
         String subsysName = hs.getSubsystemName();
         if (subsysName != null && subsysName.equals(this.name)) {
            if (hs.getState() == 3 || hs.getState() == 2) {
               if (RPDebug.isDebugEnabled()) {
                  RPDebug.debug("RPServiceGroup <" + this.name + "> healthstatechange, state=" + hs.getState());
               }

               synchronized(this.lock) {
                  if (this.rpTimer == null) {
                     ManagedInvocationContext mic = cicManager.setCurrentComponentInvocationContext(this.cic);
                     Throwable var5 = null;

                     try {
                        long interval = (long)(this.secondsBetweenRestarts * 1000);
                        TimerManager timerManager = TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager();
                        this.rpTimer = timerManager.schedule(this, 0L, interval);
                        if (RPDebug.isDebugEnabled()) {
                           RPDebug.debug("RPServiceGroup <" + this.name + ">, restart scheduled, interval=" + interval);
                        }

                        HealthLogger.logInfoServiceGroupRestartInPlaceStarted(this.name, this.cic.getPartitionName(), this.numberOfRestartAttempts, this.secondsBetweenRestarts);
                     } catch (Throwable var18) {
                        var5 = var18;
                        throw var18;
                     } finally {
                        if (mic != null) {
                           if (var5 != null) {
                              try {
                                 mic.close();
                              } catch (Throwable var17) {
                                 var5.addSuppressed(var17);
                              }
                           } else {
                              mic.close();
                           }
                        }

                     }
                  }

               }
            }
         }
      }
   }

   private void deactivateAll() {
      if (RPDebug.isDebugEnabled()) {
         RPDebug.debug("RPServiceGroup <" + this.name + "> deactivateAll started: service size=" + this.services.size());
      }

      Iterator it = this.services.descendingIterator();

      while(it.hasNext()) {
         RPService service = (RPService)it.next();
         if (RPDebug.isDebugEnabled()) {
            RPDebug.debug("RPServiceGroup <" + this.name + "> deactivateAll: deactive service <" + service.getName() + ">");
         }

         try {
            service.rpDeactivate(this.name);
         } catch (Exception var4) {
            if (RPDebug.isDebugEnabled()) {
               RPDebug.debug("RPServiceGroup <" + this.name + "> deactivateAll: error deactiving service <" + service.getName() + ">", var4);
            }
         }
      }

      if (RPDebug.isDebugEnabled()) {
         RPDebug.debug("RPServiceGroup <" + this.name + "> deactivateAll succeed.");
      }

   }

   public synchronized void timerExpired(Timer timer) {
      if (RPDebug.isDebugEnabled()) {
         RPDebug.debug("timer is triggered under CIC: " + this.cic.getPartitionName());
      }

      boolean rpFailed = false;
      boolean rpGivenup = false;
      Loggable loggable = null;
      if (this.triedAttemps == 0) {
         HealthMonitorService.deregisterForCallback(this);
         this.deactivateAll();
         ++this.triedAttemps;
         this.startedServices = new HashSet();
      } else {
         ++this.triedAttemps;
         if (RPDebug.isDebugEnabled()) {
            RPDebug.debug("RPServiceGroup <" + this.name + "> activateAll started, service size=" + this.services.size());
         }

         Iterator it = this.services.iterator();

         while(it.hasNext()) {
            RPService service = (RPService)it.next();
            if (!this.startedServices.contains(service.getName())) {
               if (RPDebug.isDebugEnabled()) {
                  RPDebug.debug("RPServiceGroup <" + this.name + "> activateAll: activating service " + service.getName());
               }

               try {
                  service.rpActivate(this.name);
                  this.startedServices.add(service.getName());
                  if (RPDebug.isDebugEnabled()) {
                     RPDebug.debug("RPServiceGroup <" + this.name + "> activateAll, service " + service.getName() + " is activated.");
                  }
               } catch (Exception var10) {
                  if (RPDebug.isDebugEnabled()) {
                     RPDebug.debug("Error activing service=" + service.getName(), var10);
                     RPDebug.debug("RPServiceGroup <" + this.name + "> activateAll, restart failed, retry attempts=" + (this.triedAttemps - 1));
                  }

                  try {
                     service.rpDeactivate(this.name);
                  } catch (Exception var9) {
                     if (RPDebug.isDebugEnabled()) {
                        RPDebug.debug("RPServiceGroup <" + this.name + "> activateAll, error deactiving service " + service.getName(), var9);
                     }
                  }

                  if (this.triedAttemps > this.numberOfRestartAttempts) {
                     if (RPDebug.isDebugEnabled()) {
                        RPDebug.debug("RPServiceGroup <" + this.name + "> activateAll, restart failed, no more retry.");
                     }

                     loggable = HealthLogger.logErrorServiceGroupRestartInPlaceFailedWithReasonLoggable(this.name, this.cic.getPartitionName(), this.numberOfRestartAttempts, this.secondsBetweenRestarts * this.numberOfRestartAttempts, var10.getMessage());
                     loggable.log();
                     rpGivenup = true;
                  }

                  rpFailed = true;
                  break;
               }
            }
         }

         if (!rpFailed) {
            if (RPDebug.isDebugEnabled()) {
               RPDebug.debug("RPServiceGroup <" + this.name + "> activateAll succeed.");
            }

            HealthLogger.logInfoServiceGroupRestartInPlaceFinished(this.name, this.cic.getPartitionName());
            HealthMonitorService.registerForCallback(this);
         } else if (rpGivenup && this.cic.isGlobalRuntime()) {
            HealthMonitorService.subsystemFailed(this.name, loggable.getMessageBody());
         }

         if (!rpFailed || rpGivenup) {
            this.rpCleanup();
         }

      }
   }

   private void rpCleanup() {
      synchronized(this.lock) {
         if (this.rpTimer != null && !this.rpTimer.isCancelled()) {
            this.rpTimer.cancel();
            this.rpTimer = null;
         }
      }

      this.startedServices = null;
      this.triedAttemps = 0;
   }

   public synchronized Set getServices() {
      return this.services;
   }

   static {
      cicManager = ComponentInvocationContextManager.getInstance(kernelId);
   }

   private static class RPServiceComparator implements Comparator {
      private RPServiceComparator() {
      }

      public int compare(Object a, Object b) {
         RPService am = (RPService)a;
         RPService bm = (RPService)b;
         if (am.getOrder() < bm.getOrder()) {
            return -1;
         } else {
            return am.getOrder() > bm.getOrder() ? 1 : am.toString().compareTo(bm.toString());
         }
      }

      public boolean equals(Object obj) {
         return obj instanceof RPServiceComparator;
      }

      // $FF: synthetic method
      RPServiceComparator(Object x0) {
         this();
      }
   }
}
