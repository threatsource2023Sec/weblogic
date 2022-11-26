package weblogic.work;

import java.util.ArrayList;
import java.util.List;
import weblogic.utils.UnsyncCircularQueue;
import weblogic.utils.collections.Stack;

public final class WorkManagerLite extends WorkManagerImpl {
   private static final ThreadGroup THREAD_GROUP = initThreadGroup();
   private static final int THREAD_POOL_SIZE = initThreadPoolSize();
   private final Stack idleThreads;
   private final List threadPool;
   private final UnsyncCircularQueue queue;
   private static final String THREAD_POOL_SIZE_PROP = "weblogic.thinclient.kernel.ThreadPoolSize";
   private static final int DEFAULT_POOL_SIZE = 5;

   public String getName() {
      return this.wmName;
   }

   public String getApplicationName() {
      return null;
   }

   public String getModuleName() {
      return null;
   }

   public int getType() {
      return 2;
   }

   public int getConfiguredThreadCount() {
      return this.threadPool.size();
   }

   private static int initThreadPoolSize() {
      try {
         return Integer.getInteger("weblogic.thinclient.kernel.ThreadPoolSize", 5);
      } catch (SecurityException var1) {
         return 5;
      } catch (NumberFormatException var2) {
         return 5;
      }
   }

   private static ThreadGroup initThreadGroup() {
      try {
         return new ThreadGroup("Pooled Threads");
      } catch (SecurityException var1) {
         return null;
      }
   }

   private ExecuteThreadLite create(int id) {
      return new ExecuteThreadLite(id, this, THREAD_GROUP);
   }

   private void start(ExecuteThreadLite t) {
      t.start();
      synchronized(t) {
         while(!t.isStarted()) {
            try {
               t.wait();
            } catch (InterruptedException var5) {
            }
         }

      }
   }

   WorkManagerLite() {
      this.idleThreads = new Stack();
      this.threadPool = new ArrayList();
      this.queue = new UnsyncCircularQueue();
      this.wmName = "direct";
   }

   WorkManagerLite(String name) {
      this(name, THREAD_POOL_SIZE);
   }

   WorkManagerLite(String name, int threadCount) {
      this.idleThreads = new Stack();
      this.threadPool = new ArrayList();
      this.queue = new UnsyncCircularQueue();
      this.wmName = name;
      this.setThreadCount(threadCount);
   }

   public void setThreadCount(int threadCount) {
      synchronized(this) {
         if (threadCount > this.threadPool.size()) {
            int firstNew = this.threadPool.size();

            for(int count = firstNew; count < threadCount; ++count) {
               ExecuteThreadLite t = this.create(count);
               this.threadPool.add(t);
               this.start(t);
            }

         }
      }
   }

   public void schedule(Runnable runnable) {
      if ("direct" == this.wmName) {
         runnable.run();
      } else {
         ExecuteThreadLite t = null;
         synchronized(this) {
            if (this.idleThreads.size() > 0) {
               t = (ExecuteThreadLite)this.idleThreads.pop();
            }

            if (t == null) {
               this.queue.put(runnable);
               return;
            }
         }

         t.notifyRequest(runnable);
      }
   }

   public void registerIdle(ExecuteThreadLite t) {
      Runnable runnable = null;
      synchronized(this) {
         runnable = (Runnable)this.queue.get();
         if (runnable == null) {
            this.idleThreads.push(t);
            return;
         }
      }

      t.setRequest(runnable);
   }

   public boolean executeIfIdle(Runnable runnable) {
      ExecuteThreadLite t;
      synchronized(this) {
         if (this.idleThreads.size() == 0) {
            return false;
         }

         t = (ExecuteThreadLite)this.idleThreads.pop();
      }

      t.notifyRequest(runnable);
      return true;
   }

   public boolean scheduleIfBusy(Runnable runnable) {
      if (this.getQueueDepth() > 0) {
         this.schedule(runnable);
         return true;
      } else {
         return false;
      }
   }

   public int getQueueDepth() {
      return this.queue.size();
   }

   public boolean isThreadOwner(Thread th) {
      if (!(th instanceof ExecuteThreadLite)) {
         return false;
      } else {
         ExecuteThreadLite et = (ExecuteThreadLite)th;
         return this == et.getWorkManager();
      }
   }

   public void setInternal() {
   }

   public boolean isInternal() {
      return false;
   }
}
