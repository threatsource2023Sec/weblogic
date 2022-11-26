package weblogic.ejb.container.timer;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import javax.ejb.EJBException;
import javax.ejb.NoMoreTimeoutsException;
import javax.ejb.NoSuchEJBException;
import javax.ejb.NoSuchEntityException;
import javax.ejb.NoSuchObjectLocalException;
import javax.ejb.ScheduleExpression;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerHandle;
import javax.transaction.xa.Xid;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.WLTimer;
import weblogic.ejb.WLTimerInfo;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.dd.xml.DDUtils;
import weblogic.ejb.container.interfaces.BeanManager;
import weblogic.ejb.container.interfaces.DeploymentInfo;
import weblogic.ejb.container.interfaces.EntityBeanInfo;
import weblogic.ejb.container.interfaces.TimerIntf;
import weblogic.ejb.container.interfaces.WLEnterpriseBean;
import weblogic.ejb.container.internal.AllowedMethodsHelper;
import weblogic.ejb.container.internal.EJBContextHandler;
import weblogic.ejb.container.internal.EJBContextManager;
import weblogic.ejb.container.internal.EJBRuntimeUtils;
import weblogic.ejb.container.internal.InvocationContextStack;
import weblogic.ejb.container.internal.InvocationWrapper;
import weblogic.ejb.container.internal.MethodDescriptor;
import weblogic.ejb.container.internal.TimerDrivenLocalObject;
import weblogic.ejb.container.internal.TransactionService;
import weblogic.ejb.container.internal.WLEnterpriseBeanUtils;
import weblogic.ejb.container.manager.BaseEntityManager;
import weblogic.ejb.container.manager.MessageDrivenManager;
import weblogic.ejb.container.monitoring.EJBTimerRuntimeMBeanImpl;
import weblogic.ejb.container.persistence.spi.CMPBean;
import weblogic.ejb20.timer.TimerHandleImpl;
import weblogic.logging.Loggable;
import weblogic.store.PersistentHandle;
import weblogic.store.PersistentStoreException;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.internal.ScheduleExpressionWrapper;
import weblogic.transaction.Transaction;

final class TimerImpl extends TimerDrivenLocalObject implements Timer, NakedTimerListener, WLTimer, TimerIntf {
   private static final DebugLogger debugLogger;
   static final int READY_STATE = 1;
   static final int EJB_TIMEOUT_STATE = 2;
   static final int CANCEL_PENDING_STATE = 3;
   static final int CREATE_PENDING_STATE = 4;
   static final int TIMEOUT_PENDING_STATE = 5;
   static final int TIMEOUT_CANCEL_STATE = 6;
   static final int DOES_NOT_EXIST_STATE = 7;
   static final int RETRY_STATE = 8;
   private final EJBTimerManager timerManager;
   private final EJBTimerRuntimeMBeanImpl timerRtMBean;
   private final boolean isTransactional;
   private final int txTimeoutSeconds;
   private final TimerData data;
   private final boolean isPersistent;
   private final ScheduleExpressionWrapper scheduleWrapper;
   private final String methodSignature;
   private weblogic.timers.Timer timer;
   private PersistentHandle handle;
   private int retryAttempt = 0;
   private Date retryExpiration;
   private int state;
   private boolean entityRemovedFromTimeout;
   private Xid pendingTx;
   private boolean isInTimeoutCallback;
   private String lastThrowableText;
   private boolean consecutiveThrowable;

   TimerImpl(EJBTimerManager timerManager, BeanManager beanManager, TimerData data) {
      super.setBeanManager(beanManager);
      super.setBeanInfo(beanManager.getBeanInfo());
      this.timerManager = timerManager;
      this.isTransactional = data.isTransactional();
      this.timerRtMBean = timerManager.getTimerRuntimeMBean();
      this.txTimeoutSeconds = beanManager.getBeanInfo().getTransactionTimeoutSeconds();
      this.isPersistent = true;
      this.data = data;
      ScheduleExpression se = data.getTimerSchedule();
      this.scheduleWrapper = se != null ? ScheduleExpressionWrapper.create(Utils.asWeblogicScheduleExpression(se)) : null;
      this.methodSignature = data.getCallbackMethodString();
   }

