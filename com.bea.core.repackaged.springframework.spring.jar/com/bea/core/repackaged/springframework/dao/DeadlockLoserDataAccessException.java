package com.bea.core.repackaged.springframework.dao;

public class DeadlockLoserDataAccessException extends PessimisticLockingFailureException {
   public DeadlockLoserDataAccessException(String msg, Throwable cause) {
      super(msg, cause);
   }
}
