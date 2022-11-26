package com.bea.core.repackaged.springframework.dao;

import com.bea.core.repackaged.springframework.lang.Nullable;

public abstract class TransientDataAccessException extends DataAccessException {
   public TransientDataAccessException(String msg) {
      super(msg);
   }

   public TransientDataAccessException(String msg, @Nullable Throwable cause) {
      super(msg, cause);
   }
}
