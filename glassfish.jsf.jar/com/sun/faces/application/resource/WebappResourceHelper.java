package com.sun.faces.application.resource;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.util.FacesLogger;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.application.ProjectStage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.ResourceResolver;

public class WebappResourceHelper extends ResourceHelper {
   private static final Logger LOGGER;
   private final String BASE_RESOURCE_PATH;
   private final String BASE_CONTRACTS_PATH;
   private boolean cacheTimestamp;

   public WebappResourceHelper() {
      WebConfiguration webconfig = WebConfiguration.getInstance();
      this.cacheTimestamp = webconfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.CacheResourceModificationTimestamp);
      this.BASE_RESOURCE_PATH = webconfig.getOptionValue(WebConfiguration.WebContextInitParameter.WebAppResourcesDirectory);
      this.BASE_CONTRACTS_PATH = webconfig.getOptionValue(WebConfiguration.WebContextInitParameter.WebAppContractsDirectory);
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         WebappResourceHelper other = (WebappResourceHelper)obj;
         if (this.BASE_RESOURCE_PATH == null) {
            if (other.BASE_RESOURCE_PATH != null) {
               return false;
            }
         } else if (!this.BASE_RESOURCE_PATH.equals(other.BASE_RESOURCE_PATH)) {
            return false;
         }

         label29: {
            if (this.BASE_CONTRACTS_PATH == null) {
               if (other.BASE_CONTRACTS_PATH == null) {
                  break label29;
               }
            } else if (this.BASE_CONTRACTS_PATH.equals(other.BASE_CONTRACTS_PATH)) {
               break label29;
            }

            return false;
         }

