package weblogic.jms.adapter;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Set;

public class IsolatingClassLoader extends URLClassLoader {
   private String m_name;
   private String[] m_isolatedPrefixes;
   private Set m_isolatedClassNames = new HashSet();

   public IsolatingClassLoader(String name, URL[] classpath, String[] isolated, boolean augmentClassPath) throws InvalidContextClassLoaderException {
      super(classpath);
      this.init(name, isolated, augmentClassPath);
   }

   public IsolatingClassLoader(String name, URL[] classpath, ClassLoader parent, String[] isolated, boolean augmentClassPath) throws InvalidContextClassLoaderException {
      super(classpath, parent);
      this.init(name, isolated, augmentClassPath);
   }

   private void init(String name, String[] isolated, boolean augmentClassPath) throws InvalidContextClassLoaderException {
      this.m_name = name;
      Set prefixes = new HashSet();

      for(int i = 0; i < isolated.length; ++i) {
         int index = isolated[i].indexOf(42);
         if (index >= 0) {
            prefixes.add(isolated[i].substring(0, index));
         } else {
            this.m_isolatedClassNames.add(isolated[i]);
         }
      }

      this.m_isolatedPrefixes = (String[])((String[])prefixes.toArray(new String[0]));
      if (augmentClassPath) {
         ClassLoader callerClassLoader = Thread.currentThread().getContextClassLoader();
         if (!(callerClassLoader instanceof URLClassLoader)) {
            throw new InvalidContextClassLoaderException("Caller classloader is not a URLClassLoader, can't automatically augument classpath.Its a " + callerClassLoader.getClass());
         }

         URL[] newURLs = ((URLClassLoader)callerClassLoader).getURLs();

         for(int i = 0; i < newURLs.length; ++i) {
            this.addURL(newURLs[i]);
         }
      }

   }

   protected synchronized Class loadClass(String name, boolean resolve) throws ClassNotFoundException {
      boolean isolated = this.m_isolatedClassNames.contains(name);
      if (!isolated) {
         for(int i = 0; i < this.m_isolatedPrefixes.length; ++i) {
            if (name.startsWith(this.m_isolatedPrefixes[i])) {
               isolated = true;
               break;
            }
         }
      }

      if (isolated) {
         Class c = this.findLoadedClass(name);
         if (c == null) {
            c = this.findClass(name);
         }

         if (resolve) {
            this.resolveClass(c);
         }

         return c;
      } else {
         return super.loadClass(name, resolve);
      }
   }

   public static class InvalidContextClassLoaderException extends Exception {
      public InvalidContextClassLoaderException(String message) {
         super(message);
      }
   }
}
