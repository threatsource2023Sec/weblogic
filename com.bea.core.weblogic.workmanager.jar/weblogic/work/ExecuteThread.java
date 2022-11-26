package weblogic.work;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import sun.misc.Unsafe;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.kernel.AuditableThread;
import weblogic.kernel.KernelLogger;
import weblogic.kernel.KernelStatus;

public final class ExecuteThread extends AuditableThread {
   private static volatile boolean useDetailedThreadName = false;
   private boolean usingDetailedThreadName;
   static final String EXECUTING_WORK_STRING = ", executing work: ";
   static final String WM_STRING = " for workmanager: ";
   private static final DebugLogger selfTuningDebugLogger = DebugLogger.getDebugLogger("DebugSelfTuning");
   final int id;
   private String activeName;
   private String standbyName;
   private String stuckName;
   private volatile Calendar calendar = null;
   private ClassLoader defaultContextClassLoader;
   private volatile Date date = null;
   private final long threadStartTime;
   private ClassLoader contextClassLoader;
   private StuckThreadAction stuckThreadAction;
   private boolean started = false;
   private int executeCount = 0;
   private long timeStamp = 0L;
   private int usage = 0;
   private long usageNS = 0L;
   private long startTimeNS = 0L;
   private volatile WorkAdapter workEntry;
   private volatile WorkAdapter previousWork;
   private boolean standby = false;
   private boolean isHogger;
   private volatile long longRunningTaskOriginalStartTime;
   private volatile boolean underExecution;
   private volatile ComponentInvocationContext currentCIC;
   private volatile ComponentInvocationContext previousCIC;
   private static boolean cleanupTLAfterEachRequest = false;
   private long lastTLCleanupTime;
   private static volatile long requestTLTime;
   private static final Unsafe U;
   private static final long THREADLOCALS;
   private static final long INHERITABLETHREADLOCALS;
   private static ClassLoader weblogicSystemLoader;

   public static void setWeblogicSystemLoader(ClassLoader cl) {
      weblogicSystemLoader = cl;
   }

   ExecuteThread(int which, String name, ThreadGroup tg) {
      super(tg, "[ACTIVE] ExecuteThread: '" + which + "' for queue: '" + name + "'");
      this.id = which;
      String thdName = "ExecuteThread: '" + which + "' for queue: '" + name + "'";
      this.activeName = this.getName();
      this.standbyName = "[STANDBY] " + thdName;
      this.stuckName = "[STUCK] " + thdName;
      this.threadStartTime = System.currentTimeMillis();
      this.init();
   }

   private void init() {
      if (Thread.currentThread() instanceof ExecuteThread) {
         this.defaultContextClassLoader = ((ExecuteThread)Thread.currentThread()).defaultContextClassLoader;
      } else if (weblogicSystemLoader != null) {
         this.defaultContextClassLoader = weblogicSystemLoader;
      } else {
         this.defaultContextClassLoader = ClassLoader.getSystemClassLoader();
      }

      this.setContextClassLoader(this.defaultContextClassLoader);
      this.setDaemon(true);
      this.usingDetailedThreadName = selfTuningDebugLogger.isDebugEnabled() || useDetailedThreadName;
   }

   boolean isStarted() {
      return this.started;
   }

   public Calendar getCalendar() {
      Calendar result = this.calendar;
      if (result == null) {
         synchronized(this) {
            result = this.calendar;
            if (result == null) {
               this.calendar = (Calendar)(result = new GregorianCalendar());
               this.calendar.setTimeInMillis(this.threadStartTime);
            }
         }
      }

      return (Calendar)result;
   }

   public WorkAdapter getCurrentWork() {
      return this.workEntry;
   }

   public SelfTuningWorkManagerImpl getWorkManager() {
      WorkAdapter work = this.workEntry;
      if (work == null) {
         work = this.previousWork;
      }

      return work == null ? null : work.wm;
   }

   public Date getDate() {
      Date result = this.date;
      if (result == null) {
         synchronized(this) {
            result = this.date;
            if (result == null) {
               this.date = result = new Date(this.threadStartTime);
               this.date.setYear(this.date.getYear());
            }
         }
      }

      return result;
   }

   public int getExecuteCount() {
      return this.executeCount;
   }

   long getTimeStamp() {
      return this.timeStamp;
   }

   int updateTimeStampAndReturnCurrentThreadUsage() {
      if (this.timeStamp != 0L) {
         long now = System.currentTimeMillis();
         this.usage = (int)(now - this.timeStamp);
         this.timeStamp = now;
      }

      return this.usage;
   }

   long updateTimeStampAndReturnCurrentThreadUsageNS() {
      if (this.timeStamp != 0L) {
         this.timeStamp = System.currentTimeMillis();
      }

      if (this.startTimeNS != 0L) {
         long now = System.nanoTime();
         this.usageNS = now - this.startTimeNS;
         this.startTimeNS = now;
      }

      return this.usageNS;
   }

