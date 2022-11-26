package weblogic.security.service;

import weblogic.security.service.internal.RoleDeploymentService;
import weblogic.security.spi.DeployRoleHandle;

public final class RoleManagerDeployHandleImpl implements RoleManagerDeployHandle {
   private String appId = null;
   private boolean ignore = true;
   private DeployRoleHandle[] handles = null;
   private RoleDeploymentService.DeploymentHandler handler = null;

   RoleManagerDeployHandleImpl(String appId, boolean ignore) {
      this.appId = appId;
      this.ignore = ignore;
   }

   RoleManagerDeployHandleImpl(RoleDeploymentService.DeploymentHandler handler) {
      this.handler = handler;
   }

   public RoleDeploymentService.DeploymentHandler getRoleDeploymentHandler() {
      return this.handler;
   }

   public DeployRoleHandle[] getDeployRoleHandles() {
      return this.handles;
   }

   public void setDeployRoleHandles(DeployRoleHandle[] handles) {
      this.handles = handles;
   }

   public boolean isDeployRoleIgnored() {
      return this.ignore;
   }

   public String getApplicationIdentifier() {
      return this.appId;
   }
}
