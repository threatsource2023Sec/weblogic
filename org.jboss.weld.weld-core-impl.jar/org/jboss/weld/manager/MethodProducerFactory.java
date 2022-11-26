package org.jboss.weld.manager;

import javax.enterprise.inject.spi.AnnotatedMember;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.Producer;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedMethod;
import org.jboss.weld.bean.DisposalMethod;
import org.jboss.weld.injection.producer.ProducerMethodProducer;
import org.jboss.weld.resources.MemberTransformer;
import org.jboss.weld.util.reflection.Reflections;

public class MethodProducerFactory extends AbstractProducerFactory {
   private final AnnotatedMethod method;

   protected MethodProducerFactory(AnnotatedMethod method, Bean declaringBean, BeanManagerImpl manager) {
      super(declaringBean, manager);
      this.method = (AnnotatedMethod)Reflections.cast(method);
   }

   public Producer createProducer(final Bean declaringBean, final Bean bean, DisposalMethod disposalMethod) {
      EnhancedAnnotatedMethod enhancedMethod = (EnhancedAnnotatedMethod)((MemberTransformer)this.getManager().getServices().get(MemberTransformer.class)).loadEnhancedMember(this.method, this.getManager().getId());
      return new ProducerMethodProducer(enhancedMethod, disposalMethod) {
         public AnnotatedMethod getAnnotated() {
            return MethodProducerFactory.this.method;
         }

         public BeanManagerImpl getBeanManager() {
            return MethodProducerFactory.this.getManager();
         }

         public Bean getDeclaringBean() {
            return declaringBean;
         }

         public Bean getBean() {
            return bean;
         }
      };
   }

   protected AnnotatedMember getAnnotatedMember() {
      return this.method;
   }
}
