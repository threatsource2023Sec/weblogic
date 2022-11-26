package org.jboss.weld.bootstrap.events;

import java.lang.reflect.Type;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.AnnotatedParameter;
import javax.enterprise.inject.spi.ProcessBean;
import org.jboss.weld.bean.AbstractProducerBean;
import org.jboss.weld.injection.producer.AbstractMemberProducer;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.reflection.Reflections;

public abstract class AbstractProcessProducerBean extends AbstractDefinitionContainerEvent implements ProcessBean {
   private final AbstractProducerBean bean;

   public AbstractProcessProducerBean(BeanManagerImpl beanManager, Type rawType, Type[] actualTypeArguments, AbstractProducerBean bean) {
      super(beanManager, rawType, actualTypeArguments);
      this.bean = bean;
   }

   public Annotated getAnnotated() {
      this.checkWithinObserverNotification();
      return this.bean.getAnnotated();
   }

   public AbstractProducerBean getBean() {
      this.checkWithinObserverNotification();
      return this.bean;
   }

   public AnnotatedParameter getAnnotatedDisposedParameter() {
      this.checkWithinObserverNotification();
      if (this.getBean().getProducer() instanceof AbstractMemberProducer) {
         AbstractMemberProducer producer = (AbstractMemberProducer)this.getBean().getProducer();
         if (producer.getDisposalMethod() != null) {
            return (AnnotatedParameter)Reflections.cast(producer.getDisposalMethod().getDisposesParameter());
         }
      }

      return null;
   }
}
