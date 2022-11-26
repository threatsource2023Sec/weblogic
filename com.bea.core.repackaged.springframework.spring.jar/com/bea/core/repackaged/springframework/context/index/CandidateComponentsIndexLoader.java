package com.bea.core.repackaged.springframework.context.index;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.core.SpringProperties;
import com.bea.core.repackaged.springframework.core.io.Resource;
import com.bea.core.repackaged.springframework.core.io.UrlResource;
import com.bea.core.repackaged.springframework.core.io.support.PropertiesLoaderUtils;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ConcurrentReferenceHashMap;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentMap;

public final class CandidateComponentsIndexLoader {
   public static final String COMPONENTS_RESOURCE_LOCATION = "META-INF/spring.components";
   public static final String IGNORE_INDEX = "spring.index.ignore";
   private static final boolean shouldIgnoreIndex = SpringProperties.getFlag("spring.index.ignore");
   private static final Log logger = LogFactory.getLog(CandidateComponentsIndexLoader.class);
   private static final ConcurrentMap cache = new ConcurrentReferenceHashMap();

   private CandidateComponentsIndexLoader() {
   }

   @Nullable
   public static CandidateComponentsIndex loadIndex(@Nullable ClassLoader classLoader) {
      ClassLoader classLoaderToUse = classLoader;
      if (classLoader == null) {
         classLoaderToUse = CandidateComponentsIndexLoader.class.getClassLoader();
      }

      return (CandidateComponentsIndex)cache.computeIfAbsent(classLoaderToUse, CandidateComponentsIndexLoader::doLoadIndex);
   }

   @Nullable
   private static CandidateComponentsIndex doLoadIndex(ClassLoader classLoader) {
      if (shouldIgnoreIndex) {
         return null;
      } else {
         try {
            Enumeration urls = classLoader.getResources("META-INF/spring.components");
            if (!urls.hasMoreElements()) {
               return null;
            } else {
               List result = new ArrayList();

               while(urls.hasMoreElements()) {
                  URL url = (URL)urls.nextElement();
                  Properties properties = PropertiesLoaderUtils.loadProperties((Resource)(new UrlResource(url)));
                  result.add(properties);
               }

               if (logger.isDebugEnabled()) {
                  logger.debug("Loaded " + result.size() + "] index(es)");
               }

               int totalCount = result.stream().mapToInt(Hashtable::size).sum();
               return totalCount > 0 ? new CandidateComponentsIndex(result) : null;
            }
         } catch (IOException var5) {
            throw new IllegalStateException("Unable to load indexes from location [META-INF/spring.components]", var5);
         }
      }
   }
}
