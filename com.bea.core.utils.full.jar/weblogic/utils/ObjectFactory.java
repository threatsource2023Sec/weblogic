package weblogic.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Properties;

/** @deprecated */
@Deprecated
public class ObjectFactory {
   private static final boolean DEBUG = false;

   public static Object createObject(String factoryId, String fallbackClassName) throws ConfigurationError {
      return createObject(factoryId, (String)null, fallbackClassName);
   }

   public static Object createObject(String factoryId, String propertiesFilename, String fallbackClassName) throws ConfigurationError {
      debugPrintln("debug is on");
      ClassLoader cl = findClassLoader();

      String javah;
      try {
         javah = getSystemProperty(factoryId);
         if (javah != null) {
            debugPrintln("found system property, value=" + javah);
            return newInstance(javah, cl, true);
         }
      } catch (SecurityException var19) {
      }

      try {
         javah = getSystemProperty("java.home");
         String configFile = javah + File.separator + "lib" + File.separator + propertiesFilename;
         FileInputStream fis = getFileInputStream(new File(configFile));
         Properties props = new Properties();

         try {
            props.load(fis);
         } finally {
            try {
               fis.close();
            } catch (Exception var16) {
            }

         }

         String factoryClassName = props.getProperty(factoryId);
         if (factoryClassName != null) {
            debugPrintln("found in jaxp.properties, value=" + factoryClassName);
            return newInstance(factoryClassName, cl, true);
         }
      } catch (Exception var18) {
      }

      Object provider = findJarServiceProvider(factoryId);
      if (provider != null) {
         return provider;
      } else if (fallbackClassName == null) {
         throw new ConfigurationError("Provider for " + factoryId + " cannot be found", (Exception)null);
      } else {
         debugPrintln("using fallback, value=" + fallbackClassName);
         return newInstance(fallbackClassName, cl, true);
      }
   }

   private static void debugPrintln(String msg) {
   }

   public static ClassLoader findClassLoader() throws ConfigurationError {
      ClassLoader cl = getContextClassLoader();
      if (cl == null) {
         cl = ObjectFactory.class.getClassLoader();
      }

      return cl;
   }

   public static Object newInstance(String className, ClassLoader cl, boolean doFallback) throws ConfigurationError {
      try {
         Class providerClass;
         if (cl == null) {
            providerClass = Class.forName(className);
         } else {
            try {
               providerClass = cl.loadClass(className);
            } catch (ClassNotFoundException var5) {
               if (!doFallback) {
                  throw var5;
               }

               cl = ObjectFactory.class.getClassLoader();
               providerClass = cl.loadClass(className);
            }
         }

         Object instance = providerClass.newInstance();
         debugPrintln("created new instance of " + providerClass + " using ClassLoader: " + cl);
         return instance;
      } catch (ClassNotFoundException var6) {
         throw new ConfigurationError("Provider " + className + " not found", var6);
      } catch (Exception var7) {
         throw new ConfigurationError("Provider " + className + " could not be instantiated: " + var7, var7);
      }
   }

   private static Object findJarServiceProvider(String factoryId) throws ConfigurationError {
      String serviceId = "META-INF/services/" + factoryId;
      InputStream is = null;
      ClassLoader cl = getContextClassLoader();
      if (cl != null) {
         is = getResourceAsStream(cl, serviceId);
         if (is == null) {
            cl = ObjectFactory.class.getClassLoader();
            is = getResourceAsStream(cl, serviceId);
         }
      } else {
         cl = ObjectFactory.class.getClassLoader();
         is = getResourceAsStream(cl, serviceId);
      }

      if (is == null) {
         return null;
      } else {
         debugPrintln("found jar resource=" + serviceId + " using ClassLoader: " + cl);

         BufferedReader rd;
         try {
            rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
         } catch (UnsupportedEncodingException var8) {
            rd = new BufferedReader(new InputStreamReader(is));
         }

         String factoryClassName = null;

         try {
            factoryClassName = rd.readLine();
            rd.close();
         } catch (IOException var7) {
            return null;
         }

         if (factoryClassName != null && !"".equals(factoryClassName)) {
            debugPrintln("found in resource, value=" + factoryClassName);
            return newInstance(factoryClassName, cl, false);
         } else {
            return null;
         }
      }
   }

   public static ClassLoader getContextClassLoader() {
      return (ClassLoader)AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            ClassLoader cl = null;

            try {
               cl = Thread.currentThread().getContextClassLoader();
            } catch (SecurityException var3) {
            }

            return cl;
         }
      });
   }

   public static String getSystemProperty(final String propName) {
      return (String)AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            return System.getProperty(propName);
         }
      });
   }

   public static FileInputStream getFileInputStream(final File file) throws FileNotFoundException {
      try {
         return (FileInputStream)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws FileNotFoundException {
               return new FileInputStream(file);
            }
         });
      } catch (PrivilegedActionException var2) {
         throw (FileNotFoundException)var2.getException();
      }
   }

   public static InputStream getResourceAsStream(final ClassLoader cl, final String name) {
      return (InputStream)AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            InputStream ris;
            if (cl == null) {
               ris = ClassLoader.getSystemResourceAsStream(name);
            } else {
               ris = cl.getResourceAsStream(name);
            }

            return ris;
         }
      });
   }

   public static class ConfigurationError extends Error {
      private Exception exception;

      public ConfigurationError(String msg, Exception x) {
         super(msg);
         this.exception = x;
      }

      public Exception getException() {
         return this.exception;
      }
   }
}
