package weblogic.work;

import java.util.Date;
import java.util.Random;
import java.util.TimerTask;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;

public final class IncrementAdvisor2 extends TimerTask implements IncrementAdvisorIntf {
   private static final DebugCategory debugSelfTuning = Debug.getCategory("weblogic.IncrementAdvisor");
   private static final String minPoolSizeProp = "weblogic.threadpool.MinPoolSize";
   private static final int MIN_POOL_SIZE = initProperty("weblogic.threadpool.MinPoolSize", -1);
   private static final String maxPoolSizeProp = "weblogic.threadpool.MaxPoolSize";
   private static final int MAX_POOL_SIZE = initProperty("weblogic.threadpool.MaxPoolSize", -1);
   private static final int DEFAULT_MIN_POOL_SIZE = 1;
   private static final int DEFAULT_MAX_POOL_SIZE = 100;
   private static int minThreadPoolSize = 1;
   private static int maxThreadPoolSize = 100;
   public static final double PERIOD = 2000.0;
   private static final double NOVELTY_ATTRACTION = 0.5;
   private static final float ADD_SAMPLE_MARGIN = 0.98F;
   private static final Random RANDOM = new Random(123L);
   private static double HORIZON = 50.0;
   private static final int Y_THRESHOLD_FOR_CPU_INTENSIVE_LOAD = 15000;
   private static final int HIGH_THROUGHPUT_THRESHOLD = 20000;
   private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
   static int FAST_RAMP_UP_THREADCOUNT_LIMIT = initProperty("weblogic.threadpool.fastRampUpThresholdLimit", 16);
   private static boolean incrementByCPUCount = false;
   private static int LARGEST_INCR_INTERVAL = 20;
   static int LARGEST_HOGGER_COMPENSATION;
   private static int LONG_QUEUE_FACTOR;
   private static int VERY_LONG_QUEUE_FACTOR;
   private static final double[] IMPORTANCE_SAMPLED_NORM_CUMULATIVE;
   private static final double IMPORTANCE_SAMPLING_SCALE = 0.024760009915212;
   private static final double LARGEST_SAMPLED_NORM_CUMULATIVE;
   private static final double VERY_SMALL_P;
   private static final int ZERO_QUEUE_TOUCHED_DURATION_CRITERIA;
   private static final int NUMBER_OF_ADDED_THREADS;
   private int direction = 0;
   private SmoothedStats[] throughput = new SmoothedStats[0];
   private int zeroCompletedDuration;
   private int zeroQueueTouchedDuration;
   private int dCompleted;
   private long timeStamp = System.currentTimeMillis();
   private int attemptToIncrementCount;
   private int maxY = 0;
   private double y;
   private int n = 0;
   private double maxThroughput;
   private double lastThroughput;
   private static boolean allowMaxValuesReset;
   private int previousSampleIndex;
   private int nextSampleIndex;
   private int cumulativeThreadUse;
   private int numSuspiciousInARow;
   private long workManagerDumpCount;

   private static int initProperty(String name, int defaultValue) {
      try {
         return Integer.getInteger(name, defaultValue);
      } catch (SecurityException var3) {
         return defaultValue;
      } catch (NumberFormatException var4) {
         return defaultValue;
      }
   }

   private static double normCumulative(double z) {
      if (Double.isNaN(z)) {
         return z;
      } else if (z < -2.5) {
         return VERY_SMALL_P;
      } else if (z > 2.5) {
         return 1.0 - VERY_SMALL_P;
      } else {
         int idx = (int)Math.round(Math.log(z < 0.0 ? 1.0 - z : 1.0 + z) / 0.024760009915212);

         assert idx >= 0 && idx < IMPORTANCE_SAMPLED_NORM_CUMULATIVE.length : "anomalous z: " + z + " (raw bits: " + Long.toString(Double.doubleToLongBits(z), 16) + ") wasn't filtered out by isNaN and comparisons to 2.5";

         double p = IMPORTANCE_SAMPLED_NORM_CUMULATIVE[idx];
         return z < 0.0 ? 1.0 - p : p;
      }
   }

   public static void setIncrementByCPUCount(boolean val) {
      incrementByCPUCount = val;
   }

