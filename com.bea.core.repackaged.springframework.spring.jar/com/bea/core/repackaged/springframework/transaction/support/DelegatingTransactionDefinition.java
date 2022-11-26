package com.bea.core.repackaged.springframework.transaction.support;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.transaction.TransactionDefinition;
import com.bea.core.repackaged.springframework.util.Assert;
import java.io.Serializable;

public abstract class DelegatingTransactionDefinition implements TransactionDefinition, Serializable {
   private final TransactionDefinition targetDefinition;

   public DelegatingTransactionDefinition(TransactionDefinition targetDefinition) {
      Assert.notNull(targetDefinition, (String)"Target definition must not be null");
      this.targetDefinition = targetDefinition;
   }

   public int getPropagationBehavior() {
      return this.targetDefinition.getPropagationBehavior();
   }

   public int getIsolationLevel() {
      return this.targetDefinition.getIsolationLevel();
   }

   public int getTimeout() {
      return this.targetDefinition.getTimeout();
   }

   public boolean isReadOnly() {
      return this.targetDefinition.isReadOnly();
   }

   @Nullable
   public String getName() {
      return this.targetDefinition.getName();
   }

   public boolean equals(Object other) {
      return this.targetDefinition.equals(other);
   }

   public int hashCode() {
      return this.targetDefinition.hashCode();
   }

   public String toString() {
      return this.targetDefinition.toString();
   }
}
