package org.jboss.weld.util;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceConfigurationError;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.jboss.weld.metadata.FileMetadata;
import org.jboss.weld.resources.ClassLoaderResourceLoader;
import org.jboss.weld.resources.spi.ResourceLoader;
import org.jboss.weld.resources.spi.ResourceLoadingException;

public class ServiceLoader implements Iterable {
   private static final String ERROR_INSTANTIATING = "Error instantiating ";
   private static final String SERVICES = "META-INF/services";
   private static final Logger log = Logger.getLogger("ServiceLoader");
   private final String serviceFile;
   private Class expectedType;
   private final ResourceLoader loader;
   private Set providers;

   public static ServiceLoader load(Class service) {
      return load(service, Thread.currentThread().getContextClassLoader());
   }

   public static ServiceLoader load(Class service, ClassLoader loader) {
      if (loader == null) {
         loader = service.getClassLoader();
      }

      return new ServiceLoader(service, new ClassLoaderResourceLoader(loader));
   }

   public static ServiceLoader load(Class service, ResourceLoader loader) {
      return loader == null ? load(service, service.getClassLoader()) : new ServiceLoader(service, loader);
   }

   public static ServiceLoader loadInstalled(Class service) {
      throw new UnsupportedOperationException("Not implemented");
   }

   private ServiceLoader(Class service, ResourceLoader loader) {
      this.loader = loader;
      this.serviceFile = "META-INF/services/" + service.getName();
      this.expectedType = service;
   }

   public void reload() {
      this.providers = new HashSet();
      Iterator var1 = this.loadServiceFiles().iterator();

      while(var1.hasNext()) {
         URL serviceFile = (URL)var1.next();
         this.loadServiceFile(serviceFile);
      }

   }

   private List loadServiceFiles() {
      return new ArrayList(this.loader.getResources(this.serviceFile));
   }

   @SuppressFBWarnings(
      value = {"OS_OPEN_STREAM"},
      justification = "False positive"
   )
   private void loadServiceFile(URL serviceFile) {
      InputStream is = null;

      try {
         is = serviceFile.openStream();
         BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
         String serviceClassName = null;
         int i = 0;

         while((serviceClassName = reader.readLine()) != null) {
            ++i;
            serviceClassName = this.trim(serviceClassName);
            if (serviceClassName.length() > 0) {
               this.loadService(serviceClassName, serviceFile, i);
            }
         }
      } catch (IOException var13) {
         throw new RuntimeException("Could not read services file " + serviceFile, var13);
      } finally {
         if (is != null) {
            try {
               is.close();
            } catch (IOException var12) {
               throw new RuntimeException("Could not close services file " + serviceFile, var12);
            }
         }

      }

   }

   private String trim(String line) {
      int comment = line.indexOf(35);
      if (comment > -1) {
         line = line.substring(0, comment);
      }

      return line.trim();
   }

   private void loadService(String serviceClassName, URL file, int lineNumber) {
      Class serviceClass = this.loadClass(serviceClassName);
      if (serviceClass != null) {
         Object serviceInstance = this.prepareInstance(serviceClass);
         if (serviceInstance != null) {
            this.providers.add(new FileMetadata(serviceInstance, file, lineNumber));
         }
      }
   }

   private Class loadClass(String serviceClassName) {
      Class clazz = null;
      Class serviceClass = null;

      try {
         clazz = this.loader.classForName(serviceClassName);
         serviceClass = clazz.asSubclass(this.expectedType);
      } catch (ResourceLoadingException var5) {
         log.warning("Could not load service class " + serviceClassName);
      } catch (ClassCastException var6) {
         throw new RuntimeException("Service class " + serviceClassName + " didn't implement the required interface");
      }

      return serviceClass;
   }

   private Object prepareInstance(final Class serviceClass) {
      SecurityManager securityManager = System.getSecurityManager();
      return securityManager != null ? AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            return ServiceLoader.this.createInstance(serviceClass);
         }
      }) : this.createInstance(serviceClass);
   }

   public Iterator iterator() {
      if (this.providers == null) {
         this.reload();
      }

      return this.providers.iterator();
   }

   public String toString() {
      return "Services for " + this.serviceFile;
   }

   public Stream stream() {
      return StreamSupport.stream(this.spliterator(), false);
   }

   private Object createInstance(Class serviceClass) {
      Constructor constructor = null;

      try {
         constructor = serviceClass.getDeclaredConstructor();
         constructor.setAccessible(true);
         return constructor.newInstance();
      } catch (Throwable var4) {
         throw new ServiceConfigurationError("Error instantiating :" + serviceClass.getName(), var4);
      }
   }
}