   TimerImpl(EJBTimerManager timerManager, BeanManager beanManager, Object pk, boolean isTransactional, ScheduleExpression se, TimerConfig conf, Long timerID, boolean autoCreated, String callbackMethodSignature) {
      super.setBeanManager(beanManager);
      super.setBeanInfo(beanManager.getBeanInfo());
      this.timerManager = timerManager;
      this.isTransactional = isTransactional;
      this.timerRtMBean = timerManager.getTimerRuntimeMBean();
      this.txTimeoutSeconds = beanManager.getBeanInfo().getTransactionTimeoutSeconds();
      this.isPersistent = conf == null || conf.isPersistent();
      this.scheduleWrapper = ScheduleExpressionWrapper.create(Utils.asWeblogicScheduleExpression(se));
      this.methodSignature = callbackMethodSignature;
      long timeout = this.scheduleWrapper.getNextTimeout();
      this.data = creatTimerData(pk, initScheduleExpression(se), conf, timeout == -1L ? null : new Date(timeout), -1L, timerID, (WLTimerInfo)null, autoCreated, isTransactional, callbackMethodSignature);
   }

   TimerImpl(EJBTimerManager timerManager, BeanManager beanManager, Object pk, TimerConfig conf, MethodDescriptor md, Date nextExpiration, long intervalDuration, Long timerID, WLTimerInfo wlTimerInfo) {
      super.setBeanManager(beanManager);
      super.setBeanInfo(beanManager.getBeanInfo());
      this.timerManager = timerManager;
      this.isTransactional = md.requiresTransaction();
      this.timerRtMBean = timerManager.getTimerRuntimeMBean();
      this.txTimeoutSeconds = beanManager.getBeanInfo().getTransactionTimeoutSeconds();
      this.isPersistent = conf == null || conf.isPersistent();
      this.scheduleWrapper = null;
      this.methodSignature = DDUtils.getMethodSignature(md.getMethod());
      this.data = creatTimerData(pk, (ScheduleExpression)null, conf, nextExpiration, intervalDuration, timerID, wlTimerInfo, false, this.isTransactional, this.methodSignature);
   }

   private static TimerData creatTimerData(Object pk, ScheduleExpression schedule, TimerConfig conf, Date nextExpiration, long intervalDuration, Long timerID, WLTimerInfo wlti, boolean autoCreated, boolean isTransactional, String callbackMethodSignature) {
      TimerData data = new TimerData(pk, timerID, isTransactional, schedule, autoCreated, intervalDuration, wlti != null ? wlti.getMaxRetryAttempts() : -1, wlti != null ? wlti.getRetryDelay() : 0L, wlti != null ? wlti.getTimeoutFailureAction() : 2, wlti != null ? wlti.getMaxTimeouts() : 0, callbackMethodSignature);
      if (conf != null) {
         data.setInfo(conf.getInfo());
      }

      data.setNextExpiration(nextExpiration);
      return data;
   }

   private static ScheduleExpression initScheduleExpression(ScheduleExpression se) {
      if (se == null) {
         return null;
      } else {
         ScheduleExpression newSE = new ScheduleExpression();
         newSE.second(se.getSecond() == null ? "0" : se.getSecond());
         newSE.minute(se.getMinute() == null ? "0" : se.getMinute());
         newSE.hour(se.getHour() == null ? "0" : se.getHour());
         newSE.dayOfMonth(se.getDayOfMonth() == null ? "*" : se.getDayOfMonth());
         newSE.dayOfWeek(se.getDayOfWeek() == null ? "*" : se.getDayOfWeek());
         newSE.month(se.getMonth() == null ? "*" : se.getMonth());
         newSE.year(se.getYear() == null ? "*" : se.getYear());
         newSE.timezone(se.getTimezone());
         newSE.end(se.getEnd());
         newSE.start(se.getStart());
         return newSE;
      }
   }

   String getCallbackMethodSignature() {
      return this.methodSignature;
   }

   void setTimer(weblogic.timers.Timer timer) {
      this.timer = timer;
   }

   weblogic.timers.Timer getTimer() {
      return this.timer;
   }

   void setPersistentHandle(PersistentHandle handle) {
      this.handle = handle;
   }

   PersistentHandle getPersistentHandle() {
      return this.handle;
   }

   Object getPK() {
      return this.data.getPk();
   }

   Long getID() {
      return this.data.getTimerId();
   }