         if (this.cacheTimestamp != other.cacheTimestamp) {
            return false;
         } else {
            return true;
         }
      }
   }

   public int hashCode() {
      int hash = 5;
      hash = 37 * hash + (this.BASE_RESOURCE_PATH != null ? this.BASE_RESOURCE_PATH.hashCode() : 0);
      hash = 37 * hash + (this.BASE_CONTRACTS_PATH != null ? this.BASE_CONTRACTS_PATH.hashCode() : 0);
      hash = 37 * hash + (this.cacheTimestamp ? 1 : 0);
      return hash;
   }

   public String getBaseResourcePath() {
      return this.BASE_RESOURCE_PATH;
   }

   public String getBaseContractsPath() {
      return this.BASE_CONTRACTS_PATH;
   }

   protected InputStream getNonCompressedInputStream(ResourceInfo resource, FacesContext ctx) throws IOException {
      return ctx.getExternalContext().getResourceAsStream(resource.getPath());
   }

   public URL getURL(ResourceInfo resource, FacesContext ctx) {
      ResourceResolver nonDefaultResourceResolver = (ResourceResolver)ctx.getAttributes().get("com.sun.faces.NDRRPN");
      String path = resource.getPath();
      if (null != nonDefaultResourceResolver) {
         return nonDefaultResourceResolver.resolveUrl(path);
      } else {
         try {
            return ctx.getExternalContext().getResource(path);
         } catch (MalformedURLException var6) {
            return null;
         }
      }
   }

   public LibraryInfo findLibrary(String libraryName, String localePrefix, String contract, FacesContext ctx) {
      String path;
      if (localePrefix == null) {
         path = this.getBasePath(contract) + '/' + libraryName;
      } else {
         path = this.getBasePath(contract) + '/' + localePrefix + '/' + libraryName;
      }

      Set resourcePaths = ctx.getExternalContext().getResourcePaths(path);
      if (resourcePaths != null && !resourcePaths.isEmpty()) {
         VersionInfo version = this.getVersion(resourcePaths, false);
         return new LibraryInfo(libraryName, version, localePrefix, contract, this);
      } else {
         return null;
      }
   }

   public ResourceInfo findResource(LibraryInfo library, String resourceName, String localePrefix, boolean compressable, FacesContext ctx) {
      resourceName = this.trimLeadingSlash(resourceName);
      ContractInfo[] outContract = new ContractInfo[]{null};
      String basePath = this.findPathConsideringContracts(library, resourceName, localePrefix, outContract, ctx);
      if (null == basePath) {
         if (library != null) {
            basePath = library.getPath(localePrefix) + '/' + resourceName;
         } else if (localePrefix == null) {
            basePath = this.getBaseResourcePath() + '/' + resourceName;
         } else {
            basePath = this.getBaseResourcePath() + '/' + localePrefix + '/' + resourceName;
         }

         try {
            if (ctx.getExternalContext().getResource(basePath) == null) {
               return null;
            }
         } catch (MalformedURLException var11) {
            throw new FacesException(var11);
         }
      }

      Set resourcePaths = ctx.getExternalContext().getResourcePaths(basePath);
      ClientResourceInfo value;
      if (resourcePaths != null && resourcePaths.size() != 0) {
         VersionInfo version = this.getVersion(resourcePaths, true);
         if (version == null && LOGGER.isLoggable(Level.WARNING)) {
            LOGGER.log(Level.WARNING, "jsf.application.resource.unable_to_determine_resource_version.", resourceName);
         }

         if (library != null) {
            value = new ClientResourceInfo(library, outContract[0], resourceName, version, compressable, this.resourceSupportsEL(resourceName, library.getName(), ctx), ctx.isProjectStage(ProjectStage.Development), this.cacheTimestamp);
         } else {
            value = new ClientResourceInfo(outContract[0], resourceName, version, localePrefix, this, compressable, this.resourceSupportsEL(resourceName, (String)null, ctx), ctx.isProjectStage(ProjectStage.Development), this.cacheTimestamp);
         }
      } else if (library != null) {
         value = new ClientResourceInfo(library, outContract[0], resourceName, (VersionInfo)null, compressable, this.resourceSupportsEL(resourceName, library.getName(), ctx), ctx.isProjectStage(ProjectStage.Development), this.cacheTimestamp);
      } else {
         value = new ClientResourceInfo(outContract[0], resourceName, (VersionInfo)null, localePrefix, this, compressable, this.resourceSupportsEL(resourceName, (String)null, ctx), ctx.isProjectStage(ProjectStage.Development), this.cacheTimestamp);
      }

      if (value.isCompressable()) {
         value = this.handleCompression(value);
      }

      return value;
   }

   private String findPathConsideringContracts(LibraryInfo library, String resourceName, String localePrefix, ContractInfo[] outContract, FacesContext ctx) {
      UIViewRoot root = ctx.getViewRoot();
      List contracts = null;
      String basePath;
      if (library != null) {
         if (library.getContract() == null) {
            contracts = Collections.emptyList();
         } else {
            contracts = new ArrayList(1);
            ((List)contracts).add(library.getContract());
         }
      } else if (root == null) {
         basePath = (String)ctx.getExternalContext().getRequestParameterMap().get("con");
         if (null == basePath || 0 >= basePath.length() || ResourceManager.nameContainsForbiddenSequence(basePath)) {
            return null;
         }

         contracts = new ArrayList();
         ((List)contracts).add(basePath);
      } else {
         contracts = ctx.getResourceLibraryContracts();
      }

      basePath = null;
      Iterator var9 = ((List)contracts).iterator();

      while(var9.hasNext()) {
         String curContract = (String)var9.next();
         if (library != null) {
            basePath = library.getPath(localePrefix) + '/' + resourceName;
         } else if (localePrefix == null) {
            basePath = this.getBaseContractsPath() + '/' + curContract + '/' + resourceName;
         } else {
            basePath = this.getBaseContractsPath() + '/' + curContract + '/' + localePrefix + '/' + resourceName;
         }

         try {
            if (ctx.getExternalContext().getResource(basePath) != null) {
               outContract[0] = new ContractInfo(curContract);
               break;
            }

            basePath = null;
         } catch (MalformedURLException var12) {
            throw new FacesException(var12);
         }
      }

      return basePath;
   }

   static {
      LOGGER = FacesLogger.RESOURCE.getLogger();
   }
}
