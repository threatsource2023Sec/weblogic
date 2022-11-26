package org.jboss.weld.bean;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.enterprise.inject.spi.BeanAttributes;
import javax.enterprise.inject.spi.Decorator;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.InjectionTargetFactory;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.collections.ImmutableSet;
import org.jboss.weld.util.reflection.Reflections;

public class SyntheticDecorator extends SyntheticClassBean implements Decorator {
   private final InjectionPoint delegate = this.identifyDelegateInjectionPoint(this.getInjectionPoints());
   private final Set decoratedTypes;

   public SyntheticDecorator(BeanAttributes attributes, Class beanClass, InjectionTargetFactory factory, BeanManagerImpl manager) {
      super(attributes, beanClass, factory, manager);
      this.decoratedTypes = ImmutableSet.copyOf(this.getDecoratedTypes(attributes.getTypes()));
   }

   protected InjectionPoint identifyDelegateInjectionPoint(Set injectionPoints) {
      InjectionPoint delegate = null;
      Iterator var3 = injectionPoints.iterator();

      while(var3.hasNext()) {
         InjectionPoint injectionPoint = (InjectionPoint)var3.next();
         if (injectionPoint.isDelegate()) {
            if (delegate != null) {
               throw BeanLogger.LOG.tooManyDelegateInjectionPoints(this.getBeanClass());
            }

            delegate = injectionPoint;
         }
      }

      if (delegate == null) {
         throw BeanLogger.LOG.noDelegateInjectionPoint(this.getBeanClass());
      } else {
         return delegate;
      }
   }

   protected Set getDecoratedTypes(Set types) {
      Set decoratedTypes = new HashSet();
      Iterator var3 = types.iterator();

      while(var3.hasNext()) {
         Type type = (Type)var3.next();
         Class rawType = Reflections.getRawType(type);
         if (rawType.isInterface() && !Serializable.class.equals(rawType)) {
            decoratedTypes.add(type);
         }
      }

      return decoratedTypes;
   }

   public Type getDelegateType() {
      return this.delegate.getType();
   }

   public Set getDelegateQualifiers() {
      return this.delegate.getQualifiers();
   }

   public Set getDecoratedTypes() {
      return this.decoratedTypes;
   }
}
