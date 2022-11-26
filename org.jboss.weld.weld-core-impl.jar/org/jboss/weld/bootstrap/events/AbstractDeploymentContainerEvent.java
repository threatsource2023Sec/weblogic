package org.jboss.weld.bootstrap.events;

import java.lang.reflect.Type;
import org.jboss.weld.exceptions.DeploymentException;
import org.jboss.weld.manager.BeanManagerImpl;

public abstract class AbstractDeploymentContainerEvent extends AbstractContainerEvent {
   protected AbstractDeploymentContainerEvent(BeanManagerImpl beanManager, Type rawType, Type[] actualTypeArguments) {
      super(beanManager, rawType, actualTypeArguments);
   }

   public void fire() {
      super.fire();
      if (!this.getErrors().isEmpty()) {
         if (this.getErrors().size() == 1) {
            throw new DeploymentException((Throwable)this.getErrors().get(0));
         } else {
            throw new DeploymentException(this.getErrors());
         }
      }
   }
}