   Date getNextExpiration(boolean needThrowException) {
      if (this.retryExpiration != null) {
         return this.retryExpiration;
      } else {
         if (this.__WL_isCalendarTimer()) {
            long nextTimeout = this.scheduleWrapper.getNextTimeout();
            if (needThrowException && nextTimeout == -1L) {
               throw new NoMoreTimeoutsException("No more timeouts for calendar timer " + this);
            }

            this.data.setNextExpiration(nextTimeout == -1L ? null : new Date(nextTimeout));
         }

         return this.data.getNextExpiration();
      }
   }

   boolean isIntervalTimer() {
      if (this.data.getIntervalDuration() != -1L) {
         return true;
      } else {
         return this.scheduleWrapper != null && this.scheduleWrapper.getNextTimeout() != -1L;
      }
   }

   public void timerExpired(weblogic.timers.Timer timer) {
      if (debugLogger.isDebugEnabled()) {
         debug("ejbTimeout for Timer: " + this);
      }

      MethodDescriptor md;
      if (this.isAutoCreated()) {
         md = this.beanInfo.getAutomaticTimerMethodDescriptor(this.getCallbackMethodSignature());
      } else {
         md = this.beanInfo.getEjbTimeoutMethodDescriptor();
      }

      InvocationWrapper wrap;
      try {
         wrap = this.preInvoke(this.data.getPk(), md, new EJBContextHandler(md, new Object[]{timer}));
      } catch (Throwable var26) {
         if (var26 instanceof EJBException) {
            Exception cb = ((EJBException)var26).getCausedByException();
            if (cb instanceof NoSuchEntityException) {
               this.handleNoSuchEntity(false);
               return;
            }
         }

         if (this.shouldLogThrowable(var26)) {
            if (EJBRuntimeUtils.isCausedBy(var26, NoSuchEJBException.class)) {
               EJBLogger.logInvokeEJBTimeoutCallbackOnUndeployedBeanInstance(this.beanInfo.getDisplayName());
            } else {
               EJBLogger.logExceptionBeforeInvokingEJBTimeout(this.beanInfo.getDisplayName(), var26);
            }
         }

         timer.cancel();
         this.handleTimeoutFailure((TimerData)null);
         return;
      }

      synchronized(this) {
         if (!this.ensureReadyState()) {
            if (debugLogger.isDebugEnabled()) {
               debug("Unable to get ready states for Timer: " + this);
            }

            try {
               this.postInvoke(wrap, (Throwable)null);
            } catch (Throwable var27) {
               if (this.shouldLogThrowable(var27)) {
                  EJBLogger.logExceptionBeforeInvokingEJBTimeout(this.beanInfo.getDisplayName(), var27);
               }
            }

            if (!this.isCancelled()) {
               timer.cancel();
               this.handleTimeoutFailure((TimerData)null);
            }
         } else {
            if (debugLogger.isDebugEnabled()) {
               debug("prepare to invoke timeout callback: " + md.getMethod());
            }

            boolean nonTXRetry = true;
            boolean registered = false;

            try {
               wrap.skipLoggingException();
               Object bean = wrap.getBean();
               WLEnterpriseBean wlBean = null;
               if (this.beanInfo.isClientDriven()) {
                  wlBean = (WLEnterpriseBean)bean;
               }

               int oldState = 0;
               if (wlBean != null) {
                  oldState = wlBean.__WL_getMethodState();
               }

               boolean contextPushed = false;
               Throwable ee = null;

               try {
                  if (wlBean != null) {
                     wlBean.__WL_setMethodState(65536);
                  }

                  if (this.beanInfo.isSessionBean() && wlBean != null) {
                     EJBContextManager.pushEjbContext(wlBean.__WL_getEJBContext());
                     InvocationContextStack.push(wrap);
                     contextPushed = true;
                  } else if (!this.beanInfo.isClientDriven()) {
                     EJBContextManager.pushEjbContext(((MessageDrivenManager)this.beanInfo.getBeanManager()).getMessageDrivenContext());
                     InvocationContextStack.push(wrap);
                     contextPushed = true;
                  }

                  this.setState(2);
                  registered = this.timerManager.registerTimerExpirationOperation(this);

                  assert registered == this.isTransactional;

                  this.isInTimeoutCallback = true;
                  this.beanInfo.getBeanManager().invokeTimeoutMethod(bean, this, md.getMethod());
                  this.isInTimeoutCallback = false;
                  if (!this.isTransactional) {
                     this.timerRtMBean.incrementTimeoutCount();
                  }

                  this.data.incrementSuccessfulTimeouts();
                  if (this.data.getSuccessfulTimeouts() == this.data.getMaxTimeouts()) {
                     this.__WL_cancel();
                  }

                  nonTXRetry = false;
                  if (!this.isCancelled()) {
                     if (this.__WL_isCalendarTimer()) {
                        long nextTimeout = this.scheduleWrapper.getNextTimeout();
                        if (nextTimeout != -1L) {
                           this.data.setNextExpiration(new Date(nextTimeout));
                        } else {
                           this.__WL_cancel();
                        }
                     } else if (this.isIntervalTimer()) {
                        if (this.data.getNextExpiration() != null) {
                           this.data.setNextExpiration(new Date(this.data.getNextExpiration().getTime() + this.data.getIntervalDuration()));
                           this.accountForSkippedIntervals();
                           if (!this.isTransactional) {
                              this.timerManager.updatePersistentStoreEntry(this);
                           }
                        }
                     } else if (!this.isTransactional) {
                        this.doCancel();
                        this.timerManager.removePersistentStoreEntry(this);
                     }
                  }
               } catch (Throwable var28) {
                  Throwable t = var28;
                  if (var28 instanceof InvocationTargetException) {
                     t = var28.getCause();
                  }

                  if (debugLogger.isDebugEnabled()) {
                     debug("ejbTimeout failed due to Exception for Timer: " + this, t);
                  }

                  ee = t;
               } finally {
                  if (wlBean != null) {
                     wlBean.__WL_setMethodState(oldState);
                  }

                  if (contextPushed) {
                     InvocationContextStack.pop();
                     EJBContextManager.popEjbContext();
                  }

                  if (!this.isCancelled()) {
                     if (this.isTransactional) {
                        this.setState(5);
                     } else {
                        this.setState(1);
                     }
                  }

               }

               if (this.beanInfo.isEntityBean() && !this.entityRemovedFromTimeout) {
                  try {
                     if (!((EntityBeanInfo)this.beanInfo).getIsBeanManagedPersistence()) {
                        ((CMPBean)bean).__WL_doCheckExistsOnMethod();
                     } else if (ee != null) {
                        ((BaseEntityManager)this.getBeanManager()).ensureDBExistence(this.data.getPk());
                     }
                  } catch (NoSuchEntityException var24) {
                     this.handleNoSuchEntity(true);
                     ee = var24;
                  } catch (Throwable var25) {
                     ee = var25;
                  }
               }

               this.entityRemovedFromTimeout = false;
               this.postInvoke(wrap, (Throwable)ee);

               assert ee == null;
            } catch (Throwable var30) {
               if (this.shouldLogThrowable(var30)) {
                  EJBLogger.logExceptionInvokingEJBTimeout(this.beanInfo.getDisplayName(), var30);
               }
            }

            if (!this.isTransactional && !this.isCancelled()) {
               if (nonTXRetry) {
                  this.handleTimeoutFailure((TimerData)null);
               } else if (this.isIntervalTimer()) {
                  this.handleTimeoutSuccess();
               }
            }

         }
      }
   }

