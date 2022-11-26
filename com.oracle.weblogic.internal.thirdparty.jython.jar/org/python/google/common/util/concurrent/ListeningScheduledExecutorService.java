package org.python.google.common.util.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@Beta
@CanIgnoreReturnValue
@GwtIncompatible
public interface ListeningScheduledExecutorService extends ScheduledExecutorService, ListeningExecutorService {
   ListenableScheduledFuture schedule(Runnable var1, long var2, TimeUnit var4);

   ListenableScheduledFuture schedule(Callable var1, long var2, TimeUnit var4);

   ListenableScheduledFuture scheduleAtFixedRate(Runnable var1, long var2, long var4, TimeUnit var6);

   ListenableScheduledFuture scheduleWithFixedDelay(Runnable var1, long var2, long var4, TimeUnit var6);
}
