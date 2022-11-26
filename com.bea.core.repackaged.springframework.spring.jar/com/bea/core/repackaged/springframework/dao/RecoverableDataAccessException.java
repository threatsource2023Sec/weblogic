package com.bea.core.repackaged.springframework.dao;

public class RecoverableDataAccessException extends DataAccessException {
   public RecoverableDataAccessException(String msg) {
      super(msg);
   }

   public RecoverableDataAccessException(String msg, Throwable cause) {
      super(msg, cause);
   }
}
