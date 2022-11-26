package weblogic.security;

import org.jvnet.hk2.annotations.Contract;
import weblogic.management.runtime.SingleSignOnServicesRuntimeMBean;

@Contract
public interface ServerRuntimeSecurityAccess {
   void setSingleSignOnServicesRuntime(SingleSignOnServicesRuntimeMBean var1);
}
