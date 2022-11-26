package com.bea.core.repackaged.springframework.dao;

import com.bea.core.repackaged.springframework.lang.Nullable;

public abstract class UncategorizedDataAccessException extends NonTransientDataAccessException {
   public UncategorizedDataAccessException(@Nullable String msg, @Nullable Throwable cause) {
      super(msg, cause);
   }
}
