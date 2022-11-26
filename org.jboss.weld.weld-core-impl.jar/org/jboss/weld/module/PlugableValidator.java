package org.jboss.weld.module;

import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.weld.manager.BeanManagerImpl;

public interface PlugableValidator {
   default void validateInjectionPointForDefinitionErrors(InjectionPoint ij, Bean bean, BeanManagerImpl beanManager) {
   }

   default void validateInjectionPointForDeploymentProblems(InjectionPoint ij, Bean bean, BeanManagerImpl beanManager) {
   }
}
