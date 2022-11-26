package com.bea.core.repackaged.springframework.transaction;

public class TransactionSuspensionNotSupportedException extends CannotCreateTransactionException {
   public TransactionSuspensionNotSupportedException(String msg) {
      super(msg);
   }

   public TransactionSuspensionNotSupportedException(String msg, Throwable cause) {
      super(msg, cause);
   }
}
