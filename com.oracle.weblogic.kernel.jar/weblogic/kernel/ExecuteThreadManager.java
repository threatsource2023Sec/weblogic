package weblogic.kernel;

import java.util.ArrayList;
import weblogic.health.HealthState;
import weblogic.health.Symptom;
import weblogic.health.Symptom.Severity;
import weblogic.health.Symptom.SymptomType;
import weblogic.management.configuration.ExecuteQueueMBean;
import weblogic.utils.UnsyncCircularQueue;
import weblogic.utils.collections.Stack;

public class ExecuteThreadManager {
   private static boolean netscape = false;
   private static final ShutdownRequest SHUTDOWN_REQUEST = new ShutdownRequest();
   private final String name;
   private final ThreadGroup threadGroup;
   private final UnsyncCircularQueue q;
   private final ArrayList threads;
   private final Stack idleThreads;
   private boolean shutdownRequested = false;
   private int departures = 0;
   private final Object printOnceLock = new Object();
   private boolean capacityGreaterThanThreshold = false;
   private Symptom healthSymptom;
   private int healthState = 0;
   private final ExecuteQueueMBean queueMBean;

   protected ExecuteThreadManager(String name) {
      this.name = name;
      this.threadGroup = null;
      this.q = null;
      this.threads = null;
      this.idleThreads = null;
      this.queueMBean = null;
   }

   ExecuteThreadManager(String policyName, ExecuteQueueMBean queueBean) {
      this.name = policyName;
      this.queueMBean = queueBean;
      this.q = new UnsyncCircularQueue(256, queueBean.getQueueLength());
      this.idleThreads = new Stack(queueBean.getThreadCount());
      this.threads = new ArrayList(queueBean.getThreadCount());
      ThreadGroup tg = null;

      try {
         tg = new ThreadGroup("Thread Group for Queue: '" + policyName + "'");
      } catch (SecurityException var5) {
         System.err.println("Caught a security exception. That's okay.");
         netscape = true;
      }

      this.threadGroup = tg;
      this.setThreadCount(queueBean.getThreadCount());
   }

   private int getThreadsIncrease() {
      return this.queueMBean != null ? this.queueMBean.getThreadsIncrease() : 0;
   }

   private int getThreadsMaximum() {
      return this.queueMBean != null ? this.queueMBean.getThreadsMaximum() : 0;
   }

   private int getCalculatedPercent() {
      return this.queueMBean == null ? 0 : Math.max(this.queueMBean.getQueueLength() * this.queueMBean.getQueueLengthThresholdPercent() / 100, 1);
   }

   public boolean isShutdownInProgress() {
      return this.shutdownRequested;
   }

   ExecuteThread[] getExecuteThreads() {
      return (ExecuteThread[])((ExecuteThread[])this.threads.toArray(new ExecuteThread[this.threads.size()]));
   }

   public int getExecuteQueueDepth() {
      return this.q.size();
   }

   public int getExecuteQueueSize() {
      return this.q.capacity();
   }

   public int getExecuteQueueDepartures() {
      return this.departures;
   }

   public int getExecuteThreadCount() {
      return this.threads.size();
   }

   public final String getName() {
      return this.name;
   }

   public void setThreadCount(int count) throws SecurityException {
      int firstNew;
      synchronized(this) {
         if (this.shutdownRequested) {
            throw new IllegalStateException("Shutdown in progress");
         }

         int threadsMaximum = this.getThreadsMaximum();
         if (count > threadsMaximum) {
            count = threadsMaximum;
         }

         if (count <= this.threads.size()) {
            return;
         }

         int threadPriority = this.queueMBean != null ? this.queueMBean.getThreadPriority() : 5;
         firstNew = this.threads.size();
         int i = firstNew;

         while(true) {
            if (i >= count) {
               break;
            }

            ExecuteThread thread;
            if (netscape) {
               thread = createExecuteThread(i, this);
            } else {
               try {
                  thread = createExecuteThread(i, this, this.threadGroup);
                  thread.setDaemon(true);
               } catch (SecurityException var10) {
                  System.err.println("Caught a security exception. That's okay.");
                  netscape = true;
                  thread = createExecuteThread(i, this);
               }
            }

            thread.setPriority(threadPriority);
            this.threads.add(thread);
            ++i;
         }
      }

      this.startThreads(firstNew);
   }

