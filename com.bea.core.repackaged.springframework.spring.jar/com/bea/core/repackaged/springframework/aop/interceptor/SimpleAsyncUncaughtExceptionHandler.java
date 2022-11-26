package com.bea.core.repackaged.springframework.aop.interceptor;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import java.lang.reflect.Method;

public class SimpleAsyncUncaughtExceptionHandler implements AsyncUncaughtExceptionHandler {
   private static final Log logger = LogFactory.getLog(SimpleAsyncUncaughtExceptionHandler.class);

   public void handleUncaughtException(Throwable ex, Method method, Object... params) {
      if (logger.isErrorEnabled()) {
         logger.error("Unexpected exception occurred invoking async method: " + method, ex);
      }

   }
}