   private void addSample(int n, double y) {
      if (n != 0) {
         if (debugEnabled()) {
            log("adding sample. n=" + n + ", " + y);
         }

         SmoothedStats ss = this.throughput[n];
         if (ss == null) {
            ss = this.throughput[n] = new SmoothedStats(y);
         } else {
            if (ss.exceedsZ(y, 3.0)) {
               HORIZON = (HORIZON + 1.0) / 2.0;
               if (debugEnabled()) {
                  log("outlier z= " + ss.zScore(y) + ", y=" + y + ", avg=" + ss.getAvg() + " halve horizon to " + HORIZON);
               }
            } else {
               ++HORIZON;
            }

            ss.add(y);
         }

         this.lastThroughput = ss.getAvg();
      }
   }

   private void showThroughput() {
      if (debugEnabled()) {
         String sep = "";
         String message = "";

         for(int i = 0; i < this.throughput.length; ++i) {
            SmoothedStats ss = this.throughput[i];
            message = message + (ss == null ? sep + "N/A" : sep + (int)(ss.getAvg() + 0.5));
            sep = "\t";
         }

         log(message);
      }

   }

   public void run() {
      try {
         this.evaluateThreadCount();
      } catch (Throwable var2) {
         if (debugEnabled()) {
            log(var2.getMessage());
         }

         if (var2 instanceof Error) {
            WorkManagerLogger.logSelfTuningStopped(var2);
            throw var2;
         }
      }

   }

