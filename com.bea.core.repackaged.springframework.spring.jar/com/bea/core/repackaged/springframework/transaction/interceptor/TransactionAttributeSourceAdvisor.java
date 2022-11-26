package com.bea.core.repackaged.springframework.transaction.interceptor;

import com.bea.core.repackaged.aopalliance.aop.Advice;
import com.bea.core.repackaged.springframework.aop.ClassFilter;
import com.bea.core.repackaged.springframework.aop.Pointcut;
import com.bea.core.repackaged.springframework.aop.support.AbstractPointcutAdvisor;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;

public class TransactionAttributeSourceAdvisor extends AbstractPointcutAdvisor {
   @Nullable
   private TransactionInterceptor transactionInterceptor;
   private final TransactionAttributeSourcePointcut pointcut = new TransactionAttributeSourcePointcut() {
      @Nullable
      protected TransactionAttributeSource getTransactionAttributeSource() {
         return TransactionAttributeSourceAdvisor.this.transactionInterceptor != null ? TransactionAttributeSourceAdvisor.this.transactionInterceptor.getTransactionAttributeSource() : null;
      }
   };

   public TransactionAttributeSourceAdvisor() {
   }

   public TransactionAttributeSourceAdvisor(TransactionInterceptor interceptor) {
      this.setTransactionInterceptor(interceptor);
   }

   public void setTransactionInterceptor(TransactionInterceptor interceptor) {
      this.transactionInterceptor = interceptor;
   }

   public void setClassFilter(ClassFilter classFilter) {
      this.pointcut.setClassFilter(classFilter);
   }

   public Advice getAdvice() {
      Assert.state(this.transactionInterceptor != null, "No TransactionInterceptor set");
      return this.transactionInterceptor;
   }

   public Pointcut getPointcut() {
      return this.pointcut;
   }
}
