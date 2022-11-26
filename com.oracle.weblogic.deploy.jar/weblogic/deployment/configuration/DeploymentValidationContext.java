package weblogic.deployment.configuration;

import weblogic.deploy.api.tools.SessionHelper;

public interface DeploymentValidationContext {
   SessionHelper getSessionHelper();

   DeploymentValidationLogger getLogger();
}