   private void startThreads(int firstNew) {
      synchronized(this.threads) {
         for(int i = firstNew; i < this.threads.size(); ++i) {
            ExecuteThread thread = (ExecuteThread)this.threads.get(i);
            if (thread != null) {
               thread.start();
               if (!thread.isStarted()) {
                  try {
                     Thread.sleep(5L);
                  } catch (InterruptedException var7) {
                  }
               }
            }
         }

      }
   }

   private void expandThreadPool() {
      int threadsIncrease = this.getThreadsIncrease();
      if (this.threads.size() == 0) {
         this.setThreadCount(threadsIncrease);
      } else if (this.q.size() + 1 >= this.getCalculatedPercent()) {
         this.capacityGreaterThanThreshold = true;
         Symptom symptom = new Symptom(SymptomType.EXECUTEQUEUE_OVERFLOW, Severity.MEDIUM, this.queueMBean.getName(), "Queue Capacity greater than configured threshold of " + this.queueMBean.getQueueLengthThresholdPercent() + "%.  Will try to allocate: '" + threadsIncrease + "' threads to help.");
         this.setHealthState(1, symptom);
         this.setThreadCount(this.threads.size() + threadsIncrease);
      } else if (this.healthState != 0) {
         this.capacityGreaterThanThreshold = false;
         this.setHealthState(0, (Symptom)null);
      }

   }

   synchronized ExecuteThread[] getStuckExecuteThreads(long maxTime) {
      long currentTime = System.currentTimeMillis();
      ArrayList stuckThreadList = null;
      if (this.threads.size() != 0 && maxTime != 0L) {
         int threadsIncrease;
         for(threadsIncrease = 0; threadsIncrease < this.threads.size(); ++threadsIncrease) {
            ExecuteThread thread = (ExecuteThread)this.threads.get(threadsIncrease);
            if (thread != null && !thread.getSystemThread() && thread.getCurrentRequest() != null) {
               long timeStamp = thread.getTimeStamp();
               if (timeStamp > 0L) {
                  long elapsedTime = currentTime - timeStamp;
                  if (elapsedTime > maxTime) {
                     thread.setPrintStuckThreadMessage(true);
                     if (stuckThreadList == null) {
                        stuckThreadList = new ArrayList();
                     }

                     stuckThreadList.add(thread);
                  }
               }
            }
         }

         threadsIncrease = this.getThreadsIncrease();
         if (stuckThreadList != null && stuckThreadList.size() == this.threads.size()) {
            Symptom symptom = new Symptom(SymptomType.STUCK_THREADS, Severity.HIGH, this.name, "All Threads are stuck.  Will try to allocate: '" + threadsIncrease + "' threads to help.");
            this.setHealthState(2, symptom);
            this.setThreadCount(this.threads.size() + threadsIncrease);
         } else if (this.healthState != 0) {
            this.setHealthState(0, (Symptom)null);
         }

         return (ExecuteThread[])((ExecuteThread[])(stuckThreadList != null ? stuckThreadList.toArray(new ExecuteThread[stuckThreadList.size()]) : null));
      } else {
         return null;
      }
   }

   synchronized void shutdown() throws SecurityException {
      if (!this.shutdownRequested) {
         this.shutdownRequested = true;

         while(this.idleThreads.size() != 0) {
            ExecuteThread t = (ExecuteThread)this.idleThreads.pop();
            t.notifyRequest(SHUTDOWN_REQUEST);
         }

      }
   }

   public int getIdleThreadCount() {
      return this.idleThreads.size();
   }

