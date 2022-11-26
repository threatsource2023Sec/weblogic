package weblogic.security.service;

import java.util.Map;
import javax.security.auth.Subject;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.spi.Direction;
import weblogic.security.spi.Resource;

public interface AuthorizationManager {
   boolean isAccessAllowed(AuthenticatedSubject var1, Map var2, Resource var3, ContextHandler var4, Direction var5);

   boolean isAccessAllowed(AuthenticatedSubject var1, Resource var2, ContextHandler var3);

   boolean isProtectedResource(Subject var1, Resource var2);

   boolean isProtectedResource(AuthenticatedSubject var1, Resource var2);

   boolean isResourceProtected(Subject var1, Resource var2);

   AuthorizationManagerDeployHandle startDeployPolicies(SecurityApplicationInfo var1) throws DeployHandleCreationException;

   void deployPolicy(AuthorizationManagerDeployHandle var1, Resource var2, String[] var3) throws ResourceCreationException;

   void deployUncheckedPolicy(AuthorizationManagerDeployHandle var1, Resource var2) throws ResourceCreationException;

   void deployExcludedPolicy(AuthorizationManagerDeployHandle var1, Resource var2) throws ResourceCreationException;

   void endDeployPolicies(AuthorizationManagerDeployHandle var1) throws ResourceCreationException;

   void undeployAllPolicies(AuthorizationManagerDeployHandle var1) throws ResourceRemovalException;

   void deleteApplicationPolicies(SecurityApplicationInfo var1) throws ResourceRemovalException;

   boolean isVersionableApplicationSupported();

   void createApplicationVersion(String var1, String var2) throws ApplicationVersionCreationException;

   void deleteApplicationVersion(String var1) throws ApplicationVersionRemovalException;

   void deleteApplication(String var1) throws ApplicationRemovalException;

   AuthorizationPolicyHandler getAuthorizationPolicyHandler(String var1, String var2, String var3, Resource[] var4) throws ConsumptionException;
}
