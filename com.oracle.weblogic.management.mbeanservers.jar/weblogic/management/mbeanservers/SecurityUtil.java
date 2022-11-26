package weblogic.management.mbeanservers;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.ObjectName;
import weblogic.management.mbeanservers.internal.SecurityInterceptor;

public final class SecurityUtil {
   public static final int DOMAIN_RUNTIME = 1;
   public static final int RUNTIME = 2;

   public static boolean isGetAccessAllowed(int mbeanServerType, ObjectName objectName, String attr) throws AttributeNotFoundException, InstanceNotFoundException {
      String mbeanServerName = null;
      switch (mbeanServerType) {
         case 1:
            mbeanServerName = "weblogic.management.mbeanservers.domainruntime";
            break;
         case 2:
            mbeanServerName = "weblogic.management.mbeanservers.runtime";
            break;
         default:
            throw new IllegalArgumentException("Unknown MBean Server type");
      }

      return SecurityInterceptor.isGetAccessAllowed(mbeanServerName, objectName, attr);
   }
}
