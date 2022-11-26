package com.bea.core.repackaged.springframework.scheduling.annotation;

import com.bea.core.repackaged.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.concurrent.Executor;

public interface AsyncConfigurer {
   @Nullable
   default Executor getAsyncExecutor() {
      return null;
   }

   @Nullable
   default AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
      return null;
   }
}
