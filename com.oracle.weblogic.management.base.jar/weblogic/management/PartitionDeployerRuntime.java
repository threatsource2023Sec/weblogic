package weblogic.management;

import org.jvnet.hk2.annotations.Contract;
import weblogic.management.runtime.DeployerRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;

@Contract
public interface PartitionDeployerRuntime {
   DeployerRuntimeMBean createDeployerRuntimeMBean(RuntimeMBean var1, String var2, AuthenticatedSubject var3) throws ManagementException;

   void destroyDeployerRuntimeMBean(String var1, AuthenticatedSubject var2) throws ManagementException;
}
