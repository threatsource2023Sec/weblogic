package com.bea.core.repackaged.springframework.transaction.interceptor;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.lang.reflect.Method;

public interface TransactionAttributeSource {
   @Nullable
   TransactionAttribute getTransactionAttribute(Method var1, @Nullable Class var2);
}
