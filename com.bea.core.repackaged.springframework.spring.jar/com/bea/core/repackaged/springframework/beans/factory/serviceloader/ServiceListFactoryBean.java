package com.bea.core.repackaged.springframework.beans.factory.serviceloader;

import com.bea.core.repackaged.springframework.beans.factory.BeanClassLoaderAware;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;

public class ServiceListFactoryBean extends AbstractServiceLoaderBasedFactoryBean implements BeanClassLoaderAware {
   protected Object getObjectToExpose(ServiceLoader serviceLoader) {
      List result = new LinkedList();
      Iterator var3 = serviceLoader.iterator();

      while(var3.hasNext()) {
         Object loaderObject = var3.next();
         result.add(loaderObject);
      }

      return result;
   }

   public Class getObjectType() {
      return List.class;
   }
}
