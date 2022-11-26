package weblogic.security.service;

import weblogic.security.spi.Resource;

public interface DeploymentValidator {
   DeploymentValidationResult doesPrincipalExist(String var1);

   DeploymentValidationResult doesRoleExist(String var1, Resource var2);
}
