package org.jboss.weld.bootstrap.events;

import java.lang.reflect.Type;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.ProcessInjectionPoint;
import javax.enterprise.inject.spi.configurator.InjectionPointConfigurator;
import org.jboss.weld.bootstrap.events.configurator.InjectionPointConfiguratorImpl;
import org.jboss.weld.injection.attributes.FieldInjectionPointAttributes;
import org.jboss.weld.injection.attributes.ForwardingFieldInjectionPointAttributes;
import org.jboss.weld.injection.attributes.ForwardingParameterInjectionPointAttributes;
import org.jboss.weld.injection.attributes.ParameterInjectionPointAttributes;
import org.jboss.weld.logging.BootstrapLogger;
import org.jboss.weld.manager.BeanManagerImpl;

public class ProcessInjectionPointImpl extends AbstractDefinitionContainerEvent implements ProcessInjectionPoint {
   private InjectionPoint ip;
   private InjectionPointConfiguratorImpl configurator;
   private boolean dirty;
   private boolean injectionPointSet;

   protected static FieldInjectionPointAttributes fire(FieldInjectionPointAttributes attributes, Class declaringComponentClass, BeanManagerImpl manager) {
      ProcessInjectionPointImpl event = new ProcessInjectionPointImpl(attributes, declaringComponentClass, manager, attributes.getAnnotated().getBaseType()) {
      };
      event.fire();
      return !event.isDirty() ? attributes : ForwardingFieldInjectionPointAttributes.of(event.getInjectionPointInternal());
   }

   public static ParameterInjectionPointAttributes fire(ParameterInjectionPointAttributes attributes, Class declaringComponentClass, BeanManagerImpl manager) {
      ProcessInjectionPointImpl event = new ProcessInjectionPointImpl(attributes, declaringComponentClass, manager, attributes.getAnnotated().getBaseType()) {
      };
      event.fire();
      return (ParameterInjectionPointAttributes)(!event.isDirty() ? attributes : ForwardingParameterInjectionPointAttributes.of(event.getInjectionPointInternal()));
   }

   protected ProcessInjectionPointImpl(InjectionPoint ip, Class declaringComponentClass, BeanManagerImpl beanManager, Type injectionPointType) {
      super(beanManager, ProcessInjectionPoint.class, new Type[]{ip.getBean() == null ? declaringComponentClass : ip.getBean().getBeanClass(), injectionPointType});
      this.ip = ip;
   }

   public InjectionPoint getInjectionPoint() {
      this.checkWithinObserverNotification();
      return this.ip;
   }

   InjectionPoint getInjectionPointInternal() {
      return this.ip;
   }

   public void setInjectionPoint(InjectionPoint injectionPoint) {
      if (this.configurator != null) {
         throw BootstrapLogger.LOG.configuratorAndSetMethodBothCalled(ProcessInjectionPoint.class.getSimpleName(), this.getReceiver());
      } else {
         this.checkWithinObserverNotification();
         BootstrapLogger.LOG.setInjectionPointCalled(this.getReceiver(), this.ip, injectionPoint);
         this.ip = injectionPoint;
         this.dirty = true;
         this.injectionPointSet = true;
      }
   }

   public InjectionPointConfigurator configureInjectionPoint() {
      if (this.injectionPointSet) {
         throw BootstrapLogger.LOG.configuratorAndSetMethodBothCalled(ProcessInjectionPoint.class.getSimpleName(), this.getReceiver());
      } else {
         this.checkWithinObserverNotification();
         if (this.configurator == null) {
            this.configurator = new InjectionPointConfiguratorImpl(this.ip);
         }

         BootstrapLogger.LOG.configureInjectionPointCalled(this.getReceiver(), this.ip);
         return this.configurator;
      }
   }

   public boolean isDirty() {
      return this.dirty;
   }

   public void postNotify(Extension extension) {
      super.postNotify(extension);
      if (this.configurator != null) {
         this.ip = this.configurator.complete();
         this.configurator = null;
         this.dirty = true;
      }

      this.injectionPointSet = false;
   }
}
