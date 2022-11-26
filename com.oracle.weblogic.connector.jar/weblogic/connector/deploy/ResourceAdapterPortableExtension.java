package weblogic.connector.deploy;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import weblogic.connector.common.ManagedBeanProducer;

public class ResourceAdapterPortableExtension implements Extension {
   public void registerManagedBeanProducer(@Observes BeforeBeanDiscovery bbd, BeanManager bm) {
      bbd.addAnnotatedType(bm.createAnnotatedType(ManagedBeanProducer.class), ManagedBeanProducer.class.getName());
   }
}
