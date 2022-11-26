package com.bea.common.security.servicecfg;

public interface ChallengeIdentityAssertionServiceConfig {
   String getAuditServiceName();

   String getChallengeIdentityAssertionTokenServiceName();

   String getIdentityAssertionCallbackServiceName();
}
