package weblogic.work.concurrent.future;

import java.util.concurrent.TimeUnit;

public interface LockStrategy {
   void acquire() throws InterruptedException;

   boolean tryAcquire(long var1, TimeUnit var3) throws InterruptedException;

   void release();
}
