package org.jboss.weld.injection;

import java.util.Iterator;
import java.util.List;
import javax.enterprise.inject.spi.AnnotatedConstructor;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.Decorator;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedConstructor;
import org.jboss.weld.bean.proxy.BeanInstance;
import org.jboss.weld.bean.proxy.CombinedInterceptorAndDecoratorStackMethodHandler;
import org.jboss.weld.bean.proxy.InterceptedSubclassFactory;
import org.jboss.weld.bean.proxy.ProxyFactory;
import org.jboss.weld.bean.proxy.ProxyObject;
import org.jboss.weld.bean.proxy.TargetBeanInstance;
import org.jboss.weld.manager.BeanManagerImpl;

public class ProxyClassConstructorInjectionPointWrapper extends ConstructorInjectionPoint {
   private final ConstructorInjectionPoint originalConstructorInjectionPoint;
   private final boolean decorator;
   private final int delegateInjectionPointPosition;
   private final Bean bean;
   private final String contextId;

   public ProxyClassConstructorInjectionPointWrapper(Bean declaringBean, Class declaringComponentClass, EnhancedAnnotatedConstructor weldConstructor, ConstructorInjectionPoint originalConstructorInjectionPoint, BeanManagerImpl manager) {
      super(weldConstructor, declaringBean, declaringComponentClass, InjectionPointFactory.silentInstance(), manager);
      this.contextId = manager.getContextId();
      this.decorator = declaringBean instanceof Decorator;
      this.originalConstructorInjectionPoint = originalConstructorInjectionPoint;
      this.bean = declaringBean;
      this.delegateInjectionPointPosition = this.initDelegateInjectionPointPosition();
   }

   private int initDelegateInjectionPointPosition() {
      Iterator var1 = this.getParameterInjectionPoints().iterator();

      ParameterInjectionPoint parameter;
      do {
         if (!var1.hasNext()) {
            return -1;
         }

         parameter = (ParameterInjectionPoint)var1.next();
      } while(!parameter.isDelegate());

      return parameter.getAnnotated().getPosition();
   }

   private boolean hasDelegateInjectionPoint() {
      return this.delegateInjectionPointPosition != -1;
   }

   public List getParameterInjectionPoints() {
      return this.originalConstructorInjectionPoint.getParameterInjectionPoints();
   }

   protected Object newInstance(Object[] parameterValues) {
      Object instance = super.newInstance(parameterValues);
      if (this.decorator) {
         BeanInstance beanInstance = null;
         if (this.hasDelegateInjectionPoint()) {
            Object decoratorDelegate = parameterValues[this.delegateInjectionPointPosition];
            beanInstance = new TargetBeanInstance(decoratorDelegate);
         }

         ProxyFactory.setBeanInstance(this.contextId, instance, beanInstance, this.bean);
      } else if (instance instanceof ProxyObject) {
         ((ProxyObject)instance).weld_setHandler(new CombinedInterceptorAndDecoratorStackMethodHandler());
         InterceptedSubclassFactory.setPrivateMethodHandler(instance);
      }

      return instance;
   }

   public AnnotatedConstructor getComponentConstructor() {
      return this.originalConstructorInjectionPoint.getAnnotated();
   }

   public int hashCode() {
      int prime = true;
      int result = super.hashCode();
      result = 31 * result + (this.originalConstructorInjectionPoint == null ? 0 : this.originalConstructorInjectionPoint.hashCode());
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!super.equals(obj)) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         ProxyClassConstructorInjectionPointWrapper other = (ProxyClassConstructorInjectionPointWrapper)obj;
         if (this.originalConstructorInjectionPoint == null) {
            if (other.originalConstructorInjectionPoint != null) {
               return false;
            }
         } else if (!this.originalConstructorInjectionPoint.equals(other.originalConstructorInjectionPoint)) {
            return false;
         }

         return true;
      }
   }
}
