package org.jboss.weld.resources;

public class DefaultResourceLoader extends WeldClassLoaderResourceLoader {
   public static final DefaultResourceLoader INSTANCE = new DefaultResourceLoader();

   protected DefaultResourceLoader() {
   }

   public void cleanup() {
   }

   protected ClassLoader classLoader() {
      ClassLoader tccl = Thread.currentThread().getContextClassLoader();
      return tccl == null ? super.classLoader() : tccl;
   }
}
