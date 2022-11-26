package com.bea.common.security.servicecfg;

public interface AuthorizationServiceConfig {
   String getAuditServiceName();

   String getAccessDecisionServiceName();

   String getAdjudicationServiceName();
}
