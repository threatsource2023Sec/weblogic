package weblogic.security.service;

import weblogic.security.service.internal.PolicyDeploymentService;
import weblogic.security.spi.DeployPolicyHandle;

public final class AuthorizationManagerDeployHandleImpl implements AuthorizationManagerDeployHandle {
   private String appId = null;
   private boolean ignore = true;
   private DeployPolicyHandle[] handles = null;
   private PolicyDeploymentService.DeploymentHandler handler = null;

   AuthorizationManagerDeployHandleImpl(String appId, boolean ignore) {
      this.appId = appId;
      this.ignore = ignore;
   }

   AuthorizationManagerDeployHandleImpl(PolicyDeploymentService.DeploymentHandler handler) {
      this.handler = handler;
   }

   public PolicyDeploymentService.DeploymentHandler getPolicyDeploymentHandler() {
      return this.handler;
   }

   public DeployPolicyHandle[] getDeployPolicyHandles() {
      return this.handles;
   }

   public void setDeployPolicyHandles(DeployPolicyHandle[] handles) {
      this.handles = handles;
   }

   public boolean isDeployPolicyIgnored() {
      return this.ignore;
   }

   public String getApplicationIdentifier() {
      return this.appId;
   }
}
