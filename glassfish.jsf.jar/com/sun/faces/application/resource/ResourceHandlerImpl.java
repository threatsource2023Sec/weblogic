package com.sun.faces.application.resource;

import com.sun.faces.application.ApplicationAssociate;
import com.sun.faces.config.WebConfiguration;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.RequestStateManager;
import com.sun.faces.util.Util;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import javax.faces.application.ProjectStage;
import javax.faces.application.ResourceHandler;
import javax.faces.application.ResourceVisitOption;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class ResourceHandlerImpl extends ResourceHandler {
   private static final Logger LOGGER;
   ResourceManager manager;
   List excludePatterns;
   private long creationTime = System.currentTimeMillis();
   private long maxAge;
   private WebConfiguration webconfig = WebConfiguration.getInstance();

   public ResourceHandlerImpl() {
      ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
      this.manager = ApplicationAssociate.getInstance(extContext).getResourceManager();
      this.initExclusions(extContext.getApplicationMap());
      this.initMaxAge();
   }

   public javax.faces.application.Resource createResource(String resourceName) {
      return this.createResource(resourceName, (String)null, (String)null);
   }

   public javax.faces.application.Resource createResource(String resourceName, String libraryName) {
      return this.createResource(resourceName, libraryName, (String)null);
   }

   public javax.faces.application.Resource createResource(String resourceName, String libraryName, String contentType) {
      Util.notNull("resourceName", resourceName);
      FacesContext ctx = FacesContext.getCurrentInstance();
      String ctype = contentType != null ? contentType : this.getContentType(ctx, resourceName);
      ResourceInfo info = this.manager.findResource(libraryName, resourceName, ctype, ctx);
      return info == null ? null : new ResourceImpl(info, ctype, this.creationTime, this.maxAge);
   }

   public javax.faces.application.Resource createViewResource(FacesContext facesContext, String resourceName) {
      Util.notNull("resourceName", resourceName);
      String contentType = this.getContentType(facesContext, resourceName);
      ResourceInfo resourceInfo = this.manager.findViewResource(resourceName, contentType, facesContext);
      return resourceInfo == null ? null : new ResourceImpl(resourceInfo, contentType, this.creationTime, this.maxAge);
   }

   public Stream getViewResources(FacesContext facesContext, String path, ResourceVisitOption... options) {
      Util.notNull("path", path);
      return this.manager.getViewResources(facesContext, path, Integer.MAX_VALUE, options);
   }

   public Stream getViewResources(FacesContext facesContext, String path, int maxDepth, ResourceVisitOption... options) {
      Util.notNull("path", path);
      Util.notNegative("maxDepth", (long)maxDepth);
      return this.manager.getViewResources(facesContext, path, maxDepth, options);
   }

   public javax.faces.application.Resource createResourceFromId(String resourceId) {
      Util.notNull("resourceId", resourceId);
      FacesContext ctx = FacesContext.getCurrentInstance();
      boolean development = ctx.isProjectStage(ProjectStage.Development);
      ResourceInfo info = this.manager.findResource(resourceId);
      String ctype = this.getContentType(ctx, resourceId);
      if (info == null) {
         this.logMissingResource(ctx, resourceId, (Throwable)null);
         return null;
      } else {
         return new ResourceImpl(info, ctype, this.creationTime, this.maxAge);
      }
   }

   public boolean libraryExists(String libraryName) {
      if (libraryName.contains("../")) {
         return false;
      } else {
         FacesContext context = FacesContext.getCurrentInstance();
         LibraryInfo info = this.manager.findLibrary(libraryName, (String)null, (String)null, context);
         if (info == null) {
            info = this.manager.findLibraryOnClasspathWithZipDirectoryEntryScan(libraryName, (String)null, (String)null, context, true);
         }

         return info != null;
      }
   }

   public boolean isResourceRequest(FacesContext context) {
      Boolean isResourceRequest = (Boolean)RequestStateManager.get(context, "com.sun.faces.RESOURCE_REQUEST");
      if (isResourceRequest == null) {
         String resourceId = this.normalizeResourceRequest(context);
         isResourceRequest = resourceId != null ? resourceId.startsWith("/javax.faces.resource") : Boolean.FALSE;
         RequestStateManager.set(context, "com.sun.faces.RESOURCE_REQUEST", isResourceRequest);
      }

      return isResourceRequest;
   }

   public String getRendererTypeForResourceName(String resourceName) {
      String rendererType = null;
      String contentType = this.getContentType(FacesContext.getCurrentInstance(), resourceName);
      if (null != contentType) {
         contentType = contentType.toLowerCase();
         if (-1 != contentType.indexOf("javascript")) {
            rendererType = "javax.faces.resource.Script";
         } else if (-1 != contentType.indexOf("css")) {
            rendererType = "javax.faces.resource.Stylesheet";
         }
      }

      return rendererType;
   }

   public void handleResourceRequest(FacesContext context) throws IOException {
      String resourceId = this.normalizeResourceRequest(context);
      if (resourceId != null) {
         ExternalContext extContext = context.getExternalContext();
         if (this.isExcluded(resourceId)) {
            extContext.setResponseStatus(404);
         } else {
            assert null != resourceId;

            assert resourceId.startsWith("/javax.faces.resource");

            javax.faces.application.Resource resource = null;
            String resourceName = null;
            String libraryName = null;
            if ("/javax.faces.resource".length() < resourceId.length()) {
               resourceName = resourceId.substring("/javax.faces.resource".length() + 1);

               assert resourceName != null;

               libraryName = (String)context.getExternalContext().getRequestParameterMap().get("ln");
               boolean createResource;
               if (libraryName != null) {
                  createResource = this.libraryNameIsSafe(libraryName);
                  if (!createResource) {
                     this.send404(context, resourceName, libraryName, true);
                     return;
                  }
               } else {
                  createResource = true;
               }

               if (createResource) {
                  resource = context.getApplication().getResourceHandler().createResource(resourceName, libraryName);
               }
            }

            if (resource == null) {
               this.send404(context, resourceName, libraryName, true);
            } else if (resource.userAgentNeedsUpdate(context)) {
               ReadableByteChannel resourceChannel = null;
               WritableByteChannel out = null;
               ByteBuffer buf = this.allocateByteBuffer();

               try {
                  InputStream in = resource.getInputStream();
                  if (in != null) {
                     resourceChannel = Channels.newChannel(in);
                     out = Channels.newChannel(extContext.getResponseOutputStream());
                     extContext.setResponseBufferSize(buf.capacity());
                     String contentType = resource.getContentType();
                     if (contentType != null) {
                        extContext.setResponseContentType(resource.getContentType());
                     }

                     this.handleHeaders(context, resource);
                     int size = 0;
                     int thisRead = resourceChannel.read(buf);

                     for(int totalWritten = 0; thisRead != -1; thisRead = resourceChannel.read(buf)) {
                        buf.rewind();
                        buf.limit(thisRead);
                        size += thisRead;

                        do {
                           totalWritten += out.write(buf);
                        } while(totalWritten < size);

                        buf.clear();
                     }

                     if (!extContext.isResponseCommitted()) {
                        extContext.setResponseContentLength(size);
                     }

                     return;
                  }

                  this.send404(context, resourceName, libraryName, true);
               } catch (IOException var24) {
                  this.send404(context, resourceName, libraryName, var24, true);
                  return;
               } finally {
                  if (out != null) {
                     try {
                        out.close();
                     } catch (IOException var23) {
                     }
                  }

                  if (resourceChannel != null) {
                     resourceChannel.close();
                  }

               }

               return;
            } else {
               this.send304(context);
            }

         }
      }
   }

   private boolean libraryNameIsSafe(String libraryName) {
      assert null != libraryName;

      boolean result = !libraryName.startsWith(".") && !libraryName.startsWith("/") && !libraryName.contains("/") && !libraryName.startsWith("\\") && !libraryName.contains("\\") && !libraryName.startsWith("%2e") && !libraryName.startsWith("%2f") && !libraryName.contains("%2f") && !libraryName.startsWith("%5c") && !libraryName.contains("%5c") && !libraryName.startsWith("\\u002e") && !libraryName.startsWith("\\u002f") && !libraryName.contains("\\u002f") && !libraryName.startsWith("\\u005c") && !libraryName.contains("\\u005c");
      return result;
   }

   private void send404(FacesContext ctx, String resourceName, String libraryName, boolean logMessage) {
      this.send404(ctx, resourceName, libraryName, (Throwable)null, logMessage);
   }

   private void send404(FacesContext ctx, String resourceName, String libraryName, Throwable t, boolean logMessage) {
      ctx.getExternalContext().setResponseStatus(404);
      if (logMessage) {
         this.logMissingResource(ctx, resourceName, libraryName, t);
      }

   }

   private void send304(FacesContext ctx) {
      ctx.getExternalContext().setResponseStatus(304);
   }

   long getCreationTime() {
      return this.creationTime;
   }

   void setCreationTime(long creationTime) {
      this.creationTime = creationTime;
   }

   WebConfiguration getWebConfig() {
      return this.webconfig;
   }

   private void logMissingResource(FacesContext ctx, String resourceName, String libraryName, Throwable t) {
      Level level;
      if (!ctx.isProjectStage(ProjectStage.Production)) {
         level = Level.WARNING;
      } else {
         level = t != null ? Level.WARNING : Level.FINE;
      }

      if (libraryName != null) {
         if (LOGGER.isLoggable(level)) {
            LOGGER.log(level, "jsf.application.resource.unable_to_serve_from_library", new Object[]{resourceName, libraryName});
            if (t != null) {
               LOGGER.log(level, "", t);
            }
         }
      } else if (LOGGER.isLoggable(level)) {
         LOGGER.log(level, "jsf.application.resource.unable_to_serve", new Object[]{resourceName});
         if (t != null) {
            LOGGER.log(level, "", t);
         }
      }

   }

   private void logMissingResource(FacesContext ctx, String resourceId, Throwable t) {
      Level level;
      if (!ctx.isProjectStage(ProjectStage.Production)) {
         level = Level.WARNING;
      } else {
         level = t != null ? Level.WARNING : Level.FINE;
      }

      if (LOGGER.isLoggable(level)) {
         LOGGER.log(level, "jsf.application.resource.unable_to_serve", new Object[]{resourceId});
         if (t != null) {
            LOGGER.log(level, "", t);
         }
      }

   }

   private String getContentType(FacesContext ctx, String resourceName) {
      return ctx.getExternalContext().getMimeType(resourceName);
   }

   private String normalizeResourceRequest(FacesContext context) {
      String facesServletMapping = Util.getFacesMapping(context);
      String path;
      if (!Util.isPrefixMapped(facesServletMapping)) {
         path = context.getExternalContext().getRequestServletPath();
         int i = path.lastIndexOf(".");
         if (0 < i) {
            path = path.substring(0, i);
         }
      } else {
         path = context.getExternalContext().getRequestPathInfo();
      }

      return path;
   }

   private boolean isExcluded(String resourceId) {
      Iterator var2 = this.excludePatterns.iterator();

      Pattern pattern;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         pattern = (Pattern)var2.next();
      } while(!pattern.matcher(resourceId).matches());

      return true;
   }

   private void initExclusions(Map appMap) {
      String excludesParam = this.webconfig.getOptionValue(WebConfiguration.WebContextInitParameter.ResourceExcludes);
      String[] patterns = Util.split(appMap, excludesParam, " ");
      this.excludePatterns = new ArrayList(patterns.length);
      String[] var4 = patterns;
      int var5 = patterns.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         String pattern = var4[var6];
         this.excludePatterns.add(Pattern.compile(".*\\" + pattern));
      }

   }

   private void initMaxAge() {
      this.maxAge = Long.parseLong(this.webconfig.getOptionValue(WebConfiguration.WebContextInitParameter.DefaultResourceMaxAge));
   }

   private void handleHeaders(FacesContext ctx, javax.faces.application.Resource resource) {
      ExternalContext extContext = ctx.getExternalContext();
      Iterator var4 = resource.getResponseHeaders().entrySet().iterator();

      while(var4.hasNext()) {
         Map.Entry cur = (Map.Entry)var4.next();
         extContext.setResponseHeader((String)cur.getKey(), (String)cur.getValue());
      }

   }

   private ByteBuffer allocateByteBuffer() {
      int size;
      try {
         size = Integer.parseInt(this.webconfig.getOptionValue(WebConfiguration.WebContextInitParameter.ResourceBufferSize));
      } catch (NumberFormatException var3) {
         if (LOGGER.isLoggable(Level.WARNING)) {
            LOGGER.log(Level.WARNING, "jsf.application.resource.invalid_resource_buffer_size", new Object[]{this.webconfig.getOptionValue(WebConfiguration.WebContextInitParameter.ResourceBufferSize), WebConfiguration.WebContextInitParameter.ResourceBufferSize.getQualifiedName(), WebConfiguration.WebContextInitParameter.ResourceBufferSize.getDefaultValue()});
         }

         size = Integer.parseInt(WebConfiguration.WebContextInitParameter.ResourceBufferSize.getDefaultValue());
      }

      return ByteBuffer.allocate(size);
   }

   static {
      LOGGER = FacesLogger.APPLICATION.getLogger();
   }
}
