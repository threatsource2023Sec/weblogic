package com.bea.core.repackaged.springframework.scheduling.annotation;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.aop.framework.autoproxy.AbstractBeanFactoryAwareAdvisingPostProcessor;
import com.bea.core.repackaged.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.function.SingletonSupplier;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

public class AsyncAnnotationBeanPostProcessor extends AbstractBeanFactoryAwareAdvisingPostProcessor {
   public static final String DEFAULT_TASK_EXECUTOR_BEAN_NAME = "taskExecutor";
   protected final Log logger = LogFactory.getLog(this.getClass());
   @Nullable
   private Supplier executor;
   @Nullable
   private Supplier exceptionHandler;
   @Nullable
   private Class asyncAnnotationType;

   public AsyncAnnotationBeanPostProcessor() {
      this.setBeforeExistingAdvisors(true);
   }

   public void configure(@Nullable Supplier executor, @Nullable Supplier exceptionHandler) {
      this.executor = executor;
      this.exceptionHandler = exceptionHandler;
   }

   public void setExecutor(Executor executor) {
      this.executor = SingletonSupplier.of((Object)executor);
   }

   public void setExceptionHandler(AsyncUncaughtExceptionHandler exceptionHandler) {
      this.exceptionHandler = SingletonSupplier.of((Object)exceptionHandler);
   }

   public void setAsyncAnnotationType(Class asyncAnnotationType) {
      Assert.notNull(asyncAnnotationType, (String)"'asyncAnnotationType' must not be null");
      this.asyncAnnotationType = asyncAnnotationType;
   }

   public void setBeanFactory(BeanFactory beanFactory) {
      super.setBeanFactory(beanFactory);
      AsyncAnnotationAdvisor advisor = new AsyncAnnotationAdvisor(this.executor, this.exceptionHandler);
      if (this.asyncAnnotationType != null) {
         advisor.setAsyncAnnotationType(this.asyncAnnotationType);
      }

      advisor.setBeanFactory(beanFactory);
      this.advisor = advisor;
   }
}
