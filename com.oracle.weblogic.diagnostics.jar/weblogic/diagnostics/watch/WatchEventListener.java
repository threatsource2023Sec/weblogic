package weblogic.diagnostics.watch;

import com.bea.logging.BaseLogEntry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import weblogic.diagnostics.accessor.DataRecord;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.instrumentation.InstrumentationEventListener;
import weblogic.diagnostics.utils.LogEventRulesEvaluator;
import weblogic.i18n.logging.Severities;
import weblogic.logging.LoggerNotAvailableException;
import weblogic.t3.srvr.T3Srvr;
import weblogic.utils.LocatorUtilities;
import weblogic.utils.PropertyHelper;
import weblogic.utils.collections.CircularQueue;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

class WatchEventListener implements InstrumentationEventListener, LogEventRulesEvaluator, Runnable {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticWatchEvents");
   private static DebugLogger detailsdebugLogger = DebugLogger.getDebugLogger("DebugDiagnosticWatchEventsDetails");
   private static final String MAX_PENDING_JOBS_PROP = "weblogic.diagnostics.watch.max_pending_jobs";
   private static final int MAX_PENDING_JOBS = PropertyHelper.getInteger("weblogic.diagnostics.watch.max_pending_jobs", 10);
   private static final String WORK_MANAGER_NAME = "WatchManagerEvents";
   private static final int MAX_THREADS = 1;
   private WorkManager workManager;
   private CircularQueue eventQueue;
   private Thread asyncLogThread;
   private ArrayList listeners;
   private int logEventSeverityThreshold;
   private int enabledLogWatches;
   private int enabledDomainLogWatches;
   private int enabledEventWatches;
   private boolean registeredForInstEvents;
   private boolean registeredForLogEvents;
   private boolean registeredForDomainLogEvents;
   private AtomicBoolean queueWorkInProgress;

   WatchEventListener(WorkManager testWM) {
      this.eventQueue = new CircularQueue();
      this.listeners = new ArrayList();
      this.logEventSeverityThreshold = 32;
      this.queueWorkInProgress = new AtomicBoolean(false);
      this.workManager = testWM;
   }

   private WatchEventListener() {
      this.eventQueue = new CircularQueue();
      this.listeners = new ArrayList();
      this.logEventSeverityThreshold = 32;
      this.queueWorkInProgress = new AtomicBoolean(false);
      this.workManager = WorkManagerFactory.getInstance().findOrCreate("WatchManagerEvents", 1, 1);
   }

   static WatchEventListener getInstance() {
      return WatchEventListener.ListenerSingleton.getInstance();
   }

   synchronized void addListener(WatchManager m) throws LoggerNotAvailableException {
      if (!this.listeners.contains(m)) {
         this.listeners.add(m);
         int previousSeverity = this.logEventSeverityThreshold;
         this.recalcThresholds();
         if (previousSeverity != this.logEventSeverityThreshold) {
            this.deinitializeLogEventHandler();
         }

         this.initializeEventListener();
         this.initializeLogEventHandler();
      }

   }

