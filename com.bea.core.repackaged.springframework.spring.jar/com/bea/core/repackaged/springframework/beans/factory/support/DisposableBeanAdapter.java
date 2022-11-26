package com.bea.core.repackaged.springframework.beans.factory.support;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.beans.BeanUtils;
import com.bea.core.repackaged.springframework.beans.factory.DisposableBean;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanPostProcessor;
import com.bea.core.repackaged.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.CollectionUtils;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

class DisposableBeanAdapter implements DisposableBean, Runnable, Serializable {
   private static final String CLOSE_METHOD_NAME = "close";
   private static final String SHUTDOWN_METHOD_NAME = "shutdown";
   private static final Log logger = LogFactory.getLog(DisposableBeanAdapter.class);
   private final Object bean;
   private final String beanName;
   private final boolean invokeDisposableBean;
   private final boolean nonPublicAccessAllowed;
   @Nullable
   private final AccessControlContext acc;
   @Nullable
   private String destroyMethodName;
   @Nullable
   private transient Method destroyMethod;
   @Nullable
   private List beanPostProcessors;

   public DisposableBeanAdapter(Object bean, String beanName, RootBeanDefinition beanDefinition, List postProcessors, @Nullable AccessControlContext acc) {
      Assert.notNull(bean, "Disposable bean must not be null");
      this.bean = bean;
      this.beanName = beanName;
      this.invokeDisposableBean = this.bean instanceof DisposableBean && !beanDefinition.isExternallyManagedDestroyMethod("destroy");
      this.nonPublicAccessAllowed = beanDefinition.isNonPublicAccessAllowed();
      this.acc = acc;
      String destroyMethodName = this.inferDestroyMethodIfNecessary(bean, beanDefinition);
      if (destroyMethodName != null && (!this.invokeDisposableBean || !"destroy".equals(destroyMethodName)) && !beanDefinition.isExternallyManagedDestroyMethod(destroyMethodName)) {
         this.destroyMethodName = destroyMethodName;
         Method destroyMethod = this.determineDestroyMethod(destroyMethodName);
         if (destroyMethod == null) {
            if (beanDefinition.isEnforceDestroyMethod()) {
               throw new BeanDefinitionValidationException("Could not find a destroy method named '" + destroyMethodName + "' on bean with name '" + beanName + "'");
            }
         } else {
            Class[] paramTypes = destroyMethod.getParameterTypes();
            if (paramTypes.length > 1) {
               throw new BeanDefinitionValidationException("Method '" + destroyMethodName + "' of bean '" + beanName + "' has more than one parameter - not supported as destroy method");
            }

            if (paramTypes.length == 1 && Boolean.TYPE != paramTypes[0]) {
               throw new BeanDefinitionValidationException("Method '" + destroyMethodName + "' of bean '" + beanName + "' has a non-boolean parameter - not supported as destroy method");
            }

            destroyMethod = ClassUtils.getInterfaceMethodIfPossible(destroyMethod);
         }

         this.destroyMethod = destroyMethod;
      }

      this.beanPostProcessors = this.filterPostProcessors(postProcessors, bean);
   }

   public DisposableBeanAdapter(Object bean, List postProcessors, AccessControlContext acc) {
      Assert.notNull(bean, "Disposable bean must not be null");
      this.bean = bean;
      this.beanName = bean.getClass().getName();
      this.invokeDisposableBean = this.bean instanceof DisposableBean;
      this.nonPublicAccessAllowed = true;
      this.acc = acc;
      this.beanPostProcessors = this.filterPostProcessors(postProcessors, bean);
   }

   private DisposableBeanAdapter(Object bean, String beanName, boolean invokeDisposableBean, boolean nonPublicAccessAllowed, @Nullable String destroyMethodName, @Nullable List postProcessors) {
      this.bean = bean;
      this.beanName = beanName;
      this.invokeDisposableBean = invokeDisposableBean;
      this.nonPublicAccessAllowed = nonPublicAccessAllowed;
      this.acc = null;
      this.destroyMethodName = destroyMethodName;
      this.beanPostProcessors = postProcessors;
   }

   @Nullable
   private String inferDestroyMethodIfNecessary(Object bean, RootBeanDefinition beanDefinition) {
      String destroyMethodName = beanDefinition.getDestroyMethodName();
      if ("(inferred)".equals(destroyMethodName) || destroyMethodName == null && bean instanceof AutoCloseable) {
         if (!(bean instanceof DisposableBean)) {
            try {
               return bean.getClass().getMethod("close").getName();
            } catch (NoSuchMethodException var7) {
               try {
                  return bean.getClass().getMethod("shutdown").getName();
               } catch (NoSuchMethodException var6) {
               }
            }
         }

         return null;
      } else {
         return StringUtils.hasLength(destroyMethodName) ? destroyMethodName : null;
      }
   }

   @Nullable
   private List filterPostProcessors(List processors, Object bean) {
      List filteredPostProcessors = null;
      if (!CollectionUtils.isEmpty((Collection)processors)) {
         filteredPostProcessors = new ArrayList(processors.size());
         Iterator var4 = processors.iterator();

         while(var4.hasNext()) {
            BeanPostProcessor processor = (BeanPostProcessor)var4.next();
            if (processor instanceof DestructionAwareBeanPostProcessor) {
               DestructionAwareBeanPostProcessor dabpp = (DestructionAwareBeanPostProcessor)processor;
               if (dabpp.requiresDestruction(bean)) {
                  filteredPostProcessors.add(dabpp);
               }
            }
         }
      }

      return filteredPostProcessors;
   }

   public void run() {
      this.destroy();
   }

