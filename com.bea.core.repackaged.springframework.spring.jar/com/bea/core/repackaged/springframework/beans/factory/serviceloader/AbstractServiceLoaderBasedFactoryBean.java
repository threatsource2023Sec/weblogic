package com.bea.core.repackaged.springframework.beans.factory.serviceloader;

import com.bea.core.repackaged.springframework.beans.factory.BeanClassLoaderAware;
import com.bea.core.repackaged.springframework.beans.factory.config.AbstractFactoryBean;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import java.util.ServiceLoader;

public abstract class AbstractServiceLoaderBasedFactoryBean extends AbstractFactoryBean implements BeanClassLoaderAware {
   @Nullable
   private Class serviceType;
   @Nullable
   private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

   public void setServiceType(@Nullable Class serviceType) {
      this.serviceType = serviceType;
   }

   @Nullable
   public Class getServiceType() {
      return this.serviceType;
   }

   public void setBeanClassLoader(@Nullable ClassLoader beanClassLoader) {
      this.beanClassLoader = beanClassLoader;
   }

   protected Object createInstance() {
      Assert.notNull(this.getServiceType(), (String)"Property 'serviceType' is required");
      return this.getObjectToExpose(ServiceLoader.load(this.getServiceType(), this.beanClassLoader));
   }

   protected abstract Object getObjectToExpose(ServiceLoader var1);
}
