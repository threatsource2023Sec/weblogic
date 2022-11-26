package weblogic.j2eeclient;

import weblogic.j2ee.ComponentRuntimeMBeanImpl;
import weblogic.management.ManagementException;
import weblogic.management.runtime.AppClientComponentRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;

public class AppClientComponentRuntimeMBeanImpl extends ComponentRuntimeMBeanImpl implements AppClientComponentRuntimeMBean {
   public AppClientComponentRuntimeMBeanImpl(String name, String moduleId, RuntimeMBean parent) throws ManagementException {
      super(name, moduleId, parent);
   }
}
