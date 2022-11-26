package com.bea.core.repackaged.springframework.transaction;

public class UnexpectedRollbackException extends TransactionException {
   public UnexpectedRollbackException(String msg) {
      super(msg);
   }

   public UnexpectedRollbackException(String msg, Throwable cause) {
      super(msg, cause);
   }
}
