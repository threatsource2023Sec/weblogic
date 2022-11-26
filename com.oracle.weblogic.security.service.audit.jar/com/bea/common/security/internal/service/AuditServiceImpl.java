package com.bea.common.security.internal.service;

import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.SecurityLogger;
import com.bea.common.security.internal.utils.Delegator;
import com.bea.common.security.service.AuditService;
import com.bea.common.security.servicecfg.AuditServiceConfig;
import weblogic.security.spi.AuditChannelV2;
import weblogic.security.spi.AuditEvent;
import weblogic.security.spi.AuditSeverity;
import weblogic.security.spi.AuditLifecycleEvent.AuditLifecycleEventType;

public class AuditServiceImpl implements ServiceLifecycleSpi, AuditService {
   private LoggerSpi logger;
   private AuditChannelV2[] auditChannels;
   private String[] auditChannelServiceNames;
   private boolean enabled = false;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.AuditService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      AuditServiceConfig myconfig = (AuditServiceConfig)config;
      String[] names = myconfig.getAuditChannelNames();
      this.auditChannels = new AuditChannelV2[names.length];
      this.auditChannelServiceNames = new String[names.length];

      for(int i = 0; i < names.length; ++i) {
         com.bea.common.security.spi.AuditChannelV2 channelProvider = (com.bea.common.security.spi.AuditChannelV2)dependentServices.getService(names[i]);
         this.auditChannels[i] = channelProvider.getAuditChannel();
         this.auditChannelServiceNames[i] = dependentServices.getServiceLoggingName(names[i]);
         if (debug) {
            this.logger.debug(method + " got AuditChannelV2 " + names[i]);
         }
      }

      if (names.length > 0) {
         this.enabled = true;
      }

      if (debug) {
         this.logger.debug("Auditor enabled is " + this.enabled);
      }

      if (this.enabled) {
         this.writeEvent(new AuditLifecycleEventImpl(AuditSeverity.INFORMATION, AuditLifecycleEventType.START_AUDIT, (Exception)null));
      }

      return Delegator.getProxy(AuditService.class, this);
   }

   public void shutdown() {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".shutdown" : null;
      if (debug) {
         this.logger.debug(method);
      }

      if (this.enabled) {
         this.writeEvent(new AuditLifecycleEventImpl(AuditSeverity.INFORMATION, AuditLifecycleEventType.STOP_AUDIT, (Exception)null));
         this.enabled = false;
      }

   }

   public boolean isAuditEnabled() {
      return this.enabled;
   }

   public void writeEvent(AuditEvent event) {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".writeEvent" : null;
      if (debug) {
         this.logger.debug(method);
      }

      for(int i = 0; i < this.auditChannels.length; ++i) {
         try {
            this.auditChannels[i].writeEvent(event);
            if (this.logger.isDebugEnabled()) {
               this.logger.debug("Audit Channel: " + i + "successfully audited the event: " + event.toString());
            }
         } catch (RuntimeException var6) {
            if (this.logger.isDebugEnabled()) {
               this.logger.debug("Audit Channel: " + i + "failed to audit event: " + event.toString(), var6);
            }

            SecurityLogger.logAuditWriteEventError(this.logger, this.auditChannelServiceNames[i]);
         }
      }

   }
}
