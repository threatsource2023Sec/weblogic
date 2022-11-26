package org.jboss.weld.bean;

import java.util.Set;
import javax.enterprise.inject.spi.BeanAttributes;
import javax.enterprise.inject.spi.Producer;
import org.jboss.weld.manager.BeanManagerImpl;

public abstract class AbstractSyntheticBean extends CommonBean {
   private final Class beanClass;

   protected AbstractSyntheticBean(BeanAttributes attributes, BeanManagerImpl manager, Class beanClass) {
      super(attributes, new StringBeanIdentifier(BeanIdentifiers.forSyntheticBean(attributes, beanClass)));
      this.beanClass = beanClass;
   }

   public Class getBeanClass() {
      return this.beanClass;
   }

   public Set getInjectionPoints() {
      return this.getProducer().getInjectionPoints();
   }

   protected abstract Producer getProducer();
}
