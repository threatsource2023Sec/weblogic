package com.bea.core.repackaged.springframework.scheduling.support;

import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ErrorHandler;
import java.lang.reflect.UndeclaredThrowableException;

public class DelegatingErrorHandlingRunnable implements Runnable {
   private final Runnable delegate;
   private final ErrorHandler errorHandler;

   public DelegatingErrorHandlingRunnable(Runnable delegate, ErrorHandler errorHandler) {
      Assert.notNull(delegate, (String)"Delegate must not be null");
      Assert.notNull(errorHandler, (String)"ErrorHandler must not be null");
      this.delegate = delegate;
      this.errorHandler = errorHandler;
   }

   public void run() {
      try {
         this.delegate.run();
      } catch (UndeclaredThrowableException var2) {
         this.errorHandler.handleError(var2.getUndeclaredThrowable());
      } catch (Throwable var3) {
         this.errorHandler.handleError(var3);
      }

   }

   public String toString() {
      return "DelegatingErrorHandlingRunnable for " + this.delegate;
   }
}
