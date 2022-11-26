package org.jboss.weld.bean.builtin.ee;

import java.io.Serializable;
import java.util.Iterator;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.BeanAttributes;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedField;
import org.jboss.weld.bean.AbstractClassBean;
import org.jboss.weld.bean.DisposalMethod;
import org.jboss.weld.bean.ProducerField;
import org.jboss.weld.bean.builtin.CallableMethodHandler;
import org.jboss.weld.bean.proxy.BeanInstance;
import org.jboss.weld.bean.proxy.EnterpriseTargetBeanInstance;
import org.jboss.weld.bean.proxy.ProxyFactory;
import org.jboss.weld.bootstrap.BeanDeployerEnvironment;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.injection.ResourceInjectionFactory;
import org.jboss.weld.injection.ResourceInjectionProcessor;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.serialization.spi.BeanIdentifier;
import org.jboss.weld.serialization.spi.ContextualStore;
import org.jboss.weld.util.reflection.Reflections;

public class EEResourceProducerField extends ProducerField {
   private ProxyFactory proxyFactory;
   private final Class rawType;

   public static EEResourceProducerField of(BeanAttributes attributes, EnhancedAnnotatedField field, AbstractClassBean declaringBean, DisposalMethod disposalMethod, BeanManagerImpl manager, ServiceRegistry services) {
      return new EEResourceProducerField(attributes, field, declaringBean, disposalMethod, manager, services);
   }

   public static boolean isEEResourceProducerField(BeanManagerImpl beanManager, AnnotatedField field) {
      ResourceInjectionFactory factory = (ResourceInjectionFactory)beanManager.getServices().get(ResourceInjectionFactory.class);
      Iterator var3 = factory.iterator();

      ResourceInjectionProcessor processor;
      do {
         if (!var3.hasNext()) {
            return false;
         }

         processor = (ResourceInjectionProcessor)var3.next();
      } while(!field.isAnnotationPresent(processor.getMarkerAnnotation(beanManager)));

      return true;
   }

   protected EEResourceProducerField(BeanAttributes attributes, EnhancedAnnotatedField field, AbstractClassBean declaringBean, DisposalMethod disposalMethod, BeanManagerImpl manager, ServiceRegistry services) {
      super(attributes, field, declaringBean, disposalMethod, manager, services);
      this.rawType = field.getJavaClass();
   }

   public void internalInitialize(BeanDeployerEnvironment environment) {
      super.internalInitialize(environment);
      this.checkEEResource();
      this.proxyFactory = new ProxyFactory(this.getBeanManager().getContextId(), this.getType(), this.getTypes(), this);
   }

   protected void checkEEResource() {
      if (!this.getScope().equals(Dependent.class)) {
         throw BeanLogger.LOG.nonDependentResourceProducerField(this);
      } else if (this.getName() != null) {
         throw BeanLogger.LOG.namedResourceProducerField(this);
      } else if (!isEEResourceProducerField(this.beanManager, this.getAnnotated())) {
         throw BeanLogger.LOG.invalidResourceProducerField(this.getAnnotated());
      }
   }

   public Object create(CreationalContext creationalContext) {
      Object beanInstance = this.getProducer().produce(creationalContext);
      if (!Reflections.isFinal(this.rawType) && !Serializable.class.isAssignableFrom(beanInstance.getClass())) {
         BeanInstance proxyBeanInstance = new EnterpriseTargetBeanInstance(this.getTypes(), new CallableMethodHandler(new EEResourceCallable(this.getBeanManager(), this, creationalContext, beanInstance)));
         return this.checkReturnValue(this.proxyFactory.create(proxyBeanInstance));
      } else {
         return this.checkReturnValue(beanInstance);
      }
   }

   private Object createUnderlying(CreationalContext creationalContext) {
      return super.create(creationalContext);
   }

   public boolean isPassivationCapableBean() {
      return true;
   }

   public String toString() {
      return "Resource " + super.toString();
   }

   private static class EEResourceCallable extends AbstractEECallable {
      private static final long serialVersionUID = 6287931036073200963L;
      private final BeanIdentifier beanId;
      private transient Object instance;
      private final CreationalContext creationalContext;

      private EEResourceCallable(BeanManagerImpl beanManager, ProducerField producerField, CreationalContext creationalContext, Object instance) {
         super(beanManager);
         this.beanId = producerField.getIdentifier();
         this.creationalContext = creationalContext;
         this.instance = instance;
      }

      public Object call() throws Exception {
         if (this.instance == null) {
            Contextual contextual = ((ContextualStore)this.getBeanManager().getServices().get(ContextualStore.class)).getContextual(this.beanId);
            if (!(contextual instanceof EEResourceProducerField)) {
               throw BeanLogger.LOG.beanNotEeResourceProducer(contextual);
            }

            this.instance = ((EEResourceProducerField)Reflections.cast(contextual)).createUnderlying(this.creationalContext);
         }

         return this.instance;
      }

      public String toString() {
         return this.instance == null ? "null" : this.instance.toString();
      }

      // $FF: synthetic method
      EEResourceCallable(BeanManagerImpl x0, ProducerField x1, CreationalContext x2, Object x3, Object x4) {
         this(x0, x1, x2, x3);
      }
   }
}
