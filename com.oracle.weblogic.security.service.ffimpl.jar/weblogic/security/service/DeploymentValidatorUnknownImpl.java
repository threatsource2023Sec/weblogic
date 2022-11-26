package weblogic.security.service;

import weblogic.security.spi.Resource;

public class DeploymentValidatorUnknownImpl implements DeploymentValidator {
   public DeploymentValidationResult doesPrincipalExist(String principalName) {
      return DeploymentValidationResult.UNKNOWN;
   }

   public DeploymentValidationResult doesRoleExist(String roleName, Resource resource) {
      return DeploymentValidationResult.UNKNOWN;
   }
}
