package weblogic.utils.classloaders;

import java.net.URL;
import java.net.URLClassLoader;
import weblogic.utils.Classpath;
import weblogic.utils.PlatformConstants;

public class OSGIwlsURLClassLoader extends URLClassLoader {
   public OSGIwlsURLClassLoader(URL[] urls) {
      super(urls);
   }

   public OSGIwlsURLClassLoader(URL[] urls, ClassLoader parent) {
      super(urls, parent);
   }

   public String getClassPath() {
      StringBuilder sb = new StringBuilder();
      ClassLoader parent = this.getParent();
      String parentClasspath;
      if (parent instanceof GenericClassLoader) {
         parentClasspath = ((GenericClassLoader)parent).getClassPath();
      } else {
         parentClasspath = getExpanded();
      }

      if (parentClasspath != null && !parentClasspath.equals("")) {
         if (sb.length() > 0) {
            sb.append(PlatformConstants.PATH_SEP);
         }

         sb.append(parentClasspath);
      }

      return sb.toString();
   }

   private static String getExpanded() {
      ClasspathClassFinderInt cf = null;

      String var1;
      try {
         cf = new ClasspathClassFinder2.NoValidate(Classpath.get());
         var1 = cf.getNoDupExpandedClassPath();
      } finally {
         if (cf != null) {
            cf.close();
         }

      }

      return var1;
   }

   public String toString() {
      ClassLoader parent = this.getParent();
      return parent != null ? "OSGIwlsURLClassLoader(" + super.toString() + "," + this.getParent().toString() + "," + System.identityHashCode(this) + ")" : "OSGIwlsURLClassLoader(" + super.toString() + "," + System.identityHashCode(this) + ")";
   }
}
