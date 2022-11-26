package com.bea.common.security.servicecfg;

public interface IsProtectedResourceServiceConfig {
   String getAuditServiceName();

   String[] getAccessDecisionProviderNames();
}
