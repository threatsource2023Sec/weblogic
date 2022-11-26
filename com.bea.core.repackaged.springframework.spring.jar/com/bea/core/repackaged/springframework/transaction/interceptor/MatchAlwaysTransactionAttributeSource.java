package com.bea.core.repackaged.springframework.transaction.interceptor;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.io.Serializable;
import java.lang.reflect.Method;

public class MatchAlwaysTransactionAttributeSource implements TransactionAttributeSource, Serializable {
   private TransactionAttribute transactionAttribute = new DefaultTransactionAttribute();

   public void setTransactionAttribute(TransactionAttribute transactionAttribute) {
      this.transactionAttribute = transactionAttribute;
   }

   @Nullable
   public TransactionAttribute getTransactionAttribute(Method method, @Nullable Class targetClass) {
      return ClassUtils.isUserLevelMethod(method) ? this.transactionAttribute : null;
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof MatchAlwaysTransactionAttributeSource)) {
         return false;
      } else {
         MatchAlwaysTransactionAttributeSource otherTas = (MatchAlwaysTransactionAttributeSource)other;
         return ObjectUtils.nullSafeEquals(this.transactionAttribute, otherTas.transactionAttribute);
      }
   }

   public int hashCode() {
      return MatchAlwaysTransactionAttributeSource.class.hashCode();
   }

   public String toString() {
      return this.getClass().getName() + ": " + this.transactionAttribute;
   }
}
