package com.sun.faces.facelets.impl;

import java.net.URL;
import javax.faces.application.ResourceHandler;
import javax.faces.application.ViewResource;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.ResourceResolver;

public class DefaultResourceResolver extends ResourceResolver {
   private ResourceHandler resourceHandler = null;
   public static final String NON_DEFAULT_RESOURCE_RESOLVER_PARAM_NAME = "com.sun.faces.NDRRPN";

   public DefaultResourceResolver(ResourceHandler resourceHandler) {
      this.resourceHandler = resourceHandler;
   }

   public URL resolveUrl(String path) {
      ViewResource faceletResource = this.resourceHandler.createViewResource(FacesContext.getCurrentInstance(), path);
      return faceletResource != null ? faceletResource.getURL() : null;
   }

   public String toString() {
      return "DefaultResourceResolver";
   }
}
