package com.bea.core.repackaged.aspectj.apache.bcel.util;

public class DefaultClassLoaderReference implements ClassLoaderReference {
   private ClassLoader loader;

   public DefaultClassLoaderReference(ClassLoader classLoader) {
      this.loader = classLoader;
   }

   public ClassLoader getClassLoader() {
      return this.loader;
   }
}
