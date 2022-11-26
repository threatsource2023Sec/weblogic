package com.bea.core.repackaged.springframework.transaction.interceptor;

import com.bea.core.repackaged.springframework.aop.support.StaticMethodMatcherPointcut;
import com.bea.core.repackaged.springframework.dao.support.PersistenceExceptionTranslator;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.transaction.PlatformTransactionManager;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.io.Serializable;
import java.lang.reflect.Method;

abstract class TransactionAttributeSourcePointcut extends StaticMethodMatcherPointcut implements Serializable {
   public boolean matches(Method method, Class targetClass) {
      if (!TransactionalProxy.class.isAssignableFrom(targetClass) && !PlatformTransactionManager.class.isAssignableFrom(targetClass) && !PersistenceExceptionTranslator.class.isAssignableFrom(targetClass)) {
         TransactionAttributeSource tas = this.getTransactionAttributeSource();
         return tas == null || tas.getTransactionAttribute(method, targetClass) != null;
      } else {
         return false;
      }
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof TransactionAttributeSourcePointcut)) {
         return false;
      } else {
         TransactionAttributeSourcePointcut otherPc = (TransactionAttributeSourcePointcut)other;
         return ObjectUtils.nullSafeEquals(this.getTransactionAttributeSource(), otherPc.getTransactionAttributeSource());
      }
   }

   public int hashCode() {
      return TransactionAttributeSourcePointcut.class.hashCode();
   }

   public String toString() {
      return this.getClass().getName() + ": " + this.getTransactionAttributeSource();
   }

   @Nullable
   protected abstract TransactionAttributeSource getTransactionAttributeSource();
}
