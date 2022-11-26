package com.bea.common.security.servicecfg;

public interface JAASAuthenticationConfigurationServiceConfig {
   String[] getJAASAuthenticationProviderNames();

   boolean isOracleDefaultLoginConfiguration();
}