   synchronized void removeListener(WatchManager m) throws LoggerNotAvailableException {
      boolean removed = this.listeners.remove(m);
      if (removed) {
         int previousSeverity = this.logEventSeverityThreshold;
         this.recalcThresholds();
         if (this.listeners.size() == 0) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("un-initializing event listeners, remove " + m.getWatchConfiguration().getName());
            }

            this.deinitializeEventListener();
            this.deinitializeLogEventHandler();
         } else if (previousSeverity != this.logEventSeverityThreshold) {
            this.deinitializeLogEventHandler();
            this.initializeLogEventHandler();
         }
      }

   }

   private void recalcThresholds() {
      this.enabledLogWatches = 0;
      this.enabledDomainLogWatches = 0;
      this.enabledEventWatches = 0;
      this.logEventSeverityThreshold = 32;

      WatchConfiguration wc;
      for(Iterator var1 = this.listeners.iterator(); var1.hasNext(); this.enabledEventWatches += wc.getEnabledEventDataWatches().size()) {
         WatchManager wm = (WatchManager)var1.next();
         wc = wm.getWatchConfiguration();
         this.logEventSeverityThreshold = Math.max(wc.getEventHandlerSeverity(), this.logEventSeverityThreshold);
         this.enabledLogWatches += wc.getEnabledLogWatches().size();
         this.enabledDomainLogWatches += wc.getEnabledDomainLogWatches().size();
      }

      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("listener thresholds: \n\teventSeverity: " + Severities.severityNumToString(this.logEventSeverityThreshold) + "\n\tenabledLogWatches: " + this.enabledLogWatches + "\n\tenabledEventWatches: " + this.enabledEventWatches);
      }

   }

   int getEventQueueSize() {
      return this.eventQueue.size();
   }

   public void handleInstrumentationEvent(DataRecord dataRecord) {
      if (detailsdebugLogger.isDebugEnabled()) {
         detailsdebugLogger.debug("Processing inst record " + dataRecord);
      }

      if (!isServerRunning()) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("handleInstrumentationEvent(): Server not in RUNNING state, skip processing.");
         }

      } else if (this.listeners.size() != 0) {
         if (this.enabledEventWatches != 0) {
            if (detailsdebugLogger.isDebugEnabled()) {
               detailsdebugLogger.debug("Queuing inst record " + dataRecord);
            }

            synchronized(this.eventQueue) {
               this.eventQueue.add(dataRecord);
            }

            this.scheduleQueueWork();
         }
      }
   }

   public void evaluateLogEventRules(BaseLogEntry logEntry, int logRuleType) {
      if (detailsdebugLogger.isDebugEnabled()) {
         detailsdebugLogger.debug("Processing log entry " + logEntry);
      }

      if (!isServerRunning()) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("evaluateLogEventRules(): Server not in RUNNING state, skip processing.");
         }

      } else if (this.listeners.size() != 0) {
         if (logRuleType != 1 || this.enabledLogWatches != 0) {
            if (logRuleType != 4 || this.enabledDomainLogWatches != 0) {
               if (logEntry.getSeverity() < 128) {
                  if (detailsdebugLogger.isDebugEnabled()) {
                     detailsdebugLogger.debug("Queuing log entry " + logEntry);
                  }

                  synchronized(this.eventQueue) {
                     if (this.asyncLogThread != null && Thread.currentThread().equals(this.asyncLogThread)) {
                        return;
                     }

                     this.eventQueue.add(new LogEntryWork(logEntry, logRuleType));
                  }

                  this.scheduleQueueWork();
               }
            }
         }
      }
   }

   public void run() {
      if (!this.queueWorkInProgress.compareAndSet(false, true)) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Event queue async processing already in progress, returning");
         }

      } else {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Starting event queue handler");
         }

         long logRecordsProcessed = 0L;
         long eventRecordsProcessed = 0L;
         WatchManager[] wmArray = null;
         synchronized(this) {
            wmArray = (WatchManager[])this.listeners.toArray(new WatchManager[this.listeners.size()]);
         }

         try {
            while(true) {
               Object eventWork;
               synchronized(this.eventQueue) {
                  this.asyncLogThread = null;
                  if (this.eventQueue.size() == 0) {
                     if (debugLogger.isDebugEnabled()) {
                        debugLogger.debug("Event queue processing complete");
                     }

                     this.queueWorkInProgress.set(false);
                     return;
                  }

                  eventWork = this.eventQueue.remove();
                  if (eventWork instanceof BaseLogEntry) {
                     this.asyncLogThread = Thread.currentThread();
                  }
               }

               if (detailsdebugLogger.isDebugEnabled()) {
                  detailsdebugLogger.debug("Executing event " + eventWork);
               }

               if (eventWork instanceof LogEntryWork) {
                  ++logRecordsProcessed;
                  LogEntryWork logEntryWork = (LogEntryWork)eventWork;
                  this.dispatchLogEvent(wmArray, logEntryWork.logEntry, logEntryWork.logRuleType);
               } else {
                  if (!(eventWork instanceof DataRecord)) {
                     throw new AssertionError("Unknown event work" + eventWork);
                  }

                  ++eventRecordsProcessed;
                  this.dispatchEventDataRecord(wmArray, (DataRecord)eventWork);
               }
            }
         } finally {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Total events processed: " + (logRecordsProcessed + eventRecordsProcessed) + ", Log: " + logRecordsProcessed + ", Inst: " + eventRecordsProcessed);
            }

         }
      }
   }

   private void dispatchEventDataRecord(WatchManager[] wmArray, DataRecord eventWork) {
      WatchManager[] var3 = wmArray;
      int var4 = wmArray.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         WatchManager wm = var3[var5];

         try {
            wm.evaluateEventDataRulesAsync(eventWork);
         } catch (Throwable var8) {
            DiagnosticsLogger.logWatchInstrumentationEventDispatchError(var8);
         }
      }

   }

   private void dispatchLogEvent(WatchManager[] wmArray, BaseLogEntry eventWork, int logRuleType) {
      WatchManager[] var4 = wmArray;
      int var5 = wmArray.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         WatchManager wm = var4[var6];

         try {
            wm.evaluateLogEventRulesAsync(eventWork, logRuleType);
         } catch (Throwable var9) {
            DiagnosticsLogger.logWatchLogEventDispatchError(var9);
         }
      }

   }

   private void initializeEventListener() {
      if (!this.registeredForInstEvents) {
         if (this.enabledEventWatches > 0) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("enabling Instrumentation event listener");
            }

            InstrumentationManagerService instManager = (InstrumentationManagerService)LocatorUtilities.getService(InstrumentationManagerService.class);
            instManager.removeInstrumentationEventListener(this);
            instManager.addInstrumentationEventListener(this);
            this.registeredForInstEvents = true;
         } else if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Not enabling INST event listener, no event watches enabled");
         }
      }

   }

   private void initializeLogEventHandler() throws LoggerNotAvailableException {
      if (!this.registeredForLogEvents) {
         if (this.enabledLogWatches > 0) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("enabling LOG event listener");
            }

            WatchLogService.deregisterFromServerLogger();
            WatchLogService.registerToServerLogger(this, this.logEventSeverityThreshold);
            this.registeredForLogEvents = true;
         } else if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Not enabling LOG event listener, no log watches enabled");
         }
      }

      if (WatchConfiguration.isAdminServer() && !this.registeredForDomainLogEvents) {
         if (this.enabledDomainLogWatches > 0) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Enabling domain LOG event listener");
            }

            WatchLogService.deregisterFromDomainLogger();
            WatchLogService.registerToDomainLogger(this, this.logEventSeverityThreshold);
            this.registeredForDomainLogEvents = true;
         } else if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Not enabling domain LOG event listener");
         }
      }

   }

   private void deinitializeEventListener() {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("uninitializing INST event listener");
      }

      InstrumentationManagerService instManager = (InstrumentationManagerService)LocatorUtilities.getService(InstrumentationManagerService.class);
      instManager.removeInstrumentationEventListener(this);
      this.registeredForInstEvents = false;
   }

   private void deinitializeLogEventHandler() throws LoggerNotAvailableException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("uninitializing LOG event listener");
      }

      WatchLogService.deregisterFromServerLogger();
      if (WatchConfiguration.isAdminServer()) {
         WatchLogService.deregisterFromDomainLogger();
      }

      this.registeredForLogEvents = false;
      this.registeredForDomainLogEvents = false;
   }

   static boolean isServerRunning() {
      return T3Srvr.getT3Srvr().getRunState() == 2;
   }

   private void scheduleQueueWork() {
      if (!this.queueWorkInProgress.get() && this.workManager.getQueueDepth() < MAX_PENDING_JOBS) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Scheduling queue for processing...");
         }

         this.workManager.schedule(this);
      }

   }

   // $FF: synthetic method
   WatchEventListener(Object x0) {
      this();
   }

   private static final class LogEntryWork {
      private BaseLogEntry logEntry;
      private int logRuleType;

      LogEntryWork(BaseLogEntry logEvent, int type) {
         this.logEntry = logEvent;
         this.logRuleType = type;
      }
   }

   private static class ListenerSingleton {
      private static WatchEventListener singleton;

      static synchronized WatchEventListener getInstance() {
         if (singleton == null) {
            singleton = new WatchEventListener();
         }

         return singleton;
      }
   }
}
