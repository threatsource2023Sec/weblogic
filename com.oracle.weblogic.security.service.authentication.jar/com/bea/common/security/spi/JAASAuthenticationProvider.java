package com.bea.common.security.spi;

import javax.security.auth.login.AppConfigurationEntry;

public interface JAASAuthenticationProvider {
   boolean supportsAuthentication();

   AppConfigurationEntry getLoginModuleConfiguration();

   ClassLoader getClassLoader();
}
