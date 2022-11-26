package com.bea.core.repackaged.springframework.dao;

public class InvalidDataAccessApiUsageException extends NonTransientDataAccessException {
   public InvalidDataAccessApiUsageException(String msg) {
      super(msg);
   }

   public InvalidDataAccessApiUsageException(String msg, Throwable cause) {
      super(msg, cause);
   }
}
