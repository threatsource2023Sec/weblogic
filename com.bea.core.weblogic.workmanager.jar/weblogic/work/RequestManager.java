package weblogic.work;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.kernel.KernelStatus;
import weblogic.kernel.T3SrvrLogger;
import weblogic.utils.collections.MaybeMapper;
import weblogic.utils.collections.PartialOrderSet;

public final class RequestManager {
   private static final int INCREMENT_ADVISOR_PERIOD = 2000;
   private static final int INCREMENT_ADVISOR_START_DELAY = 20000;
   private static final int MAX_STANDBY_THREADS = 256;
   private static final WorkAdapter ACTIVATE_REQUEST = new ActivateRequest();
   private static final WorkAdapter SHUTDOWN_REQUEST = new ShutdownRequest();
   private static final WorkAdapter THREADLOCAL_FORCE_CLEANUP_REQUEST = new ThreadLocalForceCleanupRequest();
   private final ArrayList allThreads = new ArrayList(256);
   final PartitionAffinityThreadPool idleThreadPool;
   final PartitionAffinityThreadPool standbyThreadPool;
   private AtomicInteger runningThreadsCount;
   final PriorityRequestQueue queue;
   private AtomicInteger queueDepth;
   private static boolean useEnhancedPriorityQueue;
   private static boolean allowShrinkingPriorityQueue;
   private static boolean useEnhancedIncrementAdvisor;
   private static boolean isolatePartitionThreadLocal;
   private AtomicInteger queueTouched;
   private final ArrayList requestClasses;
   List minThreadsConstraints;
   private AtomicLong departures;
   private AtomicLong mtcDepartures;
   private final IncrementAdvisorIntf incrementAdvisor;
   private volatile int toDecrement;
   private AtomicInteger standbyThreadsCount;
   private volatile int hogCounter;
   private volatile int longRunningThreadCount;
   private volatile boolean allNonStandbyThreadsExecutingMinTCWork;
   private int maxThreadIdValue;
   private final ThreadGroup threadGroup;
   private final BitSet recycledIDs;
   private final ServiceClassesStats stats;
   private static boolean useBufferQueue;
   private PartialOrderSet bufferWorkQueue;
   private static final StuckThreadAction DEFAULT_STUCK_THREAD_ACTION = new DefaultStuckThreadAction();
   private static final int USE_POOL_SIZE = -1;
   private int[] targetStandbyThreadPoolSize;
   private final String CMM_STANDBY_POOL_SIZE_PROPERTY;
   static final boolean INCLUDE_HOGS_IN_SELF_TUNING_STATS = Boolean.getBoolean("weblogic.work.includeHogsInSelfTuningStats");
   static final boolean INCLUDE_LONG_RUNNING_THREADS_IN_SELF_TUNING_STATS = Boolean.getBoolean("weblogic.work.includeLongRunningInSelfTuningStats");
   static boolean DISABLE_ALL_NON_STANDBY_THREADS_EXECUTING_MINTC_WORK = Boolean.getBoolean("weblogic.work.disableAddThreadWhenAllThreadsExecutingMinTCWork");
   static final long THREAD_POOL_SIZE_LOG_INTERVAL_MILLIS = 1000L * (long)Integer.getInteger("weblogic.work.threadPoolSizeLogIntervalSeconds", 120);
   private long lastThreadPoolSizeLogTime;
   public static final DebugLogger debugRM = DebugLogger.getDebugLogger("DebugRequestManager");
   static RequestManager THE_ONE;
   private final Callable PUSH_AND_THEN;
   private final Callable POP_OR_ELSE;

   private RequestManager() {
      this.idleThreadPool = new PartitionAffinityThreadPool(IncrementAdvisor.getMaxThreadPoolSize(), false, isolatePartitionThreadLocal);
      this.standbyThreadPool = new PartitionAffinityThreadPool(256, true, isolatePartitionThreadLocal);
      this.runningThreadsCount = new AtomicInteger();
      this.queueDepth = new AtomicInteger();
      this.queueTouched = new AtomicInteger();
      this.requestClasses = new ArrayList();
      this.minThreadsConstraints = new CopyOnWriteArrayList();
      this.departures = new AtomicLong();
      this.mtcDepartures = new AtomicLong();
      this.standbyThreadsCount = new AtomicInteger();
      this.recycledIDs = new BitSet();
      this.stats = new ServiceClassesStats();
      this.targetStandbyThreadPoolSize = new int[]{-1, -1, -1, -1, -1, 128, 64, 32, 16, 8, 4};
      this.CMM_STANDBY_POOL_SIZE_PROPERTY = "weblogic.work.cmm.standbypoolsize";
      this.PUSH_AND_THEN = new Callable() {
         public ExecuteThread call(Object o) {
            return RequestManager.this.idleThreadPool.poll((WorkAdapter)null);
         }
      };
      this.POP_OR_ELSE = new Callable() {
         public WorkAdapter call(ExecuteThread t) {
            RequestManager.this.runningThreadsCount.getAndDecrement();
            RequestManager.this.addToIdlePool(t, false);
            return null;
         }
      };
      if (useEnhancedPriorityQueue) {
         this.queue = new ConcurrentCalendarQueue(allowShrinkingPriorityQueue, WorkAdapter.CHECK_EXPIRED_WORK);
         if (debugRM.isDebugEnabled()) {
            debugRM.debug("Enhanced CalendarQueue created");
         }
      } else {
         this.queue = new CalendarQueue(allowShrinkingPriorityQueue, WorkAdapter.CHECK_EXPIRED_WORK);
         if (debugRM.isDebugEnabled()) {
            debugRM.debug("Classic CalendarQueue created");
         }
      }

      ThreadGroup tg = null;

      try {
         tg = new ThreadGroup("Pooled Threads");
      } catch (SecurityException var6) {
      }

      this.threadGroup = tg;
      TimerTask incrementAdvisorTask = null;
      if (useEnhancedIncrementAdvisor) {
         IncrementAdvisor2 ia2 = new IncrementAdvisor2();
         incrementAdvisorTask = ia2;
         this.incrementAdvisor = ia2;
         if (debugRM.isDebugEnabled()) {
            debugRM.debug("Enhanced IncrementAdvisor2 created");
         }
      } else {
         IncrementAdvisor ia = new IncrementAdvisor();
         incrementAdvisorTask = ia;
         this.incrementAdvisor = ia;
         if (debugRM.isDebugEnabled()) {
            debugRM.debug("Classic IncrementAdvisor created");
         }
      }

      if (KernelStatus.isServer()) {
         int delay = 20000;

         try {
            String s = System.getProperty("weblogic.work.increment.delay");
            if (s != null) {
               delay = Integer.parseInt(s);
            }
         } catch (Exception var5) {
         }

         (new Timer(true)).schedule((TimerTask)incrementAdvisorTask, (long)delay, 2000L);
      }

      if (useBufferQueue) {
         this.bufferWorkQueue = new PartialOrderSet(8192);
         WorkManagerImpl.executeDaemonTask("RequestManagerPoller", 10, new BufferQueueDrainer());
      }

      String standbyPoolSizeProperty = System.getProperty("weblogic.work.cmm.standbypoolsize");
      this.loadTargetStandbyPoolSizeTable(standbyPoolSizeProperty);
   }

