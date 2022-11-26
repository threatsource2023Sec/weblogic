package javax.faces.application;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import javax.faces.context.FacesContext;

public abstract class Resource extends ViewResource {
   public static final String COMPONENT_RESOURCE_KEY = "javax.faces.application.Resource.ComponentResource";
   private String contentType;
   private String libraryName;
   private String resourceName;

   public String getContentType() {
      return this.contentType;
   }

   public void setContentType(String contentType) {
      this.contentType = contentType;
   }

   public String getLibraryName() {
      return this.libraryName;
   }

   public void setLibraryName(String libraryName) {
      this.libraryName = libraryName;
   }

   public String getResourceName() {
      return this.resourceName;
   }

   public void setResourceName(String resourceName) {
      if (resourceName == null) {
         throw new NullPointerException("All resources must have a non-null resourceName.");
      } else {
         this.resourceName = resourceName;
      }
   }

   public abstract InputStream getInputStream() throws IOException;

   public abstract Map getResponseHeaders();

   public abstract String getRequestPath();

   public abstract URL getURL();

   public String toString() {
      return this.getRequestPath();
   }

   public abstract boolean userAgentNeedsUpdate(FacesContext var1);
}
