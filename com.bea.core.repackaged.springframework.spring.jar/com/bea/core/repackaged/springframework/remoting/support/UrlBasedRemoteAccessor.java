package com.bea.core.repackaged.springframework.remoting.support;

import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;

public abstract class UrlBasedRemoteAccessor extends RemoteAccessor implements InitializingBean {
   private String serviceUrl;

   public void setServiceUrl(String serviceUrl) {
      this.serviceUrl = serviceUrl;
   }

   public String getServiceUrl() {
      return this.serviceUrl;
   }

   public void afterPropertiesSet() {
      if (this.getServiceUrl() == null) {
         throw new IllegalArgumentException("Property 'serviceUrl' is required");
      }
   }
}
