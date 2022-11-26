package org.jboss.weld.bootstrap.events;

import java.lang.reflect.Type;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ObserverMethod;
import javax.enterprise.inject.spi.ProcessObserverMethod;
import javax.enterprise.inject.spi.configurator.ObserverMethodConfigurator;
import org.jboss.weld.bootstrap.events.configurator.ObserverMethodConfiguratorImpl;
import org.jboss.weld.logging.BootstrapLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.Observers;
import org.jboss.weld.util.Preconditions;

public class ProcessObserverMethodImpl extends AbstractDefinitionContainerEvent implements ProcessObserverMethod {
   private final AnnotatedMethod beanMethod;
   private final ObserverMethod initialObserverMethod;
   private ObserverMethodConfiguratorImpl configurator;
   protected ObserverMethod observerMethod;
   protected boolean vetoed;
   private boolean observerMethodSet;

   public static ObserverMethod fire(BeanManagerImpl beanManager, AnnotatedMethod beanMethod, ObserverMethod observerMethod) {
      return fire(new ProcessObserverMethodImpl(beanManager, beanMethod, observerMethod));
   }

   protected static ObserverMethod fire(ProcessObserverMethodImpl event) {
      event.fire();
      return event.vetoed ? null : event.observerMethod;
   }

   ProcessObserverMethodImpl(BeanManagerImpl beanManager, AnnotatedMethod beanMethod, ObserverMethod observerMethod) {
      this(beanManager, beanMethod, observerMethod, ProcessObserverMethod.class);
   }

   ProcessObserverMethodImpl(BeanManagerImpl beanManager, AnnotatedMethod beanMethod, ObserverMethod observerMethod, Class rawType) {
      super(beanManager, rawType, new Type[]{observerMethod.getObservedType(), observerMethod.getBeanClass()});
      this.beanMethod = beanMethod;
      this.initialObserverMethod = observerMethod;
      this.observerMethod = observerMethod;
   }

   public AnnotatedMethod getAnnotatedMethod() {
      this.checkWithinObserverNotification();
      return this.beanMethod;
   }

   public ObserverMethod getObserverMethod() {
      this.checkWithinObserverNotification();
      return this.observerMethod;
   }

   public void setObserverMethod(ObserverMethod observerMethod) {
      if (this.configurator != null) {
         throw BootstrapLogger.LOG.configuratorAndSetMethodBothCalled(ProcessObserverMethod.class.getSimpleName(), this.getReceiver());
      } else {
         Preconditions.checkArgumentNotNull(observerMethod, "observerMethod");
         this.checkWithinObserverNotification();
         this.replaceObserverMethod(observerMethod);
         this.observerMethodSet = true;
      }
   }

   public ObserverMethodConfigurator configureObserverMethod() {
      if (this.observerMethodSet) {
         throw BootstrapLogger.LOG.configuratorAndSetMethodBothCalled(ProcessObserverMethod.class.getSimpleName(), this.getReceiver());
      } else {
         this.checkWithinObserverNotification();
         if (this.configurator == null) {
            this.configurator = new ObserverMethodConfiguratorImpl(this.observerMethod, this.getReceiver());
         }

         BootstrapLogger.LOG.configureObserverMethodCalled(this.getReceiver(), this.observerMethod);
         return this.configurator;
      }
   }

   public void veto() {
      this.checkWithinObserverNotification();
      this.vetoed = true;
   }

   public boolean isDirty() {
      return this.observerMethod != this.initialObserverMethod;
   }

   public void postNotify(Extension extension) {
      super.postNotify(extension);
      if (this.configurator != null) {
         this.replaceObserverMethod(this.configurator.complete());
         this.configurator = null;
      }

      this.observerMethodSet = false;
   }

   private void replaceObserverMethod(ObserverMethod observerMethod) {
      Observers.validateObserverMethod(observerMethod, this.getBeanManager(), this.initialObserverMethod);
      this.observerMethod = observerMethod;
   }
}
