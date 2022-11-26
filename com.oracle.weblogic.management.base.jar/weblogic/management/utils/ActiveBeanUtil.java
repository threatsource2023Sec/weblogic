package weblogic.management.utils;

import org.jvnet.hk2.annotations.Contract;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.PartitionMBean;

@Contract
public interface ActiveBeanUtil extends ActiveBeanUtilBase {
   boolean isInPartition(ConfigurationMBean var1);

   PartitionMBean findContainingPartition(ConfigurationMBean var1);
}
