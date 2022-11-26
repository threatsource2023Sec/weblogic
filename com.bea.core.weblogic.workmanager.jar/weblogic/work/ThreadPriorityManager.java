package weblogic.work;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

final class ThreadPriorityManager {
   private static final String disableThreadPriorityProp = "weblogic.DisableThreadPriority";
   private static final boolean DISABLE_THREAD_PRIORITY = initProperty("weblogic.DisableThreadPriority", false);
   private static final long PERIOD = 6000L;
   private static final double SHORT_OUTLIER_MULTIPLE = 1.5;
   private static final double LONG_OUTLIER_MULTIPLE = 2.0;
   private static final int MIN_PRIORITY = 1;
   private static final int LOW_PRIORITY = 2;
   private static final int NORMAL_PRIORITY = 5;
   private static final int HIGH_PRIORITY = 9;
   private long lastExecutionTime;
   private long sum;
   private long square;
   private long count;

   private static boolean initProperty(String name, boolean defaultValue) {
      try {
         return Boolean.getBoolean(name);
      } catch (SecurityException var3) {
         return defaultValue;
      }
   }

   private ThreadPriorityManager() {
   }

   public static ThreadPriorityManager getInstance() {
      return ThreadPriorityManager.Factory.THE_ONE;
   }

   void computeThreadPriorities(List requestClasses) {
      try {
         if (!isDisabled() && !this.notDueForExecution()) {
            if (requestClasses != null && requestClasses.size() != 0) {
               this.count = 0L;
               this.sum = 0L;
               this.square = 0L;

               long increment;
               for(Iterator i = requestClasses.iterator(); i.hasNext(); this.square += increment * increment) {
                  ServiceClassSupport requestClass = (ServiceClassSupport)i.next();
                  increment = requestClass.getIncrementForThreadPriorityCalculation();
                  ++this.count;
                  if (debugEnabled()) {
                     log(requestClass.getName() + " has incr " + increment);
                  }

                  this.sum += increment;
               }

               if (this.count >= 2L) {
                  double mean = (double)ServiceClassStatsSupport.div(this.sum, this.count);
                  double stdev = ServiceClassStatsSupport.stdev(this.sum, this.square, this.count);
                  long previousCount = this.count;
                  this.count = 0L;
                  this.sum = 0L;
                  this.square = 0L;
                  List subset = this.assignThreadPriorityToRequestClasses(requestClasses, mean, stdev);
                  if (subset != null && (long)subset.size() != previousCount && subset.size() >= 2) {
                     mean = (double)ServiceClassStatsSupport.div(this.sum, this.count);
                     stdev = ServiceClassStatsSupport.stdev(this.sum, this.square, this.count);
                     this.assignThreadPriorityToRequestClasses(subset, mean, stdev);
                  }
               }
            }
         }
      } catch (ConcurrentModificationException var9) {
      }
   }

   private static boolean isDisabled() {
      return DISABLE_THREAD_PRIORITY;
   }

   private boolean notDueForExecution() {
      long currentTime = System.currentTimeMillis();
      if (currentTime - this.lastExecutionTime < 6000L) {
         return true;
      } else {
         this.lastExecutionTime = currentTime;
         return false;
      }
   }

   private List assignThreadPriorityToRequestClasses(List requestClasses, double mean, double stdev) {
      if (stdev == 0.0) {
         return null;
      } else {
         if (debugEnabled()) {
            log("[mean] " + mean);
         }

         if (debugEnabled()) {
            log("[stdev] " + stdev);
         }

         List subset = new ArrayList();
         Iterator i = requestClasses.iterator();

         while(i.hasNext()) {
            ServiceClassSupport requestClass = (ServiceClassSupport)i.next();
            long increment = requestClass.getIncrementForThreadPriorityCalculation();
            if (lowerThreadPriority(increment, mean, stdev)) {
               if (!requestClass.isInternal()) {
                  if (debugEnabled()) {
                     log(requestClass.getName() + " with incr " + increment + " is set to low thread priority");
                  }

                  requestClass.setThreadPriority(2);
               }
            } else {
               if (increaseThreadPriority(increment, mean, stdev)) {
                  if (debugEnabled()) {
                     log(requestClass.getName() + " with incr " + increment + " is set to high thread priority");
                  }

                  requestClass.setThreadPriority(9);
               } else if (requestClass.getThreadPriority() > 1) {
                  requestClass.setThreadPriority(5);
               }

               subset.add(requestClass);
               ++this.count;
               this.sum += increment;
               this.square += increment * increment;
               if (debugEnabled()) {
                  log(requestClass.getName() + " thread priority is " + requestClass.getThreadPriority());
               }
            }
         }

         return subset;
      }
   }

