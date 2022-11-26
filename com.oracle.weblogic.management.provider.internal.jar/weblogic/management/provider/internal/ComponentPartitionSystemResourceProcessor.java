package weblogic.management.provider.internal;

import org.jvnet.hk2.annotations.Contract;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.SystemResourceMBean;

@Contract
public interface ComponentPartitionSystemResourceProcessor {
   boolean handles(Class var1);

   Class descriptorBeanType();

   void processResource(PartitionMBean var1, ResourceGroupMBean var2, SystemResourceMBean var3, SystemResourceMBean var4, Object var5);
}
