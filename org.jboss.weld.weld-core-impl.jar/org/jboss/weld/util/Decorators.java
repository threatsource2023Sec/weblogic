package org.jboss.weld.util;

import java.io.Serializable;
import java.lang.reflect.Member;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.Decorator;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.PassivationCapable;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedMethod;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.annotated.enhanced.MethodSignature;
import org.jboss.weld.annotated.runtime.InvokableAnnotatedMethod;
import org.jboss.weld.bean.CustomDecoratorWrapper;
import org.jboss.weld.bean.DecoratorImpl;
import org.jboss.weld.bean.WeldDecorator;
import org.jboss.weld.bean.proxy.DecorationHelper;
import org.jboss.weld.bean.proxy.TargetBeanInstance;
import org.jboss.weld.exceptions.WeldException;
import org.jboss.weld.injection.attributes.WeldInjectionPointAttributes;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.resources.ClassTransformer;
import org.jboss.weld.serialization.spi.ContextualStore;
import org.jboss.weld.util.collections.ImmutableSet;
import org.jboss.weld.util.reflection.Formats;
import org.jboss.weld.util.reflection.HierarchyDiscovery;
import org.jboss.weld.util.reflection.Reflections;

public class Decorators {
   private Decorators() {
   }

   public static Set getDecoratorMethods(BeanManagerImpl beanManager, WeldDecorator decorator) {
      ImmutableSet.Builder builder = ImmutableSet.builder();
      Iterator var3 = decorator.getDecoratedTypes().iterator();

      while(var3.hasNext()) {
         Type type = (Type)var3.next();
         EnhancedAnnotatedType weldClass = getEnhancedAnnotatedTypeOfDecoratedType(beanManager, type);
         Iterator var6 = weldClass.getDeclaredEnhancedMethods().iterator();

         while(var6.hasNext()) {
            EnhancedAnnotatedMethod method = (EnhancedAnnotatedMethod)var6.next();
            if (decorator.getEnhancedAnnotated().getEnhancedMethod(method.getSignature()) != null) {
               builder.add(InvokableAnnotatedMethod.of(method.slim()));
            }
         }
      }

      return builder.build();
   }

   private static EnhancedAnnotatedType getEnhancedAnnotatedTypeOfDecoratedType(BeanManagerImpl beanManager, Type type) {
      if (type instanceof Class) {
         return beanManager.createEnhancedAnnotatedType((Class)type);
      } else if (type instanceof ParameterizedType && ((ParameterizedType)type).getRawType() instanceof Class) {
         return beanManager.createEnhancedAnnotatedType((Class)((ParameterizedType)type).getRawType());
      } else {
         throw BeanLogger.LOG.unableToProcessDecoratedType(type);
      }
   }

   public static WeldInjectionPointAttributes findDelegateInjectionPoint(AnnotatedType type, Iterable injectionPoints) {
      WeldInjectionPointAttributes result = null;
      Iterator var3 = injectionPoints.iterator();

      while(var3.hasNext()) {
         InjectionPoint injectionPoint = (InjectionPoint)var3.next();
         if (injectionPoint.isDelegate()) {
            if (result != null) {
               throw BeanLogger.LOG.tooManyDelegateInjectionPoints(type);
            }

            result = InjectionPoints.getWeldInjectionPoint(injectionPoint);
         }
      }

      if (result == null) {
         throw BeanLogger.LOG.noDelegateInjectionPoint(type);
      } else {
         return result;
      }
   }

   public static Object getOuterDelegate(Bean bean, Object instance, CreationalContext creationalContext, Class proxyClass, InjectionPoint originalInjectionPoint, BeanManagerImpl manager, List decorators) {
      TargetBeanInstance beanInstance = new TargetBeanInstance(bean, instance);
      DecorationHelper decorationHelper = new DecorationHelper(beanInstance, bean, proxyClass, manager, (ContextualStore)manager.getServices().get(ContextualStore.class), decorators);
      DecorationHelper.push(decorationHelper);

      Object var10;
      try {
         Object outerDelegate = decorationHelper.getNextDelegate(originalInjectionPoint, creationalContext);
         if (outerDelegate == null) {
            throw new WeldException(BeanLogger.LOG.proxyInstantiationFailed(bean));
         }

         var10 = outerDelegate;
      } finally {
         DecorationHelper.pop();
      }

      return var10;
   }

   public static void checkDelegateType(Decorator decorator) {
      Set types = (new HierarchyDiscovery(decorator.getDelegateType())).getTypeClosure();
      Iterator var2 = decorator.getDecoratedTypes().iterator();

      Type decoratedType;
      do {
         if (!var2.hasNext()) {
            return;
         }

         decoratedType = (Type)var2.next();
      } while(types.contains(decoratedType));

      throw BeanLogger.LOG.delegateMustSupportEveryDecoratedType(decoratedType, decorator);
   }

   public static void checkAbstractMethods(Set decoratedTypes, EnhancedAnnotatedType type, BeanManagerImpl beanManager) {
      if (decoratedTypes == null) {
         decoratedTypes = new HashSet(type.getInterfaceClosure());
         ((Set)decoratedTypes).remove(Serializable.class);
      }

      Set signatures = new HashSet();
      Iterator var4 = ((Set)decoratedTypes).iterator();

      while(var4.hasNext()) {
         Type decoratedType = (Type)var4.next();
         Iterator var6 = ClassTransformer.instance(beanManager).getEnhancedAnnotatedType(Reflections.getRawType(decoratedType), beanManager.getId()).getEnhancedMethods().iterator();

         while(var6.hasNext()) {
            EnhancedAnnotatedMethod method = (EnhancedAnnotatedMethod)var6.next();
            signatures.add(method.getSignature());
         }
      }

      var4 = type.getEnhancedMethods().iterator();

      while(var4.hasNext()) {
         EnhancedAnnotatedMethod method = (EnhancedAnnotatedMethod)var4.next();
         if (Reflections.isAbstract(method.getJavaMember())) {
            MethodSignature methodSignature = method.getSignature();
            if (!signatures.contains(methodSignature)) {
               throw BeanLogger.LOG.abstractMethodMustMatchDecoratedType(method, Formats.formatAsStackTraceElement((Member)method.getJavaMember()));
            }
         }
      }

   }

   public static boolean isPassivationCapable(Decorator decorator) {
      if (decorator instanceof CustomDecoratorWrapper) {
         decorator = ((CustomDecoratorWrapper)Reflections.cast(decorator)).delegate();
      }

      if (decorator instanceof DecoratorImpl) {
         DecoratorImpl weldDecorator = (DecoratorImpl)decorator;
         return weldDecorator.getEnhancedAnnotated().isSerializable();
      } else {
         return decorator instanceof PassivationCapable;
      }
   }
}
