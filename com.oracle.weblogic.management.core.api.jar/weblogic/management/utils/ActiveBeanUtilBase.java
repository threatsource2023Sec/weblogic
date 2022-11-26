package weblogic.management.utils;

import org.jvnet.hk2.annotations.Contract;
import weblogic.management.configuration.ConfigurationMBean;

@Contract
public interface ActiveBeanUtilBase {
   ConfigurationMBean toOriginalBean(ConfigurationMBean var1);

   ConfigurationMBean toActiveBean(ConfigurationMBean var1);
}
