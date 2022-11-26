package weblogic.management.runtime;

import org.jvnet.hk2.annotations.Contract;
import weblogic.management.ManagementException;

@Contract
public interface AggregateProgressMBean extends RuntimeMBean {
   ProgressMBean[] getProgress();

   ProgressMBean lookupProgress(String var1);

   ProgressMBean createProgress(String var1) throws ManagementException;

   void destroyProgress(ProgressMBean var1) throws ManagementException;

   String getAggregateState();
}