   private void evaluateThreadCount() {
      long time = System.currentTimeMillis();
      long elapsedTime = time - this.timeStamp;
      if (elapsedTime != 0L) {
         boolean debug = debugEnabled();
         RequestManager manager = RequestManager.getInstance();
         manager.activeRequestClassesInOverload();
         long[] holder = new long[2];
         int currentdCompleted = (int)manager.computeThreadUsageNS(holder);
         this.dCompleted += currentdCompleted;
         int threadUse = (int)holder[0];
         int threadUseByHogs = (int)holder[1];
         if (this.cumulativeThreadUse > 0) {
            threadUse += this.cumulativeThreadUse;
            this.cumulativeThreadUse = 0;
         }

         if (this.dCompleted == 0) {
            ++this.zeroCompletedDuration;
         } else {
            this.zeroCompletedDuration = 0;
         }

         int dTouched = manager.getAndResetQueueTouched();
         if (dTouched == 0) {
            ++this.zeroQueueTouchedDuration;
         } else {
            this.zeroQueueTouchedDuration = 0;
         }

         int hogDuration = this.dCompleted != 0 && 7 * threadUse >= 4000 * this.dCompleted ? div(7 * threadUse, this.dCompleted) : 4000;
         this.n = manager.purgeHogs(hogDuration);
         int hogSize = manager.getHogSize();
         int numThreads = this.n + hogSize;
         int numIdleThreads = manager.getIdleThreadCount();
         boolean noIdleThreads = numIdleThreads == 0;
         int numThreadsForThroughputCalc = RequestManager.INCLUDE_HOGS_IN_SELF_TUNING_STATS ? numThreads : this.n;
         if (this.direction > 0) {
            --this.direction;
         } else if (this.direction < 0) {
            ++this.direction;
         }

         if (debug) {
            log("all threads: " + manager.getExecuteThreadCount() + ", healthy threads: " + this.n + ", hogs: " + hogSize + ", idle threads: " + numIdleThreads + ", standby threads: " + manager.getStandbyCount());
         }

         int usedThreads = (int)(((long)threadUse + elapsedTime / 2L) / elapsedTime);
         this.y = 1000.0 * (double)this.dCompleted / (double)elapsedTime;
         int queueSize = manager.queue.size();
         boolean queueBusy = queueSize > this.n;
         if (debug) {
            log("y,dCompleted,elapsedTime,threadUse,n,usedThreads,queuelength,queueBusy=\t" + (int)(this.y + 0.5) + "\t" + this.dCompleted + "\t" + elapsedTime + "\t" + threadUse + "\t" + this.n + "\t" + usedThreads + "\t" + queueSize + "\t" + queueBusy);
         }

         if (WorkManagerDumper.getInstance().isEnabled()) {
            if (this.workManagerDumpCount % 30L == 0L) {
               String debugData = "ia2:" + manager.getConciseStateDump() + ",healthy:" + this.n + ",zerocomp:" + this.zeroCompletedDuration + ",zerotouch:" + this.zeroQueueTouchedDuration + " at " + new Date();
               WorkManagerDumper.getInstance().setDebugData(debugData);
            }

            ++this.workManagerDumpCount;
         }

         int minPoolSize;
         if (queueSize > 0 && this.dCompleted > 0 && (double)usedThreads < (double)this.n * 0.95 && hogSize == 0) {
            if (debug) {
               log("Suspicious: throughput= " + this.y + ", usedThreads= " + usedThreads);
            }

            if (this.numSuspiciousInARow++ < 5) {
               minPoolSize = threadUse + threadUseByHogs;
               int usedThreadsInclHogs = (int)(((long)minPoolSize + elapsedTime / 2L) / elapsedTime);
               if ((double)usedThreadsInclHogs >= (double)this.n * 0.95) {
                  this.timeStamp = time;
                  manager.updateElapsedTime(elapsedTime);
                  if (debug) {
                     log("Calling updateElapsedTime due to usage by hogs: n=" + this.n + ", usedThreads=" + usedThreads + ", usedThreadsInclHogs=" + usedThreadsInclHogs);
                  }
               }

               this.cumulativeThreadUse = threadUse;
               return;
            }

            if (debug) {
               log(this.numSuspiciousInARow + " consecutive suspicious. Proceeding...");
            }
         }

         this.numSuspiciousInARow = 0;
         if (numThreadsForThroughputCalc >= this.throughput.length) {
            SmoothedStats[] old = this.throughput;
            this.throughput = new SmoothedStats[numThreadsForThroughputCalc + 1];
            System.arraycopy(old, 0, this.throughput, 0, old.length);
         }

         if ((float)usedThreads >= (float)this.n * 0.98F || queueBusy) {
            this.addSample(numThreadsForThroughputCalc, this.y);
         }

         this.dCompleted = 0;
         this.timeStamp = time;
         manager.updateElapsedTime(elapsedTime);
         minPoolSize = getMinThreadPoolSize();
         boolean longQueue = (double)(queueSize / LONG_QUEUE_FACTOR) > this.y;
         boolean veryLongQueue = (double)(queueSize / VERY_LONG_QUEUE_FACTOR) > this.y;
         int hoggersToCompensate = 0;
         if (noIdleThreads && longQueue && hogSize >= this.n) {
            hoggersToCompensate = LARGEST_HOGGER_COMPENSATION;
         }

         boolean noThreadsForNonMinTCWork = manager.isAllNonStandbyThreadsExecutingMinTCWork();
         boolean doNotShrinkPool = noIdleThreads && hogSize >= this.n || this.n < minPoolSize || veryLongQueue || noThreadsForNonMinTCWork;
         if (doNotShrinkPool) {
            if (debug) {
               log("Do not shrink pool: hogSize:" + hogSize + ", n" + this.n + ",veryLongQueue:" + veryLongQueue);
            }

            if (this.direction < 0) {
               this.direction = 0;
            }
         }

         if (queueBusy && this.zeroCompletedDuration > 2) {
            if (debug) {
               log("Encountered zero completion in successive runs. Adding a new thread to prevent deadlock");
            }

            this.incrPoolSize(manager, 1);
         } else if (this.maxY == 0 && this.y > 15000.0) {
            reset(manager, this.n);
            this.initMaxValues(manager.getTotalRequestsCount(), this.y);
         } else if (queueSize <= 0 || this.zeroQueueTouchedDuration <= ZERO_QUEUE_TOUCHED_DURATION_CRITERIA && (!noIdleThreads || !noThreadsForNonMinTCWork)) {
            manager.computeThreadPriorities();
            this.initMaxValues(manager.getTotalRequestsCount(), this.y);
            this.showThroughput();
            this.initIndexes(numThreadsForThroughputCalc);
            double rand = (double)RANDOM.nextFloat();
            double decrAttraction = this.getDecrAttraction(numThreadsForThroughputCalc, minPoolSize);
            double incrAttraction = this.getIncrAttraction(numThreadsForThroughputCalc);
            if (debug) {
               log("attraction decr= " + decrAttraction + ", incr= " + incrAttraction + ", rand= " + rand);
            }

            int incrementInterval;
            if (this.n < minPoolSize) {
               incrementInterval = minPoolSize - this.n;
               if (incrementInterval >= hoggersToCompensate) {
                  if (debug) {
                     log("Adding new threads; Total threads excluding hoggers are less than min pool size; n: " + this.n + ", minPoolSize: " + minPoolSize);
                  }

                  this.incrPoolSize(manager, incrementInterval);
                  return;
               }
            }

            if (decrAttraction >= incrAttraction && !doNotShrinkPool) {
               if (decrAttraction > rand) {
                  incrementInterval = (this.previousSampleIndex - numThreadsForThroughputCalc - 1) / 2;
                  if (debug) {
                     log("Shrinking with attraction " + decrAttraction + " to thread count= " + (numThreads + incrementInterval));
                  }

                  this.incrPoolSize(manager, incrementInterval);
               } else if (this.direction <= 0 && this.exploreDecrementByOne(incrAttraction, decrAttraction, numThreadsForThroughputCalc)) {
                  this.incrPoolSize(manager, -1);
               } else if (debug) {
                  log("Not shrinking the pool even though decrAttr >= incrAttr");
               }

               this.attemptToIncrementCount = 0;
            } else {
               if ((incrAttraction > decrAttraction && queueSize > 0 || incrAttraction >= decrAttraction && veryLongQueue) && this.n < getMaxThreadPoolSize()) {
                  if (this.attemptToIncrementCount >= 3 || queueBusy && incrAttraction > rand) {
                     this.attemptToIncrementCount = 0;
                     if (incrementByCPUCount) {
                        if (debug) {
                           log("Current thread count n = " + this.n + "; incrementing it by " + CPU_COUNT);
                        }

                        this.incrPoolSize(manager, CPU_COUNT);
                        return;
                     }

                     incrementInterval = 1;
                     if (!this.mustIncrementByOne(incrAttraction, decrAttraction, this.n, this.nextSampleIndex)) {
                        int maxIncrement = this.getMaxIncrement(numThreadsForThroughputCalc, this.n, this.nextSampleIndex, CPU_COUNT);
                        incrementInterval = Math.max(maxIncrement, this.getIncrementInterval((int)this.y));
                     }

                     if (debug) {
                        log("Growing with attraction= " + incrAttraction + ", increment interval= " + incrementInterval + " to " + (numThreads + incrementInterval));
                     }

                     this.incrPoolSize(manager, incrementInterval);
                     return;
                  }

                  if (debug) {
                     log("Not incrementing thread count. attemptToIncrementCount=" + this.attemptToIncrementCount + ", queueBusy=" + queueBusy + ", incrAttraction=" + incrAttraction + ", rand=" + rand);
                  }
               }

               if (hoggersToCompensate > 0) {
                  if (debug) {
                     log("Adding new threads to compensate for hoggers");
                  }

                  this.incrPoolSize(manager, Math.min(LARGEST_HOGGER_COMPENSATION, hoggersToCompensate));
               } else {
                  ++this.attemptToIncrementCount;
                  if (debug) {
                     log("No action. Incremented attemptToIncrementCount to " + this.attemptToIncrementCount);
                  }

               }
            }
         } else {
            if (debug) {
               log("Encountered zero CalendarQueue watch activity in " + ZERO_QUEUE_TOUCHED_DURATION_CRITERIA + " several periods.Adding " + NUMBER_OF_ADDED_THREADS + "  thread(s) to review CalendarQueue");
            }

            manager.incrPoolSize(NUMBER_OF_ADDED_THREADS);
         }
      }
   }

