package com.oracle.injection.provider.weld;

import com.oracle.injection.InjectionArchive;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import org.jboss.weld.resources.spi.ResourceLoader;
import org.jboss.weld.resources.spi.ResourceLoadingException;

class BasicResourceLoader implements ResourceLoader {
   private final ClassLoader classLoader;

   BasicResourceLoader(InjectionArchive injectionArchive) {
      if (injectionArchive == null) {
         throw new IllegalArgumentException("InjectionArchive cannot be null");
      } else {
         this.classLoader = injectionArchive.getClassLoader();
      }
   }

   BasicResourceLoader(ClassLoader classLoader) {
      if (classLoader == null) {
         throw new IllegalArgumentException("ClassLoader cannot be null");
      } else {
         this.classLoader = classLoader;
      }
   }

   public Class classForName(String name) {
      try {
         return Class.forName(name, false, this.classLoader);
      } catch (ClassNotFoundException var3) {
         throw new ResourceLoadingException("Unable to load class = " + name, var3);
      }
   }

   public URL getResource(String name) {
      return null;
   }

   public Collection getResources(String name) {
      return Collections.EMPTY_LIST;
   }

   public void cleanup() {
   }

   public ClassLoader getInjectionArchiveClassLoader() {
      return this.classLoader;
   }
}
