package com.sun.faces.application.resource;

import java.net.URL;

public class FaceletLibraryInfo extends LibraryInfo {
   private URL url;

   public FaceletLibraryInfo(String name, VersionInfo version, String localePrefix, String contract, ResourceHelper helper, URL url) {
      super(name, version, localePrefix, contract, helper);
      this.url = url;
   }

   public URL getUrl() {
      return this.url;
   }
}