   void registerIdle(ExecuteThread t) {
      if (t.getPrintStuckThreadMessage()) {
         T3SrvrLogger.logInfoUnstuckThread(t.getName());
         t.setPrintStuckThreadMessage(false);
      }

      Object req;
      synchronized(this) {
         if (this.shutdownRequested) {
            req = SHUTDOWN_REQUEST;
         } else {
            req = (ExecuteRequest)this.q.get();
         }

         if (req == null) {
            this.idleThreads.push(t);
            return;
         }
      }

      t.setRequest((ExecuteRequest)req);
      ++this.departures;
   }

   void execute(ExecuteRequest r, boolean mayThrottle) {
      if (mayThrottle && Kernel.isQueueThrottleAllowed()) {
         int queueLength = this.queueMBean.getQueueLength();
         if (this.q.size() >= queueLength) {
            throw new QueueFullException(queueLength);
         }
      }

      ExecuteThread t;
      try {
         synchronized(this) {
            if (this.idleThreads.size() == 0) {
               this.expandThreadPool();
               this.q.put(r);
               return;
            }

            t = (ExecuteThread)this.idleThreads.pop();
         }
      } finally {
         this.logQueueCapacityWarning();
      }

      ++this.departures;
      t.notifyRequest(r);
   }

   private void logQueueCapacityWarning() {
      boolean doLog = false;
      synchronized(this.printOnceLock) {
         if (this.capacityGreaterThanThreshold) {
            doLog = true;
            this.capacityGreaterThanThreshold = false;
         }
      }

      int threadsIncrease = this.getThreadsIncrease();
      if (doLog && threadsIncrease != 0 && this.threads.size() + threadsIncrease <= this.getThreadsMaximum()) {
         T3SrvrLogger.logWarnQueueCapacityGreaterThanThreshold(this.queueMBean.getQueueLengthThresholdPercent(), threadsIncrease);
      }

   }

   boolean executeIfIdle(ExecuteRequest r) {
      ExecuteThread t;
      synchronized(this) {
         if (this.idleThreads.size() == 0) {
            return false;
         }

         t = (ExecuteThread)this.idleThreads.pop();
      }

      ++this.departures;
      t.notifyRequest(r);
      return true;
   }

   private void setHealthState(int state, Symptom symptom) {
      this.healthState = state;
      this.healthSymptom = symptom;
   }

   public HealthState getHealthState() {
      return new HealthState(this.healthState, this.healthSymptom);
   }

   int getPendingTasksCount() {
      int taskCount = 0;
      if (this.threads.size() == 0) {
         return 0;
      } else {
         for(int i = 0; i < this.threads.size(); ++i) {
            ExecuteThread thread = (ExecuteThread)this.threads.get(i);
            if (thread != null && !thread.getSystemThread()) {
               ExecuteRequest request = thread.getCurrentRequest();
               if (request != null) {
                  ++taskCount;
               }
            }
         }

         taskCount += this.getExecuteQueueDepth();
         return taskCount;
      }
   }

   public final synchronized String toString() {
      return super.toString() + " - name: '" + this.getName() + "' threads: '" + this.getExecuteThreadCount() + "' idle: '" + this.getIdleThreadCount() + " departures: '" + this.getExecuteQueueDepartures() + "' queue:\n\t" + this.q;
   }

   private static ExecuteThread createExecuteThread(int which, ExecuteThreadManager etm) {
      return (ExecuteThread)(KernelStatus.isApplet() ? new ExecuteThread(which, etm) : new ServerExecuteThread(which, etm));
   }

   private static ExecuteThread createExecuteThread(int which, ExecuteThreadManager etm, ThreadGroup tg) {
      return (ExecuteThread)(KernelStatus.isApplet() ? new ExecuteThread(which, etm, tg) : new ServerExecuteThread(which, etm, tg));
   }

   static final class ShutdownError extends Error {
   }

   private static final class ShutdownRequest implements ExecuteRequest {
      private ShutdownRequest() {
      }

      public void execute(ExecuteThread thd) {
         throw new ShutdownError();
      }

      // $FF: synthetic method
      ShutdownRequest(Object x0) {
         this();
      }
   }
}
