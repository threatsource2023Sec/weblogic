package org.glassfish.hk2.runlevel.internal;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.DescriptorVisibility;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.Visibility;
import org.glassfish.hk2.runlevel.CurrentlyRunningException;
import org.glassfish.hk2.runlevel.RunLevelController;
import org.glassfish.hk2.runlevel.RunLevelFuture;
import org.glassfish.hk2.runlevel.utilities.Utilities;
import org.jvnet.hk2.annotations.Service;

@Service
@Visibility(DescriptorVisibility.LOCAL)
public class AsyncRunLevelContext {
   private static final String DEBUG_CONTEXT_PROPERTY = "org.jvnet.hk2.properties.debug.runlevel.context";
   private static final boolean DEBUG_CONTEXT = (Boolean)AccessController.doPrivileged(new PrivilegedAction() {
      public Boolean run() {
         return Boolean.parseBoolean(System.getProperty("org.jvnet.hk2.properties.debug.runlevel.context", "false"));
      }
   });
   private static final Logger logger = Logger.getLogger(AsyncRunLevelContext.class.getName());
   private static final Timer timer = new Timer(true);
   private static final ThreadFactory THREAD_FACTORY = new RunLevelThreadFactory();
   private final org.glassfish.hk2.utilities.reflection.Logger hk2Logger = org.glassfish.hk2.utilities.reflection.Logger.getLogger();
   private int currentLevel = -2;
   private CurrentTaskFutureWrapper currentTask = null;
   private static final Executor DEFAULT_EXECUTOR;
   private final Map backingMap = new HashMap();
   private final Map levelErrorMap = new HashMap();
   private boolean wasCancelled = false;
   private final HashMap creatingDescriptors = new HashMap();
   private final HashSet hardCancelledDescriptors = new HashSet();
   private final LinkedList orderedCreationList = new LinkedList();
   private Executor executor;
   private final ServiceLocator locator;
   private int maxThreads;
   private RunLevelController.ThreadingPolicy policy;
   private long cancelTimeout;
   private Integer modeOverride;

   @Inject
   private AsyncRunLevelContext(ServiceLocator locator) {
      this.executor = DEFAULT_EXECUTOR;
      this.maxThreads = Integer.MAX_VALUE;
      this.policy = RunLevelController.ThreadingPolicy.FULLY_THREADED;
      this.cancelTimeout = 5000L;
      this.modeOverride = null;
      this.locator = locator;
   }

