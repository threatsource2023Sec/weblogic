package weblogic.work.concurrent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.naming.Context;
import weblogic.work.WorkManager;
import weblogic.work.concurrent.context.ApplicationContextProcessor;
import weblogic.work.concurrent.future.AbstractFutureImpl;
import weblogic.work.concurrent.future.ManagedFutureImpl;
import weblogic.work.concurrent.spi.ConcurrentManagedObjectBuilder;
import weblogic.work.concurrent.spi.ContextProvider;
import weblogic.work.concurrent.spi.DaemonThreadManager;
import weblogic.work.concurrent.spi.RejectException;
import weblogic.work.concurrent.utils.LogUtils;

public class ManagedExecutorServiceImpl extends AbstractConcurrentManagedObject implements ManagedExecutorService {
   private final WorkManager workManager;
   private final int priority;
   protected final DaemonThreadManager daemonThreadManager;
   final AtomicLong submitedShortRunningRequest = new AtomicLong();
   final AtomicLong submitedLongRunningRequest = new AtomicLong();
   final AtomicLong completedLongRunningRequest = new AtomicLong();
   final AtomicLong completedShortRunningRequest = new AtomicLong();
   final AtomicLong failedRequest = new AtomicLong();
   final AtomicLong rejectedShortRunningRequest = new AtomicLong();
   protected ClassLoader taskClassloader;

   public ManagedExecutorServiceImpl(ConcurrentManagedObjectBuilder builder) {
      super(builder);
      this.workManager = builder.getWorkManager();
      this.daemonThreadManager = builder.getDaemonThreadManager();
      this.priority = builder.getPriority();
      if (this.cmoType == 1) {
         this.taskClassloader = this.parCL;
      }

   }

   protected ManagedExecutorServiceImpl(ManagedExecutorServiceImpl target, ContextProvider provider) {
      super(target, provider);
      this.workManager = target.workManager;
      this.daemonThreadManager = target.daemonThreadManager;
      this.priority = target.priority;
      this.taskClassloader = target.taskClassloader;
   }

   public int getPriority() {
      return this.priority;
   }

   private void startThread(AbstractFutureImpl work) {
      ExecutorLongRunningTask daemonThreadTask = new ExecutorLongRunningTask(work, this.priority);
      work.setDaemonThreadTask(daemonThreadTask);

      try {
         this.daemonThreadManager.createAndStart(daemonThreadTask);
      } catch (RejectException var4) {
         throw new RejectedExecutionException(var4);
      }
   }

   protected void warnUserObjectCheckSkipped(TaskWrapper taskWrapper) {
      if (this.cmoType == 1 && this.warnIfUserObjectCheckSkipped && !taskWrapper.isCheckUserObject()) {
         LogUtils.warnSkipClassloaderCheck(this.name, taskWrapper.getTaskName());
         this.warnIfUserObjectCheckSkipped = false;
      }

   }

   private Future scheduleWork(TaskWrapper taskWrapper, BatchTaskListener internalTaskListener) {
      this.warnUserObjectCheckSkipped(taskWrapper);
      TaskStateNotifier stateNotifier = new TaskStateNotifier(this, taskWrapper, internalTaskListener);
      ManagedFutureImpl work = new ManagedFutureImpl(taskWrapper, stateNotifier);
      if (taskWrapper.isLongRunning()) {
         this.startThread(work);
      } else {
         this.workManager.schedule(work);
      }

      stateNotifier.taskSubmitted(work);
      return work;
   }

   private List submitBatchTask(Collection tasks, BatchTaskListener batchTaskListener) {
      if (tasks.isEmpty()) {
         throw new IllegalArgumentException(LogUtils.getMessageEmptyTaskList());
      } else {
         this.checkServerStatus();
         List taskWrappers = new ArrayList();
         Iterator var4 = tasks.iterator();

         TaskWrapper taskWrapper;
         while(var4.hasNext()) {
            Callable c = (Callable)var4.next();
            taskWrapper = new TaskWrapper(c, this.getContextSetupProcessor(), this.taskClassloader);
            taskWrappers.add(taskWrapper);
         }

         List results = new ArrayList();
         Iterator var8 = taskWrappers.iterator();

         while(var8.hasNext()) {
            taskWrapper = (TaskWrapper)var8.next();
            results.add(this.scheduleWork(taskWrapper, batchTaskListener));
         }

         return results;
      }
   }

   protected void rejectUnsupportedRequestOutOfMES() {
      try {
         this.rejectIfOutOfScope();
         this.rejectIfSubmittingCompNotStarted();
      } catch (RejectException var2) {
         throw new RejectedExecutionException(var2);
      }
   }

   public List invokeAll(Collection tasks) throws InterruptedException {
      this.rejectUnsupportedRequestOutOfMES();
      AllDoneListener taskListener = new AllDoneListener(tasks.size());
      List results = this.submitBatchTask(tasks, taskListener);
      taskListener.await();
      return results;
   }

   public List invokeAll(Collection tasks, long timeout, TimeUnit unit) throws InterruptedException {
      this.rejectUnsupportedRequestOutOfMES();
      AllDoneListener taskListener = new AllDoneListener(tasks.size());
      List results = this.submitBatchTask(tasks, taskListener);
      if (!taskListener.await(timeout, unit)) {
         Iterator var7 = results.iterator();

         while(var7.hasNext()) {
            Future future = (Future)var7.next();
            future.cancel(true);
         }
      }

      return results;
   }

