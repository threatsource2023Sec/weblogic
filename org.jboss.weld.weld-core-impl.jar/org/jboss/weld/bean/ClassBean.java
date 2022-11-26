package org.jboss.weld.bean;

import javax.enterprise.inject.spi.InjectionTarget;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.annotated.slim.SlimAnnotatedType;
import org.jboss.weld.manager.BeanManagerImpl;

public interface ClassBean extends WeldBean {
   SlimAnnotatedType getAnnotated();

   EnhancedAnnotatedType getEnhancedAnnotated();

   BeanManagerImpl getBeanManager();

   InjectionTarget getProducer();
}