   public static RequestManager getInstance() {
      if (THE_ONE == null) {
         initSingleton();
      }

      return THE_ONE;
   }

   private static synchronized void initSingleton() {
      if (THE_ONE == null) {
         THE_ONE = new RequestManager();
      }

   }

   public static void enableBufferQueue(boolean flag) {
      useBufferQueue = flag;
   }

   public static void enableEnhancedPriorityQueue(boolean flag) {
      useEnhancedPriorityQueue = flag;
   }

   public static void enableAllowShrinkingPriorityQueue(boolean flag) {
      allowShrinkingPriorityQueue = flag;
   }

   public static void useEnhancedIncrementAdvisor(boolean flag) {
      useEnhancedIncrementAdvisor = flag;
   }

   public static void setIsolatePartitionThreadLocal(boolean flag) {
      isolatePartitionThreadLocal = flag;
   }

   static void initInternalRequests(SelfTuningWorkManagerImpl workManager) {
      ACTIVATE_REQUEST.setWorkManager(workManager);
      SHUTDOWN_REQUEST.setWorkManager(workManager);
      THREADLOCAL_FORCE_CLEANUP_REQUEST.setWorkManager(workManager);
      getInstance().incrPoolSize(IncrementAdvisor.getMinThreadPoolSize());
   }

   private void loadTargetStandbyPoolSizeTable(String propertyValue) {
      if (propertyValue != null) {
         StringTokenizer st = new StringTokenizer(propertyValue, ",");

         for(int index = 0; st.hasMoreTokens() && index < this.targetStandbyThreadPoolSize.length; ++index) {
            String token = st.nextToken().trim();
            boolean valueValid = false;

            try {
               int entry = Integer.parseInt(token);
               if (entry >= 0 && entry <= 256 || entry == -1) {
                  this.targetStandbyThreadPoolSize[index] = entry;
                  valueValid = true;
               }
            } catch (NumberFormatException var7) {
            }

            if (!valueValid && debugRM.isDebugEnabled()) {
               debugRM.debug("Ignored value '" + token + "' at position " + index + " of property '" + "weblogic.work.cmm.standbypoolsize" + "'.");
            }
         }

         if (debugRM.isDebugEnabled()) {
            StringBuffer sb = new StringBuffer();

            for(int i = 0; i < this.targetStandbyThreadPoolSize.length; ++i) {
               String size = this.targetStandbyThreadPoolSize[i] == -1 ? "USE_POOL_SIZE" : Integer.toString(this.targetStandbyThreadPoolSize[i]);
               sb.append(" Level " + i + "=" + size);
            }

            debugRM.debug("CMM Standby Thread Pool size set to: " + sb.toString());
         }

      }
   }

   public boolean executeIt(WorkAdapter adapter) {
      try {
         return this.executeItInternal(adapter);
      } catch (ConstraintFullQueueException var3) {
         if (this.cancelTask(adapter, var3.getMessage())) {
            adapter.run();
            workCompleted(adapter);
         }

         return false;
      }
   }

   public boolean executeItWithRethrow(WorkAdapter adapter) throws ConstraintFullQueueException {
      try {
         return this.executeItInternal(adapter);
      } catch (ConstraintFullQueueException var3) {
         if (this.cancelTask(adapter, var3.getMessage())) {
            workCompleted(adapter);
            throw var3;
         } else {
            return false;
         }
      }
   }

   private boolean cancelTask(WorkAdapter adapter, String cancelMessage) {
      Runnable cancelTask = adapter.cancel(cancelMessage);
      if (cancelTask != null) {
         cancelTask.run();
         workCompleted(adapter);
      }

      return cancelTask == null;
   }

   private boolean executeItInternal(WorkAdapter entry) throws ConstraintFullQueueException {
      entry.wm.accepted();
      long v = entry.getVersion();
      ExecuteThread t = this.idleThreadPool.poll(entry);
      if (t == null) {
         MinThreadsConstraint minConstraint = entry.getMinThreadsConstraint();
         if (minConstraint == null) {
            this.queueDepth.getAndIncrement();
            this.addToPriorityQueue(entry, -1L);
            return false;
         } else {
            MaxThreadsConstraint mtc = entry.getMaxThreadsConstraint();
            boolean b;
            if (mtc != null) {
               synchronized(mtc) {
                  if (b = minConstraint.tryAcquire()) {
                     mtc.acquire();
                  }
               }
            } else {
               b = minConstraint.tryAcquire();
            }

            if (!b) {
               this.addToMinAndPriorityQueue(entry, v);
               return false;
            } else {
               this.mtcDepartures.getAndIncrement();
               t = this.standbyThreadPool.poll(entry);
               if (t == null) {
                  if (debugRM.isDebugEnabled()) {
                     debugRM.debug("<executeIt> MinThread Constraint is violated but no idle thread in pool; hence creating a new thread");
                  }

                  entry.wm.started();
                  entry.started();
                  this.departures.getAndIncrement();
                  this.createStandbyThreadAndExecute(this.threadID(), entry);
               } else {
                  this.runningThreadsCount.getAndIncrement();
                  this.setNextRequest(t, true, entry);
               }

               return true;
            }
         }
      } else {
         MaxThreadsConstraint mtc = entry.getMaxThreadsConstraint();
         if (mtc == null) {
            entry.wm.increaseMinThreadConstraintInProgress(false);
         } else {
            boolean b;
            synchronized(mtc) {
               b = mtc.reserveIfConstraintNotReached();
               if (b) {
                  entry.wm.increaseMinThreadConstraintInProgress(false);
               }
            }

            if (!b) {
               this.addToIdlePool(t, true);
               this.addToMinAndPriorityQueue(entry, v);
               return false;
            }
         }

         this.runningThreadsCount.getAndIncrement();
         t = this.standbyThreadPool.switchThreadWithPartitionAffinity(t, entry, true);
         this.setNextRequest(t, true, entry);
         return true;
      }
   }

