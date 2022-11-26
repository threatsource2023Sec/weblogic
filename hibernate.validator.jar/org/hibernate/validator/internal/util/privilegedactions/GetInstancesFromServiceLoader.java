package org.hibernate.validator.internal.util.privilegedactions;

import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

public class GetInstancesFromServiceLoader implements PrivilegedAction {
   private final ClassLoader primaryClassLoader;
   private final Class clazz;

   private GetInstancesFromServiceLoader(ClassLoader primaryClassLoader, Class clazz) {
      this.primaryClassLoader = primaryClassLoader;
      this.clazz = clazz;
   }

   public static GetInstancesFromServiceLoader action(ClassLoader primaryClassLoader, Class serviceClass) {
      return new GetInstancesFromServiceLoader(primaryClassLoader, serviceClass);
   }

   public List run() {
      List instances = this.loadInstances(this.primaryClassLoader);
      if (instances.isEmpty() && GetInstancesFromServiceLoader.class.getClassLoader() != this.primaryClassLoader) {
         instances = this.loadInstances(GetInstancesFromServiceLoader.class.getClassLoader());
      }

      return instances;
   }

   private List loadInstances(ClassLoader classloader) {
      ServiceLoader loader = ServiceLoader.load(this.clazz, classloader);
      Iterator iterator = loader.iterator();
      List instances = new ArrayList();

      while(iterator.hasNext()) {
         try {
            instances.add(iterator.next());
         } catch (ServiceConfigurationError var6) {
         }
      }

      return instances;
   }
}
