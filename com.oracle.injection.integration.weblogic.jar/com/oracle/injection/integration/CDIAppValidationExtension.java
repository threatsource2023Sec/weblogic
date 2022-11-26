package com.oracle.injection.integration;

import javax.enterprise.inject.spi.DefinitionException;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.utils.BaseAppDeploymentExtension;
import weblogic.management.DeploymentException;

public class CDIAppValidationExtension extends BaseAppDeploymentExtension {
   public void activate(ApplicationContextInternal appCtx) throws DeploymentException {
      if (!appCtx.getCdiPolicy().equals("Disabled")) {
         WeblogicContainerIntegrationService integrationService = (WeblogicContainerIntegrationService)appCtx.getUserObject(WeblogicContainerIntegrationService.class.getName());
         if (integrationService != null) {
            try {
               integrationService.validateProducerFields();
            } catch (DefinitionException var4) {
               throw new DeploymentException(var4);
            }
         }
      }

   }
}
