package com.bea.common.security.internal.legacy.service;

import com.bea.common.security.legacy.spi.LegacyConfigInfoSpi;
import weblogic.management.security.ProviderMBean;

public interface SecurityProviderConfig {
   ProviderMBean getProviderMBean();

   String getProviderClassName();

   String getAuditServiceName();

   String getIdentityServiceName();

   LegacyConfigInfoSpi getLegacyConfigInfo();
}
