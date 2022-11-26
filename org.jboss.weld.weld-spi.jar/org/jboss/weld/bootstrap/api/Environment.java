package org.jboss.weld.bootstrap.api;

import java.util.Set;

public interface Environment {
   Set getRequiredDeploymentServices();

   Set getRequiredBeanDeploymentArchiveServices();

   default boolean isEEModulesAware() {
      return true;
   }
}