   private void handleNoSuchEntity(boolean timeoutCalled) {
      this.doCancel();
      if (!this.isTransactional || !timeoutCalled) {
         try {
            this.timerManager.removePersistentStoreEntry(this);
         } catch (PersistentStoreException var3) {
            EJBLogger.logErrorRemovingTimer(this.beanInfo.getDisplayName(), var3);
         }
      }

   }

   void accountForSkippedIntervals() {
      assert this.isIntervalTimer();

      long timeout = this.data.getNextExpiration().getTime();
      long current = System.currentTimeMillis();
      if (timeout < current) {
         long skips = (current - timeout) / this.data.getIntervalDuration();
         timeout += skips * this.data.getIntervalDuration();
         this.data.setNextExpiration(new Date(timeout));
      }

   }

   synchronized void handleTimeoutSuccess() {
      assert this.isIntervalTimer();

      if (!this.isCancelled()) {
         this.setState(1);
         this.resetErrorLogging();
         if (this.retryAttempt != 0) {
            this.retryAttempt = 0;
            if (this.timer != null) {
               this.timer.cancel();
            }

            this.retryExpiration = null;
            this.timer = null;
         }

         if (this.timer == null) {
            this.timerManager.scheduleTimer(this);
         }

      }
   }

   synchronized void handleTimeoutFailure(TimerData oldTimerState) {
      if (debugLogger.isDebugEnabled()) {
         debug("Executing handleTimeoutFailure for Timer: " + this);
      }

      if (this.exists()) {
         if (oldTimerState != null) {
            this.data.setNextExpiration(oldTimerState.getNextExpiration());
            this.data.setInfo(oldTimerState.getInfo());
         }

         if (this.isCancelled()) {
            this.timer = null;
         }

         this.setState(1);
         int maxRetries = this.data.getMaxRetryAttempts();
         if (this.retryAttempt >= maxRetries && maxRetries != -1) {
            this.retryAttempt = 0;
            this.retryExpiration = null;
            switch (this.data.getFailureAction()) {
               case 1:
                  this.doCancel();

                  try {
                     this.timerManager.removePersistentStoreEntry(this);
                  } catch (PersistentStoreException var6) {
                     EJBLogger.logErrorRemovingTimer(this.beanInfo.getDisplayName(), var6);
                  }
                  break;
               case 2:
                  this.timerManager.disableTimer(this);
                  break;
               case 3:
                  this.data.setNextExpiration(new Date(this.data.getNextExpiration().getTime() + this.data.getIntervalDuration()));
                  this.accountForSkippedIntervals();
                  this.handleTimeoutSuccess();
                  break;
               default:
                  throw new AssertionError("Unknown action");
            }
         } else {
            this.setState(8);
            int timeoutAttempts;
            if (this.data.getRetryDelay() > 0L) {
               this.retryExpiration = new Date(System.currentTimeMillis() + this.data.getRetryDelay());
               timeoutAttempts = this.retryAttempt + 1;
               EJBLogger.logConfiguredEJBTimeoutDelayApplied(this.getDisplayString(), timeoutAttempts, this.data.getRetryDelay());
            } else {
               timeoutAttempts = this.retryAttempt + 1;
               if (timeoutAttempts >= 10) {
                  int previousDelays = timeoutAttempts - 10;
                  int delay = false;
                  int delay;
                  if (previousDelays > 3) {
                     delay = 60;
                  } else {
                     delay = 5 * (int)StrictMath.pow(2.0, (double)previousDelays);
                  }

                  EJBLogger.logEJBTimeoutDelayAutomaticallyApplied(this.getDisplayString(), timeoutAttempts, delay);
                  this.retryExpiration = new Date(System.currentTimeMillis() + (long)(delay * 1000));
               }
            }

            ++this.retryAttempt;
            this.timerManager.scheduleTimer(this);
         }

      }
   }

