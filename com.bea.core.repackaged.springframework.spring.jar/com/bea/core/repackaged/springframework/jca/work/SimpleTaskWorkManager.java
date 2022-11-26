package com.bea.core.repackaged.springframework.jca.work;

import com.bea.core.repackaged.springframework.core.task.AsyncTaskExecutor;
import com.bea.core.repackaged.springframework.core.task.SimpleAsyncTaskExecutor;
import com.bea.core.repackaged.springframework.core.task.SyncTaskExecutor;
import com.bea.core.repackaged.springframework.core.task.TaskExecutor;
import com.bea.core.repackaged.springframework.core.task.TaskRejectedException;
import com.bea.core.repackaged.springframework.core.task.TaskTimeoutException;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import javax.resource.spi.work.ExecutionContext;
import javax.resource.spi.work.Work;
import javax.resource.spi.work.WorkAdapter;
import javax.resource.spi.work.WorkCompletedException;
import javax.resource.spi.work.WorkEvent;
import javax.resource.spi.work.WorkException;
import javax.resource.spi.work.WorkListener;
import javax.resource.spi.work.WorkManager;
import javax.resource.spi.work.WorkRejectedException;

public class SimpleTaskWorkManager implements WorkManager {
   @Nullable
   private TaskExecutor syncTaskExecutor = new SyncTaskExecutor();
   @Nullable
   private AsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();

   public void setSyncTaskExecutor(TaskExecutor syncTaskExecutor) {
      this.syncTaskExecutor = syncTaskExecutor;
   }

   public void setAsyncTaskExecutor(AsyncTaskExecutor asyncTaskExecutor) {
      this.asyncTaskExecutor = asyncTaskExecutor;
   }

   public void doWork(Work work) throws WorkException {
      this.doWork(work, Long.MAX_VALUE, (ExecutionContext)null, (WorkListener)null);
   }

   public void doWork(Work work, long startTimeout, @Nullable ExecutionContext executionContext, @Nullable WorkListener workListener) throws WorkException {
      Assert.state(this.syncTaskExecutor != null, "No 'syncTaskExecutor' set");
      this.executeWork(this.syncTaskExecutor, work, startTimeout, false, executionContext, workListener);
   }

   public long startWork(Work work) throws WorkException {
      return this.startWork(work, Long.MAX_VALUE, (ExecutionContext)null, (WorkListener)null);
   }

   public long startWork(Work work, long startTimeout, @Nullable ExecutionContext executionContext, @Nullable WorkListener workListener) throws WorkException {
      Assert.state(this.asyncTaskExecutor != null, "No 'asyncTaskExecutor' set");
      return this.executeWork(this.asyncTaskExecutor, work, startTimeout, true, executionContext, workListener);
   }

   public void scheduleWork(Work work) throws WorkException {
      this.scheduleWork(work, Long.MAX_VALUE, (ExecutionContext)null, (WorkListener)null);
   }

   public void scheduleWork(Work work, long startTimeout, @Nullable ExecutionContext executionContext, @Nullable WorkListener workListener) throws WorkException {
      Assert.state(this.asyncTaskExecutor != null, "No 'asyncTaskExecutor' set");
      this.executeWork(this.asyncTaskExecutor, work, startTimeout, false, executionContext, workListener);
   }

   protected long executeWork(TaskExecutor taskExecutor, Work work, long startTimeout, boolean blockUntilStarted, @Nullable ExecutionContext executionContext, @Nullable WorkListener workListener) throws WorkException {
      if (executionContext != null && executionContext.getXid() != null) {
         throw new WorkException("SimpleTaskWorkManager does not supported imported XIDs: " + executionContext.getXid());
      } else {
         WorkListener workListenerToUse = workListener;
         if (workListener == null) {
            workListenerToUse = new WorkAdapter();
         }

         boolean isAsync = taskExecutor instanceof AsyncTaskExecutor;
         DelegatingWorkAdapter workHandle = new DelegatingWorkAdapter(work, (WorkListener)workListenerToUse, !isAsync);

         WorkRejectedException wex;
         try {
            if (isAsync) {
               ((AsyncTaskExecutor)taskExecutor).execute(workHandle, startTimeout);
            } else {
               taskExecutor.execute(workHandle);
            }
         } catch (TaskTimeoutException var16) {
            wex = new WorkRejectedException("TaskExecutor rejected Work because of timeout: " + work, var16);
            wex.setErrorCode("1");
            ((WorkListener)workListenerToUse).workRejected(new WorkEvent(this, 2, work, wex));
            throw wex;
         } catch (TaskRejectedException var17) {
            wex = new WorkRejectedException("TaskExecutor rejected Work: " + work, var17);
            wex.setErrorCode("-1");
            ((WorkListener)workListenerToUse).workRejected(new WorkEvent(this, 2, work, wex));
            throw wex;
         } catch (Throwable var18) {
            WorkException wex = new WorkException("TaskExecutor failed to execute Work: " + work, var18);
            wex.setErrorCode("-1");
            throw wex;
         }

         if (isAsync) {
            ((WorkListener)workListenerToUse).workAccepted(new WorkEvent(this, 1, work, (WorkException)null));
         }

         if (blockUntilStarted) {
            long acceptanceTime = System.currentTimeMillis();
            synchronized(workHandle.monitor) {
               try {
                  while(!workHandle.started) {
                     workHandle.monitor.wait();
                  }
               } catch (InterruptedException var19) {
                  Thread.currentThread().interrupt();
               }
            }

            return System.currentTimeMillis() - acceptanceTime;
         } else {
            return -1L;
         }
      }
   }

   private static class DelegatingWorkAdapter implements Work {
      private final Work work;
      private final WorkListener workListener;
      private final boolean acceptOnExecution;
      public final Object monitor = new Object();
      public boolean started = false;

      public DelegatingWorkAdapter(Work work, WorkListener workListener, boolean acceptOnExecution) {
         this.work = work;
         this.workListener = workListener;
         this.acceptOnExecution = acceptOnExecution;
      }

      public void run() {
         if (this.acceptOnExecution) {
            this.workListener.workAccepted(new WorkEvent(this, 1, this.work, (WorkException)null));
         }

         synchronized(this.monitor) {
            this.started = true;
            this.monitor.notify();
         }

         this.workListener.workStarted(new WorkEvent(this, 3, this.work, (WorkException)null));

         try {
            this.work.run();
         } catch (Error | RuntimeException var3) {
            this.workListener.workCompleted(new WorkEvent(this, 4, this.work, new WorkCompletedException(var3)));
            throw var3;
         }

         this.workListener.workCompleted(new WorkEvent(this, 4, this.work, (WorkException)null));
      }

      public void release() {
         this.work.release();
      }
   }
}
