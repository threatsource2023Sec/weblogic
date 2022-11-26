package com.bea.core.repackaged.springframework.beans.factory.serviceloader;

import com.bea.core.repackaged.springframework.beans.factory.BeanClassLoaderAware;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Iterator;
import java.util.ServiceLoader;

public class ServiceFactoryBean extends AbstractServiceLoaderBasedFactoryBean implements BeanClassLoaderAware {
   protected Object getObjectToExpose(ServiceLoader serviceLoader) {
      Iterator it = serviceLoader.iterator();
      if (!it.hasNext()) {
         throw new IllegalStateException("ServiceLoader could not find service for type [" + this.getServiceType() + "]");
      } else {
         return it.next();
      }
   }

   @Nullable
   public Class getObjectType() {
      return this.getServiceType();
   }
}
