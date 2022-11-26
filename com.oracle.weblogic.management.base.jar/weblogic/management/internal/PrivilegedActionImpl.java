package weblogic.management.internal;

import java.security.PrivilegedAction;
import javax.management.ObjectName;
import weblogic.management.ManagementLogger;

class PrivilegedActionImpl implements PrivilegedAction {
   ObjectName name;
   String action;
   String target;
   String methodName;

   PrivilegedActionImpl(ObjectName name, String action, String target, String methodName) {
      this.name = name;
      this.action = action;
      this.target = target;
      this.methodName = methodName;
   }

   public Object run() {
      if (this.target == null) {
         ManagementLogger.logNoMBeanAccess(this.name, this.action, this.methodName);
      } else {
         ManagementLogger.logNoAccess(this.name, this.action, this.target, this.methodName);
      }

      return null;
   }
}
