package com.oracle.cmm.lowertier.pressure;

import com.oracle.cmm.lowertier.Informer;
import com.oracle.cmm.lowertier.gathering.StatisticsGatherer;
import com.oracle.cmm.lowertier.jfr.FlightRecorderManager;
import com.oracle.cmm.lowertier.jfr.ResourcePressureChangeEvent;
import com.oracle.cmm.lowertier.jfr.ResourcePressureEvaluationEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EvaluationManager {
   private static final Logger LOGGER = Logger.getLogger(EvaluationManager.class.getPackage().getName());
   private static final String PROPERTY_FILE_NAME = "com.oracle.cmm.lowertier.pressure.PROPERTY_FILE_NAME";
   private static final String PROPERTY_RESOURCE_NAME_LINUX = "com/oracle/cmm/lowertier/linux.pressure.properties";
   private static final String PROPERTY_RESOURCE_NAME_WINDOWS = "com/oracle/cmm/lowertier/windows.pressure.properties";
   private static final long DEFAULT_TIMER_INTERVAL = 5000L;
   private static final String TIMER_INTERVAL_NAME = "com.oracle.cmm.lowertier.pressure.TimerInterval";
   private static final String EVALUATOR_NAME = "com.oracle.cmm.lowertier.pressure.Evaluator";
   private static final String ADJUSTER_NAME = "com.oracle.cmm.lowertier.pressure.Adjuster";
   private static final String STATISTICS_NAMES = "com.oracle.cmm.lowertier.pressure.Statistics";
   private static final String TEST_MODE = "com.oracle.cmm.lowertier.pressure.TEST_MODE";
   private static EvaluationManager SINGLETON = null;
   private ResourcePressureEvaluator resEval = null;
   private ResourcePressureAdjustmentDamper adjustmentDamper = null;
   private FlightRecorderManager flightRecorderManager = null;
   private long intervalsSincePressureChange = 0L;
   private int currentMemoryPressure = 0;
   private Properties props = null;
   private long timerInterval = 5000L;
   private long lastFullTaskExecute = 0L;
   private long lastTimerActualInterval = 0L;
   private boolean managerInitialized = false;
   private boolean managerValid = true;
   private Timer intervalTimer;
   private EvaluationTask currentEvaluationTask;
   private static Tester tester;
   private Informer informer = null;
   private StatisticsGatherer[] statistics;
   private boolean testModeEnabled = false;

   public static synchronized EvaluationManager getEvaluationManager() {
      return SINGLETON;
   }

   public static synchronized EvaluationManager getEvaluationManager(Informer informer, Properties properties) throws IllegalStateException {
      if (SINGLETON != null) {
         throw new IllegalStateException("LowerTier was already initialized");
      } else if (informer == null) {
         SINGLETON = new EvaluationManager();
         return SINGLETON;
      } else {
         SINGLETON = new EvaluationManager(informer, properties);
         SINGLETON.initialize();
         return SINGLETON;
      }
   }

   private EvaluationManager() {
      this.managerInitialized = true;
      this.managerValid = false;
   }

   private EvaluationManager(Informer informer, Properties properties) {
      this.informer = informer;
      this.props = properties;
   }

   public synchronized boolean isInitialized() {
      return this.managerInitialized;
   }

   public synchronized boolean isValid() {
      return this.managerValid;
   }

   public synchronized Properties getProperties(String prefix) {
      if (prefix != null && prefix.length() != 0) {
         Properties subProps = new Properties();
         Iterator var3 = this.props.stringPropertyNames().iterator();

         while(var3.hasNext()) {
            String name = (String)var3.next();
            if (name.startsWith(prefix)) {
               subProps.setProperty(name, this.props.getProperty(name));
            }
         }

         return subProps;
      } else {
         return new Properties(this.props);
      }
   }

   public synchronized void addJFREventClass(Class eventClass) {
      if (this.flightRecorderManager != null && eventClass != null) {
         this.flightRecorderManager.addEvent(eventClass);
      }
   }

   public synchronized int getCurrentMemoryPressure() {
      return this.currentMemoryPressure;
   }

   public synchronized long getTimerInterval() {
      return this.timerInterval;
   }

   public synchronized long getLastTimerActualInterval() {
      return this.lastTimerActualInterval;
   }

   private void initialize() {
      AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            EvaluationManager.this.initializeInternal();
            return null;
         }
      });
   }

   private synchronized void initializeInternal() {
      try {
         String modeProp;
         if (!this.loadProperties() || !this.initializeFlightRecorder() || !this.loadStatistics() || !this.loadCurrentEvaluator() || !this.loadCurrentAdjustmentDamper()) {
            this.managerInitialized = true;
            this.managerValid = false;
            modeProp = this.props.getProperty("com.oracle.cmm.lowertier.pressure.TEST_MODE");
            if (modeProp != null) {
               this.testModeEnabled = Boolean.parseBoolean(modeProp);
            }

            return;
         }

         modeProp = this.props.getProperty("com.oracle.cmm.lowertier.pressure.TEST_MODE");
         if (modeProp != null) {
            this.testModeEnabled = Boolean.parseBoolean(modeProp);
         }

         String timerProp = this.props.getProperty("com.oracle.cmm.lowertier.pressure.TimerInterval");
         if (timerProp != null) {
            try {
               this.timerInterval = Long.parseLong(timerProp);
            } catch (Exception var4) {
               if (LOGGER.isLoggable(Level.FINER)) {
                  var4.printStackTrace();
               }

               this.managerInitialized = true;
               this.managerValid = false;
               return;
            }
         }

         int pressure = this.resEval.evaluateMemoryPressure();
         if (pressure < 0 || pressure > 10) {
            if (LOGGER.isLoggable(Level.FINER)) {
               LOGGER.finer("Resource pressure evaluator is broken, it returned initial pressure value out of range: " + pressure);
            }

            this.managerInitialized = true;
            this.managerValid = false;
            return;
         }

         this.currentMemoryPressure = pressure;
         this.managerInitialized = true;
         this.informer.setMemoryPressure(this.currentMemoryPressure);
         this.intervalTimer = new Timer(true);
         this.currentEvaluationTask = new EvaluationTask(this);
         this.intervalTimer.scheduleAtFixedRate(this.currentEvaluationTask, this.timerInterval, this.timerInterval);
      } catch (Throwable var5) {
         if (LOGGER.isLoggable(Level.FINER)) {
            var5.printStackTrace();
         }

         this.managerInitialized = true;
         this.managerValid = false;
      }

   }

   private String getPlatformResourcePropertyFileName() {
      String osName = System.getProperty("os.name");
      return osName != null && osName.startsWith("Windows") ? "com/oracle/cmm/lowertier/windows.pressure.properties" : "com/oracle/cmm/lowertier/linux.pressure.properties";
   }

   private boolean loadProperties() {
      if (this.props != null) {
         return true;
      } else {
         this.props = new Properties();
         String propFileName = System.getProperty("com.oracle.cmm.lowertier.pressure.PROPERTY_FILE_NAME");
         InputStream propertyInputStream = null;

         try {
            boolean var4;
            if (propFileName != null) {
               try {
                  propertyInputStream = new FileInputStream(propFileName);
               } catch (Exception var18) {
                  if (LOGGER.isLoggable(Level.FINER)) {
                     var18.printStackTrace();
                  }

                  var4 = false;
                  return var4;
               }
            } else {
               ClassLoader cl = EvaluationManager.class.getClassLoader();
               propertyInputStream = cl.getResourceAsStream(this.getPlatformResourcePropertyFileName());
               if (propertyInputStream == null) {
                  if (LOGGER.isLoggable(Level.FINER)) {
                     LOGGER.finer("Properties file resource not found");
                  }

                  var4 = false;
                  return var4;
               }
            }

            try {
               this.props.load((InputStream)propertyInputStream);
               if (LOGGER.isLoggable(Level.FINER)) {
                  LOGGER.finer("Properties loaded: " + this.props.toString());
               }

               return true;
            } catch (Exception var17) {
               if (LOGGER.isLoggable(Level.FINER)) {
                  var17.printStackTrace();
               }

               var4 = false;
               return var4;
            }
         } finally {
            if (propertyInputStream != null) {
               try {
                  ((InputStream)propertyInputStream).close();
               } catch (IOException var16) {
               }
            }

         }
      }
   }

   private boolean initializeFlightRecorder() {
      this.flightRecorderManager = FlightRecorderManager.Factory.getInstance();
      return this.flightRecorderManager != null;
   }

   private boolean loadCurrentEvaluator() {
      String evaluatorName = this.props.getProperty("com.oracle.cmm.lowertier.pressure.Evaluator");
      if (evaluatorName == null) {
         if (LOGGER.isLoggable(Level.FINER)) {
            LOGGER.finer("No resource pressure evaluator name was specified");
         }

         return false;
      } else {
         String evaluatorSettings = this.props.getProperty(evaluatorName);
         if (evaluatorSettings == null) {
            if (LOGGER.isLoggable(Level.FINER)) {
               LOGGER.finer("No setting value was provided for " + evaluatorName);
            }

            return false;
         } else {
            String className = null;
            String initialValue = null;
            int firstSeparatorIndex = evaluatorSettings.indexOf(":");
            if (firstSeparatorIndex != -1) {
               if (firstSeparatorIndex == 0) {
                  if (LOGGER.isLoggable(Level.FINER)) {
                     LOGGER.finer("Invalid class name was provided for " + evaluatorName);
                  }

                  return false;
               }

               className = evaluatorSettings.substring(0, firstSeparatorIndex);
               if (firstSeparatorIndex < evaluatorSettings.length() - 1) {
                  initialValue = evaluatorSettings.substring(firstSeparatorIndex + 1);
               }
            } else {
               className = evaluatorSettings;
            }

            try {
               Class evalClazz = Class.forName(className, true, ResourcePressureEvaluator.class.getClassLoader());
               this.resEval = (ResourcePressureEvaluator)evalClazz.newInstance();
               this.resEval.initialize(initialValue);
            } catch (ClassNotFoundException var7) {
               if (LOGGER.isLoggable(Level.FINER)) {
                  var7.printStackTrace();
               }

               return false;
            } catch (InstantiationException var8) {
               if (LOGGER.isLoggable(Level.FINER)) {
                  var8.printStackTrace();
               }

               return false;
            } catch (IllegalAccessException var9) {
               if (LOGGER.isLoggable(Level.FINER)) {
                  var9.printStackTrace();
               }

               return false;
            } catch (Throwable var10) {
               if (LOGGER.isLoggable(Level.FINER)) {
                  var10.printStackTrace();
               }

               return false;
            }

            if (LOGGER.isLoggable(Level.FINER)) {
               LOGGER.finer("evaluator: " + evaluatorName + ", settings: " + evaluatorSettings);
            }

            return true;
         }
      }
   }

   private boolean loadCurrentAdjustmentDamper() {
      String adjusterName = this.props.getProperty("com.oracle.cmm.lowertier.pressure.Adjuster");
      if (adjusterName == null) {
         return true;
      } else {
         String adjusterSettings = this.props.getProperty(adjusterName);
         if (adjusterSettings == null) {
            if (LOGGER.isLoggable(Level.FINER)) {
               LOGGER.finer("No setting value was provided for " + adjusterName);
            }

            return false;
         } else {
            String className = null;
            String initialValue = null;
            int firstSeparatorIndex = adjusterSettings.indexOf(":");
            if (firstSeparatorIndex != -1) {
               if (firstSeparatorIndex == 0) {
                  if (LOGGER.isLoggable(Level.FINER)) {
                     LOGGER.finer("Invalid class name was provided for " + adjusterName);
                  }

                  return false;
               }

               className = adjusterSettings.substring(0, firstSeparatorIndex);
               if (firstSeparatorIndex < adjusterSettings.length() - 1) {
                  initialValue = adjusterSettings.substring(firstSeparatorIndex + 1);
               }
            } else {
               className = adjusterSettings;
            }

            try {
               Class adjClazz = Class.forName(className, true, ResourcePressureAdjustmentDamper.class.getClassLoader());
               synchronized(this) {
                  this.adjustmentDamper = (ResourcePressureAdjustmentDamper)adjClazz.newInstance();
                  this.adjustmentDamper.initialize(initialValue);
               }
            } catch (ClassNotFoundException var10) {
               if (LOGGER.isLoggable(Level.FINER)) {
                  var10.printStackTrace();
               }

               return false;
            } catch (InstantiationException var11) {
               if (LOGGER.isLoggable(Level.FINER)) {
                  var11.printStackTrace();
               }

               return false;
            } catch (IllegalAccessException var12) {
               if (LOGGER.isLoggable(Level.FINER)) {
                  var12.printStackTrace();
               }

               return false;
            } catch (Throwable var13) {
               if (LOGGER.isLoggable(Level.FINER)) {
                  var13.printStackTrace();
               }

               return false;
            }

            if (LOGGER.isLoggable(Level.FINER)) {
               LOGGER.finer("damper: " + adjusterName + ", settings: " + adjusterSettings);
            }

            return true;
         }
      }
   }

   public synchronized StatisticsGatherer[] getStatisticsGatherers() {
      return this.statistics;
   }

   private boolean loadStatistics() {
      String[] statisticsNames = ParseUtils.parseCommaSeparatedValues(this.props.getProperty("com.oracle.cmm.lowertier.pressure.Statistics"));
      if (statisticsNames.length == 0) {
         if (LOGGER.isLoggable(Level.FINER)) {
            LOGGER.finer("No statistics were specified");
         }

         return true;
      } else {
         this.statistics = new StatisticsGatherer[statisticsNames.length];

         for(int i = 0; i < statisticsNames.length; ++i) {
            if (statisticsNames[i] == null) {
               this.statistics[i] = null;
            } else {
               String trimmed = statisticsNames[i].trim();
               if (trimmed.length() == 0) {
                  this.statistics[i] = null;
               } else {
                  this.statistics[i] = this.findAndLoadStatisticsGatherer(trimmed);
                  if (this.statistics[i] == null) {
                     return false;
                  }
               }
            }
         }

         return true;
      }
   }

   private StatisticsGatherer findAndLoadStatisticsGatherer(String name) {
      String statsProperty = this.props.getProperty(name);
      if (statsProperty != null && statsProperty.length() != 0) {
         String className = null;
         String initialValue = null;
         int firstSeparatorIndex = statsProperty.indexOf(":");
         if (firstSeparatorIndex != -1) {
            if (firstSeparatorIndex == 0) {
               if (LOGGER.isLoggable(Level.FINER)) {
                  LOGGER.finer("Invalid class name was provided for " + name);
               }

               return null;
            }

            className = statsProperty.substring(0, firstSeparatorIndex);
            if (firstSeparatorIndex < statsProperty.length() - 1) {
               initialValue = statsProperty.substring(firstSeparatorIndex + 1);
            }
         } else {
            className = statsProperty;
         }

         StatisticsGatherer gatherer = null;

         try {
            Class statClazz = Class.forName(className, true, StatisticsGatherer.class.getClassLoader());
            gatherer = (StatisticsGatherer)statClazz.newInstance();
            gatherer.initialize(initialValue);
         } catch (ClassNotFoundException var8) {
            if (LOGGER.isLoggable(Level.FINER)) {
               var8.printStackTrace();
            }

            return null;
         } catch (InstantiationException var9) {
            if (LOGGER.isLoggable(Level.FINER)) {
               var9.printStackTrace();
            }

            return null;
         } catch (IllegalAccessException var10) {
            if (LOGGER.isLoggable(Level.FINER)) {
               var10.printStackTrace();
            }

            return null;
         } catch (Throwable var11) {
            if (LOGGER.isLoggable(Level.FINER)) {
               var11.printStackTrace();
            }

            return null;
         }

         if (LOGGER.isLoggable(Level.FINER)) {
            LOGGER.finer("StatisticsGatherer: " + name + ", settings: " + statsProperty);
         }

         return gatherer;
      } else {
         if (LOGGER.isLoggable(Level.FINER)) {
            LOGGER.finer("No statistics property found for: " + name);
         }

         return null;
      }
   }

   public synchronized void gatherStatistics() {
      if (this.statistics != null) {
         for(int i = 0; i < this.statistics.length; ++i) {
            if (this.statistics[i] != null) {
               this.statistics[i].gatherStatistics();
               if (LOGGER.isLoggable(Level.FINER)) {
                  LOGGER.finer(this.statistics[i].toString());
               }
            }
         }

      }
   }

   private synchronized void evaluateResourcePressure(EvaluationTask evaluationTask) {
      printIfCICGlobal("evaluateResourcePressure");
      if (this.managerValid) {
         if (this.currentEvaluationTask != evaluationTask) {
            if (LOGGER.isLoggable(Level.FINER)) {
               LOGGER.finer("evaluateResourcePressure called with defunct TimerTask, no computations performed");
            }

         } else {
            long currentTime = System.currentTimeMillis();
            if (currentTime - this.lastFullTaskExecute < this.timerInterval / 2L) {
               if (LOGGER.isLoggable(Level.FINER)) {
                  LOGGER.finer("evaluateResourcePressure backed up likely called as a catch-up, skipping: " + (currentTime - this.lastFullTaskExecute));
               }

            } else {
               this.lastTimerActualInterval = currentTime - this.lastFullTaskExecute;
               this.lastFullTaskExecute = currentTime;
               int evaluatedMemoryPressure = this.resEval.evaluateMemoryPressure();
               if (evaluatedMemoryPressure < 0 || evaluatedMemoryPressure > 10) {
                  if (LOGGER.isLoggable(Level.FINER)) {
                     LOGGER.finer("invalid evaluated pressure returned (" + evaluatedMemoryPressure + "), the pressure evaluation is broken, leaving pressure unchanged");
                  }

                  evaluatedMemoryPressure = this.currentMemoryPressure;
               }

               int adjustedMemoryPressure = this.adjustmentDamper != null ? this.adjustmentDamper.adjustMemoryPressure(evaluatedMemoryPressure) : evaluatedMemoryPressure;
               if (this.flightRecorderManager.isEventGenerationEnabled(2)) {
                  ResourcePressureEvaluationEvent changedEvent = new ResourcePressureEvaluationEvent();
                  changedEvent.timerInterval = this.lastTimerActualInterval;
                  changedEvent.memoryPressureBefore = this.currentMemoryPressure;
                  changedEvent.evaluatedMemoryPressure = evaluatedMemoryPressure;
                  changedEvent.adjustedMemoryPressure = adjustedMemoryPressure;
                  changedEvent.commit();
               }

               if (adjustedMemoryPressure < 0 || adjustedMemoryPressure > 10) {
                  if (LOGGER.isLoggable(Level.FINER)) {
                     LOGGER.finer("Invalid adjusted pressure returned (" + adjustedMemoryPressure + "), the adjustment damper is broken, setting pressure to the unadjusted value (" + evaluatedMemoryPressure + ")");
                  }

                  adjustedMemoryPressure = evaluatedMemoryPressure;
               }

               if (LOGGER.isLoggable(Level.FINER)) {
                  LOGGER.finer("evaluateResourcePressure curr: " + this.currentMemoryPressure + ", adj: " + adjustedMemoryPressure);
               }

               if (this.currentMemoryPressure != adjustedMemoryPressure) {
                  this.informer.setMemoryPressure(adjustedMemoryPressure);
                  if (this.flightRecorderManager.isEventGenerationEnabled(1)) {
                     ResourcePressureChangeEvent changedEvent = new ResourcePressureChangeEvent();
                     changedEvent.timerInterval = this.lastTimerActualInterval;
                     changedEvent.evaluatedMemoryPressure = evaluatedMemoryPressure;
                     changedEvent.currentMemoryPressure = adjustedMemoryPressure;
                     changedEvent.previousMemoryPressure = this.currentMemoryPressure;
                     changedEvent.intervalsSinceLastChange = this.intervalsSincePressureChange;
                     changedEvent.commit();
                  }

                  this.currentMemoryPressure = adjustedMemoryPressure;
                  this.intervalsSincePressureChange = 0L;
               } else {
                  ++this.intervalsSincePressureChange;
               }

            }
         }
      }
   }

   public static void main(String[] args) {
      if (args != null && args.length == 1 && args[0] != null) {
         long testDuration = Long.parseLong(args[0]);
         tester = new Tester();
         Thread testerThread = new Thread(tester);
         testerThread.setDaemon(true);
         testerThread.start();
         EvaluationManager mgr = getEvaluationManager(tester, (Properties)null);
         if (!mgr.isValid()) {
            LOGGER.info("The EvaluationManager failed to initialize properly, check that the settings are correct");
         } else {
            waitForDuration(testDuration);
            System.exit(0);
         }
      } else {
         usage();
      }
   }

   private static long waitForDuration(long durationMillis) {
      long startTime = System.currentTimeMillis();
      long endTime = startTime + durationMillis;
      long remaining = durationMillis;

      long lastTime;
      for(lastTime = startTime; remaining > 0L; remaining = endTime - lastTime) {
         try {
            Thread.sleep(remaining);
         } catch (InterruptedException var11) {
         }

         lastTime = System.currentTimeMillis();
      }

      return lastTime - startTime;
   }

   private static void usage() {
      LOGGER.info("usage: java EvaluationManager timeToRunMillis");
   }

   public static final synchronized void reset() {
      if (SINGLETON != null && SINGLETON.getTestModeEnabled()) {
         SINGLETON.cleanup();
         SINGLETON = null;
      }
   }

   private final synchronized boolean getTestModeEnabled() {
      return this.testModeEnabled;
   }

   private final synchronized void cleanup() {
      if (this.testModeEnabled) {
         LOGGER.info("TEST MODE CLEANUP");
         if (this.intervalTimer != null) {
            this.intervalTimer.cancel();
         }

      }
   }

   private static void printIfCICGlobal(String location) {
      if (LOGGER.isLoggable(Level.FINER)) {
         try {
            Class cicMgrClazz = Class.forName("weblogic.invocation.ComponentInvocationContextManager");
            Class cicClazz = Class.forName("weblogic.invocation.ComponentInvocationContext");
            Method getInstanceMtd = cicMgrClazz.getDeclaredMethod("getInstance");
            Method getCurrCICMtd = cicMgrClazz.getDeclaredMethod("getCurrentComponentInvocationContext");
            Method isGlobalRTMtd = cicClazz.getDeclaredMethod("isGlobalRuntime");
            Object cicMgr = getInstanceMtd.invoke((Object)null);
            Object cic = getCurrCICMtd.invoke(cicMgr);
            Boolean isGlobal = (Boolean)isGlobalRTMtd.invoke(cic);
            LOGGER.finer("CMMLowerTier " + location + " GLOBAL = " + isGlobal);
         } catch (Throwable var9) {
         }
      }

   }

   public static class Tester implements Informer, Runnable {
      private String jvmName = null;
      private int currentMemoryPressure = 0;
      private int hogMoreAtNoPressure = 0;
      private byte[] baseMemoryChunk = null;
      private Object[] incrementalMemoryChunks = null;
      private int baseSize = 0;
      private int incrementalSize = 0;
      private byte[] hogMemoryChunk;

      public void run() {
         this.jvmName = ManagementFactory.getRuntimeMXBean().getName();
         MemoryUsage memUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
         long baseUsed = memUsage.getUsed();
         long max = memUsage.getMax();
         long committed = memUsage.getCommitted();
         long committedAvailableNow = committed - baseUsed;
         long possibleMaxNow = max - baseUsed;
         this.hogMoreAtNoPressure = (int)((possibleMaxNow > committedAvailableNow ? possibleMaxNow - committedAvailableNow : 0L) / 2L);
         this.baseSize = (int)(committedAvailableNow / 4L);
         this.incrementalSize = (int)((committedAvailableNow - committedAvailableNow / 4L) / 11L);
         this.incrementalMemoryChunks = new Object[11];
         this.currentMemoryPressure = EvaluationManager.getEvaluationManager().getCurrentMemoryPressure();
         this.adjustMemoryUsage(this.currentMemoryPressure);

         while(true) {
            EvaluationManager.waitForDuration(1000L);
         }
      }

      public void setMemoryPressure(int newPressure) {
         if (newPressure != this.currentMemoryPressure) {
            this.adjustMemoryUsage(newPressure);
            this.currentMemoryPressure = newPressure;
         }

      }

      private void adjustMemoryUsage(int pressure) {
         EvaluationManager.LOGGER.info(this.jvmName + "," + System.currentTimeMillis() + "," + pressure);
         if (this.baseMemoryChunk == null) {
            this.baseMemoryChunk = this.getAndFill(this.baseSize);
         }

         for(int i = 0; i <= 10; ++i) {
            if (i >= pressure) {
               this.incrementalMemoryChunks[i] = this.getAndFill(this.incrementalSize);
            } else {
               this.incrementalMemoryChunks[i] = null;
            }
         }

         if (pressure == 0 && this.hogMoreAtNoPressure > 0) {
            this.hogMemoryChunk = this.getAndFill(this.hogMoreAtNoPressure);
         } else {
            this.hogMemoryChunk = null;
         }

         if (this.hogMemoryChunk != null) {
            EvaluationManager.LOGGER.info("oink oink oink");
         }

      }

      private byte[] getAndFill(int size) {
         byte[] newArr = null;

         try {
            newArr = new byte[size];
            Arrays.fill(newArr, (byte)-1);
         } catch (Throwable var4) {
         }

         return newArr;
      }
   }

   private static class EvaluationTask extends TimerTask {
      private EvaluationManager evaluationManager;

      EvaluationTask(EvaluationManager evaluationManager) {
         this.evaluationManager = evaluationManager;
      }

      public void run() {
         this.evaluationManager.evaluateResourcePressure(this);
      }
   }
}
