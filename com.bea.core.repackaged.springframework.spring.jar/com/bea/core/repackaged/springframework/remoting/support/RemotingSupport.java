package com.bea.core.repackaged.springframework.remoting.support;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanClassLoaderAware;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;

public abstract class RemotingSupport implements BeanClassLoaderAware {
   protected final Log logger = LogFactory.getLog(this.getClass());
   private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

   public void setBeanClassLoader(ClassLoader classLoader) {
      this.beanClassLoader = classLoader;
   }

   protected ClassLoader getBeanClassLoader() {
      return this.beanClassLoader;
   }

   @Nullable
   protected ClassLoader overrideThreadContextClassLoader() {
      return ClassUtils.overrideThreadContextClassLoader(this.getBeanClassLoader());
   }

   protected void resetThreadContextClassLoader(@Nullable ClassLoader original) {
      if (original != null) {
         Thread.currentThread().setContextClassLoader(original);
      }

   }
}
