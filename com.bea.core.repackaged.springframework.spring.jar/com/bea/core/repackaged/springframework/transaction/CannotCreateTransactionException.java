package com.bea.core.repackaged.springframework.transaction;

public class CannotCreateTransactionException extends TransactionException {
   public CannotCreateTransactionException(String msg) {
      super(msg);
   }

   public CannotCreateTransactionException(String msg, Throwable cause) {
      super(msg, cause);
   }
}
