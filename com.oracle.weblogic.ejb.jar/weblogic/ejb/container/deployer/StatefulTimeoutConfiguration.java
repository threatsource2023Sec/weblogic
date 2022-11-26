package weblogic.ejb.container.deployer;

import java.util.concurrent.TimeUnit;
import weblogic.j2ee.descriptor.StatefulTimeoutBean;

public class StatefulTimeoutConfiguration {
   private final long timeout;
   private final TimeUnit sourceUnit;

   StatefulTimeoutConfiguration(StatefulTimeoutBean timeoutBean) {
      this.timeout = timeoutBean.getTimeout();
      this.sourceUnit = TimeUnit.valueOf(timeoutBean.getUnit().toUpperCase());
   }

   public long getScrubberDelay(TimeUnit unit) {
      if (this.timeout == -1L) {
         return -1L;
      } else {
         long delaySeconds = 60L;
         if (this.timeout > 1L) {
            delaySeconds = Math.max(delaySeconds, this.getStatefulTimeout(TimeUnit.SECONDS) / 10L);
            delaySeconds = Math.min(delaySeconds, TimeUnit.HOURS.toSeconds(1L));
         }

         return unit.convert(delaySeconds, TimeUnit.SECONDS);
      }
   }

   public long getStatefulTimeout(TimeUnit unit) {
      return this.timeout == -1L ? -1L : unit.convert(this.timeout, this.sourceUnit);
   }
}
