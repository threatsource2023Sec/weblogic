package com.bea.core.repackaged.springframework.dao;

public class QueryTimeoutException extends TransientDataAccessException {
   public QueryTimeoutException(String msg) {
      super(msg);
   }

   public QueryTimeoutException(String msg, Throwable cause) {
      super(msg, cause);
   }
}
