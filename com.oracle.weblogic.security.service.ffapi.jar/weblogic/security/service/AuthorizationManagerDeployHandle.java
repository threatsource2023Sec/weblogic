package weblogic.security.service;

import weblogic.security.service.internal.PolicyDeploymentService;
import weblogic.security.spi.DeployPolicyHandle;

public interface AuthorizationManagerDeployHandle {
   DeployPolicyHandle[] getDeployPolicyHandles();

   boolean isDeployPolicyIgnored();

   String getApplicationIdentifier();

   PolicyDeploymentService.DeploymentHandler getPolicyDeploymentHandler();
}