   void remove() {
      if (this.state == 2 || this.state == 6) {
         this.entityRemovedFromTimeout = true;
      }

      if (!this.isCancelled()) {
         this.__WL_cancel();
      }

   }

   synchronized void __WL_cancel() {
      if (this.state == 2 && !this.isIntervalTimer() && !this.__WL_isCalendarTimer()) {
         Loggable log = EJBLogger.logSingleExpirationTimerCannotBeCancelledLoggable();
         throw new IllegalStateException(log.getMessage());
      } else {
         if (this.state == 2 && this.isTransactional) {
            this.setState(6);
         } else {
            try {
               if (this.timerManager.registerTimerCancellationOperation(this)) {
                  if (this.state == 2) {
                     this.setState(6);
                  } else {
                     this.setState(3);
                  }
               } else {
                  this.timerManager.removePersistentStoreEntry(this);
                  this.setState(7);
                  this.timerManager.removeTimerFromMaps(this);
                  this.timerRtMBean.incrementCancelledTimerCount();
               }
            } catch (PersistentStoreException var3) {
               if (debugLogger.isDebugEnabled()) {
                  debug("Error cancelling timer", var3);
               }

               EJBException ee = new EJBException(EJBLogger.logErrorCacelTimerLoggable().getMessage(), var3);
               ee.initCause(var3);
               throw ee;
            }
         }

         this.timerManager.cancelTimer(this);
         this.timerManager.removeTimerFromMaps(this);
      }
   }

