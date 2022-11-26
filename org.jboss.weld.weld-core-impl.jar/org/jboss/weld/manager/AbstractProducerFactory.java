package org.jboss.weld.manager;

import javax.enterprise.inject.spi.AnnotatedMember;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.Producer;
import javax.enterprise.inject.spi.ProducerFactory;
import org.jboss.weld.annotated.AnnotatedTypeValidator;
import org.jboss.weld.bean.DisposalMethod;
import org.jboss.weld.exceptions.IllegalArgumentException;
import org.jboss.weld.injection.producer.InjectionTargetService;
import org.jboss.weld.logging.BeanManagerLogger;

public abstract class AbstractProducerFactory implements ProducerFactory {
   private final Bean declaringBean;
   private final BeanManagerImpl manager;

   protected AbstractProducerFactory(Bean declaringBean, BeanManagerImpl manager) {
      this.declaringBean = declaringBean;
      this.manager = manager;
   }

   protected Bean getDeclaringBean() {
      return this.declaringBean;
   }

   protected BeanManagerImpl getManager() {
      return this.manager;
   }

   protected abstract AnnotatedMember getAnnotatedMember();

   public abstract Producer createProducer(Bean var1, Bean var2, DisposalMethod var3);

   public Producer createProducer(Bean bean) {
      if (this.getDeclaringBean() == null && !this.getAnnotatedMember().isStatic()) {
         throw BeanManagerLogger.LOG.nullDeclaringBean(this.getAnnotatedMember());
      } else {
         AnnotatedTypeValidator.validateAnnotatedMember(this.getAnnotatedMember());

         try {
            Producer producer = this.createProducer(this.getDeclaringBean(), bean, (DisposalMethod)null);
            ((InjectionTargetService)this.getManager().getServices().get(InjectionTargetService.class)).validateProducer(producer);
            return producer;
         } catch (Throwable var3) {
            throw new IllegalArgumentException(var3);
         }
      }
   }
}
