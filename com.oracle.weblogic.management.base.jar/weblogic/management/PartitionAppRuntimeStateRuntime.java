package weblogic.management;

import org.jvnet.hk2.annotations.Contract;
import weblogic.management.runtime.AppRuntimeStateRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;

@Contract
public interface PartitionAppRuntimeStateRuntime {
   AppRuntimeStateRuntimeMBean createAppRuntimeStateRuntimeMBean(RuntimeMBean var1, String var2, AuthenticatedSubject var3) throws ManagementException;

   void destroyAppRuntimeStateRuntimeMBean(String var1, AuthenticatedSubject var2) throws ManagementException;
}
