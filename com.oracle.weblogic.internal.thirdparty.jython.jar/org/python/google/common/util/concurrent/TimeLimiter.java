package org.python.google.common.util.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@Beta
@GwtIncompatible
public interface TimeLimiter {
   Object newProxy(Object var1, Class var2, long var3, TimeUnit var5);

   /** @deprecated */
   @Deprecated
   @CanIgnoreReturnValue
   Object callWithTimeout(Callable var1, long var2, TimeUnit var4, boolean var5) throws Exception;

   Object callWithTimeout(Callable var1, long var2, TimeUnit var4) throws TimeoutException, InterruptedException, ExecutionException;

   Object callUninterruptiblyWithTimeout(Callable var1, long var2, TimeUnit var4) throws TimeoutException, ExecutionException;

   void runWithTimeout(Runnable var1, long var2, TimeUnit var4) throws TimeoutException, InterruptedException;

   void runUninterruptiblyWithTimeout(Runnable var1, long var2, TimeUnit var4) throws TimeoutException;
}
