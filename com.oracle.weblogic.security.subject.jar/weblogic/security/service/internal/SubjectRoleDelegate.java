package weblogic.security.service.internal;

import javax.security.auth.Subject;
import weblogic.security.acl.internal.AuthenticatedSubject;

public interface SubjectRoleDelegate {
   boolean isUserAnAdministrator(Subject var1);

   boolean isUserAnAdministrator(AuthenticatedSubject var1);

   boolean doesUserHaveAnyAdminRoles(AuthenticatedSubject var1);

   boolean isUserInAdminRoles(AuthenticatedSubject var1, String[] var2);

   boolean isAdminPrivilegeEscalation(AuthenticatedSubject var1, String var2, String var3);

   boolean isAdminPrivilegeEscalation(AuthenticatedSubject var1, AuthenticatedSubject var2);
}
