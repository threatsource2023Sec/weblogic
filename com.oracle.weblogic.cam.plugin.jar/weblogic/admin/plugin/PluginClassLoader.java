package weblogic.admin.plugin;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import weblogic.admin.plugin.utils.CommonUtils;
import weblogic.admin.plugin.utils.ExtensionFilter;

public class PluginClassLoader {
   private static ClassLoader classLoader = null;
   private static final Logger _logger = Logger.getLogger(PluginClassLoader.class.getName());
   private static String[] _locations = null;

   static void setPluginJarFileLocations(String[] locations) {
      _locations = locations;
   }

   static String[] getPluginJarFileLocations() {
      return _locations;
   }

   public static synchronized ClassLoader getClassLoader() throws InstantiationException {
      if (classLoader == null) {
         classLoader = Thread.currentThread().getContextClassLoader();
         if (classLoader == null) {
            classLoader = PluginClassLoader.class.getClassLoader();
         }

         if (getPluginJarFileLocations() != null) {
            try {
               classLoader = getExtendedClassLoader(classLoader, getPluginJarFileLocations());
            } catch (InstantiationException var1) {
               if (_logger.isLoggable(Level.WARNING)) {
                  _logger.log(Level.WARNING, "Error creating a classloader to load the system component plugin jar files.", var1);
               }

               throw var1;
            }
         }
      }

      return classLoader;
   }

   private static ClassLoader getExtendedClassLoader(ClassLoader parent, String[] locations) throws InstantiationException {
      Set uriSet = new HashSet();
      String[] var3 = locations;
      int var4 = locations.length;

      int lcv;
      for(lcv = 0; lcv < var4; ++lcv) {
         String location = var3[lcv];
         File pluginJarLocation = new File(location);
         if (pluginJarLocation.exists()) {
            if (pluginJarLocation.isDirectory()) {
               findJars(pluginJarLocation, uriSet);
            } else {
               InstantiationException ie;
               try {
                  uriSet.add(pluginJarLocation.getCanonicalFile().toURI());
               } catch (MalformedURLException var10) {
                  if (_logger.isLoggable(Level.WARNING)) {
                     _logger.log(Level.WARNING, "Error parsing the plugin jar file: " + pluginJarLocation, var10);
                  }

                  ie = new InstantiationException("Error when parsing a malformed URL: " + pluginJarLocation + ", " + var10.getMessage());
                  ie.initCause(var10);
                  throw ie;
               } catch (IOException var11) {
                  if (_logger.isLoggable(Level.WARNING)) {
                     _logger.log(Level.WARNING, "Error parsing the plugin jar file: " + pluginJarLocation, var11);
                  }

                  ie = new InstantiationException(var11.getMessage());
                  ie.initCause(var11);
                  throw ie;
               }
            }
         }
      }

      if (uriSet.size() == 0) {
         return parent;
      } else {
         URI[] uris = (URI[])uriSet.toArray(new URI[uriSet.size()]);
         URL[] urls = new URL[uris.length];

         for(lcv = 0; lcv < uris.length; ++lcv) {
            try {
               urls[lcv] = uris[lcv].toURL();
            } catch (MalformedURLException var12) {
               if (_logger.isLoggable(Level.WARNING)) {
                  _logger.log(Level.WARNING, "Error parsing the plugin jar file: " + uris[lcv].toString(), var12);
               }

               InstantiationException ie = new InstantiationException(var12.getMessage());
               ie.initCause(var12);
               throw ie;
            }
         }

         return new URLClassLoader(urls, parent);
      }
   }

   private static void findJars(File directory, Set urlSet) throws InstantiationException {
      if (directory.exists() && directory.isDirectory()) {
         File[] jars = CommonUtils.find(directory, new ExtensionFilter("jar"));

         for(int i = 0; i < jars.length; ++i) {
            urlSet.add(jars[i].toURI());
         }
      }

   }

   public static Class loadClass(String className) throws Exception {
      if (className.equals("long")) {
         return Long.TYPE;
      } else if (className.equals("double")) {
         return Double.TYPE;
      } else if (className.equals("float")) {
         return Float.TYPE;
      } else if (className.equals("int")) {
         return Integer.TYPE;
      } else if (className.equals("char")) {
         return Character.TYPE;
      } else if (className.equals("short")) {
         return Short.TYPE;
      } else if (className.equals("byte")) {
         return Byte.TYPE;
      } else if (className.equals("boolean")) {
         return Boolean.TYPE;
      } else {
         return className.equals("void") ? Void.TYPE : getClassLoader().loadClass(className);
      }
   }

   public static synchronized void cleanupClassLoader() {
      if (classLoader != null) {
         classLoader = null;
      }

      if (_locations != null) {
         _locations = null;
      }

   }
}
