package weblogic.websocket.tyrus.monitoring;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import weblogic.management.runtime.WebsocketErrorCount;

class BaseMonitor {
   private final Map errorStatistics = new ConcurrentHashMap();
   private final Object errorStatisticsLock = new Object();

   Callable getErrorCounts() {
      return new Callable() {
         public List call() {
            List errorCounts = new ArrayList();
            Iterator var2 = BaseMonitor.this.errorStatistics.entrySet().iterator();

            while(var2.hasNext()) {
               Map.Entry errorCount = (Map.Entry)var2.next();
               errorCounts.add(new WebsocketErrorCount((String)errorCount.getKey(), ((AtomicLong)errorCount.getValue()).get()));
            }

            return errorCounts;
         }
      };
   }

   void onError(Throwable t) {
      String throwableClassName;
      if (t.getCause() == null) {
         throwableClassName = t.getClass().getName();
      } else {
         throwableClassName = t.getCause().getClass().getName();
      }

      if (!this.errorStatistics.containsKey(throwableClassName)) {
         synchronized(this.errorStatisticsLock) {
            if (!this.errorStatistics.containsKey(throwableClassName)) {
               this.errorStatistics.put(throwableClassName, new AtomicLong());
            }
         }
      }

      AtomicLong throwableCounter = (AtomicLong)this.errorStatistics.get(throwableClassName);
      throwableCounter.incrementAndGet();
   }
}
