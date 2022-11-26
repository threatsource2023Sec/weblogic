package weblogic.management.provider;

import weblogic.management.configuration.DomainMBean;

public interface ConfigurationProcessor {
   void updateConfiguration(DomainMBean var1) throws UpdateException;
}
