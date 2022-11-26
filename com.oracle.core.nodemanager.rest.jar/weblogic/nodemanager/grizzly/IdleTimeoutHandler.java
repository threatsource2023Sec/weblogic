package weblogic.nodemanager.grizzly;

import java.util.concurrent.TimeUnit;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.filterchain.Filter;
import org.glassfish.grizzly.utils.DelayedExecutor;
import org.glassfish.grizzly.utils.IdleTimeoutFilter;

public class IdleTimeoutHandler {
   private static final DelayedExecutor excecutor = IdleTimeoutFilter.createDefaultIdleDelayedExecutor();
   private static IdleTimeoutFilter idleFilter = null;

   public static Filter getIdleTimeoutFilter(long timeout, TimeUnit timeUnit) {
      if (idleFilter == null) {
         idleFilter = new IdleTimeoutFilter(excecutor, timeout, timeUnit);
         excecutor.start();
      }

      return idleFilter;
   }

   public static void setConnectionIdleTimeout(Connection connection, long timeMillisec) {
      if (idleFilter != null) {
         idleFilter.getResolver().setTimeoutMillis(connection, timeMillisec);
      }
   }
}
