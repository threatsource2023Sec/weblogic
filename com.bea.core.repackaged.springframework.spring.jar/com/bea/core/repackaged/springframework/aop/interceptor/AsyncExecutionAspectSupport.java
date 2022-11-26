package com.bea.core.repackaged.springframework.aop.interceptor;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryAware;
import com.bea.core.repackaged.springframework.beans.factory.NoSuchBeanDefinitionException;
import com.bea.core.repackaged.springframework.beans.factory.NoUniqueBeanDefinitionException;
import com.bea.core.repackaged.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import com.bea.core.repackaged.springframework.core.task.AsyncListenableTaskExecutor;
import com.bea.core.repackaged.springframework.core.task.AsyncTaskExecutor;
import com.bea.core.repackaged.springframework.core.task.TaskExecutor;
import com.bea.core.repackaged.springframework.core.task.support.TaskExecutorAdapter;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import com.bea.core.repackaged.springframework.util.concurrent.ListenableFuture;
import com.bea.core.repackaged.springframework.util.function.SingletonSupplier;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.function.Supplier;

public abstract class AsyncExecutionAspectSupport implements BeanFactoryAware {
   public static final String DEFAULT_TASK_EXECUTOR_BEAN_NAME = "taskExecutor";
   protected final Log logger = LogFactory.getLog(this.getClass());
   private final Map executors = new ConcurrentHashMap(16);
   private SingletonSupplier defaultExecutor;
   private SingletonSupplier exceptionHandler;
   @Nullable
   private BeanFactory beanFactory;

   public AsyncExecutionAspectSupport(@Nullable Executor defaultExecutor) {
      this.defaultExecutor = new SingletonSupplier(defaultExecutor, () -> {
         return this.getDefaultExecutor(this.beanFactory);
      });
      this.exceptionHandler = SingletonSupplier.of(SimpleAsyncUncaughtExceptionHandler::new);
   }

   public AsyncExecutionAspectSupport(@Nullable Executor defaultExecutor, AsyncUncaughtExceptionHandler exceptionHandler) {
      this.defaultExecutor = new SingletonSupplier(defaultExecutor, () -> {
         return this.getDefaultExecutor(this.beanFactory);
      });
      this.exceptionHandler = SingletonSupplier.of((Object)exceptionHandler);
   }

   public void configure(@Nullable Supplier defaultExecutor, @Nullable Supplier exceptionHandler) {
      this.defaultExecutor = new SingletonSupplier(defaultExecutor, () -> {
         return this.getDefaultExecutor(this.beanFactory);
      });
      this.exceptionHandler = new SingletonSupplier(exceptionHandler, SimpleAsyncUncaughtExceptionHandler::new);
   }

   public void setExecutor(Executor defaultExecutor) {
      this.defaultExecutor = SingletonSupplier.of((Object)defaultExecutor);
   }

   public void setExceptionHandler(AsyncUncaughtExceptionHandler exceptionHandler) {
      this.exceptionHandler = SingletonSupplier.of((Object)exceptionHandler);
   }

   public void setBeanFactory(BeanFactory beanFactory) {
      this.beanFactory = beanFactory;
   }

   @Nullable
   protected AsyncTaskExecutor determineAsyncExecutor(Method method) {
      AsyncTaskExecutor executor = (AsyncTaskExecutor)this.executors.get(method);
      if (executor == null) {
         String qualifier = this.getExecutorQualifier(method);
         Executor targetExecutor;
         if (StringUtils.hasLength(qualifier)) {
            targetExecutor = this.findQualifiedExecutor(this.beanFactory, qualifier);
         } else {
            targetExecutor = (Executor)this.defaultExecutor.get();
         }

         if (targetExecutor == null) {
            return null;
         }

         executor = targetExecutor instanceof AsyncListenableTaskExecutor ? (AsyncListenableTaskExecutor)targetExecutor : new TaskExecutorAdapter(targetExecutor);
         this.executors.put(method, executor);
      }

      return (AsyncTaskExecutor)executor;
   }

   @Nullable
   protected abstract String getExecutorQualifier(Method var1);

   @Nullable
   protected Executor findQualifiedExecutor(@Nullable BeanFactory beanFactory, String qualifier) {
      if (beanFactory == null) {
         throw new IllegalStateException("BeanFactory must be set on " + this.getClass().getSimpleName() + " to access qualified executor '" + qualifier + "'");
      } else {
         return (Executor)BeanFactoryAnnotationUtils.qualifiedBeanOfType(beanFactory, Executor.class, qualifier);
      }
   }

   @Nullable
   protected Executor getDefaultExecutor(@Nullable BeanFactory beanFactory) {
      if (beanFactory != null) {
         try {
            return (Executor)beanFactory.getBean(TaskExecutor.class);
         } catch (NoUniqueBeanDefinitionException var6) {
            this.logger.debug("Could not find unique TaskExecutor bean", var6);

            try {
               return (Executor)beanFactory.getBean("taskExecutor", Executor.class);
            } catch (NoSuchBeanDefinitionException var4) {
               if (this.logger.isInfoEnabled()) {
                  this.logger.info("More than one TaskExecutor bean found within the context, and none is named 'taskExecutor'. Mark one of them as primary or name it 'taskExecutor' (possibly as an alias) in order to use it for async processing: " + var6.getBeanNamesFound());
               }
            }
         } catch (NoSuchBeanDefinitionException var7) {
            this.logger.debug("Could not find default TaskExecutor bean", var7);

            try {
               return (Executor)beanFactory.getBean("taskExecutor", Executor.class);
            } catch (NoSuchBeanDefinitionException var5) {
               this.logger.info("No task executor bean found for async processing: no bean of type TaskExecutor and no bean named 'taskExecutor' either");
            }
         }
      }

      return null;
   }

   @Nullable
   protected Object doSubmit(Callable task, AsyncTaskExecutor executor, Class returnType) {
      if (CompletableFuture.class.isAssignableFrom(returnType)) {
         return CompletableFuture.supplyAsync(() -> {
            try {
               return task.call();
            } catch (Throwable var2) {
               throw new CompletionException(var2);
            }
         }, executor);
      } else if (ListenableFuture.class.isAssignableFrom(returnType)) {
         return ((AsyncListenableTaskExecutor)executor).submitListenable(task);
      } else if (Future.class.isAssignableFrom(returnType)) {
         return executor.submit(task);
      } else {
         executor.submit(task);
         return null;
      }
   }

   protected void handleError(Throwable ex, Method method, Object... params) throws Exception {
      if (Future.class.isAssignableFrom(method.getReturnType())) {
         ReflectionUtils.rethrowException(ex);
      } else {
         try {
            ((AsyncUncaughtExceptionHandler)this.exceptionHandler.obtain()).handleUncaughtException(ex, method, params);
         } catch (Throwable var5) {
            this.logger.warn("Exception handler for async method '" + method.toGenericString() + "' threw unexpected exception itself", var5);
         }
      }

   }
}
