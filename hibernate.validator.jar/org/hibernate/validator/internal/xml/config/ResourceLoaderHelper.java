package org.hibernate.validator.internal.xml.config;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.security.AccessController;
import java.security.PrivilegedAction;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.internal.util.privilegedactions.GetClassLoader;

final class ResourceLoaderHelper {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());

   private ResourceLoaderHelper() {
   }

   static InputStream getResettableInputStreamForPath(String path, ClassLoader externalClassLoader) {
      String inputPath = path;
      if (path.startsWith("/")) {
         inputPath = path.substring(1);
      }

      InputStream inputStream = null;
      if (externalClassLoader != null) {
         LOG.debug("Trying to load " + path + " via user class loader");
         inputStream = externalClassLoader.getResourceAsStream(inputPath);
      }

      ClassLoader loader;
      if (inputStream == null) {
         loader = (ClassLoader)run(GetClassLoader.fromContext());
         if (loader != null) {
            LOG.debug("Trying to load " + path + " via TCCL");
            inputStream = loader.getResourceAsStream(inputPath);
         }
      }

      if (inputStream == null) {
         LOG.debug("Trying to load " + path + " via Hibernate Validator's class loader");
         loader = ResourceLoaderHelper.class.getClassLoader();
         inputStream = loader.getResourceAsStream(inputPath);
      }

      if (inputStream == null) {
         return null;
      } else {
         return (InputStream)(inputStream.markSupported() ? inputStream : new BufferedInputStream(inputStream));
      }
   }

   private static Object run(PrivilegedAction action) {
      return System.getSecurityManager() != null ? AccessController.doPrivileged(action) : action.run();
   }
}
