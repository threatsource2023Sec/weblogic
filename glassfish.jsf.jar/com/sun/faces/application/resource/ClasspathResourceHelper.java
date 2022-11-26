package com.sun.faces.application.resource;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.util.Util;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.ProjectStage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.ResourceResolver;

public class ClasspathResourceHelper extends ResourceHelper {
   private static final String BASE_RESOURCE_PATH = "META-INF/resources";
   private boolean cacheTimestamp;
   private volatile ZipDirectoryEntryScanner libraryScanner;
   private boolean enableMissingResourceLibraryDetection;

   public ClasspathResourceHelper() {
      WebConfiguration webconfig = WebConfiguration.getInstance();
      this.cacheTimestamp = webconfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.CacheResourceModificationTimestamp);
      this.enableMissingResourceLibraryDetection = webconfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.EnableMissingResourceLibraryDetection);
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         ClasspathResourceHelper other = (ClasspathResourceHelper)obj;
         if (this.cacheTimestamp != other.cacheTimestamp) {
            return false;
         } else {
            return this.enableMissingResourceLibraryDetection == other.enableMissingResourceLibraryDetection;
         }
      }
   }

   public int hashCode() {
      int hash = 5;
      hash = 67 * hash + (this.cacheTimestamp ? 1 : 0);
      hash = 67 * hash + (this.enableMissingResourceLibraryDetection ? 1 : 0);
      return hash;
   }

   public String getBaseResourcePath() {
      return "META-INF/resources";
   }

   public String getBaseContractsPath() {
      return WebConfiguration.META_INF_CONTRACTS_DIR;
   }

   protected InputStream getNonCompressedInputStream(ResourceInfo resource, FacesContext ctx) throws IOException {
      InputStream in = null;
      ClassLoader loader;
      String path;
      if (ctx.isProjectStage(ProjectStage.Development)) {
         loader = Util.getCurrentLoader(this.getClass());
         path = resource.getPath();
         if (loader.getResource(path) != null) {
            in = loader.getResource(path).openStream();
         }

         if (in == null && this.getClass().getClassLoader().getResource(path) != null) {
            in = this.getClass().getClassLoader().getResource(path).openStream();
         }
      } else {
         loader = Util.getCurrentLoader(this.getClass());
         path = resource.getPath();
         in = loader.getResourceAsStream(path);
         if (in == null) {
            in = this.getClass().getClassLoader().getResourceAsStream(path);
         }
      }

      return in;
   }

   public URL getURL(ResourceInfo resource, FacesContext ctx) {
      ResourceResolver nonDefaultResourceResolver = (ResourceResolver)ctx.getAttributes().get("com.sun.faces.NDRRPN");
      String path = resource.getPath();
      URL url = null;
      if (null != nonDefaultResourceResolver) {
         url = nonDefaultResourceResolver.resolveUrl(path);
      }

      if (null == url) {
         ClassLoader loader = Util.getCurrentLoader(this.getClass());
         url = loader.getResource(path);
         if (url == null) {
            url = this.getClass().getClassLoader().getResource(resource.getPath());
         }
      }

      return url;
   }

   public LibraryInfo findLibrary(String libraryName, String localePrefix, String contract, FacesContext ctx) {
      ClassLoader loader = Util.getCurrentLoader(this);
      String basePath;
      if (localePrefix == null) {
         basePath = this.getBasePath(contract) + '/' + libraryName + '/';
      } else {
         basePath = this.getBasePath(contract) + '/' + localePrefix + '/' + libraryName + '/';
      }

      URL basePathURL = loader.getResource(basePath);
      if (basePathURL == null) {
         basePathURL = this.getClass().getClassLoader().getResource(basePath);
         if (basePathURL == null) {
            return null;
         }
      }

      return new LibraryInfo(libraryName, (VersionInfo)null, localePrefix, contract, this);
   }

   public LibraryInfo findLibraryWithZipDirectoryEntryScan(String libraryName, String localePrefix, String contract, FacesContext ctx, boolean forceScan) {
      ClassLoader loader = Util.getCurrentLoader(this);
      String basePath;
      if (localePrefix == null) {
         basePath = this.getBasePath(contract) + '/' + libraryName + '/';
      } else {
         basePath = this.getBasePath(contract) + '/' + localePrefix + '/' + libraryName + '/';
      }

      URL basePathURL = loader.getResource(basePath);
      if (basePathURL == null) {
         basePathURL = this.getClass().getClassLoader().getResource(basePath);
         if (basePathURL == null) {
            if (null != localePrefix && libraryName.equals("javax.faces")) {
               return null;
            }

            if (this.enableMissingResourceLibraryDetection || forceScan) {
               if (null == this.libraryScanner) {
                  this.libraryScanner = new ZipDirectoryEntryScanner();
               }

               if (!this.libraryScanner.libraryExists(libraryName, localePrefix)) {
                  return null;
               }
            }
         }
      }

      return new LibraryInfo(libraryName, (VersionInfo)null, localePrefix, contract, this);
   }

   public ResourceInfo findResource(LibraryInfo library, String resourceName, String localePrefix, boolean compressable, FacesContext ctx) {
      resourceName = this.trimLeadingSlash(resourceName);
      ContractInfo[] outContract = new ContractInfo[]{null};
      String[] outBasePath = new String[]{null};
      ClassLoader loader = Util.getCurrentLoader(this);
      URL basePathURL = this.findPathConsideringContracts(loader, library, resourceName, localePrefix, outContract, outBasePath, ctx);
      String basePath = outBasePath[0];
      if (null == basePathURL) {
         basePath = this.deriveBasePath(library, resourceName, localePrefix);
         basePathURL = loader.getResource(basePath);
      }

      if (null == basePathURL) {
         basePathURL = this.getClass().getClassLoader().getResource(basePath);
         if (basePathURL == null) {
            if (library != null) {
               basePath = library.getPath((String)null) + '/' + resourceName;
            } else {
               basePath = this.getBaseResourcePath() + '/' + resourceName;
            }

            basePathURL = loader.getResource(basePath);
            if (basePathURL == null) {
               basePathURL = this.getClass().getClassLoader().getResource(basePath);
               if (basePathURL == null) {
                  return null;
               }
            }

            localePrefix = null;
         }
      }

      ClientResourceInfo value;
      if (library != null) {
         value = new ClientResourceInfo(library, outContract[0], resourceName, (VersionInfo)null, compressable, this.resourceSupportsEL(resourceName, library.getName(), ctx), ctx.isProjectStage(ProjectStage.Development), this.cacheTimestamp);
      } else {
         value = new ClientResourceInfo(outContract[0], resourceName, (VersionInfo)null, localePrefix, this, compressable, this.resourceSupportsEL(resourceName, (String)null, ctx), ctx.isProjectStage(ProjectStage.Development), this.cacheTimestamp);
      }

      if (value.isCompressable()) {
         value = this.handleCompression(value);
      }

      return value;
   }

   private String deriveBasePath(LibraryInfo library, String resourceName, String localePrefix) {
      String basePath = null;
      if (library != null) {
         basePath = library.getPath(localePrefix) + '/' + resourceName;
      } else if (localePrefix == null) {
         basePath = this.getBaseResourcePath() + '/' + resourceName;
      } else {
         basePath = this.getBaseResourcePath() + '/' + localePrefix + '/' + resourceName;
      }

      return basePath;
   }

   private URL findPathConsideringContracts(ClassLoader loader, LibraryInfo library, String resourceName, String localePrefix, ContractInfo[] outContract, String[] outBasePath, FacesContext ctx) {
      UIViewRoot root = ctx.getViewRoot();
      List contracts = null;
      URL result = null;
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

      for(Iterator var12 = ((List)contracts).iterator(); var12.hasNext(); basePath = null) {
         String curContract = (String)var12.next();
         if (library != null) {
            basePath = library.getPath(localePrefix) + '/' + resourceName;
         } else if (localePrefix == null) {
            basePath = this.getBaseContractsPath() + '/' + curContract + '/' + resourceName;
         } else {
            basePath = this.getBaseContractsPath() + '/' + curContract + '/' + localePrefix + '/' + resourceName;
         }

         if (null != (result = loader.getResource(basePath))) {
            outContract[0] = new ContractInfo(curContract);
            outBasePath[0] = basePath;
            break;
         }
      }

      return result;
   }
}
