package com.bea.core.repackaged.springframework.dao;

public class CannotAcquireLockException extends PessimisticLockingFailureException {
   public CannotAcquireLockException(String msg) {
      super(msg);
   }

   public CannotAcquireLockException(String msg, Throwable cause) {
      super(msg, cause);
   }
}
