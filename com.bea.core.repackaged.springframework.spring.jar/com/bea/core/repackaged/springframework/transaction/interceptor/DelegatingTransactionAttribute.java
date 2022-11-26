package com.bea.core.repackaged.springframework.transaction.interceptor;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.transaction.support.DelegatingTransactionDefinition;
import java.io.Serializable;

public abstract class DelegatingTransactionAttribute extends DelegatingTransactionDefinition implements TransactionAttribute, Serializable {
   private final TransactionAttribute targetAttribute;

   public DelegatingTransactionAttribute(TransactionAttribute targetAttribute) {
      super(targetAttribute);
      this.targetAttribute = targetAttribute;
   }

   @Nullable
   public String getQualifier() {
      return this.targetAttribute.getQualifier();
   }

   public boolean rollbackOn(Throwable ex) {
      return this.targetAttribute.rollbackOn(ex);
   }
}
