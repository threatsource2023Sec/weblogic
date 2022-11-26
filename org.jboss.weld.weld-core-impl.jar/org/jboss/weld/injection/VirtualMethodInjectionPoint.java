package org.jboss.weld.injection;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.enterprise.inject.spi.Bean;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedMethod;
import org.jboss.weld.bean.AbstractClassBean;
import org.jboss.weld.bean.AbstractProducerBean;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.collections.ImmutableMap;
import org.jboss.weld.util.reflection.Reflections;

class VirtualMethodInjectionPoint extends StaticMethodInjectionPoint {
   private volatile Map methods;

   VirtualMethodInjectionPoint(MethodInjectionPoint.MethodInjectionPointType methodInjectionPointType, EnhancedAnnotatedMethod enhancedMethod, Bean declaringBean, Class declaringComponentClass, Set specialParameterMarkers, InjectionPointFactory factory, BeanManagerImpl manager) {
      super(methodInjectionPointType, enhancedMethod, declaringBean, declaringComponentClass, specialParameterMarkers, factory, manager);
      this.methods = Collections.singletonMap(this.getAnnotated().getJavaMember().getDeclaringClass(), this.accessibleMethod);
   }

   protected Method getMethod(Object receiver) throws NoSuchMethodException {
      Map methods = this.methods;
      Method method = (Method)this.methods.get(receiver.getClass());
      if (method == null) {
         Method delegate = this.getAnnotated().getJavaMember();
         if (!this.hasDecorators() && !MethodInjectionPoint.MethodInjectionPointType.INITIALIZER.equals(this.type) || !Reflections.isPrivate(delegate) && (!Reflections.isPackagePrivate(delegate.getModifiers()) || Objects.equals(delegate.getDeclaringClass().getPackage(), receiver.getClass().getPackage()))) {
            method = SecurityActions.lookupMethod(receiver.getClass(), delegate.getName(), delegate.getParameterTypes());
            SecurityActions.ensureAccessible(method);
         } else {
            method = this.accessibleMethod;
         }

         Map newMethods = ImmutableMap.builder().putAll(methods).put(receiver.getClass(), method).build();
         this.methods = newMethods;
      }

      return method;
   }

   private boolean hasDecorators() {
      if (this.getBean() instanceof AbstractClassBean) {
         return ((AbstractClassBean)this.getBean()).hasDecorators();
      } else {
         return this.getBean() instanceof AbstractProducerBean ? ((AbstractProducerBean)this.getBean()).getDeclaringBean().hasDecorators() : false;
      }
   }
}
