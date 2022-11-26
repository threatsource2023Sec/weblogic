package org.jboss.weld.bootstrap.events;

import java.lang.reflect.Type;
import javax.enterprise.inject.spi.AnnotatedMember;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessProducer;
import javax.enterprise.inject.spi.Producer;
import javax.enterprise.inject.spi.configurator.ProducerConfigurator;
import org.jboss.weld.bean.AbstractProducerBean;
import org.jboss.weld.bootstrap.events.configurator.ProducerConfiguratorImpl;
import org.jboss.weld.logging.BootstrapLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.Preconditions;
import org.jboss.weld.util.reflection.Reflections;

public class ProcessProducerImpl extends AbstractDefinitionContainerEvent implements ProcessProducer {
   private final AnnotatedMember annotatedMember;
   private AbstractProducerBean bean;
   private ProducerConfiguratorImpl configurator;
   private boolean producerSet;

   protected static void fire(BeanManagerImpl beanManager, AbstractProducerBean bean) {
      if (beanManager.isBeanEnabled(bean)) {
         (new ProcessProducerImpl(beanManager, (AnnotatedMember)Reflections.cast(bean.getAnnotated()), bean) {
         }).fire();
      }

   }

   private ProcessProducerImpl(BeanManagerImpl beanManager, AnnotatedMember annotatedMember, AbstractProducerBean bean) {
      super(beanManager, ProcessProducer.class, new Type[]{bean.getAnnotated().getDeclaringType().getBaseType(), bean.getAnnotated().getBaseType()});
      this.bean = bean;
      this.annotatedMember = annotatedMember;
   }

   public AnnotatedMember getAnnotatedMember() {
      this.checkWithinObserverNotification();
      return this.annotatedMember;
   }

   public Producer getProducer() {
      this.checkWithinObserverNotification();
      return this.bean.getProducer();
   }

   public void setProducer(Producer producer) {
      if (this.configurator != null) {
         throw BootstrapLogger.LOG.configuratorAndSetMethodBothCalled(ProcessProducer.class.getSimpleName(), this.getReceiver());
      } else {
         Preconditions.checkArgumentNotNull(producer, "producer");
         this.checkWithinObserverNotification();
         BootstrapLogger.LOG.setProducerCalled(this.getReceiver(), this.getProducer(), producer);
         this.bean.setProducer(producer);
         this.producerSet = true;
      }
   }

   public ProducerConfigurator configureProducer() {
      if (this.producerSet) {
         throw BootstrapLogger.LOG.configuratorAndSetMethodBothCalled(ProcessProducer.class.getSimpleName(), this.getReceiver());
      } else {
         this.checkWithinObserverNotification();
         if (this.configurator == null) {
            this.configurator = new ProducerConfiguratorImpl(this.bean.getProducer());
         }

         BootstrapLogger.LOG.configureProducerCalled(this.getReceiver(), this.bean);
         return this.configurator;
      }
   }

   public void postNotify(Extension extension) {
      super.postNotify(extension);
      if (this.configurator != null) {
         this.bean.setProducer(this.configurator.complete());
         this.configurator = null;
      }

      this.producerSet = false;
   }

   // $FF: synthetic method
   ProcessProducerImpl(BeanManagerImpl x0, AnnotatedMember x1, AbstractProducerBean x2, Object x3) {
      this(x0, x1, x2);
   }
}
