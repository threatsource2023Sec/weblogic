package weblogic.security.service;

import java.util.Map;
import org.jvnet.hk2.annotations.Contract;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.acl.internal.AuthenticatedUser;
import weblogic.security.utils.KeyStoreInfo;

@Contract
public interface SecurityServiceManagerDelegate2 {
   AuthenticatedSubject getASFromAU(AuthenticatedUser var1);

   AuthenticatedSubject getSealedSubjectFromWire(AuthenticatedSubject var1, AuthenticatedUser var2);

   AuthenticatedSubject getASFromAUInServerOrClient(AuthenticatedUser var1);

   AuthenticatedSubject getASFromWire(AuthenticatedSubject var1);

   AuthenticatedSubject sendASToWire(AuthenticatedSubject var1);

   AuthenticatedUser convertToAuthenticatedUser(AuthenticatedUser var1);

   AuthenticatedSubject getServerIdentity(AuthenticatedSubject var1);

   boolean isTrustedServerIdentity(AuthenticatedSubject var1);

   AuthenticatedSubject seal(AuthenticatedSubject var1, AuthenticatedSubject var2);

   void initializeConfiguration(AuthenticatedSubject var1);

   void initializeDeploymentCallbacks(AuthenticatedSubject var1);

   void initializeServiceDelegate(AuthenticatedSubject var1, SecurityServiceManagerDelegate var2);

   /** @deprecated */
   @Deprecated
   boolean isAnonymousAdminLookupEnabled();

   boolean getEnforceStrictURLPattern();

   boolean getEnforceValidBasicAuthCredentials();

   AuthenticatedSubject getCurrentSubjectForWire(AuthenticatedSubject var1);

   boolean isKernelIdentity(AuthenticatedSubject var1);

   boolean isServerIdentity(AuthenticatedSubject var1);

   boolean isUserInRole(AuthenticatedSubject var1, String var2, Map var3);

   boolean areWebAppFilesCaseInsensitive();

   KeyStoreInfo getServerIdentityKeyStore(AuthenticatedSubject var1);

   KeyStoreInfo[] getServerTrustKeyStores(AuthenticatedSubject var1);

   boolean isCaseSensitiveUserNames();

   boolean isEmbeddedLdapNeeded(AuthenticatedSubject var1);
}
