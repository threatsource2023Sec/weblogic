package com.bea.core.repackaged.springframework.transaction.annotation;

import com.bea.core.repackaged.springframework.context.annotation.Bean;
import com.bea.core.repackaged.springframework.context.annotation.Configuration;
import com.bea.core.repackaged.springframework.context.annotation.Role;
import com.bea.core.repackaged.springframework.transaction.interceptor.BeanFactoryTransactionAttributeSourceAdvisor;
import com.bea.core.repackaged.springframework.transaction.interceptor.TransactionAttributeSource;
import com.bea.core.repackaged.springframework.transaction.interceptor.TransactionInterceptor;

@Configuration
public class ProxyTransactionManagementConfiguration extends AbstractTransactionManagementConfiguration {
   @Bean(
      name = {"com.bea.core.repackaged.springframework.transaction.config.internalTransactionAdvisor"}
   )
   @Role(2)
   public BeanFactoryTransactionAttributeSourceAdvisor transactionAdvisor() {
      BeanFactoryTransactionAttributeSourceAdvisor advisor = new BeanFactoryTransactionAttributeSourceAdvisor();
      advisor.setTransactionAttributeSource(this.transactionAttributeSource());
      advisor.setAdvice(this.transactionInterceptor());
      if (this.enableTx != null) {
         advisor.setOrder((Integer)this.enableTx.getNumber("order"));
      }

      return advisor;
   }

   @Bean
   @Role(2)
   public TransactionAttributeSource transactionAttributeSource() {
      return new AnnotationTransactionAttributeSource();
   }

   @Bean
   @Role(2)
   public TransactionInterceptor transactionInterceptor() {
      TransactionInterceptor interceptor = new TransactionInterceptor();
      interceptor.setTransactionAttributeSource(this.transactionAttributeSource());
      if (this.txManager != null) {
         interceptor.setTransactionManager(this.txManager);
      }

      return interceptor;
   }
}
