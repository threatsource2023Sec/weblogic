package com.bea.core.repackaged.springframework.context.annotation;

import com.bea.core.repackaged.springframework.aop.TargetSource;
import com.bea.core.repackaged.springframework.aop.framework.ProxyFactory;
import com.bea.core.repackaged.springframework.beans.TypeConverter;
import com.bea.core.repackaged.springframework.beans.factory.NoSuchBeanDefinitionException;
import com.bea.core.repackaged.springframework.beans.factory.annotation.QualifierAnnotationAutowireCandidateResolver;
import com.bea.core.repackaged.springframework.beans.factory.config.DependencyDescriptor;
import com.bea.core.repackaged.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.bea.core.repackaged.springframework.core.MethodParameter;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationUtils;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ContextAnnotationAutowireCandidateResolver extends QualifierAnnotationAutowireCandidateResolver {
   @Nullable
   public Object getLazyResolutionProxyIfNecessary(DependencyDescriptor descriptor, @Nullable String beanName) {
      return this.isLazy(descriptor) ? this.buildLazyResolutionProxy(descriptor, beanName) : null;
   }

   protected boolean isLazy(DependencyDescriptor descriptor) {
      Annotation[] var2 = descriptor.getAnnotations();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Annotation ann = var2[var4];
         Lazy lazy = (Lazy)AnnotationUtils.getAnnotation(ann, Lazy.class);
         if (lazy != null && lazy.value()) {
            return true;
         }
      }

      MethodParameter methodParam = descriptor.getMethodParameter();
      if (methodParam != null) {
         Method method = methodParam.getMethod();
         if (method == null || Void.TYPE == method.getReturnType()) {
            Lazy lazy = (Lazy)AnnotationUtils.getAnnotation(methodParam.getAnnotatedElement(), Lazy.class);
            if (lazy != null && lazy.value()) {
               return true;
            }
         }
      }

      return false;
   }

   protected Object buildLazyResolutionProxy(final DependencyDescriptor descriptor, @Nullable final String beanName) {
      Assert.state(this.getBeanFactory() instanceof DefaultListableBeanFactory, "BeanFactory needs to be a DefaultListableBeanFactory");
      final DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)this.getBeanFactory();
      TargetSource ts = new TargetSource() {
         public Class getTargetClass() {
            return descriptor.getDependencyType();
         }

         public boolean isStatic() {
            return false;
         }

         public Object getTarget() {
            Object target = beanFactory.doResolveDependency(descriptor, beanName, (Set)null, (TypeConverter)null);
            if (target == null) {
               Class type = this.getTargetClass();
               if (Map.class == type) {
                  return Collections.emptyMap();
               } else if (List.class == type) {
                  return Collections.emptyList();
               } else if (Set.class != type && Collection.class != type) {
                  throw new NoSuchBeanDefinitionException(descriptor.getResolvableType(), "Optional dependency not present for lazy injection point");
               } else {
                  return Collections.emptySet();
               }
            } else {
               return target;
            }
         }

         public void releaseTarget(Object target) {
         }
      };
      ProxyFactory pf = new ProxyFactory();
      pf.setTargetSource(ts);
      Class dependencyType = descriptor.getDependencyType();
      if (dependencyType.isInterface()) {
         pf.addInterface(dependencyType);
      }

      return pf.getProxy(beanFactory.getBeanClassLoader());
   }
}