   private void addToPriorityQueue(WorkAdapter work, long v) {
      if (useBufferQueue) {
         this.bufferWorkQueue.putMaybe(work, v);
      } else {
         this.addToCalendarQueue(work, v);
      }

   }

   private void addToCalendarQueue(WorkAdapter work, long v) {
      ExecuteThread t = (ExecuteThread)this.queue.add(work, v, work.requestClass, this.PUSH_AND_THEN, (Object)null);
      if (t != null) {
         if (debugEnabled()) {
            log("Activating an idle thread as we have new work in Calendar Queue");
         }

         this.runningThreadsCount.getAndIncrement();
         this.executeWorkFromPriorityQueue(t, true);
      }

   }

   private void addToMinAndPriorityQueue(WorkAdapter entry, long v) throws ConstraintFullQueueException {
      this.queueDepth.getAndIncrement();
      MinThreadsConstraint minConstraint = entry.getMinThreadsConstraint();
      if (minConstraint != null) {
         try {
            minConstraint.add(entry, v);
         } catch (ConstraintFullQueueException var6) {
            this.queueDepth.getAndDecrement();
            throw var6;
         }

         this.addToPriorityQueue(entry, v);
      } else {
         this.addToPriorityQueue(entry, -1L);
      }

   }

   private void createThreadAndExecute(int threadId, WorkAdapter entry) {
      ExecuteThread t = this.createThread(threadId);
      this.startThread(t, entry);
   }

   private void createStandbyThreadAndExecute(int threadId, WorkAdapter entry) {
      ExecuteThread t = this.createThread(threadId);
      t.setStandby(true);
      this.standbyThreadsCount.getAndIncrement();
      this.startThread(t, entry);
   }

   private void startThread(ExecuteThread t, WorkAdapter entry) {
      synchronized(this.allThreads) {
         this.allThreads.add(t);
      }

      this.runningThreadsCount.getAndIncrement();
      t.setRequest(entry);
      t.start();
   }

   private ExecuteThread createThread(int threadId) {
      if (Thread.currentThread() instanceof ExecuteThread) {
         ClassLoader savedCL = Thread.currentThread().getContextClassLoader();
         ClassLoader defaultContextClassLoader = ((ExecuteThread)Thread.currentThread()).getDefaultContextClassLoader();
         Thread.currentThread().setContextClassLoader(defaultContextClassLoader);

         ExecuteThread var4;
         try {
            var4 = this.doCreateThreadUnderGlobalContext(threadId, "weblogic.kernel.Default (self-tuning)");
         } finally {
            Thread.currentThread().setContextClassLoader(savedCL);
         }

         return var4;
      } else {
         return this.doCreateThreadUnderGlobalContext(threadId, "weblogic.kernel.Default (self-tuning)");
      }
   }

   private ExecuteThread doCreateThreadUnderGlobalContext(final int threadId, final String name) {
      try {
         return (ExecuteThread)PartitionUtility.runWorkUnderGlobalContext(new java.util.concurrent.Callable() {
            public ExecuteThread call() throws Exception {
               return new ExecuteThread(threadId, name, RequestManager.this.threadGroup);
            }
         });
      } catch (ExecutionException var5) {
         ComponentInvocationContext currentContext = PartitionUtility.getCurrentComponentInvocationContext();
         if (!currentContext.isGlobalRuntime()) {
            WorkManagerLogger.logCreateThreadUnderGlobalContextFailed(var5.getCause(), currentContext.getPartitionName());
         }

         return new ExecuteThread(threadId, name, this.threadGroup);
      }
   }

   public boolean executeIfIdle(WorkAdapter entry) {
      ExecuteThread t = this.idleThreadPool.poll(entry);
      if (t == null) {
         return false;
      } else {
         entry.wm.accepted();
         entry.wm.started();
         entry.wm.acquireMinMaxConstraint(false);
         entry.started();
         this.runningThreadsCount.getAndIncrement();
         this.departures.getAndIncrement();
         t.setRequest(entry, true);
         return true;
      }
   }

   boolean registerIdle(ExecuteThread t, WorkAdapter previousEntry) {
      assert previousEntry != SHUTDOWN_REQUEST : "This thread was told to kill itself";

      if (previousEntry != null && previousEntry != ACTIVATE_REQUEST && previousEntry != THREADLOCAL_FORCE_CLEANUP_REQUEST) {
         this.updateStats(previousEntry, t);
         workCompleted(previousEntry);
      }

      if (!this.executeWorkFromMinOrMaxQueue(previousEntry, t)) {
         if (this.canBeDeactivated(previousEntry, t)) {
            this.runningThreadsCount.getAndDecrement();
            this.addToStandbyPool(t, false);
            return true;
         } else {
            return !this.executeWorkFromPriorityQueue(t, false);
         }
      } else {
         return false;
      }
   }

   private boolean canBeDeactivated(WorkAdapter prevWork, ExecuteThread t) {
      if (t.isStandby()) {
         return true;
      } else {
         if (prevWork != null && this.toDecrement > 0) {
            synchronized(this) {
               if (this.toDecrement > 0) {
                  --this.toDecrement;
                  t.setStandby(true);
                  this.standbyThreadsCount.getAndIncrement();
                  return true;
               }
            }
         }

         return false;
      }
   }

