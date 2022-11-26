package org.jboss.weld.manager;

import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedMember;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.Producer;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedField;
import org.jboss.weld.bean.DisposalMethod;
import org.jboss.weld.injection.producer.ProducerFieldProducer;
import org.jboss.weld.resources.MemberTransformer;
import org.jboss.weld.util.reflection.Reflections;

public class FieldProducerFactory extends AbstractProducerFactory {
   private final AnnotatedField field;

   protected FieldProducerFactory(AnnotatedField field, Bean declaringBean, BeanManagerImpl manager) {
      super(declaringBean, manager);
      this.field = (AnnotatedField)Reflections.cast(field);
   }

   public Producer createProducer(final Bean declaringBean, final Bean bean, DisposalMethod disposalMethod) {
      EnhancedAnnotatedField enhancedField = (EnhancedAnnotatedField)((MemberTransformer)this.getManager().getServices().get(MemberTransformer.class)).loadEnhancedMember(this.field, this.getManager().getId());
      return new ProducerFieldProducer(enhancedField, disposalMethod) {
         public AnnotatedField getAnnotated() {
            return FieldProducerFactory.this.field;
         }

         public BeanManagerImpl getBeanManager() {
            return FieldProducerFactory.this.getManager();
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
      return this.field;
   }
}
