package weblogic.work;

import weblogic.management.ManagementException;
import weblogic.management.runtime.CapacityRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public class CapacityRuntimeMBeanImpl extends RuntimeMBeanDelegate implements CapacityRuntimeMBean {
   private OverloadManager overloadManager;

   CapacityRuntimeMBeanImpl(OverloadManager overloadManager) throws ManagementException {
      super(overloadManager.getName());
      this.overloadManager = overloadManager;
   }

   CapacityRuntimeMBeanImpl(OverloadManager overloadManager, RuntimeMBean parent) throws ManagementException {
      this(overloadManager, parent, true);
   }

   CapacityRuntimeMBeanImpl(OverloadManager overloadManager, RuntimeMBean parent, boolean isRegisterNow) throws ManagementException {
      super(overloadManager.getName(), parent, isRegisterNow);
      this.overloadManager = overloadManager;
   }

   public int getCount() {
      return this.overloadManager.getCapacity();
   }
}
