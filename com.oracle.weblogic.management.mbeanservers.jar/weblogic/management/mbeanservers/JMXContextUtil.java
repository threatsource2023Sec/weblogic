package weblogic.management.mbeanservers;

import java.security.PrivilegedActionException;
import java.util.Locale;
import javax.management.ObjectName;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.management.mbeanservers.internal.JMXContextInterceptor;
import weblogic.management.mbeanservers.internal.MBeanCICInterceptor;

public final class JMXContextUtil {
   public static Locale getLocale() throws PrivilegedActionException {
      return JMXContextInterceptor.getThreadLocale();
   }

   public static String getPartitionNameForMBean(ObjectName oname) {
      if (oname != null) {
         ComponentInvocationContext cic = MBeanCICInterceptor.getCICForMBean(oname);
         if (cic != null) {
            return cic.getPartitionName();
         }
      }

      return "DOMAIN";
   }

   public static ComponentInvocationContext getMBeanCIC(ObjectName oname) {
      return MBeanCICInterceptor.getCICForMBean(oname);
   }
}
