package weblogic.utils.concurrent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

final class JDK15ConcurrentFactory extends ConcurrentFactory.Factory {
   ConcurrentBlockingQueue createConcurrentBlockingQueue() {
      return new JDK15ConcurrentBlockingQueue();
   }

   Map createConcurrentMap() {
      return new ConcurrentHashMap();
   }
}
