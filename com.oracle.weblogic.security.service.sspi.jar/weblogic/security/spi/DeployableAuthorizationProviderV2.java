package weblogic.security.spi;

public interface DeployableAuthorizationProviderV2 extends AuthorizationProvider {
   DeployPolicyHandle startDeployPolicies(ApplicationInfo var1) throws DeployHandleCreationException;

   void deployPolicy(DeployPolicyHandle var1, Resource var2, String[] var3) throws ResourceCreationException;

   void deployUncheckedPolicy(DeployPolicyHandle var1, Resource var2) throws ResourceCreationException;

   void deployExcludedPolicy(DeployPolicyHandle var1, Resource var2) throws ResourceCreationException;

   void endDeployPolicies(DeployPolicyHandle var1) throws ResourceCreationException;

   void undeployAllPolicies(DeployPolicyHandle var1) throws ResourceRemovalException;

   void deleteApplicationPolicies(ApplicationInfo var1) throws ResourceRemovalException;
}
