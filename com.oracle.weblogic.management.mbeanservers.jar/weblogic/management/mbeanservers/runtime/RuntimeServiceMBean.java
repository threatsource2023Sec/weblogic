package weblogic.management.mbeanservers.runtime;

import weblogic.descriptor.DescriptorBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.mbeanservers.Service;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;

public interface RuntimeServiceMBean extends Service {
   String MBEANSERVER_JNDI_NAME = "weblogic.management.mbeanservers.runtime";
   String MBEANSERVER_RUNTIME_CONTEXT = "jmx/runtime";
   String OBJECT_NAME = "com.bea:Name=RuntimeService,Type=" + RuntimeServiceMBean.class.getName();

   DomainMBean getDomainConfiguration();

   ServerMBean getServerConfiguration();

   String getServerName();

   ServerRuntimeMBean getServerRuntime();

   RuntimeMBean findRuntime(DescriptorBean var1);

   DescriptorBean findConfiguration(RuntimeMBean var1);

   Service findService(String var1, String var2);

   Service[] getServices();
}
