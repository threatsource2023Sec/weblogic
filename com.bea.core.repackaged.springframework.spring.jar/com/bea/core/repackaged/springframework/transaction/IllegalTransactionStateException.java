package com.bea.core.repackaged.springframework.transaction;

public class IllegalTransactionStateException extends TransactionUsageException {
   public IllegalTransactionStateException(String msg) {
      super(msg);
   }

   public IllegalTransactionStateException(String msg, Throwable cause) {
      super(msg, cause);
   }
}
