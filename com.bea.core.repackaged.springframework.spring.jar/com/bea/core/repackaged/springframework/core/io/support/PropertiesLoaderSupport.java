package com.bea.core.repackaged.springframework.core.io.support;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.core.io.Resource;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.CollectionUtils;
import com.bea.core.repackaged.springframework.util.DefaultPropertiesPersister;
import com.bea.core.repackaged.springframework.util.PropertiesPersister;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Properties;

public abstract class PropertiesLoaderSupport {
   protected final Log logger = LogFactory.getLog(this.getClass());
   @Nullable
   protected Properties[] localProperties;
   protected boolean localOverride = false;
   @Nullable
   private Resource[] locations;
   private boolean ignoreResourceNotFound = false;
   @Nullable
   private String fileEncoding;
   private PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();

   public void setProperties(Properties properties) {
      this.localProperties = new Properties[]{properties};
   }

   public void setPropertiesArray(Properties... propertiesArray) {
      this.localProperties = propertiesArray;
   }

   public void setLocation(Resource location) {
      this.locations = new Resource[]{location};
   }

   public void setLocations(Resource... locations) {
      this.locations = locations;
   }

   public void setLocalOverride(boolean localOverride) {
      this.localOverride = localOverride;
   }

   public void setIgnoreResourceNotFound(boolean ignoreResourceNotFound) {
      this.ignoreResourceNotFound = ignoreResourceNotFound;
   }

   public void setFileEncoding(String encoding) {
      this.fileEncoding = encoding;
   }

   public void setPropertiesPersister(@Nullable PropertiesPersister propertiesPersister) {
      this.propertiesPersister = (PropertiesPersister)(propertiesPersister != null ? propertiesPersister : new DefaultPropertiesPersister());
   }

   protected Properties mergeProperties() throws IOException {
      Properties result = new Properties();
      if (this.localOverride) {
         this.loadProperties(result);
      }

      if (this.localProperties != null) {
         Properties[] var2 = this.localProperties;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Properties localProp = var2[var4];
            CollectionUtils.mergePropertiesIntoMap(localProp, result);
         }
      }

      if (!this.localOverride) {
         this.loadProperties(result);
      }

      return result;
   }

   protected void loadProperties(Properties props) throws IOException {
      if (this.locations != null) {
         Resource[] var2 = this.locations;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Resource location = var2[var4];
            if (this.logger.isTraceEnabled()) {
               this.logger.trace("Loading properties file from " + location);
            }

            try {
               PropertiesLoaderUtils.fillProperties(props, new EncodedResource(location, this.fileEncoding), this.propertiesPersister);
            } catch (UnknownHostException | FileNotFoundException var7) {
               if (!this.ignoreResourceNotFound) {
                  throw var7;
               }

               if (this.logger.isDebugEnabled()) {
                  this.logger.debug("Properties resource not found: " + var7.getMessage());
               }
            }
         }
      }

   }
}
