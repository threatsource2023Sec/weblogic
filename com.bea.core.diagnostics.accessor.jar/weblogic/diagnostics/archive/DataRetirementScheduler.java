package weblogic.diagnostics.archive;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import weblogic.diagnostics.accessor.AccessRuntime;
import weblogic.diagnostics.accessor.AccessorConfiguration;
import weblogic.diagnostics.accessor.AccessorConfigurationProvider;
import weblogic.diagnostics.accessor.AccessorMBeanFactory;
import weblogic.diagnostics.accessor.DataAccessRuntime;
import weblogic.diagnostics.accessor.EditableAccessorConfiguration;
import weblogic.diagnostics.accessor.runtime.DataRetirementTaskRuntimeMBean;
import weblogic.diagnostics.archive.wlstore.PersistentStoreDataArchive;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.type.UnexpectedExceptionHandler;
import weblogic.management.ManagementException;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.PropertyHelper;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public final class DataRetirementScheduler implements TimerListener {
   private static final DebugLogger DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugDiagnosticArchiveRetirement");
   private static final long MILLIS_IN_SECOND = 1000L;
   private static final long MILLIS_IN_MINUTE = 60000L;
   private static final long MILLIS_IN_HOUR = 3600000L;
   private static final long MILLIS_IN_DAY = 86400000L;
   private static final long STORE_SIZE_LIMIT_SCALE = 838860L;
   private static final String WORK_MANAGER_NAME = "DataRetirementWorkManager";
   private static final int MAX_THREADS = 1;
   private boolean testMode;
   private static DataRetirementScheduler SINGLETON;
   private Timer timer;
   private long lastTime = 0L;
   private WorkManager workManager = WorkManagerFactory.getInstance().findOrCreate("DataRetirementWorkManager", 1, 1);
   private Runnable dataRetirementTask;

   private DataRetirementScheduler() {
   }

   public static synchronized DataRetirementScheduler getInstance() {
      if (SINGLETON == null) {
         SINGLETON = new DataRetirementScheduler();
      }

      return SINGLETON;
   }

   public void start() {
      if (this.timer == null) {
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Starting DataRetirementScheduler");
         }

         this.testMode = PropertyHelper.getBoolean("weblogic.diagnostics.archive.DataRetirementScheduler.testMode");
         if (!this.testMode) {
            try {
               AccessorConfigurationProvider wldfConfig = this.getConfiguration();
               this.testMode = wldfConfig.isDataRetirementTestModeEnabled();
            } catch (Throwable var5) {
               if (DEBUG_LOGGER.isDebugEnabled()) {
                  DEBUG_LOGGER.debug("Failed to get accessor configuration", var5);
               }
            }
         }

         long period = this.testMode ? 1000L : 60000L;
         TimerManagerFactory timerManagerFactory = TimerManagerFactory.getTimerManagerFactory();
         TimerManager timerManager = timerManagerFactory.getDefaultTimerManager();
         this.timer = timerManager.scheduleAtFixedRate(this, 0L, period);
      }

   }

   public void stop() {
      if (this.timer != null) {
         this.timer.cancel();
         this.timer = null;
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Stopped DataRetirementScheduler");
         }
      }

   }

   public void timerExpired(Timer timer) {
      try {
         this.processTimer(timer);
      } catch (Exception var3) {
         UnexpectedExceptionHandler.handle("Internal error in data retirement scheduler", var3);
      }

   }

   private void processTimer(Timer timer) throws Exception {
      AccessorConfigurationProvider wldfConfig = this.getConfiguration();
      boolean enabled = wldfConfig.isDataRetirementEnabled();
      if (!this.testMode && DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("Data retirement enabled: " + enabled);
      }

      if (enabled) {
         long now = System.currentTimeMillis();
         long millisSinceMidnight = now % 86400000L;
         long hoursSinceMidnight = millisSinceMidnight / 3600000L;
         long millisSinceHour = millisSinceMidnight % 3600000L;
         long minutesSinceHour = millisSinceHour / 60000L;
         long millisSinceMinute = millisSinceHour % 60000L;
         long secondsSinceMinute = millisSinceMinute / 1000L;
         long minutesSinceMidnight = millisSinceMidnight / 60000L;
         long cmpValue = this.testMode ? secondsSinceMinute : minutesSinceHour;
         if (cmpValue < this.lastTime) {
            int timeSinceMidnight = (int)(this.testMode ? minutesSinceMidnight : hoursSinceMidnight);
            this.createDataRetirementTask(wldfConfig, now, timeSinceMidnight);
         }

         this.lastTime = cmpValue;
      }
   }

   private void createDataRetirementTask(AccessorConfigurationProvider wldfConfig, long now, int timeSinceMidnight) throws ManagementException {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("Scheduling data retirement tasks as per configuration.");
      }

      ArrayList subtaskList = new ArrayList();
      AccessorMBeanFactory accessorFactory = AccessRuntime.getAccessorInstance().getAccessorMBeanFactory();
      String[] accessorNames = accessorFactory.getAvailableDiagnosticDataAccessorNames();
      int accCount = accessorNames != null ? accessorNames.length : 0;
      int storeCheckPeriod = wldfConfig.getStoreSizeCheckPeriod();
      if (storeCheckPeriod > 0 && timeSinceMidnight % storeCheckPeriod == 0) {
         boolean enableSizeBasedRetirement = false;

         for(int i = 0; !enableSizeBasedRetirement && i < accCount; ++i) {
            AccessorConfiguration accConf = this.getAccessorConfiguration(wldfConfig, accessorNames[i]);
            if (accConf instanceof EditableAccessorConfiguration) {
               EditableAccessorConfiguration eaConf = (EditableAccessorConfiguration)accConf;
               if (eaConf.isParticipantInSizeBasedDataRetirement()) {
                  enableSizeBasedRetirement = true;
               }
            }
         }

         if (enableSizeBasedRetirement) {
            long preferredStoreSizeLimit = (long)wldfConfig.getPreferredStoreSizeLimit() * 838860L;
            File storeDir = new File(wldfConfig.getStoreDirectory());
            long storeSize = this.computeStoreSize(storeDir);
            if (DEBUG_LOGGER.isDebugEnabled()) {
               DEBUG_LOGGER.debug("storeSize=" + storeSize + " preferredStoreSizeLimit=" + preferredStoreSizeLimit);
            }

            if (storeSize > preferredStoreSizeLimit) {
               try {
                  subtaskList.add(new DataRetirementByQuotaTaskImpl());
               } catch (ManagementException var20) {
                  UnexpectedExceptionHandler.handle("Could not create size based retirement task", var20);
               }
            } else {
               if (DEBUG_LOGGER.isDebugEnabled()) {
                  DEBUG_LOGGER.debug("Diagnostic store size (" + storeSize + ") is within limit");
               }

               for(int i = 0; i < accCount; ++i) {
                  AccessorConfiguration accConf = this.getAccessorConfiguration(wldfConfig, accessorNames[i]);
                  if (accConf instanceof EditableAccessorConfiguration) {
                     EditableAccessorConfiguration eaConf = (EditableAccessorConfiguration)accConf;
                     if (eaConf.isParticipantInSizeBasedDataRetirement()) {
                        this.captureCurrentRecordCount(accessorNames[i]);
                     }
                  }
               }
            }
         }
      }

      int size;
      for(size = 0; size < accCount; ++size) {
         AccessorConfiguration accConf = this.getAccessorConfiguration(wldfConfig, accessorNames[size]);
         if (accConf instanceof EditableAccessorConfiguration) {
            EditableAccessorConfiguration eaConf = (EditableAccessorConfiguration)accConf;
            this.createDataRetirementTask(subtaskList, eaConf, now, timeSinceMidnight);
         }
      }

      size = subtaskList.size();
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("Data retirement task has " + size + " subtasks");
      }

      if (size > 0) {
         try {
            DataRetirementTaskRuntimeMBean[] subtasks = new DataRetirementTaskRuntimeMBean[size];
            subtasks = (DataRetirementTaskRuntimeMBean[])((DataRetirementTaskRuntimeMBean[])subtaskList.toArray(subtasks));
            Runnable retirementTask = new DataRetirementTaskImpl("ScheduledDataRetirement", subtasks);
            this.workManager.schedule(retirementTask);
         } catch (Exception var19) {
            UnexpectedExceptionHandler.handle("Could not schedule retirement task", var19);
         }
      }

      if (size > 0) {
         DiagnosticsLogger.logScheduledDataRetirementEnd(size);
      } else if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("Scheduled " + size + " data retirement tasks as per configuration.");
      }

   }

   private void captureCurrentRecordCount(String archiveName) {
      try {
         EditableDataArchive arc = this.getArchive(archiveName);
         if (arc instanceof PersistentStoreDataArchive) {
            PersistentStoreDataArchive archive = (PersistentStoreDataArchive)arc;
            archive.captureCurrentRecordCount();
         }
      } catch (Exception var4) {
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Could not access archive " + archiveName);
         }
      }

   }

   private EditableAccessorConfiguration findWLDFDataRetirement(AccessorConfigurationProvider wldfConfig, String archiveName) {
      AccessorConfiguration accConfig = this.getAccessorConfiguration(wldfConfig, archiveName);
      return accConfig instanceof EditableAccessorConfiguration ? (EditableAccessorConfiguration)accConfig : null;
   }

   private void createDataRetirementTask(ArrayList subtaskList, EditableAccessorConfiguration retirement, long now, int timeSinceMidnight) throws ManagementException {
      if (retirement.isAgeBasedDataRetirementEnabled()) {
         this.createDataRetirementTask(this.getAvailableAccessors(), subtaskList, retirement.getName(), now, timeSinceMidnight, retirement.getRetirementAge(), retirement.getRetirementTime(), retirement.getRetirementPeriod());
      }
   }

   private void createDataRetirementTask(Map availableAccessors, ArrayList subtaskList, String archiveName, long now, int timeSinceMidnight, int retirementAge, int retirementTime, int retirementPeriod) {
      DataRetirementTaskRuntimeMBean task = null;
      boolean isScheduled = this.isScheduledAtCurrentTime(now, timeSinceMidnight, retirementTime, retirementPeriod);
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("DataRetirementScheduler.isScheduledAtCurrentTime: " + archiveName + ": " + isScheduled);
      }

      if (isScheduled) {
         try {
            EditableDataArchive archive = null;
            if (availableAccessors.get(archiveName) != null) {
               archive = this.getArchive(archiveName);
            }

            if (archive != null) {
               long ageInMillis = (long)retirementAge * (this.testMode ? 60000L : 3600000L);
               long endTime = now - ageInMillis;
               task = new DataRetirementByAgeTaskImpl(archive, endTime);
               if (DEBUG_LOGGER.isDebugEnabled()) {
                  DEBUG_LOGGER.debug("DataRetirementScheduler: created retirement task: " + task);
               }
            } else {
               DiagnosticsLogger.logArchiveNotFound(archiveName);
            }
         } catch (Exception var17) {
            UnexpectedExceptionHandler.handle("Could not create age based retirement task for " + archiveName, var17);
         }
      }

      if (task != null) {
         subtaskList.add(task);
      }

   }

   private Map getAvailableAccessors() throws ManagementException {
      Map availableAccessors = new HashMap();

      try {
         AccessRuntime accessor = AccessRuntime.getAccessorInstance();
         String[] names = accessor.getAvailableDiagnosticDataAccessorNames();
         int size = names != null ? names.length : 0;

         for(int i = 0; i < size; ++i) {
            availableAccessors.put(names[i], names[i]);
         }
      } catch (Exception var6) {
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Could not find accessor names", var6);
         }
      }

      return availableAccessors;
   }

   private boolean isScheduledAtCurrentTime(long now, int timeSinceMidnight, int retirementTime, int retirementPeriod) {
      boolean retVal = false;
      int diff = timeSinceMidnight - retirementTime;
      if (diff >= 0) {
         if (retirementPeriod <= 0) {
            retirementPeriod = 1;
         }

         if (diff % retirementPeriod == 0) {
            retVal = true;
         }
      }

      return retVal;
   }

   public DataRetirementTaskRuntimeMBean scheduleDataRetirementTask(EditableDataArchive archive) throws Exception {
      DataRetirementTaskRuntimeMBean task = null;
      String archiveName = archive.getName();
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("Scheduling data retirement task (on demand) for " + archiveName);
      }

      AccessorConfigurationProvider wldfConfig = this.getConfiguration();
      EditableAccessorConfiguration retirement = this.findWLDFDataRetirement(wldfConfig, archiveName);
      int retirementAge = 0;
      if (retirement != null) {
         retirementAge = retirement.getRetirementAge();
      } else if ("HarvestedDataArchive".equals(archiveName) || "EventsDataArchive".equals(archiveName)) {
         retirementAge = 72;
      }

      if (retirementAge > 0) {
         long ageInMillis = (long)retirementAge * (this.testMode ? 60000L : 3600000L);
         long now = System.currentTimeMillis();
         long endTime = now - ageInMillis;
         AccessorMBeanFactory accFactory = AccessRuntime.getAccessorInstance().getAccessorMBeanFactory();
         task = accFactory.createRetirementByAgeTask(archive, endTime);
         this.workManager.schedule((Runnable)task);
      }

      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("Scheduled on-demand data retirement for " + archiveName + " task=" + task);
      }

      return task;
   }

   private AccessorConfigurationProvider getConfiguration() throws ManagementException {
      return AccessRuntime.getAccessorInstance().getAccessorConfigurationProvider();
   }

   private AccessorConfiguration getAccessorConfiguration(AccessorConfigurationProvider config, String accessorName) {
      try {
         return config.getAccessorConfiguration(accessorName);
      } catch (Exception var4) {
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Failed to find accessor for " + accessorName, var4);
         }

         return null;
      }
   }

   private EditableDataArchive getArchive(String name) {
      try {
         AccessRuntime accessor = AccessRuntime.getAccessorInstance();
         DataAccessRuntime runtime = (DataAccessRuntime)accessor.lookupDataAccessRuntime(name);
         EditableDataArchive archive = (EditableDataArchive)runtime.getDiagnosticDataAccessService();
         return archive;
      } catch (Exception var5) {
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Could not obtain archive: " + name, var5);
         }

         return null;
      }
   }

   private long computeStoreSize(File storeDir) {
      long storeSize = 0L;
      if (storeDir.isDirectory()) {
         File[] files = storeDir.listFiles();
         int cnt = files != null ? files.length : 0;

         for(int i = 0; i < cnt; ++i) {
            String fileName = files[i].getName();
            if (fileName.startsWith("WLS_DIAGNOSTICS")) {
               storeSize += files[i].length();
            }
         }
      }

      return storeSize;
   }
}
