package org.glassfish.hk2.runlevel.internal;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.IndexedFilter;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.runlevel.ChangeableRunLevelFuture;
import org.glassfish.hk2.runlevel.ErrorInformation;
import org.glassfish.hk2.runlevel.ProgressStartedListener;
import org.glassfish.hk2.runlevel.RunLevel;
import org.glassfish.hk2.runlevel.RunLevelListener;
import org.glassfish.hk2.runlevel.Sorter;
import org.glassfish.hk2.runlevel.utilities.Utilities;

public class CurrentTaskFuture implements ChangeableRunLevelFuture {
   private final AsyncRunLevelContext asyncContext;
   private final Executor executor;
   private final ServiceLocator locator;
   private int proposedLevel;
   private final boolean useThreads;
   private final List allListenerHandles;
   private final List allProgressStartedHandles;
   private final List allSorterHandles;
   private final int maxThreads;
   private final Timer timer;
   private final long cancelTimeout;
   private UpAllTheWay upAllTheWay;
   private DownAllTheWay downAllTheWay;
   private boolean done = false;
   private boolean cancelled = false;
   private boolean inCallback = false;

   CurrentTaskFuture(AsyncRunLevelContext asyncContext, Executor executor, ServiceLocator locator, int proposedLevel, int maxThreads, boolean useThreads, long cancelTimeout, Timer timer) {
      this.asyncContext = asyncContext;
      this.executor = executor;
      this.locator = locator;
      this.proposedLevel = proposedLevel;
      this.useThreads = useThreads;
      this.maxThreads = maxThreads;
      this.cancelTimeout = cancelTimeout;
      this.timer = timer;
      int currentLevel = asyncContext.getCurrentLevel();
      this.allListenerHandles = locator.getAllServiceHandles(RunLevelListener.class, new Annotation[0]);
      this.allProgressStartedHandles = locator.getAllServiceHandles(ProgressStartedListener.class, new Annotation[0]);
      this.allSorterHandles = locator.getAllServiceHandles(Sorter.class, new Annotation[0]);
      if (currentLevel == proposedLevel) {
         this.done = true;
      } else if (currentLevel < proposedLevel) {
         this.upAllTheWay = new UpAllTheWay(proposedLevel, this, this.allListenerHandles, this.allSorterHandles, maxThreads, useThreads, cancelTimeout);
      } else {
         this.downAllTheWay = new DownAllTheWay(proposedLevel, this, this.allListenerHandles);
      }

   }

   void go() {
      UpAllTheWay localUpAllTheWay;
      DownAllTheWay localDownAllTheWay;
      synchronized(this) {
         localUpAllTheWay = this.upAllTheWay;
         localDownAllTheWay = this.downAllTheWay;
      }

      if (localUpAllTheWay != null || localDownAllTheWay != null) {
         int currentLevel = this.asyncContext.getCurrentLevel();
         this.invokeOnProgressStarted(this, currentLevel, this.allProgressStartedHandles);
      }

      this.go(localUpAllTheWay, localDownAllTheWay);
   }

   private void go(UpAllTheWay localUpAllTheWay, DownAllTheWay localDownAllTheWay) {
      if (localUpAllTheWay != null) {
         localUpAllTheWay.go();
      } else if (localDownAllTheWay != null) {
         if (this.useThreads) {
            this.executor.execute(localDownAllTheWay);
         } else {
            localDownAllTheWay.run();
         }
      } else {
         this.asyncContext.jobDone();
      }

   }

   public boolean isUp() {
      synchronized(this) {
         return this.upAllTheWay != null;
      }
   }

   public boolean isDown() {
      synchronized(this) {
         return this.downAllTheWay != null;
      }
   }

   public boolean cancel(boolean mayInterruptIfRunning) {
      synchronized(this.asyncContext) {
         boolean var10000;
         synchronized(this) {
            if (this.done) {
               var10000 = false;
               return var10000;
            }

            if (!this.cancelled) {
               this.cancelled = true;
               if (this.upAllTheWay != null) {
                  this.upAllTheWay.cancel();
               } else if (this.downAllTheWay != null) {
                  this.downAllTheWay.cancel();
               }

               var10000 = true;
               return var10000;
            }

            var10000 = false;
         }

         return var10000;
      }
   }

   public boolean isCancelled() {
      synchronized(this) {
         return this.cancelled;
      }
   }

