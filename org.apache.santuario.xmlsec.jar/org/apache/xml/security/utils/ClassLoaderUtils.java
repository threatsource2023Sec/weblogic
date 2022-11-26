package org.apache.xml.security.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ClassLoaderUtils {
   private static final Logger LOG = LoggerFactory.getLogger(ClassLoaderUtils.class);

   private ClassLoaderUtils() {
   }

   public static URL getResource(String resourceName, Class callingClass) {
      URL url = Thread.currentThread().getContextClassLoader().getResource(resourceName);
      if (url == null && resourceName.startsWith("/")) {
         url = Thread.currentThread().getContextClassLoader().getResource(resourceName.substring(1));
      }

      ClassLoader cluClassloader = ClassLoaderUtils.class.getClassLoader();
      if (cluClassloader == null) {
         cluClassloader = ClassLoader.getSystemClassLoader();
      }

      if (url == null) {
         url = cluClassloader.getResource(resourceName);
      }

      if (url == null && resourceName.startsWith("/")) {
         url = cluClassloader.getResource(resourceName.substring(1));
      }

      if (url == null) {
         ClassLoader cl = callingClass.getClassLoader();
         if (cl != null) {
            url = cl.getResource(resourceName);
         }
      }

      if (url == null) {
         url = callingClass.getResource(resourceName);
      }

      return url == null && resourceName != null && resourceName.charAt(0) != '/' ? getResource('/' + resourceName, callingClass) : url;
   }

   public static List getResources(String resourceName, Class callingClass) {
      List ret = new ArrayList();
      Enumeration urls = new Enumeration() {
         public boolean hasMoreElements() {
            return false;
         }

         public URL nextElement() {
            return null;
         }
      };

      try {
         urls = Thread.currentThread().getContextClassLoader().getResources(resourceName);
      } catch (IOException var11) {
         LOG.debug(var11.getMessage(), var11);
      }

      if (!urls.hasMoreElements() && resourceName.startsWith("/")) {
         try {
            urls = Thread.currentThread().getContextClassLoader().getResources(resourceName.substring(1));
         } catch (IOException var10) {
            LOG.debug(var10.getMessage(), var10);
         }
      }

      ClassLoader cluClassloader = ClassLoaderUtils.class.getClassLoader();
      if (cluClassloader == null) {
         cluClassloader = ClassLoader.getSystemClassLoader();
      }

      if (!urls.hasMoreElements()) {
         try {
            urls = cluClassloader.getResources(resourceName);
         } catch (IOException var9) {
            LOG.debug(var9.getMessage(), var9);
         }
      }

      if (!urls.hasMoreElements() && resourceName.startsWith("/")) {
         try {
            urls = cluClassloader.getResources(resourceName.substring(1));
         } catch (IOException var8) {
            LOG.debug(var8.getMessage(), var8);
         }
      }

      if (!urls.hasMoreElements()) {
         ClassLoader cl = callingClass.getClassLoader();
         if (cl != null) {
            try {
               urls = cl.getResources(resourceName);
            } catch (IOException var7) {
               LOG.debug(var7.getMessage(), var7);
            }
         }
      }

      if (!urls.hasMoreElements()) {
         URL url = callingClass.getResource(resourceName);
         if (url != null) {
            ret.add(url);
         }
      }

      while(urls.hasMoreElements()) {
         ret.add((URL)urls.nextElement());
      }

      return (List)(ret.isEmpty() && resourceName != null && resourceName.charAt(0) != '/' ? getResources('/' + resourceName, callingClass) : ret);
   }

   public static InputStream getResourceAsStream(String resourceName, Class callingClass) {
      URL url = getResource(resourceName, callingClass);

      try {
         return url != null ? url.openStream() : null;
      } catch (IOException var4) {
         LOG.debug(var4.getMessage(), var4);
         return null;
      }
   }

   public static Class loadClass(String className, Class callingClass) throws ClassNotFoundException {
      try {
         ClassLoader cl = Thread.currentThread().getContextClassLoader();
         if (cl != null) {
            return cl.loadClass(className);
         }
      } catch (ClassNotFoundException var3) {
         LOG.debug(var3.getMessage(), var3);
      }

      return loadClass2(className, callingClass);
   }

   private static Class loadClass2(String className, Class callingClass) throws ClassNotFoundException {
      try {
         return Class.forName(className);
      } catch (ClassNotFoundException var5) {
         try {
            if (ClassLoaderUtils.class.getClassLoader() != null) {
               return ClassLoaderUtils.class.getClassLoader().loadClass(className);
            }
         } catch (ClassNotFoundException var4) {
            if (callingClass != null && callingClass.getClassLoader() != null) {
               return callingClass.getClassLoader().loadClass(className);
            }
         }

         LOG.debug(var5.getMessage(), var5);
         throw var5;
      }
   }
}
