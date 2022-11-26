package org.glassfish.grizzly.threadpool;

final class ProbeNotifier {
   static void notifyThreadPoolStarted(AbstractThreadPool threadPool) {
      ThreadPoolProbe[] probes = (ThreadPoolProbe[])threadPool.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         ThreadPoolProbe[] var2 = probes;
         int var3 = probes.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            ThreadPoolProbe probe = var2[var4];
            probe.onThreadPoolStartEvent(threadPool);
         }
      }

   }

   static void notifyThreadPoolStopped(AbstractThreadPool threadPool) {
      ThreadPoolProbe[] probes = (ThreadPoolProbe[])threadPool.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         ThreadPoolProbe[] var2 = probes;
         int var3 = probes.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            ThreadPoolProbe probe = var2[var4];
            probe.onThreadPoolStopEvent(threadPool);
         }
      }

   }

   static void notifyThreadAllocated(AbstractThreadPool threadPool, Thread thread) {
      ThreadPoolProbe[] probes = (ThreadPoolProbe[])threadPool.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         ThreadPoolProbe[] var3 = probes;
         int var4 = probes.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            ThreadPoolProbe probe = var3[var5];
            probe.onThreadAllocateEvent(threadPool, thread);
         }
      }

   }

   static void notifyThreadReleased(AbstractThreadPool threadPool, Thread thread) {
      ThreadPoolProbe[] probes = (ThreadPoolProbe[])threadPool.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         ThreadPoolProbe[] var3 = probes;
         int var4 = probes.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            ThreadPoolProbe probe = var3[var5];
            probe.onThreadReleaseEvent(threadPool, thread);
         }
      }

   }

   static void notifyMaxNumberOfThreads(AbstractThreadPool threadPool, int maxNumberOfThreads) {
      ThreadPoolProbe[] probes = (ThreadPoolProbe[])threadPool.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         ThreadPoolProbe[] var3 = probes;
         int var4 = probes.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            ThreadPoolProbe probe = var3[var5];
            probe.onMaxNumberOfThreadsEvent(threadPool, maxNumberOfThreads);
         }
      }

   }

   static void notifyTaskQueued(AbstractThreadPool threadPool, Runnable task) {
      ThreadPoolProbe[] probes = (ThreadPoolProbe[])threadPool.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         ThreadPoolProbe[] var3 = probes;
         int var4 = probes.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            ThreadPoolProbe probe = var3[var5];
            probe.onTaskQueueEvent(threadPool, task);
         }
      }

   }

   static void notifyTaskDequeued(AbstractThreadPool threadPool, Runnable task) {
      ThreadPoolProbe[] probes = (ThreadPoolProbe[])threadPool.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         ThreadPoolProbe[] var3 = probes;
         int var4 = probes.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            ThreadPoolProbe probe = var3[var5];
            probe.onTaskDequeueEvent(threadPool, task);
         }
      }

   }

   static void notifyTaskCancelled(AbstractThreadPool threadPool, Runnable task) {
      ThreadPoolProbe[] probes = (ThreadPoolProbe[])threadPool.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         ThreadPoolProbe[] var3 = probes;
         int var4 = probes.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            ThreadPoolProbe probe = var3[var5];
            probe.onTaskCancelEvent(threadPool, task);
         }
      }

   }

   static void notifyTaskCompleted(AbstractThreadPool threadPool, Runnable task) {
      ThreadPoolProbe[] probes = (ThreadPoolProbe[])threadPool.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         ThreadPoolProbe[] var3 = probes;
         int var4 = probes.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            ThreadPoolProbe probe = var3[var5];
            probe.onTaskCompleteEvent(threadPool, task);
         }
      }

   }

   static void notifyTaskQueueOverflow(AbstractThreadPool threadPool) {
      ThreadPoolProbe[] probes = (ThreadPoolProbe[])threadPool.monitoringConfig.getProbesUnsafe();
      if (probes != null) {
         ThreadPoolProbe[] var2 = probes;
         int var3 = probes.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            ThreadPoolProbe probe = var2[var4];
            probe.onTaskQueueOverflowEvent(threadPool);
         }
      }

   }
}
