package org.jboss.weld.resources;

public class WeldClassLoaderResourceLoader extends AbstractClassLoaderResourceLoader {
   public static final WeldClassLoaderResourceLoader INSTANCE = new WeldClassLoaderResourceLoader();

   protected WeldClassLoaderResourceLoader() {
   }

   protected ClassLoader classLoader() {
      return this.getClass().getClassLoader();
   }

   public void cleanup() {
   }
}
