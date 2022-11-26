package org.jboss.weld.injection.producer;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.List;
import javax.enterprise.inject.spi.Bean;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedConstructor;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.injection.ConstructorInjectionPoint;
import org.jboss.weld.injection.InjectionPointFactory;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.Beans;

public class DefaultInstantiator extends AbstractInstantiator {
   private final ConstructorInjectionPoint constructor;

   public DefaultInstantiator(EnhancedAnnotatedType type, Bean bean, BeanManagerImpl manager) {
      EnhancedAnnotatedConstructor constructor = Beans.getBeanConstructor(type);
      this.constructor = InjectionPointFactory.instance().createConstructorInjectionPoint(bean, type.getJavaClass(), constructor, manager);
   }

   public ConstructorInjectionPoint getConstructorInjectionPoint() {
      return this.constructor;
   }

   public Constructor getConstructor() {
      return this.constructor == null ? null : this.constructor.getAnnotated().getJavaMember();
   }

   public List getParameterInjectionPoints() {
      return this.constructor == null ? Collections.emptyList() : this.constructor.getParameterInjectionPoints();
   }

   public String toString() {
      return "SimpleInstantiator [constructor=" + this.constructor.getMember() + "]";
   }

   public boolean hasInterceptorSupport() {
      return false;
   }

   public boolean hasDecoratorSupport() {
      return false;
   }
}
