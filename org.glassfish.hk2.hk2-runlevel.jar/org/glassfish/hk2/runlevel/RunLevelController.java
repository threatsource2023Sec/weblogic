package org.glassfish.hk2.runlevel;

import java.util.concurrent.Executor;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface RunLevelController {
   RunLevelFuture proceedToAsync(int var1) throws CurrentlyRunningException, IllegalStateException;

   void proceedTo(int var1) throws CurrentlyRunningException;

   RunLevelFuture getCurrentProceeding();

   void cancel();

   int getCurrentRunLevel();

   void setMaximumUseableThreads(int var1);

   int getMaximumUseableThreads();

   void setThreadingPolicy(ThreadingPolicy var1);

   ThreadingPolicy getThreadingPolicy();

   void setExecutor(Executor var1);

   Executor getExecutor();

   long getCancelTimeoutMilliseconds();

   void setCancelTimeoutMilliseconds(long var1);

   Integer getValidationOverride();

   void setValidationOverride(Integer var1);

   public static enum ThreadingPolicy {
      FULLY_THREADED,
      USE_NO_THREADS;
   }
}
