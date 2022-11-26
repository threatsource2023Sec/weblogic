package org.jboss.weld.executor;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.jboss.weld.manager.api.ExecutorServices;
import org.jboss.weld.util.collections.Iterables;

public abstract class IterativeWorkerTaskFactory implements ExecutorServices.TaskFactory {
   private final Queue queue = new ConcurrentLinkedQueue();

   public IterativeWorkerTaskFactory(Iterable iterable) {
      Iterables.addAll(this.queue, iterable);
   }

   public List createTasks(int threadPoolSize) {
      int taskCount = Runtime.getRuntime().availableProcessors();
      if (threadPoolSize > 0) {
         taskCount = Math.min(Runtime.getRuntime().availableProcessors(), threadPoolSize);
      }

      List tasks = new LinkedList();

      for(int i = 0; i < taskCount; ++i) {
         tasks.add(new Callable() {
            public Void call() throws Exception {
               IterativeWorkerTaskFactory.this.init();
               Thread thread = Thread.currentThread();

               for(Object i = IterativeWorkerTaskFactory.this.queue.poll(); i != null && !thread.isInterrupted(); i = IterativeWorkerTaskFactory.this.queue.poll()) {
                  IterativeWorkerTaskFactory.this.doWork(i);
               }

               IterativeWorkerTaskFactory.this.cleanup();
               return null;
            }
         });
      }

      return tasks;
   }

   protected void init() {
   }

   protected void cleanup() {
   }

   protected abstract void doWork(Object var1);

   public Queue getQueue() {
      return this.queue;
   }
}
