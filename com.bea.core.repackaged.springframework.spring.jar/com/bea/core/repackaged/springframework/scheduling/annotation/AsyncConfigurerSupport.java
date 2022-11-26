package com.bea.core.repackaged.springframework.scheduling.annotation;

import com.bea.core.repackaged.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.concurrent.Executor;

public class AsyncConfigurerSupport implements AsyncConfigurer {
   public Executor getAsyncExecutor() {
      return null;
   }

   @Nullable
   public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
      return null;
   }
}
