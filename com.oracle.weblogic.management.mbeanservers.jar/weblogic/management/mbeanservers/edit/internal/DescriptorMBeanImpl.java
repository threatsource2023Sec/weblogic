package weblogic.management.mbeanservers.edit.internal;

import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.mbeanservers.Service;
import weblogic.management.mbeanservers.edit.DescriptorMBean;
import weblogic.management.mbeanservers.internal.ServiceImpl;

public class DescriptorMBeanImpl extends ServiceImpl implements DescriptorMBean {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugJMXEdit");

   DescriptorMBeanImpl(String name, String type, Service parent, String parentAttribute) {
      super(name, type, parent, parentAttribute);
   }
}
