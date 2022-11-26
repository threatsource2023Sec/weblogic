package weblogic.descriptor;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Set;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.FileUtils;
import weblogic.utils.StringUtils;
import weblogic.utils.io.ExtensionFilter;

public class DescriptorClassLoader {
   private static DebugLogger debug = DebugLogger.getDebugLogger("DebugDescriptor");
   private static final String NAME_OF_TYPES_DIRECTORY = "mbeantypes";
   private static final String NAME_OF_SCHEMA_DIRECTORY = "schema";
   private static String ALTERNATE_TYPES_DIRECTORY = System.getProperty("weblogic.alternateTypesDirectory", (String)null);
   private static ClassLoader classLoader = null;
   private static boolean includeSIP = false;

   public static synchronized ClassLoader getClassLoader() {
      if (classLoader == null) {
         classLoader = Thread.currentThread().getContextClassLoader();
         if (classLoader == null) {
            classLoader = DescriptorClassLoader.class.getClassLoader();
         }

         classLoader = getExtendedClassLoader(classLoader);
      } else {
         ClassLoader ccl = Thread.currentThread().getContextClassLoader();
         if (isChildOfDescriptorClassLoader(ccl)) {
            return ccl;
         }
      }

      return classLoader;
   }

   private static final boolean isChildOfDescriptorClassLoader(ClassLoader cl) {
      boolean isChild = false;
      if (cl != null) {
         ClassLoader current = cl;

         for(ClassLoader systemCl = ClassLoader.getSystemClassLoader(); current != systemCl && current != null; current = current.getParent()) {
            if (current == classLoader) {
               isChild = true;
               break;
            }
         }
      }

      return isChild;
   }

   public static final Class loadClass(String className) throws ClassNotFoundException {
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

   public static void setIncludeSIP(boolean includeSIPJar) {
      includeSIP = includeSIPJar;
   }

   private static void findJars(File directory, Set uriSet) {
      if (directory.exists() && directory.isDirectory()) {
         File[] jars = FileUtils.find(directory, new ExtensionFilter("jar"));

         for(int i = 0; i < jars.length; ++i) {
            uriSet.add(jars[i].toURI());
         }
      }

   }

   private static boolean isWebLogicJarInClassPath(URLClassLoader loader) {
      URL[] urls = loader.getURLs();

      for(int i = 0; i < urls.length; ++i) {
         URL url = urls[i];
         if (url.getProtocol().equals("file")) {
            File file = new File(url.getFile());
            if ("weblogic.jar".equals(file.getName()) && file.exists()) {
               return true;
            }
         }
      }

      return false;
   }

   private static File getHomeWithReflection() {
      try {
         Class homeClass = Class.forName("weblogic.Home");
         Method getFileMethod = homeClass.getMethod("getFile", (Class[])null);
         return (File)getFileMethod.invoke((Object)null, (Object[])null);
      } catch (ClassNotFoundException var2) {
         return null;
      } catch (NoSuchMethodException var3) {
         return null;
      } catch (IllegalArgumentException var4) {
         return null;
      } catch (IllegalAccessException var5) {
         return null;
      } catch (InvocationTargetException var6) {
         return null;
      }
   }

   private static ClassLoader getExtendedClassLoader(ClassLoader parent) {
      File libDir = null;
      File homeFile = null;

      try {
         homeFile = getHomeWithReflection();
         if (homeFile == null) {
            return parent;
         }

         libDir = new File(homeFile, "lib");
      } catch (Exception var13) {
         return parent;
      }

      Set uriSet = new HashSet();
      File securityDir = new File(libDir, "mbeantypes");
      findJars(securityDir, uriSet);
      if (debug.isDebugEnabled() && uriSet.size() == 0) {
         debug.debug("WARNING: no jars found in mbeantypes directory.");
      }

      if (ALTERNATE_TYPES_DIRECTORY != null) {
         String[] dirs = StringUtils.splitCompletely(ALTERNATE_TYPES_DIRECTORY, ",");

         for(int i = 0; i < dirs.length; ++i) {
            File altDir = new File(dirs[i]);
            findJars(altDir, uriSet);
         }
      }

      File schemaDir = new File(libDir, "schema");
      boolean wlsJarFnd = false;

      for(ClassLoader cls = parent; !wlsJarFnd && cls != null && cls instanceof URLClassLoader; cls = cls.getParent()) {
         if (isWebLogicJarInClassPath((URLClassLoader)cls)) {
            wlsJarFnd = true;
         }
      }

      if (!wlsJarFnd) {
         findJars(schemaDir, uriSet);
      }

      if (includeSIP) {
         File sipLib = new File(homeFile, ".." + File.separator + "sip" + File.separator + "server" + File.separator + "lib" + File.separator + "weblogic_sip.jar");
         uriSet.add(sipLib.toURI());
      }

      if (uriSet.size() == 0) {
         return parent;
      } else {
         URI[] uris = (URI[])((URI[])uriSet.toArray(new URI[uriSet.size()]));
         if (debug.isDebugEnabled()) {
            for(int i = 0; i < uris.length; ++i) {
               debug.debug("Descriptor Class loader URL: " + uris[i]);
            }
         }

         URL[] urls = new URL[uris.length];

         for(int lcv = 0; lcv < uris.length; ++lcv) {
            try {
               urls[lcv] = uris[lcv].toURL();
            } catch (MalformedURLException var12) {
            }
         }

         return new URLClassLoader(urls, parent);
      }
   }
}