   int getMaxIncrement(int numThreadsForThroughputCalc, int numThreads, int nextSampleIndex, int cpuCount) {
      int maxIncrement = (nextSampleIndex - numThreadsForThroughputCalc + 1) / 2;
      if (nextSampleIndex == 0) {
         int FAST_RAMP_UP_THREADCOUNT = Math.min(FAST_RAMP_UP_THREADCOUNT_LIMIT, cpuCount);
         if (numThreads > FAST_RAMP_UP_THREADCOUNT) {
            maxIncrement = cpuCount;
         } else {
            maxIncrement = 1;
         }
      }

      return Math.max(maxIncrement, 0);
   }

   private boolean exploreDecrementByOne(double incrAttraction, double decrAttraction, int n) {
      if (n > 0 && decrAttraction <= VERY_SMALL_P && incrAttraction <= VERY_SMALL_P) {
         if (debugEnabled()) {
            log("exploreDecrementByOne returning true");
         }

         this.throughput[n - 1] = null;
         return true;
      } else {
         return false;
      }
   }

   private boolean mustIncrementByOne(double incrAttraction, double decrAttraction, int n, int nextSampleIndex) {
      if (nextSampleIndex > n + 1 && decrAttraction <= VERY_SMALL_P && incrAttraction <= VERY_SMALL_P) {
         if (debugEnabled()) {
            log("mustIncrementByOne returning true");
         }

         SmoothedStats[] old = this.throughput;
         this.throughput = new SmoothedStats[n];
         System.arraycopy(old, 0, this.throughput, 0, this.throughput.length);
         return true;
      } else {
         return false;
      }
   }

