package weblogic.connector.work;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.connector.ConnectorLogger;
import weblogic.connector.common.Debug;
import weblogic.connector.extensions.LongRunning;
import weblogic.connector.security.layer.WorkImpl;
import weblogic.work.WorkManagerImpl;

public class LongRunningWorkManager {
   private final List activeWorks = new ArrayList();
   private volatile int maxConcurrentRequests;
   private volatile int activeWorkCount = 0;
   private volatile int completedWorkCount = 0;
   private volatile int rejectedWorkCount = 0;
   private volatile int totalWorkCount = 0;
   WorkContextManager ctxManager;

   LongRunningWorkManager(WorkContextManager ctxManager) {
      this.ctxManager = ctxManager;
   }

   public boolean isLongRunningWork(WorkImpl work) {
      return this.is16LongRunningWork(work) || this.isWLSLongRunningWork(work);
   }

   boolean is16LongRunningWork(WorkImpl work) {
      List wcList = work.getWorkContexts();
      if (wcList == null) {
         return false;
      } else {
         WorkContextProcessor.WMPreference wmPref = this.ctxManager.getpreferredWM(wcList);
         boolean isLongRunning = WorkContextProcessor.WMPreference.longRunningWM.equals(wmPref);
         if (isLongRunning && Debug.isWorkEnabled()) {
            Debug.work("WorkManager.is16LongRunningWork(): true : work:" + work);
         }

         return isLongRunning;
      }
   }

   boolean isWLSLongRunningWork(WorkImpl work) {
      boolean isLongRunning = work.getOriginalWork().getClass().isAnnotationPresent(LongRunning.class);
      if (isLongRunning && Debug.isWorkEnabled()) {
         Debug.work("WorkManager.isLongRunningWork(): true : work:" + work);
      }

      return isLongRunning;
   }

   boolean allowNewWork() {
      return this.activeWorkCount < this.maxConcurrentRequests;
   }

   void increaseRejecteCound() {
      ++this.rejectedWorkCount;
   }

   public void schedule(LongRunningWorkRequest request) {
      int counter;
      synchronized(this) {
         counter = this.totalWorkCount++;
         ++this.activeWorkCount;
         this.activeWorks.add(request);
      }

      WorkImpl work = request.getWork();
      work.getRuntimeMetadata().setWorkId((long)counter);
      String threadName = work.getRuntimeMetadata().getPreferredThreadname();
      if (Debug.isWorkEventsEnabled()) {
         Debug.workEvent("Will execute LONG RUNNING work in thread '" + threadName + "' for work " + request.getWork());
      }

      WorkManagerImpl.executeDaemonTask(threadName, 5, request);
   }

   public synchronized void cleanup() {
      Iterator var1 = this.activeWorks.iterator();

      while(var1.hasNext()) {
         LongRunningWorkRequest work = (LongRunningWorkRequest)var1.next();
         work.release();
      }

      this.activeWorks.clear();
   }

   public int getMaxConcurrentRequests() {
      return this.maxConcurrentRequests;
   }

   public void setMaxConcurrentRequests(int maxConcurrentRequests) {
      ConnectorLogger.logSetMaxConcurrentRequests(this.maxConcurrentRequests, maxConcurrentRequests);
      this.maxConcurrentRequests = maxConcurrentRequests;
   }

   public int getActiveWorkCount() {
      return this.activeWorkCount;
   }

   public int getCompletedWorkCount() {
      return this.completedWorkCount;
   }

   public int getRejectedWorkCount() {
      return this.rejectedWorkCount;
   }

   public int getTotalWorkCount() {
      return this.totalWorkCount;
   }

   protected synchronized void unregister(LongRunningWorkRequest request) {
      this.activeWorks.remove(request);
      --this.activeWorkCount;
      ++this.completedWorkCount;
   }
}
