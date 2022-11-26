package org.jboss.weld.bootstrap.events;

import javax.enterprise.inject.spi.AfterDeploymentValidation;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.reflection.Reflections;

public class AfterDeploymentValidationImpl extends AbstractDeploymentContainerEvent implements AfterDeploymentValidation {
   public static void fire(BeanManagerImpl beanManager) {
      (new AfterDeploymentValidationImpl(beanManager)).fire();
   }

   protected AfterDeploymentValidationImpl(BeanManagerImpl beanManager) {
      super(beanManager, AfterDeploymentValidation.class, Reflections.EMPTY_TYPES);
   }

   public void addDeploymentProblem(Throwable t) {
      this.checkWithinObserverNotification();
      this.getErrors().add(t);
   }
}
