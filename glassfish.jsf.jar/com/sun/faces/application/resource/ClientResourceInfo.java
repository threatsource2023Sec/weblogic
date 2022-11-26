package com.sun.faces.application.resource;

import com.sun.faces.util.FacesLogger;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;

public class ClientResourceInfo extends ResourceInfo {
   private static final Logger LOGGER;
   private static final String COMPRESSED_CONTENT_DIRECTORY = "jsf-compressed";
   boolean cacheTimestamp;
   boolean isDevStage;
   String compressedPath;
   boolean compressible;
   boolean supportsEL;
   private volatile long lastModified = Long.MIN_VALUE;

   public ClientResourceInfo(LibraryInfo library, ContractInfo contract, String name, VersionInfo version, boolean compressible, boolean supportsEL, boolean isDevStage, boolean cacheTimestamp) {
      super(library, contract, name, version);
      this.compressible = compressible;
      this.supportsEL = supportsEL;
      this.isDevStage = isDevStage;
      this.cacheTimestamp = !isDevStage && cacheTimestamp;
      this.initPath(isDevStage);
   }

   ClientResourceInfo(ContractInfo contract, String name, VersionInfo version, String localePrefix, ResourceHelper helper, boolean compressible, boolean supportsEL, boolean isDevStage, boolean cacheTimestamp) {
      super(contract, name, version, helper);
      this.name = name;
      this.version = version;
      this.localePrefix = localePrefix;
      this.helper = helper;
      this.compressible = compressible;
      this.supportsEL = supportsEL;
      this.isDevStage = isDevStage;
      this.cacheTimestamp = !isDevStage && cacheTimestamp;
      this.initPath(isDevStage);
   }

   ClientResourceInfo(ClientResourceInfo other, boolean copyLocalePrefix) {
      super(other, copyLocalePrefix);
      this.cacheTimestamp = other.cacheTimestamp;
      this.compressedPath = other.compressedPath;
      this.compressible = other.compressible;
      this.isDevStage = other.isDevStage;
      this.lastModified = other.lastModified;
      this.supportsEL = other.supportsEL;
      this.initPath(this.isDevStage);
   }

   public void copy(ClientResourceInfo other) {
      super.copy(other);
      this.cacheTimestamp = other.cacheTimestamp;
      this.compressedPath = other.compressedPath;
      this.compressible = other.compressible;
      this.isDevStage = other.isDevStage;
      this.lastModified = other.lastModified;
      this.supportsEL = other.supportsEL;
   }

   public String getCompressedPath() {
      return this.compressedPath;
   }

   public boolean isCompressable() {
      return this.compressible;
   }

   public boolean supportsEL() {
      return this.supportsEL;
   }

   public void disableEL() {
      this.supportsEL = false;
   }

   public long getLastModified(FacesContext ctx) {
      if (this.cacheTimestamp) {
         if (this.lastModified == Long.MIN_VALUE) {
            synchronized(this) {
               if (this.lastModified == Long.MIN_VALUE) {
                  this.lastModified = this.helper.getLastModified(this, ctx);
               }
            }
         }

         return this.lastModified;
      } else {
         return this.helper.getLastModified(this, ctx);
      }
   }

   public String toString() {
      return "ResourceInfo{name='" + this.name + '\'' + ", version='" + (this.version != null ? this.version : "NONE") + '\'' + ", libraryName='" + this.libraryName + '\'' + ", contractInfo='" + (this.contract != null ? this.contract.contract : "NONE") + '\'' + ", libraryVersion='" + (this.library != null ? this.library.getVersion() : "NONE") + '\'' + ", localePrefix='" + (this.localePrefix != null ? this.localePrefix : "NONE") + '\'' + ", path='" + this.path + '\'' + ", compressible='" + this.compressible + '\'' + ", compressedPath=" + this.compressedPath + '}';
   }

   private void initPath(boolean isDevStage) {
      StringBuilder sb = new StringBuilder(32);
      if (this.library != null) {
         sb.append(this.library.getPath());
      } else if (null != this.contract) {
         sb.append(this.helper.getBaseContractsPath());
         sb.append("/").append(this.contract);
      } else {
         sb.append(this.helper.getBaseResourcePath());
      }

      if (this.library == null && this.localePrefix != null) {
         sb.append('/').append(this.localePrefix);
      }

      if (isDevStage && "javax.faces".equals(this.libraryName) && "jsf.js".equals(this.name)) {
         sb.append('/').append("jsf-uncompressed.js");
      } else {
         sb.append('/').append(this.name);
      }

      if (this.version != null) {
         sb.append('/').append(this.version.getVersion());
         String extension = this.version.getExtension();
         if (extension != null) {
            sb.append('.').append(extension);
         }
      }

      this.path = sb.toString();
      if (this.compressible && !this.supportsEL) {
         FacesContext ctx = FacesContext.getCurrentInstance();
         File servletTmpDir = (File)ctx.getExternalContext().getApplicationMap().get("javax.servlet.context.tempdir");
         if (servletTmpDir != null && servletTmpDir.isDirectory()) {
            String tPath = this.path.charAt(0) == '/' ? this.path : '/' + this.path;
            File newDir = new File(servletTmpDir, "jsf-compressed" + tPath);

            try {
               if (!newDir.exists()) {
                  if (newDir.mkdirs()) {
                     this.compressedPath = newDir.getCanonicalPath();
                  } else {
                     this.compressible = false;
                     if (LOGGER.isLoggable(Level.WARNING)) {
                        LOGGER.log(Level.WARNING, "jsf.application.resource.unable_to_create_compression_directory", newDir.getCanonicalPath());
                     }
                  }
               } else {
                  this.compressedPath = newDir.getCanonicalPath();
               }
            } catch (Exception var8) {
               if (LOGGER.isLoggable(Level.SEVERE)) {
                  LOGGER.log(Level.SEVERE, var8.toString(), var8);
               }

               this.compressible = false;
            }
         } else {
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, "File ({0}) referenced by javax.servlet.context.tempdir attribute is null, or was is not a directory.  Compression for {1} will be unavailable.", new Object[]{servletTmpDir == null ? "null" : servletTmpDir.toString(), this.path});
            }

            this.compressible = false;
         }
      }

   }

   static {
      LOGGER = FacesLogger.RESOURCE.getLogger();
   }
}
