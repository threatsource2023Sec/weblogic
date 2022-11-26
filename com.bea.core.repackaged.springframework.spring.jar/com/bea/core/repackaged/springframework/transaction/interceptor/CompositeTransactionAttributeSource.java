package com.bea.core.repackaged.springframework.transaction.interceptor;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.io.Serializable;
import java.lang.reflect.Method;

public class CompositeTransactionAttributeSource implements TransactionAttributeSource, Serializable {
   private final TransactionAttributeSource[] transactionAttributeSources;

   public CompositeTransactionAttributeSource(TransactionAttributeSource... transactionAttributeSources) {
      Assert.notNull(transactionAttributeSources, (String)"TransactionAttributeSource array must not be null");
      this.transactionAttributeSources = transactionAttributeSources;
   }

   public final TransactionAttributeSource[] getTransactionAttributeSources() {
      return this.transactionAttributeSources;
   }

   @Nullable
   public TransactionAttribute getTransactionAttribute(Method method, @Nullable Class targetClass) {
      TransactionAttributeSource[] var3 = this.transactionAttributeSources;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         TransactionAttributeSource source = var3[var5];
         TransactionAttribute attr = source.getTransactionAttribute(method, targetClass);
         if (attr != null) {
            return attr;
         }
      }

      return null;
   }
}
