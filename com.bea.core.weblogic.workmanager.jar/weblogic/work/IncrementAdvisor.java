package weblogic.work;

import java.util.Date;
import java.util.Random;
import java.util.TimerTask;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;

public final class IncrementAdvisor extends TimerTask implements IncrementAdvisorIntf {
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
   private static final Random RANDOM = new Random(123L);
   private static double HORIZON = 50.0;
   private static final int Y_THRESHOLD_FOR_CPU_INTENSIVE_LOAD = 15000;
   private static final int HIGH_THROUGHPUT_THRESHOLD = 20000;
   private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
   private final int SELF_TUNING_THRESHOLD;
   private static boolean incrementByCPUCount = false;
   private static final double[] NORM_CUMULATIVE = new double[]{0.001349898032, 0.001865813301, 0.002555130331, 0.003466973804, 0.004661188025, 0.006209665326, 0.008197535926, 0.010724110021, 0.013903447513, 0.017864420562, 0.022750131948, 0.028716559815, 0.035930319112, 0.044565462762, 0.054799291699, 0.06680720127, 0.080756659236, 0.096800484586, 0.115069670223, 0.135666060948, 0.158655253932, 0.184060125347, 0.211855398583, 0.241963652224, 0.274253117751, 0.308537538726, 0.34457825839, 0.382088577811, 0.420740290562, 0.460172162723, 0.5, 0.539827837277, 0.579259709439, 0.617911422189, 0.65542174161, 0.691462461274, 0.725746882249, 0.758036347776, 0.788144601417, 0.815939874653, 0.841344746068, 0.864333939052, 0.884930329777, 0.903199515414, 0.919243340764, 0.93319279873, 0.945200708301, 0.955434537238, 0.964069680888, 0.971283440185, 0.977249868052, 0.982135579438, 0.986096552487, 0.989275889979, 0.991802464074, 0.993790334674, 0.995338811975, 0.996533026196, 0.997444869669, 0.998134186699, 0.998650101968};
   private static final int ZERO_QUEUE_TOUCHED_DURATION_CRITERIA = Integer.getInteger("weblogic.work.IncrementAdvisor.zeroQueueTouchedDurationCriteria", 5);
   private static final int NUMBER_OF_ADDED_THREADS = Integer.getInteger("weblogic.work.IncrementAdvisor.numOfAddedThreads", 1);
   private SmoothedStats[] throughput;
   private int zeroCompletedDuration;
   int zeroQueueTouchedDuration;
   private long previousCompleted;
   long currentCompleted;
   private long previousThreadTime;
   long currentThreadTime;
   private long timeStamp;
   private int attemptToIncrementCount;
   private int maxY;
   private double y;
   private double maxThroughput;
   private double lastThroughput;
   private int previousSampleIndex;
   private int nextSampleIndex;
   private int numSuspiciousInARow;
   private long workManagerDumpCount;

   public IncrementAdvisor() {
      this.SELF_TUNING_THRESHOLD = Math.min(CPU_COUNT, 8);
      this.throughput = new SmoothedStats[0];
      this.timeStamp = System.currentTimeMillis();
      this.maxY = 0;
   }

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
      if (z < -3.0) {
         z = -3.0;
      }

      if (z > 3.0) {
         z = 3.0;
      }

