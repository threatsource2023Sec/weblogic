package weblogic.t3.srvr;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import weblogic.kernel.T3SrvrLogger;

public class T3ServerFuture implements Future {
   private final Object lock = new Object();
   private final T3Srvr parent;
   private Integer result = null;

   public T3ServerFuture(T3Srvr parent) {
      this.parent = parent;
   }

   public T3ServerFuture(int result) {
      this.parent = null;
      this.result = result;
   }

   public boolean cancel(boolean mayInterruptIfRunning) {
      return false;
   }

   public boolean isCancelled() {
      return false;
   }

   public boolean isDone() {
      synchronized(this.lock) {
         return this.result != null;
      }
   }

   public Integer get() throws InterruptedException, ExecutionException {
      try {
         return this.get(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
      } catch (TimeoutException var2) {
         throw new AssertionError(var2);
      }
   }

   public Integer get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
      synchronized(this.lock) {
         if (this.result != null) {
            return this.result;
         }
      }

      try {
         this.parent.waitForDeath(TimeUnit.MILLISECONDS.convert(timeout, unit));
         if (this.parent.isLifecycleExceptionThrown()) {
            synchronized(this.lock) {
               this.result = -1;
            }
         } else {
            synchronized(this.lock) {
               this.result = 0;
            }
         }
      } catch (Throwable var22) {
         T3SrvrLogger.logErrorWhileServerShutdown(var22);
         this.parent.setPreventShutdownHook();
         synchronized(this.lock) {
            this.result = -1;
         }
      } finally {
         T3SrvrLogger.logShutdownCompleted();
      }

      synchronized(this.lock) {
         return this.result;
      }
   }
}
