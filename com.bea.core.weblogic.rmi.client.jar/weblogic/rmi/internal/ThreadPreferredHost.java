package weblogic.rmi.internal;

import weblogic.kernel.AuditableThreadLocal;
import weblogic.kernel.AuditableThreadLocalFactory;
import weblogic.rmi.spi.HostID;

public class ThreadPreferredHost {
   public static final AuditableThreadLocal preferredHost = AuditableThreadLocalFactory.createThreadLocal();

   public static void set(HostID host) {
      preferredHost.set(host);
   }

   public static HostID get() {
      return (HostID)preferredHost.get();
   }
}
