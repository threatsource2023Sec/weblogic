package org.jboss.weld.bean;

import java.util.Set;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanAttributes;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedField;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.injection.producer.ProducerFieldProducer;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.Beans;
import org.jboss.weld.util.Proxies;
import org.jboss.weld.util.reflection.Formats;

public class ProducerField extends AbstractProducerBean {
   private final AnnotatedField annotatedField;
   private volatile EnhancedAnnotatedField enhancedAnnotatedField;
   private final boolean proxiable;

   public static ProducerField of(BeanAttributes attributes, EnhancedAnnotatedField field, AbstractClassBean declaringBean, DisposalMethod disposalMethod, BeanManagerImpl beanManager, ServiceRegistry services) {
      return new ProducerField(attributes, field, declaringBean, disposalMethod, beanManager, services);
   }

   protected ProducerField(BeanAttributes attributes, EnhancedAnnotatedField field, AbstractClassBean declaringBean, DisposalMethod disposalMethod, BeanManagerImpl manager, ServiceRegistry services) {
      super(attributes, new StringBeanIdentifier(BeanIdentifiers.forProducerField(field, declaringBean)), declaringBean, manager, services);
      this.enhancedAnnotatedField = field;
      this.annotatedField = field.slim();
      this.initType();
      this.proxiable = Proxies.isTypesProxyable((Iterable)field.getTypeClosure(), this.beanManager.getServices());
      this.setProducer(new ProducerFieldProducer(field, disposalMethod) {
         public AnnotatedField getAnnotated() {
            return ProducerField.this.annotatedField;
         }

         public BeanManagerImpl getBeanManager() {
            return ProducerField.this.beanManager;
         }

         public Bean getDeclaringBean() {
            return ProducerField.this.getDeclaringBean();
         }

         public Bean getBean() {
            return ProducerField.this;
         }
      });
   }

   public AnnotatedField getAnnotated() {
      return this.annotatedField;
   }

   public EnhancedAnnotatedField getEnhancedAnnotated() {
      return (EnhancedAnnotatedField)Beans.checkEnhancedAnnotatedAvailable(this.enhancedAnnotatedField);
   }

   public void cleanupAfterBoot() {
      this.enhancedAnnotatedField = null;
   }

   public boolean isSpecializing() {
      return false;
   }

   public String toString() {
      return "Producer Field [" + Formats.formatType(this.getAnnotated().getBaseType()) + "] with qualifiers [" + Formats.formatAnnotations((Iterable)this.getQualifiers()) + "] declared as [" + this.getAnnotated() + "]";
   }

   public boolean isProxyable() {
      return this.proxiable;
   }

   public Set getSpecializedBeans() {
      throw new UnsupportedOperationException("Producer field may not specialize other beans " + this);
   }
}
