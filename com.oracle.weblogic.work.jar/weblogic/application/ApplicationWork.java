package weblogic.application;

import org.jvnet.hk2.annotations.Contract;
import weblogic.management.runtime.MaxThreadsConstraintRuntimeMBean;
import weblogic.management.runtime.MinThreadsConstraintRuntimeMBean;
import weblogic.management.runtime.WorkManagerRuntimeMBean;
import weblogic.work.RequestClassRuntimeMBeanImpl;

@Contract
public interface ApplicationWork {
   boolean addRequestClass(RequestClassRuntimeMBeanImpl var1);

   boolean addMaxThreadsConstraint(MaxThreadsConstraintRuntimeMBean var1);

   boolean addMinThreadsConstraint(MinThreadsConstraintRuntimeMBean var1);

   boolean addWorkManager(WorkManagerRuntimeMBean var1);
}
