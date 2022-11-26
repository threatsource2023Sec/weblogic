package com.bea.common.engine.internal;

import com.bea.common.classloader.service.ClassLoaderService;

public class DefaultClassLoaderServiceImpl implements ClassLoaderService {
   ClassLoader classLoader = this.getClass().getClassLoader();

   public ClassLoader getClassLoader(String classLoaderName) {
      return this.classLoader;
   }
}
