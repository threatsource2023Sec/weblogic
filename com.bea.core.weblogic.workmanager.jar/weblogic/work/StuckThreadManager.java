package weblogic.work;

import java.util.ArrayList;
import java.util.BitSet;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;

final class StuckThreadManager {
   private static final DebugCategory debug = Debug.getCategory("weblogic.stuckthreadhandling");
   private final StuckThreadAction[] stuckThreadActions;
   private final String name;
   private BitSet stuckThreads = new BitSet();

   StuckThreadManager() {
      this.stuckThreadActions = null;
      this.name = "NO STUCK THREAD ACTIONS !";
   }

   StuckThreadManager(StuckThreadAction workManagerShutdown, StuckThreadAction adminMode, StuckThreadAction serverFailure) {
      StringBuffer sb = new StringBuffer();
      ArrayList list = new ArrayList();
      if (workManagerShutdown != null) {
         sb.append("WorkManagerShutdown: " + workManagerShutdown + "\n");
         list.add(workManagerShutdown);
      }

      if (adminMode != null) {
         sb.append("ApplicationAdminMode: " + adminMode + "\n");
         list.add(adminMode);
      }

      if (serverFailure != null) {
         sb.append("ServerFailureAction: " + serverFailure + "\n");
         list.add(serverFailure);
      }

      this.name = sb.toString();
      if (this.isDebugEnabled()) {
         this.debug("created StuckThreadManager");
      }

      this.stuckThreadActions = new StuckThreadAction[list.size()];
      list.toArray(this.stuckThreadActions);
   }

   public void threadUnStuck(int threadId) {
      if (this.stuckThreadActions != null) {
         if (this.isDebugEnabled()) {
            this.debug("Thread detected as unstuck !");
         }

         for(int cnt = 0; cnt < this.stuckThreadActions.length; ++cnt) {
            this.stuckThreadActions[cnt].threadUnStuck(threadId);
         }

         synchronized(this) {
            this.stuckThreads.clear(threadId);
         }
      }
   }

   public StuckThreadAction threadStuck(ExecuteThread thread, long elapsedTime, long maxTime) {
      if (this.stuckThreadActions == null) {
         return null;
      } else {
         int threadId = thread.id;
         StuckThreadAction stuck = null;
         if (this.isDebugEnabled()) {
            this.debug("Checking for stuck threads");
         }

         for(int cnt = 0; cnt < this.stuckThreadActions.length; ++cnt) {
            if (this.stuckThreadActions[cnt].threadStuck(threadId, elapsedTime, maxTime)) {
               stuck = this.stuckThreadActions[cnt];
            }
         }

         if (stuck != null) {
            boolean invokeCancel;
            synchronized(this) {
               if (invokeCancel = !this.stuckThreads.get(threadId)) {
                  this.stuckThreads.set(threadId);
               }
            }

            if (invokeCancel) {
               if (this.isDebugEnabled()) {
                  this.debug("Thread detected as stuck !");
               }

               this.cancelWork(thread);
            }
         }

         return stuck;
      }
   }

   private void cancelWork(ExecuteThread thread) {
      Work work = thread.getCurrentWork();
      if (work != null) {
         Runnable cancelTask = work.cancel("Work cancelled due to stuck thread");
         if (cancelTask != null) {
            WorkManagerFactory.getInstance().getRejector().schedule(cancelTask);
         }
      }

   }

   int getStuckThreadCount() {
      return this.stuckThreads.cardinality();
   }

   public String toString() {
      return this.name;
   }

   private boolean isDebugEnabled() {
      return debug.isEnabled();
   }

   private void debug(String str) {
      WorkManagerLogger.logDebug("[StuckThreadManager][" + this.name + "]" + str);
   }
}
