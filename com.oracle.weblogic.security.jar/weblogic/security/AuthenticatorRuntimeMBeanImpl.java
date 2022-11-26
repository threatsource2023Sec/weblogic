package weblogic.security;

import weblogic.management.ManagementException;
import weblogic.management.runtime.AuthenticatorRuntimeMBean;
import weblogic.management.runtime.RealmRuntimeMBean;

public class AuthenticatorRuntimeMBeanImpl extends ProviderRuntimeMBeanImpl implements AuthenticatorRuntimeMBean {
   public AuthenticatorRuntimeMBeanImpl(String providerName, RealmRuntimeMBean realmRuntime) throws ManagementException {
      super(providerName, realmRuntime);
   }
}