   void executeImmediately(WorkAdapter[] workList, boolean mtc) {
      if (workList != null && workList.length != 0) {
         if (debugRM.isDebugEnabled()) {
            debugRM.debug("<executeImmediately> Got new jobs which need to be executed immediately " + workList.length, new Exception());
         }

         for(int cnt = 0; cnt < workList.length; ++cnt) {
            workList[cnt].wm.started();
            workList[cnt].started();
         }

         this.queueDepth.getAndAdd(-workList.length);
         if (mtc) {
            this.mtcDepartures.getAndAdd((long)workList.length);
         }

         this.departures.getAndAdd((long)workList.length);
         ArrayList threads = this.getStandbyThreads(workList.length);
         int[] ids = this.threadID(workList.length - threads.size());
         this.executeWorkList(workList, threads, ids);
      }
   }

   private ArrayList getStandbyThreads(int mustRunCount) {
      ArrayList threads = new ArrayList();

      while(mustRunCount-- > 0) {
         ExecuteThread et = this.standbyThreadPool.poll((WorkAdapter)null);
         if (et == null) {
            break;
         }

         threads.add(et);
      }

      return threads;
   }

   private void executeWorkList(WorkAdapter[] workList, ArrayList threads, int[] ids) {
      int count = 0;
      Iterator var5 = threads.iterator();

      while(var5.hasNext()) {
         ExecuteThread et = (ExecuteThread)var5.next();
         this.runningThreadsCount.getAndIncrement();
         et.setRequest(workList[count++], true);
      }

      if (debugRM.isDebugEnabled()) {
         debugRM.debug("<executeWorkList> creating new threads " + ids.length);
      }

      int[] var9 = ids;
      int var10 = ids.length;

      for(int var7 = 0; var7 < var10; ++var7) {
         int i = var9[var7];
         this.createStandbyThreadAndExecute(i, workList[count++]);
      }

   }

   private static WorkAdapter getMaxConstraintWork(WorkAdapter previousEntry, boolean isStandbyThread) {
      assert previousEntry != null : "Previous work entry can't be null";

      MaxThreadsConstraint mtc = previousEntry.getMaxThreadsConstraint();
      if (mtc == null) {
         return null;
      } else {
         WorkAdapter work;
         synchronized(mtc) {
            work = mtc.getNext();
            if (work == null) {
               mtc.release();
               return null;
            }
         }

         MinThreadsConstraint wmin = work.getMinThreadsConstraint();
         if (wmin != null) {
            wmin.acquire(isStandbyThread);
         }

         return work;
      }
   }

   private static void workCompleted(WorkAdapter previousEntry) {
      previousEntry.wm.completed();
      previousEntry.returnForReuse();
   }

   private static void reclaimStuckThread(WorkAdapter previousEntry, ExecuteThread t) {
      t.setStuckThread((StuckThreadAction)null);
      notifyWMOfThreadUnstuck(t);
      T3SrvrLogger.logInfoUnstuckThread(t.getName());
      StuckThreadManager stm = previousEntry.getWorkManager().getStuckThreadManager();
      if (stm != null) {
         stm.threadUnStuck(t.id);
      }

   }

   private boolean executeWorkFromMinOrMaxQueue(WorkAdapter previousEntry, ExecuteThread t) {
      if (previousEntry == null) {
         return false;
      } else {
         MaxThreadsConstraint maxC = previousEntry.getMaxThreadsConstraint();
         if (maxC != null && maxC.releaseIfConstraintOverSubscribed()) {
            MinThreadsConstraint minC = previousEntry.getMinThreadsConstraint();
            if (minC != null) {
               minC.release(t.isStandby());
            }

            return false;
         } else {
            WorkAdapter next = this.getMinConstraintWork(previousEntry, t.isStandby());
            if (next == null) {
               next = getMaxConstraintWork(previousEntry, t.isStandby());
               if (next == null) {
                  return false;
               }
            }

            this.queueDepth.getAndDecrement();
            this.setNextRequest(t, false, next);
            return true;
         }
      }
   }

   private boolean executeWorkFromPriorityQueue(ExecuteThread t, boolean notifyThread) {
      WorkAdapter next = (WorkAdapter)this.queue.pop(MaxThreadsConstraint.CHECK_MAX_CONSTRAINT, this.POP_OR_ELSE, t);
      this.queueTouched.getAndIncrement();
      if (next == null) {
         return false;
      } else {
         next.wm.increaseMinThreadConstraintInProgress(t.isStandby());
         this.queueDepth.getAndDecrement();
         ExecuteThread executeThread = this.standbyThreadPool.switchThreadWithPartitionAffinity(t, next, false);
         if (t != executeThread) {
            notifyThread = true;
         }

         this.setNextRequest(executeThread, notifyThread, next);
         return true;
      }
   }

   private void setNextRequest(ExecuteThread t, boolean notifyThread, WorkAdapter next) {
      next.wm.started();
      next.started();
      this.departures.getAndIncrement();
      t.setRequest(next, notifyThread);
   }

   private void updateStats(WorkAdapter previousEntry, ExecuteThread t) {
      if (t.isStuck()) {
         reclaimStuckThread(previousEntry, t);
      }

      updateRequestClass((ServiceClassStatsSupport)previousEntry.requestClass, t);
      t.setHog(false);
   }

   static void updateRequestClass(ServiceClassStatsSupport previousRequestClass, ExecuteThread t) {
      if (previousRequestClass != null) {
         if (useEnhancedIncrementAdvisor) {
            long usageNS = t.updateTimeStampAndReturnCurrentThreadUsageNS();
            synchronized(previousRequestClass) {
               ++previousRequestClass.completedCount;
               if ((INCLUDE_HOGS_IN_SELF_TUNING_STATS || !t.isHog()) && (INCLUDE_LONG_RUNNING_THREADS_IN_SELF_TUNING_STATS || !t.isLongRunningTask())) {
                  previousRequestClass.threadUseNS += usageNS;
               } else {
                  previousRequestClass.threadUseHogNS += usageNS;
               }
            }
         } else {
            int usage = t.updateTimeStampAndReturnCurrentThreadUsage();
            synchronized(previousRequestClass) {
               ++previousRequestClass.completedCount;
               previousRequestClass.totalThreadUse += (long)usage;
               previousRequestClass.threadUseSquares += (long)(usage * usage);
            }
         }
      }

   }

