package com.bea.common.security.legacy;

import com.bea.common.engine.ServiceEngineConfig;

public interface AuthenticationServicesConfigHelper {
   String getPrincipalValidationServiceName();

   String getJAASAuthenticationConfigurationServiceName();

   String getJAASIdentityAssertionConfigurationServiceName();

   String getJAASLoginServiceName();

   String getIdentityImpersonationServiceName();

   String getIdentityAssertionCallbackServiceName();

   String getIdentityAssertionTokenServiceName();

   String getIdentityAssertionServiceName();

   String getChallengeIdentityAssertionTokenServiceName();

   String getChallengeIdentityAssertionServiceName();

   String getJAASAuthenticationServiceName();

   String getIdentityCacheServiceName();

   String getIdentityServiceName();

   String getNegotiateIdentityAsserterServiceName();

   String getPasswordValidationServiceName();

   ServiceConfigCustomizer getPrincipalValidationServiceCustomizer();

   ServiceConfigCustomizer getJAASAuthenticationConfigurationServiceCustomizer();

   ServiceConfigCustomizer getJAASIdentityAssertionConfigurationServiceCustomizer();

   ServiceConfigCustomizer getJAASLoginServiceCustomizer();

   ServiceConfigCustomizer getIdentityImpersonationServiceCustomizer();

   ServiceConfigCustomizer getIdentityAssertionCallbackServiceCustomizer();

   ServiceConfigCustomizer getIdentityAssertionTokenServiceCustomizer();

   ServiceConfigCustomizer getIdentityAssertionServiceCustomizer();

   ServiceConfigCustomizer getChallengeIdentityAssertionTokenServiceCustomizer();

   ServiceConfigCustomizer getChallengeIdentityAssertionServiceCustomizer();

   ServiceConfigCustomizer getJAASAuthenticationServiceCustomizer();

   IdentityCacheServiceConfigCustomizer getIdentityCacheServiceCustomizer();

   ServiceConfigCustomizer getNegotiateIdentityAsserterServiceCustomizer();

   void addToConfig(ServiceEngineConfig var1, String var2);

   public interface IdentityCacheServiceConfigCustomizer extends ServiceConfigCustomizer {
      void setIdentityCacheEnabled(boolean var1);

      void setMaxIdentitiesInCache(int var1);

      void setIdentityCacheTTL(long var1);

      void setIdentityAssertionDoNotCacheContextElements(String[] var1);
   }
}