   public Object findOrCreate(ActiveDescriptor activeDescriptor, ServiceHandle root) {
      String oneLineDescriptor = null;
      if (DEBUG_CONTEXT) {
         oneLineDescriptor = oneLineDescriptor(activeDescriptor);
         this.hk2Logger.debug("AsyncRunLevelController findOrCreate for " + oneLineDescriptor + " and root " + oneLineRoot(root));
      }

      boolean throwWouldBlock;
      Object retVal;
      if (root == null) {
         throwWouldBlock = false;
      } else {
         retVal = root.getServiceData();
         if (retVal == null) {
            throwWouldBlock = false;
         } else {
            throwWouldBlock = (Boolean)retVal;
         }
      }

      retVal = null;
      long tid = -1L;
      int localCurrentLevel;
      Integer localModeOverride;
      synchronized(this) {
         localModeOverride = this.modeOverride;
         retVal = this.backingMap.get(activeDescriptor);
         if (retVal != null) {
            if (DEBUG_CONTEXT) {
               this.hk2Logger.debug("AsyncRunLevelController found " + oneLineDescriptor);
            }

            return retVal;
         }

         Throwable previousException = (Throwable)this.levelErrorMap.get(activeDescriptor);
         if (previousException != null) {
            if (DEBUG_CONTEXT) {
               this.hk2Logger.debug("AsyncRunLevelController tried once, it failed, rethrowing " + oneLineDescriptor, previousException);
            }

            if (previousException instanceof RuntimeException) {
               throw (RuntimeException)previousException;
            }

            throw new RuntimeException(previousException);
         }

         if (this.hardCancelledDescriptors.contains(activeDescriptor)) {
            if (DEBUG_CONTEXT) {
               this.hk2Logger.debug("AsyncRunLevelController hard cancelled " + oneLineDescriptor);
            }

            throw new MultiException(new WasCancelledException(activeDescriptor), false);
         }

         while(this.creatingDescriptors.containsKey(activeDescriptor)) {
            if (DEBUG_CONTEXT) {
               this.hk2Logger.debug("AsyncRunLevelController already being created " + oneLineDescriptor);
            }

            long holdingLock = (Long)this.creatingDescriptors.get(activeDescriptor);
            if (holdingLock == Thread.currentThread().getId()) {
               if (DEBUG_CONTEXT) {
                  this.hk2Logger.debug("AsyncRunLevelController circular dependency " + oneLineDescriptor);
               }

               throw new MultiException(new IllegalStateException("Circular dependency involving " + activeDescriptor.getImplementation() + " was found.  Full descriptor is " + activeDescriptor));
            }

            if (throwWouldBlock) {
               if (DEBUG_CONTEXT) {
                  this.hk2Logger.debug("AsyncRunLevelController would block optimization " + oneLineDescriptor);
               }

               throw new MultiException(new WouldBlockException(activeDescriptor), false);
            }

            try {
               this.wait();
            } catch (InterruptedException var29) {
               throw new MultiException(var29);
            }
         }

         if (DEBUG_CONTEXT) {
            this.hk2Logger.debug("AsyncRunLevelController finished creating wait for " + oneLineDescriptor);
         }

         retVal = this.backingMap.get(activeDescriptor);
         if (retVal != null) {
            if (DEBUG_CONTEXT) {
               this.hk2Logger.debug("AsyncRunLevelController second chance found " + oneLineDescriptor);
            }

            return retVal;
         }

         previousException = (Throwable)this.levelErrorMap.get(activeDescriptor);
         if (previousException != null) {
            if (DEBUG_CONTEXT) {
               this.hk2Logger.debug("AsyncRunLevelController service already threw " + oneLineDescriptor);
            }

            if (previousException instanceof RuntimeException) {
               throw (RuntimeException)previousException;
            }

            throw new RuntimeException(previousException);
         }

         if (this.hardCancelledDescriptors.contains(activeDescriptor)) {
            if (DEBUG_CONTEXT) {
               this.hk2Logger.debug("AsyncRunLevelController second chance hard cancel " + oneLineDescriptor);
            }

            throw new MultiException(new WasCancelledException(activeDescriptor), false);
         }

         tid = Thread.currentThread().getId();
         if (DEBUG_CONTEXT) {
            this.hk2Logger.debug("AsyncRunLevelController am creating " + oneLineDescriptor + " in thread " + tid);
         }

         this.creatingDescriptors.put(activeDescriptor, tid);
         localCurrentLevel = this.currentLevel;
         if (this.currentTask != null && this.currentTask.isUp()) {
            ++localCurrentLevel;
            if (localCurrentLevel > this.currentTask.getProposedLevel()) {
               localCurrentLevel = this.currentTask.getProposedLevel();
            }
         }
      }

      Throwable error = null;
      boolean var28 = false;

      Object var36;
      try {
         var28 = true;
         int mode = Utilities.getRunLevelMode(this.locator, activeDescriptor, localModeOverride);
         if (mode == 1) {
            this.validate(activeDescriptor, localCurrentLevel);
         }

         if (DEBUG_CONTEXT) {
            this.hk2Logger.debug("AsyncRunLevelController prior to actual create " + oneLineDescriptor + " in thread " + tid);
         }

         retVal = activeDescriptor.create(root);
         if (DEBUG_CONTEXT) {
            this.hk2Logger.debug("AsyncRunLevelController after actual create " + oneLineDescriptor + " in thread " + tid);
         }

         var36 = retVal;
         var28 = false;
      } catch (Throwable var32) {
         if (DEBUG_CONTEXT) {
            this.hk2Logger.debug("AsyncRunLevelController got exception for " + oneLineDescriptor + " in thread " + tid, var32);
         }

         if (var32 instanceof MultiException) {
            if (!CurrentTaskFuture.isWouldBlock((MultiException)var32)) {
               error = var32;
            }
         } else {
            error = var32;
         }

         if (var32 instanceof RuntimeException) {
            throw (RuntimeException)var32;
         }

         throw new RuntimeException(var32);
      } finally {
         if (var28) {
            synchronized(this) {
               boolean hardCancelled = this.hardCancelledDescriptors.remove(activeDescriptor);
               if (retVal != null) {
                  if (!hardCancelled) {
                     this.backingMap.put(activeDescriptor, retVal);
                     this.orderedCreationList.addFirst(activeDescriptor);
                  }

                  if (this.wasCancelled || hardCancelled) {
                     if (DEBUG_CONTEXT) {
                        this.hk2Logger.debug("AsyncRunLevelController cancellation race failed " + oneLineDescriptor + " in thread " + tid);
                     }

                     MultiException cancelledException = new MultiException(new WasCancelledException(activeDescriptor), false);
                     if (!hardCancelled) {
                        this.levelErrorMap.put(activeDescriptor, cancelledException);
                     }

                     this.creatingDescriptors.remove(activeDescriptor);
                     this.notifyAll();
                     if (DEBUG_CONTEXT) {
                        this.hk2Logger.debug("AsyncRunLevelController other threads notified cancellation path for " + oneLineDescriptor + " in thread " + tid);
                     }

                     throw cancelledException;
                  }
               } else if (error != null && !hardCancelled) {
                  this.levelErrorMap.put(activeDescriptor, error);
               }

               this.creatingDescriptors.remove(activeDescriptor);
               this.notifyAll();
               if (DEBUG_CONTEXT) {
                  this.hk2Logger.debug("AsyncRunLevelController other threads notified for " + oneLineDescriptor + " in thread " + tid);
               }

            }
         }
      }

      synchronized(this) {
         boolean hardCancelled = this.hardCancelledDescriptors.remove(activeDescriptor);
         if (retVal != null) {
            if (!hardCancelled) {
               this.backingMap.put(activeDescriptor, retVal);
               this.orderedCreationList.addFirst(activeDescriptor);
            }

            if (this.wasCancelled || hardCancelled) {
               if (DEBUG_CONTEXT) {
                  this.hk2Logger.debug("AsyncRunLevelController cancellation race failed " + oneLineDescriptor + " in thread " + tid);
               }

               MultiException cancelledException = new MultiException(new WasCancelledException(activeDescriptor), false);
               if (!hardCancelled) {
                  this.levelErrorMap.put(activeDescriptor, cancelledException);
               }

               this.creatingDescriptors.remove(activeDescriptor);
               this.notifyAll();
               if (DEBUG_CONTEXT) {
                  this.hk2Logger.debug("AsyncRunLevelController other threads notified cancellation path for " + oneLineDescriptor + " in thread " + tid);
               }

               throw cancelledException;
            }
         } else if (error != null && !hardCancelled) {
            this.levelErrorMap.put(activeDescriptor, error);
         }

         this.creatingDescriptors.remove(activeDescriptor);
         this.notifyAll();
         if (DEBUG_CONTEXT) {
            this.hk2Logger.debug("AsyncRunLevelController other threads notified for " + oneLineDescriptor + " in thread " + tid);
         }

         return var36;
      }
   }

