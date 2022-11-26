package weblogic.management.utils;

import org.jvnet.hk2.annotations.Contract;
import weblogic.management.runtime.JobSchedulerRuntimeMBean;

@Contract
public interface RuntimeGeneratorService {
   JobSchedulerRuntimeMBean createJobSchedulerRuntimeMBean();
}
