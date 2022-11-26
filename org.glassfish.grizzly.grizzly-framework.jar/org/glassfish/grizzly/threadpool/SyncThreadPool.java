package org.glassfish.grizzly.threadpool;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

public class SyncThreadPool extends AbstractThreadPool {
   private final Queue workQueue;
   protected int maxQueuedTasks = -1;
   private int currentPoolSize;
   private int activeThreadsCount;

   public SyncThreadPool(ThreadPoolConfig config) {
      super(config);
      if (config.getKeepAliveTime(TimeUnit.MILLISECONDS) < 0L) {
         throw new IllegalArgumentException("keepAliveTime < 0");
      } else {
         this.workQueue = config.getQueue() != null ? config.getQueue() : config.setQueue(new LinkedList()).getQueue();
         this.maxQueuedTasks = config.getQueueLimit();
         int corePoolSize = config.getCorePoolSize();

         while(this.currentPoolSize < corePoolSize) {
            this.startWorker(new SyncThreadWorker(true));
         }

         ProbeNotifier.notifyThreadPoolStarted(this);
      }
   }

   public void execute(Runnable task) {
      if (task == null) {
         throw new IllegalArgumentException("Runnable task is null");
      } else {
         synchronized(this.stateLock) {
            if (!this.running) {
               throw new RejectedExecutionException("ThreadPool is not running");
            } else {
               int workQueueSize = this.workQueue.size() + 1;
               if ((this.maxQueuedTasks < 0 || workQueueSize <= this.maxQueuedTasks) && this.workQueue.offer(task)) {
                  this.onTaskQueued(task);
               } else {
                  this.onTaskQueueOverflow();

                  assert false;
               }

               int idleThreadsNumber = this.currentPoolSize - this.activeThreadsCount;
               if (idleThreadsNumber >= workQueueSize) {
                  this.stateLock.notify();
               } else {
                  if (this.currentPoolSize < this.config.getMaxPoolSize()) {
                     boolean isCore = this.currentPoolSize < this.config.getCorePoolSize();
                     this.startWorker(new SyncThreadWorker(isCore));
                     if (this.currentPoolSize == this.config.getMaxPoolSize()) {
                        this.onMaxNumberOfThreadsReached();
                     }
                  }

               }
            }
         }
      }
   }

   protected void startWorker(AbstractThreadPool.Worker worker) {
      synchronized(this.stateLock) {
         super.startWorker(worker);
         ++this.activeThreadsCount;
         ++this.currentPoolSize;
      }
   }

   protected void onWorkerExit(AbstractThreadPool.Worker worker) {
      super.onWorkerExit(worker);
      synchronized(this.stateLock) {
         --this.currentPoolSize;
         --this.activeThreadsCount;
      }
   }

   protected void poisonAll() {
      int size = this.currentPoolSize;
      Queue q = this.getQueue();

      while(size-- > 0) {
         q.offer(poison);
      }

   }

   public String toString() {
      synchronized(this.stateLock) {
         return super.toString() + ", max-queue-size=" + this.maxQueuedTasks;
      }
   }

   protected class SyncThreadWorker extends AbstractThreadPool.Worker {
      private final boolean core;

      public SyncThreadWorker(boolean core) {
         super();
         this.core = core;
      }

      protected Runnable getTask() throws InterruptedException {
         synchronized(SyncThreadPool.this.stateLock) {
            SyncThreadPool.this.activeThreadsCount--;

            try {
               Runnable r;
               if (SyncThreadPool.this.running && (this.core || SyncThreadPool.this.currentPoolSize <= SyncThreadPool.this.config.getMaxPoolSize())) {
                  r = (Runnable)SyncThreadPool.this.workQueue.poll();
                  if (r != null) {
                     Runnable var15 = r;
                     return var15;
                  } else {
                     long keepAliveMillis = SyncThreadPool.this.config.getKeepAliveTime(TimeUnit.MILLISECONDS);
                     boolean hasKeepAlive = !this.core && keepAliveMillis >= 0L;
                     long endTime = -1L;
                     if (hasKeepAlive) {
                        endTime = System.currentTimeMillis() + keepAliveMillis;
                     }

                     while(true) {
                        if (!hasKeepAlive) {
                           SyncThreadPool.this.stateLock.wait();
                        } else {
                           SyncThreadPool.this.stateLock.wait(keepAliveMillis);
                        }

                        r = (Runnable)SyncThreadPool.this.workQueue.poll();
                        Runnable var8;
                        if (r != null) {
                           var8 = r;
                           return var8;
                        }

                        if (!SyncThreadPool.this.running) {
                           var8 = null;
                           return var8;
                        }

                        if (hasKeepAlive) {
                           keepAliveMillis = endTime - System.currentTimeMillis();
                           if (keepAliveMillis < 20L) {
                              var8 = null;
                              return var8;
                           }
                        }
                     }
                  }
               } else {
                  r = null;
                  return r;
               }
            } finally {
               SyncThreadPool.this.activeThreadsCount++;
            }
         }
      }
   }
}
