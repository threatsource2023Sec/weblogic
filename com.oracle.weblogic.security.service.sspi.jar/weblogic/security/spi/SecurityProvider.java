package weblogic.security.spi;

import weblogic.management.security.ProviderMBean;

public interface SecurityProvider {
   void initialize(ProviderMBean var1, SecurityServices var2);

   String getDescription();

   void shutdown();
}