   public synchronized void cancel() {
      if (debugLogger.isDebugEnabled()) {
         debug("cancel called for Timer: " + this);
      }

      this.checkAllowedInvoke();
      this.__WL_cancel();
   }

   public synchronized TimerHandle getHandle() {
      this.checkAllowedInvoke();
      if (!this.__WL_isPersistent()) {
         throw new IllegalStateException("Cannot invoke getHandle() on a non-persistent timer!");
      } else {
         DeploymentInfo di = this.beanInfo.getDeploymentInfo();
         return new TimerHandleImpl(this, this.data.getTimerId(), di.getApplicationId(), di.getModuleId(), this.beanInfo.getEJBName());
      }
   }

   public synchronized Serializable getInfo() {
      this.checkAllowedInvoke();
      return this.data.getInfo();
   }

   public synchronized Date getNextTimeout() {
      this.checkAllowedInvoke();
      return this.getNextExpiration(this.isInTimeoutCallback);
   }

   public synchronized long getTimeRemaining() {
      this.checkAllowedInvoke();
      Date next = this.getNextExpiration(this.isInTimeoutCallback);
      if (next == null) {
         throw new NoMoreTimeoutsException("No more timeouts for this timer!");
      } else {
         return next.getTime() - System.currentTimeMillis();
      }
   }

   public int getRetryAttemptCount() {
      return this.retryAttempt;
   }

   public int getMaximumRetryAttempts() {
      return this.data.getMaxRetryAttempts();
   }

   public int getCompletedTimeoutCount() {
      return this.data.getSuccessfulTimeouts();
   }

   TimerData getTimerData() {
      return this.data;
   }

   public String toString() {
      return "[EJB Timer] id: " + this.data.getTimerId() + " pk: " + this.data.getPk() + " info: " + this.data.getInfo() + " isAutoCreated: " + this.data.isAutoCreated() + " timer: " + this.timer + " state: " + this.state + " ejb: " + this.beanInfo.getDisplayName() + " Thread: " + Thread.currentThread();
   }

   private String getDisplayString() {
      return "(timer id: " + this.data.getTimerId() + ", info: " + this.data.getInfo() + ", ejb: " + this.beanInfo.getDisplayName() + ")";
   }

   private void checkAllowedInvoke() {
      int beanState = WLEnterpriseBeanUtils.getCurrentState(AllowedMethodsHelper.getBean());
      Loggable log;
      if (beanState == 4) {
         log = EJBLogger.logCannotInvokeTimerObjectsFromEjbCreateLoggable();
         throw new IllegalStateException(log.getMessageText());
      } else {
         if (beanState == 16) {
            if (!(this.getBeanManager() instanceof BaseEntityManager)) {
               log = EJBLogger.logIllegalInvokeTimerMethodInEJbRemoveOrPreDestroyLoggable();
               throw new IllegalStateException(log.getMessageText());
            }
         } else {
            if (beanState == 32) {
               log = EJBLogger.logIllegalInvokeTimerMethodInEJbRAvitvateOrPostActivateLoggable();
               throw new IllegalStateException(log.getMessageText());
            }

            if (beanState == 64) {
               log = EJBLogger.logIllegalInvokeTimerMethodInEjbPassivateOrPrePassivateLoggable();
               throw new IllegalStateException(log.getMessageText());
            }

            if (beanState == 1024) {
               log = EJBLogger.logCannotInvokeTimerObjectsFromAfterCompletionLoggable();
               throw new IllegalStateException(log.getMessageText());
            }

            if (beanState == 1) {
               log = EJBLogger.logIllegalInvokeTimerMethodDuringDILoggable();
               throw new IllegalStateException(log.getMessageText());
            }
         }

         if (!this.ensureReadyState()) {
            if (this.isCancelled()) {
               log = EJBLogger.logIllegalAttemptToUseCancelledTimerLoggable();
               throw new NoSuchObjectLocalException(log.getMessageText());
            } else {
               log = EJBLogger.logInvovationTimeoutLoggable();
               throw new EJBException(log.getMessageText());
            }
         }
      }
   }