   private void incrPoolSize(RequestManager manager, int interval) {
      if ((interval <= 0 || this.direction >= 0) && (interval >= 0 || this.direction <= 0)) {
         if (debugEnabled()) {
            log("Adjusting pool size by " + interval);
         }

         manager.incrPoolSize(interval);
         if (interval > 0) {
            allowMaxValuesReset = true;
         }

         this.direction = interval > 0 ? 5 : -5;
      } else {
         if (debugEnabled()) {
            log("Not adjusting pool size by " + interval + " because we had just adjusted pool size in opposite direction");
         }

      }
   }

   private static void reset(RequestManager manager, int n) {
      if (n > CPU_COUNT) {
         if (debugEnabled()) {
            log("resetting thread count to cpucount=" + CPU_COUNT);
         }

         manager.incrPoolSize(CPU_COUNT - n);
      }
   }

   private void initMaxValues(int totalRequestsCount, double y) {
      if (totalRequestsCount == 0 && y < (double)(CPU_COUNT * 5) && allowMaxValuesReset) {
         if (debugEnabled()) {
            log("RESETTING maxThroughput and maxY");
         }

         this.maxThroughput = (double)(this.maxY = 0);
         this.lastThroughput = 0.0;
      } else {
         this.maxThroughput = Math.max(this.maxThroughput, this.lastThroughput);
         if (debugEnabled()) {
            log("maxThroughput=" + this.maxThroughput + ",lastThroughput=" + this.lastThroughput);
         }

         this.maxY = (int)Math.max((double)this.maxY, y);
         if (debugEnabled()) {
            log("maxY=" + this.maxY + ", y=" + y);
         }
      }

   }

   private void initIndexes(int n) {
      this.previousSampleIndex = 0;
      this.nextSampleIndex = 0;
      SmoothedStats var10000 = this.throughput[n];
      SmoothedStats first = null;

      int count;
      for(count = n - 1; count >= 0; --count) {
         if (this.throughput[count] != null && (first == null || first.getAvg() < this.throughput[count].getAvg())) {
            first = this.throughput[count];
            this.previousSampleIndex = count;
         }
      }

      first = null;

      for(count = n + 1; count < this.throughput.length; ++count) {
         if (this.throughput[count] != null && (first == null || first.getAvg() < this.throughput[count].getAvg())) {
            first = this.throughput[count];
            this.nextSampleIndex = count;
         }
      }

   }

   private int getIncrementInterval(int y) {
      if (this.maxY != 0 && y != 0) {
         int factor = this.maxY / y;
         if (factor <= 1) {
            return 1;
         } else {
            int incr = Math.min(LARGEST_INCR_INTERVAL, 3 * factor + 1 + 1) / 2;
            if (debugEnabled()) {
               log("Calculated increment interval=" + incr);
            }

            return incr;
         }
      } else {
         return 1;
      }
   }

   private static int div(int a, int b) {
      return (a + b / 2) / b;
   }

   private double getIncrAttraction(int n) {
      if (debugEnabled()) {
         log("[getIncrAttraction] next=" + this.nextSampleIndex + ", current=" + n);
      }

      if (this.nextSampleIndex == 0) {
         return 0.5;
      } else {
         SmoothedStats ss = this.throughput[this.nextSampleIndex];
         return ss != null && this.throughput[n] != null ? this.throughput[n].pLessThan(ss) : 0.5;
      }
   }