   public boolean containsKey(ActiveDescriptor descriptor) {
      synchronized(this) {
         return this.backingMap.containsKey(descriptor);
      }
   }

   boolean wouldBlockRightNow(ActiveDescriptor desc) {
      synchronized(this) {
         return this.creatingDescriptors.containsKey(desc);
      }
   }

   void hardCancelOne(ActiveDescriptor descriptor) {
      if (this.creatingDescriptors.containsKey(descriptor)) {
         this.hardCancelledDescriptors.add(descriptor);
      }

   }

   public void destroyOne(ActiveDescriptor descriptor) {
      Object retVal = null;
      synchronized(this) {
         retVal = this.backingMap.remove(descriptor);
         if (retVal == null) {
            return;
         }
      }

      descriptor.dispose(retVal);
   }

   private void validate(ActiveDescriptor descriptor, int currentLevel) throws IllegalStateException {
      Integer runLevel = Utilities.getRunLevelValue(this.locator, descriptor);
      if (runLevel > currentLevel) {
         throw new IllegalStateException("Service " + descriptor.getImplementation() + " was started at level " + currentLevel + " but it has a run level of " + runLevel + ".  The full descriptor is " + descriptor);
      }
   }

   synchronized int getCurrentLevel() {
      return this.currentLevel;
   }

   synchronized void levelCancelled() {
      this.wasCancelled = true;
   }

