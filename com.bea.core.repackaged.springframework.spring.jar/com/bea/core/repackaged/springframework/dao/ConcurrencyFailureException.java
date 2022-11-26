package com.bea.core.repackaged.springframework.dao;

import com.bea.core.repackaged.springframework.lang.Nullable;

public class ConcurrencyFailureException extends TransientDataAccessException {
   public ConcurrencyFailureException(String msg) {
      super(msg);
   }

   public ConcurrencyFailureException(String msg, @Nullable Throwable cause) {
      super(msg, cause);
   }
}
