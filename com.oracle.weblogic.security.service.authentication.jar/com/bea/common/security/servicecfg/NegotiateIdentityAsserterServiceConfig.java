package com.bea.common.security.servicecfg;

public interface NegotiateIdentityAsserterServiceConfig {
   String getSessionServiceName();

   String getChallengeIdentityAssertionServiceName();
}