   private double getDecrAttraction(int n, int minPoolSize) {
      if (debugEnabled()) {
         log("[getDecrAttraction] previous=" + this.previousSampleIndex + ", current=" + n);
      }

      if (n <= minPoolSize) {
         return 0.0;
      } else if (this.previousSampleIndex == 0) {
         return 0.5;
      } else {
         SmoothedStats ss = this.throughput[this.previousSampleIndex];
         if (ss != null && this.throughput[n] != null) {
            double val = this.throughput[n].pLessThan(ss);
            if (val > 0.5 && this.lastThroughput < 20000.0 && this.notEnoughVariationFromMax()) {
               if (debugEnabled()) {
                  log("decrAttraction is " + val + " but limiting it to 0.5");
               }

               return 0.5;
            } else {
               return val;
            }
         } else {
            return 0.5;
         }
      }
   }

   private boolean notEnoughVariationFromMax() {
      if (this.lastThroughput >= this.maxThroughput) {
         return true;
      } else {
         double diff = this.maxThroughput - this.lastThroughput;
         return diff * 100.0 <= 20.0 * this.maxThroughput;
      }
   }

   static void setMinThreadPoolSize(int minThreadPoolSize) {
      IncrementAdvisor2.minThreadPoolSize = minThreadPoolSize;
   }

   static int getMinThreadPoolSize() {
      return MIN_POOL_SIZE > 0 ? MIN_POOL_SIZE : minThreadPoolSize;
   }

   static void setMaxThreadPoolSize(int maxThreadPoolSize) {
      IncrementAdvisor2.maxThreadPoolSize = maxThreadPoolSize;
   }

   static int getMaxThreadPoolSize() {
      return MAX_POOL_SIZE > 0 ? MAX_POOL_SIZE : maxThreadPoolSize;
   }

   public double getThroughput() {
      return this.y;
   }

   public boolean isThreadPoolBusy() {
      RequestManager requestManager = RequestManager.getInstance();
      return requestManager.getIdleThreadCount() == 0 && requestManager.getQueueDepth() > this.n;
   }

   private static boolean debugEnabled() {
      return debugSelfTuning.isEnabled() || SelfTuningWorkManagerImpl.debugEnabled();
   }

   private static void log(String str) {
      SelfTuningWorkManagerImpl.debug("<IncrAdvisor2>" + str);
   }

   static {
      LARGEST_HOGGER_COMPENSATION = Math.min(Math.max(CPU_COUNT, 2), LARGEST_INCR_INTERVAL);
      LONG_QUEUE_FACTOR = initProperty("weblogic.threadpool.longQueueFactor", 2);
      VERY_LONG_QUEUE_FACTOR = initProperty("weblogic.threadpool.veryLongQueueFactor", 20);
      IMPORTANCE_SAMPLED_NORM_CUMULATIVE = new double[]{0.5, 0.5100001175607, 0.520244326066709, 0.530731407465811, 0.54145922924081, 0.552424657225387, 0.56362346588437, 0.575050246535328, 0.58669831437481, 0.598559615358798, 0.610624634190682, 0.622882304890049, 0.635319925649539, 0.647923079931673, 0.660675566008266, 0.673559337395674, 0.686554456881908, 0.699639067066809, 0.712789380532365, 0.725979692913081, 0.739182422230046, 0.752368177869424, 0.76550586250671, 0.778562810081864, 0.791504962596034, 0.80429708800749, 0.816903040833809, 0.829286066203866, 0.841409147036706, 0.853235392752342, 0.864728466449187, 0.875853045833858, 0.886575311395886, 0.896863453433629, 0.906688187628061, 0.916023267016439, 0.92484597654474, 0.933137594998288, 0.940883808157403, 0.948075056636511, 0.954706802173284, 0.960779697254568, 0.966299644983486, 0.971277739048038, 0.975730077527635, 0.979677448981097, 0.983144894628496, 0.986161156218375, 0.988758025032539, 0.990969613031824, 0.992831571959825, 0.994380289866988};
      LARGEST_SAMPLED_NORM_CUMULATIVE = IMPORTANCE_SAMPLED_NORM_CUMULATIVE[IMPORTANCE_SAMPLED_NORM_CUMULATIVE.length - 1];
      VERY_SMALL_P = 1.0 - LARGEST_SAMPLED_NORM_CUMULATIVE;
      ZERO_QUEUE_TOUCHED_DURATION_CRITERIA = Integer.getInteger("weblogic.work.IncrementAdvisor.zeroQueueTouchedDurationCriteria", 5);
      NUMBER_OF_ADDED_THREADS = Integer.getInteger("weblogic.work.IncrementAdvisor.numOfAddedThreads", 1);
      allowMaxValuesReset = Boolean.getBoolean("weblogic.work.initialAllowMaxValuesReset");
   }

