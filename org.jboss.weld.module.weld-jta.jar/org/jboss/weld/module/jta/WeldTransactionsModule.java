package org.jboss.weld.module.jta;

import org.jboss.weld.module.ObserverNotifierFactory;
import org.jboss.weld.module.WeldModule;
import org.jboss.weld.transaction.spi.TransactionServices;

public class WeldTransactionsModule implements WeldModule {
   public String getName() {
      return "weld-jta";
   }

   public void postServiceRegistration(WeldModule.PostServiceRegistrationContext ctx) {
      if (ctx.getServices().contains(TransactionServices.class)) {
         ctx.getServices().add(ObserverNotifierFactory.class, TransactionalObserverNotifier.FACTORY);
      }

   }

   public void preBeanRegistration(WeldModule.PreBeanRegistrationContext ctx) {
      if (ctx.getBeanManager().getServices().contains(TransactionServices.class)) {
         ctx.registerBean(new UserTransactionBean(ctx.getBeanManager()));
      }

   }
}
