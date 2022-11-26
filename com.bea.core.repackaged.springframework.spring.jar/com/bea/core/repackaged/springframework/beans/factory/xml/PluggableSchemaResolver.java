package com.bea.core.repackaged.springframework.beans.factory.xml;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.core.io.ClassPathResource;
import com.bea.core.repackaged.springframework.core.io.Resource;
import com.bea.core.repackaged.springframework.core.io.support.PropertiesLoaderUtils;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.CollectionUtils;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

public class PluggableSchemaResolver implements EntityResolver {
   public static final String DEFAULT_SCHEMA_MAPPINGS_LOCATION = "META-INF/spring.schemas";
   private static final Log logger = LogFactory.getLog(PluggableSchemaResolver.class);
   @Nullable
   private final ClassLoader classLoader;
   private final String schemaMappingsLocation;
   @Nullable
   private volatile Map schemaMappings;

   public PluggableSchemaResolver(@Nullable ClassLoader classLoader) {
      this.classLoader = classLoader;
      this.schemaMappingsLocation = "META-INF/spring.schemas";
   }

   public PluggableSchemaResolver(@Nullable ClassLoader classLoader, String schemaMappingsLocation) {
      Assert.hasText(schemaMappingsLocation, "'schemaMappingsLocation' must not be empty");
      this.classLoader = classLoader;
      this.schemaMappingsLocation = schemaMappingsLocation;
   }

   @Nullable
   public InputSource resolveEntity(@Nullable String publicId, @Nullable String systemId) throws IOException {
      if (logger.isTraceEnabled()) {
         logger.trace("Trying to resolve XML entity with public id [" + publicId + "] and system id [" + systemId + "]");
      }

      if (systemId != null) {
         String resourceLocation = (String)this.getSchemaMappings().get(systemId);
         if (resourceLocation == null && systemId.startsWith("https:")) {
            resourceLocation = (String)this.getSchemaMappings().get("http:" + systemId.substring(6));
         }

         if (resourceLocation != null) {
            Resource resource = new ClassPathResource(resourceLocation, this.classLoader);

            try {
               InputSource source = new InputSource(resource.getInputStream());
               source.setPublicId(publicId);
               source.setSystemId(systemId);
               if (logger.isTraceEnabled()) {
                  logger.trace("Found XML schema [" + systemId + "] in classpath: " + resourceLocation);
               }

               return source;
            } catch (FileNotFoundException var6) {
               if (logger.isDebugEnabled()) {
                  logger.debug("Could not find XML schema [" + systemId + "]: " + resource, var6);
               }
            }
         }
      }

      return null;
   }

   private Map getSchemaMappings() {
      Map schemaMappings = this.schemaMappings;
      if (schemaMappings == null) {
         synchronized(this) {
            schemaMappings = this.schemaMappings;
            if (schemaMappings == null) {
               if (logger.isTraceEnabled()) {
                  logger.trace("Loading schema mappings from [" + this.schemaMappingsLocation + "]");
               }

               try {
                  Properties mappings = PropertiesLoaderUtils.loadAllProperties(this.schemaMappingsLocation, this.classLoader);
                  if (logger.isTraceEnabled()) {
                     logger.trace("Loaded schema mappings: " + mappings);
                  }

                  schemaMappings = new ConcurrentHashMap(mappings.size());
                  CollectionUtils.mergePropertiesIntoMap(mappings, (Map)schemaMappings);
                  this.schemaMappings = (Map)schemaMappings;
               } catch (IOException var5) {
                  throw new IllegalStateException("Unable to load schema mappings from location [" + this.schemaMappingsLocation + "]", var5);
               }
            }
         }
      }

      return (Map)schemaMappings;
   }

   public String toString() {
      return "EntityResolver using schema mappings " + this.getSchemaMappings();
   }
}
