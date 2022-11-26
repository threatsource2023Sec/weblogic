package com.bea.core.repackaged.springframework.transaction.interceptor;

import com.bea.core.repackaged.springframework.aop.ClassFilter;
import com.bea.core.repackaged.springframework.aop.Pointcut;
import com.bea.core.repackaged.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;
import com.bea.core.repackaged.springframework.lang.Nullable;

public class BeanFactoryTransactionAttributeSourceAdvisor extends AbstractBeanFactoryPointcutAdvisor {
   @Nullable
   private TransactionAttributeSource transactionAttributeSource;
   private final TransactionAttributeSourcePointcut pointcut = new TransactionAttributeSourcePointcut() {
      @Nullable
      protected TransactionAttributeSource getTransactionAttributeSource() {
         return BeanFactoryTransactionAttributeSourceAdvisor.this.transactionAttributeSource;
      }
   };

   public void setTransactionAttributeSource(TransactionAttributeSource transactionAttributeSource) {
      this.transactionAttributeSource = transactionAttributeSource;
   }

   public void setClassFilter(ClassFilter classFilter) {
      this.pointcut.setClassFilter(classFilter);
   }

   public Pointcut getPointcut() {
      return this.pointcut;
   }
}
