package org.jboss.weld.util;

import java.util.Set;
import javax.annotation.Priority;
import org.jboss.weld.bootstrap.api.BootstrapService;
import org.jboss.weld.bootstrap.api.Service;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.logging.BootstrapLogger;
import org.jboss.weld.util.reflection.Reflections;

public final class Services {
   private static final int DEFAULT_PLATFORM_PRIORITY = 4500;

   private Services() {
   }

   public static Set identifyServiceInterfaces(Class clazz, Set serviceInterfaces) {
      if (clazz != null && !Object.class.equals(clazz) && !BootstrapService.class.equals(clazz)) {
         Class[] var2 = clazz.getInterfaces();
         int var3 = var2.length;

         int var4;
         Class interfac3;
         for(var4 = 0; var4 < var3; ++var4) {
            interfac3 = var2[var4];
            if (Service.class.equals(interfac3) || BootstrapService.class.equals(interfac3)) {
               serviceInterfaces.add(Reflections.cast(clazz));
            }
         }

         var2 = clazz.getInterfaces();
         var3 = var2.length;

         for(var4 = 0; var4 < var3; ++var4) {
            interfac3 = var2[var4];
            identifyServiceInterfaces(interfac3, serviceInterfaces);
         }

         identifyServiceInterfaces(clazz.getSuperclass(), serviceInterfaces);
         return serviceInterfaces;
      } else {
         return serviceInterfaces;
      }
   }

   public static void put(ServiceRegistry registry, Class key, Service value) {
      Service previous = registry.get(key);
      if (previous == null) {
         BootstrapLogger.LOG.debugv("Installing additional service {0} ({1})", key.getName(), value.getClass());
         registry.add(key, (Service)Reflections.cast(value));
      } else if (shouldOverride(key, previous, value)) {
         BootstrapLogger.LOG.debugv("Overriding service implementation for {0}. Previous implementation {1} is replaced with {2}", key.getName(), previous.getClass().getName(), value.getClass().getName());
         registry.add(key, (Service)Reflections.cast(value));
      }

   }

   private static boolean shouldOverride(Class key, Service previous, Service next) {
      return getPriority(next) > getPriority(previous);
   }

   private static int getPriority(Service service) {
      Priority priority = (Priority)service.getClass().getAnnotation(Priority.class);
      return priority != null ? priority.value() : 4500;
   }
}
