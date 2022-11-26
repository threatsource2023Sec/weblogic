package com.bea.core.repackaged.springframework.transaction.support;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.transaction.PlatformTransactionManager;
import com.bea.core.repackaged.springframework.transaction.TransactionDefinition;
import com.bea.core.repackaged.springframework.transaction.TransactionException;

public interface CallbackPreferringPlatformTransactionManager extends PlatformTransactionManager {
   @Nullable
   Object execute(@Nullable TransactionDefinition var1, TransactionCallback var2) throws TransactionException;
}
