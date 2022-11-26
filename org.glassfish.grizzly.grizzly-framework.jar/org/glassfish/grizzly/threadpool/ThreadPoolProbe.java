package org.glassfish.grizzly.threadpool;

public interface ThreadPoolProbe {
   void onThreadPoolStartEvent(AbstractThreadPool var1);

   void onThreadPoolStopEvent(AbstractThreadPool var1);

   void onThreadAllocateEvent(AbstractThreadPool var1, Thread var2);

   void onThreadReleaseEvent(AbstractThreadPool var1, Thread var2);

   void onMaxNumberOfThreadsEvent(AbstractThreadPool var1, int var2);

   void onTaskQueueEvent(AbstractThreadPool var1, Runnable var2);

   void onTaskDequeueEvent(AbstractThreadPool var1, Runnable var2);

   void onTaskCancelEvent(AbstractThreadPool var1, Runnable var2);

   void onTaskCompleteEvent(AbstractThreadPool var1, Runnable var2);

   void onTaskQueueOverflowEvent(AbstractThreadPool var1);

   public static class Adapter implements ThreadPoolProbe {
      public void onThreadPoolStartEvent(AbstractThreadPool threadPool) {
      }

      public void onThreadPoolStopEvent(AbstractThreadPool threadPool) {
      }

      public void onThreadAllocateEvent(AbstractThreadPool threadPool, Thread thread) {
      }

      public void onThreadReleaseEvent(AbstractThreadPool threadPool, Thread thread) {
      }

      public void onMaxNumberOfThreadsEvent(AbstractThreadPool threadPool, int maxNumberOfThreads) {
      }

      public void onTaskQueueEvent(AbstractThreadPool threadPool, Runnable task) {
      }

      public void onTaskDequeueEvent(AbstractThreadPool threadPool, Runnable task) {
      }

      public void onTaskCancelEvent(AbstractThreadPool threadPool, Runnable task) {
      }

      public void onTaskCompleteEvent(AbstractThreadPool threadPool, Runnable task) {
      }

      public void onTaskQueueOverflowEvent(AbstractThreadPool threadPool) {
      }
   }
}
