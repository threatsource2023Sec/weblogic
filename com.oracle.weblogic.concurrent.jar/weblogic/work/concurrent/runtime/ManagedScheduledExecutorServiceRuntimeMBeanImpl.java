package weblogic.work.concurrent.runtime;

import weblogic.management.ManagementException;
import weblogic.management.runtime.ManagedScheduledExecutorServiceRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.WorkManagerRuntimeMBean;
import weblogic.work.concurrent.ManagedScheduledExecutorServiceImpl;

public class ManagedScheduledExecutorServiceRuntimeMBeanImpl extends ManagedExecutorServiceRuntimeMBeanImpl implements ManagedScheduledExecutorServiceRuntimeMBean {
   public ManagedScheduledExecutorServiceRuntimeMBeanImpl(ManagedScheduledExecutorServiceImpl mObj, RuntimeMBean parent, WorkManagerRuntimeMBean wmRuntimeMBean) throws ManagementException {
      super(mObj, parent, wmRuntimeMBean);
   }
}
