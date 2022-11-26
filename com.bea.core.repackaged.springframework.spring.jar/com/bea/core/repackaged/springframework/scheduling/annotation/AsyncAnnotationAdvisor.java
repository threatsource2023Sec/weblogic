package com.bea.core.repackaged.springframework.scheduling.annotation;

import com.bea.core.repackaged.aopalliance.aop.Advice;
import com.bea.core.repackaged.springframework.aop.Pointcut;
import com.bea.core.repackaged.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import com.bea.core.repackaged.springframework.aop.support.AbstractPointcutAdvisor;
import com.bea.core.repackaged.springframework.aop.support.ComposablePointcut;
import com.bea.core.repackaged.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryAware;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.function.SingletonSupplier;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

public class AsyncAnnotationAdvisor extends AbstractPointcutAdvisor implements BeanFactoryAware {
   private Advice advice;
   private Pointcut pointcut;

   public AsyncAnnotationAdvisor() {
      this((Supplier)null, (Supplier)null);
   }

   public AsyncAnnotationAdvisor(@Nullable Executor executor, @Nullable AsyncUncaughtExceptionHandler exceptionHandler) {
      this((Supplier)SingletonSupplier.ofNullable((Object)executor), (Supplier)SingletonSupplier.ofNullable((Object)exceptionHandler));
   }

   public AsyncAnnotationAdvisor(@Nullable Supplier executor, @Nullable Supplier exceptionHandler) {
      Set asyncAnnotationTypes = new LinkedHashSet(2);
      asyncAnnotationTypes.add(Async.class);

      try {
         asyncAnnotationTypes.add(ClassUtils.forName("javax.ejb.Asynchronous", AsyncAnnotationAdvisor.class.getClassLoader()));
      } catch (ClassNotFoundException var5) {
      }

      this.advice = this.buildAdvice(executor, exceptionHandler);
      this.pointcut = this.buildPointcut(asyncAnnotationTypes);
   }

   public void setAsyncAnnotationType(Class asyncAnnotationType) {
      Assert.notNull(asyncAnnotationType, (String)"'asyncAnnotationType' must not be null");
      Set asyncAnnotationTypes = new HashSet();
      asyncAnnotationTypes.add(asyncAnnotationType);
      this.pointcut = this.buildPointcut(asyncAnnotationTypes);
   }

   public void setBeanFactory(BeanFactory beanFactory) {
      if (this.advice instanceof BeanFactoryAware) {
         ((BeanFactoryAware)this.advice).setBeanFactory(beanFactory);
      }

   }

   public Advice getAdvice() {
      return this.advice;
   }

   public Pointcut getPointcut() {
      return this.pointcut;
   }

   protected Advice buildAdvice(@Nullable Supplier executor, @Nullable Supplier exceptionHandler) {
      AnnotationAsyncExecutionInterceptor interceptor = new AnnotationAsyncExecutionInterceptor((Executor)null);
      interceptor.configure(executor, exceptionHandler);
      return interceptor;
   }

   protected Pointcut buildPointcut(Set asyncAnnotationTypes) {
      ComposablePointcut result = null;

      AnnotationMatchingPointcut mpc;
      for(Iterator var3 = asyncAnnotationTypes.iterator(); var3.hasNext(); result = result.union((Pointcut)mpc)) {
         Class asyncAnnotationType = (Class)var3.next();
         Pointcut cpc = new AnnotationMatchingPointcut(asyncAnnotationType, true);
         mpc = new AnnotationMatchingPointcut((Class)null, asyncAnnotationType, true);
         if (result == null) {
            result = new ComposablePointcut(cpc);
         } else {
            result.union((Pointcut)cpc);
         }
      }

      return (Pointcut)(result != null ? result : Pointcut.TRUE);
   }
}
