package com.bea.core.repackaged.springframework.dao;

import com.bea.core.repackaged.springframework.lang.Nullable;

public abstract class NonTransientDataAccessException extends DataAccessException {
   public NonTransientDataAccessException(String msg) {
      super(msg);
   }

   public NonTransientDataAccessException(@Nullable String msg, @Nullable Throwable cause) {
      super(msg, cause);
   }
}
