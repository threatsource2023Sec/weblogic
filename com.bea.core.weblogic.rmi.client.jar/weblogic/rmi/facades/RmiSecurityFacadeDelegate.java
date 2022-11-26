package weblogic.rmi.facades;

import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import org.jvnet.hk2.annotations.Contract;
import weblogic.rmi.client.facades.RmiClientSecurityFacadeDelegate;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.acl.internal.AuthenticatedUser;
import weblogic.security.service.CredentialManager;
import weblogic.security.service.PrincipalAuthenticator;

@Contract
public interface RmiSecurityFacadeDelegate extends RmiClientSecurityFacadeDelegate {
   boolean doDoesUserHaveAnyAdminRoles(AuthenticatedSubject var1);

   AuthenticatedSubject doSendASToWire(AuthenticatedSubject var1);

   AuthenticatedSubject doGetASFromAUInServerOrClient(AuthenticatedUser var1);

   AuthenticatedUser doConvertToAuthenticatedUser(AuthenticatedSubject var1);

   CredentialManager doGetCredentialManager(AuthenticatedSubject var1, String var2);

   String doGetDefaultRealm();

   PrincipalAuthenticator doGetPrincipalAuthenticator(AuthenticatedSubject var1, String var2);

   boolean doIsSecurityServiceInitialized();

   boolean doIsUserAnAdministrator(AuthenticatedSubject var1);

   Object doRunAs(AuthenticatedSubject var1, AuthenticatedSubject var2, PrivilegedExceptionAction var3) throws PrivilegedActionException;
}
