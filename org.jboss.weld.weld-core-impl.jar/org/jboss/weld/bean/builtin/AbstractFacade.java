package org.jboss.weld.bean.builtin;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Set;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.weld.exceptions.IllegalStateException;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.manager.BeanManagerImpl;

public abstract class AbstractFacade {
   private final BeanManagerImpl beanManager;
   private final InjectionPoint injectionPoint;
   private final CreationalContext creationalContext;

   protected static Type getFacadeType(InjectionPoint injectionPoint) {
      Type genericType = injectionPoint.getType();
      if (genericType instanceof ParameterizedType) {
         return ((ParameterizedType)genericType).getActualTypeArguments()[0];
      } else {
         throw new IllegalStateException(BeanLogger.LOG.typeParameterMustBeConcrete(injectionPoint));
      }
   }

   protected AbstractFacade(InjectionPoint injectionPoint, CreationalContext creationalContext, BeanManagerImpl beanManager) {
      this.beanManager = beanManager;
      this.injectionPoint = injectionPoint;
      this.creationalContext = creationalContext;
   }

   protected BeanManagerImpl getBeanManager() {
      return this.beanManager;
   }

   protected Set getQualifiers() {
      return this.injectionPoint.getQualifiers();
   }

   protected Type getType() {
      return getFacadeType(this.injectionPoint);
   }

   protected InjectionPoint getInjectionPoint() {
      return this.injectionPoint;
   }

   protected CreationalContext getCreationalContext() {
      return this.creationalContext;
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof AbstractFacade)) {
         return false;
      } else {
         AbstractFacade that = (AbstractFacade)obj;
         return this.getType().equals(that.getType()) && this.getQualifiers().equals(that.getQualifiers());
      }
   }

   public int hashCode() {
      int hashCode = 17;
      hashCode += this.getType().hashCode() * 5;
      hashCode += this.getQualifiers().hashCode() * 7;
      return hashCode;
   }

   protected static class AbstractFacadeSerializationProxy implements Serializable {
      private static final long serialVersionUID = -9118965837530101152L;
      private final InjectionPoint injectionPoint;
      private final CreationalContext creationalContext;
      private final BeanManagerImpl beanManager;

      protected AbstractFacadeSerializationProxy(AbstractFacade facade) {
         this.injectionPoint = facade.getInjectionPoint();
         this.beanManager = facade.getBeanManager();
         this.creationalContext = facade.getCreationalContext();
      }

      protected BeanManagerImpl getBeanManager() {
         return this.beanManager;
      }

      protected InjectionPoint getInjectionPoint() {
         return this.injectionPoint;
      }

      protected CreationalContext getCreationalContext() {
         return this.creationalContext;
      }
   }
}