      return NORM_CUMULATIVE[(int)(10.0 * (z + 3.0))];
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
         long[] args = new long[2];
         manager.computeThreadUsage(args);
         this.currentCompleted = args[0];
         this.currentThreadTime = args[1];
         int dCompleted = (int)(this.currentCompleted - this.previousCompleted);
         if (dCompleted == 0) {
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

         int threadUse = (int)(this.currentThreadTime - this.previousThreadTime);
         int hogDuration = dCompleted != 0 && 7 * threadUse >= 4000 * dCompleted ? div(7 * threadUse, dCompleted) : 4000;
         int n = manager.purgeHogs(hogDuration);
         if (debug) {
            log("all threads: " + manager.getExecuteThreadCount() + ", healthy threads: " + n + ", hogs: " + manager.getHogSize() + ", idle threads: " + manager.getIdleThreadCount());
         }

         int usedThreads = (int)(((long)threadUse + elapsedTime / 2L) / elapsedTime);
         this.y = 1000.0 * (double)dCompleted / (double)elapsedTime;
         boolean queueNotEmpty = !manager.queue.isEmpty();
         if (debug) {
            log("y,dCompleted,elapsedTime,threadUse,n,usedThreads,queuelength=\t" + (int)(this.y + 0.5) + "\t" + dCompleted + "\t" + elapsedTime + "\t" + threadUse + "\t" + n + "\t" + usedThreads + "\t" + manager.queue.size());
         }

         if (WorkManagerDumper.getInstance().isEnabled()) {
            if (this.workManagerDumpCount % 30L == 0L) {
               String debugData = "ia:" + manager.getConciseStateDump() + ",healthy:" + n + ",zerocomp:" + this.zeroCompletedDuration + ",zerotouch:" + this.zeroQueueTouchedDuration + " at " + new Date();
               WorkManagerDumper.getInstance().setDebugData(debugData);
            }

            ++this.workManagerDumpCount;
         }

         if (queueNotEmpty && dCompleted > 0 && usedThreads < n) {
            if (debug) {
               log("Suspicious: throughput= " + this.y + ", usedThreads= " + usedThreads);
            }

            if (this.numSuspiciousInARow++ < 5) {
               return;
            }

            if (debug) {
               log(this.numSuspiciousInARow + " consecutive suspicious. Proceeding...");
            }
         }

         this.numSuspiciousInARow = 0;
         if (n >= this.throughput.length) {
            SmoothedStats[] old = this.throughput;
            this.throughput = new SmoothedStats[n + 1];
            System.arraycopy(old, 0, this.throughput, 0, old.length);
         }

         if (usedThreads >= n) {
            this.addSample(n, this.y);
         }

         this.previousCompleted = this.currentCompleted;
         this.previousThreadTime = this.currentThreadTime;
         this.timeStamp = time;
         manager.updateElapsedTime(elapsedTime);
         int minPoolSize = getMinThreadPoolSize();
         if (n < minPoolSize) {
            if (debug) {
               log("Adding new threads; Total threads excluding hoggers are less than min pool size; n: " + n + ", minPoolSize: " + minPoolSize);
            }

            manager.incrPoolSize(minPoolSize - n);
         } else if (queueNotEmpty && this.zeroCompletedDuration > 2) {
            if (debug) {
               log("Encountered zero completion in successive runs. Adding a new thread to prevent deadlock");
            }

            manager.incrPoolSize(1);
         } else if (this.maxY == 0 && this.y > 15000.0) {
            reset(manager, n);
            this.initMaxValues(manager.getTotalRequestsCount(), this.y);
         } else if (queueNotEmpty && this.zeroQueueTouchedDuration > ZERO_QUEUE_TOUCHED_DURATION_CRITERIA) {
            if (debug) {
               log("Encountered zero CalendarQueue watch activity in " + ZERO_QUEUE_TOUCHED_DURATION_CRITERIA + " several periods.Adding " + NUMBER_OF_ADDED_THREADS + "  thread(s) to review CalendarQueue");
            }

            manager.incrPoolSize(NUMBER_OF_ADDED_THREADS);
         } else {
            manager.computeThreadPriorities();
            this.initMaxValues(manager.getTotalRequestsCount(), this.y);
            this.showThroughput();
            this.initIndexes(n);
            double rand = (double)RANDOM.nextFloat();
            double decrAttraction = this.getDecrAttraction(n, minPoolSize);
            double incrAttraction = this.getIncrAttraction(n);
            if (debug) {
               log("attraction decr= " + decrAttraction + ", incr= " + incrAttraction + ", rand= " + rand);
            }

            if (decrAttraction > incrAttraction) {
               if (decrAttraction > rand) {
                  if (debug) {
                     log("Shrinking with attraction " + decrAttraction + " to thread count= " + this.previousSampleIndex);
                  }

                  manager.incrPoolSize(this.previousSampleIndex - n);
               } else if (debug) {
                  log("Not shrinking the pool even though decrAttr > incrAttr");
               }

               this.attemptToIncrementCount = 0;
            } else if (!queueNotEmpty || n >= getMaxThreadPoolSize() || this.attemptToIncrementCount < 3 && !(incrAttraction > rand)) {
               ++this.attemptToIncrementCount;
            } else {
               this.attemptToIncrementCount = 0;
               if (incrementByCPUCount) {
                  if (debug) {
                     log("Current thread count n = " + n + "; incrementing it by " + CPU_COUNT);
                  }

                  manager.incrPoolSize(CPU_COUNT);
               } else {
                  int incrementInterval = 1;
                  if (!this.mustIncrementByOne(incrAttraction, decrAttraction, n, this.nextSampleIndex)) {
                     incrementInterval = Math.max(this.nextSampleIndex - n, this.getIncrementInterval((int)this.y));
                  }

                  if (debug) {
                     log("Growing with attraction= " + incrAttraction + ", increment interval= " + incrementInterval);
                  }

                  manager.incrPoolSize(incrementInterval);
               }
            }
         }
      }
   }

   private boolean mustIncrementByOne(double incrAttraction, double decrAttraction, int n, int nextSampleIndex) {
      if (nextSampleIndex > n + 1 && decrAttraction == 0.001349898032 && incrAttraction == 0.001349898032) {
         SmoothedStats[] old = this.throughput;
         this.throughput = new SmoothedStats[n];
         System.arraycopy(old, 0, this.throughput, 0, this.throughput.length);
         return true;
      } else {
         return false;
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
      if (totalRequestsCount == 0 && y < (double)(CPU_COUNT * 5)) {
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

      int count;
      for(count = n - 1; count >= 0; --count) {
         if (this.throughput[count] != null) {
            this.previousSampleIndex = count;
            break;
         }
      }

      for(count = n + 1; count < this.throughput.length; ++count) {
         if (this.throughput[count] != null) {
            this.nextSampleIndex = count;
            break;
         }
      }

   }

   private int getIncrementInterval(int y) {
      if (this.maxY != 0 && y != 0) {
         int factor = this.maxY / y;
         if (factor <= 1) {
            return 1;
         } else {
            int incr = Math.min(20, 3 * factor + 1);
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

      if (n > minPoolSize && this.previousSampleIndex != 0) {
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
      } else {
         return 0.0;
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
      IncrementAdvisor.minThreadPoolSize = minThreadPoolSize;
   }

   static int getMinThreadPoolSize() {
      return MIN_POOL_SIZE > 0 ? MIN_POOL_SIZE : minThreadPoolSize;
   }

   static void setMaxThreadPoolSize(int maxThreadPoolSize) {
      IncrementAdvisor.maxThreadPoolSize = maxThreadPoolSize;
   }

   static int getMaxThreadPoolSize() {
      return MAX_POOL_SIZE > 0 ? MAX_POOL_SIZE : maxThreadPoolSize;
   }

   public double getThroughput() {
      return this.y;
   }

   public boolean isThreadPoolBusy() {
      RequestManager requestManager = RequestManager.getInstance();
      return requestManager.getIdleThreadCount() == 0 && requestManager.getQueueDepth() > 0;
   }

   private static boolean debugEnabled() {
      return debugSelfTuning.isEnabled() || SelfTuningWorkManagerImpl.debugEnabled();
   }

   private static void log(String str) {
      SelfTuningWorkManagerImpl.debug("<IncrAdvisor>" + str);
   }

   private static final class SmoothedStats {
      private double sum;
      private double squaresSum;
      private double n = 1.0;
      private boolean debugEnabled;

      SmoothedStats(double y) {
         this.sum = y;
         this.squaresSum = y * y;
         this.debugEnabled = IncrementAdvisor.debugEnabled();
      }

      void add(double y) {
         double w = 1.0 - 1.0 / IncrementAdvisor.HORIZON;
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
         double meanDiff = ss.sum / ss.n - this.sum / this.n;
         double jointVar = (this.squaresSum - this.sum * this.sum / this.n + ss.squaresSum - ss.sum * ss.sum / ss.n) / (this.n + ss.n);
         double meanDiffVar = jointVar * (1.0 / this.n + 1.0 / ss.n);
         if (this.debugEnabled) {
            IncrementAdvisor.log("n=" + this.n + " ss.n=" + ss.n + " mean[n]=" + this.sum / this.n + " mean[ss]=" + ss.sum / ss.n + " meanDiff=" + meanDiff + " jointVar=" + jointVar + " meanDiffVar=" + meanDiffVar);
         }

         return meanDiffVar == 0.0 ? (double)(meanDiff > 0.0 ? 1 : 0) : IncrementAdvisor.normCumulative(meanDiff / Math.sqrt(meanDiffVar));
      }
   }
}