   void setStuckThread(StuckThreadAction value) {
      this.stuckThreadAction = value;
      if (value == null) {
         this.setName(this.standby ? this.standbyName : this.activeName);
      } else {
         this.setThdName((String)null);
      }

   }

   boolean isStuck() {
      return this.stuckThreadAction != null;
   }

   StuckThreadAction getStuckThreadAction() {
      return this.stuckThreadAction;
   }

   void setStandby(boolean flag) {
      if (this.standby != flag) {
         this.standby = flag;
         this.setName(this.standby ? this.standbyName : this.activeName);
      }

   }

   boolean isStandby() {
      return this.standby;
   }

   private void setThdName(String desc) {
      if (this.usingDetailedThreadName) {
         StringBuilder sb = new StringBuilder(" for workmanager: ");
         SelfTuningWorkManagerImpl workManager = this.getWorkManager();
         sb.append(workManager == null ? "null" : workManager.toString());
         WorkAdapter thisWorkEntry = this.workEntry;
         String workDesc = thisWorkEntry != null ? thisWorkEntry.getDescription() : null;
         if (workDesc == null) {
            workDesc = desc;
         }

         if (workDesc != null) {
            sb.append(", executing work: ").append(workDesc);
         }

         this.setName(this.isStuck() ? this.stuckName + sb.toString() : (this.standby ? this.standbyName + sb.toString() : this.activeName + sb.toString()));
      } else {
         this.setName(this.isStuck() ? this.stuckName : (this.standby ? this.standbyName : this.activeName));
      }

   }

   void setRequest(WorkAdapter workEntry) {
      this.setRequest(workEntry, false);
   }

   void setRequest(WorkAdapter work, boolean notifyThread) {
      this.workEntry = work;
      WorkAdapter previousWorkAdapter = this.previousWork;
      if (this.hasDebugModeChanged() || this.usingDetailedThreadName && (previousWorkAdapter == null || work == null || previousWorkAdapter.wm != work.wm || work.getDescription() != null)) {
         this.setThdName((String)null);
      }

      if (notifyThread) {
         synchronized(this) {
            this.notify();
         }
      }

   }

   private boolean hasDebugModeChanged() {
      boolean detailedName = selfTuningDebugLogger.isDebugEnabled() || useDetailedThreadName;
      if (detailedName != this.usingDetailedThreadName) {
         this.usingDetailedThreadName = detailedName;
         return true;
      } else {
         return false;
      }
   }

   private synchronized void waitForRequest() {
      while(this.workEntry == null) {
         try {
            this.wait();
         } catch (InterruptedException var2) {
         }
      }

   }

   public void run() {
      synchronized(this) {
         this.started = true;
         this.readyToRun();
         this.notify();
      }

      while(true) {
         SelfTuningWorkManagerImpl workManager = null;

         try {
            WorkAdapter currentWorkEntry = this.workEntry;
            if (currentWorkEntry != null) {
               workManager = currentWorkEntry.wm;
               if (U != null && U.getObject(this, INHERITABLETHREADLOCALS) == null) {
                  super.restoreCurrentJavaThreadStorage();
               }

               this.execute(currentWorkEntry);
            }

            this.previousWork = currentWorkEntry;
            currentWorkEntry = null;
            this.reset();

            try {
               RequestManager.getInstance().registerIdle(this, this.previousWork);
            } catch (RuntimeException var4) {
               KernelLogger.logExecuteFailed(var4);
            }

            workManager = null;
            this.previousWork = null;
            if (this.workEntry == null) {
               if (this.usingDetailedThreadName) {
                  this.setName(this.standby ? this.standbyName : this.activeName);
               }

               this.waitForRequest();
            }
         } catch (RequestManager.ShutdownError var6) {
            return;
         } catch (ThreadDeath var7) {
            if (workManager != null) {
               workManager.releaseMinMaxConstraint(this.standby);
            }

            if (KernelStatus.isServer()) {
               if (!KernelStatus.isIntentionalShutdown()) {
                  KernelLogger.logStopped(this.getName());
               }

               throw var7;
            }
         }
      }
   }

   void execute(WorkAdapter workAdapter) {
      SelfTuningWorkManagerImpl workManager = workAdapter.wm;

      try {
         ++this.executeCount;
         this.timeStamp = System.currentTimeMillis();
         this.startTimeNS = System.nanoTime();
         this.underExecution = true;
         this.setThreadPriority();
         if (workManager != null) {
            workManager.runWorkUnderContext(this, workAdapter);
         } else {
            this.setPreviousCIC((ComponentInvocationContext)null);
            workAdapter.getWork().run();
         }
      } catch (ThreadDeath var10) {
         throw var10;
      } catch (RequestManager.ShutdownError var11) {
         throw var11;
      } catch (OutOfMemoryError var12) {
         KernelLogger.logExecuteFailed(var12);
         if (workManager != null) {
            workManager.notifyOOME(var12);
         }
      } catch (Throwable var13) {
         KernelLogger.logExecuteFailed(var13);
      } finally {
         this.underExecution = false;
         this.usage = (int)(System.currentTimeMillis() - this.timeStamp);
         this.usageNS = System.nanoTime() - this.startTimeNS;
         this.timeStamp = 0L;
         this.startTimeNS = 0L;
         this.longRunningTaskOriginalStartTime = 0L;
      }

   }

