package org.jboss.weld.bean;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.BeanAttributes;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.enterprise.inject.spi.InjectionTargetFactory;
import org.jboss.weld.manager.BeanManagerImpl;

public class SyntheticClassBean extends AbstractSyntheticBean {
   protected final InjectionTarget producer;

   public SyntheticClassBean(BeanAttributes attributes, Class beanClass, InjectionTargetFactory factory, BeanManagerImpl manager) {
      super(attributes, manager, beanClass);
      this.producer = factory.createInjectionTarget(this);
   }

   public Object create(CreationalContext creationalContext) {
      Object instance = this.producer.produce(creationalContext);
      this.producer.inject(instance, creationalContext);
      this.producer.postConstruct(instance);
      return instance;
   }

   public void destroy(Object instance, CreationalContext creationalContext) {
      try {
         this.producer.preDestroy(instance);
         this.producer.dispose(instance);
      } finally {
         creationalContext.release();
      }

   }

   protected InjectionTarget getProducer() {
      return this.producer;
   }

   public String toString() {
      return "SyntheticClassBean [attributes=" + this.attributes() + ", injectionTarget=" + this.producer.getClass() + ", beanClass=" + this.getBeanClass() + "]";
   }
}
