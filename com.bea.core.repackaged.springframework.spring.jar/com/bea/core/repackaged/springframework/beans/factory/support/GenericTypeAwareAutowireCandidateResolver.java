package com.bea.core.repackaged.springframework.beans.factory.support;

import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryAware;
import com.bea.core.repackaged.springframework.beans.factory.FactoryBean;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinitionHolder;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.config.DependencyDescriptor;
import com.bea.core.repackaged.springframework.core.ResolvableType;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import java.lang.reflect.Method;
import java.util.Properties;

public class GenericTypeAwareAutowireCandidateResolver extends SimpleAutowireCandidateResolver implements BeanFactoryAware {
   @Nullable
   private BeanFactory beanFactory;

   public void setBeanFactory(BeanFactory beanFactory) {
      this.beanFactory = beanFactory;
   }

   @Nullable
   protected final BeanFactory getBeanFactory() {
      return this.beanFactory;
   }

   public boolean isAutowireCandidate(BeanDefinitionHolder bdHolder, DependencyDescriptor descriptor) {
      return !super.isAutowireCandidate(bdHolder, descriptor) ? false : this.checkGenericTypeMatch(bdHolder, descriptor);
   }

   protected boolean checkGenericTypeMatch(BeanDefinitionHolder bdHolder, DependencyDescriptor descriptor) {
      ResolvableType dependencyType = descriptor.getResolvableType();
      if (dependencyType.getType() instanceof Class) {
         return true;
      } else {
         ResolvableType targetType = null;
         boolean cacheType = false;
         RootBeanDefinition rbd = null;
         if (bdHolder.getBeanDefinition() instanceof RootBeanDefinition) {
            rbd = (RootBeanDefinition)bdHolder.getBeanDefinition();
         }

         if (rbd != null) {
            targetType = rbd.targetType;
            if (targetType == null) {
               cacheType = true;
               targetType = this.getReturnTypeForFactoryMethod(rbd, descriptor);
               if (targetType == null) {
                  RootBeanDefinition dbd = this.getResolvedDecoratedDefinition(rbd);
                  if (dbd != null) {
                     targetType = dbd.targetType;
                     if (targetType == null) {
                        targetType = this.getReturnTypeForFactoryMethod(dbd, descriptor);
                     }
                  }
               }
            }
         }

         if (targetType == null) {
            Class beanClass;
            if (this.beanFactory != null) {
               beanClass = this.beanFactory.getType(bdHolder.getBeanName());
               if (beanClass != null) {
                  targetType = ResolvableType.forClass(ClassUtils.getUserClass(beanClass));
               }
            }

            if (targetType == null && rbd != null && rbd.hasBeanClass() && rbd.getFactoryMethodName() == null) {
               beanClass = rbd.getBeanClass();
               if (!FactoryBean.class.isAssignableFrom(beanClass)) {
                  targetType = ResolvableType.forClass(ClassUtils.getUserClass(beanClass));
               }
            }
         }

         if (targetType == null) {
            return true;
         } else {
            if (cacheType) {
               rbd.targetType = targetType;
            }

            return !descriptor.fallbackMatchAllowed() || !targetType.hasUnresolvableGenerics() && targetType.resolve() != Properties.class ? dependencyType.isAssignableFrom(targetType) : true;
         }
      }
   }

   @Nullable
   protected RootBeanDefinition getResolvedDecoratedDefinition(RootBeanDefinition rbd) {
      BeanDefinitionHolder decDef = rbd.getDecoratedDefinition();
      if (decDef != null && this.beanFactory instanceof ConfigurableListableBeanFactory) {
         ConfigurableListableBeanFactory clbf = (ConfigurableListableBeanFactory)this.beanFactory;
         if (clbf.containsBeanDefinition(decDef.getBeanName())) {
            BeanDefinition dbd = clbf.getMergedBeanDefinition(decDef.getBeanName());
            if (dbd instanceof RootBeanDefinition) {
               return (RootBeanDefinition)dbd;
            }
         }
      }

      return null;
   }

   @Nullable
   protected ResolvableType getReturnTypeForFactoryMethod(RootBeanDefinition rbd, DependencyDescriptor descriptor) {
      ResolvableType returnType = rbd.factoryMethodReturnType;
      if (returnType == null) {
         Method factoryMethod = rbd.getResolvedFactoryMethod();
         if (factoryMethod != null) {
            returnType = ResolvableType.forMethodReturnType(factoryMethod);
         }
      }

      if (returnType != null) {
         Class resolvedClass = returnType.resolve();
         if (resolvedClass != null && descriptor.getDependencyType().isAssignableFrom(resolvedClass)) {
            return returnType;
         }
      }

      return null;
   }
}
