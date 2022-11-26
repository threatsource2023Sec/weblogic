package javax.faces.application;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;
import javax.faces.context.FacesContext;

public abstract class ResourceHandler {
   public static final String RESOURCE_IDENTIFIER = "/javax.faces.resource";
   public static final String JSF_SCRIPT_RESOURCE_NAME = "jsf.js";
   public static final String JSF_SCRIPT_LIBRARY_NAME = "javax.faces";
   public static final String RESOURCE_CONTRACT_XML = "javax.faces.contract.xml";
   public static final String WEBAPP_RESOURCES_DIRECTORY_PARAM_NAME = "javax.faces.WEBAPP_RESOURCES_DIRECTORY";
   public static final String WEBAPP_CONTRACTS_DIRECTORY_PARAM_NAME = "javax.faces.WEBAPP_CONTRACTS_DIRECTORY";
   public static final String LOCALE_PREFIX = "javax.faces.resource.localePrefix";
   public static final String RESOURCE_EXCLUDES_PARAM_NAME = "javax.faces.RESOURCE_EXCLUDES";
   public static final String RESOURCE_EXCLUDES_DEFAULT_VALUE = ".class .jsp .jspx .properties .xhtml .groovy";

   public abstract Resource createResource(String var1);

   public ViewResource createViewResource(FacesContext context, String resourceName) {
      return context.getApplication().getResourceHandler().createResource(resourceName);
   }

   public Stream getViewResources(FacesContext facesContext, String path, int maxDepth, ResourceVisitOption... options) {
      return Stream.empty();
   }

   public Stream getViewResources(FacesContext facesContext, String path, ResourceVisitOption... options) {
      return Stream.empty();
   }

   public Resource createResourceFromId(String resourceId) {
      return null;
   }

   public abstract Resource createResource(String var1, String var2);

   public abstract Resource createResource(String var1, String var2, String var3);

   public abstract boolean libraryExists(String var1);

   public abstract void handleResourceRequest(FacesContext var1) throws IOException;

   public abstract boolean isResourceRequest(FacesContext var1);

   public boolean isResourceURL(String url) {
      if (url == null) {
         throw new NullPointerException("null url");
      } else {
         return url.contains("/javax.faces.resource");
      }
   }

   public abstract String getRendererTypeForResourceName(String var1);

   public void markResourceRendered(FacesContext context, String resourceName, String libraryName) {
      String resourceIdentifier = libraryName + ":" + resourceName;
      Set resourceIdentifiers = (Set)context.getAttributes().computeIfAbsent("/javax.faces.resource", (k) -> {
         return new HashSet();
      });
      resourceIdentifiers.add(resourceIdentifier);
   }

   public boolean isResourceRendered(FacesContext context, String resourceName, String libraryName) {
      String resourceIdentifier = libraryName + ":" + resourceName;
      Set resourceIdentifiers = (Set)context.getAttributes().get("/javax.faces.resource");
      return resourceIdentifiers != null && resourceIdentifiers.contains(resourceIdentifier);
   }
}
