package org.glassfish.grizzly.http.server.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;

public class ClassLoaderUtil {
   private static final Logger LOGGER = Grizzly.logger(ClassLoaderUtil.class);

   /** @deprecated */
   public static ClassLoader createClassloader(File libDir, ClassLoader cl) throws IOException {
      URLClassLoader urlClassloader = null;
      if (libDir.exists() && libDir.isDirectory()) {
         String[] jars = libDir.list(new FilenameFilter() {
            public boolean accept(File dir, String name) {
               return name.endsWith(".jar") || name.endsWith(".zip");
            }
         });
         URL[] urls = new URL[jars.length];

         for(int i = 0; i < jars.length; ++i) {
            String path = (new File(libDir.getName() + File.separator + jars[i])).getCanonicalFile().toURI().toURL().toString();
            urls[i] = new URL(path);
         }

         urlClassloader = createClassLoaderWithSecCheck(urls, cl);
      }

      return urlClassloader;
   }

   public static URLClassLoader createURLClassLoader(String dirPath) throws IOException {
      if (!dirPath.endsWith(File.separator) && !dirPath.endsWith(".war") && !dirPath.endsWith(".jar")) {
         dirPath = dirPath + File.separator;
      }

      String separator = System.getProperty("os.name").toLowerCase().startsWith("win") ? "/" : "//";
      String path;
      URL appRoot;
      URL classesURL;
      if (dirPath == null || !dirPath.endsWith(".war") && !dirPath.endsWith(".jar")) {
         path = dirPath;
         classesURL = new URL("file://" + dirPath + "WEB-INF/classes/");
         appRoot = new URL("file://" + dirPath);
      } else {
         File file = new File(dirPath);
         appRoot = new URL("jar:file:" + separator + file.getCanonicalPath().replace('\\', '/') + "!/");
         classesURL = new URL("jar:file:" + separator + file.getCanonicalPath().replace('\\', '/') + "!/WEB-INF/classes/");
         path = ExpandJar.expand(appRoot);
      }

      String absolutePath = (new File(path)).getAbsolutePath();
      File libFiles = new File(absolutePath + File.separator + "WEB-INF" + File.separator + "lib");
      int arraySize = 4;
      URL[] urls;
      if (libFiles.exists() && libFiles.isDirectory()) {
         urls = new URL[libFiles.listFiles().length + arraySize];

         for(int i = 0; i < libFiles.listFiles().length; ++i) {
            urls[i] = new URL("jar:file:" + separator + libFiles.listFiles()[i].toString().replace('\\', '/') + "!/");
         }
      } else {
         urls = new URL[arraySize];
      }

      urls[urls.length - 1] = classesURL;
      urls[urls.length - 2] = appRoot;
      urls[urls.length - 3] = new URL("file://" + path + "/WEB-INF/classes/");
      urls[urls.length - 4] = new URL("file://" + path);
      return createClassLoaderWithSecCheck(urls, Thread.currentThread().getContextClassLoader());
   }

   public static URLClassLoader createURLClassLoader(String location, ClassLoader parent) throws IOException {
      ClassLoader loader = Thread.currentThread().getContextClassLoader();
      Thread.currentThread().setContextClassLoader(parent);

      URLClassLoader var3;
      try {
         var3 = createURLClassLoader(location);
      } finally {
         Thread.currentThread().setContextClassLoader(loader);
      }

      return var3;
   }

   public static Object load(String clazzName) {
      return load(clazzName, Thread.currentThread().getContextClassLoader());
   }

   public static Object load(String clazzName, ClassLoader classLoader) {
      try {
         Class className = Class.forName(clazzName, true, classLoader);
         return className.newInstance();
      } catch (Throwable var4) {
         LOGGER.log(Level.SEVERE, "Unable to load class " + clazzName, var4);
         return null;
      }
   }

   private static URLClassLoader createClassLoaderWithSecCheck(final URL[] urls, final ClassLoader parent) {
      return System.getSecurityManager() == null ? new URLClassLoader(urls, parent) : (URLClassLoader)AccessController.doPrivileged(new PrivilegedAction() {
         public URLClassLoader run() {
            return new URLClassLoader(urls, parent);
         }
      });
   }
}
