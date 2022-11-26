package weblogic.management.provider.internal.situationalconfig;

import org.jvnet.hk2.annotations.Contract;
import weblogic.management.configuration.DomainMBean;

@Contract
public interface SituationalPropertiesProcessor {
   void loadConfiguration(DomainMBean var1) throws Exception;

   void unloadConfiguration(DomainMBean var1) throws Exception;
}
