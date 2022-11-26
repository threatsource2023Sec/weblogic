package com.bea.common.security.servicecfg;

public interface IdentityAssertionTokenServiceConfig {
   String getAuditServiceName();

   IdentityAsserterV2Config[] getIdentityAsserterV2Configs();

   public interface IdentityAsserterV2Config {
      String getIdentityAsserterV2Name();

      String[] getActiveTypes();
   }
}