   public boolean isDone() {
      synchronized(this) {
         return this.done;
      }
   }

   public int getProposedLevel() {
      synchronized(this) {
         return this.proposedLevel;
      }
   }

   public int changeProposedLevel(int proposedLevel) {
      boolean needGo = false;
      int oldProposedVal;
      synchronized(this) {
         if (this.done) {
            throw new IllegalStateException("Cannot change the proposed level of a future that is already complete");
         }

         if (!this.inCallback) {
            throw new IllegalStateException("changeProposedLevel must only be called from inside a RunLevelListener callback method");
         }

         oldProposedVal = this.proposedLevel;
         int currentLevel = this.asyncContext.getCurrentLevel();
         this.proposedLevel = proposedLevel;
         if (this.upAllTheWay != null) {
            if (currentLevel <= proposedLevel) {
               this.upAllTheWay.setGoingTo(proposedLevel, false);
            } else {
               this.upAllTheWay.setGoingTo(currentLevel, true);
               this.upAllTheWay = null;
               this.downAllTheWay = new DownAllTheWay(proposedLevel, this, this.allListenerHandles);
               needGo = true;
            }
         } else {
            if (this.downAllTheWay == null) {
               throw new AssertionError("Can not determine previous job");
            }

            if (currentLevel >= proposedLevel) {
               this.downAllTheWay.setGoingTo(proposedLevel, false);
            } else {
               this.downAllTheWay.setGoingTo(currentLevel, true);
               this.downAllTheWay = null;
               this.upAllTheWay = new UpAllTheWay(proposedLevel, this, this.allListenerHandles, this.allSorterHandles, this.maxThreads, this.useThreads, this.cancelTimeout);
               needGo = true;
            }
         }
      }

      if (needGo) {
         this.go(this.upAllTheWay, this.downAllTheWay);
      }

      return oldProposedVal;
   }

   private void setInCallback(boolean inCallback) {
      synchronized(this) {
         this.inCallback = inCallback;
      }
   }

   public Object get() throws InterruptedException, ExecutionException {
      try {
         return this.get(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
      } catch (TimeoutException var2) {
         throw new AssertionError(var2);
      }
   }

   public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
      AllTheWay allTheWay = null;
      synchronized(this) {
         if (this.upAllTheWay != null) {
            allTheWay = this.upAllTheWay;
         } else if (this.downAllTheWay != null) {
            allTheWay = this.downAllTheWay;
         }
      }

      if (allTheWay == null) {
         return null;
      } else {
         Boolean result = null;

         while(true) {
            try {
               result = ((AllTheWay)allTheWay).waitForResult(timeout, unit);
               if (result != null) {
                  if (!result) {
                     throw new TimeoutException();
                  }

                  synchronized(this) {
                     this.done = true;
                  }

                  return null;
               }

               synchronized(this) {
                  if (this.upAllTheWay != null) {
                     allTheWay = this.upAllTheWay;
                  } else if (this.downAllTheWay != null) {
                     allTheWay = this.downAllTheWay;
                  }
               }
            } catch (MultiException var14) {
               synchronized(this) {
                  this.done = true;
               }

               throw new ExecutionException(var14);
            }
         }
      }
   }

   private void invokeOnProgress(ChangeableRunLevelFuture job, int level, List listeners) {
      this.setInCallback(true);

      try {
         Iterator var4 = listeners.iterator();

         while(var4.hasNext()) {
            ServiceHandle listener = (ServiceHandle)var4.next();

            try {
               RunLevelListener rll = (RunLevelListener)listener.getService();
               if (rll != null) {
                  rll.onProgress(job, level);
               }
            } catch (Throwable var10) {
            }
         }
      } finally {
         this.setInCallback(false);
      }

   }

   private void invokeOnProgressStarted(ChangeableRunLevelFuture job, int level, List listeners) {
      this.setInCallback(true);

      try {
         Iterator var4 = listeners.iterator();

         while(var4.hasNext()) {
            ServiceHandle listener = (ServiceHandle)var4.next();

            try {
               ProgressStartedListener psl = (ProgressStartedListener)listener.getService();
               if (psl != null) {
                  psl.onProgressStarting(job, level);
               }
            } catch (Throwable var10) {
            }
         }
      } finally {
         this.setInCallback(false);
      }

   }

