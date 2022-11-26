package com.bea.common.security.legacy;

import com.bea.common.engine.Services;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.legacy.spi.LegacyConfigInfoSpi;
import com.bea.common.security.service.AuditService;
import com.bea.common.security.service.IdentityService;
import weblogic.security.spi.SecurityServices;

public interface ExtendedSecurityServices extends SecurityServices {
   Services getServices();

   LoggerSpi getLogger(String var1);

   AuditService getAuditService();

   IdentityService getIdentityService();

   LegacyConfigInfoSpi getLegacyConfig();
}
