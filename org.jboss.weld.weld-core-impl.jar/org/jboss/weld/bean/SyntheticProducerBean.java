package org.jboss.weld.bean;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.BeanAttributes;
import javax.enterprise.inject.spi.Producer;
import javax.enterprise.inject.spi.ProducerFactory;
import org.jboss.weld.manager.BeanManagerImpl;

public class SyntheticProducerBean extends AbstractSyntheticBean {
   private final Producer producer;

   protected SyntheticProducerBean(BeanAttributes attributes, Class beanClass, ProducerFactory factory, BeanManagerImpl manager) {
      super(attributes, manager, beanClass);
      this.producer = factory.createProducer(this);
   }

   public Object create(CreationalContext creationalContext) {
      return this.getProducer().produce(creationalContext);
   }

   public void destroy(Object instance, CreationalContext creationalContext) {
      try {
         this.getProducer().dispose(instance);
      } finally {
         creationalContext.release();
      }

   }

   protected Producer getProducer() {
      return this.producer;
   }
}
