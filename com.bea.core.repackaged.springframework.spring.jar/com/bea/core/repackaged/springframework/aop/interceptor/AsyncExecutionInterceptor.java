package com.bea.core.repackaged.springframework.aop.interceptor;

import com.bea.core.repackaged.aopalliance.intercept.MethodInterceptor;
import com.bea.core.repackaged.aopalliance.intercept.MethodInvocation;
import com.bea.core.repackaged.springframework.aop.support.AopUtils;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.core.BridgeMethodResolver;
import com.bea.core.repackaged.springframework.core.Ordered;
import com.bea.core.repackaged.springframework.core.task.AsyncTaskExecutor;
import com.bea.core.repackaged.springframework.core.task.SimpleAsyncTaskExecutor;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

public class AsyncExecutionInterceptor extends AsyncExecutionAspectSupport implements MethodInterceptor, Ordered {
   public AsyncExecutionInterceptor(@Nullable Executor defaultExecutor) {
      super(defaultExecutor);
   }

   public AsyncExecutionInterceptor(@Nullable Executor defaultExecutor, AsyncUncaughtExceptionHandler exceptionHandler) {
      super(defaultExecutor, exceptionHandler);
   }

   @Nullable
   public Object invoke(MethodInvocation invocation) throws Throwable {
      Class targetClass = invocation.getThis() != null ? AopUtils.getTargetClass(invocation.getThis()) : null;
      Method specificMethod = ClassUtils.getMostSpecificMethod(invocation.getMethod(), targetClass);
      Method userDeclaredMethod = BridgeMethodResolver.findBridgedMethod(specificMethod);
      AsyncTaskExecutor executor = this.determineAsyncExecutor(userDeclaredMethod);
      if (executor == null) {
         throw new IllegalStateException("No executor specified and no default executor set on AsyncExecutionInterceptor either");
      } else {
         Callable task = () -> {
            try {
               Object result = invocation.proceed();
               if (result instanceof Future) {
                  return ((Future)result).get();
               }
            } catch (ExecutionException var4) {
               this.handleError(var4.getCause(), userDeclaredMethod, invocation.getArguments());
            } catch (Throwable var5) {
               this.handleError(var5, userDeclaredMethod, invocation.getArguments());
            }

            return null;
         };
         return this.doSubmit(task, executor, invocation.getMethod().getReturnType());
      }
   }

   @Nullable
   protected String getExecutorQualifier(Method method) {
      return null;
   }

   @Nullable
   protected Executor getDefaultExecutor(@Nullable BeanFactory beanFactory) {
      Executor defaultExecutor = super.getDefaultExecutor(beanFactory);
      return (Executor)(defaultExecutor != null ? defaultExecutor : new SimpleAsyncTaskExecutor());
   }

   public int getOrder() {
      return Integer.MIN_VALUE;
   }
}