   synchronized void setCurrentLevel(int currentLevel) {
      this.currentLevel = currentLevel;
   }

   synchronized void setPolicy(RunLevelController.ThreadingPolicy policy) {
      this.policy = policy;
   }

   synchronized void setExecutor(Executor executor) {
      if (executor == null) {
         this.executor = DEFAULT_EXECUTOR;
      } else {
         this.executor = executor;
      }

   }

   synchronized Executor getExecutor() {
      return this.executor;
   }

   synchronized RunLevelController.ThreadingPolicy getPolicy() {
      return this.policy;
   }

   List getOrderedListOfServicesAtLevel(int level) {
      synchronized(this) {
         LinkedList retVal = new LinkedList();

         while(!this.orderedCreationList.isEmpty()) {
            ActiveDescriptor zero = (ActiveDescriptor)this.orderedCreationList.get(0);
            int zeroLevel = Utilities.getRunLevelValue(this.locator, zero);
            if (zeroLevel < level) {
               return retVal;
            }

            retVal.add(this.orderedCreationList.remove(0));
         }

         return retVal;
      }
   }

   public RunLevelFuture proceedTo(int level) throws CurrentlyRunningException {
      CurrentTaskFutureWrapper localTask;
      synchronized(this) {
         boolean fullyThreaded = this.policy.equals(RunLevelController.ThreadingPolicy.FULLY_THREADED);
         if (this.currentTask != null) {
            throw new CurrentlyRunningException(this.currentTask);
         }

         this.currentTask = new CurrentTaskFutureWrapper(new CurrentTaskFuture(this, this.executor, this.locator, level, this.maxThreads, fullyThreaded, this.cancelTimeout, timer));
         localTask = this.currentTask;
      }

      localTask.getDelegate().go();
      return localTask;
   }

   synchronized void jobDone() {
      this.currentTask = null;
   }

   public synchronized RunLevelFuture getCurrentFuture() {
      return this.currentTask;
   }

   synchronized void setMaximumThreads(int maximum) {
      if (maximum < 1) {
         this.maxThreads = 1;
      } else {
         this.maxThreads = maximum;
      }

   }

   synchronized int getMaximumThreads() {
      return this.maxThreads;
   }

   synchronized void clearErrors() {
      this.levelErrorMap.clear();
      this.wasCancelled = false;
   }

   synchronized void setCancelTimeout(long cancelTimeout) {
      this.cancelTimeout = cancelTimeout;
   }

   synchronized long getCancelTimeout() {
      return this.cancelTimeout;
   }

   synchronized Integer getModeOverride() {
      return this.modeOverride;
   }

   synchronized void setModeOverride(Integer modeOverride) {
      this.modeOverride = modeOverride;
   }

   private static String oneLineDescriptor(ActiveDescriptor descriptor) {
      return descriptor == null ? "null-descriptor" : descriptor.getImplementation() + (descriptor.getName() != null ? "/" + descriptor.getName() : "") + "(" + descriptor.getServiceId() + "," + descriptor.getLocatorId() + "," + System.identityHashCode(descriptor) + ")";
   }

   private static String oneLineRoot(ServiceHandle root) {
      if (root == null) {
         return "null-root";
      } else {
         root.getActiveDescriptor();
         return "root(" + oneLineDescriptor(root.getActiveDescriptor()) + "," + System.identityHashCode(root) + ")";
      }
   }

   static {
      DEFAULT_EXECUTOR = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue(true), THREAD_FACTORY);
   }

   private static class RunLevelThreadFactory implements ThreadFactory {
      private RunLevelThreadFactory() {
      }

      public Thread newThread(Runnable runnable) {
         Thread activeThread = new RunLevelControllerThread(runnable);
         AsyncRunLevelContext.logger.log(Level.FINE, "new thread: {0}", activeThread);
         return activeThread;
      }

      // $FF: synthetic method
      RunLevelThreadFactory(Object x0) {
         this();
      }
   }

   private static class RunLevelControllerThread extends Thread {
      private RunLevelControllerThread(Runnable r) {
         super(r);
         this.setDaemon(true);
         this.setName(this.getClass().getSimpleName() + "-" + System.currentTimeMillis());
      }

      // $FF: synthetic method
      RunLevelControllerThread(Runnable x0, Object x1) {
         this(x0);
      }
   }
}
