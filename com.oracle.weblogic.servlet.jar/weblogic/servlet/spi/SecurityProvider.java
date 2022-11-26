package weblogic.servlet.spi;

import java.security.PermissionCollection;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.SecureRandom;
import javax.net.ssl.SSLSocket;
import weblogic.management.DeploymentException;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrincipalAuthenticator;
import weblogic.security.service.SecurityServiceException;

public interface SecurityProvider {
   SubjectHandle getCurrentSubject();

   void pushSubject(SubjectHandle var1);

   void popSubject();

   SubjectHandle getAnonymousSubject();

   SubjectHandle getKernelSubject();

   Object unwrapSubject(SubjectHandle var1);

   SubjectHandle wrapSubject(Object var1);

   SubjectHandle wrapSubject(Object var1, Object var2);

   boolean registerSEPermissions(String[] var1, PermissionCollection var2, String var3) throws SecurityServiceException;

   void setJavaSecurityPolicies(String[] var1, String var2);

   void removeJavaSecurityPolices(String[] var1);

   boolean areWebAppFilesCaseInsensitive();

   boolean getEnforceStrictURLPattern();

   String getDefaultRealmName();

   boolean isSamlApp(String var1);

   boolean getEnforceValidBasicAuthCredentials();

   byte[] getRandomBytesFromSalt(int var1);

   String getRealmAuthMethods();

   SecureRandom getSecureRandom();

   PrincipalAuthenticator getSecurityService(String var1);

   void initializeJACC() throws DeploymentException;

   Object[] getSSLAttributes(SSLSocket var1);

   boolean isAdminPrivilegeEscalation(SubjectHandle var1, SubjectHandle var2);

   Object runAs(SubjectHandle var1, PrivilegedAction var2, AuthenticatedSubject var3);

   Object runAs(SubjectHandle var1, PrivilegedExceptionAction var2, AuthenticatedSubject var3) throws PrivilegedActionException;

   Object runAs(AuthenticatedSubject var1, PrivilegedAction var2, AuthenticatedSubject var3);

   Object runAs(AuthenticatedSubject var1, PrivilegedExceptionAction var2, AuthenticatedSubject var3) throws PrivilegedActionException;

   Object runAsForUserCode(AuthenticatedSubject var1, PrivilegedAction var2, AuthenticatedSubject var3);
}
