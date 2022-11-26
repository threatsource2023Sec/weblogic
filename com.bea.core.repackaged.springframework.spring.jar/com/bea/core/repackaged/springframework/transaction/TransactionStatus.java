package com.bea.core.repackaged.springframework.transaction;

import java.io.Flushable;

public interface TransactionStatus extends SavepointManager, Flushable {
   boolean isNewTransaction();

   boolean hasSavepoint();

   void setRollbackOnly();

   boolean isRollbackOnly();

   void flush();

   boolean isCompleted();
}
