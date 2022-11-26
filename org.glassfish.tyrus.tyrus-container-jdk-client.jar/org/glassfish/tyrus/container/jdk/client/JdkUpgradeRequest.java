package org.glassfish.tyrus.container.jdk.client;

import java.net.URI;
import java.security.Principal;
import java.util.Map;
import org.glassfish.tyrus.spi.UpgradeRequest;

abstract class JdkUpgradeRequest extends UpgradeRequest {
   private final UpgradeRequest upgradeRequest;

   JdkUpgradeRequest(UpgradeRequest upgradeRequest) {
      this.upgradeRequest = upgradeRequest;
   }

   public abstract String getHttpMethod();

   public String getHeader(String name) {
      return this.upgradeRequest.getHeader(name);
   }

   public boolean isSecure() {
      return this.upgradeRequest.isSecure();
   }

   public Map getHeaders() {
      return this.upgradeRequest.getHeaders();
   }

   public Principal getUserPrincipal() {
      return this.upgradeRequest.getUserPrincipal();
   }

   public URI getRequestURI() {
      return this.upgradeRequest.getRequestURI();
   }

   public boolean isUserInRole(String role) {
      return this.upgradeRequest.isUserInRole(role);
   }

   public Object getHttpSession() {
      return this.upgradeRequest.getHttpSession();
   }

   public Map getParameterMap() {
      return this.upgradeRequest.getParameterMap();
   }

   public String getQueryString() {
      return this.upgradeRequest.getQueryString();
   }
}
