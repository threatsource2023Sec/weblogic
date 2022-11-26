package com.sun.faces.application.resource;

import com.sun.faces.application.ApplicationAssociate;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.ProjectStage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class ResourceImpl extends javax.faces.application.Resource implements Externalizable {
   private static final Logger LOGGER;
   private static final String RFC1123_DATE_PATTERN = "EEE, dd MMM yyyy HH:mm:ss zzz";
   private static final String IF_MODIFIED_SINCE = "If-Modified-Since";
   private static final TimeZone GMT;
   private transient ResourceInfo resourceInfo;
   private transient Map responseHeaders;
   private long initialTime;
   private long maxAge;

   public ResourceImpl() {
   }

   public ResourceImpl(ResourceInfo resourceInfo, String contentType, long initialTime, long maxAge) {
      this.resourceInfo = resourceInfo;
      super.setResourceName(resourceInfo.getName());
      super.setLibraryName(resourceInfo.getLibraryInfo() != null ? resourceInfo.getLibraryInfo().getName() : null);
      super.setContentType(contentType);
      this.initialTime = initialTime;
      this.maxAge = maxAge;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         ResourceImpl resource = (ResourceImpl)o;
         return this.resourceInfo.equals(resource.resourceInfo);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.resourceInfo.hashCode();
   }

   public InputStream getInputStream() throws IOException {
      this.initResourceInfo();
      return this.resourceInfo.getHelper().getInputStream(this.resourceInfo, FacesContext.getCurrentInstance());
   }

   public URL getURL() {
      return this.resourceInfo.getHelper().getURL(this.resourceInfo, FacesContext.getCurrentInstance());
   }

   public Map getResponseHeaders() {
      if (!this.isResourceRequest()) {
         return Collections.emptyMap();
      } else {
         if (this.responseHeaders == null) {
            this.responseHeaders = new HashMap(6, 1.0F);
         }

         long expiresTime;
         if (FacesContext.getCurrentInstance().isProjectStage(ProjectStage.Development)) {
            expiresTime = (new Date()).getTime();
         } else {
            expiresTime = (new Date()).getTime() + this.maxAge;
         }

         SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
         format.setTimeZone(GMT);
         this.responseHeaders.put("Expires", format.format(new Date(expiresTime)));
         URL url = this.getURL();
         InputStream in = null;

         try {
            URLConnection conn = url.openConnection();
            conn.setUseCaches(false);
            conn.connect();
            in = conn.getInputStream();
            long lastModified = Util.getLastModified(url);
            long contentLength = (long)conn.getContentLength();
            if (lastModified == 0L) {
               lastModified = this.initialTime;
            }

            this.responseHeaders.put("Last-Modified", format.format(new Date(lastModified)));
            if (lastModified != 0L && contentLength != -1L) {
               this.responseHeaders.put("ETag", "W/\"" + contentLength + '-' + lastModified + '"');
            }
         } catch (IOException var19) {
            if (LOGGER.isLoggable(Level.FINEST)) {
               LOGGER.log(Level.FINEST, "Closing stream", var19);
            }
         } finally {
            if (in != null) {
               try {
                  in.close();
               } catch (IOException var18) {
                  if (LOGGER.isLoggable(Level.FINEST)) {
                     LOGGER.log(Level.FINEST, "Closing stream", var18);
                  }
               }
            }

         }

         return this.responseHeaders;
      }
   }

   public String getRequestPath() {
      FacesContext context = FacesContext.getCurrentInstance();
      String facesServletMapping = Util.getFacesMapping(context);
      String uri = null;
      String version;
      if (Util.isExactMapped(facesServletMapping)) {
         String resource = "/javax.faces.resource/" + this.getResourceName();
         if (Util.isResourceExactMappedToFacesServlet(context.getExternalContext(), resource)) {
            uri = facesServletMapping + resource;
         } else {
            version = Util.getFirstWildCardMappingToFacesServlet(context.getExternalContext());
            if (version == null) {
               throw new IllegalStateException("No suitable mapping for FacesServlet found. To serve resources FacesServlet should have at least one prefix or suffix mapping.");
            }

            facesServletMapping = version.replace("*", "");
         }
      }

      if (uri == null) {
         if (Util.isPrefixMapped(facesServletMapping)) {
            uri = facesServletMapping + "/javax.faces.resource" + '/' + this.getResourceName();
         } else {
            uri = "/javax.faces.resource/" + this.getResourceName() + facesServletMapping;
         }
      }

      boolean queryStarted = false;
      if (this.getLibraryName() != null) {
         queryStarted = true;
         uri = uri + "?ln=" + this.getLibraryName();
      }

      version = "";
      this.initResourceInfo();
      if (this.resourceInfo.getLibraryInfo() != null && this.resourceInfo.getLibraryInfo().getVersion() != null) {
         version = version + this.resourceInfo.getLibraryInfo().getVersion().toString();
      }

      if (this.resourceInfo.getVersion() != null) {
         version = version + this.resourceInfo.getVersion().toString();
      }

      if (version.length() > 0) {
         uri = uri + (queryStarted ? "&v=" : "?v=") + version;
         queryStarted = true;
      }

      String localePrefix = this.resourceInfo.getLocalePrefix();
      if (localePrefix != null) {
         uri = uri + (queryStarted ? "&loc=" : "?loc=") + localePrefix;
         queryStarted = true;
      }

      String contract = this.resourceInfo.getContract();
      if (contract != null) {
         uri = uri + (queryStarted ? "&con=" : "?con=") + contract;
         queryStarted = true;
      }

      if ("jsf.js".equals(this.getResourceName()) && "javax.faces".equals(this.getLibraryName())) {
         ProjectStage stage = context.getApplication().getProjectStage();
         switch (stage) {
            case Development:
               uri = uri + (queryStarted ? "&stage=Development" : "?stage=Development");
               break;
            case SystemTest:
               uri = uri + (queryStarted ? "&stage=SystemTest" : "?stage=SystemTest");
               break;
            case UnitTest:
               uri = uri + (queryStarted ? "&stage=UnitTest" : "?stage=UnitTest");
               break;
            default:
               assert stage.equals(ProjectStage.Production);
         }
      }

      uri = context.getApplication().getViewHandler().getResourceURL(context, uri);
      return uri;
   }

   public boolean userAgentNeedsUpdate(FacesContext context) {
      if (this.resourceInfo instanceof FaceletResourceInfo) {
         return true;
      } else {
         Map requestHeaders = context.getExternalContext().getRequestHeaderMap();
         if (requestHeaders.containsKey("If-Modified-Since")) {
            this.initResourceInfo();
            long lastModifiedOfResource = ((ClientResourceInfo)this.resourceInfo).getLastModified(context) / 1000L * 1000L;
            long lastModifiedHeader = this.getIfModifiedHeader(context.getExternalContext());
            if (0L == lastModifiedOfResource) {
               long startupTime = ApplicationAssociate.getInstance(context.getExternalContext()).getTimeOfInstantiation();
               return startupTime > lastModifiedHeader;
            } else {
               return lastModifiedOfResource > lastModifiedHeader;
            }
         } else {
            return true;
         }
      }
   }

   private long getIfModifiedHeader(ExternalContext extcontext) {
      Object request = extcontext.getRequest();
      if (request instanceof HttpServletRequest) {
         return ((HttpServletRequest)request).getDateHeader("If-Modified-Since");
      } else {
         SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);

         try {
            Date ifModifiedSinceDate = format.parse((String)extcontext.getRequestHeaderMap().get("If-Modified-Since"));
            return ifModifiedSinceDate.getTime();
         } catch (ParseException var5) {
            if (LOGGER.isLoggable(Level.WARNING)) {
               LOGGER.log(Level.WARNING, "jsf.application.resource.invalid_if_modified_since_header", new Object[]{extcontext.getRequestHeaderMap().get("If-Modified-Since")});
               if (var5 != null) {
                  LOGGER.log(Level.WARNING, "", var5);
               }
            }

            return -1L;
         }
      }
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeObject(this.getResourceName());
      out.writeObject(this.getLibraryName());
      out.writeObject(this.getContentType());
      out.writeLong(this.initialTime);
      out.writeLong(this.maxAge);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.setResourceName((String)in.readObject());
      this.setLibraryName((String)in.readObject());
      this.setContentType((String)in.readObject());
      this.initialTime = in.readLong();
      this.maxAge = in.readLong();
   }

   private void initResourceInfo() {
      if (this.resourceInfo == null) {
         ResourceManager manager = ApplicationAssociate.getInstance(FacesContext.getCurrentInstance().getExternalContext()).getResourceManager();
         this.resourceInfo = manager.findResource(this.getLibraryName(), this.getResourceName(), this.getContentType(), FacesContext.getCurrentInstance());
      }
   }

   private boolean isResourceRequest() {
      FacesContext ctx = FacesContext.getCurrentInstance();
      return ctx.getApplication().getResourceHandler().isResourceRequest(ctx);
   }

   static {
      LOGGER = FacesLogger.APPLICATION.getLogger();
      GMT = TimeZone.getTimeZone("GMT");
   }
}
