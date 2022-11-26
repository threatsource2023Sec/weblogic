package weblogic.rmi.cluster;

import org.jvnet.hk2.annotations.Service;
import weblogic.core.base.api.FastThreadLocalMarker;
import weblogic.kernel.AuditableThreadLocal;
import weblogic.kernel.AuditableThreadLocalFactory;
import weblogic.rmi.spi.HostID;

@Service
public final class ThreadPreferredHost implements FastThreadLocalMarker {
   public static final AuditableThreadLocal preferredHost = AuditableThreadLocalFactory.createThreadLocal();

   public static void set(HostID host) {
      preferredHost.set(host);
   }

   public static HostID get() {
      return (HostID)preferredHost.get();
   }

   public String getFastThreadLocalClassName() {
      return this.getClass().getCanonicalName();
   }
}
