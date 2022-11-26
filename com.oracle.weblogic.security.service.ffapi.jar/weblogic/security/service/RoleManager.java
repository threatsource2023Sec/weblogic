package weblogic.security.service;

import java.util.Map;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.spi.Resource;

public interface RoleManager {
   Map getRoles(AuthenticatedSubject var1, Resource var2, ContextHandler var3);

   RoleManagerDeployHandle startDeployRoles(SecurityApplicationInfo var1) throws DeployHandleCreationException;

   void deployRole(RoleManagerDeployHandle var1, Resource var2, String var3, String[] var4) throws RoleCreationException;

   void endDeployRoles(RoleManagerDeployHandle var1) throws RoleCreationException;

   void undeployAllRoles(RoleManagerDeployHandle var1) throws RoleRemovalException;

   void deleteApplicationRoles(SecurityApplicationInfo var1) throws RoleRemovalException;

   boolean isVersionableApplicationSupported();

   void createApplicationVersion(String var1, String var2) throws ApplicationVersionCreationException;

   void deleteApplicationVersion(String var1) throws ApplicationVersionRemovalException;

   void deleteApplication(String var1) throws ApplicationRemovalException;

   boolean isUserInRole(AuthenticatedSubject var1, String var2, Resource var3, ContextHandler var4);
}
