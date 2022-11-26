package weblogic.ejb.container.internal;

import java.util.Date;
import java.util.Random;
import javax.jms.JMSException;
import javax.transaction.SystemException;
import weblogic.connector.external.EndpointActivationException;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.deployer.MDBService;
import weblogic.ejb.container.deployer.MDBServiceActivator;
import weblogic.ejb.container.interfaces.MessageDrivenBeanInfo;
import weblogic.ejb.container.interfaces.MessageDrivenManagerIntf;
import weblogic.ejb.container.monitoring.MessageDrivenEJBRuntimeMBeanImpl;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.management.runtime.MessageDrivenEJBRuntimeMBean;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.Debug;
import weblogic.utils.StackTraceUtilsClient;
import weblogic.work.WorkManagerFactory;

public abstract class MDConnectionManager implements TimerListener {
   protected static final DebugLogger debugLogger;
   protected static final int STATE_DISCONNECTED = 1;
   protected static final int STATE_CONNECTED = 2;
   protected static final int STATE_UNDEPLOYING = 3;
   protected static final int STATE_UNDEPLOYED = 4;
   protected static final int STATE_SUSPENDED = 5;
   protected static final int STATE_CONNECTION_ERROR = 6;
   protected static final int STATE_SUSPENDED_CONNECTION_ERROR = 7;
   private static final int RECONNECTION_ERROR_INTERVAL = 600000;
   private static final int EXCEPTION_LOG_THRESHOLD = 10;
   protected final MessageDrivenBeanInfo info;
   protected final MessageDrivenEJBRuntimeMBeanImpl runtimeMBean;
   protected final TimerManager timerManager;
   protected final MessageDrivenManagerIntf mgr;
   protected final Object stateLock = new Object();
   protected int state = 1;
   protected int reconnectionCount = 0;
   private long lastReconnectionFailureTime = 0L;
   private Exception lastReconnectionFailureException = new Exception("init exception");
   protected boolean scheduleResume;
   private int errorRepeatCount;
   private int deliveryFailureCount;
   private static final Throwable INIT_LASTDELIVERYFAILUREEXCEPTION;
   private Throwable lastDeliveryFailureException;
   private int suspendInterval;
   private final Object scheduleResumeLock;
   protected Timer timer;

   MDConnectionManager(MessageDrivenBeanInfo i, MessageDrivenEJBRuntimeMBean mb) {
      this.lastDeliveryFailureException = INIT_LASTDELIVERYFAILUREEXCEPTION;
      this.scheduleResumeLock = new Object();
      this.info = i;
      this.runtimeMBean = (MessageDrivenEJBRuntimeMBeanImpl)mb;
      this.runtimeMBean.setMDConnManager(this);
      this.timerManager = TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager();
      this.mgr = (MessageDrivenManagerIntf)this.info.getBeanManager();
      this.initDeliveryFailureParams();
   }

   public abstract boolean suspend(boolean var1);

   public abstract boolean resume(boolean var1);

   public boolean resume(boolean byUser, boolean fromContainerInternal) {
      return this.resume(byUser);
   }

   protected abstract void connect() throws WLDeploymentException, JMSException, SystemException, EndpointActivationException;

   protected abstract void disconnect(boolean var1) throws JMSException, EndpointActivationException;

   protected String getDestinationJndi() {
      return this.mgr.getDestinationName();
   }

   protected abstract void logException(Exception var1);

   public void timerExpired(Timer t) {
      synchronized(this.scheduleResumeLock) {
         if (this.scheduleResume) {
            if (this.timer != null) {
               this.timer.cancel();
               this.timer = null;
            }

            this.resume(false, true);
            this.scheduleResume = false;
            this.errorRepeatCount = 0;
            return;
         }
      }

      if (debugLogger.isDebugEnabled()) {
         this.debug("Trying to reconnect to: " + this.getDestinationJndi());
      }

      try {
         if (debugLogger.isDebugEnabled()) {
            this.debugState();
         }

         if (this.getState() == 6) {
            try {
               this.disconnect(false);
            } catch (Exception var8) {
               if (debugLogger.isDebugEnabled()) {
                  this.debug("Exception disconnecting in timerExpired method: " + var8);
               }
            }
         }

         if (this.getState() == 1) {
            this.connect();
         }

         if (debugLogger.isDebugEnabled()) {
            this.debugState();
         }

         MDBService service = MDBServiceActivator.MDBServiceLocator.getMDBService();
         if (service != null) {
            service.addDeployed(this, this.info);
         }
      } catch (Exception var9) {
         if (this.getState() != 3 && this.getState() != 4) {
            this.runtimeMBean.setLastException(var9);
            if (!var9.toString().equals(this.lastReconnectionFailureException.toString()) || this.lastReconnectionFailureTime + 600000L <= System.currentTimeMillis()) {
               this.lastReconnectionFailureTime = System.currentTimeMillis();
               this.lastReconnectionFailureException = var9;
               EJBLogger.logMDBReconnectInfo(this.info.getEJBName(), this.getDestinationJndi(), this.reconnectionCount, this.info.getJMSPollingIntervalSeconds(), 600L);
               this.logException(var9);
            }
         } else {
            EJBLogger.logErrorInConnectWhileConnectionPollingCancelled(this.info.getEJBName(), this.getDestinationJndi(), var9);
         }
      }

      synchronized(this) {
         synchronized(this.stateLock) {
            if (this.state != 1 && this.state != 6 && this.timer != null) {
               this.timer.cancel();
               this.timer = null;
            }
         }
      }

      if (debugLogger.isDebugEnabled()) {
         this.debug("Connect attempt for: " + this.getDestinationJndi() + " was: " + (this.getState() != 2 && this.getState() != 5 ? "unsuccessful" : "Successful"));
      }

   }

