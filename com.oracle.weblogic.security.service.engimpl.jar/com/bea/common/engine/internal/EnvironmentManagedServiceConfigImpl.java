package com.bea.common.engine.internal;

class EnvironmentManagedServiceConfigImpl extends BaseServiceConfigImpl {
   private Object service = null;

   EnvironmentManagedServiceConfigImpl(String serviceName, Object service, boolean exposedToEnvironment, String serviceLoggingName) throws IllegalArgumentException {
      super(serviceName, exposedToEnvironment, serviceLoggingName);
      if (service == null) {
         throw new IllegalArgumentException();
      } else {
         this.service = service;
      }
   }

   Object getService() {
      return this.service;
   }
}