   private static void invokeOnCancelled(CurrentTaskFuture job, int levelAchieved, List listeners) {
      Iterator var3 = listeners.iterator();

      while(var3.hasNext()) {
         ServiceHandle listener = (ServiceHandle)var3.next();

         try {
            RunLevelListener rll = (RunLevelListener)listener.getService();
            if (rll != null) {
               rll.onCancelled(new CurrentTaskFutureWrapper(job), levelAchieved);
            }
         } catch (Throwable var6) {
         }
      }

   }

   private static ErrorInformation invokeOnError(CurrentTaskFuture job, Throwable th, ErrorInformation.ErrorAction action, List listeners, Descriptor descriptor) {
      ErrorInformationImpl errorInfo = new ErrorInformationImpl(th, action, descriptor);
      Iterator var6 = listeners.iterator();

      while(var6.hasNext()) {
         ServiceHandle listener = (ServiceHandle)var6.next();

         try {
            RunLevelListener rll = (RunLevelListener)listener.getService();
            if (rll != null) {
               rll.onError(new CurrentTaskFutureWrapper(job), errorInfo);
            }
         } catch (Throwable var9) {
         }
      }

      return errorInfo;
   }

   static final boolean isWouldBlock(Throwable th) {
      return isACertainException(th, WouldBlockException.class);
   }

   private static final boolean isWasCancelled(Throwable th) {
      return isACertainException(th, WasCancelledException.class);
   }

   private static final boolean isACertainException(Throwable th, Class type) {
      for(Throwable cause = th; cause != null; cause = cause.getCause()) {
         if (cause instanceof MultiException) {
            MultiException me = (MultiException)cause;
            Iterator var4 = me.getErrors().iterator();

            while(var4.hasNext()) {
               Throwable innerMulti = (Throwable)var4.next();
               if (isACertainException(innerMulti, type)) {
                  return true;
               }
            }
         } else if (type.isAssignableFrom(cause.getClass())) {
            return true;
         }
      }

      return false;
   }

   public String toString() {
      return "CurrentTaskFuture(proposedLevel=" + this.proposedLevel + "," + System.identityHashCode(this) + ")";
   }

   private static class DownQueueRunner implements Runnable {
      private final Object queueLock;
      private final List queue;
      private final DownAllTheWay parent;
      private final ServiceLocator locator;
      private boolean caput;

      private DownQueueRunner(Object queueLock, List queue, DownAllTheWay parent, ServiceLocator locator) {
         this.queueLock = queue;
         this.queue = queue;
         this.parent = parent;
         this.locator = locator;
      }

      public void run() {
         while(true) {
            ActiveDescriptor job = null;
            synchronized(this.queueLock) {
               if (this.caput) {
                  return;
               }

               if (this.queue.isEmpty()) {
                  this.queueLock.notify();
                  return;
               }

               job = (ActiveDescriptor)this.queue.remove(0);
            }

            try {
               this.locator.getServiceHandle(job).destroy();
            } catch (Throwable var6) {
               Throwable th = var6;
               synchronized(this.queueLock) {
                  this.parent.lastError = th;
                  this.parent.lastErrorDescriptor = job;
                  this.queueLock.notify();
               }
            }
         }
      }

      // $FF: synthetic method
      DownQueueRunner(Object x0, List x1, DownAllTheWay x2, ServiceLocator x3, Object x4) {
         this(x0, x1, x2, x3);
      }
   }

   private static class QueueRunner implements Runnable {
      private final ServiceLocator locator;
      private final AsyncRunLevelContext asyncContext;
      private final Object queueLock;
      private final List queue;
      private final UpOneLevel parent;
      private final Object parentLock;
      private final int maxThreads;
      private ServiceHandle wouldHaveBlocked;
      private final HashSet alreadyTried;

      private QueueRunner(ServiceLocator locator, AsyncRunLevelContext asyncContext, Object queueLock, List queue, UpOneLevel parent, Object parentLock, int maxThreads) {
         this.alreadyTried = new HashSet();
         this.locator = locator;
         this.asyncContext = asyncContext;
         this.queueLock = queueLock;
         this.queue = queue;
         this.parent = parent;
         this.parentLock = parentLock;
         this.maxThreads = maxThreads;
      }

