package com.bea.core.repackaged.springframework.dao;

import com.bea.core.repackaged.springframework.lang.Nullable;

public class DataRetrievalFailureException extends NonTransientDataAccessException {
   public DataRetrievalFailureException(String msg) {
      super(msg);
   }

   public DataRetrievalFailureException(String msg, @Nullable Throwable cause) {
      super(msg, cause);
   }
}
