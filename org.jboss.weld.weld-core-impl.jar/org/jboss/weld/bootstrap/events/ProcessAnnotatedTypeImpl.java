package org.jboss.weld.bootstrap.events;

import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.inject.spi.configurator.AnnotatedTypeConfigurator;
import org.jboss.weld.annotated.AnnotatedTypeValidator;
import org.jboss.weld.annotated.slim.SlimAnnotatedType;
import org.jboss.weld.bootstrap.events.configurator.AnnotatedTypeConfiguratorImpl;
import org.jboss.weld.logging.BootstrapLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.resources.ClassTransformer;

public class ProcessAnnotatedTypeImpl extends ContainerEvent implements ProcessAnnotatedType {
   private final SlimAnnotatedType originalAnnotatedType;
   private final BeanManagerImpl manager;
   private AnnotatedType annotatedType;
   private AnnotatedTypeConfiguratorImpl configurator;
   private boolean veto;
   private boolean annotatedTypeSet;

   public ProcessAnnotatedTypeImpl(BeanManagerImpl beanManager, SlimAnnotatedType annotatedType) {
      this(beanManager, annotatedType, ProcessAnnotatedType.class);
   }

   protected ProcessAnnotatedTypeImpl(BeanManagerImpl beanManager, SlimAnnotatedType annotatedType, Class rawType) {
      this.manager = beanManager;
      this.annotatedType = annotatedType;
      this.originalAnnotatedType = annotatedType;
   }

   public AnnotatedType getAnnotatedType() {
      this.checkWithinObserverNotification();
      return this.annotatedType;
   }

   public SlimAnnotatedType getResultingAnnotatedType() {
      return (SlimAnnotatedType)(this.isDirty() ? ClassTransformer.instance(this.manager).getUnbackedAnnotatedType(this.originalAnnotatedType, this.annotatedType) : this.originalAnnotatedType);
   }

   public SlimAnnotatedType getOriginalAnnotatedType() {
      return this.originalAnnotatedType;
   }

   public void setAnnotatedType(AnnotatedType type) {
      if (this.configurator != null) {
         throw BootstrapLogger.LOG.configuratorAndSetMethodBothCalled(ProcessAnnotatedType.class.getSimpleName(), this.getReceiver());
      } else {
         this.checkWithinObserverNotification();
         if (type == null) {
            throw BootstrapLogger.LOG.annotationTypeNull(this);
         } else {
            this.replaceAnnotatedType(type);
            this.annotatedTypeSet = true;
            BootstrapLogger.LOG.setAnnotatedTypeCalled(this.getReceiver(), this.annotatedType, type);
         }
      }
   }

   public AnnotatedTypeConfigurator configureAnnotatedType() {
      if (this.annotatedTypeSet) {
         throw BootstrapLogger.LOG.configuratorAndSetMethodBothCalled(ProcessAnnotatedType.class.getSimpleName(), this.getReceiver());
      } else {
         this.checkWithinObserverNotification();
         if (this.configurator == null) {
            this.configurator = new AnnotatedTypeConfiguratorImpl(this.annotatedType);
         }

         BootstrapLogger.LOG.configureAnnotatedTypeCalled(this.getReceiver(), this.annotatedType);
         return this.configurator;
      }
   }

   public void veto() {
      this.checkWithinObserverNotification();
      this.veto = true;
      BootstrapLogger.LOG.annotatedTypeVetoed(this.getReceiver(), this.annotatedType);
   }

   public boolean isVeto() {
      return this.veto;
   }

   public boolean isDirty() {
      return this.originalAnnotatedType != this.annotatedType;
   }

   public void postNotify(Extension extension) {
      super.postNotify(extension);
      if (this.configurator != null) {
         this.replaceAnnotatedType(this.configurator.complete());
         this.configurator = null;
      }

      this.annotatedTypeSet = false;
   }

   public String toString() {
      return this.annotatedType.toString();
   }

   private void replaceAnnotatedType(AnnotatedType type) {
      if (!this.originalAnnotatedType.getJavaClass().equals(type.getJavaClass())) {
         throw BootstrapLogger.LOG.annotatedTypeJavaClassMismatch(this.annotatedType.getJavaClass(), type.getJavaClass());
      } else {
         AnnotatedTypeValidator.validateAnnotatedType(type);
         this.annotatedType = type;
      }
   }
}
