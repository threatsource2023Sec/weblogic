package kodo.manage;

import com.solarmetric.manage.TimeWatch;
import com.solarmetric.manage.jmx.StatisticMBeanFactory;
import java.util.HashMap;
import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.kernel.BrokerFactory;
import org.apache.openjpa.kernel.StoreContext;

public class KodoTimeWatchManager {
   private final Management mgmt;
   private final HashMap timeWatchHashMap = new HashMap();

   public KodoTimeWatchManager(Management m) {
      this.mgmt = m;
   }

   public synchronized TimeWatch getTimeWatch(String name) {
      TimeWatch tw = (TimeWatch)this.timeWatchHashMap.get(name);
      if (tw == null) {
         tw = new TimeWatch();
         this.timeWatchHashMap.put(name, tw);
         new StatisticMBeanFactory(this.mgmt.getConfiguration(), this.mgmt.getMBeanServer(), tw, "TimeWatch", name);
      }

      return tw;
   }

   public TimeWatch getTimeWatch() {
      TimeWatch tw = this.getTimeWatch("default");
      return tw;
   }

   public static TimeWatch getTimeWatch(BrokerFactory bf, String name) {
      return Management.getInstance(bf.getConfiguration()).getTimeWatchManager().getTimeWatch(name);
   }

   public static TimeWatch getTimeWatch(Broker b, String name) {
      return Management.getInstance(b.getConfiguration()).getTimeWatchManager().getTimeWatch(name);
   }

   public static TimeWatch getTimeWatch(StoreContext ctx, String name) {
      return Management.getInstance(ctx.getConfiguration()).getTimeWatchManager().getTimeWatch(name);
   }

   public static TimeWatch getTimeWatch(BrokerFactory bf) {
      return Management.getInstance(bf.getConfiguration()).getTimeWatchManager().getTimeWatch();
   }

   public static TimeWatch getTimeWatch(Broker b) {
      return Management.getInstance(b.getConfiguration()).getTimeWatchManager().getTimeWatch();
   }

   public static TimeWatch getTimeWatch(StoreContext ctx) {
      return Management.getInstance(ctx.getConfiguration()).getTimeWatchManager().getTimeWatch();
   }
}
