package weblogic.management.utils;

import org.jvnet.hk2.annotations.Contract;
import weblogic.security.acl.internal.AuthenticatedSubject;

@Contract
public interface AdminServerDeploymentManagerServiceGenerator {
   AdminServerDeploymentManagerService createAdminServerDeploymentManager(AuthenticatedSubject var1);

   AdminServerDeploymentManagerService createAdminServerDeploymentManager(AuthenticatedSubject var1, String var2);

   AdminServerDeploymentManagerService createAdminServerDeploymentManager(AuthenticatedSubject var1, String var2, String var3);
}