   private void setThreadPriority() {
      try {
         RequestClass requestClass = this.workEntry.requestClass;
         if (requestClass == null) {
            return;
         }

         int requestedPriority = requestClass.getThreadPriority();
         if (this.getPriority() != requestedPriority) {
            this.setPriority(requestedPriority);
         }
      } catch (SecurityException var3) {
      }

   }

   public ClassLoader getContextClassLoader() {
      return this.contextClassLoader;
   }

   public void setContextClassLoader(ClassLoader cl) {
      this.contextClassLoader = cl != null ? cl : ClassLoader.getSystemClassLoader();
   }

   protected final void reset() {
      super.reset();
      if (!KernelStatus.isApplet()) {
         this.setContextClassLoader(this.defaultContextClassLoader);
      }

      this.workEntry = null;
      if (cleanupTLAfterEachRequest) {
         this.eraseThreadLocals();
      } else {
         this.eraseThreadLocalsIfNeeded();
      }

   }

   private final void eraseThreadLocals() {
      if (U != null) {
         U.putObject(this, THREADLOCALS, (Object)null);
         U.putObject(this, INHERITABLETHREADLOCALS, (Object)null);
      }
   }

   private final void eraseThreadLocalsIfNeeded() {
      if (!cleanupTLAfterEachRequest && requestTLTime != 0L) {
         if (this.lastTLCleanupTime < requestTLTime) {
            this.lastTLCleanupTime = System.currentTimeMillis();
            this.eraseThreadLocals();
         }

      }
   }

   final void forceEraseThreadLocals() {
      this.eraseThreadLocals();
      if (!cleanupTLAfterEachRequest) {
         this.lastTLCleanupTime = System.currentTimeMillis();
      }

   }

   public ClassLoader getDefaultContextClassLoader() {
      return this.defaultContextClassLoader;
   }

   void setHog(boolean b) {
      this.isHogger = b;
   }

   boolean isHog() {
      return this.isHogger;
   }

   void setLongRunningTask() {
      if (this.longRunningTaskOriginalStartTime == 0L) {
         this.longRunningTaskOriginalStartTime = this.timeStamp;
      }

   }

   boolean isLongRunningTask() {
      return this.longRunningTaskOriginalStartTime != 0L;
   }

   boolean isExecutingInternalWork() {
      try {
         return this.getWorkManager().isInternal();
      } catch (Exception var2) {
         return false;
      }
   }

   boolean isUnderExecution() {
      return this.underExecution;
   }

   static void setUseDetailedThreadName(boolean value) {
      useDetailedThreadName = value;
   }

   static boolean isCleanupTLAfterEachRequest() {
      return cleanupTLAfterEachRequest;
   }

   static void setCleanupTLAfterEachRequest(boolean cleanupTLAfterEachRequest) {
      ExecuteThread.cleanupTLAfterEachRequest = cleanupTLAfterEachRequest;
   }

   public static void updateWorkDescription(String desc) {
      Thread currThread = Thread.currentThread();
      if (currThread instanceof ExecuteThread) {
         ExecuteThread et = (ExecuteThread)currThread;
         if (et.isUnderExecution()) {
            et.setThdName(desc);
         }
      }

   }

   public void setCurrentCIC(ComponentInvocationContext newContext) {
      this.currentCIC = newContext;
   }

   public String getPartitionName() {
      ComponentInvocationContext cic = this.currentCIC;
      return cic == null ? null : cic.getPartitionName();
   }

   String getPreviousPartitionName() {
      ComponentInvocationContext prevCic = this.previousCIC;
      return prevCic == null ? null : prevCic.getPartitionName();
   }

   static void updateRequestTLTime() {
      requestTLTime = System.currentTimeMillis();
   }

   public void setPreviousCIC(ComponentInvocationContext prevCIC) {
      this.previousCIC = prevCIC;
   }

   static {
      Unsafe unsafe = null;
      long threadLocals = 0L;
      long inheritableThreadLocals = 0L;

      try {
         Field f = Unsafe.class.getDeclaredField("theUnsafe");
         f.setAccessible(true);
         unsafe = (Unsafe)f.get((Object)null);
         Class tk = Thread.class;
         threadLocals = unsafe.objectFieldOffset(tk.getDeclaredField("threadLocals"));
         inheritableThreadLocals = unsafe.objectFieldOffset(tk.getDeclaredField("inheritableThreadLocals"));
      } catch (Throwable var10) {
         WorkManagerLogger.logThreadLocalCleanupDisabled(var10);
      } finally {
         U = unsafe;
         THREADLOCALS = threadLocals;
         INHERITABLETHREADLOCALS = inheritableThreadLocals;
      }

   }
}