   void activeRequestClassesInOverload() {
      if (this.requestClasses.size() != 0) {
         OverloadManager om = SelfTuningWorkManagerImpl.SHARED_OVERLOAD_MANAGER;
         boolean updateGlobal = false;
         if (om != null) {
            updateGlobal = this.getTotalRequestsCount() >= om.getCapacity();
            if (!updateGlobal) {
               om.resetActiveRequestClassesInOverload();
            }
         }

         List nearCapacityOverloadManagers = OverloadManager.getListOfNearCapacityPartitionOverloadManager();
         if (updateGlobal || nearCapacityOverloadManagers != null) {
            ArrayList sortedRequestClasses;
            synchronized(this.requestClasses) {
               sortedRequestClasses = new ArrayList(this.requestClasses);
            }

            Collections.sort(sortedRequestClasses);
            if (updateGlobal) {
               om.activeRequestClassNamesInOverload(sortedRequestClasses);
            }

            if (nearCapacityOverloadManagers != null) {
               Iterator var5 = nearCapacityOverloadManagers.iterator();

               while(var5.hasNext()) {
                  OverloadManager overloadManager = (OverloadManager)var5.next();
                  overloadManager.activeRequestClassNamesInOverload(sortedRequestClasses);
               }
            }

         }
      }
   }

   void updateElapsedTime(long elapsedTime) {
      this.stats.reset();
      synchronized(this.requestClasses) {
         Iterator var4 = this.requestClasses.iterator();

         while(var4.hasNext()) {
            RequestClass sc = (RequestClass)var4.next();
            if (sc != null) {
               sc.timeElapsed(elapsedTime, this.stats);
            }
         }

      }
   }

   void computeThreadUsage(long[] holder) {
      long completed = 0L;
      long threadTime = 0L;
      synchronized(this.requestClasses) {
         Iterator var7 = this.requestClasses.iterator();

         while(true) {
            if (!var7.hasNext()) {
               break;
            }

            ServiceClassStatsSupport scss = (ServiceClassStatsSupport)var7.next();
            if (scss != null) {
               completed += scss.getCompleted();
               threadTime += scss.getThreadUse();
            }
         }
      }

      holder[0] = completed;
      holder[1] = threadTime;
   }

   long computeThreadUsageNS(long[] holder) {
      long completed = 0L;
      long threadTime = 0L;
      long hogThreadTime = 0L;
      synchronized(this.requestClasses) {
         Iterator var9 = this.requestClasses.iterator();

         while(true) {
            if (!var9.hasNext()) {
               break;
            }

            ServiceClassStatsSupport scss = (ServiceClassStatsSupport)var9.next();
            if (scss != null) {
               completed += scss.getCompletedCountDelta();
               threadTime += scss.getAndResetThreadUseNS();
               hogThreadTime += scss.lastThreadUseHogNS;
            }
         }
      }

      holder[0] = (threadTime + 500000L) / 1000000L;
      holder[1] = (hogThreadTime + 500000L) / 1000000L;
      return completed;
   }

   void computeThreadPriorities() {
      ThreadPriorityManager.getInstance().computeThreadPriorities(this.requestClasses);
   }

   public void register(ServiceClassSupport scs) {
      synchronized(this.requestClasses) {
         this.requestClasses.add(scs);
      }
   }

   public void deregister(ServiceClassSupport scs) {
      synchronized(this.requestClasses) {
         this.requestClasses.remove(scs);
      }
   }

   public void register(MinThreadsConstraint mtc) {
      if (mtc != null) {
         this.minThreadsConstraints.add(mtc);
      }

   }

   public void deregister(MinThreadsConstraint mtc) {
      if (mtc != null) {
         this.minThreadsConstraints.remove(mtc);
      }

   }

   void processStartPartition(String partitionName, boolean rcmEnabled) {
      this.idleThreadPool.addPartition(partitionName, rcmEnabled);
      this.standbyThreadPool.addPartition(partitionName, rcmEnabled);
   }

   void cleanupForPartition(String partitionName) {
      if (partitionName != null) {
         ArrayList mtcToRemove = new ArrayList();
         Iterator var3 = this.minThreadsConstraints.iterator();

         while(var3.hasNext()) {
            MinThreadsConstraint minThreadsConstraint = (MinThreadsConstraint)var3.next();
            PartitionMinThreadsConstraint partitionMinThreadsConstraint = minThreadsConstraint.getPartitionMinThreadsConstraint();
            if (partitionMinThreadsConstraint != null && partitionName.equals(partitionMinThreadsConstraint.getPartitionName())) {
               mtcToRemove.add(minThreadsConstraint);
            }
         }

         this.minThreadsConstraints.removeAll(mtcToRemove);
         ArrayList rcToRemove = new ArrayList();
         Iterator var12;
         synchronized(this.requestClasses) {
            var12 = this.requestClasses.iterator();

            while(var12.hasNext()) {
               ServiceClassSupport requestClass = (ServiceClassSupport)var12.next();
               PartitionFairShare partitionFairShare = requestClass.getPartitionFairShare();
               if (partitionFairShare != null && partitionName.equals(partitionFairShare.getName())) {
                  rcToRemove.add(requestClass);
               }
            }

            this.requestClasses.removeAll(rcToRemove);
         }

         List extraStandbyThreads = this.standbyThreadPool.removePartition(partitionName);
         var12 = extraStandbyThreads.iterator();

         while(var12.hasNext()) {
            ExecuteThread extraStandbyThread = (ExecuteThread)var12.next();
            this.retireStandbyThread(extraStandbyThread, true);
         }

         if (!extraStandbyThreads.isEmpty()) {
            WorkManagerLogger.logRemovedStandbyThreads(extraStandbyThreads.size());
         }

         List extraIdleThreads = this.idleThreadPool.removePartition(partitionName);
         Iterator var15 = extraIdleThreads.iterator();

         while(var15.hasNext()) {
            ExecuteThread extraIdleThread = (ExecuteThread)var15.next();
            this.standbyThreadsCount.getAndIncrement();
            this.addToStandbyPool(extraIdleThread, true);
         }

      }
   }

