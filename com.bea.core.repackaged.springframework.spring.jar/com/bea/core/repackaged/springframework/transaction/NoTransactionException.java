package com.bea.core.repackaged.springframework.transaction;

public class NoTransactionException extends TransactionUsageException {
   public NoTransactionException(String msg) {
      super(msg);
   }

   public NoTransactionException(String msg, Throwable cause) {
      super(msg, cause);
   }
}
