package weblogic.ejb.container.monitoring;

import java.util.concurrent.atomic.AtomicInteger;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.TimerManager;
import weblogic.ejb.container.internal.MDConnectionManager;
import weblogic.ejb.spi.EJBRuntimeHolder;
import weblogic.health.HealthMonitorService;
import weblogic.health.HealthState;
import weblogic.health.Symptom;
import weblogic.health.Symptom.SymptomType;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.management.ManagementException;
import weblogic.management.runtime.EJBPoolRuntimeMBean;
import weblogic.management.runtime.EJBTimerRuntimeMBean;
import weblogic.management.runtime.MessageDrivenEJBRuntimeMBean;
import weblogic.utils.StackTraceUtilsClient;
import weblogic.work.WorkManagerFactory;

public final class MessageDrivenEJBRuntimeMBeanImpl extends EJBRuntimeMBeanImpl implements MessageDrivenEJBRuntimeMBean {
   private final EJBPoolRuntimeMBean poolRtMBean;
   private final EJBTimerRuntimeMBean timerRtMBean;
   private final BeanInfo bi;
   private boolean isJMSConnectionAlive = false;
   private String connectionStatus = "disconnected";
   private final String destination;
   private String jmsClientID = "";
   private String mdbStatus = "initializing";
   private final AtomicInteger suspendCount = new AtomicInteger(0);
   private Throwable lastException;
   private MDConnectionManager mdConnManager;
   private final String applicationName;

   public MessageDrivenEJBRuntimeMBeanImpl(String name, BeanInfo bi, String applicationName, EJBRuntimeHolder component, String destination, TimerManager timerManager) throws ManagementException {
      super(name, bi.getEJBName(), component);
      this.bi = bi;
      this.destination = destination;
      this.applicationName = applicationName;
      this.poolRtMBean = new EJBPoolRuntimeMBeanImpl(name, bi, this);
      this.timerRtMBean = timerManager != null ? new EJBTimerRuntimeMBeanImpl(name, bi, this, timerManager) : null;
      HealthMonitorService.register("MDB" + applicationName, this, false);
   }

   public void unregister() throws ManagementException {
      super.unregister();
      HealthMonitorService.unregister("MDB" + this.applicationName);
   }

   public HealthState getHealthState() {
      byte healthState;
      String reason;
      if (this.isJMSConnectionAlive()) {
         healthState = 0;
         reason = "MDB application " + this.applicationName + " is connected to messaging system.";
      } else {
         healthState = 1;
         reason = "MDB application " + this.applicationName + " is NOT connected to messaging system.";
      }

      Symptom symptom = new Symptom(SymptomType.MDB, Symptom.healthStateSeverity(healthState), this.applicationName, reason);
      return new HealthState(healthState, symptom);
   }

   public EJBPoolRuntimeMBean getPoolRuntime() {
      return this.poolRtMBean;
   }

   public EJBTimerRuntimeMBean getTimerRuntime() {
      return this.timerRtMBean;
   }

   public boolean isJMSConnectionAlive() {
      return this.isJMSConnectionAlive;
   }

   public void setJMSConnectionAlive(boolean b) {
      this.isJMSConnectionAlive = b;
   }

   public String getConnectionStatus() {
      return this.connectionStatus;
   }

   public void setConnectionStatus(String connectionStatus) {
      this.connectionStatus = connectionStatus;
   }

   public String getJmsClientID() {
      return this.jmsClientID;
   }

   public void setJmsClientID(String jmsClientID) {
      this.jmsClientID = jmsClientID;
   }

   public String getDestination() {
      return this.destination;
   }

   public String getMDBStatus() {
      return this.mdbStatus;
   }

   public void setMDBStatus(String mdbStatus) {
      this.mdbStatus = mdbStatus;
   }

   public long getProcessedMessageCount() {
      return this.getPoolRuntime().getAccessTotalCount();
   }

   public int getSuspendCount() {
      return this.suspendCount.get();
   }

   public void incrementSuspendCount() {
      this.suspendCount.incrementAndGet();
   }

   public Throwable getLastException() {
      return this.lastException;
   }

   public String getLastExceptionAsString() {
      return this.lastException != null ? this.lastException.toString() : "";
   }

