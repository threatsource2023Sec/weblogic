package com.bea.core.repackaged.springframework.transaction.support;

import java.io.Flushable;

public interface SmartTransactionObject extends Flushable {
   boolean isRollbackOnly();

   void flush();
}
