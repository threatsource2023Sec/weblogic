package com.bea.common.security.internal.legacy.helper;

import com.bea.common.security.legacy.ServiceConfigCustomizer;

class ServiceConfigCustomizerImpl implements ServiceConfigCustomizer {
   private String serviceName;
   private boolean serviceRemoved;

   ServiceConfigCustomizerImpl(String serviceName) {
      this.serviceName = serviceName;
   }

   public void renameService(String serviceName) {
      this.serviceName = serviceName;
   }

   public void removeService() {
      this.serviceRemoved = true;
   }

   boolean isServiceRemoved() {
      return this.serviceRemoved;
   }

   String getServiceName() {
      return this.serviceName;
   }
}
