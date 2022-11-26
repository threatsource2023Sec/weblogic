package com.sun.faces.facelets.tag.jsf;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.facelets.tag.AbstractTagLibrary;
import com.sun.faces.util.FacesLogger;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.ComponentConfig;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagHandler;

public class CompositeComponentTagLibrary extends LazyTagLibrary {
   private static final Logger LOGGER;
   private String ns = null;
   private String compositeLibraryName;
   private boolean enableMissingResourceLibraryDetection;
   private static final String NS_COMPOSITE_COMPONENT_PREFIX = "http://java.sun.com/jsf/composite/";
   private static final String XMLNS_COMPOSITE_COMPONENT_PREFIX = "http://xmlns.jcp.org/jsf/composite/";

   public CompositeComponentTagLibrary(String ns) {
      super(ns);
      if (null == ns) {
         throw new NullPointerException();
      } else {
         this.ns = ns;
         this.init();
      }
   }

   public CompositeComponentTagLibrary(String ns, String compositeLibraryName) {
      super(ns);
      if (null == ns) {
         throw new NullPointerException();
      } else {
         this.ns = ns;
         if (null == compositeLibraryName) {
            throw new NullPointerException();
         } else {
            this.compositeLibraryName = compositeLibraryName;
            this.init();
         }
      }
   }

   private void init() {
      WebConfiguration webconfig = WebConfiguration.getInstance();
      this.enableMissingResourceLibraryDetection = webconfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.EnableMissingResourceLibraryDetection);
   }

   public boolean containsTagHandler(String ns, String localName) {
      boolean result = false;
      Resource ccResource = null;
      if (null != (ccResource = this.getCompositeComponentResource(ns, localName))) {
         try {
            InputStream componentStream = ccResource.getInputStream();
            Throwable var6 = null;

            try {
               result = componentStream != null;
            } catch (Throwable var16) {
               var6 = var16;
               throw var16;
            } finally {
               if (componentStream != null) {
                  if (var6 != null) {
                     try {
                        componentStream.close();
                     } catch (Throwable var15) {
                        var6.addSuppressed(var15);
                     }
                  } else {
                     componentStream.close();
                  }
               }

            }
         } catch (IOException var18) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.log(Level.SEVERE, var18.toString(), var18);
            }
         }
      }

      return result || super.containsTagHandler(ns, localName);
   }

   private Resource getCompositeComponentResource(String ns, String localName) {
      Resource ccResource = null;
      if (ns.equals(this.ns)) {
         FacesContext context = FacesContext.getCurrentInstance();
         String libraryName = this.getCompositeComponentLibraryName(this.ns);
         if (null != libraryName) {
            String ccName = localName + ".xhtml";
            ResourceHandler resourceHandler = context.getApplication().getResourceHandler();
            ccResource = resourceHandler.createResource(ccName, libraryName);
         }
      }

      return ccResource;
   }

   public TagHandler createTagHandler(String ns, String localName, TagConfig tag) throws FacesException {
      TagHandler result = super.createTagHandler(ns, localName, tag);
      if (result == null) {
         ComponentConfig componentConfig = new AbstractTagLibrary.ComponentConfigWrapper(tag, "javax.faces.NamingContainer", (String)null);
         result = new CompositeComponentTagHandler(this.getCompositeComponentResource(ns, localName), componentConfig);
      }

      return (TagHandler)result;
   }

   public boolean tagLibraryForNSExists(String toTest) {
      boolean result = false;
      String resourceId = null;
      if (null != (resourceId = this.getCompositeComponentLibraryName(toTest))) {
         if (this.enableMissingResourceLibraryDetection) {
            result = FacesContext.getCurrentInstance().getApplication().getResourceHandler().libraryExists(resourceId);
         } else {
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, "Skipping call to libraryExists().  Please set context-param {0} to true to verify if library {1} actually exists", new Object[]{WebConfiguration.BooleanWebContextInitParameter.EnableMissingResourceLibraryDetection.getQualifiedName(), toTest});
            }

            result = true;
         }
      }

      return result;
   }

   public static boolean scriptComponentForResourceExists(FacesContext context, Resource componentResource) {
      boolean result = false;
      Resource scriptComponentResource = context.getApplication().getViewHandler().getViewDeclarationLanguage(context, context.getViewRoot().getViewId()).getScriptComponentResource(context, componentResource);
      InputStream is = null;

      try {
         is = scriptComponentResource.getInputStream();
         result = null != scriptComponentResource && null != is;
      } catch (IOException var14) {
         if (LOGGER.isLoggable(Level.SEVERE)) {
            LOGGER.log(Level.SEVERE, var14.toString(), var14);
         }
      } finally {
         try {
            if (null != is) {
               is.close();
            }
         } catch (IOException var13) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.log(Level.SEVERE, var13.toString(), var13);
            }
         }

      }

      return result;
   }

   private String getCompositeComponentLibraryName(String toTest) {
      String resourceId = null;
      if (null != this.compositeLibraryName) {
         resourceId = this.compositeLibraryName;
      } else {
         int resourceIdIndex;
         if (-1 != (resourceIdIndex = toTest.indexOf("http://java.sun.com/jsf/composite/"))) {
            resourceIdIndex += "http://java.sun.com/jsf/composite/".length();
            if (resourceIdIndex < toTest.length()) {
               resourceId = toTest.substring(resourceIdIndex);
            }
         }

         if (-1 != (resourceIdIndex = toTest.indexOf("http://xmlns.jcp.org/jsf/composite/"))) {
            resourceIdIndex += "http://xmlns.jcp.org/jsf/composite/".length();
            if (resourceIdIndex < toTest.length()) {
               resourceId = toTest.substring(resourceIdIndex);
            }
         }
      }

      return resourceId;
   }

   static {
      LOGGER = FacesLogger.FACELETS_COMPONENT.getLogger();
   }
}
