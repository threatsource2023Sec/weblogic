package com.bea.core.repackaged.springframework.beans.factory.serviceloader;

import com.bea.core.repackaged.springframework.beans.factory.BeanClassLoaderAware;
import java.util.ServiceLoader;

public class ServiceLoaderFactoryBean extends AbstractServiceLoaderBasedFactoryBean implements BeanClassLoaderAware {
   protected Object getObjectToExpose(ServiceLoader serviceLoader) {
      return serviceLoader;
   }

   public Class getObjectType() {
      return ServiceLoader.class;
   }
}
