package com.bea.core.repackaged.springframework.transaction.jta;

import com.bea.core.repackaged.springframework.lang.Nullable;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;

public interface TransactionFactory {
   Transaction createTransaction(@Nullable String var1, int var2) throws NotSupportedException, SystemException;

   boolean supportsResourceAdapterManagedTransactions();
}
