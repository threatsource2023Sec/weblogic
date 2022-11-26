package com.bea.common.engine.internal;

public class BaseServiceConfigImpl {
   protected boolean configLockedDown = false;
   protected String serviceName = null;
   protected String serviceLoggingName = null;
   protected boolean exposed = false;

   protected BaseServiceConfigImpl(String serviceName, boolean exposedToEnvironment, String serviceLoggingName) throws IllegalArgumentException {
      if (serviceName != null && serviceLoggingName != null) {
         this.serviceName = serviceName;
         this.serviceLoggingName = serviceLoggingName;
         this.exposed = exposedToEnvironment;
      } else {
         throw new IllegalArgumentException();
      }
   }

   synchronized String getServiceName() {
      return this.serviceName;
   }

   synchronized String getServiceLoggingName() {
      return this.serviceLoggingName;
   }

   synchronized boolean isExposedToEnvironment() {
      return this.exposed;
   }

   synchronized void lockDownConfig() {
      this.configLockedDown = true;
   }

   protected void failIfConfigLockedDown() {
      if (this.configLockedDown) {
         throw new IllegalStateException(EngineLogger.getConfigurationNotModifiedAfterStart());
      }
   }
}
