package weblogic.management.mbeans.custom;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import weblogic.management.DomainDir;
import weblogic.management.ManagementException;
import weblogic.management.configuration.CoherenceCacheConfigMBean;
import weblogic.management.internal.PendingDirectoryManager;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.utils.FileUtils;
import weblogic.utils.io.StreamUtils;

public class CoherenceCacheConfigCustomizer extends ConfigurationExtension {
   private static final String RELATIVE_BASE_DIR;
   private static final String CACHE_CONFIG_SUFFIX = "-cache-config.xml";
   private transient String cacheConfigFile;

   public CoherenceCacheConfigCustomizer(ConfigurationMBeanCustomized customized) {
      super(customized);
   }

   public String getCacheConfigurationFile() {
      return this.cacheConfigFile;
   }

   public void setCacheConfigurationFile(String cacheConfigUri) {
      this.cacheConfigFile = cacheConfigUri;
   }

   public void _preDestroy() {
      File cacheConfigRoot = new File(this.getRootLocation());
      FileUtils.remove(cacheConfigRoot);
   }

   public void _validate() throws IllegalArgumentException {
      String config = this.getRuntimeCacheConfigFile();
      if (config == null || config.isEmpty()) {
         this.setCacheConfigFile(this.cacheConfigFile, this.cacheConfigFile);
      }

   }

   public void importCacheConfigurationFile() throws ManagementException {
      this.importCacheConfigurationFile(this.getCacheConfigurationFile());
   }

   public void importCacheConfigurationFile(String uri) throws ManagementException {
      if (uri != null && !uri.isEmpty()) {
         InputStream in = this.determineInput(uri);
         if (in != null) {
            PendingDirectoryManager mgr = PendingDirectoryManager.getInstance();
            String destName = this.deriveConfigFileName();
            OutputStream osDest = null;

            try {
               try {
                  osDest = mgr.getFileOutputStream(destName);
                  StreamUtils.writeTo(in, osDest);
               } finally {
                  in.close();
                  if (osDest != null) {
                     osDest.close();
                  }

               }

               this.setCacheConfigFile(uri, destName);
            } catch (IOException var10) {
               throw new ManagementException("Failed to import cache configuration file (" + uri + ") for CoherenceCacheConfigMBean: " + this.getName(), var10);
            }
         }
      }
   }

   private InputStream determineInput(String url) throws ManagementException {
      InputStream in = null;

      try {
         URL urlOrig = new URL(url);
         in = urlOrig.openStream();
      } catch (MalformedURLException var4) {
      } catch (IOException var5) {
      }

      if (in != null) {
         return (InputStream)in;
      } else {
         try {
            File fileOrig = new File(url);
            if (!fileOrig.exists() || !fileOrig.isFile() || !fileOrig.canRead()) {
               throw new ManagementException("Unable to read from " + fileOrig);
            }

            in = new FileInputStream(fileOrig);
         } catch (IOException var6) {
         }

         return (InputStream)in;
      }
   }

   private String deriveConfigFileName() {
      return RELATIVE_BASE_DIR + File.separator + this.getMbean().getName() + File.separator + this.getMbean().getName() + "-cache-config.xml";
   }

   private String getRuntimeCacheConfigFile() {
      CoherenceCacheConfigMBean cacheConfigMBean = (CoherenceCacheConfigMBean)this.getMbean();
      return cacheConfigMBean.getRuntimeCacheConfigurationUri();
   }

   private void setCacheConfigFile(String src, String dest) {
      this.cacheConfigFile = src;
      CoherenceCacheConfigMBean cacheConfigMBean = (CoherenceCacheConfigMBean)this.getMbean();
      cacheConfigMBean.setRuntimeCacheConfigurationUri(dest);
   }

   private String getRootLocation() {
      return DomainDir.getConfigDir() + File.separator + RELATIVE_BASE_DIR + File.separator + this.getMbean().getName();
   }

   static {
      RELATIVE_BASE_DIR = "coherence" + File.separator + "cache-config";
   }
}
