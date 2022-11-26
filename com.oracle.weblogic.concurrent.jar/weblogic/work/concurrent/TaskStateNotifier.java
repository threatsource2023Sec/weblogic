package weblogic.work.concurrent;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import javax.enterprise.concurrent.ManagedTaskListener;

public class TaskStateNotifier {
   private final ManagedExecutorServiceImpl service;
   private final ManagedTaskListener listener;
   private final TaskWrapper task;
   private final BatchTaskListener internalTaskListener;
   private final boolean longRunningTask;

   public TaskStateNotifier(ManagedExecutorServiceImpl service, TaskWrapper task, BatchTaskListener internalTaskListener) {
      this.service = service;
      this.task = task;
      this.listener = task.buildTaskListener();
      this.internalTaskListener = internalTaskListener;
      this.longRunningTask = task.isLongRunning();
   }

   private void notifyTaskDone(Future future, Throwable throwable) {
      this.listener.taskDone(future, this.service, this.task.getUserTask(), throwable);
   }

   public void taskSuccess(Future future, Object result) {
      this.notifyTaskDone(future, (Throwable)null);
      if (this.longRunningTask) {
         this.service.completedLongRunningRequest.incrementAndGet();
      } else {
         this.service.completedShortRunningRequest.incrementAndGet();
      }

      if (this.internalTaskListener != null) {
         this.internalTaskListener.taskSuccess(result);
      }

   }

   public void taskFailed(Future future, ExecutionException exception) {
      this.notifyTaskDone(future, exception.getCause());
      this.service.failedRequest.incrementAndGet();
      if (this.internalTaskListener != null) {
         this.internalTaskListener.taskFailed(exception);
      }

   }

   public void taskSkipped(Future future, ExecutionException exception) {
      this.notifyTaskDone(future, exception);
      if (this.internalTaskListener != null) {
         this.internalTaskListener.taskFailed(exception);
      }

   }

   public void taskRejected(Future future, ExecutionException exception) {
      this.notifyTaskDone(future, exception);
      if (!this.longRunningTask) {
         this.service.rejectedShortRunningRequest.incrementAndGet();
      }

      if (this.internalTaskListener != null) {
         this.internalTaskListener.taskFailed(exception);
      }

   }

   public void taskCanceled(Future future, CancellationException exception) {
      this.notifyTaskDone(future, exception);
   }

   public void taskCanceling(Future future, CancellationException exception) {
      this.listener.taskAborted(future, this.service, this.task.getUserTask(), exception);
      if (this.internalTaskListener != null) {
         this.internalTaskListener.taskCanceled(exception);
      }

   }

   public void taskStarting(Future future) {
      this.listener.taskStarting(future, this.service, this.task.getUserTask());
   }

   public void taskSubmitted(Future future) {
      this.listener.taskSubmitted(future, this.service, this.task.getUserTask());
      if (this.longRunningTask) {
         this.service.submitedLongRunningRequest.incrementAndGet();
      } else {
         this.service.submitedShortRunningRequest.incrementAndGet();
      }

   }

   public ManagedExecutorServiceImpl getService() {
      return this.service;
   }
}
