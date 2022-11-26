package weblogic.security;

import weblogic.management.ManagementException;
import weblogic.management.runtime.ProviderRuntimeMBean;
import weblogic.management.runtime.RealmRuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public class ProviderRuntimeMBeanImpl extends RuntimeMBeanDelegate implements ProviderRuntimeMBean {
   public ProviderRuntimeMBeanImpl(String providerName, RealmRuntimeMBean realmRuntime) throws ManagementException {
      super(providerName, realmRuntime);
   }

   public String getProviderName() {
      return this.getName();
   }
}