      public void run() {
         ServiceHandle runningHandle = null;

         while(true) {
            ServiceHandle job;
            boolean block;
            synchronized(this.queueLock) {
               if (runningHandle != null) {
                  this.parent.jobFinished(runningHandle);
               }

               if (this.wouldHaveBlocked != null) {
                  this.alreadyTried.add(this.wouldHaveBlocked.getActiveDescriptor());
                  this.queue.add(this.queue.size(), this.wouldHaveBlocked);
                  this.wouldHaveBlocked = null;
               }

               if (this.queue.isEmpty()) {
                  return;
               }

               int lcv;
               if (this.maxThreads <= 0) {
                  block = true;
               } else {
                  lcv = this.maxThreads - this.parent.getJobsRunning();
                  block = this.queue.size() <= lcv;
               }

               if (block) {
                  job = (ServiceHandle)this.queue.remove(0);
               } else {
                  job = null;

                  for(lcv = 0; lcv < this.queue.size(); ++lcv) {
                     ActiveDescriptor candidate = ((ServiceHandle)this.queue.get(lcv)).getActiveDescriptor();
                     if (!this.alreadyTried.contains(candidate)) {
                        job = (ServiceHandle)this.queue.remove(lcv);
                        break;
                     }
                  }

                  if (job == null) {
                     job = (ServiceHandle)this.queue.remove(0);
                     block = true;
                  }
               }

               this.parent.jobRunning(job);
               runningHandle = job;
            }

            this.oneJob(job, block);
         }
      }

      private boolean isWouldBlockRightNow(HashSet cycleChecker, ActiveDescriptor checkMe) {
         if (checkMe == null) {
            return false;
         } else if (cycleChecker.contains(checkMe)) {
            return false;
         } else {
            cycleChecker.add(checkMe);
            if (this.asyncContext.wouldBlockRightNow(checkMe)) {
               return true;
            } else {
               if (!checkMe.isReified()) {
                  checkMe = this.locator.reifyDescriptor(checkMe);
               }

               Iterator var3 = checkMe.getInjectees().iterator();

               while(var3.hasNext()) {
                  Injectee ip = (Injectee)var3.next();

                  ActiveDescriptor childService;
                  try {
                     childService = this.locator.getInjecteeDescriptor(ip);
                  } catch (MultiException var7) {
                     continue;
                  }

                  if (childService != null && childService.getScope().equals(RunLevel.class.getName()) && this.isWouldBlockRightNow(cycleChecker, childService)) {
                     if (this.asyncContext.wouldBlockRightNow(checkMe)) {
                        return true;
                     }

                     return true;
                  }
               }

               return false;
            }
         }
      }

      private void oneJob(ServiceHandle fService, boolean block) {
         fService.setServiceData(!block);
         boolean completed = true;

         try {
            boolean ok;
            synchronized(this.parentLock) {
               ok = !this.parent.cancelled && this.parent.accumulatedExceptions == null;
            }

            if (!block && this.isWouldBlockRightNow(new HashSet(), fService.getActiveDescriptor())) {
               this.wouldHaveBlocked = fService;
               completed = false;
               ok = false;
            }

            if (ok) {
               fService.getService();
            }
         } catch (MultiException var13) {
            if (!block && CurrentTaskFuture.isWouldBlock(var13)) {
               this.wouldHaveBlocked = fService;
               completed = false;
            } else if (!CurrentTaskFuture.isWasCancelled(var13)) {
               this.parent.fail(var13, fService.getActiveDescriptor());
            }
         } catch (Throwable var14) {
            this.parent.fail(var14, fService.getActiveDescriptor());
         } finally {
            fService.setServiceData((Object)null);
            if (completed) {
               this.parent.jobComplete();
            }

         }

      }

      // $FF: synthetic method
      QueueRunner(ServiceLocator x0, AsyncRunLevelContext x1, Object x2, List x3, UpOneLevel x4, Object x5, int x6, Object x7) {
         this(x0, x1, x2, x3, x4, x5, x6);
      }
   }

   private static class HardCancelDownTimer extends TimerTask {
      private final DownAllTheWay parent;
      private final List queue;
      private int lastQueueSize;

      private HardCancelDownTimer(DownAllTheWay parent, List queue) {
         this.parent = parent;
         this.queue = queue;
         this.lastQueueSize = queue.size();
      }

