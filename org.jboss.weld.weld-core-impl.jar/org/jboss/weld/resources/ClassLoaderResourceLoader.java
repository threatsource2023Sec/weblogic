package org.jboss.weld.resources;

public class ClassLoaderResourceLoader extends AbstractClassLoaderResourceLoader {
   private ClassLoader classLoader;

   public ClassLoaderResourceLoader(ClassLoader classLoader) {
      this.classLoader = classLoader;
   }

   protected ClassLoader classLoader() {
      return this.classLoader;
   }

   public void cleanup() {
      this.classLoader = null;
   }
}
