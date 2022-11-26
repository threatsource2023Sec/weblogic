package com.bea.core.repackaged.springframework.transaction;

import com.bea.core.repackaged.springframework.core.NestedRuntimeException;

public abstract class TransactionException extends NestedRuntimeException {
   public TransactionException(String msg) {
      super(msg);
   }

   public TransactionException(String msg, Throwable cause) {
      super(msg, cause);
   }
}