      public void run() {
         synchronized(this.queue) {
            int currentSize = this.queue.size();
            if (currentSize != 0) {
               if (currentSize == this.lastQueueSize) {
                  this.parent.downHardCancelled = true;
                  this.queue.notify();
               } else {
                  this.lastQueueSize = currentSize;
               }

            }
         }
      }

      // $FF: synthetic method
      HardCancelDownTimer(DownAllTheWay x0, List x1, Object x2) {
         this(x0, x1);
      }
   }

   private class DownAllTheWay implements Runnable, AllTheWay {
      private int goingTo;
      private CurrentTaskFuture future;
      private final List listeners;
      private int workingOn;
      private boolean cancelled = false;
      private boolean done = false;
      private boolean repurposed = false;
      private Throwable lastError = null;
      private ActiveDescriptor lastErrorDescriptor = null;
      private List queue = Collections.emptyList();
      private boolean downHardCancelled = false;
      private HardCancelDownTimer hardCancelDownTimer = null;

      public DownAllTheWay(int goingTo, CurrentTaskFuture future, List listeners) {
         this.goingTo = goingTo;
         this.future = future;
         this.listeners = listeners;
         if (future == null) {
            this.workingOn = CurrentTaskFuture.this.asyncContext.getCurrentLevel() + 1;
         } else {
            this.workingOn = CurrentTaskFuture.this.asyncContext.getCurrentLevel();
         }

      }

      private void cancel() {
         List localQueue;
         synchronized(this) {
            if (this.cancelled) {
               return;
            }

            this.cancelled = true;
            if (this.done) {
               return;
            }

            localQueue = this.queue;
         }

         synchronized(localQueue) {
            if (!localQueue.isEmpty()) {
               this.hardCancelDownTimer = new HardCancelDownTimer(this, localQueue);
               CurrentTaskFuture.this.timer.schedule(this.hardCancelDownTimer, CurrentTaskFuture.this.cancelTimeout, CurrentTaskFuture.this.cancelTimeout);
            }
         }
      }

      private void setGoingTo(int goingTo, boolean repurposed) {
         synchronized(this) {
            this.goingTo = goingTo;
            if (repurposed) {
               this.repurposed = true;
            }

         }
      }

      private int getGoingTo() {
         synchronized(this) {
            return this.goingTo;
         }
      }

      public void run() {
         while(this.workingOn > this.getGoingTo()) {
            boolean runOnCancelled;
            boolean localCancelled;
            synchronized(this) {
               localCancelled = this.cancelled;
               runOnCancelled = this.cancelled && this.future != null;
            }

            if (runOnCancelled) {
               CurrentTaskFuture.invokeOnCancelled(this.future, this.workingOn, this.listeners);
            }

            synchronized(this) {
               if (localCancelled) {
                  CurrentTaskFuture.this.asyncContext.jobDone();
                  this.done = true;
                  this.notifyAll();
                  return;
               }
            }

            int proceedingTo = this.workingOn - 1;
            CurrentTaskFuture.this.asyncContext.setCurrentLevel(proceedingTo);
            List localQueue = CurrentTaskFuture.this.asyncContext.getOrderedListOfServicesAtLevel(this.workingOn);
            synchronized(this) {
               this.queue = localQueue;
            }

            ErrorInformation errorInfo = null;
            synchronized(this.queue) {
               label131:
               do {
                  DownQueueRunner currentRunner = new DownQueueRunner(this.queue, this.queue, this, CurrentTaskFuture.this.locator);
                  CurrentTaskFuture.this.executor.execute(currentRunner);
                  this.lastError = null;

                  while(true) {
                     while(this.queue.isEmpty() || this.lastError != null || this.downHardCancelled) {
                        if (this.downHardCancelled) {
                           currentRunner.caput = true;
                        }

                        if (this.lastError != null && this.future != null) {
                           errorInfo = CurrentTaskFuture.invokeOnError(this.future, this.lastError, ErrorInformation.ErrorAction.IGNORE, this.listeners, this.lastErrorDescriptor);
                        }

                        this.lastError = null;
                        this.lastErrorDescriptor = null;
                        if (this.queue.isEmpty() || this.downHardCancelled) {
                           this.downHardCancelled = false;
                           continue label131;
                        }
                     }

                     try {
                        this.queue.wait();
                     } catch (InterruptedException var15) {
                        throw new RuntimeException(var15);
                     }
                  }
               } while(!this.queue.isEmpty());

               if (this.hardCancelDownTimer != null) {
                  this.hardCancelDownTimer.cancel();
               }
            }

            synchronized(this) {
               this.queue = Collections.emptyList();
            }

            if (errorInfo != null && ErrorInformation.ErrorAction.GO_TO_NEXT_LOWER_LEVEL_AND_STOP.equals(errorInfo.getAction())) {
               synchronized(this) {
                  this.goingTo = this.workingOn;
               }
            }

            --this.workingOn;
            if (this.future != null) {
               CurrentTaskFuture.this.invokeOnProgress(this.future, proceedingTo, this.listeners);
            }
         }

         if (this.future != null) {
            synchronized(this) {
               if (!this.repurposed) {
                  CurrentTaskFuture.this.asyncContext.jobDone();
                  this.done = true;
               }

               this.notifyAll();
            }
         }
      }