   private static final class SmoothedStats {
      private double sum;
      private double squaresSum;
      private double n = 1.0;
      private double lastHorizon;
      private boolean debugEnabled;

      SmoothedStats(double y) {
         this.sum = y;
         this.squaresSum = y * y;
         this.lastHorizon = 1.0;
         this.debugEnabled = IncrementAdvisor2.debugEnabled();
      }

      void add(double y) {
         double w = 1.0 - 1.0 / IncrementAdvisor2.HORIZON;
         this.lastHorizon = IncrementAdvisor2.HORIZON;
         this.n = w * this.n + 1.0;
         this.sum = w * this.sum + y;
         this.squaresSum = w * this.squaresSum + y * y;
      }

      double getAvg() {
         return this.sum / this.n;
      }

      boolean exceedsZ(double y, double z) {
         double d = this.sum - this.n * y;
         return this.n > 1.0 && d * d > (this.n * this.squaresSum - this.sum * this.sum) * z * z;
      }

      double zScore(double y) {
         double avg = this.sum / this.n;
         return (y - avg) / Math.sqrt(this.squaresSum / this.n - avg * avg);
      }

      double pLessThan(SmoothedStats ss) {
         double avg = this.sum / this.n;
         double ss_avg = ss.sum / ss.n;
         double meanDiff = ss_avg - avg;
         if (this.debugEnabled) {
            IncrementAdvisor2.log("pLessThan debug: avg:\t" + avg + "\tss.avg:\t" + ss_avg + "\tmeanDiff:\t" + meanDiff);
         }

         double meanDiffVar;
         double r;
         if (this.debugEnabled) {
            meanDiffVar = (this.squaresSum - this.sum * this.sum / this.n + ss.squaresSum - ss.sum * ss.sum / ss.n) / (this.n + ss.n);
            r = meanDiffVar * (1.0 / this.n + 1.0 / ss.n);
            double z = r == 0.0 ? Double.NaN : meanDiff / Math.sqrt(r);
            IncrementAdvisor2.log("old pLessThan debug: meanDiffVar:\t" + this.squaresSum + " / " + this.n * ss.n + " - " + avg * avg + " / " + ss.n + " + " + ss.squaresSum + " / " + this.n * ss.n + " - " + ss_avg * ss_avg + " / " + this.n);
            IncrementAdvisor2.log("old pLessThan debug: meanDiffVar:\t" + r);
            IncrementAdvisor2.log("old pLessThan debug: z:\t" + z);
            IncrementAdvisor2.log("");
         }

         meanDiffVar = this.squaresSum / (this.lastHorizon * this.n * this.n) - avg * avg / (this.lastHorizon * this.n) + ss.squaresSum / (ss.lastHorizon * ss.n * ss.n) - ss_avg * ss_avg / (ss.lastHorizon * ss.n);
         if (this.debugEnabled) {
            IncrementAdvisor2.log("pLessThan debug: meanDiffVar:\t" + this.squaresSum + " / " + this.lastHorizon * this.n * this.n + " - " + avg * avg + " / " + this.lastHorizon * this.n + " + " + ss.squaresSum + " / " + ss.lastHorizon * ss.n * ss.n + " - " + ss_avg * ss_avg + " / " + ss.lastHorizon * ss.n);
         }

         if (this.debugEnabled) {
            IncrementAdvisor2.log("pLessThan debug: meanDiffVar:\t" + meanDiffVar);
         }

         if (meanDiffVar == 0.0) {
            return meanDiff > 0.0 ? 1.0 - IncrementAdvisor2.VERY_SMALL_P : IncrementAdvisor2.VERY_SMALL_P;
         } else {
            if (this.debugEnabled) {
               IncrementAdvisor2.log("pLessThan debug: z:\t" + meanDiff / Math.sqrt(meanDiffVar));
            }

            r = IncrementAdvisor2.normCumulative(meanDiff / Math.sqrt(meanDiffVar));
            return r;
         }
      }
   }
}