   public void destroy() {
      if (!CollectionUtils.isEmpty((Collection)this.beanPostProcessors)) {
         Iterator var1 = this.beanPostProcessors.iterator();

         while(var1.hasNext()) {
            DestructionAwareBeanPostProcessor processor = (DestructionAwareBeanPostProcessor)var1.next();
            processor.postProcessBeforeDestruction(this.bean, this.beanName);
         }
      }

      if (this.invokeDisposableBean) {
         if (logger.isTraceEnabled()) {
            logger.trace("Invoking destroy() on bean with name '" + this.beanName + "'");
         }

         try {
            if (System.getSecurityManager() != null) {
               AccessController.doPrivileged(() -> {
                  ((DisposableBean)this.bean).destroy();
                  return null;
               }, this.acc);
            } else {
               ((DisposableBean)this.bean).destroy();
            }
         } catch (Throwable var3) {
            String msg = "Invocation of destroy method failed on bean with name '" + this.beanName + "'";
            if (logger.isDebugEnabled()) {
               logger.warn(msg, var3);
            } else {
               logger.warn(msg + ": " + var3);
            }
         }
      }

      if (this.destroyMethod != null) {
         this.invokeCustomDestroyMethod(this.destroyMethod);
      } else if (this.destroyMethodName != null) {
         Method methodToInvoke = this.determineDestroyMethod(this.destroyMethodName);
         if (methodToInvoke != null) {
            this.invokeCustomDestroyMethod(ClassUtils.getInterfaceMethodIfPossible(methodToInvoke));
         }
      }

   }

   @Nullable
   private Method determineDestroyMethod(String name) {
      try {
         return System.getSecurityManager() != null ? (Method)AccessController.doPrivileged(() -> {
            return this.findDestroyMethod(name);
         }) : this.findDestroyMethod(name);
      } catch (IllegalArgumentException var3) {
         throw new BeanDefinitionValidationException("Could not find unique destroy method on bean with name '" + this.beanName + ": " + var3.getMessage());
      }
   }

   @Nullable
   private Method findDestroyMethod(String name) {
      return this.nonPublicAccessAllowed ? BeanUtils.findMethodWithMinimalParameters(this.bean.getClass(), name) : BeanUtils.findMethodWithMinimalParameters(this.bean.getClass().getMethods(), name);
   }

   private void invokeCustomDestroyMethod(Method destroyMethod) {
      int paramCount = destroyMethod.getParameterCount();
      Object[] args = new Object[paramCount];
      if (paramCount == 1) {
         args[0] = Boolean.TRUE;
      }

      if (logger.isTraceEnabled()) {
         logger.trace("Invoking destroy method '" + this.destroyMethodName + "' on bean with name '" + this.beanName + "'");
      }

      try {
         if (System.getSecurityManager() != null) {
            AccessController.doPrivileged(() -> {
               ReflectionUtils.makeAccessible(destroyMethod);
               return null;
            });

            try {
               AccessController.doPrivileged(() -> {
                  return destroyMethod.invoke(this.bean, args);
               }, this.acc);
            } catch (PrivilegedActionException var6) {
               throw (InvocationTargetException)var6.getException();
            }
         } else {
            ReflectionUtils.makeAccessible(destroyMethod);
            destroyMethod.invoke(this.bean, args);
         }
      } catch (InvocationTargetException var7) {
         String msg = "Destroy method '" + this.destroyMethodName + "' on bean with name '" + this.beanName + "' threw an exception";
         if (logger.isDebugEnabled()) {
            logger.warn(msg, var7.getTargetException());
         } else {
            logger.warn(msg + ": " + var7.getTargetException());
         }
      } catch (Throwable var8) {
         logger.warn("Failed to invoke destroy method '" + this.destroyMethodName + "' on bean with name '" + this.beanName + "'", var8);
      }

   }

   protected Object writeReplace() {
      List serializablePostProcessors = null;
      if (this.beanPostProcessors != null) {
         serializablePostProcessors = new ArrayList();
         Iterator var2 = this.beanPostProcessors.iterator();

         while(var2.hasNext()) {
            DestructionAwareBeanPostProcessor postProcessor = (DestructionAwareBeanPostProcessor)var2.next();
            if (postProcessor instanceof Serializable) {
               serializablePostProcessors.add(postProcessor);
            }
         }
      }

      return new DisposableBeanAdapter(this.bean, this.beanName, this.invokeDisposableBean, this.nonPublicAccessAllowed, this.destroyMethodName, serializablePostProcessors);
   }

   public static boolean hasDestroyMethod(Object bean, RootBeanDefinition beanDefinition) {
      if (!(bean instanceof DisposableBean) && !(bean instanceof AutoCloseable)) {
         String destroyMethodName = beanDefinition.getDestroyMethodName();
         if (!"(inferred)".equals(destroyMethodName)) {
            return StringUtils.hasLength(destroyMethodName);
         } else {
            return ClassUtils.hasMethod(bean.getClass(), "close") || ClassUtils.hasMethod(bean.getClass(), "shutdown");
         }
      } else {
         return true;
      }
   }

   public static boolean hasApplicableProcessors(Object bean, List postProcessors) {
      if (!CollectionUtils.isEmpty((Collection)postProcessors)) {
         Iterator var2 = postProcessors.iterator();

         while(var2.hasNext()) {
            BeanPostProcessor processor = (BeanPostProcessor)var2.next();
            if (processor instanceof DestructionAwareBeanPostProcessor) {
               DestructionAwareBeanPostProcessor dabpp = (DestructionAwareBeanPostProcessor)processor;
               if (dabpp.requiresDestruction(bean)) {
                  return true;
               }
            }
         }
      }

      return false;
   }
}