   public void setLastException(Throwable lastException) {
      this.lastException = lastException;
   }

   public String getApplicationName() {
      return this.applicationName;
   }

   public void setMDConnManager(MDConnectionManager mdConnManager) {
      this.mdConnManager = mdConnManager;
   }

   public boolean suspend() throws ManagementException {
      this.checkConnManagerAvailable();
      ManagedInvocationContext mic = this.bi.setCIC();
      Throwable var2 = null;

      boolean var3;
      try {
         var3 = this.mdConnManager.suspend(true);
      } catch (Throwable var12) {
         var2 = var12;
         throw var12;
      } finally {
         if (mic != null) {
            if (var2 != null) {
               try {
                  mic.close();
               } catch (Throwable var11) {
                  var2.addSuppressed(var11);
               }
            } else {
               mic.close();
            }
         }

      }

      return var3;
   }

   public void scheduleSuspend() throws ManagementException {
      this.checkConnManagerAvailable();
      WorkManagerFactory.getInstance().getDefault().schedule(new Runnable() {
         public void run() {
            try {
               MessageDrivenEJBRuntimeMBeanImpl.this.suspend();
            } catch (ManagementException var2) {
               EJBLogger.logErrorAsyncSuspendOrResumeMDB("suspend", MessageDrivenEJBRuntimeMBeanImpl.this.getEJBName(), MessageDrivenEJBRuntimeMBeanImpl.this.getApplicationName(), MessageDrivenEJBRuntimeMBeanImpl.this.getDestination(), StackTraceUtilsClient.throwable2StackTraceTruncated(var2, 10));
            }

         }
      });
   }

   public boolean resume() throws ManagementException {
      this.checkConnManagerAvailable();

      try {
         ManagedInvocationContext mic = this.bi.setCIC();
         Throwable var2 = null;

         boolean var3;
         try {
            var3 = !this.mdConnManager.activate() ? this.mdConnManager.resume(true) : true;
         } catch (Throwable var13) {
            var2 = var13;
            throw var13;
         } finally {
            if (mic != null) {
               if (var2 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var12) {
                     var2.addSuppressed(var12);
                  }
               } else {
                  mic.close();
               }
            }

         }

         return var3;
      } catch (Exception var15) {
         throw new ManagementException(var15.getMessage());
      }
   }

   public void scheduleResume() throws ManagementException {
      this.checkConnManagerAvailable();
      WorkManagerFactory.getInstance().getDefault().schedule(new Runnable() {
         public void run() {
            try {
               MessageDrivenEJBRuntimeMBeanImpl.this.resume();
            } catch (ManagementException var2) {
               EJBLogger.logErrorAsyncSuspendOrResumeMDB("resume", MessageDrivenEJBRuntimeMBeanImpl.this.getEJBName(), MessageDrivenEJBRuntimeMBeanImpl.this.getApplicationName(), MessageDrivenEJBRuntimeMBeanImpl.this.getDestination(), StackTraceUtilsClient.throwable2StackTraceTruncated(var2, 10));
            }

         }
      });
   }

   public String statusAsString(int status) {
      switch (status) {
         case 0:
            return "INITIALIZING";
         case 1:
            return "ACTIVE";
         case 2:
            return "RUNNING";
         case 3:
            return "SUSPENDED";
         case 4:
            return "ERROR";
         case 5:
            return "INACTIVE";
         case 6:
            return "SUSPENDING";
         case 7:
            return "UNDEPLOYING";
         case 8:
            return "UNDEPLOYED";
         default:
            throw new IllegalArgumentException("Unknown status" + status);
      }
   }

   public String statusAsStringInStartCase(int status) {
      switch (status) {
         case 0:
            return "Initializing";
         case 1:
            return "Active";
         case 2:
            return "Running";
         case 3:
            return "Suspended";
         case 4:
            return "Error";
         case 5:
            return "Inactive";
         case 6:
            return "Suspending";
         case 7:
            return "Undeploying";
         case 8:
            return "Undeployed";
         default:
            throw new IllegalArgumentException("Unknown status" + status);
      }
   }

   private void checkConnManagerAvailable() throws ManagementException {
      if (this.mdConnManager == null) {
         throw new ManagementException("Destination is not available");
      }
   }
}
