package com.bea.core.repackaged.springframework.transaction;

public class NestedTransactionNotSupportedException extends CannotCreateTransactionException {
   public NestedTransactionNotSupportedException(String msg) {
      super(msg);
   }

   public NestedTransactionNotSupportedException(String msg, Throwable cause) {
      super(msg, cause);
   }
}
