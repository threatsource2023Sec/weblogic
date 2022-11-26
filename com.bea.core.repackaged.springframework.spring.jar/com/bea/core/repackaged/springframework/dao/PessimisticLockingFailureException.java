package com.bea.core.repackaged.springframework.dao;

public class PessimisticLockingFailureException extends ConcurrencyFailureException {
   public PessimisticLockingFailureException(String msg) {
      super(msg);
   }

   public PessimisticLockingFailureException(String msg, Throwable cause) {
      super(msg, cause);
   }
}