   private boolean ensureReadyState() {
      if (this.pendingTx == null) {
         return !this.isCancelled();
      } else {
         Transaction tx = TransactionService.getWeblogicTransaction();
         if (tx != null && this.pendingTx.equals(tx.getXID())) {
            return !this.isCancelled();
         } else {
            long timeoutMS = System.currentTimeMillis() + (long)this.txTimeoutSeconds * 1000L;

            for(boolean timedOut = false; this.pendingTx != null && !timedOut; timedOut = System.currentTimeMillis() > timeoutMS) {
               try {
                  if (debugLogger.isDebugEnabled()) {
                     debug("Waiting.... State: " + this.state);
                  }

                  this.wait((long)this.txTimeoutSeconds * 1000L);
               } catch (InterruptedException var6) {
               }

               if (debugLogger.isDebugEnabled()) {
                  debug("Done Waiting. State: " + this.state);
               }
            }

            return !this.isCancelled() && this.pendingTx == null;
         }
      }
   }

   synchronized void finalizeCancel() {
      this.setState(7);
      this.timerManager.removeTimerFromMaps(this);
   }

   synchronized void undoCancel() {
      if (this.state != 7) {
         if (this.state == 6) {
            this.setState(2);
            this.timer = null;
         } else {
            assert this.state == 3;

            this.setState(1);
            this.timerManager.scheduleTimer(this);
         }

      }
   }

   synchronized void finalizeCreate() {
      if (!this.isCancelled()) {
         this.setState(1);
         this.timerManager.scheduleTimer(this);
      }

   }

   synchronized void undoCreate() {
      this.setState(7);
      this.timerManager.cancelTimer(this);
      this.timerManager.removeTimerFromMaps(this);
   }

   private void doCancel() {
      this.setState(7);
      this.timerManager.cancelTimer(this);
      this.timerManager.removeTimerFromMaps(this);
   }

   boolean isCancelled() {
      return this.state == 7 || this.state == 3 || this.state == 6;
   }

   public boolean exists() {
      return this.state != 7;
   }

   void perhapsMarkCreatePending() {
      if (this.state != 3) {
         this.setState(4);
      }

   }

   void setState(int newState) {
      if (this.state == 4 || this.state == 3 || this.state == 5 || this.state == 6) {
         this.pendingTx = null;
         this.notifyAll();
      }

      this.state = newState;
   }

   boolean isInRetryState() {
      return this.state == 8;
   }

   void setXid(Xid pendingTx) {
      this.pendingTx = pendingTx;
   }

   private boolean shouldLogThrowable(Throwable th) {
      Throwable cause = th.getCause();
      if (cause != null) {
         th = cause;
      }

      String text = th.toString();
      if (text == null) {
         text = th.getClass().getName();
      }

      if (text.equals(this.lastThrowableText)) {
         if (!this.consecutiveThrowable) {
            EJBLogger.logSuppressingEJBTimeoutErrors(this.getDisplayString());
            this.consecutiveThrowable = true;
         }

         return false;
      } else {
         this.lastThrowableText = text;
         this.consecutiveThrowable = false;
         return true;
      }
   }

   private void resetErrorLogging() {
      this.lastThrowableText = null;
      this.consecutiveThrowable = false;
   }

   public boolean isCalendarTimer() {
      this.checkAllowedInvoke();
      return this.__WL_isCalendarTimer();
   }

   public boolean isPersistent() {
      this.checkAllowedInvoke();
      return this.__WL_isPersistent();
   }

   public ScheduleExpression getSchedule() {
      this.checkAllowedInvoke();
      return this.__WL_getSchedule();
   }

   public boolean isAutoCreated() {
      return this.data.isAutoCreated();
   }

   ScheduleExpression __WL_getSchedule() {
      if (this.__WL_isCalendarTimer()) {
         return this.data.getTimerSchedule();
      } else {
         throw new IllegalStateException("Cannot invoke getSchedule() for non calendar-based timers!");
      }
   }

   boolean __WL_isPersistent() {
      return this.isPersistent;
   }

   boolean __WL_isCalendarTimer() {
      return this.data.getTimerSchedule() != null;
   }

   private static void debug(String s) {
      debugLogger.debug("[TimerImpl] " + s);
   }

   private static void debug(String s, Throwable th) {
      debugLogger.debug("[TimerImpl] " + s, th);
   }

   static {
      debugLogger = EJBDebugService.timerLogger;
   }
}
