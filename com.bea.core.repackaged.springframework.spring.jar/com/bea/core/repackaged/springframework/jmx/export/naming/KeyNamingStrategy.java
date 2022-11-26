package com.bea.core.repackaged.springframework.jmx.export.naming;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.core.io.Resource;
import com.bea.core.repackaged.springframework.core.io.support.PropertiesLoaderUtils;
import com.bea.core.repackaged.springframework.jmx.support.ObjectNameManager;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.CollectionUtils;
import java.io.IOException;
import java.util.Properties;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

public class KeyNamingStrategy implements ObjectNamingStrategy, InitializingBean {
   protected final Log logger = LogFactory.getLog(this.getClass());
   @Nullable
   private Properties mappings;
   @Nullable
   private Resource[] mappingLocations;
   @Nullable
   private Properties mergedMappings;

   public void setMappings(Properties mappings) {
      this.mappings = mappings;
   }

   public void setMappingLocation(Resource location) {
      this.mappingLocations = new Resource[]{location};
   }

   public void setMappingLocations(Resource... mappingLocations) {
      this.mappingLocations = mappingLocations;
   }

   public void afterPropertiesSet() throws IOException {
      this.mergedMappings = new Properties();
      CollectionUtils.mergePropertiesIntoMap(this.mappings, this.mergedMappings);
      if (this.mappingLocations != null) {
         Resource[] var1 = this.mappingLocations;
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            Resource location = var1[var3];
            if (this.logger.isDebugEnabled()) {
               this.logger.debug("Loading JMX object name mappings file from " + location);
            }

            PropertiesLoaderUtils.fillProperties(this.mergedMappings, location);
         }
      }

   }

   public ObjectName getObjectName(Object managedBean, @Nullable String beanKey) throws MalformedObjectNameException {
      Assert.notNull(beanKey, (String)"KeyNamingStrategy requires bean key");
      String objectName = null;
      if (this.mergedMappings != null) {
         objectName = this.mergedMappings.getProperty(beanKey);
      }

      if (objectName == null) {
         objectName = beanKey;
      }

      return ObjectNameManager.getInstance(objectName);
   }
}