   private WorkAdapter getMinConstraintWork(WorkAdapter previousEntry, boolean isStandbyThread) {
      assert previousEntry != null : "Previous work entry can't be null";

      MinThreadsConstraint mtc = previousEntry.getMinThreadsConstraint();
      if (mtc == null) {
         return null;
      } else {
         WorkAdapter work = mtc.getNext(isStandbyThread, this.getIdleThreadCount() > 0);
         if (work != null) {
            this.mtcDepartures.getAndIncrement();
            MaxThreadsConstraint pmax = previousEntry.getMaxThreadsConstraint();
            MaxThreadsConstraint wmax = work.getMaxThreadsConstraint();
            if (pmax != wmax) {
               if (pmax != null) {
                  pmax.release();
               }

               if (wmax != null) {
                  wmax.acquire();
               }
            }
         }

         return work;
      }
   }

   long getMinThreadsConstraintsCompleted() {
      return this.mtcDepartures.get();
   }

   int getMustRunCount() {
      int addup = 0;

      MinThreadsConstraint mtc;
      for(Iterator var2 = this.minThreadsConstraints.iterator(); var2.hasNext(); addup += mtc.getMustRunCount()) {
         mtc = (MinThreadsConstraint)var2.next();
      }

      return addup;
   }

   private boolean addToIdlePool(ExecuteThread t, boolean notifyThread) {
      if (!this.idleThreadPool.offer(t)) {
         t.setStandby(true);
         this.standbyThreadsCount.getAndIncrement();
         return this.addToStandbyPool(t, notifyThread);
      } else {
         return true;
      }
   }

   private boolean addToStandbyPool(ExecuteThread t, boolean notifyThread) {
      t.forceEraseThreadLocals();
      if (!this.standbyThreadPool.offer(t)) {
         this.retireStandbyThread(t, notifyThread);
         return false;
      } else {
         return true;
      }
   }

   private void retireStandbyThread(ExecuteThread t, boolean notifyThread) {
      this.standbyThreadsCount.getAndDecrement();
      synchronized(this) {
         this.recycledIDs.set(t.id);
      }

      synchronized(this.allThreads) {
         this.allThreads.remove(t);
      }

      t.setRequest(SHUTDOWN_REQUEST, notifyThread);
   }

   void handleMemoryPressure(int memLevel) {
      if (memLevel >= 0 && memLevel < this.targetStandbyThreadPoolSize.length) {
         int targetStandbyPoolSize = this.targetStandbyThreadPoolSize[memLevel];
         if (targetStandbyPoolSize == -1 || targetStandbyPoolSize > 256) {
            targetStandbyPoolSize = 256;
         }

         this.standbyThreadPool.setLimit(targetStandbyPoolSize);
         this.purgeStandbyThreads(targetStandbyPoolSize);
      }
   }

   void purgeStandbyThreads(int targetNumThreads) {
      int toPurge = this.standbyThreadPool.size() - targetNumThreads;
      ExecuteThread t = null;

      int numThreadsPurged;
      for(numThreadsPurged = 0; toPurge-- > 0 && (t = this.standbyThreadPool.poll((WorkAdapter)null)) != null; ++numThreadsPurged) {
         this.retireStandbyThread(t, true);
      }

      if (numThreadsPurged > 0) {
         WorkManagerLogger.logRemovedStandbyThreads(numThreadsPurged);
      }

   }

