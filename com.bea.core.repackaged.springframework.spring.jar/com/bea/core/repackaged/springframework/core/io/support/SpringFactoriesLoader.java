package com.bea.core.repackaged.springframework.core.io.support;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationAwareOrderComparator;
import com.bea.core.repackaged.springframework.core.io.Resource;
import com.bea.core.repackaged.springframework.core.io.UrlResource;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ConcurrentReferenceHashMap;
import com.bea.core.repackaged.springframework.util.LinkedMultiValueMap;
import com.bea.core.repackaged.springframework.util.MultiValueMap;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public final class SpringFactoriesLoader {
   public static final String FACTORIES_RESOURCE_LOCATION = "META-INF/spring.factories";
   private static final Log logger = LogFactory.getLog(SpringFactoriesLoader.class);
   private static final Map cache = new ConcurrentReferenceHashMap();

   private SpringFactoriesLoader() {
   }

   public static List loadFactories(Class factoryClass, @Nullable ClassLoader classLoader) {
      Assert.notNull(factoryClass, (String)"'factoryClass' must not be null");
      ClassLoader classLoaderToUse = classLoader;
      if (classLoader == null) {
         classLoaderToUse = SpringFactoriesLoader.class.getClassLoader();
      }

      List factoryNames = loadFactoryNames(factoryClass, classLoaderToUse);
      if (logger.isTraceEnabled()) {
         logger.trace("Loaded [" + factoryClass.getName() + "] names: " + factoryNames);
      }

      List result = new ArrayList(factoryNames.size());
      Iterator var5 = factoryNames.iterator();

      while(var5.hasNext()) {
         String factoryName = (String)var5.next();
         result.add(instantiateFactory(factoryName, factoryClass, classLoaderToUse));
      }

      AnnotationAwareOrderComparator.sort((List)result);
      return result;
   }

   public static List loadFactoryNames(Class factoryClass, @Nullable ClassLoader classLoader) {
      String factoryClassName = factoryClass.getName();
      return (List)loadSpringFactories(classLoader).getOrDefault(factoryClassName, Collections.emptyList());
   }

   private static Map loadSpringFactories(@Nullable ClassLoader classLoader) {
      MultiValueMap result = (MultiValueMap)cache.get(classLoader);
      if (result != null) {
         return result;
      } else {
         try {
            Enumeration urls = classLoader != null ? classLoader.getResources("META-INF/spring.factories") : ClassLoader.getSystemResources("META-INF/spring.factories");
            MultiValueMap result = new LinkedMultiValueMap();

            while(urls.hasMoreElements()) {
               URL url = (URL)urls.nextElement();
               UrlResource resource = new UrlResource(url);
               Properties properties = PropertiesLoaderUtils.loadProperties((Resource)resource);
               Iterator var6 = properties.entrySet().iterator();

               while(var6.hasNext()) {
                  Map.Entry entry = (Map.Entry)var6.next();
                  String factoryClassName = ((String)entry.getKey()).trim();
                  String[] var9 = StringUtils.commaDelimitedListToStringArray((String)entry.getValue());
                  int var10 = var9.length;

                  for(int var11 = 0; var11 < var10; ++var11) {
                     String factoryName = var9[var11];
                     result.add(factoryClassName, factoryName.trim());
                  }
               }
            }

            cache.put(classLoader, result);
            return result;
         } catch (IOException var13) {
            throw new IllegalArgumentException("Unable to load factories from location [META-INF/spring.factories]", var13);
         }
      }
   }

   private static Object instantiateFactory(String instanceClassName, Class factoryClass, ClassLoader classLoader) {
      try {
         Class instanceClass = ClassUtils.forName(instanceClassName, classLoader);
         if (!factoryClass.isAssignableFrom(instanceClass)) {
            throw new IllegalArgumentException("Class [" + instanceClassName + "] is not assignable to [" + factoryClass.getName() + "]");
         } else {
            return ReflectionUtils.accessibleConstructor(instanceClass).newInstance();
         }
      } catch (Throwable var4) {
         throw new IllegalArgumentException("Unable to instantiate factory class: " + factoryClass.getName(), var4);
      }
   }
}