   public Object invokeAny(Collection tasks) throws InterruptedException, ExecutionException {
      this.rejectUnsupportedRequestOutOfMES();
      AnyDoneListener taskListener = new AnyDoneListener(tasks.size());
      List results = this.submitBatchTask(tasks, taskListener);
      boolean var11 = false;

      Object var4;
      try {
         var11 = true;
         var4 = taskListener.await();
         var11 = false;
      } finally {
         if (var11) {
            Iterator var8 = results.iterator();

            while(var8.hasNext()) {
               Future future = (Future)var8.next();
               future.cancel(true);
            }

         }
      }

      Iterator var5 = results.iterator();

      while(var5.hasNext()) {
         Future future = (Future)var5.next();
         future.cancel(true);
      }

      return var4;
   }

   public Object invokeAny(Collection tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
      this.rejectUnsupportedRequestOutOfMES();
      AnyDoneListener taskListener = new AnyDoneListener(tasks.size());
      List results = this.submitBatchTask(tasks, taskListener);
      boolean var14 = false;

      Object var7;
      try {
         var14 = true;
         var7 = taskListener.await(timeout, unit);
         var14 = false;
      } finally {
         if (var14) {
            Iterator var11 = results.iterator();

            while(var11.hasNext()) {
               Future future = (Future)var11.next();
               future.cancel(true);
            }

         }
      }

      Iterator var8 = results.iterator();

      while(var8.hasNext()) {
         Future future = (Future)var8.next();
         future.cancel(true);
      }

      return var7;
   }

   public Future submit(Callable task) {
      this.rejectUnsupportedRequestOutOfMES();
      this.checkServerStatus();
      TaskWrapper taskWrapper = new TaskWrapper(task, this.getContextSetupProcessor(), this.taskClassloader);
      return this.scheduleWork(taskWrapper, (BatchTaskListener)null);
   }

   public Future submit(Runnable run) {
      this.rejectUnsupportedRequestOutOfMES();
      this.checkServerStatus();
      TaskWrapper taskWrapper = new TaskWrapper(run, (Object)null, this.getContextSetupProcessor(), this.taskClassloader);
      return this.scheduleWork(taskWrapper, (BatchTaskListener)null);
   }

   public Future submit(Runnable run, Object result) {
      this.rejectUnsupportedRequestOutOfMES();
      this.checkServerStatus();
      TaskWrapper taskWrapper = new TaskWrapper(run, result, this.getContextSetupProcessor(), this.taskClassloader);
      taskWrapper.checkUserObject(result, this.taskClassloader);
      return this.scheduleWork(taskWrapper, (BatchTaskListener)null);
   }

   public void execute(Runnable command) {
      this.rejectUnsupportedRequestOutOfMES();
      this.checkServerStatus();
      TaskWrapper taskWrapper = new TaskWrapper(command, (Object)null, this.getContextSetupProcessor(), this.taskClassloader);
      this.scheduleWork(taskWrapper, (BatchTaskListener)null);
   }

   public boolean isShutdown() {
      throw new IllegalStateException(LogUtils.getMessageLiftcycleNotSupported());
   }

   public boolean isTerminated() {
      throw new IllegalStateException(LogUtils.getMessageLiftcycleNotSupported());
   }

   public void shutdown() {
      throw new IllegalStateException(LogUtils.getMessageLiftcycleNotSupported());
   }

   public List shutdownNow() {
      throw new IllegalStateException(LogUtils.getMessageLiftcycleNotSupported());
   }

   public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
      throw new IllegalStateException(LogUtils.getMessageLiftcycleNotSupported());
   }

   public long getSubmittedRequest(boolean longRunning) {
      return longRunning ? this.submitedLongRunningRequest.get() : this.submitedShortRunningRequest.get();
   }

   public long getCompletedRequest(boolean longRunning) {
      return longRunning ? this.completedLongRunningRequest.get() : this.completedShortRunningRequest.get();
   }

   public int getRunningLongRunningRequest() {
      return this.daemonThreadManager.getRunningThreadCount();
   }

   public long getRejectedRequest(boolean longRunning) {
      return longRunning ? this.daemonThreadManager.getRejectedThreads() : this.rejectedShortRunningRequest.get();
   }

   public long getFailedRequest() {
      return this.failedRequest.get();
   }

   protected final void checkServerStatus() {
      if (!this.daemonThreadManager.isStarted()) {
         throw new RejectedExecutionException(LogUtils.getMessageRejectForStop(this.name));
      }
   }

   public boolean start() {
      return this.daemonThreadManager.start();
   }

   public boolean stop() {
      return this.daemonThreadManager.stop();
   }

   public boolean isStarted() {
      return this.daemonThreadManager.isStarted();
   }

   public WorkManager getWorkManager() {
      return this.workManager;
   }

   public void shutdownThreadsSubmittedBy(String applicationId) {
      if (this.cmoType == 0) {
         this.daemonThreadManager.shutdownThreadsSubmittedBy(applicationId);
      }

   }

   public String getJSR236Class() {
      return ManagedExecutorService.class.getName();
   }

   AbstractConcurrentManagedObject.ConcurrentOpaqueReference createApplicationDelegator(ClassLoader classLoader, Context jndiContext) {
      return new AbstractConcurrentManagedObject.ConcurrentOpaqueReference(new ManagedExecutorServiceImpl(this, new ApplicationContextProcessor(this.getAppId(), classLoader, jndiContext, 4)));
   }
}
