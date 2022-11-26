package weblogic.transaction.cdi;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;

public class TransactionScopedContextExtension implements Extension {
   public void afterBeanDiscovery(@Observes AfterBeanDiscovery event, BeanManager manager) {
      event.addContext(new TransactionScopedContextImpl());
      event.addBean(TransactionScopedCDIUtil.createHelperBean(manager, TransactionScopedCDIEventHelperImpl.class));
   }
}
