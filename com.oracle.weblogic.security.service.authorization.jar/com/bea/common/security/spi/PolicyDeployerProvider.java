package com.bea.common.security.spi;

import weblogic.security.spi.ApplicationInfo;
import weblogic.security.spi.DeployHandleCreationException;
import weblogic.security.spi.Resource;
import weblogic.security.spi.ResourceCreationException;
import weblogic.security.spi.ResourceRemovalException;

public interface PolicyDeployerProvider {
   DeploymentHandler startDeployPolicies(ApplicationInfo var1) throws DeployHandleCreationException;

   void deleteApplicationPolicies(ApplicationInfo var1) throws ResourceRemovalException;

   public interface DeploymentHandler {
      void deployPolicy(Resource var1, String[] var2) throws ResourceCreationException;

      void deployUncheckedPolicy(Resource var1) throws ResourceCreationException;

      void deployExcludedPolicy(Resource var1) throws ResourceCreationException;

      void endDeployPolicies() throws ResourceCreationException;

      void undeployAllPolicies() throws ResourceRemovalException;
   }
}
