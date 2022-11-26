package weblogic.security;

import org.jvnet.hk2.annotations.Contract;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.runtime.RuntimeMBean;

@Contract
public interface SecurityRuntimeAccess {
   DomainMBean getDomain();

   ServerMBean getServer();

   String getServerName();

   RuntimeMBean getServerRuntime();
}
