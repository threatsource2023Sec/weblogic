package com.bea.core.repackaged.springframework.transaction.interceptor;

import com.bea.core.repackaged.springframework.aop.Pointcut;
import com.bea.core.repackaged.springframework.aop.framework.AbstractSingletonProxyFactoryBean;
import com.bea.core.repackaged.springframework.aop.framework.ProxyFactory;
import com.bea.core.repackaged.springframework.aop.support.DefaultPointcutAdvisor;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryAware;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.transaction.PlatformTransactionManager;
import java.util.Properties;

public class TransactionProxyFactoryBean extends AbstractSingletonProxyFactoryBean implements BeanFactoryAware {
   private final TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
   @Nullable
   private Pointcut pointcut;

   public void setTransactionManager(PlatformTransactionManager transactionManager) {
      this.transactionInterceptor.setTransactionManager(transactionManager);
   }

   public void setTransactionAttributes(Properties transactionAttributes) {
      this.transactionInterceptor.setTransactionAttributes(transactionAttributes);
   }

   public void setTransactionAttributeSource(TransactionAttributeSource transactionAttributeSource) {
      this.transactionInterceptor.setTransactionAttributeSource(transactionAttributeSource);
   }

   public void setPointcut(Pointcut pointcut) {
      this.pointcut = pointcut;
   }

   public void setBeanFactory(BeanFactory beanFactory) {
      this.transactionInterceptor.setBeanFactory(beanFactory);
   }

   protected Object createMainInterceptor() {
      this.transactionInterceptor.afterPropertiesSet();
      return this.pointcut != null ? new DefaultPointcutAdvisor(this.pointcut, this.transactionInterceptor) : new TransactionAttributeSourceAdvisor(this.transactionInterceptor);
   }

   protected void postProcessProxyFactory(ProxyFactory proxyFactory) {
      proxyFactory.addInterface(TransactionalProxy.class);
   }
}
