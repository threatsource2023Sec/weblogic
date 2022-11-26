package com.bea.core.repackaged.springframework.transaction.interceptor;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.transaction.TransactionDefinition;

public interface TransactionAttribute extends TransactionDefinition {
   @Nullable
   String getQualifier();

   boolean rollbackOn(Throwable var1);
}
