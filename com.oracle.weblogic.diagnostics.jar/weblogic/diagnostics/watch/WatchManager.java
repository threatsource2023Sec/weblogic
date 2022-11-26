package weblogic.diagnostics.watch;

import com.bea.adaptive.harvester.WatchedValues;
import com.bea.logging.BaseLogEntry;
import com.oracle.weblogic.diagnostics.timerservice.TimerService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import weblogic.diagnostics.accessor.DataRecord;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.descriptor.WLDFResourceBean;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.module.WLDFModuleException;
import weblogic.diagnostics.timerservice.WLDFTimerListener;
import weblogic.diagnostics.timerservice.WLDFTimerServiceFactory;
import weblogic.logging.LoggerNotAvailableException;
import weblogic.timers.ScheduleExpression;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.collections.ConcurrentHashMap;

public class WatchManager implements TimerListener {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticWatch");
   private WatchConfiguration watchConfig = null;
   private ConcurrentHashMap alarmWatches = new ConcurrentHashMap();
   private TimerManagerFactory timerManagerFactory;
   private TimerManager timerManager;
   private Timer alarmResetTimer;
   private boolean enabled;
   private ModuleTimerListener watchModuleTimerListener = new ModuleTimerListener();
   private Map listenersMap = new ConcurrentHashMap();
   private WatchManagerFactory watchManagerFactory;
   private TimerService timerService = WLDFTimerServiceFactory.getTimerService();

   WatchManager(WatchManagerFactory wmFactory, WLDFResourceBean bean) throws WLDFModuleException {
      this.watchManagerFactory = wmFactory;
      this.watchConfig = new WatchConfiguration(wmFactory.getPartitionName(), bean);
   }

