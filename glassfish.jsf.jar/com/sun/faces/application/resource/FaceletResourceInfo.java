package com.sun.faces.application.resource;

import java.net.URL;

public class FaceletResourceInfo extends ResourceInfo {
   private URL url;

   FaceletResourceInfo(ContractInfo contract, String name, VersionInfo version, ResourceHelper helper, URL url) {
      super(contract, name, version, helper);
      this.url = url;
   }

   public URL getUrl() {
      return this.url;
   }
}
