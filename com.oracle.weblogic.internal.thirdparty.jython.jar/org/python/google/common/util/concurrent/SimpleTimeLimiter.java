package org.python.google.common.util.concurrent;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Preconditions;
import org.python.google.common.collect.ObjectArrays;
import org.python.google.common.collect.Sets;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@Beta
@GwtIncompatible
public final class SimpleTimeLimiter implements TimeLimiter {
   private final ExecutorService executor;

   /** @deprecated */
   @Deprecated
   public SimpleTimeLimiter(ExecutorService executor) {
      this.executor = (ExecutorService)Preconditions.checkNotNull(executor);
   }

   /** @deprecated */
   @Deprecated
   public SimpleTimeLimiter() {
      this(Executors.newCachedThreadPool());
   }

   public static SimpleTimeLimiter create(ExecutorService executor) {
      return new SimpleTimeLimiter(executor);
   }

   public Object newProxy(final Object target, Class interfaceType, final long timeoutDuration, final TimeUnit timeoutUnit) {
      Preconditions.checkNotNull(target);
      Preconditions.checkNotNull(interfaceType);
      Preconditions.checkNotNull(timeoutUnit);
      checkPositiveTimeout(timeoutDuration);
      Preconditions.checkArgument(interfaceType.isInterface(), "interfaceType must be an interface type");
      final Set interruptibleMethods = findInterruptibleMethods(interfaceType);
      InvocationHandler handler = new InvocationHandler() {
         public Object invoke(Object obj, final Method method, final Object[] args) throws Throwable {
            Callable callable = new Callable() {
               public Object call() throws Exception {
                  try {
                     return method.invoke(target, args);
                  } catch (InvocationTargetException var2) {
                     throw SimpleTimeLimiter.throwCause(var2, false);
                  }
               }
            };
            return SimpleTimeLimiter.this.callWithTimeout(callable, timeoutDuration, timeoutUnit, interruptibleMethods.contains(method));
         }
      };
      return newProxy(interfaceType, handler);
   }

   /** @deprecated */
   @Deprecated
   @CanIgnoreReturnValue
   public Object callWithTimeout(Callable callable, long timeoutDuration, TimeUnit timeoutUnit, boolean amInterruptible) throws Exception {
      Preconditions.checkNotNull(callable);
      Preconditions.checkNotNull(timeoutUnit);
      checkPositiveTimeout(timeoutDuration);
      Future future = this.executor.submit(callable);

      try {
         if (amInterruptible) {
            try {
               return future.get(timeoutDuration, timeoutUnit);
            } catch (InterruptedException var8) {
               future.cancel(true);
               throw var8;
            }
         } else {
            return Uninterruptibles.getUninterruptibly(future, timeoutDuration, timeoutUnit);
         }
      } catch (ExecutionException var9) {
         throw throwCause(var9, true);
      } catch (TimeoutException var10) {
         future.cancel(true);
         throw new UncheckedTimeoutException(var10);
      }
   }

   public Object callWithTimeout(Callable callable, long timeoutDuration, TimeUnit timeoutUnit) throws TimeoutException, InterruptedException, ExecutionException {
      Preconditions.checkNotNull(callable);
      Preconditions.checkNotNull(timeoutUnit);
      checkPositiveTimeout(timeoutDuration);
      Future future = this.executor.submit(callable);

      try {
         return future.get(timeoutDuration, timeoutUnit);
      } catch (TimeoutException | InterruptedException var7) {
         future.cancel(true);
         throw var7;
      } catch (ExecutionException var8) {
         this.wrapAndThrowExecutionExceptionOrError(var8.getCause());
         throw new AssertionError();
      }
   }

