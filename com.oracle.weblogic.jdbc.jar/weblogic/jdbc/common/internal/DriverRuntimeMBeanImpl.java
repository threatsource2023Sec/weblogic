package weblogic.jdbc.common.internal;

import weblogic.management.ManagementException;
import weblogic.management.runtime.JDBCDriverRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public final class DriverRuntimeMBeanImpl extends RuntimeMBeanDelegate implements JDBCDriverRuntimeMBean {
   public DriverRuntimeMBeanImpl(String name, RuntimeMBean restParent) throws ManagementException {
      super(name);
      if (restParent != null) {
         this.setRestParent(restParent);
      }

   }
}
