package org.jboss.weld.executor;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.jboss.weld.logging.BootstrapLogger;
import org.jboss.weld.manager.api.ExecutorServices;
import org.jboss.weld.util.ForwardingExecutorService;

public class ProfilingExecutorServices implements ExecutorServices {
   private final ExecutorServices delegate;
   private final AtomicInteger executionId = new AtomicInteger();
   private final AtomicLong executionTimeSum = new AtomicLong();
   private final ProfilingExecutorService wrappedInstance = new ProfilingExecutorService();

   public ProfilingExecutorServices(ExecutorServices delegate) {
      this.delegate = delegate;
      BootstrapLogger.LOG.infov("Delegating to {0}", delegate);
   }

   public ExecutorService getTaskExecutor() {
      return this.wrappedInstance;
   }

   public void cleanup() {
      if (!this.getTaskExecutor().isShutdown()) {
         BootstrapLogger.LOG.infov("Total time spent in ThreadPool execution is {0} ms", this.executionTimeSum.get());
      }

      this.delegate.cleanup();
   }

   public List invokeAllAndCheckForExceptions(Collection tasks) {
      Measurement measurement = new Measurement();
      measurement.startProfiling();

      List var3;
      try {
         var3 = this.delegate.invokeAllAndCheckForExceptions(tasks);
      } finally {
         measurement.stopProfiling();
      }

      return var3;
   }

   public List invokeAllAndCheckForExceptions(ExecutorServices.TaskFactory factory) {
      Measurement measurement = new Measurement();
      measurement.startProfiling();

      List var3;
      try {
         var3 = this.delegate.invokeAllAndCheckForExceptions(factory);
      } finally {
         measurement.stopProfiling();
      }

      return var3;
   }

   public ExecutorServices getDelegate() {
      return this.delegate;
   }

   private class ProfilingExecutorService extends ForwardingExecutorService {
      private ProfilingExecutorService() {
      }

      protected ExecutorService delegate() {
         return ProfilingExecutorServices.this.delegate.getTaskExecutor();
      }

      public List invokeAll(Collection tasks) throws InterruptedException {
         Measurement measurement = ProfilingExecutorServices.this.new Measurement();

         List var3;
         try {
            measurement.startProfiling();
            var3 = this.delegate().invokeAll(tasks);
         } finally {
            measurement.stopProfiling();
         }

         return var3;
      }

      // $FF: synthetic method
      ProfilingExecutorService(Object x1) {
         this();
      }
   }

   private class Measurement {
      private static final int CALLER_STACK_TRACE_ORDER = 3;
      private volatile long start;
      private final int id;

      private Measurement() {
         this.start = 0L;
         this.id = ProfilingExecutorServices.this.executionId.incrementAndGet();
      }

      public void startProfiling() {
         if (this.start != 0L) {
            throw new IllegalStateException();
         } else {
            this.start = System.currentTimeMillis();
         }
      }

      public void stopProfiling() {
         if (this.start == 0L) {
            throw new IllegalStateException();
         } else {
            long current = System.currentTimeMillis();
            long time = current - this.start;
            StackTraceElement myCaller = Thread.currentThread().getStackTrace()[3];
            BootstrapLogger.LOG.infov("ThreadPool task execution with ID #{0} called by {1}.{2}()  took {3} ms", new Object[]{this.id, myCaller.getClassName(), myCaller.getMethodName(), time});
            this.start = 0L;
            ProfilingExecutorServices.this.executionTimeSum.addAndGet(time);
         }
      }

      // $FF: synthetic method
      Measurement(Object x1) {
         this();
      }
   }
}
