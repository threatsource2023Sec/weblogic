package org.jboss.weld.module.jta;

import javax.transaction.UserTransaction;
import org.jboss.weld.bean.builtin.ee.AbstractEEBean;
import org.jboss.weld.bean.builtin.ee.AbstractEECallable;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.transaction.spi.TransactionServices;

class UserTransactionBean extends AbstractEEBean {
   UserTransactionBean(BeanManagerImpl beanManager) {
      super(UserTransaction.class, new UserTransactionCallable(beanManager), beanManager);
   }

   public String toString() {
      return "Built-in Bean [javax.transaction.UserTransaction] with qualifiers [@Default]";
   }

   private static class UserTransactionCallable extends AbstractEECallable {
      private static final long serialVersionUID = -6320641773968440920L;

      public UserTransactionCallable(BeanManagerImpl beanManager) {
         super(beanManager);
      }

      public UserTransaction call() throws Exception {
         TransactionServices transactionServices = (TransactionServices)this.getBeanManager().getServices().get(TransactionServices.class);
         if (transactionServices != null) {
            return transactionServices.getUserTransaction();
         } else {
            throw BeanLogger.LOG.transactionServicesNotAvailable();
         }
      }
   }
}
