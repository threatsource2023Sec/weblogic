package com.bea.common.security.service;

import weblogic.security.spi.AuditEvent;

public interface AuditService {
   boolean isAuditEnabled();

   void writeEvent(AuditEvent var1);
}
