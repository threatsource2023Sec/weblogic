package org.glassfish.grizzly.http.server;

public class BackendConfiguration {
   private String scheme;
   private String schemeMapping;
   private String remoteUserMapping;

   public String getScheme() {
      return this.scheme;
   }

   public void setScheme(String scheme) {
      this.scheme = scheme;
      this.schemeMapping = null;
   }

   public String getSchemeMapping() {
      return this.schemeMapping;
   }

   public void setSchemeMapping(String schemeMapping) {
      this.schemeMapping = schemeMapping;
      this.scheme = null;
   }

   public String getRemoteUserMapping() {
      return this.remoteUserMapping;
   }

   public void setRemoteUserMapping(String remoteUserMapping) {
      this.remoteUserMapping = remoteUserMapping;
   }
}