   public synchronized void activate() throws WLDFModuleException {
      if (this.watchConfig != null && this.watchConfig.isWatchNotificationEnabled()) {
         this.enabled = true;
         this.watchConfig.activate(this);
         if (this.watchConfig.getEnabledHarvesterWatches().size() > 0) {
            WLDFTimerServiceFactory.getTimerService().registerListener(this.watchModuleTimerListener);
         }

         this.activateCalendarWatchRules();

         try {
            WatchEventListener.getInstance().addListener(this);
         } catch (LoggerNotAvailableException var2) {
            throw new WLDFModuleException(var2);
         }

         debugLogger.debug("WatchManager " + this.watchConfig.getName() + " has been activated");
      } else if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("WatchManager " + this.watchConfig.getName() + " has been activated, but its configuration has been disabled.");
      }

   }

   synchronized void destroy() throws WLDFModuleException {
      this.deactivate();
   }

   public synchronized void deactivate() throws WLDFModuleException {
      if (this.enabled) {
         this.enabled = false;
         this.cancelAlarmResetTimer();
         this.alarmWatches.clear();
         WLDFTimerServiceFactory.getTimerService().unregisterListener(this.watchModuleTimerListener);
         if (this.watchConfig != null) {
            this.watchConfig.deactivate();
         }

         this.deactivateCalendarWatchRules();

         try {
            WatchEventListener.getInstance().removeListener(this);
         } catch (LoggerNotAvailableException var2) {
            throw new WLDFModuleException(var2);
         }
      }

   }

   WatchConfiguration getWatchConfiguration() {
      return this.watchConfig;
   }

   void setWatchConfiguration(WatchConfiguration proposedConfig) {
      this.watchConfig = proposedConfig;
   }

   WatchNotificationRuntimeMBeanImpl getWatchNotificationRuntime() {
      return this.watchManagerFactory.getWatchNotificationRuntime();
   }

   private void evaluateHarvesterRules() {
      if (!this.enabled) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("evaluateHarvesterRules(): watch manager disabled, returning");
         }

      } else {
         WatchConfiguration watchConfigLocalRef = this.watchConfig;
         if (watchConfigLocalRef != null && watchConfigLocalRef.isWatchNotificationEnabled()) {
            watchConfigLocalRef.getStatistics().incrementTotalHarvesterEvaluationCycles();
            ArrayList enabledHarvesterWatches = watchConfigLocalRef.getEnabledHarvesterWatches();
            if (enabledHarvesterWatches.size() == 0) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("No harvester rules configured");
               }
            } else {
               watchConfigLocalRef.harvest();
               this.performHarvesterWatchEvaluations(enabledHarvesterWatches);
               this.getWatchConfiguration().getWatchedValues().resetRawValues();
            }

            this.updateWatchNotificationRuntime();
            this.getWatchNotificationRuntime().sendNotification(watchConfigLocalRef.getName());
         }
      }
   }

   public Watch[] getActiveAlarmWatches() {
      Watch[] retArray = new Watch[this.alarmWatches.size()];
      this.alarmWatches.values().toArray(retArray);
      return retArray;
   }

   public String[] getActiveAlarmWatchNames() {
      ArrayList results = new ArrayList();
      Iterator it = this.alarmWatches.values().iterator();

      while(it.hasNext()) {
         results.add(((Watch)it.next()).getWatchName());
      }

      return (String[])results.toArray(new String[results.size()]);
   }

   int getCurrentActiveAlarmsCount() {
      return this.alarmWatches.size();
   }

   public WatchManagerStatistics getStatistics() {
      return this.watchConfig.getStatistics();
   }

   public void timerExpired(Timer timer) {
      try {
         long currentTime = System.currentTimeMillis();
         Iterator alarmEntriesIt = this.alarmWatches.entrySet().iterator();

         while(alarmEntriesIt.hasNext()) {
            Map.Entry entry = (Map.Entry)alarmEntriesIt.next();
            Watch watch = (Watch)entry.getValue();
            if (watch.getAlarmType() == 2 && watch.getResetTime() <= currentTime) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Reset watch " + watch.getWatchName());
               }

               alarmEntriesIt.remove();
               watch.setAlarm(false);
            }
         }

         this.alarmResetTimer = null;
      } catch (Exception var10) {
         DiagnosticsLogger.logUnexpectedException("" + timer, var10);
      } finally {
         this.resetAlarmTimer();
      }

   }

   public boolean hasWatch(String watchName) {
      return this.getWatchConfiguration().hasWatch(watchName);
   }

   public void resetWatchAlarm(String watchName) throws WatchNotFoundException, WatchNotActiveAlarmException {
      WatchConfiguration wci = this.watchConfig;
      if (wci != null) {
         Watch watch = wci.getWatch(watchName);
         if (!watch.isAlarm()) {
            throw new WatchNotActiveAlarmException("Watch " + watchName + " is not an active alarm");
         } else {
            this.alarmWatches.remove(watchName);
            watch.setAlarm(false);
         }
      }
   }

   public int getNumActiveImageNotifications() {
      return this.watchConfig != null ? this.watchConfig.getNumActiveImageNotifications() : 0;
   }

   private boolean setAlarm(Watch watch) {
      boolean isAutomaticAlarm = false;
      if (watch.getAlarmType() == 0) {
         return false;
      } else {
         watch.setAlarm(true);
         if (watch.getAlarmType() == 2) {
            watch.setResetTime(System.currentTimeMillis() + (long)watch.getAlarmResetPeriod());
            isAutomaticAlarm = true;
            this.watchConfig.getStatistics().incrementTotalActiveAutomaticResetAlarms();
         } else {
            this.watchConfig.getStatistics().incrementTotalActiveManualResetAlarms();
         }

         this.alarmWatches.put(watch.getWatchName(), watch);
         return isAutomaticAlarm;
      }
   }

   private TimerManager getTimerManager() {
      if (this.timerManager == null) {
         this.timerManagerFactory = TimerManagerFactory.getTimerManagerFactory();
         this.timerManager = this.timerManagerFactory.getDefaultTimerManager();
      }

      return this.timerManager;
   }

   private void resetAlarmTimer() {
      long nextResetTime = 0L;
      Iterator var3 = this.alarmWatches.values().iterator();

      while(true) {
         long resetTime;
         do {
            Watch watch;
            do {
               if (!var3.hasNext()) {
                  if (nextResetTime > 0L) {
                     this.cancelAlarmResetTimer();
                     this.scheduleAlarmResetTimer(nextResetTime);
                  } else {
                     this.alarmResetTimer = null;
                     if (debugLogger.isDebugEnabled()) {
                        debugLogger.debug("No timer scheduled");
                     }
                  }

                  return;
               }

               watch = (Watch)var3.next();
            } while(watch.getAlarmType() != 2);

            resetTime = watch.getResetTime();
         } while(resetTime >= nextResetTime && nextResetTime != 0L);

         nextResetTime = resetTime;
      }
   }

   void scheduleAlarmResetTimer(long nextResetTime) {
      long delay = nextResetTime - System.currentTimeMillis();
      if (delay < 0L) {
         delay = 0L;
      }

      this.alarmResetTimer = this.getTimerManager().schedule(this, delay);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Scheduled timer for delay of " + delay);
      }

   }

   void cancelAlarmResetTimer() {
      if (this.alarmResetTimer != null) {
         boolean canceled = this.alarmResetTimer.cancel();
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Canceled timer with result " + canceled);
         }
      }

   }

   private void performHarvesterWatchEvaluations(ArrayList enabledHarvesterWatches) {
      boolean resetTimer = false;
      long startTime = System.nanoTime();
      WatchConfiguration watchConfigLocalRef = this.watchConfig;
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Evaluating harvester watch rules ");
         WatchedValues wv = watchConfigLocalRef.getWatchedValues();
         debugLogger.debug(wv.dump("", true, false, true));
      }

      int evaluatedWatches = 0;
      Iterator watchIt = enabledHarvesterWatches.iterator();

      while(watchIt.hasNext()) {
         Watch watch = (Watch)watchIt.next();
         if (watch.isDisabled()) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Watch has been disabled, removing from enabled set: " + watch.getWatchName());
            }

            watchIt.remove();
         } else if (watch.isCalendarSchedule()) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Skipping calendar watch " + watch.getWatchName());
            }
         } else if (!watch.isAlarm()) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Evaluating watch " + watch);
            }

            try {
               boolean ruleResult;
               if (watch.evaluateHarvesterRuleWatch()) {
                  ruleResult = true;
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("Evaluated watch to true " + watch.getWatchName());
                  }
               } else {
                  ruleResult = false;
               }

               ++evaluatedWatches;
               if (ruleResult) {
                  watchConfigLocalRef.getStatistics().incrementTotalHarvesterWatchesTriggered();
                  if (watch.hasAlarm()) {
                     resetTimer = this.setAlarm(watch);
                  }
               }
            } catch (Throwable var11) {
               DiagnosticsLogger.logWatchEvaluationFailed(watch.getWatchName(), var11);
               watch.setDisabled();
               watchIt.remove();
            }
         }
      }

      if (evaluatedWatches > 0) {
         watchConfigLocalRef.getStatistics().incrementTotalHarvesterWatchEvaluations(evaluatedWatches);
      }

      if (resetTimer) {
         this.resetAlarmTimer();
      }

      long elapsedTime = System.nanoTime() - startTime;
      watchConfigLocalRef.getStatistics().incrementTotalHarvesterWatchEvaluationTime(elapsedTime);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Finished evaluating harvester watch rules in " + elapsedTime + " nanos");
      }

   }

   private void evaluateCalendarWatch(Watch watch) {
      WatchConfiguration watchConfigLocalRef = this.watchConfig;
      if (!watchConfigLocalRef.isWatchNotificationEnabled()) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("WatchNotification disabled for resource " + watchConfigLocalRef.getModuleName() + ", watch " + watch.getWatchName() + " eval skipped");
         }

      } else if (watch.isAlarm()) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Watch " + watch.getWatchName() + " is still in alarm state.");
         }

      } else if (watch.isDisabled()) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Watch " + watch.getWatchName() + " is disabled.");
         }

      } else {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Evaluating watch " + watch);
         }

         watchConfigLocalRef.getStatistics().incrementTotalHarvesterWatchEvaluations(1);

         try {
            boolean ruleResult;
            if (watch.evaluateHarvesterRuleWatch()) {
               ruleResult = true;
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Evaluated watch to true " + watch.getWatchName());
               }
            } else {
               ruleResult = false;
            }

            if (ruleResult) {
               watchConfigLocalRef.getStatistics().incrementTotalHarvesterWatchesTriggered();
               if (watch.hasAlarm() && this.setAlarm(watch)) {
                  this.resetAlarmTimer();
               }
            }
         } catch (Throwable var5) {
            DiagnosticsLogger.logWatchEvaluationFailed(watch.getWatchName(), var5);
            watch.setDisabled();
            watchConfigLocalRef.getEnabledHarvesterWatches().remove(watch);
            this.unregisterCalendarWatch(watch);
         }

      }
   }

   void evaluateLogEventRulesAsync(BaseLogEntry logEntry, int logRuleType) {
      if (!this.enabled) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("evaluateLogEventRulesAsync(): watch manager disabled, returning");
         }

      } else {
         WatchConfiguration watchConfigLocalRef = this.watchConfig;
         watchConfigLocalRef.getStatistics().incrementTotalLogEvaluationCycles();
         boolean resetTimer = false;
         long startTime = System.currentTimeMillis();
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Evaluating log watch rules in separate thread ");
         }

         WatchConfiguration wci = this.getWatchConfiguration();
         if (wci != null) {
            ArrayList allLogWatches = new ArrayList();
            switch (logRuleType) {
               case 1:
                  allLogWatches = wci.getEnabledLogWatches();
                  break;
               case 4:
                  allLogWatches = wci.getEnabledDomainLogWatches();
            }

            int watchRulesEvaluated = 0;
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Evaluating " + allLogWatches.size() + " watches");
            }

            Iterator watchIt = allLogWatches.iterator();

            while(watchIt.hasNext()) {
               Watch watch = (Watch)watchIt.next();
               if (!watch.isAlarm() && !watch.isDisabled()) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("Evaluating watch " + watch);
                  }

                  ++watchRulesEvaluated;

                  try {
                     if (watch.evaluateLogRuleWatch(logEntry)) {
                        watchConfigLocalRef.getStatistics().incrementTotalLogWatchesTriggered();
                        if (watch.hasAlarm()) {
                           if (debugLogger.isDebugEnabled()) {
                              debugLogger.debug("Handling alarm for watch " + watch.getWatchName());
                           }

                           resetTimer = this.setAlarm(watch);
                        }
                     }
                  } catch (Throwable var13) {
                     DiagnosticsLogger.logWatchEvaluationFailed(watch.getWatchName(), var13);
                     watch.setDisabled();
                     watchIt.remove();
                  }
               }
            }

            watchConfigLocalRef.getStatistics().incrementTotalLogWatchEvaluations(watchRulesEvaluated);
            if (resetTimer) {
               this.resetAlarmTimer();
            }

            long elapsedTime = System.currentTimeMillis() - startTime;
            watchConfigLocalRef.getStatistics().incrementTotalLogWatchEvaluationTime(elapsedTime);
         }
      }
   }

   void evaluateEventDataRulesAsync(DataRecord dataRecord) {
      if (!this.enabled) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("evaluateEventDataRulesAsync(): watch manager disabled, returning");
         }

      } else {
         WatchConfiguration watchConfigLocalRef = this.watchConfig;
         if (watchConfigLocalRef != null) {
            watchConfigLocalRef.getStatistics().incrementTotalEventDataEvaluationCycles();
            long startTime = System.currentTimeMillis();
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Evaluating event data watch rules in separate thread ");
            }

            ArrayList enabledEventDataWatches = watchConfigLocalRef.getEnabledEventDataWatches();
            int evaluatedWatches = 0;
            boolean resetTimer = false;
            Iterator watchIt = enabledEventDataWatches.iterator();

            while(watchIt.hasNext()) {
               Watch watch = (Watch)watchIt.next();
               if (!watch.isAlarm() && !watch.isDisabled()) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("Evaluating watch " + watch);
                  }

                  ++evaluatedWatches;

                  try {
                     if (watch.evaluateEventDataRuleWatch(dataRecord)) {
                        watchConfigLocalRef.getStatistics().incrementTotalEventDataWatchesTriggered();
                        if (watch.hasAlarm()) {
                           if (debugLogger.isDebugEnabled()) {
                              debugLogger.debug("Handling alarm for watch " + watch.getWatchName());
                           }

                           resetTimer = this.setAlarm(watch);
                        }
                     }
                  } catch (Throwable var11) {
                     DiagnosticsLogger.logWatchEvaluationFailed(watch.getWatchName(), var11);
                     watch.setDisabled();
                     watchIt.remove();
                  }
               }
            }

            watchConfigLocalRef.getStatistics().incrementTotalEventDataWatchEvaluations(evaluatedWatches);
            if (resetTimer) {
               this.resetAlarmTimer();
            }

            long elapsedTime = System.currentTimeMillis() - startTime;
            watchConfigLocalRef.getStatistics().incrementTotalEventDataWatchEvaluationTime(elapsedTime);
         }
      }
   }

   private void updateWatchNotificationRuntime() {
      int currentAlarmCount = this.alarmWatches.size();
      this.watchConfig.getStatistics().setCurrentActiveAlarmsCount(currentAlarmCount);
   }

   private void activateCalendarWatchRules() {
      this.scheduleCalendarWatchRules(this.watchConfig.getEnabledHarvesterWatches(), false);
   }

   private void deactivateCalendarWatchRules() {
      this.scheduleCalendarWatchRules(this.watchConfig.getEnabledHarvesterWatches(), true);
   }

   private void scheduleCalendarWatchRules(ArrayList watches, boolean cancel) {
      if (watches != null) {
         Iterator var3 = watches.iterator();

         while(var3.hasNext()) {
            Watch watch = (Watch)var3.next();
            if (watch.isCalendarSchedule()) {
               if (cancel) {
                  this.unregisterCalendarWatch(watch);
               } else {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("Scheduling calendar timer for watch " + watch.getWatchName());
                  }

                  WatchTimerListener listener = (WatchTimerListener)this.listenersMap.get(watch.getWatchName());
                  if (listener != null) {
                     this.timerService.unregisterListener(listener);
                  } else {
                     listener = new WatchTimerListener(watch);
                     this.listenersMap.put(watch.getWatchName(), listener);
                  }

                  this.timerService.registerListener(listener);
               }
            }
         }

      }
   }

   private void unregisterCalendarWatch(Watch watch) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Canceling calendar timer for watch " + watch.getWatchName());
      }

      WatchTimerListener listener = (WatchTimerListener)this.listenersMap.remove(watch.getWatchName());
      if (listener != null) {
         this.timerService.unregisterListener(listener);
      }

   }

   void initializeJMXNotificationListeners(WatchNotificationRuntimeMBeanImpl watchNotificationRuntime) {
      this.watchConfig.initializeJMXNotificationListeners(watchNotificationRuntime);
   }

   protected int getSamplePeriodSeconds() {
      long periodMillis = this.watchConfig != null ? this.watchConfig.getResourceBean().getHarvester().getSamplePeriod() : 300000L;
      return (int)(periodMillis / 1000L);
   }

   class ModuleTimerListener implements com.oracle.weblogic.diagnostics.timerservice.TimerListener {
      private AtomicBoolean harvesterRulesCallbackInProgress = new AtomicBoolean(false);

      public int getFrequency() {
         return WatchManager.this.getSamplePeriodSeconds();
      }

      public void timerExpired() {
         if (this.harvesterRulesCallbackInProgress.compareAndSet(false, true)) {
            try {
               if (WatchEventListener.isServerRunning()) {
                  WatchManager.this.evaluateHarvesterRules();
                  return;
               }

               if (WatchManager.debugLogger.isDebugEnabled()) {
                  WatchManager.debugLogger.debug("ModuleTimerListener: Server not in RUNNING state, skip processing harvester rules.");
               }
            } finally {
               this.harvesterRulesCallbackInProgress.set(false);
            }

         }
      }
   }

   final class WatchTimerListener implements WLDFTimerListener {
      private AtomicBoolean calendarRulesCallbackInProgress = new AtomicBoolean(false);
      private Watch watch;

      public WatchTimerListener(Watch watch) {
         this.watch = watch;
      }

      public void timerExpired() {
         if (this.calendarRulesCallbackInProgress.compareAndSet(false, true)) {
            try {
               if (WatchEventListener.isServerRunning()) {
                  WatchManager.this.evaluateCalendarWatch(this.watch);
                  return;
               }

               if (WatchManager.debugLogger.isDebugEnabled()) {
                  WatchManager.debugLogger.debug("WatchTimerListener timerExpired(): Server not in RUNNING state, skip processing  schedule-based rules.");
               }
            } finally {
               this.calendarRulesCallbackInProgress.set(false);
            }

         }
      }

      public boolean useCalendarSchedule() {
         return this.watch.isCalendarSchedule();
      }

      public int getFrequency() {
         return WatchManager.this.getSamplePeriodSeconds();
      }

      public ScheduleExpression getSchedule() {
         return this.watch.getScheduleExpression();
      }
   }
}
