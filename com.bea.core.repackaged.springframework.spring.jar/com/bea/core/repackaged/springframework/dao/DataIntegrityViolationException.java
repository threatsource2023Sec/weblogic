package com.bea.core.repackaged.springframework.dao;

public class DataIntegrityViolationException extends NonTransientDataAccessException {
   public DataIntegrityViolationException(String msg) {
      super(msg);
   }

   public DataIntegrityViolationException(String msg, Throwable cause) {
      super(msg, cause);
   }
}
