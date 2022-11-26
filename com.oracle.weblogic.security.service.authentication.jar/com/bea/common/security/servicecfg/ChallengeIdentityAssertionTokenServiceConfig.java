package com.bea.common.security.servicecfg;

public interface ChallengeIdentityAssertionTokenServiceConfig {
   String getAuditServiceName();

   ChallengeIdentityAssertionProviderConfig[] getChallengeIdentityAssertionProviderConfigs();

   public interface ChallengeIdentityAssertionProviderConfig {
      String getChallengeIdentityAssertionProviderName();

      String[] getActiveTypes();
   }
}
