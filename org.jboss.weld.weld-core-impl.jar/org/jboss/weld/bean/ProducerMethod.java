package org.jboss.weld.bean;

import java.lang.reflect.Member;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanAttributes;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedMethod;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.injection.producer.ProducerMethodProducer;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.serialization.spi.BeanIdentifier;
import org.jboss.weld.util.Beans;
import org.jboss.weld.util.Proxies;
import org.jboss.weld.util.reflection.Formats;

public class ProducerMethod extends AbstractProducerBean {
   private final boolean proxiable;
   private final AnnotatedMethod annotatedMethod;
   private volatile EnhancedAnnotatedMethod enhancedAnnotatedMethod;

   public static ProducerMethod of(BeanAttributes attributes, EnhancedAnnotatedMethod method, AbstractClassBean declaringBean, DisposalMethod disposalMethod, BeanManagerImpl beanManager, ServiceRegistry services) {
      return new ProducerMethod(createId(attributes, method, declaringBean), attributes, method, declaringBean, disposalMethod, beanManager, services);
   }

   private static BeanIdentifier createId(BeanAttributes attributes, EnhancedAnnotatedMethod method, AbstractClassBean declaringBean) {
      return (BeanIdentifier)(!Dependent.class.equals(attributes.getScope()) && !ApplicationScoped.class.equals(attributes.getScope()) ? new StringBeanIdentifier(BeanIdentifiers.forProducerMethod(method, declaringBean)) : new ProducerMethodIdentifier(method, declaringBean));
   }

   protected ProducerMethod(BeanIdentifier identifier, BeanAttributes attributes, EnhancedAnnotatedMethod method, AbstractClassBean declaringBean, DisposalMethod disposalMethod, BeanManagerImpl beanManager, ServiceRegistry services) {
      super(attributes, identifier, declaringBean, beanManager, services);
      this.enhancedAnnotatedMethod = method;
      this.annotatedMethod = method.slim();
      this.initType();
      this.proxiable = Proxies.isTypesProxyable((Iterable)method.getTypeClosure(), beanManager.getServices());
      this.setProducer(new ProducerMethodProducer(method, disposalMethod) {
         public BeanManagerImpl getBeanManager() {
            return ProducerMethod.this.beanManager;
         }

         public Bean getDeclaringBean() {
            return ProducerMethod.this.getDeclaringBean();
         }

         public Bean getBean() {
            return ProducerMethod.this;
         }
      });
   }

   public AnnotatedMethod getAnnotated() {
      return this.annotatedMethod;
   }

   public EnhancedAnnotatedMethod getEnhancedAnnotated() {
      return (EnhancedAnnotatedMethod)Beans.checkEnhancedAnnotatedAvailable(this.enhancedAnnotatedMethod);
   }

   public void cleanupAfterBoot() {
      this.enhancedAnnotatedMethod = null;
   }

   protected void specialize() {
      Set specializedBeans = this.getSpecializedBeans();
      if (specializedBeans.isEmpty()) {
         throw BeanLogger.LOG.producerMethodNotSpecializing(this, Formats.formatAsStackTraceElement((Member)this.annotatedMethod.getJavaMember()));
      }
   }

   public String toString() {
      return "Producer Method [" + Formats.formatType(this.getAnnotated().getBaseType()) + "] with qualifiers [" + Formats.formatAnnotations((Iterable)this.getQualifiers()) + "] declared as [" + this.getAnnotated() + "]";
   }

   public boolean isProxyable() {
      return this.proxiable;
   }
}
