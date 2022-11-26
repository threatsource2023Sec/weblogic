package com.bea.core.repackaged.springframework.transaction.support;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.transaction.TransactionStatus;

@FunctionalInterface
public interface TransactionCallback {
   @Nullable
   Object doInTransaction(TransactionStatus var1);
}
