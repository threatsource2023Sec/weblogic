package org.jboss.weld.bootstrap.events;

import java.lang.reflect.Type;
import org.jboss.weld.exceptions.DefinitionException;
import org.jboss.weld.logging.BootstrapLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.Preconditions;

public abstract class AbstractDefinitionContainerEvent extends AbstractContainerEvent {
   protected AbstractDefinitionContainerEvent(BeanManagerImpl beanManager, Type rawType, Type[] actualTypeArguments) {
      super(beanManager, rawType, actualTypeArguments);
   }

   public void addDefinitionError(Throwable t) {
      Preconditions.checkArgumentNotNull(t, "Throwable t");
      this.checkWithinObserverNotification();
      this.getErrors().add(t);
      BootstrapLogger.LOG.addDefinitionErrorCalled(this.getReceiver(), t);
   }

   public void fire() {
      super.fire();
      if (!this.getErrors().isEmpty()) {
         throw new DefinitionException(this.getErrors());
      }
   }
}
