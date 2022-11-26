package com.bea.core.repackaged.springframework.beans.factory.annotation;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.factory.BeanCreationException;
import com.bea.core.repackaged.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import com.bea.core.repackaged.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import com.bea.core.repackaged.springframework.beans.factory.support.RootBeanDefinition;
import com.bea.core.repackaged.springframework.core.PriorityOrdered;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class InitDestroyAnnotationBeanPostProcessor implements DestructionAwareBeanPostProcessor, MergedBeanDefinitionPostProcessor, PriorityOrdered, Serializable {
   protected transient Log logger = LogFactory.getLog(this.getClass());
   @Nullable
   private Class initAnnotationType;
   @Nullable
   private Class destroyAnnotationType;
   private int order = Integer.MAX_VALUE;
   @Nullable
   private final transient Map lifecycleMetadataCache = new ConcurrentHashMap(256);

   public void setInitAnnotationType(Class initAnnotationType) {
      this.initAnnotationType = initAnnotationType;
   }

   public void setDestroyAnnotationType(Class destroyAnnotationType) {
      this.destroyAnnotationType = destroyAnnotationType;
   }

   public void setOrder(int order) {
      this.order = order;
   }

   public int getOrder() {
      return this.order;
   }

   public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class beanType, String beanName) {
      LifecycleMetadata metadata = this.findLifecycleMetadata(beanType);
      metadata.checkConfigMembers(beanDefinition);
   }

   public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
      LifecycleMetadata metadata = this.findLifecycleMetadata(bean.getClass());

      try {
         metadata.invokeInitMethods(bean, beanName);
         return bean;
      } catch (InvocationTargetException var5) {
         throw new BeanCreationException(beanName, "Invocation of init method failed", var5.getTargetException());
      } catch (Throwable var6) {
         throw new BeanCreationException(beanName, "Failed to invoke init method", var6);
      }
   }

   public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
      return bean;
   }

   public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
      LifecycleMetadata metadata = this.findLifecycleMetadata(bean.getClass());

      try {
         metadata.invokeDestroyMethods(bean, beanName);
      } catch (InvocationTargetException var6) {
         String msg = "Destroy method on bean with name '" + beanName + "' threw an exception";
         if (this.logger.isDebugEnabled()) {
            this.logger.warn(msg, var6.getTargetException());
         } else {
            this.logger.warn(msg + ": " + var6.getTargetException());
         }
      } catch (Throwable var7) {
         this.logger.warn("Failed to invoke destroy method on bean with name '" + beanName + "'", var7);
      }

   }

   public boolean requiresDestruction(Object bean) {
      return this.findLifecycleMetadata(bean.getClass()).hasDestroyMethods();
   }

   private LifecycleMetadata findLifecycleMetadata(Class clazz) {
      if (this.lifecycleMetadataCache == null) {
         return this.buildLifecycleMetadata(clazz);
      } else {
         LifecycleMetadata metadata = (LifecycleMetadata)this.lifecycleMetadataCache.get(clazz);
         if (metadata == null) {
            synchronized(this.lifecycleMetadataCache) {
               metadata = (LifecycleMetadata)this.lifecycleMetadataCache.get(clazz);
               if (metadata == null) {
                  metadata = this.buildLifecycleMetadata(clazz);
                  this.lifecycleMetadataCache.put(clazz, metadata);
               }

               return metadata;
            }
         } else {
            return metadata;
         }
      }
   }

   private LifecycleMetadata buildLifecycleMetadata(Class clazz) {
      List initMethods = new ArrayList();
      List destroyMethods = new ArrayList();
      Class targetClass = clazz;

      do {
         List currInitMethods = new ArrayList();
         List currDestroyMethods = new ArrayList();
         ReflectionUtils.doWithLocalMethods(targetClass, (method) -> {
            if (this.initAnnotationType != null && method.isAnnotationPresent(this.initAnnotationType)) {
               LifecycleElement element = new LifecycleElement(method);
               currInitMethods.add(element);
               if (this.logger.isTraceEnabled()) {
                  this.logger.trace("Found init method on class [" + clazz.getName() + "]: " + method);
               }
            }

            if (this.destroyAnnotationType != null && method.isAnnotationPresent(this.destroyAnnotationType)) {
               currDestroyMethods.add(new LifecycleElement(method));
               if (this.logger.isTraceEnabled()) {
                  this.logger.trace("Found destroy method on class [" + clazz.getName() + "]: " + method);
               }
            }

         });
         initMethods.addAll(0, currInitMethods);
         destroyMethods.addAll(currDestroyMethods);
         targetClass = targetClass.getSuperclass();
      } while(targetClass != null && targetClass != Object.class);

      return new LifecycleMetadata(clazz, initMethods, destroyMethods);
   }

   private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
      ois.defaultReadObject();
      this.logger = LogFactory.getLog(this.getClass());
   }

   private static class LifecycleElement {
      private final Method method;
      private final String identifier;

      public LifecycleElement(Method method) {
         if (method.getParameterCount() != 0) {
            throw new IllegalStateException("Lifecycle method annotation requires a no-arg method: " + method);
         } else {
            this.method = method;
            this.identifier = Modifier.isPrivate(method.getModifiers()) ? ClassUtils.getQualifiedMethodName(method) : method.getName();
         }
      }

      public Method getMethod() {
         return this.method;
      }

      public String getIdentifier() {
         return this.identifier;
      }

      public void invoke(Object target) throws Throwable {
         ReflectionUtils.makeAccessible(this.method);
         this.method.invoke(target, (Object[])null);
      }

      public boolean equals(Object other) {
         if (this == other) {
            return true;
         } else if (!(other instanceof LifecycleElement)) {
            return false;
         } else {
            LifecycleElement otherElement = (LifecycleElement)other;
            return this.identifier.equals(otherElement.identifier);
         }
      }

      public int hashCode() {
         return this.identifier.hashCode();
      }
   }

   private class LifecycleMetadata {
      private final Class targetClass;
      private final Collection initMethods;
      private final Collection destroyMethods;
      @Nullable
      private volatile Set checkedInitMethods;
      @Nullable
      private volatile Set checkedDestroyMethods;

      public LifecycleMetadata(Class targetClass, Collection initMethods, Collection destroyMethods) {
         this.targetClass = targetClass;
         this.initMethods = initMethods;
         this.destroyMethods = destroyMethods;
      }

      public void checkConfigMembers(RootBeanDefinition beanDefinition) {
         Set checkedInitMethods = new LinkedHashSet(this.initMethods.size());
         Iterator var3 = this.initMethods.iterator();

         while(var3.hasNext()) {
            LifecycleElement elementx = (LifecycleElement)var3.next();
            String methodIdentifierx = elementx.getIdentifier();
            if (!beanDefinition.isExternallyManagedInitMethod(methodIdentifierx)) {
               beanDefinition.registerExternallyManagedInitMethod(methodIdentifierx);
               checkedInitMethods.add(elementx);
               if (InitDestroyAnnotationBeanPostProcessor.this.logger.isTraceEnabled()) {
                  InitDestroyAnnotationBeanPostProcessor.this.logger.trace("Registered init method on class [" + this.targetClass.getName() + "]: " + elementx);
               }
            }
         }

         Set checkedDestroyMethods = new LinkedHashSet(this.destroyMethods.size());
         Iterator var8 = this.destroyMethods.iterator();

         while(var8.hasNext()) {
            LifecycleElement element = (LifecycleElement)var8.next();
            String methodIdentifier = element.getIdentifier();
            if (!beanDefinition.isExternallyManagedDestroyMethod(methodIdentifier)) {
               beanDefinition.registerExternallyManagedDestroyMethod(methodIdentifier);
               checkedDestroyMethods.add(element);
               if (InitDestroyAnnotationBeanPostProcessor.this.logger.isTraceEnabled()) {
                  InitDestroyAnnotationBeanPostProcessor.this.logger.trace("Registered destroy method on class [" + this.targetClass.getName() + "]: " + element);
               }
            }
         }

         this.checkedInitMethods = checkedInitMethods;
         this.checkedDestroyMethods = checkedDestroyMethods;
      }

      public void invokeInitMethods(Object target, String beanName) throws Throwable {
         Collection checkedInitMethods = this.checkedInitMethods;
         Collection initMethodsToIterate = checkedInitMethods != null ? checkedInitMethods : this.initMethods;
         LifecycleElement element;
         if (!((Collection)initMethodsToIterate).isEmpty()) {
            for(Iterator var5 = ((Collection)initMethodsToIterate).iterator(); var5.hasNext(); element.invoke(target)) {
               element = (LifecycleElement)var5.next();
               if (InitDestroyAnnotationBeanPostProcessor.this.logger.isTraceEnabled()) {
                  InitDestroyAnnotationBeanPostProcessor.this.logger.trace("Invoking init method on bean '" + beanName + "': " + element.getMethod());
               }
            }
         }

      }

      public void invokeDestroyMethods(Object target, String beanName) throws Throwable {
         Collection checkedDestroyMethods = this.checkedDestroyMethods;
         Collection destroyMethodsToUse = checkedDestroyMethods != null ? checkedDestroyMethods : this.destroyMethods;
         LifecycleElement element;
         if (!((Collection)destroyMethodsToUse).isEmpty()) {
            for(Iterator var5 = ((Collection)destroyMethodsToUse).iterator(); var5.hasNext(); element.invoke(target)) {
               element = (LifecycleElement)var5.next();
               if (InitDestroyAnnotationBeanPostProcessor.this.logger.isTraceEnabled()) {
                  InitDestroyAnnotationBeanPostProcessor.this.logger.trace("Invoking destroy method on bean '" + beanName + "': " + element.getMethod());
               }
            }
         }

      }

      public boolean hasDestroyMethods() {
         Collection checkedDestroyMethods = this.checkedDestroyMethods;
         Collection destroyMethodsToUse = checkedDestroyMethods != null ? checkedDestroyMethods : this.destroyMethods;
         return !((Collection)destroyMethodsToUse).isEmpty();
      }
   }
}
