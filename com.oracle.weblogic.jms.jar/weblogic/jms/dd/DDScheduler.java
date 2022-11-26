package weblogic.jms.dd;

import java.util.HashMap;
import java.util.Map;
import weblogic.jms.JMSService;
import weblogic.jms.common.SerialScheduler;

public class DDScheduler {
   private static Map schedulers = new HashMap();

   public static void schedule(Runnable toRun) {
      getScheduler().schedule(toRun);
   }

   public static void drain() {
      getScheduler().drain();
   }

   public static Throwable waitForComplete() {
      return getScheduler().waitForComplete();
   }

   private static synchronized SerialScheduler getScheduler() {
      String partitionName = JMSService.getSafePartitionNameFromThread();
      if (schedulers.get(partitionName) != null) {
         return (SerialScheduler)schedulers.get(partitionName);
      } else {
         SerialScheduler scheduler = new SerialScheduler();
         schedulers.put(partitionName, scheduler);
         return scheduler;
      }
   }
}
