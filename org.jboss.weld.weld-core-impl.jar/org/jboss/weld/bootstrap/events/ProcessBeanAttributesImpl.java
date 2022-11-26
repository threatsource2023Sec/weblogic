package org.jboss.weld.bootstrap.events;

import java.lang.reflect.Type;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.BeanAttributes;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessBeanAttributes;
import javax.enterprise.inject.spi.configurator.BeanAttributesConfigurator;
import org.jboss.weld.bootstrap.events.configurator.BeanAttributesConfiguratorImpl;
import org.jboss.weld.logging.BootstrapLogger;
import org.jboss.weld.manager.BeanManagerImpl;

public class ProcessBeanAttributesImpl extends AbstractDefinitionContainerEvent implements ProcessBeanAttributes {
   private final Annotated annotated;
   private BeanAttributes attributes;
   private BeanAttributesConfiguratorImpl configurator;
   private boolean veto;
   private boolean dirty;
   private boolean ignoreFinalMethods;
   private boolean beanAttributesSet;

   protected static ProcessBeanAttributesImpl fire(BeanManagerImpl beanManager, BeanAttributes attributes, Annotated annotated, Type type) {
      ProcessBeanAttributesImpl event = new ProcessBeanAttributesImpl(beanManager, attributes, annotated, type) {
      };
      event.fire();
      return event;
   }

   private ProcessBeanAttributesImpl(BeanManagerImpl beanManager, BeanAttributes attributes, Annotated annotated, Type type) {
      super(beanManager, ProcessBeanAttributes.class, new Type[]{type});
      this.attributes = attributes;
      this.annotated = annotated;
   }

   public Annotated getAnnotated() {
      this.checkWithinObserverNotification();
      return this.annotated;
   }

   public BeanAttributes getBeanAttributes() {
      this.checkWithinObserverNotification();
      return this.attributes;
   }

   public BeanAttributes getBeanAttributesInternal() {
      return this.attributes;
   }

   public void setBeanAttributes(BeanAttributes beanAttributes) {
      if (this.configurator != null) {
         throw BootstrapLogger.LOG.configuratorAndSetMethodBothCalled(ProcessBeanAttributes.class.getSimpleName(), this.getReceiver());
      } else {
         this.checkWithinObserverNotification();
         BootstrapLogger.LOG.setBeanAttributesCalled(this.getReceiver(), this.attributes, beanAttributes);
         this.attributes = beanAttributes;
         this.dirty = true;
         this.beanAttributesSet = true;
      }
   }

   public BeanAttributesConfigurator configureBeanAttributes() {
      if (this.beanAttributesSet) {
         throw BootstrapLogger.LOG.configuratorAndSetMethodBothCalled(ProcessBeanAttributes.class.getSimpleName(), this.getReceiver());
      } else {
         this.checkWithinObserverNotification();
         if (this.configurator == null) {
            this.configurator = new BeanAttributesConfiguratorImpl(this.attributes, this.getBeanManager());
         }

         BootstrapLogger.LOG.configureBeanAttributesCalled(this.getReceiver(), this.attributes);
         return this.configurator;
      }
   }

   public void veto() {
      this.checkWithinObserverNotification();
      this.veto = true;
      BootstrapLogger.LOG.beanAttributesVetoed(this.getReceiver(), this.attributes);
   }

   public void ignoreFinalMethods() {
      BootstrapLogger.LOG.ignoreFinalMethodsCalled(this.getReceiver(), this.attributes);
      this.ignoreFinalMethods = true;
   }

   public boolean isVeto() {
      return this.veto;
   }

   public boolean isDirty() {
      return this.dirty;
   }

   public boolean isIgnoreFinalMethods() {
      return this.ignoreFinalMethods;
   }

   public void postNotify(Extension extension) {
      super.postNotify(extension);
      if (this.configurator != null) {
         this.attributes = this.configurator.complete();
         this.configurator = null;
         this.dirty = true;
      }

      this.beanAttributesSet = false;
   }

   // $FF: synthetic method
   ProcessBeanAttributesImpl(BeanManagerImpl x0, BeanAttributes x1, Annotated x2, Type x3, Object x4) {
      this(x0, x1, x2, x3);
   }
}
