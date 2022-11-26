package weblogic.management.logging;

import com.bea.logging.LoggingService;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.security.AccessController;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.logging.LogMgmtLogger;
import weblogic.management.configuration.LogMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.LogRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.server.ServiceFailureException;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.PlatformConstants;
import weblogic.work.WorkManagerFactory;

@Service
@Named
@RunLevel(15)
public class LogMonitoringService extends AbstractServerService implements TimerListener {
   private static final boolean DEBUG = false;
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final RuntimeAccess RUNTIME_ACCESS;
   private TimerManager timerManager = null;
   private ThrottleFilter throttleFilter;
   private boolean lastCycleThrottleOn = false;

   public void start() throws ServiceFailureException {
      final LogMBean logMBean = RUNTIME_ACCESS.getServer().getLog();
      this.startLogThrottle(logMBean);
      this.scheduleTimer(logMBean);
      logMBean.addPropertyChangeListener(new PropertyChangeListener() {
         public void propertyChange(PropertyChangeEvent evt) {
            String propName = evt.getPropertyName();
            if (propName.equals("LogMonitoringEnabled") || propName.equals("LogMonitoringIntervalSecs")) {
               LogMonitoringService.this.scheduleTimer(logMBean);
            }

         }
      });
   }

   private void scheduleTimer(LogMBean logMBean) {
      this.stopTimer();
      if (logMBean.isLogMonitoringEnabled()) {
         TimerManagerFactory timerManagerFactory = TimerManagerFactory.getTimerManagerFactory();
         this.timerManager = timerManagerFactory.getTimerManager(LogMonitoringService.class.getName(), WorkManagerFactory.getInstance().getDefault());
         int monitorIntervalSecs = logMBean.getLogMonitoringIntervalSecs();
         this.timerManager.scheduleAtFixedRate(this, 0L, (long)(monitorIntervalSecs * 1000));
         LogMgmtLogger.logMessageMonitoringStarted(monitorIntervalSecs);
      }

   }

   public void stop() throws ServiceFailureException {
      this.stopTimer();
   }

   private void stopTimer() {
      if (this.timerManager != null) {
         try {
            this.timerManager.waitForStop(1L);
         } catch (InterruptedException var5) {
         } finally {
            LogMgmtLogger.logMessageMonitoringStopped();
         }
      }

   }

   public void halt() throws ServiceFailureException {
      this.stop();
   }

   public void timerExpired(Timer timer) {
      LogMBean logMBean = RUNTIME_ACCESS.getServer().getLog();
      if (logMBean.isLogMonitoringEnabled()) {
         this.ensureLogFileOpened();
         int throttleThreshold = logMBean.getLogMonitoringThrottleThreshold();
         int lastCycleLoggedMessagesCount = this.throttleFilter.resetCycleCount();
         int timerInterval = logMBean.getLogMonitoringIntervalSecs();
         if (lastCycleLoggedMessagesCount > throttleThreshold) {
            LogMgmtLogger.logMessageThrottlingOn(lastCycleLoggedMessagesCount, timerInterval, throttleThreshold);
            this.dumpThrottleData(throttleThreshold);
            this.lastCycleThrottleOn = true;
            this.throttleFilter.incrementSuccesiveCyclesOverThreshold();
         } else {
            this.throttleFilter.clearThrottlingData();
            if (this.lastCycleThrottleOn) {
               LogMgmtLogger.logMessageThrottlingOff(lastCycleLoggedMessagesCount, timerInterval, throttleThreshold);
            }

            this.lastCycleThrottleOn = false;
         }

      }
   }

   private void ensureLogFileOpened() {
      try {
         LogRuntimeMBean logRuntime = RUNTIME_ACCESS.getServerRuntime().getLogRuntime();
         if (logRuntime != null) {
            logRuntime.ensureLogOpened();
         }
      } catch (Exception var2) {
         LogMgmtLogger.logErrorReopeningServerLogFile(var2);
      }

   }

   private void dumpThrottleData(int threshold) {
      boolean logMessage = false;
      if (this.throttleFilter != null) {
         Map throttleData = this.throttleFilter.getThrottleData();
         StringBuilder sb = new StringBuilder();
         Iterator var5 = throttleData.keySet().iterator();

         while(var5.hasNext()) {
            String key = (String)var5.next();
            AtomicInteger value = (AtomicInteger)throttleData.get(key);
            if (value != null && value.get() > (int)((double)threshold * this.throttleFilter.getMessageThresholdPercentDecimal())) {
               sb.append(key).append("[").append(value).append("]").append(PlatformConstants.EOL);
               logMessage = true;
            }
         }

         if (logMessage) {
            LogMgmtLogger.logDumpThrottleData(sb.toString().trim());
         }
      }

   }

   private synchronized void startLogThrottle(LogMBean logMBean) {
      LoggingService loggingSvc = LoggingService.getInstance();
      Logger rootLogger = loggingSvc.getLogger();
      this.throttleFilter = new ThrottleFilter(logMBean);
      rootLogger.setFilter(this.throttleFilter);
   }

   static {
      RUNTIME_ACCESS = ManagementService.getRuntimeAccess(KERNEL_ID);
   }
}
