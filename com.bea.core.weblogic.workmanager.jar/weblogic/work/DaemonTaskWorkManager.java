package weblogic.work;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

public class DaemonTaskWorkManager extends WorkManagerLifecycleImpl {
   private static final ThreadGroup APP_DAEMON_GROUP = new ThreadGroup("Application Daemon Threads");
   private final ArrayList daemonThreads = new ArrayList();
   private AtomicLong nameCount = new AtomicLong();
   private static final String DAEMONWORKTHREAD = "DaemonWorkThread: '";
   private static final String WMSTART = "' of WorkManager: '";
   private static final String WMEND = "'";
   private DaemonTaskStatisticsCollector daemonTaskStatisticsCollector;

   public DaemonTaskWorkManager(WorkManager workManager) {
      super(workManager);
      if (workManager instanceof WorkManagerService) {
         workManager = ((WorkManagerService)workManager).getDelegate();
      }

      if (workManager instanceof DaemonTaskStatisticsCollector) {
         this.daemonTaskStatisticsCollector = (DaemonTaskStatisticsCollector)workManager;
      }

   }

   public void addDaemonThread(DaemonWorkThread thread) {
      synchronized(this.daemonThreads) {
         this.daemonThreads.add(thread);
      }
   }

   public void removeDaemonThread(DaemonWorkThread thread) {
      synchronized(this.daemonThreads) {
         this.daemonThreads.remove(thread);
      }
   }

   private DaemonWorkThread[] getDaemonWorkThreads() {
      synchronized(this.daemonThreads) {
         DaemonWorkThread[] threads = new DaemonWorkThread[this.daemonThreads.size()];
         this.daemonThreads.toArray(threads);
         return threads;
      }
   }

   public void schedule(Runnable runnable) {
      if (this.permitSchedule(runnable) && runnable instanceof WorkAdapter) {
         WorkAdapter work = (WorkAdapter)runnable;
         Thread thread = new DaemonWorkThread(APP_DAEMON_GROUP, work, this.getThreadName(), this);
         thread.setDaemon(true);
         thread.setPriority(10);
         thread.start();
         if (this.daemonTaskStatisticsCollector != null) {
            this.daemonTaskStatisticsCollector.daemonTaskStarted();
         }
      }

   }

   protected synchronized void releaseExecutingRequests() {
      DaemonWorkThread[] daemonThreads = this.getDaemonWorkThreads();

      for(int i = 0; i < daemonThreads.length; ++i) {
         WorkAdapter work = daemonThreads[i].getWork();
         if (work != null) {
            work.release();
         }
      }

   }

   public void workCompleted() {
      super.workCompleted();
      if (this.daemonTaskStatisticsCollector != null) {
         this.daemonTaskStatisticsCollector.daemonTaskCompleted();
      }

   }

   private String getThreadName() {
      return "DaemonWorkThread: '" + this.nameCount.getAndIncrement() + "' of WorkManager: '" + this.getName() + "'";
   }
}
