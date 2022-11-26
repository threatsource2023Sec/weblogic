package com.bea.core.repackaged.springframework.dao;

import com.bea.core.repackaged.springframework.lang.Nullable;

public class DataAccessResourceFailureException extends NonTransientDataAccessResourceException {
   public DataAccessResourceFailureException(String msg) {
      super(msg);
   }

   public DataAccessResourceFailureException(String msg, @Nullable Throwable cause) {
      super(msg, cause);
   }
}
