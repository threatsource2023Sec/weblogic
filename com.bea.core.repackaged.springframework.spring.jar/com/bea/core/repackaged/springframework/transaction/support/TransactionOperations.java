package com.bea.core.repackaged.springframework.transaction.support;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.transaction.TransactionException;

public interface TransactionOperations {
   @Nullable
   Object execute(TransactionCallback var1) throws TransactionException;
}
