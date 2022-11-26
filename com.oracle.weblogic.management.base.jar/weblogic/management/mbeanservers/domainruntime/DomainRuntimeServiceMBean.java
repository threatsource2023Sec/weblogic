package weblogic.management.mbeanservers.domainruntime;

import weblogic.descriptor.DescriptorBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.mbeanservers.Service;
import weblogic.management.runtime.DomainRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;

public interface DomainRuntimeServiceMBean extends Service {
   String MBEANSERVER_JNDI_NAME = "weblogic.management.mbeanservers.domainruntime";
   String OBJECT_NAME = "com.bea:Name=DomainRuntimeService,Type=" + DomainRuntimeServiceMBean.class.getName();

   DomainMBean getDomainPending();

   DomainMBean getDomainConfiguration();

   DomainMBean findDomainConfiguration(String var1);

   ServerMBean findServerConfiguration(String var1);

   String getServerName();

   DomainRuntimeMBean getDomainRuntime();

   RuntimeMBean[] findRuntimes(DescriptorBean var1);

   RuntimeMBean findRuntime(DescriptorBean var1, String var2);

   DescriptorBean findConfiguration(RuntimeMBean var1);

   ServerRuntimeMBean[] getServerRuntimes();

   ServerRuntimeMBean lookupServerRuntime(String var1);

   PartitionRuntimeMBean[] findPartitionRuntimes(String var1);

   PartitionRuntimeMBean findPartitionRuntime(String var1, String var2);

   Service findService(String var1, String var2, String var3);

   Service[] getServices(String var1);
}