      public Boolean waitForResult(long timeout, TimeUnit unit) throws InterruptedException, MultiException {
         long totalWaitTimeMillis = TimeUnit.MILLISECONDS.convert(timeout, unit);
         synchronized(this) {
            while(totalWaitTimeMillis > 0L && !this.done && !this.repurposed) {
               long startTime = System.currentTimeMillis();
               this.wait(totalWaitTimeMillis);
               long elapsedTime = System.currentTimeMillis() - startTime;
               totalWaitTimeMillis -= elapsedTime;
            }

            return this.repurposed ? null : this.done;
         }
      }
   }

   private static class CancelTimer extends TimerTask {
      private final UpOneLevel parent;

      private CancelTimer(UpOneLevel parent) {
         this.parent = parent;
      }

      public void run() {
         this.parent.hardCancel();
      }

      // $FF: synthetic method
      CancelTimer(UpOneLevel x0, Object x1) {
         this(x0);
      }
   }

   private class UpOneLevel implements Runnable {
      private final Object lock;
      private final Object queueLock;
      private final int upToThisLevel;
      private final CurrentTaskFuture currentTaskFuture;
      private final List listeners;
      private final List sorters;
      private final UpAllTheWay master;
      private final int maxThreads;
      private final long cancelTimeout;
      private int numJobs;
      private int completedJobs;
      private MultiException accumulatedExceptions;
      private boolean cancelled;
      private CancelTimer hardCanceller;
      private int numJobsRunning;
      private boolean hardCancelled;
      private final HashSet outstandingHandles;

      private UpOneLevel(int paramUpToThisLevel, UpAllTheWay master, CurrentTaskFuture currentTaskFuture, List listeners, List sorters, int maxThreads, long cancelTimeout) {
         this.lock = new Object();
         this.queueLock = new Object();
         this.cancelled = false;
         this.numJobsRunning = 0;
         this.hardCancelled = false;
         this.outstandingHandles = new HashSet();
         this.upToThisLevel = paramUpToThisLevel;
         this.master = master;
         this.maxThreads = maxThreads;
         this.currentTaskFuture = currentTaskFuture;
         this.listeners = listeners;
         this.sorters = sorters;
         this.cancelTimeout = cancelTimeout;
      }

      private void cancel() {
         synchronized(this.lock) {
            this.cancelled = true;
            this.hardCanceller = new CancelTimer(this);
            CurrentTaskFuture.this.timer.schedule(this.hardCanceller, this.cancelTimeout);
         }
      }

      private void hardCancel() {
         synchronized(CurrentTaskFuture.this.asyncContext) {
            synchronized(this.lock) {
               this.hardCancelled = true;
            }

            HashSet poisonMe;
            synchronized(this.queueLock) {
               poisonMe = new HashSet(this.outstandingHandles);
               this.outstandingHandles.clear();
            }

            Iterator var3 = poisonMe.iterator();

            while(true) {
               if (!var3.hasNext()) {
                  break;
               }

               ServiceHandle handle = (ServiceHandle)var3.next();
               CurrentTaskFuture.this.asyncContext.hardCancelOne(handle.getActiveDescriptor());
            }
         }

         this.master.currentJobComplete((MultiException)null);
      }

      private void jobRunning(ServiceHandle handle) {
         ++this.numJobsRunning;
         this.outstandingHandles.add(handle);
      }

      private void jobFinished(ServiceHandle handle) {
         this.outstandingHandles.remove(handle);
         --this.numJobsRunning;
      }

