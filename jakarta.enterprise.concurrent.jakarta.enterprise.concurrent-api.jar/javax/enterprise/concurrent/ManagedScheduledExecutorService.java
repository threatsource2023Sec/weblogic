package javax.enterprise.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

public interface ManagedScheduledExecutorService extends ManagedExecutorService, ScheduledExecutorService {
   ScheduledFuture schedule(Runnable var1, Trigger var2);

   ScheduledFuture schedule(Callable var1, Trigger var2);
}
