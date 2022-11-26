package org.python.google.common.util.concurrent;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@CanIgnoreReturnValue
@GwtIncompatible
public interface ListeningExecutorService extends ExecutorService {
   ListenableFuture submit(Callable var1);

   ListenableFuture submit(Runnable var1);

   ListenableFuture submit(Runnable var1, Object var2);

   List invokeAll(Collection var1) throws InterruptedException;

   List invokeAll(Collection var1, long var2, TimeUnit var4) throws InterruptedException;
}
