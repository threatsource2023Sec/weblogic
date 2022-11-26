package weblogic.utils.classloaders;

import java.net.URL;
import java.net.URLClassLoader;

public class AddURLInterceptingClassLoader extends URLClassLoader {
   private final VisibleURLClassLoader urlLoader = new VisibleURLClassLoader(new URL[0]);

   public AddURLInterceptingClassLoader(URL[] urls, ClassLoader parent) {
      super(urls, parent);
   }

   public void addURL(URL url) {
      this.urlLoader.addURL(url);
   }

   public Class findClass(String className) throws ClassNotFoundException {
      try {
         return super.findClass(className);
      } catch (ClassNotFoundException var3) {
         return this.urlLoader.loadClass(className);
      }
   }

   private static class VisibleURLClassLoader extends URLClassLoader {
      public VisibleURLClassLoader(URL[] urls) {
         super(urls);
      }

      public void addURL(URL url) {
         super.addURL(url);
      }
   }
}
