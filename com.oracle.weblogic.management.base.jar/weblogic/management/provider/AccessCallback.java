package weblogic.management.provider;

import weblogic.management.configuration.DomainMBean;

public interface AccessCallback {
   void accessed(DomainMBean var1);

   void shutdown();
}
