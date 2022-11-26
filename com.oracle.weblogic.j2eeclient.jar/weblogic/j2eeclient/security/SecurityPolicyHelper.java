package weblogic.j2eeclient.security;

import java.io.File;
import java.security.AccessController;
import java.security.PermissionCollection;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.SupplementalPolicyObject;

public class SecurityPolicyHelper {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public static void registerSecurityPermissions(File[] codeBases, PermissionCollection pc) {
      SecurityServiceManager.checkKernelPermission();
      SupplementalPolicyObject.setPoliciesFromPermissions(kernelId, codeBases, pc, "EE_APPLICATION_CLIENT");
   }
}
