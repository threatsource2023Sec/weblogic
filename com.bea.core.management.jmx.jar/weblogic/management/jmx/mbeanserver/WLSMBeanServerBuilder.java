package weblogic.management.jmx.mbeanserver;

import javax.management.MBeanServer;
import javax.management.MBeanServerBuilder;
import javax.management.MBeanServerDelegate;

public class WLSMBeanServerBuilder extends MBeanServerBuilder {
   private static final MBeanServerBuilder defaultBuilder = new MBeanServerBuilder();

   public MBeanServerDelegate newMBeanServerDelegate() {
      return defaultBuilder.newMBeanServerDelegate();
   }

   public MBeanServer newMBeanServer(String defaultDomain, MBeanServer outer, MBeanServerDelegate delegate) {
      return this.newMBeanServer(defaultDomain, outer, delegate, (MBeanServer)null);
   }

   public MBeanServer newMBeanServer(String defaultDomain, MBeanServer outer, MBeanServerDelegate delegate, MBeanServer wrappedMBS) {
      WLSMBeanServer wlsMBS = new WLSMBeanServer();
      if (delegate == null) {
         delegate = this.newMBeanServerDelegate();
      }

      if (wrappedMBS == null) {
         wrappedMBS = defaultBuilder.newMBeanServer(defaultDomain, (MBeanServer)(outer == null ? wlsMBS : outer), delegate);
      }

      wlsMBS.setWrappedMBeanServer(wrappedMBS);
      return wlsMBS;
   }
}
