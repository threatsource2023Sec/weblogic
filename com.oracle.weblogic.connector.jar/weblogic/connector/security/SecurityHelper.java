package weblogic.connector.security;

import java.net.URL;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import javax.security.auth.login.LoginException;
import weblogic.security.acl.internal.AuthenticatedSubject;

public interface SecurityHelper {
   void pushSubject(AuthenticatedSubject var1, AuthenticatedSubject var2);

   void popSubject(AuthenticatedSubject var1);

   AuthenticatedSubject getCurrentSubject(AuthenticatedSubject var1);

   AuthenticatedSubject getAuthenticatedSubject(String var1, AuthenticatedSubject var2) throws LoginException;

   AuthenticatedSubject getAnonymousSubject();

   boolean isUserAnAdministrator(AuthenticatedSubject var1);

   boolean isUserAnonymous(AuthenticatedSubject var1);

   boolean isKernelIdentity(AuthenticatedSubject var1);

   boolean isAdminPrivilegeEscalation(AuthenticatedSubject var1, AuthenticatedSubject var2);

   AuthenticatedSubject authenticate(String var1, char[] var2, AuthenticatedSubject var3);

   Object runAs(AuthenticatedSubject var1, AuthenticatedSubject var2, PrivilegedAction var3);

   Object runAs(AuthenticatedSubject var1, AuthenticatedSubject var2, PrivilegedExceptionAction var3) throws PrivilegedActionException;

   void setPoliciesFromGrantStatement(AuthenticatedSubject var1, URL var2, String var3);

   void removePolicies(AuthenticatedSubject var1, URL var2);
}
