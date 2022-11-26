package com.bea.core.repackaged.springframework.dao;

import com.bea.core.repackaged.springframework.lang.Nullable;

public class OptimisticLockingFailureException extends ConcurrencyFailureException {
   public OptimisticLockingFailureException(String msg) {
      super(msg);
   }

   public OptimisticLockingFailureException(String msg, @Nullable Throwable cause) {
      super(msg, cause);
   }
}
