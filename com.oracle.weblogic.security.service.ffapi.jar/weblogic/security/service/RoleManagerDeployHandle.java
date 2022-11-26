package weblogic.security.service;

import weblogic.security.service.internal.RoleDeploymentService;
import weblogic.security.spi.DeployRoleHandle;

public interface RoleManagerDeployHandle {
   DeployRoleHandle[] getDeployRoleHandles();

   boolean isDeployRoleIgnored();

   String getApplicationIdentifier();

   RoleDeploymentService.DeploymentHandler getRoleDeploymentHandler();
}
