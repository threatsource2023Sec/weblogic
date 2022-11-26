package weblogic.security.service.internal;

import weblogic.security.service.DeployHandleCreationException;
import weblogic.security.service.ResourceCreationException;
import weblogic.security.service.ResourceRemovalException;
import weblogic.security.service.SecurityApplicationInfo;
import weblogic.security.spi.Resource;

public interface PolicyDeploymentService {
   DeploymentHandler startDeployPolicies(SecurityApplicationInfo var1) throws DeployHandleCreationException;

   void deleteApplicationPolicies(SecurityApplicationInfo var1) throws ResourceRemovalException;

   public interface DeploymentHandler {
      void deployPolicy(Resource var1, String[] var2) throws ResourceCreationException;

      void deployUncheckedPolicy(Resource var1) throws ResourceCreationException;

      void deployExcludedPolicy(Resource var1) throws ResourceCreationException;

      void endDeployPolicies() throws ResourceCreationException;

      void undeployAllPolicies() throws ResourceRemovalException;
   }
}