      private int getJobsRunning() {
         return this.numJobsRunning;
      }

      private List applySorters(List jobs) {
         List retVal = jobs;
         Iterator var3 = this.sorters.iterator();

         while(var3.hasNext()) {
            ServiceHandle sorterHandle = (ServiceHandle)var3.next();
            Sorter sorter = (Sorter)sorterHandle.getService();
            if (sorter != null) {
               List sortedList = sorter.sort(retVal);
               if (sortedList != null) {
                  retVal = sortedList;
               }
            }
         }

         return retVal;
      }

      public void run() {
         Object jobsLock = new Object();
         List jobs = CurrentTaskFuture.this.locator.getAllServiceHandles(new IndexedFilter() {
            public boolean matches(Descriptor d) {
               return UpOneLevel.this.upToThisLevel == Utilities.getRunLevelValue(CurrentTaskFuture.this.locator, d);
            }

            public String getAdvertisedContract() {
               return RunLevel.class.getName();
            }

            public String getName() {
               return null;
            }
         });
         jobs = this.applySorters(jobs);
         this.numJobs = jobs.size();
         if (this.numJobs <= 0) {
            this.jobComplete();
         } else {
            int runnersToCreate = (this.numJobs < this.maxThreads ? this.numJobs : this.maxThreads) - 1;
            if (!CurrentTaskFuture.this.useThreads) {
               runnersToCreate = 0;
            }

            for(int lcv = 0; lcv < runnersToCreate; ++lcv) {
               QueueRunner runner = new QueueRunner(CurrentTaskFuture.this.locator, CurrentTaskFuture.this.asyncContext, jobsLock, jobs, this, this.lock, this.maxThreads);
               CurrentTaskFuture.this.executor.execute(runner);
            }

            QueueRunner myRunner = new QueueRunner(CurrentTaskFuture.this.locator, CurrentTaskFuture.this.asyncContext, jobsLock, jobs, this, this.lock, this.maxThreads);
            myRunner.run();
         }
      }

      private void fail(Throwable th, Descriptor descriptor) {
         synchronized(this.lock) {
            if (!this.hardCancelled) {
               ErrorInformation info = CurrentTaskFuture.invokeOnError(this.currentTaskFuture, th, ErrorInformation.ErrorAction.GO_TO_NEXT_LOWER_LEVEL_AND_STOP, this.listeners, descriptor);
               if (!ErrorInformation.ErrorAction.IGNORE.equals(info.getAction())) {
                  if (this.accumulatedExceptions == null) {
                     this.accumulatedExceptions = new MultiException();
                  }

                  this.accumulatedExceptions.addError(th);
               }
            }
         }
      }

      private void jobComplete() {
         boolean complete = false;
         synchronized(this.lock) {
            if (this.hardCancelled) {
               return;
            }

            ++this.completedJobs;
            if (this.completedJobs >= this.numJobs) {
               complete = true;
               if (this.hardCanceller != null) {
                  this.hardCanceller.cancel();
                  this.hardCanceller = null;
               }
            }
         }

         if (complete) {
            this.master.currentJobComplete(this.accumulatedExceptions);
         }

      }

      // $FF: synthetic method
      UpOneLevel(int x1, UpAllTheWay x2, CurrentTaskFuture x3, List x4, List x5, int x6, long x7, Object x8) {
         this(x1, x2, x3, x4, x5, x6, x7);
      }
   }

   private class UpAllTheWay implements AllTheWay {
      private final Object lock;
      private int goingTo;
      private final int maxThreads;
      private final boolean useThreads;
      private final CurrentTaskFuture future;
      private final List listeners;
      private final List sorters;
      private final long cancelTimeout;
      private int workingOn;
      private UpOneLevel currentJob;
      private boolean cancelled;
      private boolean done;
      private boolean repurposed;
      private MultiException exception;

      private UpAllTheWay(int goingTo, CurrentTaskFuture future, List listeners, List sorters, int maxThreads, boolean useThreads, long cancelTimeout) {
         this.lock = new Object();
         this.cancelled = false;
         this.done = false;
         this.repurposed = false;
         this.exception = null;
         this.goingTo = goingTo;
         this.future = future;
         this.listeners = listeners;
         this.maxThreads = maxThreads;
         this.useThreads = useThreads;
         this.sorters = sorters;
         this.cancelTimeout = cancelTimeout;
         this.workingOn = CurrentTaskFuture.this.asyncContext.getCurrentLevel();
      }

