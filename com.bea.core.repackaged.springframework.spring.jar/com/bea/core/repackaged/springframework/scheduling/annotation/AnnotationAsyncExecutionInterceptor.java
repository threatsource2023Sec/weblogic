package com.bea.core.repackaged.springframework.scheduling.annotation;

import com.bea.core.repackaged.springframework.aop.interceptor.AsyncExecutionInterceptor;
import com.bea.core.repackaged.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import com.bea.core.repackaged.springframework.core.annotation.AnnotatedElementUtils;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.lang.reflect.Method;
import java.util.concurrent.Executor;

public class AnnotationAsyncExecutionInterceptor extends AsyncExecutionInterceptor {
   public AnnotationAsyncExecutionInterceptor(@Nullable Executor defaultExecutor) {
      super(defaultExecutor);
   }

   public AnnotationAsyncExecutionInterceptor(@Nullable Executor defaultExecutor, AsyncUncaughtExceptionHandler exceptionHandler) {
      super(defaultExecutor, exceptionHandler);
   }

   @Nullable
   protected String getExecutorQualifier(Method method) {
      Async async = (Async)AnnotatedElementUtils.findMergedAnnotation(method, Async.class);
      if (async == null) {
         async = (Async)AnnotatedElementUtils.findMergedAnnotation(method.getDeclaringClass(), Async.class);
      }

      return async != null ? async.value() : null;
   }
}
