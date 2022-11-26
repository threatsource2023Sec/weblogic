package weblogic.management.pconfigurator;

import org.jvnet.hk2.annotations.Contract;
import weblogic.management.configuration.PartitionConfiguratorMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.provider.internal.PartitionTemplateException;

@Contract
public interface PartitionConfigurator {
   void configure(PartitionMBean var1, PartitionConfiguratorMBean var2) throws PartitionTemplateException;

   Class getPartitionConfiguratorMBeanIface();
}
