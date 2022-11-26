package com.bea.core.repackaged.springframework.scheduling.support;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ErrorHandler;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;

public abstract class TaskUtils {
   public static final ErrorHandler LOG_AND_SUPPRESS_ERROR_HANDLER = new LoggingErrorHandler();
   public static final ErrorHandler LOG_AND_PROPAGATE_ERROR_HANDLER = new PropagatingErrorHandler();

   public static DelegatingErrorHandlingRunnable decorateTaskWithErrorHandler(Runnable task, @Nullable ErrorHandler errorHandler, boolean isRepeatingTask) {
      if (task instanceof DelegatingErrorHandlingRunnable) {
         return (DelegatingErrorHandlingRunnable)task;
      } else {
         ErrorHandler eh = errorHandler != null ? errorHandler : getDefaultErrorHandler(isRepeatingTask);
         return new DelegatingErrorHandlingRunnable(task, eh);
      }
   }

   public static ErrorHandler getDefaultErrorHandler(boolean isRepeatingTask) {
      return isRepeatingTask ? LOG_AND_SUPPRESS_ERROR_HANDLER : LOG_AND_PROPAGATE_ERROR_HANDLER;
   }

   private static class PropagatingErrorHandler extends LoggingErrorHandler {
      private PropagatingErrorHandler() {
         super(null);
      }

      public void handleError(Throwable t) {
         super.handleError(t);
         ReflectionUtils.rethrowRuntimeException(t);
      }

      // $FF: synthetic method
      PropagatingErrorHandler(Object x0) {
         this();
      }
   }

   private static class LoggingErrorHandler implements ErrorHandler {
      private final Log logger;

      private LoggingErrorHandler() {
         this.logger = LogFactory.getLog(LoggingErrorHandler.class);
      }

      public void handleError(Throwable t) {
         if (this.logger.isErrorEnabled()) {
            this.logger.error("Unexpected error occurred in scheduled task.", t);
         }

      }

      // $FF: synthetic method
      LoggingErrorHandler(Object x0) {
         this();
      }
   }
}
