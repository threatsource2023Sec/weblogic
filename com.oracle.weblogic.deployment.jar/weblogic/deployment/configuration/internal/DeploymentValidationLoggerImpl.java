package weblogic.deployment.configuration.internal;

import weblogic.deployment.configuration.DeploymentValidationLogger;
import weblogic.i18n.logging.NonCatalogLogger;

public class DeploymentValidationLoggerImpl implements DeploymentValidationLogger {
   static final NonCatalogLogger logger = new NonCatalogLogger("DeploymentValidation");

   public void log(String message) {
      logger.info(message);
   }
}
