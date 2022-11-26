package weblogic.deployment.configuration;

import weblogic.management.configuration.ParameterMBean;

public interface DeploymentValidationPlugin {
   void initialize(ParameterMBean[] var1);

   ValidationResult validate(DeploymentValidationContext var1);
}
