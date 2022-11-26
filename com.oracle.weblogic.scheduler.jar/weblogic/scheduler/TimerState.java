package weblogic.scheduler;

import java.io.Serializable;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import javax.naming.NamingException;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import weblogic.cluster.ClusterExtensionLogger;
import weblogic.cluster.ClusterLogger;
import weblogic.cluster.singleton.LeaseLostListener;
import weblogic.cluster.singleton.Leasing;
import weblogic.cluster.singleton.LeasingException;
import weblogic.rmi.facades.RmiSecurityFacade;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityManager;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.ScheduleExpression;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManagerFactory;
import weblogic.transaction.TxHelper;
import weblogic.utils.Debug;
import weblogic.utils.StackTraceUtils;
import weblogic.work.WorkAdapter;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public class TimerState implements NakedTimerListener, LeaseLostListener {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final boolean DEBUG = Debug.getCategory("weblogic.JobScheduler").isEnabled();
   private static final String TIMER_MANAGER = "weblogic.scheduler.TimerState";
   private final String id;
   private final TimerListener to;
   private final long duration;
   private final long interval;
   private final long timeout;
   private final AuthenticatedSubject user;
   private final TimerBasis timerBasis;
   private final String domainID;
   private int retryAttempt = 0;
   private int delay = 0;
   private boolean timerLeaseLost = false;

   public TimerState(TimerBasis timerBasis, String id, TimerListener to, long timeout, long interval, AuthenticatedSubject user, String domainID) {
      this.timerBasis = timerBasis;
      this.id = id;
      this.to = to;
      this.timeout = timeout;
      this.duration = timeout - System.currentTimeMillis();
      this.interval = interval;
      if (user == null) {
         this.user = RmiSecurityFacade.getAnonymousSubject();
      } else {
         this.user = user;
      }

      this.domainID = domainID;
   }

   Leasing getLeasing() {
      try {
         return TimerMaster.getLeaseManager();
      } catch (NamingException var2) {
         var2.printStackTrace();
         return null;
      }
   }

   public String getId() {
      return this.id;
   }

   public TimerListener getTimedObject() {
      return this.to;
   }

   public long getDuration() {
      return this.duration;
   }

   public long getTimeout() {
      return this.timeout;
   }

   public long getInterval() {
      return this.interval;
   }

   public Serializable getInfo() {
      return null;
   }

   public AuthenticatedSubject getUser() {
      return this.user;
   }

   public boolean isCalendarTimer() {
      return false;
   }

   public ScheduleExpression getSchedule() {
      return null;
   }

   String getDomainID() {
      return this.domainID;
   }

   public void fireWhenReady() {
      Leasing leaseMgr = this.getLeasing();

      assert leaseMgr != null;

      try {
         if (!leaseMgr.tryAcquire(this.id)) {
            if (DEBUG) {
               debug("failed to claim ownership of timer " + this.id);
            }

            return;
         }

         TimerMaster.addTimerWithLeaseForDomain(this.domainID, this);
         if (DEBUG) {
            debug("claimed ownership of timer " + this.id);
         }
      } catch (LeasingException var3) {
         if (DEBUG) {
            debug("failed to claim ownership of timer " + this.id);
         }

         return;
      }

      leaseMgr.addLeaseLostListener(this);
      if (this.duration <= 0L) {
         if (DEBUG) {
            debug("timer will execute immediately " + this.id);
         }

         this.enqueueTimer(this);
      } else {
         if (DEBUG) {
            debug("execute timer after " + this.duration + "ms");
         }

         TimerManagerFactory.getTimerManagerFactory().getTimerManager("weblogic.scheduler.TimerState", "weblogic.kernel.System").schedule(this, this.duration);
      }

   }

   private void fireRetryWhenReady(long delay) throws LeasingException {
      Leasing leaseMgr = this.getLeasing();

      assert leaseMgr != null;

      try {
         if (!leaseMgr.tryAcquire(this.id)) {
            throw new LeasingException("Failed to clain ownership of retry timer");
         }
      } catch (LeasingException var5) {
         throw var5;
      }

      if (DEBUG) {
         debug("execute retry timer after " + delay + "ms");
      }

      TimerManagerFactory.getTimerManagerFactory().getTimerManager("weblogic.scheduler.TimerState", "weblogic.kernel.System").schedule(this, delay);
   }

   public void timerExpired(Timer timer) {
      this.enqueueTimer(this);
   }

   private void enqueueTimer(final TimerState state) {
      WorkAdapter req = new WorkAdapter() {
         public String toString() {
            return "Execute job " + state.getId() + " for Job Scheduler";
         }

         public Runnable cancel(String reason) {
            if (TimerState.DEBUG) {
               TimerState.debug("timer execution is cancelled because of '" + reason + "'");
            }

            return new Runnable() {
               public void run() {
               }
            };
         }

         public void run() {
            boolean success = false;
            boolean requiresTransaction = false;
            if (TimerState.this.isLeaseOwner()) {
               if (state.getTimedObject() instanceof TransactionalTimerListener) {
                  requiresTransaction = true;
                  int txTimeout = ((TransactionalTimerListener)state.getTimedObject()).getTransactionTimeoutSeconds();

                  try {
                     TimerState.this.preInvoke(txTimeout);
                  } catch (SystemException var13) {
                     if (TimerState.DEBUG) {
                        TimerState.debug("tx begin failed with " + StackTraceUtils.throwable2StackTrace(var13));
                     }

                     return;
                  } catch (NotSupportedException var14) {
                     if (TimerState.DEBUG) {
                        TimerState.debug("tx begin failed with " + StackTraceUtils.throwable2StackTrace(var14));
                     }

                     return;
                  }
               }

               try {
                  try {
                     SecurityManager.runAs(TimerState.kernelId, state.getUser(), new PrivilegedExceptionAction() {
                        public Object run() throws Exception {
                           state.getTimedObject().timerExpired(new TimerImpl(state.getId(), TimerState.this.timerBasis));
                           return null;
                        }
                     });
                  } catch (PrivilegedActionException var12) {
                     throw new AssertionError(var12);
                  }

                  try {
                     if (state.shouldCancelTimer()) {
                        TimerState.this.timerBasis.cancelTimer(state.getId());
                     } else {
                        if (!TimerState.this.isLeaseOwner()) {
                           return;
                        }

                        if (!requiresTransaction) {
                           state.advanceTimer(TimerState.this.timerBasis);
                        }
                     }

                     success = true;
                     JobSchedulerRuntimeMBeanImpl.getInstance(false).timerExecuted(state.getId(), state.getTimedObject().toString(), state.getInterval());
                  } catch (NoSuchObjectLocalException var15) {
                     throw new AssertionError(var15);
                  } catch (TimerException var16) {
                     throw new AssertionError(var16);
                  }
               } finally {
                  if (requiresTransaction) {
                     if (success) {
                        state.postInvokeSuccess();
                     } else {
                        state.postInvokeFailure();
                     }
                  } else {
                     state.releaseLeaseResources();
                  }

               }
            }
         }
      };
      WorkManager wm = WorkManagerFactory.getInstance().getSystem();
      if (this.to instanceof TimerListenerExtension) {
         TimerListenerExtension listener = (TimerListenerExtension)this.to;
         if (listener.getDispatchPolicy() != null) {
            wm = WorkManagerFactory.getInstance().find(listener.getDispatchPolicy(), listener.getApplicationName(), listener.getModuleName());
         }
      }

      wm.schedule(req);
   }

   void releaseLease() {
      Leasing leaseMgr = null;

      try {
         leaseMgr = this.getLeasing();

         assert leaseMgr != null;

         leaseMgr.removeLeaseLostListener(this);
         leaseMgr.release(this.getId());
      } catch (LeasingException var3) {
         if (DEBUG) {
            debug("caught leasing exception while releasing lease: " + var3);
         }

         if (leaseMgr != null) {
            leaseMgr.removeFromOutStandingLeasesSet(this.getId());
         }
      }

   }

   protected boolean shouldCancelTimer() {
      return this.getInterval() == -1L;
   }

   protected void advanceTimer(TimerBasis basis) throws TimerException, NoSuchObjectLocalException {
      basis.advanceIntervalTimer(this.getId(), this.getTimedObject());
   }

   private boolean isLeaseOwner() {
      try {
         return this.isLeaseLost() ? false : this.getLeasing().tryAcquire(this.id);
      } catch (LeasingException var2) {
         if (DEBUG) {
            debug("failed to claim ownership of timer " + this.id);
         }

         return false;
      }
   }

   private static void debug(String s) {
      ClusterLogger.logDebug("[TimerState] " + s);
   }

   synchronized boolean isLeaseLost() {
      return this.timerLeaseLost;
   }

   public synchronized void onRelease() {
      Leasing leaseMgr = this.getLeasing();

      assert leaseMgr != null;

      leaseMgr.removeLeaseLostListener(this);
      this.timerLeaseLost = true;
   }

   public void preInvoke(int timeout) throws SystemException, NotSupportedException {
      TxHelper.getTransactionManager().begin(timeout);
   }

   public void postInvokeSuccess() {
      try {
         TxHelper.getTransactionManager().commit();
         this.handleTimeoutSuccess();
      } catch (Exception var2) {
         ClusterExtensionLogger.logErrorTransactionForClusteredTimers("commit", "TimerId:" + this.getId() + " " + this.getTimedObject().toString(), var2);
         this.handleTimeoutFailure();
      }

   }

   public void postInvokeFailure() {
      try {
         TxHelper.getTransactionManager().rollback();
      } catch (SystemException var5) {
         ClusterExtensionLogger.logErrorTransactionForClusteredTimers("rollback", "TimerId:" + this.getId() + " " + this.getTimedObject().toString(), var5);
      } finally {
         this.handleTimeoutFailure();
      }

   }

   protected void handleTimeoutSuccess() {
      try {
         if (!this.shouldCancelTimer()) {
            this.advanceTimer(this.timerBasis);
         }
      } catch (NoSuchObjectLocalException var6) {
         throw new AssertionError(var6);
      } catch (TimerException var7) {
         throw new AssertionError(var7);
      } finally {
         this.retryAttempt = 0;
         this.releaseLeaseResources();
      }

   }

   protected void handleTimeoutFailure() {
      try {
         ++this.retryAttempt;
         if (this.retryAttempt >= 10) {
            int previousDelays = this.retryAttempt - 10;
            if (previousDelays > 3) {
               this.delay = 60;
            } else {
               this.delay = 5 * (int)StrictMath.pow(2.0, (double)previousDelays);
            }

            ClusterExtensionLogger.logClusteredTimeoutDelayAutomaticallyApplied(this.id, this.retryAttempt, this.delay, this.getTimedObject().toString());
         }

         this.fireRetryWhenReady((long)this.delay * 1000L);
      } catch (LeasingException var2) {
         this.retryAttempt = 0;
         this.releaseLeaseResources();
         ClusterExtensionLogger.logFailedToFireRetryTimer("TimerId:" + this.getId() + " " + this.getTimedObject().toString(), var2);
      }

   }

   private void releaseLeaseResources() {
      this.timerLeaseLost = false;
      this.releaseLease();
      TimerMaster.removeTimerWithLeaseForDomain(this.getDomainID(), this);
   }
}
