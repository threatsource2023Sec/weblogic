package org.jboss.weld.bean;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.enterprise.inject.spi.BeanAttributes;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.enterprise.inject.spi.Producer;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.annotated.slim.SlimAnnotatedType;
import org.jboss.weld.bootstrap.BeanDeployerEnvironment;
import org.jboss.weld.interceptor.spi.model.InterceptionModel;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.serialization.spi.BeanIdentifier;
import org.jboss.weld.util.Beans;

public abstract class AbstractClassBean extends AbstractBean implements DecorableBean, ClassBean {
   protected final SlimAnnotatedType annotatedType;
   protected volatile EnhancedAnnotatedType enhancedAnnotatedItem;
   private InjectionTarget producer;

   protected AbstractClassBean(BeanAttributes attributes, EnhancedAnnotatedType type, BeanIdentifier identifier, BeanManagerImpl beanManager) {
      super(attributes, identifier, beanManager);
      this.enhancedAnnotatedItem = type;
      this.annotatedType = type.slim();
      this.initType();
   }

   public void internalInitialize(BeanDeployerEnvironment environment) {
      super.internalInitialize(environment);
      this.checkBeanImplementation();
   }

   public boolean hasDecorators() {
      return !this.getDecorators().isEmpty();
   }

   public List getDecorators() {
      return this.isInterceptionCandidate() ? this.beanManager.resolveDecorators(this.getTypes(), this.getQualifiers()) : Collections.emptyList();
   }

   protected void initType() {
      this.type = this.getEnhancedAnnotated().getJavaClass();
   }

   protected void checkBeanImplementation() {
   }

   protected void preSpecialize() {
      super.preSpecialize();
      Class superclass = this.getAnnotated().getJavaClass().getSuperclass();
      if (superclass == null || superclass.equals(Object.class)) {
         throw BeanLogger.LOG.specializingBeanMustExtendABean(this);
      }
   }

   public SlimAnnotatedType getAnnotated() {
      return this.annotatedType;
   }

   public EnhancedAnnotatedType getEnhancedAnnotated() {
      return (EnhancedAnnotatedType)Beans.checkEnhancedAnnotatedAvailable(this.enhancedAnnotatedItem);
   }

   public void cleanupAfterBoot() {
      this.enhancedAnnotatedItem = null;
   }

   protected abstract boolean isInterceptionCandidate();

   public Set getInjectionPoints() {
      return this.getProducer().getInjectionPoints();
   }

   public InterceptionModel getInterceptors() {
      return this.isInterceptionCandidate() ? (InterceptionModel)this.beanManager.getInterceptorModelRegistry().get(this.getAnnotated()) : null;
   }

   public boolean hasInterceptors() {
      return this.getInterceptors() != null;
   }

   public InjectionTarget getProducer() {
      return this.producer;
   }

   public void setProducer(InjectionTarget producer) {
      this.producer = producer;
   }

   public InjectionTarget getInjectionTarget() {
      return this.getProducer();
   }

   public void setInjectionTarget(InjectionTarget injectionTarget) {
      this.setProducer(injectionTarget);
   }

   public void setProducer(Producer producer) {
      throw new IllegalArgumentException("Class bean " + this + " requires an InjectionTarget but a Producer was provided instead " + producer);
   }
}