   public Object callUninterruptiblyWithTimeout(Callable callable, long timeoutDuration, TimeUnit timeoutUnit) throws TimeoutException, ExecutionException {
      Preconditions.checkNotNull(callable);
      Preconditions.checkNotNull(timeoutUnit);
      checkPositiveTimeout(timeoutDuration);
      Future future = this.executor.submit(callable);

      try {
         return Uninterruptibles.getUninterruptibly(future, timeoutDuration, timeoutUnit);
      } catch (TimeoutException var7) {
         future.cancel(true);
         throw var7;
      } catch (ExecutionException var8) {
         this.wrapAndThrowExecutionExceptionOrError(var8.getCause());
         throw new AssertionError();
      }
   }

   public void runWithTimeout(Runnable runnable, long timeoutDuration, TimeUnit timeoutUnit) throws TimeoutException, InterruptedException {
      Preconditions.checkNotNull(runnable);
      Preconditions.checkNotNull(timeoutUnit);
      checkPositiveTimeout(timeoutDuration);
      Future future = this.executor.submit(runnable);

      try {
         future.get(timeoutDuration, timeoutUnit);
      } catch (TimeoutException | InterruptedException var7) {
         future.cancel(true);
         throw var7;
      } catch (ExecutionException var8) {
         this.wrapAndThrowRuntimeExecutionExceptionOrError(var8.getCause());
         throw new AssertionError();
      }
   }

   public void runUninterruptiblyWithTimeout(Runnable runnable, long timeoutDuration, TimeUnit timeoutUnit) throws TimeoutException {
      Preconditions.checkNotNull(runnable);
      Preconditions.checkNotNull(timeoutUnit);
      checkPositiveTimeout(timeoutDuration);
      Future future = this.executor.submit(runnable);

      try {
         Uninterruptibles.getUninterruptibly(future, timeoutDuration, timeoutUnit);
      } catch (TimeoutException var7) {
         future.cancel(true);
         throw var7;
      } catch (ExecutionException var8) {
         this.wrapAndThrowRuntimeExecutionExceptionOrError(var8.getCause());
         throw new AssertionError();
      }
   }

   private static Exception throwCause(Exception e, boolean combineStackTraces) throws Exception {
      Throwable cause = e.getCause();
      if (cause == null) {
         throw e;
      } else {
         if (combineStackTraces) {
            StackTraceElement[] combined = (StackTraceElement[])ObjectArrays.concat(cause.getStackTrace(), e.getStackTrace(), StackTraceElement.class);
            cause.setStackTrace(combined);
         }

         if (cause instanceof Exception) {
            throw (Exception)cause;
         } else if (cause instanceof Error) {
            throw (Error)cause;
         } else {
            throw e;
         }
      }
   }

   private static Set findInterruptibleMethods(Class interfaceType) {
      Set set = Sets.newHashSet();
      Method[] var2 = interfaceType.getMethods();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Method m = var2[var4];
         if (declaresInterruptedEx(m)) {
            set.add(m);
         }
      }

      return set;
   }

   private static boolean declaresInterruptedEx(Method method) {
      Class[] var1 = method.getExceptionTypes();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Class exType = var1[var3];
         if (exType == InterruptedException.class) {
            return true;
         }
      }

      return false;
   }

   private static Object newProxy(Class interfaceType, InvocationHandler handler) {
      Object object = Proxy.newProxyInstance(interfaceType.getClassLoader(), new Class[]{interfaceType}, handler);
      return interfaceType.cast(object);
   }

   private void wrapAndThrowExecutionExceptionOrError(Throwable cause) throws ExecutionException {
      if (cause instanceof Error) {
         throw new ExecutionError((Error)cause);
      } else if (cause instanceof RuntimeException) {
         throw new UncheckedExecutionException(cause);
      } else {
         throw new ExecutionException(cause);
      }
   }

   private void wrapAndThrowRuntimeExecutionExceptionOrError(Throwable cause) {
      if (cause instanceof Error) {
         throw new ExecutionError((Error)cause);
      } else {
         throw new UncheckedExecutionException(cause);
      }
   }

   private static void checkPositiveTimeout(long timeoutDuration) {
      Preconditions.checkArgument(timeoutDuration > 0L, "timeout must be positive: %s", timeoutDuration);
   }
}
