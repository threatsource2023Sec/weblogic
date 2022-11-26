package weblogic.management.utils;

import org.jvnet.hk2.annotations.Contract;
import weblogic.management.ManagementException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.SystemResourceMBean;

@Contract
public interface AdminServerDeploymentManagerService {
   DomainMBean getEditableDomainMBean(long var1);

   void restartSystemResource(SystemResourceMBean var1) throws ManagementException;

   void removePendingUpdateTasks();
}