   private static boolean increaseThreadPriority(long increment, double mean, double stdev) {
      boolean largeStdev = 2.0 * stdev > mean;
      double factor = 2.0;
      if (largeStdev) {
         factor = 1.0;
      }

      return (double)increment < mean - factor * stdev;
   }

   private static boolean lowerThreadPriority(long increment, double mean, double stdev) {
      boolean largeStdev = 2.0 * stdev > mean;
      double factor = 2.0;
      if (largeStdev) {
         factor = 1.5;
      }

      return (double)increment > mean + factor * stdev;
   }

   private static void setThreadPriority(Thread th, int priority) {
      try {
         if (th.getPriority() != priority) {
            th.setPriority(priority);
         }
      } catch (SecurityException var3) {
      }

   }

   static void handleHogger(Thread th, boolean internalWork) {
      if (!isDisabled()) {
         if (internalWork) {
            setThreadPriority(th, 5);
         } else {
            setThreadPriority(th, 1);
         }

      }
   }

   static boolean lowerPriority(ExecuteThread thread, int amountToLower) {
      if (thread != null && amountToLower >= 1) {
         int currentPriority = thread.getPriority();
         if (currentPriority != 1 && !thread.isExecutingInternalWork()) {
            int newPriority = currentPriority - amountToLower;
            if (newPriority < 1) {
               newPriority = 1;
            }

            try {
               thread.setPriority(newPriority);
               if (debugEnabled()) {
                  log("lowerPriority set priority of thread '" + thread.toString() + "' to " + newPriority);
               }

               return true;
            } catch (SecurityException var5) {
               if (debugEnabled()) {
                  log("lowerPriority unable to set priority of thread '" + thread.toString() + "' due to " + var5);
               }

               return false;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   static boolean restorePriority(ExecuteThread thread) {
      if (thread != null && !thread.isExecutingInternalWork()) {
         if (!thread.isUnderExecution()) {
            return true;
         } else {
            int targetPriority = 5;
            WorkAdapter executingWork = thread.getCurrentWork();
            if (executingWork != null) {
               RequestClass requestClass = executingWork.requestClass;
               if (requestClass != null) {
                  targetPriority = requestClass.getThreadPriority();
               }
            }

            try {
               if (debugEnabled()) {
                  log("restorePriority set priority of thread '" + thread.toString() + "' to " + targetPriority);
               }

               thread.setPriority(targetPriority);
               return true;
            } catch (SecurityException var4) {
               if (debugEnabled()) {
                  log("restorePriority unable to set priority of thread '" + thread.toString() + "' due to " + var4);
               }

               return false;
            }
         }
      } else {
         return false;
      }
   }

   static boolean interrupt(ExecuteThread thread) {
      if (thread != null && !thread.isExecutingInternalWork()) {
         try {
            thread.interrupt();
            if (debugEnabled()) {
               log("interrupted thread '" + thread.toString());
            }

            return true;
         } catch (SecurityException var2) {
            if (debugEnabled()) {
               log("interrupt on thread '" + thread.toString() + "' failed due to " + var2);
            }

            return false;
         }
      } else {
         return false;
      }
   }

   private static boolean debugEnabled() {
      return SelfTuningWorkManagerImpl.debugEnabled();
   }

   private static void log(String str) {
      SelfTuningWorkManagerImpl.debug("<ThreadPriorityManager>" + str);
   }

   // $FF: synthetic method
   ThreadPriorityManager(Object x0) {
      this();
   }

   private static final class Factory {
      static final ThreadPriorityManager THE_ONE = new ThreadPriorityManager();
   }
}
