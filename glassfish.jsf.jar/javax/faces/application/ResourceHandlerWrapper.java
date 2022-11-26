package javax.faces.application;

import java.io.IOException;
import java.util.stream.Stream;
import javax.faces.FacesWrapper;
import javax.faces.context.FacesContext;

public abstract class ResourceHandlerWrapper extends ResourceHandler implements FacesWrapper {
   private ResourceHandler wrapped;

   /** @deprecated */
   @Deprecated
   public ResourceHandlerWrapper() {
   }

   public ResourceHandlerWrapper(ResourceHandler wrapped) {
      this.wrapped = wrapped;
   }

   public ResourceHandler getWrapped() {
      return this.wrapped;
   }

   public Resource createResource(String resourceName) {
      return this.getWrapped().createResource(resourceName);
   }

   public Resource createResourceFromId(String resourceId) {
      return this.getWrapped().createResourceFromId(resourceId);
   }

   public Resource createResource(String resourceName, String libraryName) {
      return this.getWrapped().createResource(resourceName, libraryName);
   }

   public ViewResource createViewResource(FacesContext context, String resourceName) {
      return this.getWrapped().createViewResource(context, resourceName);
   }

   public Stream getViewResources(FacesContext facesContext, String path, int maxDepth, ResourceVisitOption... options) {
      return this.getWrapped().getViewResources(facesContext, path, maxDepth, options);
   }

   public Stream getViewResources(FacesContext facesContext, String path, ResourceVisitOption... options) {
      return this.getWrapped().getViewResources(facesContext, path, options);
   }

   public Resource createResource(String resourceName, String libraryName, String contentType) {
      return this.getWrapped().createResource(resourceName, libraryName, contentType);
   }

   public void handleResourceRequest(FacesContext context) throws IOException {
      this.getWrapped().handleResourceRequest(context);
   }

   public boolean isResourceRequest(FacesContext context) {
      return this.getWrapped().isResourceRequest(context);
   }

   public boolean isResourceURL(String url) {
      return this.getWrapped().isResourceURL(url);
   }

   public boolean libraryExists(String libraryName) {
      return this.getWrapped().libraryExists(libraryName);
   }

   public String getRendererTypeForResourceName(String resourceName) {
      return this.getWrapped().getRendererTypeForResourceName(resourceName);
   }

   public void markResourceRendered(FacesContext context, String resourceName, String libraryName) {
      this.getWrapped().markResourceRendered(context, resourceName, libraryName);
   }

   public boolean isResourceRendered(FacesContext context, String resourceName, String libraryName) {
      return this.getWrapped().isResourceRendered(context, resourceName, libraryName);
   }
}
