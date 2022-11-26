package org.jboss.weld.resources;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import org.jboss.weld.resources.spi.ResourceLoader;
import org.jboss.weld.resources.spi.ResourceLoadingException;
import org.jboss.weld.util.collections.EnumerationList;

public abstract class AbstractClassLoaderResourceLoader implements ResourceLoader {
   private static final String ERROR_LOADING_CLASS = "Error loading class ";

   public Class classForName(String name) {
      try {
         return this.classLoader().loadClass(name);
      } catch (ClassNotFoundException var3) {
         throw new ResourceLoadingException("Error loading class " + name, var3);
      } catch (LinkageError var4) {
         throw new ResourceLoadingException("Error loading class " + name, var4);
      } catch (TypeNotPresentException var5) {
         throw new ResourceLoadingException("Error loading class " + name, var5);
      }
   }

   public URL getResource(String name) {
      return this.classLoader().getResource(name);
   }

   public Collection getResources(String name) {
      try {
         return new EnumerationList(this.classLoader().getResources(name));
      } catch (IOException var3) {
         throw new ResourceLoadingException("Error loading resource " + name, var3);
      }
   }

   protected abstract ClassLoader classLoader();
}
