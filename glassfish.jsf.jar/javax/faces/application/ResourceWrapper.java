package javax.faces.application;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import javax.faces.FacesWrapper;
import javax.faces.context.FacesContext;

public abstract class ResourceWrapper extends Resource implements FacesWrapper {
   private Resource wrapped;

   public ResourceWrapper(Resource wrapped) {
      this.wrapped = wrapped;
   }

   public Resource getWrapped() {
      return this.wrapped;
   }

   public InputStream getInputStream() throws IOException {
      return this.getWrapped().getInputStream();
   }

   public URL getURL() {
      return this.getWrapped().getURL();
   }

   public Map getResponseHeaders() {
      return this.getWrapped().getResponseHeaders();
   }

   public String getRequestPath() {
      return this.getWrapped().getRequestPath();
   }

   public boolean userAgentNeedsUpdate(FacesContext context) {
      return this.getWrapped().userAgentNeedsUpdate(context);
   }

   public String getContentType() {
      return this.getWrapped().getContentType();
   }

   public void setContentType(String contentType) {
      this.getWrapped().setContentType(contentType);
   }

   public String getLibraryName() {
      return this.getWrapped().getLibraryName();
   }

   public void setLibraryName(String libraryName) {
      this.getWrapped().setLibraryName(libraryName);
   }

   public String getResourceName() {
      return this.getWrapped().getResourceName();
   }

   public void setResourceName(String resourceName) {
      this.getWrapped().setResourceName(resourceName);
   }

   /** @deprecated */
   @Deprecated
   public ResourceWrapper() {
   }
}
