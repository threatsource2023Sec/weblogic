package weblogic.management;

import org.jvnet.hk2.annotations.Contract;
import weblogic.management.runtime.DeploymentManagerMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;

@Contract
public interface PartitionDeploymentManager {
   DeploymentManagerMBean createDeploymentManagerMBean(RuntimeMBean var1, String var2, AuthenticatedSubject var3) throws ManagementException;

   void destroyDeploymentManagerMBean(String var1, AuthenticatedSubject var2) throws ManagementException;
}
