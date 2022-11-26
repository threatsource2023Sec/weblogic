package weblogic.ejb.container.deployer.mbimpl;

import java.util.concurrent.TimeUnit;
import weblogic.ejb.container.interfaces.ConcurrencyInfo;
import weblogic.j2ee.descriptor.AccessTimeoutBean;
import weblogic.j2ee.descriptor.ConcurrentMethodBean;

public final class ConcurrencyInfoImpl implements ConcurrencyInfo {
   private final String lockType;
   private final long timeout;
   private final TimeUnit timeoutTimeUnit;

   public ConcurrencyInfoImpl(ConcurrentMethodBean cmb) {
      this.lockType = cmb.getConcurrentLockType();
      AccessTimeoutBean atb = cmb.getAccessTimeout();
      if (atb != null) {
         this.timeout = atb.getTimeout();
         this.timeoutTimeUnit = TimeUnit.valueOf(atb.getUnit().toUpperCase());
      } else {
         this.timeout = -1L;
         this.timeoutTimeUnit = null;
      }

   }

   public String getConcurrentLockType() {
      return this.lockType;
   }

   public long getTimeout() {
      return this.timeout;
   }

   public TimeUnit getTimeoutUnit() {
      return this.timeoutTimeUnit;
   }
}
