package weblogic.rmi.internal;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.diagnostics.debug.DebugLogger;

public class TimeoutChecker {
   private static ConcurrentHashMap timeouts = new ConcurrentHashMap();
   private static final DebugLogger debugPerf = DebugLogger.getDebugLogger("DebugRMIRequestPerf");
   private long start;
   private long timeout;
   private long threadId;

   private TimeoutChecker(long start, long timeout, long threadId) {
      this.start = start;
      this.timeout = timeout;
      this.threadId = threadId;
   }

   public static TimeoutChecker init(long timeout) {
      if (timeout == -1L) {
         return null;
      } else {
         long start = System.currentTimeMillis();
         long threadId = Thread.currentThread().getId();
         timeouts.put(threadId, start + timeout);
         return new TimeoutChecker(start, timeout, threadId);
      }
   }

   public void checkTimeout() {
      timeouts.remove(this.threadId);
      long elapsed = System.currentTimeMillis() - this.start;
      if (elapsed > this.timeout) {
         debugPerf.debug(Thread.currentThread().getName() + " took " + elapsed + "ms which is longer time than specified timeout(" + this.timeout + "ms). ");
      }

   }

   protected static boolean isThereTimedOutThreads() {
      long now = System.currentTimeMillis();
      boolean needsToDump = false;
      Iterator it = timeouts.keySet().iterator();

      while(it.hasNext()) {
         long expiredThreadId = (Long)it.next();
         long expired = (Long)timeouts.get(expiredThreadId);
         if (now > expired) {
            needsToDump = true;
            debugPerf.debug("Thread with id:" + expiredThreadId + " reached client-side read timeout");
            it.remove();
         }
      }

      return needsToDump;
   }
}
