package com.bea.core.repackaged.springframework.dao;

import com.bea.core.repackaged.springframework.core.NestedRuntimeException;
import com.bea.core.repackaged.springframework.lang.Nullable;

public abstract class DataAccessException extends NestedRuntimeException {
   public DataAccessException(String msg) {
      super(msg);
   }

   public DataAccessException(@Nullable String msg, @Nullable Throwable cause) {
      super(msg, cause);
   }
}
