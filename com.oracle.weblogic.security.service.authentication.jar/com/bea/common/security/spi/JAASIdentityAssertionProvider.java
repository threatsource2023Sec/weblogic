package com.bea.common.security.spi;

import javax.security.auth.login.AppConfigurationEntry;

public interface JAASIdentityAssertionProvider {
   boolean supportsIdentityAssertion();

   AppConfigurationEntry getAssertionModuleConfiguration();

   ClassLoader getClassLoader();
}