      private void cancel() {
         synchronized(this.lock) {
            this.cancelled = true;
            CurrentTaskFuture.this.asyncContext.levelCancelled();
            this.currentJob.cancel();
         }
      }

      public Boolean waitForResult(long timeout, TimeUnit unit) throws InterruptedException, MultiException {
         long totalWaitTimeMillis = TimeUnit.MILLISECONDS.convert(timeout, unit);
         synchronized(this.lock) {
            while(totalWaitTimeMillis > 0L && !this.done && !this.repurposed) {
               long startTime = System.currentTimeMillis();
               this.lock.wait(totalWaitTimeMillis);
               long elapsedTime = System.currentTimeMillis() - startTime;
               totalWaitTimeMillis -= elapsedTime;
            }

            if (this.repurposed) {
               return null;
            } else if (this.done && this.exception != null) {
               throw this.exception;
            } else {
               return this.done;
            }
         }
      }

      private void setGoingTo(int goingTo, boolean repurposed) {
         synchronized(this.lock) {
            this.goingTo = goingTo;
            if (repurposed) {
               this.repurposed = true;
            }

         }
      }

      private void go() {
         if (this.useThreads) {
            synchronized(this.lock) {
               ++this.workingOn;
               if (this.workingOn > this.goingTo) {
                  if (!this.repurposed) {
                     CurrentTaskFuture.this.asyncContext.jobDone();
                     this.done = true;
                  }

                  this.lock.notifyAll();
               } else {
                  this.currentJob = CurrentTaskFuture.this.new UpOneLevel(this.workingOn, this, this.future, this.listeners, this.sorters, this.maxThreads, this.cancelTimeout);
                  CurrentTaskFuture.this.executor.execute(this.currentJob);
               }
            }
         } else {
            ++this.workingOn;

            while(this.workingOn <= this.goingTo) {
               synchronized(this.lock) {
                  if (this.done) {
                     break;
                  }

                  this.currentJob = CurrentTaskFuture.this.new UpOneLevel(this.workingOn, this, this.future, this.listeners, this.sorters, 0, this.cancelTimeout);
               }

               this.currentJob.run();
               ++this.workingOn;
            }

            synchronized(this.lock) {
               if (!this.done) {
                  if (!this.repurposed) {
                     CurrentTaskFuture.this.asyncContext.jobDone();
                     this.done = true;
                  }

                  this.lock.notifyAll();
               }
            }
         }
      }

      private void currentJobComplete(MultiException accumulatedExceptions) {
         CurrentTaskFuture.this.asyncContext.clearErrors();
         DownAllTheWay downer;
         if (accumulatedExceptions != null) {
            downer = CurrentTaskFuture.this.new DownAllTheWay(this.workingOn - 1, (CurrentTaskFuture)null, (List)null);
            downer.run();
            synchronized(this.lock) {
               this.done = true;
               this.exception = accumulatedExceptions;
               this.lock.notifyAll();
               CurrentTaskFuture.this.asyncContext.jobDone();
            }
         } else {
            downer = null;
            synchronized(this.lock) {
               if (this.cancelled) {
                  downer = CurrentTaskFuture.this.new DownAllTheWay(this.workingOn - 1, (CurrentTaskFuture)null, (List)null);
               }
            }

            if (downer != null) {
               downer.run();
               CurrentTaskFuture.invokeOnCancelled(this.future, this.workingOn - 1, this.listeners);
               synchronized(this.lock) {
                  this.done = true;
                  this.lock.notifyAll();
                  CurrentTaskFuture.this.asyncContext.jobDone();
               }
            } else {
               CurrentTaskFuture.this.asyncContext.setCurrentLevel(this.workingOn);
               CurrentTaskFuture.this.invokeOnProgress(this.future, this.workingOn, this.listeners);
               if (this.useThreads) {
                  this.go();
               }

            }
         }
      }

      // $FF: synthetic method
      UpAllTheWay(int x1, CurrentTaskFuture x2, List x3, List x4, int x5, boolean x6, long x7, Object x8) {
         this(x1, x2, x3, x4, x5, x6, x7);
      }
   }

   private interface AllTheWay {
      Boolean waitForResult(long var1, TimeUnit var3) throws InterruptedException, MultiException;
   }
}
