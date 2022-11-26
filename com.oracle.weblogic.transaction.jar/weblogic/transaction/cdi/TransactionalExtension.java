package weblogic.transaction.cdi;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;

public class TransactionalExtension implements Extension {
   public void beforeBeanDiscovery(@Observes BeforeBeanDiscovery beforeBeanDiscoveryEvent, BeanManager beanManager) {
      AnnotatedType annotatedType = beanManager.createAnnotatedType(TransactionalInterceptorMandatory.class);
      beforeBeanDiscoveryEvent.addAnnotatedType(annotatedType, TransactionalInterceptorMandatory.class.getName());
      annotatedType = beanManager.createAnnotatedType(TransactionalInterceptorNever.class);
      beforeBeanDiscoveryEvent.addAnnotatedType(annotatedType, TransactionalInterceptorNever.class.getName());
      annotatedType = beanManager.createAnnotatedType(TransactionalInterceptorNotSupported.class);
      beforeBeanDiscoveryEvent.addAnnotatedType(annotatedType, TransactionalInterceptorNotSupported.class.getName());
      annotatedType = beanManager.createAnnotatedType(TransactionalInterceptorRequired.class);
      beforeBeanDiscoveryEvent.addAnnotatedType(annotatedType, TransactionalInterceptorRequired.class.getName());
      annotatedType = beanManager.createAnnotatedType(TransactionalInterceptorRequiresNew.class);
      beforeBeanDiscoveryEvent.addAnnotatedType(annotatedType, TransactionalInterceptorRequiresNew.class.getName());
      annotatedType = beanManager.createAnnotatedType(TransactionalInterceptorSupports.class);
      beforeBeanDiscoveryEvent.addAnnotatedType(annotatedType, TransactionalInterceptorSupports.class.getName());
   }
}