   public synchronized void startConnectionPolling() throws WLDeploymentException {
      if (this.timer == null) {
         if (this.state == 4) {
            this.setState(1);
         }

         if (debugLogger.isDebugEnabled()) {
            Debug.assertion(this.getState() == 1);
         }

         int delay = this.getRandomInitialConnectDelay();
         if (delay >= 5) {
            if (!this.info.getIsInactive()) {
               if (debugLogger.isDebugEnabled()) {
                  this.debugState();
               }

               synchronized(this.stateLock) {
                  if (this.state != 1 && this.state != 6) {
                     return;
                  }
               }

               this.timer = this.timerManager.scheduleAtFixedRate(this, (long)delay, (long)(this.info.getJMSPollingIntervalSeconds() * 1000));
               if (debugLogger.isDebugEnabled()) {
                  this.debug("Connecting scheduled at offset " + delay);
               }
            }
         } else {
            try {
               if (debugLogger.isDebugEnabled()) {
                  this.debugState();
               }

               this.connect();
               if (debugLogger.isDebugEnabled()) {
                  this.debugState();
               }

               MDBService service = MDBServiceActivator.MDBServiceLocator.getMDBService();
               if (service != null) {
                  service.addDeployed(this, this.info);
               }

               assert this.getState() == 2 || this.getState() == 5;
            } catch (Exception var5) {
               if (var5 instanceof EndpointActivationException && !((EndpointActivationException)var5).isChangeable()) {
                  throw new WLDeploymentException(var5.getMessage() + StackTraceUtilsClient.throwable2StackTrace(var5));
               }

               this.runtimeMBean.setLastException(var5);
               this.logException(var5);
            }

            if (debugLogger.isDebugEnabled()) {
               this.debugState();
            }

            if (this.getState() != 2 && this.getState() != 5) {
               this.scheduleReconnection();
            }
         }

      }
   }

   public void cancelConnectionPolling() {
      MDBService service = MDBServiceActivator.MDBServiceLocator.getMDBService();
      if (service != null) {
         service.removeDeployed(this);
      }

      synchronized(this) {
         this.setState(3);
         if (debugLogger.isDebugEnabled()) {
            this.debugWithState("cancel polling was performed");
         }

         try {
            this.disconnect(false);
         } catch (Exception var5) {
            this.runtimeMBean.setLastException(var5);
            if (debugLogger.isDebugEnabled()) {
               this.debug("Exception disconnecting " + var5);
            }
         }

         if (this.timer != null) {
            this.timer.cancel();
            this.timer = null;
         }

      }
   }

   public void deleteDurableSubscriber(String clientId) {
   }

   private int getRandomInitialConnectDelay() {
      if (!this.info.getMinimizeAQSessions()) {
         return -1;
      } else if (this.info.getMdbDestinationPollIntervalMillis() <= 5000) {
         return -1;
      } else {
         Random initConnectDelayRandom = new Random();
         return initConnectDelayRandom.nextInt(this.info.getMdbDestinationPollIntervalMillis());
      }
   }

   protected synchronized void scheduleReconnection() {
      if (!this.info.getIsInactive()) {
         if (this.scheduleResume) {
            this.initDeliveryFailureParams();
            if (this.timer != null) {
               this.timer.cancel();
               this.timer = null;
            }
         }

         if (this.timer == null) {
            if (debugLogger.isDebugEnabled()) {
               this.debugState();
            }

            synchronized(this.stateLock) {
               if (this.state != 1 && this.state != 6) {
                  return;
               }
            }

            int pollMillis = this.info.getJMSPollingIntervalSeconds() * 1000;
            this.timer = this.timerManager.scheduleAtFixedRate(this, (long)pollMillis, (long)pollMillis);
            if (debugLogger.isDebugEnabled()) {
               this.debug("Reconnection is scheduled at interval of every " + pollMillis);
            }

            this.reconnectionCount = 1;
            this.lastReconnectionFailureTime = System.currentTimeMillis();
            this.lastReconnectionFailureException = new Exception("init exception");
         }
      }
   }

