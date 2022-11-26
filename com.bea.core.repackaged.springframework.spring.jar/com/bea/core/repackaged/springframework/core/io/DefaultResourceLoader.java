package com.bea.core.repackaged.springframework.core.io;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ResourceUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultResourceLoader implements ResourceLoader {
   @Nullable
   private ClassLoader classLoader;
   private final Set protocolResolvers = new LinkedHashSet(4);
   private final Map resourceCaches = new ConcurrentHashMap(4);

   public DefaultResourceLoader() {
      this.classLoader = ClassUtils.getDefaultClassLoader();
   }

   public DefaultResourceLoader(@Nullable ClassLoader classLoader) {
      this.classLoader = classLoader;
   }

   public void setClassLoader(@Nullable ClassLoader classLoader) {
      this.classLoader = classLoader;
   }

   @Nullable
   public ClassLoader getClassLoader() {
      return this.classLoader != null ? this.classLoader : ClassUtils.getDefaultClassLoader();
   }

   public void addProtocolResolver(ProtocolResolver resolver) {
      Assert.notNull(resolver, (String)"ProtocolResolver must not be null");
      this.protocolResolvers.add(resolver);
   }

   public Collection getProtocolResolvers() {
      return this.protocolResolvers;
   }

   public Map getResourceCache(Class valueType) {
      return (Map)this.resourceCaches.computeIfAbsent(valueType, (key) -> {
         return new ConcurrentHashMap();
      });
   }

   public void clearResourceCaches() {
      this.resourceCaches.clear();
   }

   public Resource getResource(String location) {
      Assert.notNull(location, (String)"Location must not be null");
      Iterator var2 = this.getProtocolResolvers().iterator();

      Resource resource;
      do {
         if (!var2.hasNext()) {
            if (location.startsWith("/")) {
               return this.getResourceByPath(location);
            }

            if (location.startsWith("classpath:")) {
               return new ClassPathResource(location.substring("classpath:".length()), this.getClassLoader());
            }

            try {
               URL url = new URL(location);
               return (Resource)(ResourceUtils.isFileURL(url) ? new FileUrlResource(url) : new UrlResource(url));
            } catch (MalformedURLException var5) {
               return this.getResourceByPath(location);
            }
         }

         ProtocolResolver protocolResolver = (ProtocolResolver)var2.next();
         resource = protocolResolver.resolve(location, this);
      } while(resource == null);

      return resource;
   }

   protected Resource getResourceByPath(String path) {
      return new ClassPathContextResource(path, this.getClassLoader());
   }

   protected static class ClassPathContextResource extends ClassPathResource implements ContextResource {
      public ClassPathContextResource(String path, @Nullable ClassLoader classLoader) {
         super(path, classLoader);
      }

      public String getPathWithinContext() {
         return this.getPath();
      }

      public Resource createRelative(String relativePath) {
         String pathToUse = StringUtils.applyRelativePath(this.getPath(), relativePath);
         return new ClassPathContextResource(pathToUse, this.getClassLoader());
      }
   }
}
