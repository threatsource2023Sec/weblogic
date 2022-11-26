package com.bea.common.security.spi;

import weblogic.security.spi.ApplicationInfo;
import weblogic.security.spi.DeployHandleCreationException;
import weblogic.security.spi.Resource;
import weblogic.security.spi.RoleCreationException;
import weblogic.security.spi.RoleRemovalException;

public interface RoleDeployerProvider {
   DeploymentHandler startDeployRoles(ApplicationInfo var1) throws DeployHandleCreationException;

   void deleteApplicationRoles(ApplicationInfo var1) throws RoleRemovalException;

   public interface DeploymentHandler {
      void deployRole(Resource var1, String var2, String[] var3) throws RoleCreationException;

      void endDeployRoles() throws RoleCreationException;

      void undeployAllRoles() throws RoleRemovalException;
   }
}
