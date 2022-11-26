package com.bea.common.security.internal.legacy.service;

import com.bea.common.engine.ServiceConfigurationException;
import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.internal.service.AuditAtnEventImpl;
import com.bea.common.security.internal.service.ServiceLogger;
import weblogic.security.service.ContextHandler;
import weblogic.security.spi.AuditAtnEvent;
import weblogic.security.spi.AuditAtnEventV2;
import weblogic.security.spi.AuditChannel;
import weblogic.security.spi.AuditChannelV2;
import weblogic.security.spi.AuditEvent;
import weblogic.security.spi.AuditProvider;
import weblogic.security.spi.AuditProviderV2;
import weblogic.security.spi.AuditSeverity;
import weblogic.security.spi.AuditAtnEvent.AtnEventType;
import weblogic.security.spi.AuditAtnEventV2.AtnEventTypeV2;

public class AuditChannelImpl implements ServiceLifecycleSpi {
   private LoggerSpi logger;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("com.bea.common.security.service.AuditService");
      boolean debug = this.logger.isDebugEnabled();
      String method = this.getClass().getName() + ".init";
      if (debug) {
         this.logger.debug(method);
      }

      AuditChannelConfig myconfig = (AuditChannelConfig)config;
      Object provider = dependentServices.getService(myconfig.getAuditProviderName());
      if (provider instanceof AuditProvider) {
         AuditChannel auditChannel = ((AuditProvider)provider).getAuditChannel();
         if (auditChannel == null) {
            throw new ServiceConfigurationException(ServiceLogger.getNullObjectReturned("AuditProvider", "AuditChannel"));
         } else {
            return new ServiceImpl(new V1Adapter(auditChannel));
         }
      } else if (provider instanceof AuditProviderV2) {
         AuditChannelV2 auditChannel = ((AuditProviderV2)provider).getAuditChannel();
         if (auditChannel == null) {
            throw new ServiceConfigurationException(ServiceLogger.getNullObjectReturned("AuditProviderV2", "AuditChannelV2"));
         } else {
            return new ServiceImpl(auditChannel);
         }
      } else {
         return null;
      }
   }

   public void shutdown() {
      boolean debug = this.logger.isDebugEnabled();
      String method = debug ? this.getClass().getName() + ".shutdown" : null;
      if (debug) {
         this.logger.debug(method);
      }

   }

   private class AuditAtnEventV1Impl implements AuditAtnEvent {
      private AuditSeverity severity;
      private String username;
      private ContextHandler handler;
      private AuditAtnEvent.AtnEventType atnEventType;
      private static final String eventType = "Event Type = Authentication Audit Event";
      private Exception exception;

      AuditAtnEventV1Impl(AuditAtnEventV2 e) {
         AuditAtnEventImpl eventV2 = (AuditAtnEventImpl)e;
         this.severity = eventV2.getSeverity();
         this.username = eventV2.getUsername();
         this.handler = eventV2.getHandler();
         this.atnEventType = this.convertType(eventV2.getAtnEventType());
         this.exception = eventV2.getFailureException();
      }

      public AuditSeverity getSeverity() {
         return this.severity;
      }

      public String getUsername() {
         return this.username;
      }

      public ContextHandler getHandler() {
         return this.handler;
      }

      public AuditAtnEvent.AtnEventType getAtnEventType() {
         return this.atnEventType;
      }

      public ContextHandler getContext() {
         return this.handler;
      }

      public Exception getFailureException() {
         return this.exception;
      }

      public String getEventType() {
         return "Event Type = Authentication Audit Event";
      }

      public String toString() {
         String toString = "<<Event Type = Authentication Audit Event><" + this.username + "><" + this.atnEventType.toString() + ">>";
         return toString;
      }

      private AuditAtnEvent.AtnEventType convertType(AuditAtnEventV2.AtnEventTypeV2 typeV2) {
         AuditAtnEvent.AtnEventType typeV1 = null;
         if (typeV2.equals(AtnEventTypeV2.AUTHENTICATE)) {
            typeV1 = AtnEventType.AUTHENTICATE;
         } else if (typeV2.equals(AtnEventTypeV2.ASSERTIDENTITY)) {
            typeV1 = AtnEventType.ASSERTIDENTITY;
         } else if (typeV2.equals(AtnEventTypeV2.IMPERSONATEIDENTITY)) {
            typeV1 = AtnEventType.IMPERSONATEIDENTITY;
         } else if (typeV2.equals(AtnEventTypeV2.VALIDATEIDENTITY)) {
            typeV1 = AtnEventType.VALIDATEIDENTITY;
         } else if (typeV2.equals(AtnEventTypeV2.USERLOCKED)) {
            typeV1 = AtnEventType.USERLOCKED;
         } else if (typeV2.equals(AtnEventTypeV2.USERUNLOCKED)) {
            typeV1 = AtnEventType.USERUNLOCKED;
         } else if (typeV2.equals(AtnEventTypeV2.USERLOCKOUTEXPIRED)) {
            typeV1 = AtnEventType.USERLOCKOUTEXPIRED;
         } else {
            boolean debug = AuditChannelImpl.this.logger.isDebugEnabled();
            String method = debug ? this.getClass().getName() + ".convertType" : null;
            if (debug) {
               AuditChannelImpl.this.logger.debug(method + ": AuditAtnEventV2 is not an known AtnEventTypeV2 type");
            }
         }

         return typeV1;
      }
   }

   private class ServiceImpl implements com.bea.common.security.spi.AuditChannelV2 {
      private AuditChannelV2 channel;

      protected ServiceImpl(AuditChannelV2 channel) {
         this.channel = channel;
      }

      public AuditChannelV2 getAuditChannel() {
         return this.channel;
      }
   }

   private class V1Adapter implements AuditChannelV2 {
      private AuditChannel v1;

      private V1Adapter(AuditChannel v1) {
         this.v1 = v1;
      }

      public void writeEvent(AuditEvent event) {
         boolean debug = AuditChannelImpl.this.logger.isDebugEnabled();
         String method = debug ? this.getClass().getName() + ".writeEvent" : null;
         if (debug) {
            AuditChannelImpl.this.logger.debug(method);
         }

         if (event instanceof AuditAtnEventV2) {
            AuditAtnEventV2 evt = (AuditAtnEventV2)event;
            if (evt.getAtnEventType().equals(AtnEventTypeV2.CREATEPASSWORDDIGEST) || evt.getAtnEventType().equals(AtnEventTypeV2.CREATEDERIVEDKEY)) {
               if (debug) {
                  AuditChannelImpl.this.logger.debug(method + " could not write V2 audit event to V1 audit service: " + evt.getAtnEventType().toString());
               }

               return;
            }

            AuditAtnEventV1Impl evtV1 = AuditChannelImpl.this.new AuditAtnEventV1Impl(evt);
            this.v1.writeEvent(evtV1);
         } else {
            this.v1.writeEvent(event);
         }

      }

      // $FF: synthetic method
      V1Adapter(AuditChannel x1, Object x2) {
         this(x1);
      }
   }
}
