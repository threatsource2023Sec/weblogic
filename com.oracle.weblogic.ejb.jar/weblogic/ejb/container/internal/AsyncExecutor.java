package weblogic.ejb.container.internal;

import java.rmi.RemoteException;
import java.security.AccessController;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.interfaces.Invokable;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityManager;

public final class AsyncExecutor implements Runnable, Future {
   private static final DebugLogger DEBUG_LOGGER;
   private static final int READY = 1;
   private static final int CANCELLED = 2;
   private static final int RUNNING = 4;
   private static final int RAN = 8;
   private static final int UNDEPLOYED = 16;
   private final long id;
   private final Lock stateLock = new ReentrantLock();
   private final CountDownLatch isDone;
   private final AtomicBoolean cancelIfRunning;
   private Invoker invoker;
   private volatile Future result = null;
   private volatile ExecutionException resultedException = null;
   private volatile int state = 1;

   AsyncExecutor(long id, Invokable inv, Object bo, InvocationWrapper wrap, Object[] args, int idx) {
      assert bo instanceof BaseLocalObject || bo instanceof BaseRemoteObject;

      this.id = id;
      this.invoker = new Invoker(inv, bo, wrap, args, idx);
      this.cancelIfRunning = this.invoker.getCancelIfRunning();
      this.isDone = new CountDownLatch(1);
   }

   public void run() {
      this.stateLock.lock();

      label151: {
         try {
            if (this.state == 1) {
               this.state = 4;
               break label151;
            }

            if (this.state != 2) {
               throw new AssertionError("Error starting the task. Unknown state : " + this.state);
            }
         } finally {
            this.stateLock.unlock();
         }

         return;
      }

      if (DEBUG_LOGGER.isDebugEnabled()) {
         this.debug("Beginning to execute.");
      }

      try {
         this.result = (Future)this.invoker.invoke();
      } catch (Throwable var10) {
         if (this.invoker.isOnewayCall()) {
            EJBLogger.logExceptionDuringAsyncInvocationExecution(this.invoker.invocationDetail(), var10);
         } else {
            this.resultedException = new ExecutionException(var10);
         }
      }

      if (DEBUG_LOGGER.isDebugEnabled()) {
         this.debug("Execution completed.");
      }

      this.stateLock.lock();

      try {
         this.state = 8;
         this.isDone.countDown();
         this.invoker = null;
      } finally {
         this.stateLock.unlock();
      }

   }

   public boolean isCancelled() {
      return (this.state & 2) != 0;
   }

   public boolean isDone() {
      return (this.state & 10) != 0;
   }

   public boolean cancel(boolean mayInterruptIfRunning) {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         this.debug("User has requested cancel.");
      }

      this.stateLock.lock();

      boolean var2;
      try {
         if (this.isDone()) {
            var2 = false;
            return var2;
         }

         if ((this.state & 1) != 0) {
            this.state = 2;
            this.isDone.countDown();
            this.invoker = null;
            if (DEBUG_LOGGER.isDebugEnabled()) {
               this.debug("Cancellation successful.");
            }
         }

         if (this.cancelIfRunning != null) {
            this.cancelIfRunning.set(mayInterruptIfRunning);
         }

         var2 = (this.state & 2) != 0;
      } finally {
         this.stateLock.unlock();
      }

      return var2;
   }

   public Object get() throws InterruptedException, ExecutionException {
      this.isDone.await();
      return this.getInternal();
   }

   public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
      if (!this.isDone.await(timeout, unit)) {
         throw new TimeoutException();
      } else {
         return this.getInternal();
      }
   }

   private Object getInternal() throws InterruptedException, ExecutionException {
      if (this.isCancelled()) {
         throw new CancellationException();
      } else if (this.resultedException != null) {
         throw this.resultedException;
      } else {
         return this.result == null ? null : this.result.get();
      }
   }

   private void debug(String s) {
      DEBUG_LOGGER.debug("[AsyncExecutor] [Task - " + this.id + "] " + s);
   }

   static {
      DEBUG_LOGGER = EJBDebugService.invokeLogger;
   }

   private static final class Invoker {
      private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      private final Invokable inv;
      private final Object bo;
      private final InvocationWrapper wrap;
      private final MethodDescriptor md;
      private final Object[] args;
      private final int idx;
      private final AuthenticatedSubject capturedSubject;
      private final ClassLoader contextCL;

      Invoker(Invokable inv, Object bo, InvocationWrapper wrap, Object[] args, int idx) {
         this.inv = inv;
         this.bo = bo;
         this.wrap = wrap;
         this.args = args;
         this.idx = idx;
         this.md = wrap.getMethodDescriptor();
         if (this.md.getMethod().getReturnType() != Void.TYPE) {
            wrap.setCancelRunning(new AtomicBoolean(false));
         }

         this.capturedSubject = SecurityManager.getCurrentSubject(KERNEL_ID);
         this.contextCL = Thread.currentThread().getContextClassLoader();
      }

      AtomicBoolean getCancelIfRunning() {
         return this.wrap.getCancelRunning();
      }

      String invocationDetail() {
         return this.wrap.getInvocationDetail();
      }

      boolean isOnewayCall() {
         return this.md.getMethod().getReturnType() == Void.TYPE;
      }

      Object invoke() throws Throwable {
         ClassLoader clSave = Thread.currentThread().getContextClassLoader();
         Thread.currentThread().setContextClassLoader(this.contextCL);
         SecurityManager.pushSubject(KERNEL_ID, this.capturedSubject);

         Object var2;
         try {
            if (this.bo instanceof BaseLocalObject) {
               var2 = SessionLocalMethodInvoker.invoke(this.inv, (BaseLocalObject)this.bo, this.wrap, this.args, this.idx);
               return var2;
            }

            var2 = SessionRemoteMethodInvoker.invoke(this.inv, (BaseRemoteObject)this.bo, this.wrap, this.args, this.idx);
         } catch (RemoteException var6) {
            if (this.md.getClientViewDescriptor().extendsRemote()) {
               throw var6;
            }

            throw EJBRuntimeUtils.wrapCauseInEJBException(var6);
         } finally {
            SecurityManager.popSubject(KERNEL_ID);
            Thread.currentThread().setContextClassLoader(clSave);
         }

         return var2;
      }
   }
}