   protected void initDeliveryFailureParams() {
      this.scheduleResume = false;
      this.errorRepeatCount = 0;
      this.suspendInterval = this.info.getInitSuspendSeconds() * 1000;
      if (this.info.getInitSuspendSeconds() == 0) {
         this.suspendInterval = 5000;
      }

      if (this.info.getMaxSuspendSeconds() == 0) {
         this.suspendInterval = 0;
      }

      this.deliveryFailureCount = 0;
      this.lastDeliveryFailureException = INIT_LASTDELIVERYFAILUREEXCEPTION;
   }

   protected boolean isPrintErrorMessage(Throwable th) {
      ++this.deliveryFailureCount;
      this.runtimeMBean.setLastException(th);
      if (th.toString().equals(this.lastDeliveryFailureException.toString()) && this.suspendInterval != 0) {
         ++this.errorRepeatCount;
         if (this.errorRepeatCount < 10) {
            return false;
         }

         if (this.getState() == 5) {
            if (debugLogger.isDebugEnabled()) {
               this.debugWithState(this + " has already been suspended");
            }
         } else {
            WorkManagerFactory.getInstance().getSystem().schedule(new SuspendThread(this));
         }
      } else {
         this.errorRepeatCount = 1;
         this.lastDeliveryFailureException = th;
      }

      return true;
   }

   protected void callSuspend() {
      if (this.getState() != 5) {
         synchronized(this.scheduleResumeLock) {
            if (this.timer == null) {
               this.suspend(false);
               synchronized(this) {
                  if (this.timer == null) {
                     this.scheduleResume = true;
                     if (this.info.getMaxSuspendSeconds() == 0) {
                        this.suspendInterval = 0;
                     }

                     this.timer = this.timerManager.schedule(this, (long)this.suspendInterval);
                  }
               }

               EJBLogger.logMDBRedeliveryInfo(this.info.getEJBName(), this.deliveryFailureCount, (long)(this.suspendInterval / 1000));
               this.runtimeMBean.setMDBStatus(this.runtimeMBean.statusAsStringInStartCase(3) + " at " + new Date() + " by the EJB container, resume is scheduled at " + new Date(System.currentTimeMillis() + (long)this.suspendInterval));
               this.suspendInterval *= 2;
               if (this.suspendInterval > this.info.getMaxSuspendSeconds() * 1000) {
                  this.suspendInterval = this.info.getMaxSuspendSeconds() * 1000;
               }

            }
         }
      }
   }

   public boolean activate() throws WLDeploymentException {
      if (!this.info.getIsInactive()) {
         return false;
      } else {
         try {
            this.info.processInactive(false);
            return true;
         } catch (BeanUpdateFailedException var2) {
            throw new WLDeploymentException(var2.getMessage());
         }
      }
   }

   protected final int getState() {
      synchronized(this.stateLock) {
         return this.state;
      }
   }

   protected final int setState(int newState) {
      synchronized(this.stateLock) {
         int oldState = this.state;
         this.state = newState;
         return oldState;
      }
   }

   protected final void debugWithState(String msg) {
      this.debug("MDB " + this.info.getEJBName() + ": " + msg + ". State = " + this.getCurrentStateName());
   }

   protected final void debugState() {
      this.debug("MDB " + this.info.getEJBName() + ": State = " + this.getCurrentStateName());
   }

   protected ManagedInvocationContext setCIC() {
      return this.info.setCIC();
   }

   private String getCurrentStateName() {
      return this.getStateNameById(this.getState());
   }

   protected String getStateNameById(int stateId) {
      switch (stateId) {
         case 1:
            return "DISCONNECTED";
         case 2:
            return "CONNECTED";
         case 3:
            return "UNDEPLOYING";
         case 4:
            return "UNDEPLOYED";
         case 5:
            return "SUSPENDED";
         case 6:
            return "CONNECTION_ERROR";
         case 7:
            return "SUSPENDED_CONNECTION_ERROR";
         default:
            return "<unknown>";
      }
   }

   private void debug(String s) {
      debugLogger.debug("[MDConnectionManager] " + s);
   }

   static {
      debugLogger = EJBDebugService.mdbConnectionLogger;
      INIT_LASTDELIVERYFAILUREEXCEPTION = new Throwable("init exception");
   }

   static final class SuspendThread implements Runnable {
      private final MDConnectionManager mdConnectionManager;

      SuspendThread(MDConnectionManager mdConnectionManager) {
         this.mdConnectionManager = mdConnectionManager;
      }

      public void run() {
         ManagedInvocationContext mic = this.mdConnectionManager.setCIC();
         Throwable var2 = null;

         try {
            this.mdConnectionManager.callSuspend();
         } catch (Throwable var11) {
            var2 = var11;
            throw var11;
         } finally {
            if (mic != null) {
               if (var2 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var10) {
                     var2.addSuppressed(var10);
                  }
               } else {
                  mic.close();
               }
            }

         }

      }
   }
}