   public void cleanupThreadPoolTLs() {
      if (!ExecuteThread.isCleanupTLAfterEachRequest()) {
         ExecuteThread.updateRequestTLTime();
         WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
            public void run() {
               RequestManager.getInstance().cleanupThreadLocals();
            }
         });
      }

   }

   private void cleanupThreadLocals() {
      ExecuteThread t;
      while((t = this.idleThreadPool.poll((WorkAdapter)null)) != null) {
         this.runningThreadsCount.getAndIncrement();
         ACTIVATE_REQUEST.wm.acquireMinMaxConstraint(false);
         t.setRequest(ACTIVATE_REQUEST, true);
      }

   }

   boolean doForceCleanupThreadlocal(ExecuteThread thread) {
      if (Thread.currentThread() == thread) {
         thread.forceEraseThreadLocals();
         return true;
      } else {
         this.runningThreadsCount.getAndIncrement();
         THREADLOCAL_FORCE_CLEANUP_REQUEST.wm.acquireMinMaxConstraint(false);
         thread.setRequest(THREADLOCAL_FORCE_CLEANUP_REQUEST, true);
         return false;
      }
   }

   private void logThreadPoolSize(long currentTime) {
      if (currentTime - this.lastThreadPoolSizeLogTime >= THREAD_POOL_SIZE_LOG_INTERVAL_MILLIS) {
         this.lastThreadPoolSizeLogTime = currentTime;
         WorkManagerLogger.logSelfTuningThreadCounts(this.runningThreadsCount.get(), this.getIdleThreadCount(), this.standbyThreadsCount.get());
      }

   }

   int purgeHogs(int duration) {
      if (this.toDecrement != 0) {
         synchronized(this) {
            this.toDecrement = 0;
         }
      }

      long currentTime = System.currentTimeMillis();
      this.logThreadPoolSize(currentTime);
      long threshold = currentTime - (long)duration;
      int hogCount = 0;
      int totalThreadsChecked = 0;
      int longRunningTaskThreads = 0;
      int executingNonStandby = 0;
      int executingNonStandbyRunningMinTCWork = 0;
      synchronized(this.allThreads) {
         Iterator var12 = this.allThreads.iterator();

         while(true) {
            if (!var12.hasNext()) {
               break;
            }

            ExecuteThread t = (ExecuteThread)var12.next();
            ++totalThreadsChecked;
            Runnable r = t.getCurrentWork();
            if (r != null && t.isUnderExecution()) {
               if (!t.isStandby()) {
                  ++executingNonStandby;
                  SelfTuningWorkManagerImpl wm = t.getWorkManager();
                  if (wm != null && wm.min != null) {
                     ++executingNonStandbyRunningMinTCWork;
                  }
               }

               if (!INCLUDE_LONG_RUNNING_THREADS_IN_SELF_TUNING_STATS && t.isLongRunningTask()) {
                  ++longRunningTaskThreads;
               }

               if (threshold > t.getTimeStamp()) {
                  t.setHog(true);
                  if (!t.isStandby()) {
                     ++hogCount;
                  }
               }
            }
         }
      }

      this.hogCounter = hogCount;
      this.longRunningThreadCount = longRunningTaskThreads;
      this.allNonStandbyThreadsExecutingMinTCWork = executingNonStandby > 0 && executingNonStandby == executingNonStandbyRunningMinTCWork;
      int size = totalThreadsChecked - (hogCount + this.standbyThreadsCount.get() + longRunningTaskThreads);
      return size < 0 ? 0 : size;
   }

   private synchronized int threadID() {
      int i = this.recycledIDs.nextSetBit(0);
      if (i < 0) {
         return this.maxThreadIdValue++;
      } else {
         this.recycledIDs.clear(i);
         return i;
      }
   }

   private synchronized int[] threadID(int count) {
      int[] ids = new int[count];

      for(int cnt = 0; cnt < count; ++cnt) {
         ids[cnt] = this.threadID();
      }

      return ids;
   }

   void incrPoolSize(int incr) {
      if (incr != 0) {
         if (incr < 0) {
            if (debugRM.isDebugEnabled()) {
               debugRM.debug("<incrPoolSize> Decrease idleThread Pool size by " + -incr);
            }

            ExecuteThread t;
            while(incr < 0 && (t = this.idleThreadPool.poll((WorkAdapter)null)) != null) {
               ++incr;
               t.setStandby(true);
               this.standbyThreadsCount.getAndIncrement();
               this.addToStandbyPool(t, true);
            }

            if (incr < 0) {
               synchronized(this) {
                  this.toDecrement = -incr;
               }
            }

         } else {
            int incrementCount = IncrementAdvisor.getMaxThreadPoolSize() - this.getExecuteThreadCount() + this.standbyThreadsCount.get();
            if (incrementCount > 0) {
               for(incrementCount = Math.min(incr, incrementCount); incrementCount > 0; --incrementCount) {
                  ExecuteThread t = this.standbyThreadPool.poll((WorkAdapter)null);
                  if (t == null) {
                     break;
                  }

                  t.setStandby(false);
                  this.standbyThreadsCount.getAndDecrement();
                  this.runningThreadsCount.getAndIncrement();
                  ACTIVATE_REQUEST.wm.acquireMinMaxConstraint(false);
                  t.setRequest(ACTIVATE_REQUEST, true);
               }

               if (incrementCount > 0) {
                  if (debugRM.isDebugEnabled()) {
                     debugRM.debug("<incrPoolSize> Adding new threads " + incrementCount);
                  }

                  this.createIdleThreads(incrementCount);
               }
            }
         }
      }
   }

   private void createIdleThreads(int size) {
      int[] ids = this.threadID(size);
      int[] var3 = ids;
      int var4 = ids.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         int id = var3[var5];
         if (ACTIVATE_REQUEST.wm != null) {
            ACTIVATE_REQUEST.wm.acquireMinMaxConstraint(false);
         }

         this.createThreadAndExecute(id, ACTIVATE_REQUEST);
      }

   }

   private void createStandbyThreads(int size) {
      int[] ids = this.threadID(size);
      int[] var3 = ids;
      int var4 = ids.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         int id = var3[var5];
         if (ACTIVATE_REQUEST.wm != null) {
            ACTIVATE_REQUEST.wm.acquireMinMaxConstraint(true);
         }

         this.createStandbyThreadAndExecute(id, ACTIVATE_REQUEST);
      }

   }

   public int getQueueDepth() {
      int size = this.queueDepth.get();
      return size < 0 ? 0 : size;
   }

   public int getTotalRequestsCount() {
      return this.getQueueDepth() + this.runningThreadsCount.get();
   }

   int getRunningThreadsCount() {
      return this.runningThreadsCount.get();
   }

   public int getExecuteThreadCount() {
      synchronized(this.allThreads) {
         return this.allThreads.size();
      }
   }

   public int getExecuteThreadCount(String partitionName) {
      return this.getExecuteThreads(partitionName).length;
   }

   public long getQueueDepartures() {
      return this.departures.get();
   }

   public int getIdleThreadCount() {
      return this.idleThreadPool.size();
   }

   public int getStandbyCount() {
      return this.standbyThreadPool.size();
   }

   int getHogSize() {
      return this.hogCounter;
   }

   int getLongRunningThreadCount() {
      return this.longRunningThreadCount;
   }

   boolean isAllNonStandbyThreadsExecutingMinTCWork() {
      return DISABLE_ALL_NON_STANDBY_THREADS_EXECUTING_MINTC_WORK ? false : this.allNonStandbyThreadsExecutingMinTCWork;
   }

   ArrayList getStuckThreads(long maxTime, String partitionName) {
      long currentTime = System.currentTimeMillis();
      ArrayList stuckThreadList = new ArrayList();
      if (this.hogCounter != 0 && maxTime != 0L) {
         synchronized(this.allThreads) {
            Iterator var8 = this.allThreads.iterator();

            while(true) {
               ExecuteThread thread;
               do {
                  do {
                     if (!var8.hasNext()) {
                        return stuckThreadList;
                     }

                     thread = (ExecuteThread)var8.next();
                  } while(thread.getCurrentWork() == null);
               } while(partitionName != null && !partitionName.equals(thread.getPartitionName()));

               StuckThreadAction stuckThreadAction = isThreadStuck(thread, currentTime, maxTime);
               if (stuckThreadAction != null) {
                  stuckThreadList.add(thread);
                  if (!thread.isStuck()) {
                     notifyWMOfStuckThread(thread);
                     thread.setStuckThread(stuckThreadAction);
                     ThreadPriorityManager.handleHogger(thread, thread.isExecutingInternalWork());
                  }
               }
            }
         }
      } else {
         return null;
      }
   }

   private static StuckThreadAction isThreadStuck(ExecuteThread thread, long currentTime, long maxTime) {
      if (thread == null) {
         return null;
      } else {
         WorkAdapter currentWork = thread.getCurrentWork();
         if (currentWork != null && currentWork != ACTIVATE_REQUEST && currentWork != SHUTDOWN_REQUEST) {
            SelfTuningWorkManagerImpl wm = currentWork.getWorkManager();
            if (wm != null && !wm.isInternal()) {
               long timeStamp = thread.getTimeStamp();
               if (timeStamp <= 0L) {
                  return null;
               } else {
                  long elapsedTime = currentTime - timeStamp;
                  StuckThreadManager stm = wm.getStuckThreadManager();
                  if (stm != null) {
                     return stm.threadStuck(thread, elapsedTime, maxTime);
                  } else {
                     return elapsedTime >= maxTime ? DEFAULT_STUCK_THREAD_ACTION : null;
                  }
               }
            } else {
               return null;
            }
         } else {
            return null;
         }
      }
   }

   private static void notifyWMOfStuckThread(ExecuteThread thread) {
      SelfTuningWorkManagerImpl swm = thread.getWorkManager();
      if (swm != null) {
         swm.stuck();
      }

   }

   private static void notifyWMOfThreadUnstuck(ExecuteThread thread) {
      SelfTuningWorkManagerImpl swm = thread.getWorkManager();
      if (swm != null) {
         swm.unstuck();
      }

   }

   ExecuteThread[] getExecuteThreads(String partitionName) {
      ArrayList threadsList = new ArrayList();
      synchronized(this.allThreads) {
         Iterator var4 = this.allThreads.iterator();

         while(true) {
            if (!var4.hasNext()) {
               break;
            }

            ExecuteThread thread = (ExecuteThread)var4.next();
            if (partitionName == null || partitionName.equals(thread.getPartitionName())) {
               threadsList.add(thread);
            }
         }
      }

      ExecuteThread[] threads = new ExecuteThread[threadsList.size()];
      threadsList.toArray(threads);
      return threads;
   }

   public ExecuteThread[] getAllThreads() {
      return this.getExecuteThreads((String)null);
   }

   void releaseExecutingRequestFor(WorkManager wm, WorkFilter filter) {
      List works = new ArrayList();
      synchronized(this.allThreads) {
         Iterator var5 = this.allThreads.iterator();

         while(true) {
            if (!var5.hasNext()) {
               break;
            }

            ExecuteThread et = (ExecuteThread)var5.next();
            if (wm == et.getWorkManager()) {
               WorkAdapter work = et.getCurrentWork();
               if (work != null && (filter == null || filter.matches(work))) {
                  works.add(work);
               }
            }
         }
      }

      Iterator var4 = works.iterator();

      while(var4.hasNext()) {
         WorkAdapter work = (WorkAdapter)var4.next();
         work.release();
      }

   }

   void releaseExecutingRequestFor(WorkManager wm) {
      this.releaseExecutingRequestFor(wm, (WorkFilter)null);
   }

   double getThroughput() {
      return this.incrementAdvisor.getThroughput();
   }

   int getAndResetQueueTouched() {
      return this.queueTouched.getAndSet(0);
   }

   String getConciseStateDump() {
      return "Qsize:" + this.queue.size() + ",Qdepth:" + this.getQueueDepth() + ",all:" + this.getExecuteThreadCount() + ",running:" + this.getRunningThreadsCount() + ",idle:" + this.getIdleThreadCount() + ",standby:" + this.getStandbyCount() + ",hog:" + this.getHogSize() + ",longRunning:" + this.getLongRunningThreadCount() + ",departure:" + this.getQueueDepartures() + ",outoforder:" + this.getMinThreadsConstraintsCompleted();
   }

   boolean isBusyForScheduleIfBusy() {
      return this.incrementAdvisor.isThreadPoolBusy();
   }

   long getRequestQueueMaxValue() {
      return this.queue.getMaxValue();
   }

   private static boolean debugEnabled() {
      return SelfTuningWorkManagerImpl.debugEnabled();
   }

   private static void log(String str) {
      SelfTuningWorkManagerImpl.debug("<RequestManager> " + str);
   }

   private static class DefaultStuckThreadAction implements StuckThreadAction {
      private DefaultStuckThreadAction() {
      }

      public boolean threadStuck(int threadId, long elapsedTime, long maxTime) {
         return false;
      }

      public void threadUnStuck(int threadId) {
      }

      public int getStuckThreadCount() {
         return 0;
      }

      public void execute() {
      }

      public void withdraw() {
      }

      public String getName() {
         return "server-failure-trigger";
      }

      public long getMaxStuckTime() {
         return 0L;
      }

      // $FF: synthetic method
      DefaultStuckThreadAction(Object x0) {
         this();
      }
   }

   private static final class ThreadLocalForceCleanupRequest extends WorkAdapter {
      private ThreadLocalForceCleanupRequest() {
      }

      public void run() {
         ExecuteThread currentThread = (ExecuteThread)Thread.currentThread();
         currentThread.forceEraseThreadLocals();
      }

      // $FF: synthetic method
      ThreadLocalForceCleanupRequest(Object x0) {
         this();
      }
   }

   static final class ShutdownError extends Error {
   }

   private static final class ShutdownRequest extends WorkAdapter {
      private ShutdownRequest() {
      }

      public void run() {
         throw new ShutdownError();
      }

      // $FF: synthetic method
      ShutdownRequest(Object x0) {
         this();
      }
   }

   private static final class ActivateRequest extends WorkAdapter {
      private ActivateRequest() {
      }

      public void run() {
      }

      // $FF: synthetic method
      ActivateRequest(Object x0) {
         this();
      }
   }

   private class BufferQueueDrainer implements MaybeMapper, Runnable {
      private BufferQueueDrainer() {
      }

      public WorkAdapter unbox(WorkAdapter work, long v) {
         if (work.isCurrentVersion(v)) {
            RequestManager.this.addToCalendarQueue(work, v);
         }

         return work;
      }

      public void run() {
         while(true) {
            try {
               RequestManager.this.bufferWorkQueue.take(this);
            } catch (Throwable var2) {
               if (RequestManager.debugRM.isDebugEnabled()) {
                  RequestManager.debugRM.debug("RM.BufferQueueDrainer.run() ignore unexpected " + var2);
               }
            }
         }
      }

      // $FF: synthetic method
      BufferQueueDrainer(Object x1) {
         this();
      }
   }

   interface Callable {
      Object call(Object var1);
   }
}
