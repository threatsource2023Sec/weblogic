package org.jboss.weld.bootstrap;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.ProcessInjectionPoint;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.resolution.Resolvable;
import org.jboss.weld.resolution.ResolvableBuilder;
import org.jboss.weld.util.reflection.Reflections;

public class WeldUnusedMetadataExtension implements Extension {
   private Set componentInjectionPoints = new HashSet();
   private Set instanceInjectionPoints = new HashSet();

   WeldUnusedMetadataExtension() {
   }

   void processInjectionPoints(@Observes ProcessInjectionPoint event) {
      if (event.getInjectionPoint().getBean() == null) {
         this.componentInjectionPoints.add(event.getInjectionPoint());
      }

      if (Instance.class.equals(Reflections.getRawType(event.getInjectionPoint().getType()))) {
         this.instanceInjectionPoints.add(event.getInjectionPoint());
      }

   }

   void clear(@Observes @Initialized(ApplicationScoped.class) Object obj) {
      this.componentInjectionPoints.clear();
      this.instanceInjectionPoints.clear();
   }

   public boolean isInjectedByEEComponent(Bean bean, BeanManagerImpl beanManager) {
      if (this.componentInjectionPoints.isEmpty()) {
         return false;
      } else {
         Iterator var3 = this.componentInjectionPoints.iterator();

         InjectionPoint injectionPoint;
         do {
            if (!var3.hasNext()) {
               return false;
            }

            injectionPoint = (InjectionPoint)var3.next();
         } while(!((Set)beanManager.getBeanResolver().resolve((new ResolvableBuilder(injectionPoint, beanManager)).create(), false)).contains(bean));

         return true;
      }
   }

   public boolean isInstanceResolvedBean(Bean bean, BeanManagerImpl beanManager) {
      if (this.instanceInjectionPoints.isEmpty()) {
         return false;
      } else {
         Iterator var3 = this.instanceInjectionPoints.iterator();

         while(var3.hasNext()) {
            InjectionPoint injectionPoint = (InjectionPoint)var3.next();
            Type facadeType = this.getFacadeType(injectionPoint);
            if (facadeType != null) {
               Resolvable resolvable = (new ResolvableBuilder(facadeType, beanManager)).addQualifiers((Collection)injectionPoint.getQualifiers()).setDeclaringBean(injectionPoint.getBean()).create();
               if (((Set)beanManager.getBeanResolver().resolve(resolvable, false)).contains(bean)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   private Type getFacadeType(InjectionPoint injectionPoint) {
      Type genericType = injectionPoint.getType();
      return genericType instanceof ParameterizedType ? ((ParameterizedType)genericType).getActualTypeArguments()[0] : null;
   }
}
