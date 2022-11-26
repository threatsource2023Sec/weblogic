package com.bea.common.security.service;

import weblogic.security.service.DeployHandleCreationException;
import weblogic.security.service.RoleCreationException;
import weblogic.security.service.RoleRemovalException;
import weblogic.security.service.SecurityApplicationInfo;
import weblogic.security.spi.Resource;

public interface RoleDeploymentService {
   DeploymentHandler startDeployRoles(SecurityApplicationInfo var1) throws DeployHandleCreationException;

   void deleteApplicationRoles(SecurityApplicationInfo var1) throws RoleRemovalException;

   public interface DeploymentHandler {
      void deployRole(Resource var1, String var2, String[] var3) throws RoleCreationException;

      void endDeployRoles() throws RoleCreationException;

      void undeployAllRoles() throws RoleRemovalException;
   }
}
