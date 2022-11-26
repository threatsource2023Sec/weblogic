package weblogic.management.provider.internal;

import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;
import weblogic.deploy.service.DeploymentRequest;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.provider.EditAccess;
import weblogic.management.runtime.DeploymentRequestTaskRuntimeMBean;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.Timer;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.concurrent.TimeoutException;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

class ActivateQueue {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConfigurationEdit");
   private static final TimerManager timerManager = TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager();
   private static final WorkManager workManager = WorkManagerFactory.getInstance().getSystem();
   private final Deque deque = new ConcurrentLinkedDeque();
   private final Deque unlockedTasks = new ConcurrentLinkedDeque();

   public void enqueue(TaskWithTimeout task) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("[ActivateQueue] added to queue: " + task);
      }

      this.deque.offer(task);
      task.enqueued();
      this.processQueue();
   }

   private synchronized boolean remove(TaskWithTimeout task) {
      boolean rvfromUL = this.unlockedTasks.remove(task);
      boolean rvfromDQ = this.deque.remove(task);
      return rvfromUL || rvfromDQ;
   }

   private synchronized void unlock(TaskWithTimeout task) {
      if (this.deque.remove(task)) {
         this.unlockedTasks.offer(task);
      }

   }

   private synchronized void reLock(TaskWithTimeout task) {
      if (this.unlockedTasks.remove(task)) {
         Deque startedTask = new ConcurrentLinkedDeque();

         for(TaskWithTimeout firstOfDeque = this.deque.isEmpty() ? null : (TaskWithTimeout)this.deque.getFirst(); firstOfDeque != null && firstOfDeque.isStarted(); firstOfDeque = this.deque.isEmpty() ? null : (TaskWithTimeout)this.deque.getFirst()) {
            this.deque.remove(firstOfDeque);
            startedTask.addFirst(firstOfDeque);
         }

         this.deque.addFirst(task);

         while(!startedTask.isEmpty()) {
            this.deque.addFirst(startedTask.removeFirst());
         }
      }

   }

   TaskWithTimeout lookupTask(Long id) {
      Iterator var2 = this.deque.iterator();

      TaskWithTimeout task;
      do {
         if (!var2.hasNext()) {
            var2 = this.unlockedTasks.iterator();

            do {
               if (!var2.hasNext()) {
                  return null;
               }

               task = (TaskWithTimeout)var2.next();
            } while(!id.equals(task.requestId()));

            return task;
         }

         task = (TaskWithTimeout)var2.next();
      } while(!id.equals(task.requestId()));

      return task;
   }

   boolean isHead(TaskWithTimeout task) {
      return this.deque.isEmpty() ? false : this.deque.getFirst() == task;
   }

   public boolean containsTaskFor(EditAccess editAccess) {
      String partitionName = editAccess.getPartitionName();
      String editSessionName = editAccess.getEditSessionName();
      Iterator var4 = this.deque.iterator();

      TaskWithTimeout task;
      do {
         if (!var4.hasNext()) {
            return false;
         }

         task = (TaskWithTimeout)var4.next();
      } while(!partitionName.equals(task.getPartitionName()) || !editSessionName.equals(task.getEditSessionName()));

      return true;
   }

   private void processQueue() {
      synchronized(this.deque) {
         if (!this.deque.isEmpty()) {
            Iterator iterator = this.deque.iterator();
            TaskWithTimeout head = null;
            boolean promoted = false;

            TaskWithTimeout task;
            while(iterator.hasNext()) {
               task = (TaskWithTimeout)iterator.next();
               if (head == null) {
                  head = task;
               }

               if (!task.isStarted()) {
                  if (this.sameEditSessionAndPartition(head, task)) {
                     if (!this.anyUnLockedTaskRunningOnDomainOrSamePartition(task)) {
                        task.start();
                        if (debugLogger.isDebugEnabled()) {
                           debugLogger.debug("[ActivateQueue] started: " + task);
                        }
                     }
                  } else {
                     if (head.isStarted() || head.getConstrainedPartition() == null || head.getConstrainedPartition().equals("DOMAIN")) {
                        break;
                     }

                     if (!this.anyUnLockedTaskRunningOnDomainOrSamePartition(task)) {
                        this.promote(task);
                        promoted = true;
                        break;
                     }
                  }
               }
            }

            if (!promoted) {
               return;
            }

            iterator = this.deque.iterator();
            head = null;

            while(iterator.hasNext()) {
               task = (TaskWithTimeout)iterator.next();
               if (head == null) {
                  head = task;
               }

               if (!task.isStarted()) {
                  if (!this.sameEditSessionAndPartition(head, task)) {
                     break;
                  }

                  if (!this.anyUnLockedTaskRunningOnDomainOrSamePartition(task)) {
                     task.start();
                     if (debugLogger.isDebugEnabled()) {
                        debugLogger.debug("[ActivateQueue] started: " + task);
                     }
                  }
               }
            }
         }

      }
   }

   private boolean sameEditSessionAndPartition(TaskWithTimeout head, TaskWithTimeout task) {
      String partitionName = task.getPartitionName();
      String editSessionName = task.getEditSessionName();
      return head.getEditSessionName().equals(editSessionName) && head.getPartitionName().equals(partitionName);
   }

   private synchronized void promote(TaskWithTimeout task) {
      this.deque.remove(task);
      this.deque.addFirst(task);
   }

   private boolean anyUnLockedTaskRunningOnDomainOrSamePartition(TaskWithTimeout task) {
      if (this.findUnlockedSameEditSessionAndPartition(task)) {
         return false;
      } else {
         String partitionOfConfigChanges = task.getConstrainedPartition();
         if (partitionOfConfigChanges != null && !partitionOfConfigChanges.equals("DOMAIN")) {
            Iterator var3 = this.unlockedTasks.iterator();

            String otherRunningPartition;
            do {
               if (!var3.hasNext()) {
                  return false;
               }

               TaskWithTimeout other = (TaskWithTimeout)var3.next();
               otherRunningPartition = other.getConstrainedPartition();
            } while(otherRunningPartition != null && !otherRunningPartition.equals("DOMAIN") && !otherRunningPartition.equals(partitionOfConfigChanges));

            return true;
         } else {
            return !this.unlockedTasks.isEmpty();
         }
      }
   }

   private boolean findUnlockedSameEditSessionAndPartition(TaskWithTimeout task) {
      Iterator var2 = this.unlockedTasks.iterator();

      TaskWithTimeout other;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         other = (TaskWithTimeout)var2.next();
      } while(!this.sameEditSessionAndPartition(task, other));

      return true;
   }

   private void checkTimeout() {
      long currentTimeMillis = System.currentTimeMillis();
      boolean timeoutInDQ = this.checkTimeout(this.deque, currentTimeMillis);
      boolean timeoutInUL = this.checkTimeout(this.unlockedTasks, currentTimeMillis);
      if (timeoutInDQ || timeoutInUL) {
         this.processQueue();
      }

   }

   private boolean checkTimeout(Deque q, long currentTimeMillis) {
      boolean timeout = false;
      synchronized(q) {
         if (!q.isEmpty()) {
            Iterator iterator = q.iterator();

            while(iterator.hasNext()) {
               TaskWithTimeout task = (TaskWithTimeout)iterator.next();
               if (task.absoluteTimeout <= currentTimeMillis) {
                  timeout = true;
                  task.timeout();
                  iterator.remove();
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("[ActivateQueue] timed out: " + this);
                  }
               }
            }
         }

         return timeout;
      }
   }

   static class ResolveTaskWithTimeout extends TaskWithTimeout {
      private final ResolveActivateTask resolveActivateTask;

      ResolveTaskWithTimeout(ActivateQueue activateQueue, ResolveActivateTask resolveActivateTask) {
         super(activateQueue, resolveActivateTask.getTimeout());
         this.resolveActivateTask = resolveActivateTask;
      }

      public String getPartitionName() {
         return this.resolveActivateTask.getEditAccess().getPartitionName();
      }

      public String getEditSessionName() {
         return this.resolveActivateTask.getEditAccess().getEditSessionName();
      }

      protected void execute() {
         try {
            this.resolveActivateTask.execute();
         } finally {
            this.complete();
         }

      }

      protected void timeout() {
         this.resolveActivateTask.failed(new TimeoutException());
         this.complete();
      }

      public String getConstrainedPartition() {
         return this.getPartitionName();
      }

      public Long requestId() {
         return this.resolveActivateTask.getTaskId();
      }
   }

   static class ActivateTaskImplWithTimeout extends TaskWithTimeout implements ActivateTaskImpl.CompletionListener {
      private final ActivateTaskImpl activateTask;
      private final DeploymentRequest request;
      private volatile ActivateTaskImpl.StartListener startListener;
      private volatile ActivateTaskImpl.CompletionListener completionListener;
      private volatile String constrainedPartition = null;

      protected ActivateTaskImplWithTimeout(ActivateQueue activateQueue, ActivateTaskImpl activateTask, DeploymentRequest request, ActivateTaskImpl.StartListener startListener, ActivateTaskImpl.CompletionListener completionListener, long timeout) {
         super(activateQueue, timeout);
         this.activateTask = activateTask;
         this.request = request;
         this.startListener = startListener;
         this.completionListener = completionListener;
         this.activateTask.setCompletionListener(this);
         request.enqueued();
      }

      public void onCompleted() {
         try {
            if (this.completionListener != null) {
               this.completionListener.onCompleted();
               this.completionListener = null;
            }

            if (this.relockedAction != null) {
               this.activateTask.releaseEditLock();
               this.relockedAction = null;
            }
         } finally {
            this.activateTask.releaseEditAccess();
            this.complete();
         }

      }

      public String getPartitionName() {
         return this.activateTask.getPartitionName();
      }

      public String getEditSessionName() {
         return this.activateTask.getEditSessionName();
      }

      public String getConstrainedPartition() {
         if (this.constrainedPartition != null) {
            return this.constrainedPartition;
         } else {
            Iterator i = this.request.getDeployments();

            while(i.hasNext()) {
               Object d = i.next();
               if (d instanceof ConfigurationDeployment) {
                  this.constrainedPartition = ((ConfigurationDeployment)d).getConstrainedToPartitionName();
               }
            }

            return this.constrainedPartition;
         }
      }

      public void onLockReleased() {
         this.unlock();
      }

      public void execute() {
         if (this.startListener != null) {
            this.startListener.onStarted();
            this.startListener = null;
         }

         try {
            this.activateTask.getEditAccessImpl().doResolve(true, (ResolveActivateTask)null);
         } catch (Exception var4) {
            this.handleError(var4);
            return;
         }

         DeploymentRequestTaskRuntimeMBean taskRuntime = this.request.getTaskRuntime();

         try {
            taskRuntime.start();
         } catch (Exception var3) {
            this.handleError(var3);
         }

         if (ActivateQueue.debugLogger.isDebugEnabled()) {
            ActivateQueue.debugLogger.debug("Started deployment request with id " + taskRuntime.getTaskId() + " isComplete " + taskRuntime.isComplete());
         }

         this.activateTask.setDeploymentRequestTaskRuntimeMBean(taskRuntime);
         this.activateTask.setDeploymentStarted(true);
      }

      public DeploymentRequest getRequest() {
         return this.request;
      }

      private void handleError(Exception e) {
         if (ActivateQueue.debugLogger.isDebugEnabled()) {
            ActivateQueue.debugLogger.debug("Handle error in request id " + this.request.getId() + ": ", e);
         }

         this.activateTask.setError(e);
         this.activateTask.getEditAccessImpl().removeTask(this.activateTask);
         CommonAdminConfigurationManager.getInstance().cleanup(Long.toString(this.activateTask.getTaskId()));
         this.activateTask.setState(5);
      }

      public void timeout() {
         if (this.activateTask.getState() != 5 && this.activateTask.getState() != 4) {
            if (this.activateTask.getError() == null) {
               this.activateTask.setError(new TimeoutException());
            }

            this.activateTask.setState(5);
            this.activateTask.outputActivateTaskInfo(true, this.absoluteTimeout);
         }

         this.activateTask.releaseEditLock();
         this.remove();
      }

      public String toString() {
         return "CancellableTask{editSessionName=" + this.getEditSessionName() + ", requestId=" + this.request.getId() + ", absoluteTimeout=" + this.absoluteTimeout + ", isLockExclusive=" + this.isLockExclusive() + ", started=" + this.started + '}';
      }

      public Long requestId() {
         return this.request.getId();
      }

      private boolean isLockExclusive() {
         return this.activateTask.isLockExclusive();
      }

      ActivateTaskImpl getActivateTask() {
         return this.activateTask;
      }
   }

   abstract static class TaskWithTimeout {
      protected final ActivateQueue activateQueue;
      protected volatile Timer timeoutTimer;
      protected final long timeout;
      protected final long absoluteTimeout;
      protected volatile boolean started = false;
      protected volatile boolean unlocked = false;
      protected volatile Runnable relockedAction;

      protected TaskWithTimeout(ActivateQueue activateQueue, long timeout) {
         this.activateQueue = activateQueue;
         this.timeout = timeout;
         if (timeout == Long.MAX_VALUE) {
            this.absoluteTimeout = timeout;
         } else {
            this.absoluteTimeout = System.currentTimeMillis() + timeout;
         }

      }

      private void enqueued() {
         this.timeoutTimer = ActivateQueue.timerManager.schedule(new NakedTimerListener() {
            public void timerExpired(Timer timer) {
               TaskWithTimeout.this.activateQueue.checkTimeout();
            }
         }, this.timeout);
      }

      public final boolean isStarted() {
         return this.started;
      }

      public abstract String getPartitionName();

      public abstract String getEditSessionName();

      public abstract String getConstrainedPartition();

      public abstract Long requestId();

      public final void start() {
         this.started = true;
         if (this.relockedAction != null) {
            this.relockedAction.run();
         } else {
            this.execute();
         }

      }

      public synchronized void unlock() {
         if (!this.unlocked) {
            this.activateQueue.unlock(this);
            this.unlocked = true;
            this.activateQueue.processQueue();
         }
      }

      public synchronized void reLock(Runnable action) {
         if (this.unlocked) {
            this.activateQueue.reLock(this);
            this.unlocked = false;
            this.started = false;
            this.relockedAction = action;
            this.activateQueue.processQueue();
         }
      }

      public boolean isLockReleased() {
         return this.unlocked;
      }

      public final void complete() {
         if (ActivateQueue.debugLogger.isDebugEnabled()) {
            ActivateQueue.debugLogger.debug("[ActivateQueue] finished: " + this);
         }

         try {
            this.remove();
         } finally {
            ActivateQueue.workManager.schedule(new Runnable() {
               public void run() {
                  TaskWithTimeout.this.activateQueue.processQueue();
               }
            });
         }

      }

      final boolean remove() {
         this.timeoutTimer.cancel();
         return this.activateQueue.remove(this);
      }

      protected abstract void execute();

      protected abstract void timeout();
   }
}
