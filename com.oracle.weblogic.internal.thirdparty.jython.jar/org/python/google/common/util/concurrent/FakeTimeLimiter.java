package org.python.google.common.util.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Preconditions;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@Beta
@CanIgnoreReturnValue
@GwtIncompatible
public final class FakeTimeLimiter implements TimeLimiter {
   public Object newProxy(Object target, Class interfaceType, long timeoutDuration, TimeUnit timeoutUnit) {
      Preconditions.checkNotNull(target);
      Preconditions.checkNotNull(interfaceType);
      Preconditions.checkNotNull(timeoutUnit);
      return target;
   }

   /** @deprecated */
   @Deprecated
   public Object callWithTimeout(Callable callable, long timeoutDuration, TimeUnit timeoutUnit, boolean amInterruptible) throws Exception {
      Preconditions.checkNotNull(timeoutUnit);
      return callable.call();
   }

   public Object callWithTimeout(Callable callable, long timeoutDuration, TimeUnit timeoutUnit) throws ExecutionException {
      Preconditions.checkNotNull(callable);
      Preconditions.checkNotNull(timeoutUnit);

      try {
         return callable.call();
      } catch (RuntimeException var6) {
         throw new UncheckedExecutionException(var6);
      } catch (Exception var7) {
         throw new ExecutionException(var7);
      } catch (Error var8) {
         throw new ExecutionError(var8);
      } catch (Throwable var9) {
         throw new ExecutionException(var9);
      }
   }

   public Object callUninterruptiblyWithTimeout(Callable callable, long timeoutDuration, TimeUnit timeoutUnit) throws ExecutionException {
      return this.callWithTimeout(callable, timeoutDuration, timeoutUnit);
   }

   public void runWithTimeout(Runnable runnable, long timeoutDuration, TimeUnit timeoutUnit) {
      Preconditions.checkNotNull(runnable);
      Preconditions.checkNotNull(timeoutUnit);

      try {
         runnable.run();
      } catch (RuntimeException var6) {
         throw new UncheckedExecutionException(var6);
      } catch (Error var7) {
         throw new ExecutionError(var7);
      } catch (Throwable var8) {
         throw new UncheckedExecutionException(var8);
      }
   }

   public void runUninterruptiblyWithTimeout(Runnable runnable, long timeoutDuration, TimeUnit timeoutUnit) {
      this.runWithTimeout(runnable, timeoutDuration, timeoutUnit);
   }
}
