package weblogic.servlet.jsp;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.enumerations.EmptyEnumerator;

final class JspClassLoader extends GenericClassLoader {
   final String cname;
   final String innerClassPrefix;
   private final Map cache = new HashMap(1);

   public JspClassLoader(ClassFinder cf, ClassLoader parent, String cname) {
      super(cf, parent);
      this.cname = cname;
      this.innerClassPrefix = cname + '$';
   }

   public Class loadClass(String name) throws ClassNotFoundException {
      return this.findClass(name);
   }

   public Class findClass(String name) throws ClassNotFoundException {
      if (!name.equals(this.cname) && !name.startsWith(this.innerClassPrefix)) {
         return this.getParent().loadClass(name);
      } else {
         Class clazz = (Class)this.cache.get(name);
         if (clazz == null) {
            Thread th = Thread.currentThread();
            ClassLoader currentCL = th.getContextClassLoader();

            try {
               th.setContextClassLoader(this.getParent());
               clazz = super.findClass(name);
            } finally {
               th.setContextClassLoader(currentCL);
            }

            this.cache.put(name, clazz);
         }

         return clazz;
      }
   }

   public Enumeration findResources(String resourceName) {
      return new EmptyEnumerator();
   }

   static {
      ClassLoader.registerAsParallelCapable();
   }
}
